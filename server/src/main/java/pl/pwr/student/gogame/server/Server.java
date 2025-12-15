
package pl.pwr.student.gogame.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * A server for a multi-player tic tac toe game. Loosely based on an example in
 * Deitel and Deitel’s “Java How to Program” book from the late 1990s. For this
 * project I created a new application-level protocol called TTTP (for Tic Tac
 * Toe Protocol), which is entirely plain text. The messages of TTTP are:
 *
 * Client -> Server
 *     MOVE <n>
 *     QUIT
 *
 * Server -> Client
 *
 *     WELCOME <char>
 *     VALID_MOVE
 *     OTHER_PLAYER_MOVED <n>
 *     OTHER_PLAYER_LEFT
 *     VICTORY
 *     DEFEAT
 *     TIE
 *     MESSAGE <text>
 */

import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.builder.GameBuilder;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;
import pl.pwr.student.gogame.model.commands.Command;
import pl.pwr.student.gogame.model.exceptions.PlayersNotSettledException;

public class Server {
  // TODO: wiele gier na jednym serwerze jednocześnie
  private Game game;

  private User user1;
  private User user2;
  
  private Integer idAutoincrement = 0;

  void main(String[] args) throws Exception {
    try (var listener = new ServerSocket(58901)) {
      IO.println("Go Server is Running...");
      try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
        while (true) {
          // oczekiwanie na przychodzące połączenie
          user1 = new User(listener.accept());
          // utworzenie nowego wątku odpowiedzialnego za komunikację z użytkownikiem 1
          pool.execute(user1);

          user2 = new User(listener.accept());
          pool.execute(user2);
        }
      }
    }
  }

  private void initializeGame() {
    GameBuilder gb = new StandardGameBuilder();
    gb.setPlayer1(user1).setPlayer2(user2);
    try {
      game = gb.buildGame();
    } catch (PlayersNotSettledException e) {
      user1.output.println("SAY,Błąd serwera");
      user2.output.println("SAY,Błąd serwera");
      System.exit(1);
    }
  }

  // W przyszłości możliwość rozszerzenia klasy Player w taki sposób, aby możliwa była gra z komputerowym przeciwnikiem
  class User extends Player implements Runnable {
    final Socket socket;
    final Scanner input;
    final PrintWriter output;

    public Boolean isReady = false;

    public User(Socket socket) throws IOException {
      super();
      this.socket = socket;
      this.input = new Scanner(socket.getInputStream());
      this.output = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
      try (socket) {
        setup();
        processCommands();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        IO.println("Player " + this + " disconnected");
        if (this.getId() == user1.getId()) {
          user1 = null;
        } else {
          user2 = null;
        }
      }
    }

    private void broadcastMessage(String message) {
      user1.output.println("SAY," + message);
      user2.output.println("SAY," + message);
    }

    @Override
    public String toString() {
      return getUsername() + "@" + socket.getRemoteSocketAddress();
    }

    private void setup() {
      output.println("CONNECTED");
      output.println("SAY,Podaj swoje imię");
      setUsername(input.nextLine());
      setId(idAutoincrement++);
      output.println("SAY,Witaj " + getUsername() + "! Twoje ID to " + getId());
      isReady = true;

      if (user2 == null || !user2.isReady) {
        user1.output.println("SAY,Oczekiwanie na połączenie drugiego gracza");
      } else {
        initializeGame();

        if (game.getBlackPlayerId() == user1.getId()) {
          user1.output.println("SAY,Grasz czarnymi");
          user2.output.println("SAY,Grasz białymi");
        } else {
          user1.output.println("SAY,Grasz białymi");
          user2.output.println("SAY,Grasz czarnymi");
        }

        broadcastMessage(game.getBoard().toString());
      }
    }

    private void processCommands() {
      String request;
      Command cmd;
      while (input.hasNextLine()) {
        request = input.nextLine();
        if (request.startsWith("PUT") || request.startsWith("PASS")) {
          if (game == null) {
            output.println("SAY,Gra jeszcze się nie rozpoczęła");
            continue;
          }
          try {
            cmd = Command.fromString(request);
          } catch (Exception e) {
            output.println("SAY,Nieznane polecenie");
            continue;
          }
          game.execCommand(cmd);
        }
      }
    }
  }
}


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
import java.util.Scanner;
import java.util.concurrent.Executors;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.builder.GameBuilder;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;
import pl.pwr.student.gogame.model.commands.ClientCommand;
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

  private enum ServerCommand {
    SAY,
    PRINT_BOARD,
    PUT,
    PASS,
    GAME_START,
    BLACK_PLAYER,
    WHITE_PLAYER,
    CONNECTED,
    SET_PLAYER,
    SET_ENEMY
  }

  private void checkPlayersReady() {
    if (user1 == null || user2 == null) {
      return;
    }

    if (user1.isReady && user2.isReady) {
      user1.setEnemy(user2);
      sendCommand(ServerCommand.SET_ENEMY, user2.toString(), user1);
      user2.setEnemy(user1);
      sendCommand(ServerCommand.SET_ENEMY, user1.toString(), user2);
      initializeGame();
    }
  }

  private void initializeGame() {
    GameBuilder gb = new StandardGameBuilder();
    gb.setPlayer1(user1).setPlayer2(user2);
    try {
      game = gb.buildGame();
      game.startGame();
    } catch (PlayersNotSettledException e) {
      broadcastMessage(ServerCommand.SAY, "Błąd serwera");
      System.exit(1);
    }
    if (user1.getId() == game.getBlackPlayerId()) {
      sendCommand(ServerCommand.BLACK_PLAYER, null, user1);
      sendCommand(ServerCommand.WHITE_PLAYER, null, user2);
    } else {
      sendCommand(ServerCommand.WHITE_PLAYER, null, user1);
      sendCommand(ServerCommand.BLACK_PLAYER, null, user2);
    }

    broadcastMessage(ServerCommand.GAME_START);
  }

  private void broadcastMessage(ServerCommand command) {
    sendCommand(command, null, user1);
    sendCommand(command, null, user2);
  }

  private void broadcastMessage(ServerCommand command, String message) {
    sendCommand(command, message, user1);
    sendCommand(command, message, user2);
  }

  private void sendCommand(ServerCommand command, String message, User user) {
    user.output.println(command.name() + (message == null ? "" : "," + message));
  }
  
  private void sendCommand(ServerCommand command, User user) {
    sendCommand(command, null, user);
  }

  // W przyszłości możliwość rozszerzenia klasy Player w taki sposób, aby możliwa była gra z komputerowym przeciwnikiem
  class User extends Player implements Runnable {
    final Socket socket;
    final Scanner input;
    final PrintWriter output;

    public Boolean isReady = false;

    private User enemy;

    public User(Socket socket) throws IOException {
      super();
      this.socket = socket;
      this.input = new Scanner(socket.getInputStream());
      this.output = new PrintWriter(socket.getOutputStream(), true);
    }

    public void setEnemy(User u) {
      this.enemy = u;
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
        // TODO: mądrzejszy sposób obsługi rozłączenia klienta: na razie kończymy działanie serwera
        System.exit(0);
      }
    }

    @Override
    public String toString() {
      return getUsername()+","+getId();
    }

    private void setup() {
      sendCommand(ServerCommand.CONNECTED, this);
      sendCommand(ServerCommand.SAY, "Podaj swoje imię", this);

      // TODO: sprawdzenie czemu w tym miejscu proces blokował się na in.nextLine()
      setUsername("Player-tmp-name");
      setId(idAutoincrement++);

      sendCommand(ServerCommand.SET_PLAYER, this.toString(), this);
      sendCommand(ServerCommand.SAY, "Witaj " + getUsername() + "! Twoje ID to " + getId(), this);
      isReady = true;

      checkPlayersReady();
    }

    private void processCommands() {
      String request;
      ClientCommand cmd;
      while (input.hasNextLine()) {
        request = input.nextLine();
        if (request.startsWith("PUT") || request.startsWith("PASS")) {
          if (game == null) {
            sendCommand(ServerCommand.SAY, "Gra jeszcze się nie rozpoczęła", this);
            continue;
          }
          try {
            cmd = ClientCommand.fromString(request);
            cmd.playerId = getId();
          } catch (Exception e) {
            sendCommand(ServerCommand.SAY, "Błędny format polecenia, zobacz HELP", this);
            continue;
          }
          if (game.execCommand(cmd)) {
            // ruch się udał
            sendCommand(ServerCommand.SAY, "Ruch OK", this);
            // powiadamiamy przeciwnika o wykonanym ruchu
            enemy.output.println(cmd.toString());
            // serwer wymusza ponowne wyświetlenie boarda
            broadcastMessage(ServerCommand.PRINT_BOARD, "");
          } else {
            // ruch się nie udał
            sendCommand(ServerCommand.SAY, "Nielegalny ruch", this);
          }
        }
      }
    }
  }
}

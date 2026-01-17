
package pl.pwr.student.gogame.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;
import pl.pwr.student.gogame.model.states.BlackTurn;
import pl.pwr.student.gogame.model.utilities.Player;

public class Server {
  private StandardGameBuilder gb = new StandardGameBuilder();
  private Game game;

  private User user1;
  private User user2;
  
  private Integer idAutoincrement = 0;

  void main(String[] args) throws Exception {
    try (var listener = new ServerSocket(58901)) {
      IO.println("Go Server is Running...");
      try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
        // oczekiwanie na przychodzące połączenie
        user1 = new User(listener.accept());
        // utworzenie nowego wątku odpowiedzialnego za komunikację z użytkownikiem 1
        pool.execute(user1);
        user2 = new User(listener.accept());
        pool.execute(user2);
      }
    }
  }

  private void broadcastMessage(String msg) {
    user1.output.println(msg);
    user2.output.println(msg);
  }

  class User extends Player implements Runnable {
    final Socket socket;
    final Scanner input;
    final PrintWriter output;

    public Boolean isReady = false;

    public User(Socket socket) throws IOException {
      super("user-" + idAutoincrement++);
      this.socket = socket;
      this.input = new Scanner(socket.getInputStream());
      this.output = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public String toString() {
      return getId();
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

    private void sendBoard() {
      broadcastMessage(game.getGameInfo().board().toCSV());
    }

    private void setup() {
      gb.addPlayer(this);
      try {
        game = gb.buildGame();
        sendBoard();
      } catch (RuntimeException e) {
        output.println("SAY,Oczekiwanie na dołączenie drugiego gracza...");
      }
    }

    private void processCommands() {
      while (input.hasNextLine()) {
        String request = input.nextLine();
        System.out.println(this + ": " + request);
        String[] requestArgs = request.split(",");

        switch (requestArgs[0]) {
          case "PUT":
            Integer x = Integer.parseInt(requestArgs[1]) + 1;
            Integer y = Integer.parseInt(requestArgs[2]) + 1;
            game.putStone(x, y, getId());
            break;
          
          case "PASS":
            game.pass(getId());
            break;

          case "SURRENDER":
            game.surrender(getId());
            break;
        
          default:
            System.out.println("Nieznane polecenie: " + request);
            break;
        }

        System.out.println(game.getGameInfo().board().toString());
        System.out.println(game.getGameInfo().board().toCSV());

        if (game != null) {
          sendBoard();
        }
      }
    }
  }
}

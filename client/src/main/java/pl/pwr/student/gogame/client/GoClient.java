package pl.pwr.student.gogame.client;

import java.io.Console;
import java.io.IOError;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.utilities.Player;

// Pisane w oparciu o https://cs.lmu.edu/~ray/notes/javanetexamples/
class GoClient {
  private Game game;

  private final Socket socket;
  private final Scanner in;
  private final PrintWriter out;

  private Player myPlayer;
  private Player enemyPlayer;

  private Boolean amIBlackPlayer = null;

  private Console c = System.console();

  public GoClient(String serverAddress) throws Exception {
    socket = new Socket(serverAddress, 58901);
    in = new Scanner(socket.getInputStream());
    out = new PrintWriter(socket.getOutputStream(), true);
  }

  public void connect() throws Exception {
    awaitFromServer();
  }

  public void onConnected() {
    new Thread(() -> {
        readUserInput();
    }).start();
  }

  private void readUserInput() {
    String userInput = "";
    String[] arguments;

    while (true) {
      try {
        userInput = c.readLine();
      } catch (IOError e) {
        System.out.println("System console I/O error");
        System.exit(1);
      }

      arguments = userInput.split(" ");

      switch (arguments[0].toLowerCase()) {
        case "h":
        case "help":
          System.out.println("help - wypisz tą wiadomość\nput x y - ustaw kamień w kolumnie x oraz wierszu y (numeracja od 0)\npass - pomiń ruch");
          break;

        case "put":
          if (game == null) {
            break;
          }
          // TODO: implementacja put
          break;

        case "pass":
          if (game == null) {
            break;
          }
          // TODO: implementacja pass
          break;
      
        default:
          break;
      }
    }
  }

  private void printBoard() {
    System.out.println(game.getGameInfo().board().toString());
  }

  public void awaitFromServer() throws Exception {
    try (socket) {
      String response;
      String[] arguments;

      while (in.hasNextLine()) {
        
      }
    }

    System.exit(0);
  }
}

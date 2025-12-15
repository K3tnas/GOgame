package pl.pwr.student.gogame.client;

import java.io.Console;
import java.io.IOError;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.commands.CMDPass;

/**
 * A client for a multi-player tic tac toe game. Loosely based on an example in
 * Deitel and Deitel’s “Java How to Program” book from the late 1990s. For this
 * project I created a new application-level protocol called TTTP (for Tic Tac
 * Toe Protocol), which is entirely plain text. The messages of TTTP are:
 *
 * Client -> Server
 * MOVE <n>
 * QUIT
 *
 * Server -> Client
 * WELCOME <char>
 * VALID_MOVE
 * OTHER_PLAYER_MOVED <n>
 * OTHER_PLAYER_LEFT
 * VICTORY
 * DEFEAT
 * TIE
 * MESSAGE <text>
 */
class GoClient {
  private Game game;

  private final Socket socket;
  private final Scanner in;
  private final PrintWriter out;

  private Boolean amIBlackPlayer = null;

  private Console c = System.console();

  public GoClient(String serverAddress) throws Exception {
    socket = new Socket(serverAddress, 58901);
    in = new Scanner(socket.getInputStream());
    out = new PrintWriter(socket.getOutputStream(), true);
  }

  public void connect() {
    try {
      awaitFromServer();
    } catch (Exception e) {
      System.out.println("Błąd w komunikacji z serwerem");
      System.out.print(e.getMessage());
      System.exit(1);
    }
  }

  public void onConnected() {
    new Thread(() -> {
        readUserInput();
    }).start();
  }

  private void printHelp() {
    System.out.println("Tu będzie help");
  }

  private void readUserInput() {
    String message;
    while (true) {
      try {
        message = c.readLine();
        if ("HELP".equals(message)) {
          printHelp();
        }
        out.println(message);
      } catch (IOError e) {
        System.out.println("System console I/O error");
        System.exit(1);
      }
    }
  }

  public void awaitFromServer() throws Exception {
    try (socket) {
      String response;
      String[] arguments;

      while (in.hasNextLine()) {
        response = in.nextLine();
        arguments = response.split(",");

        switch (arguments[0]) {
          case "SAY":
            System.out.println(response.substring(4));
            break;

          case "CONNECTED":
            onConnected();
            break;

          case "BLACK_PLAYER":
            amIBlackPlayer = true;
            break;

          case "WHITE_PLAYER":
            amIBlackPlayer = false;
            break;

          case "PRINT_BOARD":
            if (game == null) {
              break;
            }
            System.out.println(game.getBoard().toString());
            break;

          case "PASS":
            if (game == null) {
              break;
            }
            game.execCommand(new CMDPass(Integer.parseInt(arguments[1])));
            break;

          case "GAME_START":

        
          default:
            break;
        }
      }
      // Inform server that we are quitting
      out.println("QUIT");
    }

    System.exit(0);
  }
}

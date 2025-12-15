package pl.pwr.student.gogame.client;

import java.io.Console;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.builder.GameBuilder;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;

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
      System.out.println("Connection with server failed");
      System.out.print(e.getMessage());
      System.exit(1);
    }
  }

  public void onConnected() {
    new Thread(() -> {
        readUserInput();
    }).start();
  }

  private void readUserInput() {
    String message;
    while (true) {
      try {
        message = c.readLine();
        out.println(message);
      } catch (IOError e) {
        System.out.println("System console I/O error");
        System.exit(1);
      }
    }
  }

  /**
   * The main thread of the client will listen for messages from the server.
   * The first message will be a "WELCOME" message in which we receive our
   * mark. Then we go into a loop listening for any of the other messages,
   * and handling each message appropriately. The "VICTORY", "DEFEAT", "TIE",
   * and "OTHER_PLAYER_LEFT" messages will ask the user whether or not to play
   * another game. If the answer is no, the loop is exited and the server is
   * sent a "QUIT" message.
   */
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

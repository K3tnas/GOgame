package pl.pwr.student.gogame.client;

import java.io.Console;
import java.io.IOError;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import pl.pwr.student.gogame.model.Game;
import pl.pwr.student.gogame.model.Player;
import pl.pwr.student.gogame.model.builder.GameBuilder;
import pl.pwr.student.gogame.model.builder.StandardGameBuilder;
import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.commands.CMDPut;
import pl.pwr.student.gogame.model.commands.ClientCommand;

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

  private Player myPlayer;
  private Player enemyPlayer;

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
          CMDPut cmdPut;
          try {
            cmdPut = new CMDPut(Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
          } catch (Exception e) {
            System.out.println(e.getMessage());
            break;
          }
          if (game.execCommand(cmdPut)) {
            out.println(cmdPut.toString());
          }
          break;

        case "pass":
          if (game == null) {
            break;
          }
          CMDPass cmdPass = new CMDPass();
          if (game.execCommand(cmdPass)) {
            out.println(cmdPass.toString());
          }
          break;
      
        default:
          break;
      }
    }
  }

  private void printBoard() {
    System.out.println(game.getBoard().toString());
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
            System.out.println("Serwer: " + response.substring(4));
            break;

          case "CONNECTED":
            onConnected();
            break;

          case "BLACK_PLAYER":
            amIBlackPlayer = true;
            System.out.println("Grasz czarnymi");
            break;

          case "WHITE_PLAYER":
            amIBlackPlayer = false;
            System.out.println("Grasz białymi");
            break;

          case "SET_PLAYER":
            myPlayer = new Player(arguments[1], Integer.parseInt(arguments[2]));
            break;

          case "SET_ENEMY":
            enemyPlayer = new Player(arguments[1], Integer.parseInt(arguments[2]));
            System.out.println("Grasz przeciwko " + enemyPlayer.toString());
            break;

          case "PRINT_BOARD":
            if (game == null) {
              break;
            }
            printBoard();
            break;

          case "PASS":
          case "PUT":
            if (game == null) {
              break;
            }
            game.execCommand(ClientCommand.fromString(response));
            break;

          case "GAME_START":
            GameBuilder gb = new StandardGameBuilder();
            if (amIBlackPlayer) {
              gb.setBlackPlayer(myPlayer).setWhitePlayer(enemyPlayer);
            } else {
              gb.setWhitePlayer(myPlayer).setBlackPlayer(enemyPlayer);
            }

            game = gb.buildGame();
            game.startGame();

            System.out.println("No to gramy!");
            printBoard();
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

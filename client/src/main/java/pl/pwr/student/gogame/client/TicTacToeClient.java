package pl.pwr.student.gogame.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
class TicTacToeClient {

  JFrame frame = new JFrame("Tic Tac Toe");
  private JLabel messageLabel = new JLabel("...");

  private Square[] board = new Square[9];
  private Square currentSquare;

  private final Socket socket;
  private final Scanner in;
  private final PrintWriter out;

  public TicTacToeClient(String serverAddress) throws Exception {
    socket = new Socket(serverAddress, 58901);
    in = new Scanner(socket.getInputStream());
    out = new PrintWriter(socket.getOutputStream(), true);
  }

  public void createAndShowGUI() {
    // Runs on the EDT
    messageLabel.setBackground(Color.lightGray);
    frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);

    var boardPanel = new JPanel();
    boardPanel.setBackground(Color.black);
    boardPanel.setLayout(new GridLayout(3, 3, 2, 2));
    for (var i = 0; i < board.length; i++) {
      final int j = i;
      board[i] = new Square();
      board[i].addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          currentSquare = board[j];
          out.println("MOVE " + j);
        }
      });
      boardPanel.add(board[i]);
    }
    frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
  }

  static class Square extends JPanel {
    JLabel label = new JLabel();

    public Square() {
      setBackground(Color.white);
      setLayout(new GridBagLayout());
      label.setFont(new Font("Arial", Font.BOLD, 40));
      add(label);
    }

    public void setText(char text) {
      label.setForeground(text == 'X' ? Color.BLUE : Color.RED);
      label.setText(text + "");
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
  public void play() throws Exception {
    try (socket) {
      var response = in.nextLine();
      final var mark = response.charAt(8);
      final var opponentMark = mark == 'X' ? 'O' : 'X';
      frame.setTitle("Tic Tac Toe: Player " + mark);
      while (in.hasNextLine()) {
        response = in.nextLine();
        if (response.startsWith("VALID_MOVE")) {
          ui(() -> {
            messageLabel.setText("Valid move, please wait");
            currentSquare.setText(mark);
            currentSquare.repaint();
          });
        } else if (response.startsWith("OPPONENT_MOVED")) {
          var loc = Integer.parseInt(response.substring(15));
          ui(() -> {
            board[loc].setText(opponentMark);
            board[loc].repaint();
            messageLabel.setText("Opponent moved, your turn");
          });
        } else if (response.startsWith("MESSAGE")) {
          var message = response.substring(8);
          ui(() -> {
            messageLabel.setText(message);
          });
        } else if (response.startsWith("VICTORY")) {
          ui(() -> {
            JOptionPane.showMessageDialog(frame, "Winner Winner");
          });
          break;
        } else if (response.startsWith("DEFEAT")) {
          ui(() -> {
            JOptionPane.showMessageDialog(frame, "Sorry you lost");
          });
          break;
        } else if (response.startsWith("TIE")) {
          ui(() -> {
            JOptionPane.showMessageDialog(frame, "Tie");
          });
          break;
        } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
          ui(() -> {
            JOptionPane.showMessageDialog(frame, "Other player left");
          });
          break;
        }
      }
      // Inform server that we are quitting
      out.println("QUIT");
    }
    // Shutdown after clicking one of the game over dialogs
    System.exit(0);
  }

  private void ui(Runnable action) throws Exception {
    SwingUtilities.invokeAndWait(action);
  }

}

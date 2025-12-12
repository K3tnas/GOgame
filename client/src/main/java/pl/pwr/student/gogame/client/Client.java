package pl.pwr.student.gogame.client;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Client {
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Pass the server IP as the sole command line argument");
      return;
    }
    var client = new TicTacToeClient(args[0]);
    SwingUtilities.invokeAndWait(() -> {
      client.createAndShowGUI();
      client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      client.frame.setSize(320, 320);
      client.frame.setVisible(true);
      client.frame.setResizable(false);
    });
    client.play();
  }
}

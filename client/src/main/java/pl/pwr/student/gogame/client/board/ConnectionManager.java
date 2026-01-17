package pl.pwr.student.gogame.client.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import pl.pwr.student.gogame.client.Client;
import pl.pwr.student.gogame.client.board.GUICommands.GUICommand;
import pl.pwr.student.gogame.model.board.Board;

/**
 * Wątek obsługujący komunikację z serwerem
 */
public class ConnectionManager extends Thread {
    private final BlockingQueue<GUICommand> commands = new LinkedBlockingQueue<GUICommand>();
    private final String serverIP;
    private final Socket socket;
    private final Scanner in;
    private final PrintWriter out;
    private final Client parent;

    public ConnectionManager(String serverIP, Client parent) throws IOException {
        this.parent = parent;
        this.serverIP = serverIP;
        socket = new Socket(this.serverIP, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Zakolejkuj polecenie, które ma zostać wysłane do serwera
     */
    public void queueCommand(GUICommand cmd) {
        commands.add(cmd);
    }

    /**
     * Kod wątku wysyłającego polecenia do serwera
     */
    public void run() {
        new Thread(this::receive).start();

        while (true) {
            GUICommand cmd = null;
            try {
                cmd = commands.poll(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (cmd == null) {
                // TODO: obsługa timeoutu metody .poll (może jakieś automatyczne poddanie? :) )
            } else {
                out.println(cmd.toString());
            }

            cmd = null;
        }
    }

    /**
     * Kod wątku odbierającego polecenia od serwera - z klasy Client
     */
    public void receive() {
        while (in.hasNextLine()) {
            String serverCommand = in.nextLine();
            if (serverCommand.startsWith("SIZE")) {
                try {
                    parent.setBoard(Board.fromCSV(serverCommand));
                    Platform.runLater(parent::redrawBoard);
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            } else if (serverCommand.startsWith("SAY")) {
                String message = serverCommand.split(",")[1];
                Platform.runLater(() -> parent.sayInChat("Serwer: " + message));
            }
        }
    }

}

package pl.pwr.student.gogame.client.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Wątek obsługujący komunikację z serwerem
 */
public class ConnectionManager extends Thread {
    private final BlockingQueue<BoardCommand> commands = new LinkedBlockingQueue<BoardCommand>();
    private final String serverIP;
    private final Socket socket;
    private final Scanner in;
    private final PrintWriter out;

    public ConnectionManager(String serverIP) throws IOException {
        this.serverIP = serverIP;
        socket = new Socket(this.serverIP, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Zakolejkuj polecenie, które ma zostać wysłane do serwera
     */
    public void queueCommand(BoardCommand cmd) {
        commands.add(cmd);
    }

    /**
     * Kod wątku wysyłającego polecenia do serwera
     */
    public void run() {
        while (true) {
            BoardCommand cmd = null;
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
            // TODO: implementacja updateowania planszy zamiast wypisywania co przyszło
            System.out.println(serverCommand);
        }
    }
}

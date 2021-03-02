import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Pusher implements Runnable, Closeable {
    private Socket socket;
    private DataOutputStream out;
    private Scanner scan;

    public Pusher(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            close();
        } finally {
            scan = new Scanner(new BufferedInputStream(System.in));
        }
    }

    public void run() {
        while (!socket.isClosed()) {
            try {
                String line = scan.nextLine();
                out.writeUTF(line);
                if (line.equals("quit")) {
                    close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }
    }

    public void close() {
        try {
            scan.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

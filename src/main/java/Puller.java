import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Stack;

public class Puller implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private Stack<String> pulls;

    public Puller(Socket socket, Stack<String> pulls) {
        this.socket = socket;
        this.pulls = pulls;
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (!socket.isClosed()) {
            try {
                pulls.add(in.readUTF());
            } catch (IOException e) {
                if(!socket.isClosed()) e.printStackTrace();
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

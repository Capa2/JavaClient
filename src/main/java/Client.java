import java.io.IOException;
import java.net.Socket;
import java.util.Stack;

public class Client implements Runnable {
    Socket socket;
    Stack<String> pulls;
    Thread puller;
    Thread pusher;

    public Client(String address, int port) {
        System.out.println("Waiting for server...");
        try {
            socket = new Socket(address, port);
            System.out.println("Connected.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pulls = new Stack<String>();
            puller = new Thread(new Puller(socket, pulls));
            pusher = new Thread(new Pusher(socket));
        }
    }

    public void run() {
        puller.start();
        pusher.start();
        while (!socket.isClosed()) {
            while (!pulls.empty()) {
                System.out.println(pulls.pop());
            }
            if (puller.getState() == Thread.State.TERMINATED ||
                pusher.getState() == Thread.State.TERMINATED) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Connection closed.");
                }
            }
        }
    }
}

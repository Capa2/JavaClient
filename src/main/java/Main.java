public class Main {
    public static void main(String[] args) {
        final String address = (args.length > 0) ? args[0] : "localhost";
        final int port = (args.length > 1) ? Integer.parseInt(args[1]) : 5000;
        Client client = new Client(address, port);
        client.run();
    }
}

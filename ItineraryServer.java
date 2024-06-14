import java.net.*;
import java.io.*;

// Implements server that runs all the threads
public class ItineraryServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java ItineraryServer.java <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;
        

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            // Forever loop where the server is always listening for connection requests
            while (listening) {
                // Each connection req gets its own thread
                new ItineraryServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}

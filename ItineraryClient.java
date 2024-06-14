
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ItineraryClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.err.println(
                    "Usage: java ItineraryClient.java <host name> <port number> <client name>");
            System.exit(1);
        }

        // Take in hostname and portnumber as command line args
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String clientName = args[2];
        Scanner scanner = new Scanner(System.in);
        String menu, id;
        SerializableItinerary inputItinerary;

        try (
                // Initiate a connection request to server's IP address, port
                Socket socket = new Socket(hostName, portNumber);
                // Create input and output object streams
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {

            while (true) {

                menu = (String) in.readObject();
                System.out.println(menu);
                String option = scanner.next();
                out.writeObject(option);
                out.flush();

                switch (Integer.valueOf(option)) {
                    case 1: // Create new itinerary
                        System.out.println(in.readObject());
                        break;
                    case 2: // Show directory of itineraries
                        System.out.println(in.readObject());
                        break;
                    case 3: // Display existing itinerary
                        System.out.println(in.readObject());
                        id = scanner.next();
                        out.writeObject(id);
                        out.flush();
                        inputItinerary = (SerializableItinerary) in.readObject();
                        System.out.println(inputItinerary.toString());
                        break;
                    case 4: // Add event to existing itinerary
                        // Specify which itinerary to add event
                        System.out.println(in.readObject());
                        id = scanner.next();
                        out.writeObject(id);
                        out.flush();
                        // User inputs details for event creation
                        System.out.println(in.readObject());
                        String eventCreation = scanner.next();
                        out.writeObject(eventCreation);
                        out.flush();
                        // Receive message to client
                        System.out.println(in.readObject());
                        break;
                    case 5:
                        System.out.println("Still working on it...");
                        break;
                    case 6: // Delete itinerary
                        System.out.println(in.readObject());
                        id = scanner.next();
                        out.writeObject(id);
                        out.flush();
                        System.out.println(in.readObject());
                        break;
                    case 7: // Clear all itinereries
                        System.out.println(in.readObject());
                    default:
                        System.out.println("Invalid choice, try again: ");
                        break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

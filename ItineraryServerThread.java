import java.net.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;

public class ItineraryServerThread extends Thread {
    private Socket socket = null;
    public static String clientName = "Client";
    public static ConcurrentHashMap<String, SerializableItinerary> itineraries = new ConcurrentHashMap<>();

    public ItineraryServerThread(Socket socket) {
        super("ItineraryServerThread");
        this.socket = socket;
    }

    public void run() {

        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {
            String readID, messageToClient;
            SerializableItinerary itin;
            while (true) {
                String option;

                // Send the menu to client
                sendMenu(out);

                // Read in selection and process the option using protocol
                option = (String) in.readObject(); // maybe ill have to change to obj

                switch (Integer.valueOf(option)) {
                    case 1: // Create a new empty itinerary and store in server
                        SerializableItinerary newItinerary = new SerializableItinerary();
                        String id = UUID.randomUUID().toString();
                        itineraries.put(id, newItinerary);
                        System.out.println("Itinerary" + id + " stored.");
                        out.writeObject("New Itinerary " + id + " created.");
                        out.flush();
                        break;
                    case 2: // Show directory of itineraries
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Directory of Itineraries: \n");
                        for (String key : itineraries.keySet()) {
                            stringBuilder.append(key).append("\n");
                        }
                        System.out.println("Keys in itineraries map: " + itineraries.keySet());
                        out.writeObject(stringBuilder.toString()); // COME BACK TO THISSSSSS
                        out.flush();
                        break;
                    case 3: // Display existing itinerary to client
                        out.writeObject("Enter the id of the itinerary: ");
                        out.flush();
                        readID = (String) in.readObject();
                        if (itineraries.containsKey(readID)) {
                            System.out.println("Getting itinerary...");
                            out.writeObject(itineraries.get(readID));
                            out.flush();
                        } else {
                            out.writeObject("Invalid itinerary ID.");
                            out.flush();
                        }
                        break;
                    case 4: // Add event to existing itinerary
                        out.writeObject("Enter the id of the itinerary: ");
                        out.flush();
                        readID = (String) in.readObject();
                        itin = itineraries.get(readID);

                        out.writeObject(
                                "For the event you want to add:\nEnter event name, location, and time with commas in between them (no spaces after the commas)\nie. studying,library,10");
                        out.flush();
                        String eventCreation = (String) in.readObject();
                        String[] parts = eventCreation.split(",");
                        String eventName = parts[0];
                        String location = parts[1];
                        int datetime = Integer.valueOf(parts[2]);
                        messageToClient = itin.addEvent(new Event(eventName, location, datetime));
                        System.out.println(itin.toString());
                        itineraries.put(readID, itin);
                        out.writeObject(messageToClient);
                        out.flush();
                        break;
                    case 5:
                        System.out.println("Still working on it...");
                        break;
                    case 6: // Delete existing itinerary from server storage
                        out.writeObject("Enter the id of the itinerary: ");
                        out.flush();
                        readID = (String) in.readObject();
                        if (itineraries.containsKey(readID)) {
                            itineraries.remove(readID);
                            out.writeObject("Itinerary deleted.");
                            out.flush();
                        } else {
                            out.writeObject("Itinerary could not be found.");
                            out.flush();
                        }
                        break;
                    case 7: // Clear all itinereries
                        itineraries.clear();
                        out.writeObject("No more itineraries left.");
                        break;
                    default:
                        System.out.println("INVALIDDDDDDD");
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendMenu(ObjectOutputStream out) throws IOException {
        out.writeObject(
                "---------------------------\nMenu:\n1. Create New Itinerary\n2. Directory of Itinerary IDS\n3. Display Itinerary\n4. Add event\n5. Delete event\n6. Delete itinerary\n7. Clear all itineraries\n---------------------------\n\nSelect an option: ");
        out.flush();
    }

}

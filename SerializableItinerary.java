import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

// Implements a serializable itinerary holding a list of events
public class SerializableItinerary implements Serializable {
    private ArrayList<Event> events;
    private String clientName;

    public SerializableItinerary() {
        this.clientName = "";
        this.events = new ArrayList<>();
    }

    public SerializableItinerary(ArrayList<Event> events) {
        this.events = events;
    }

    public SerializableItinerary(String clientName, ArrayList<Event> events) {
        this.clientName = clientName;
        this.events = events;
    }

    // Getter and setter for events
    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public synchronized String displayItinerary() {
        StringBuilder str = new StringBuilder();
        str.append("Client" + clientName);
        for (Event event : events) {
            str.append(event.toString());
        }
        return str.toString(); //
    }

    public synchronized String addEvent(Event eventToAdd) {
        String messageToClient;
        if (events.add(eventToAdd)) {
            messageToClient = "New event added.";
        } else {
            messageToClient = "Not able to add event.";
        }
        return messageToClient;
    }

    public synchronized void removeEvent(Event eventToRemove) {
        events.remove(eventToRemove);
        System.out.println("Event removed!");

    }

    public synchronized void modifyEvent() {

    }

    public synchronized void clearItinerary() {
        events.clear();
        System.out.println("Itinerary deleted!");
    }

    // Sort events by datetime using Comparator
    public void sortEvents() {
        Collections.sort(events, new EventComparator());
    }

    // Displays itinerary
    public String toString() {
        sortEvents();
        StringBuilder stringBuilder = new StringBuilder();
        if (clientName != null) {
            stringBuilder.append(clientName).append("Itinerary: \n");
        }
        for (Event event : events) {
            stringBuilder.append(event.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}

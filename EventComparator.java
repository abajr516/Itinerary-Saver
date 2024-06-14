import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    // Compares event by datetime attribute
    @Override
    public int compare(Event event1, Event event2) {
        return Integer.compare(event1.getDatetime(), event2.getDatetime());
    }
}

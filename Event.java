import java.io.Serializable;
import java.util.List;

public class Event implements Serializable{
    public List<Event> eventList;
    public String eventName;
    public String location;
    public int datetime;

    public Event(String eventName, String location, int datetime) {
        this.eventName = eventName;
        this.location = location;
        this.datetime = datetime;
    }

    public String getEventName() {
        return this.eventName;
    }
    public void setEventName(String newEventName) {
        this.eventName = newEventName;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String newLocation) {
        this.location = newLocation;
    }
    public int getDatetime() {
        return this.datetime;
    }
    public void setDatetime(int newDatetime) {
        this.datetime = newDatetime;
    }
    public String toString() {
        return "EventName: "+eventName+"  Location: "+location+"  Date: "+datetime;
    }

}

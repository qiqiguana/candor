import java.util.Vector;
import java.util.List;

public class EventSet {
    Vector<Event> events = new Vector<Event>();
    private String author;

    public String getAuthor() {
	return author;
    }

    /**Sets the author of the current event set.
     * There should be a better way to pass authors through the 
     * processing stages...
     **/
    public void setAuthor(String author) {
	this.author = author;
    } 

    /**Creates a new, empty list of events**/
    public EventSet() {
	this.events = new Vector<Event>();
    }

    /**Creates a new list of events given a previously created list of events**/
    public EventSet(List<Event> evts) {
	this.events = new Vector<Event>(evts);
    }

    /**Returns a subset of events given a starting index of an event and the 
     * number of events wanted in the returned list.  
     * TODO: Array bounds checking, if length is longer than the size of the list
     **/
    public EventSet subset(int start, int length) {
	return new EventSet(events.subList(start,length));
    }

    /**Returns the event at a given index**/
    public Event eventAt(int index) {
	return events.get(index);
    }

    /**Returns the total number of events in the set**/
    public int size() {
	return events.size();
    }


    /**Returns the string representation of this event set, which is just a 
     * comma separated list of each individual event
     **/
    public String toString() {
	String t = new String();
	for (int i = 0; i < events.size(); i++) 
	    t += events.elementAt(i) + ", ";
	return t;
    }
}

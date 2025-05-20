import java.util.Vector;
import java.util.List;

public class EventSet {

    Vector<Event> events = new Vector<Event>();

    private String author;

    public String getAuthor();

    /**
     * Sets the author of the current event set.
     *  There should be a better way to pass authors through the
     *  processing stages...
     */
    public void setAuthor(String author);

    /**
     * Creates a new, empty list of events*
     */
    public EventSet() {
    }

    /**
     * Creates a new list of events given a previously created list of events*
     */
    public EventSet(List<Event> evts) {
    }

    /**
     * Returns a subset of events given a starting index of an event and the
     *  number of events wanted in the returned list.
     *  TODO: Array bounds checking, if length is longer than the size of the list
     */
    public EventSet subset(int start, int length);

    /**
     * Returns the event at a given index*
     */
    public Event eventAt(int index);

    /**
     * Returns the total number of events in the set*
     */
    public int size();

    /**
     * Returns the string representation of this event set, which is just a
     *  comma separated list of each individual event
     */
    public String toString();
}

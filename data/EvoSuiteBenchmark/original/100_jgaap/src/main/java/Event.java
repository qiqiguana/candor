/* Event.java
 * Caleb Astey - 2007 */

/** You have an intuitive sense for an event.*/
public class Event implements Comparable {
    
    private String data;


    /** Returns the String representation of this event **/
    public String getEvent() {
	return data;
    }

    public Event() {
	data = "";
    }
    /**Create a new event given a string representation of this event**/
    public Event(String data) {
	this.data = new String(data);
    }

    /**Create a new event given a character representation of the event**/
    public Event(Character data) {
	char [] c = new char[1];
	c[0] = data.charValue();
	this.data = new String(c);
    }

    /**Overridden - from Comparable interface.  Allows for comparison 
     * of two events.  
     **/
    public int compareTo(Object o) {
	return this.data.compareTo(((Event)o).data);
    }

    public String toString() {
	return data;
    }


    /**Allows for equality comparison of two events.  Two events are 
     * the same if their string representations are the same
     **/
    public boolean equals(Object o) {
	return data.equals(((Event)o).data);
    }

    /**When overriding equals(), the hashCode() function must also be 
     * overridden.  Since two events are equal if their string 
     * representations are equal, then it is sufficient to say that two 
     * events are equal if the hash of their string representations 
     * are equal.  This comment is longer than the code itself
     **/
    public int hashCode() {
	return data.hashCode();
    }
}

import java.util.Vector;

/**This event set is all individual characters and spaces, as determined
 * by the preprocessing applied in the previous stage.
 **/
public class CharacterEventSet extends EventDriver{

    public EventSet createEventSet(DocumentSet ds) {
	EventSet es = new EventSet();
	for (int i = 0; i < ds.documentCount(); i++) {
	    Document current = ds.getDocument(i);
	    Vector<Character> cd = current.getProcessedText();
	    for (int j = 0; j < cd.size(); j++)
		es.events.add(new Event(cd.elementAt(j)));
	}
	return es;
    }
}

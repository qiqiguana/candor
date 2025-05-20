import java.util.Vector;


public class CrossEntropyDriver extends AnalysisDriver {

    public String analyze(EventSet unknown, Vector<EventSet> known) {
	double min_distance = Double.MAX_VALUE;
	String auth = null;
	Xent xent;
	for (int i = 0; i < known.size(); i++) {
	    xent = new Xent();
	    double current = xent.process(unknown, known.elementAt(i), 200);
	    if (current < min_distance) {
		current = min_distance;
		auth = known.elementAt(i).getAuthor();
	    }
	}
	return auth;
	
    }

}

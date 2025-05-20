package src;

import java.util.Collection;
import java.util.Iterator;

public class PeasantUprising extends Actions {

	public void perform(Collection args){
		Iterator argsIter = args.iterator();
		Fiefdoms fiefdom = Map.map.get((String)argsIter.next());
		fiefdom.setKingdom(false);
		fiefdom.setPeasants(fiefdom.getValue() + fiefdom.getPeasants());
	}
}

package src;

import java.util.Collection;
import java.util.Iterator;

public class VassalRebellion extends Actions{
	private Fiefdoms fiefdomObj;
	
	public void perform(Collection col){
		Iterator colIter = col.iterator();
		String fiefdomName = (String)colIter.next();
		this.fiefdomObj = (Fiefdoms) Map.map.get(fiefdomName);
		int personalCastles = fiefdomObj.getPersonalCastles();
		int fiefdomCastles = fiefdomObj.getFiefdomCastles();
		
		if(Map.map.get(fiefdomName).getLoyalVassals().size() > 0){
//Number of rebellious vassals is equal to the value of the fiefdom
			if(fiefdomObj.getLoyalVassals().size() - fiefdomObj.getValue() >= 0){
				fiefdomObj.setRebelliousVassals(fiefdomObj.getValue(), true);
				fiefdomObj.setKingdom(false);
				
			}
//All loyal that are in the fiefdom become rebellious if there is less vassals than the fiefdom value
			else{
				fiefdomObj.setRebelliousVassals(fiefdomObj.getLoyalVassals().size(), true);
			}
// Personal Castles keep 10 loyal vassals. Fiefdom castles keep 25, all others become rebellious
			if(personalCastles > 0 || fiefdomCastles > 0){
				if(fiefdomObj.getLoyalVassals().size() - ((personalCastles * 10) + (fiefdomCastles * 25)) > 0){
					fiefdomObj.setRebelliousVassals(fiefdomObj.getLoyalVassals().size() - ((personalCastles * 10) + (fiefdomCastles * 25)), true);
				}
			}
		}
		else{
			new RollEvent(fiefdomObj.getName());		
		}
	}
}

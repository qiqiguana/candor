package src.feudalismTests;

import java.util.ArrayList;
import java.util.Iterator;

import src.Knight;
import src.Map;

import junit.framework.TestCase;

public class KnightTest extends TestCase {
	
	Knight knightA = new Knight("knight","Mathew","myPassword123" );
	
	public void testGetRank() {

	}

	public void testSetRank() {

	}

	public void testGetMoney() {

	}

	public void testSetMoney() {

	}

	public void testGetName() {

	}

	public void testSetName() {

	}

	public void testSetFiefdoms() {
		knightA.setFiefdoms("Anjou", true);
		knightA.setFiefdoms("Ile-De-France", true);
		
		 
		
		Iterator knightAIter = knightA.getFiefdoms().iterator();
		while(knightAIter.hasNext()){
			String fiefStr = (String)knightAIter.next();
			System.out.println(fiefStr + " - " + Map.map.get(fiefStr).getOwner());
		}

		knightA.setFiefdoms("Anjou", false);
		ArrayList list = (ArrayList) knightA.getFiefdoms().clone();
		Iterator knightAIter2 = list.iterator();
		while(knightAIter2.hasNext()){
			String fiefStr = (String)knightAIter2.next();
			System.out.println("");
			System.out.println(fiefStr + " - " + Map.map.get(fiefStr).getOwner());
		}
	}

	public void testGetFiefdoms() {

	}
	
	public void testGetHitsCounter() {
		
	}

	public void testSetHitsCounter() {

	}

	public void testGetMercenaries() {

	}

	public void testSetMercenaries() {

	}

	public void testGetRebellionCounter() {

	}

	public void testSetRebellionCounter() {

	}

	public void testGetScore() {

	}

	public void testSetScore() {

	}

	public void testGetLocation() {

	}

	public void testSetLocation() {

	}

	public void testGetKill() {

	}

	public void testGetwound() {

	}

	public void testSetKill() {

	}

	public void testSetWound() {

	}

}

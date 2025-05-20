package src;

import java.util.ArrayList;

import junit.framework.TestCase;

public class VassalRebellionTest extends TestCase {

	public void testVassalRebellion() {
		
		String fiefdomName = "Brittany";
		Fiefdoms fiefdom = Map.map.get(fiefdomName);
		Map.map.get(fiefdomName).setLoyalVassals(26, true);
		Map.map.get(fiefdomName).setFiefdomCastles(5);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(fiefdomName);
		new GameController(new CommandBean("VassalRebellion", parameters));
		assertEquals(7,Map.map.get(fiefdomName).getRebelliousVassals().size());
		assertEquals(26,Map.map.get(fiefdomName).getLoyalVassals().size());
	}
}
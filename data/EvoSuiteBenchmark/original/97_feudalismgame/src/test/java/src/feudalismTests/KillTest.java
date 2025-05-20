package src.feudalismTests;

import src.CurrentPlayers;
import src.Knight;
import src.Map;
import junit.framework.TestCase;

public class KillTest extends TestCase {

	public void testKill() {
		Knight knight = new Knight("Mathew", "count","1234");
		knight.setFiefdoms("Blois", true);
		knight.setTotalMoney(43);
		
		
		Knight knight2 = new Knight("Johan", "king","1234");
		knight2.setFiefdoms("Ile-De-France", true);
		knight2.setTotalMoney(0);
		
		CurrentPlayers.currentPlayers.put("Mathew", knight);
		CurrentPlayers.currentPlayers.put("Johan", knight2);
		
		System.out.println("Fiefdom's Owner: " + Map.map.get("Blois").getOwner());
		System.out.println("Rank: " + CurrentPlayers.currentPlayers.get(knight.getName()).getRank());
		System.out.println("Number of fiefdoms owned by king before Mathew's death: " + CurrentPlayers.currentPlayers.get(knight2.getName()).getFiefdoms().size());
		System.out.println("King's total money before Mathew's death: " + CurrentPlayers.currentPlayers.get(knight2.getName()).getTotalMoney());
		
		for(int i = 0; i < 3; i++){
			System.out.println("kill");
			CurrentPlayers.currentPlayers.get(knight.getName()).kill();
			System.out.println(CurrentPlayers.currentPlayers.get(knight.getName()).getKill());
		}
		System.out.println("Number of fiefdoms " + CurrentPlayers.currentPlayers.get(knight.getName()).getFiefdoms().size());
		System.out.println("Fiefdom's Owner: " + Map.map.get("Blois").getOwner());
		System.out.println("The king: " + CurrentPlayers.getKing().getName());
		System.out.println("Number of fiefdoms owned by king after Mathew's death: " + CurrentPlayers.currentPlayers.get(knight2.getName()).getFiefdoms().size());
		System.out.println("King's total money after Mathew's death: " + CurrentPlayers.currentPlayers.get(knight2.getName()).getTotalMoney());
	}

}

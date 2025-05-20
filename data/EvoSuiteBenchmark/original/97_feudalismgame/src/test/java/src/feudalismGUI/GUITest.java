package src.feudalismGUI;

import java.util.HashMap;
import java.util.Iterator;

import junit.framework.TestCase;
import src.CurrentPlayers;
import src.Fiefdoms;
import src.Knight;
import src.SAXEncoder;
import src.XMLParser;

public class GUITest extends TestCase 
{
	public void addUserTest()
	{
		
	}
	
	public void testEditUser()
	{
		HashMap<String, Fiefdoms> fiefs = XMLParser.fiefdoms();
		
		HashMap<String, Knight> knightsList = CurrentPlayers.currentPlayers;
		knightsList.clear();
		
		Knight knight = new Knight("Mathew", "knight","neverguess");
		Knight knight2 = new Knight("Ryan", "duke","dontknow");
		
		knight.setFiefdoms("Artois", true);
		knight.setFiefdoms("Flanders",true);
		knight.setLocation("Artois");
		knight.setTotalMoney(15);
		knight.setScore(65);
		
		knight2.setFiefdoms("Burgundy", true);
		knight2.setFiefdoms("Maine", true);
		knight2.setFiefdoms("Toulouse", true);
		knight2.setFiefdoms("Champagne", true);
		knight2.setLocation("Champagne");
		knight2.setTotalMoney(75);
		knight2.setScore(90);
		
		knightsList.put(knight.getName(),knight);
		knightsList.put(knight2.getName(),knight2);
		SAXEncoder.knights(knightsList);
		
		knight = CurrentPlayers.currentPlayers.get("Mathew");
		Iterator fiefdomIt = knight.getFiefdoms().iterator();
		while(fiefdomIt.hasNext()){
			System.out.println(fiefdomIt.next().toString());
		}
		assertTrue(1 == 1);
		assertTrue(knight.getFiefdoms().contains("Artois"));
		assertFalse(knight2.getFiefdoms().contains("America"));
	}
	
	public void editFiefdomTest()
	{
		
	}
}

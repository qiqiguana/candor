package src.feudalismTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import src.CommandBean;
import src.CurrentPlayers;
import src.GameController;
import src.Knight;
import src.Map;
import src.SAXEncoder;
import src.XMLParser;

import junit.framework.TestCase;

public class PurchaseTest extends TestCase {

	public void testVassal() {
		XMLParser.fiefdoms();
		
		Knight knight = new Knight("Mathew", "knight","1234");
		Knight knight2 = new Knight("Johan", "knight","1234");

		HashMap knightsList = CurrentPlayers.currentPlayers;
		knightsList.clear();
		
		ArrayList parameters = new ArrayList<Object>();
		ArrayList parameters2 = new ArrayList<Object>();
		knight.setFiefdoms("Artois", true);
		knight.setFiefdoms("Flanders",true);
		knight.setLocation("Artois");
		
		knightsList.put(knight.getName(),knight);
		knightsList.put(knight2.getName(),knight2);
		SAXEncoder.knights(knightsList);
		
		knight = CurrentPlayers.currentPlayers.get("Mathew");
		knight.setTotalMoney(25);
		knight.setFiefdoms("Artois", true);
		Iterator fiefdomList = knight.getFiefdoms().iterator();
		while(fiefdomList.hasNext()){
			System.out.println(fiefdomList.next());
		}
		System.out.println("Location:             " + knight.getLocation());
		knight.setFiefdoms(knight.getLocation(),true);
		System.out.println("Location:             " + knight.getLocation() + " owner: " + Map.map.get(knight.getLocation()).getOwner());
		ArrayList arguments = new ArrayList<String>();
		arguments.add("vassal");
		arguments.add("2");
		arguments.add(knight.getName());
		arguments.add(knight.getLocation());
		
		new GameController(new CommandBean("Purchase", arguments));

		assertEquals(23,knight.getTotalMoney());
	}

	public void testCastleFiefdom() {
		
		Knight knight = new Knight("Mathew", "knight","1234");
		Knight knight2 = new Knight("Johan", "knight","1234");

		HashMap knightsList = CurrentPlayers.currentPlayers;
		knightsList.clear();
		
		ArrayList parameters = new ArrayList<Object>();
		ArrayList parameters2 = new ArrayList<Object>();
		knight.setFiefdoms("Artois", true);
		knight.setFiefdoms("Flanders",true);
		knight.setLocation("Artois");
		
		knightsList.put(knight.getName(),knight);
		knightsList.put(knight2.getName(),knight2);
		SAXEncoder.knights(knightsList);
		
		//King buys a Fiefdom castle for a knight, count, or duke
		knight2 = CurrentPlayers.currentPlayers.get("Johan");
		knight2.setFiefdoms("Gascony",true);
		knight2.setRank("duke");
		System.out.println("Gascony: " + Map.map.get("Gascony").getOwner() + " Value: " + Map.map.get("Gascony").getValue());
		Iterator fiefdomList = knight2.getFiefdoms().iterator();
		while(fiefdomList.hasNext()){
			System.out.println(fiefdomList.next());
		}
		
		knight = CurrentPlayers.currentPlayers.get("Mathew");
		knight.setTotalMoney(25);
		ArrayList arguments = new ArrayList<String>();
		arguments.add("castleFiefdom");
		arguments.add("2");
		arguments.add(knight.getName());
		arguments.add(knight.getLocation());
		new GameController(new CommandBean("Purchase", arguments));

		assertEquals(25,knight.getTotalMoney());
	}
	
	public void testCastlePersonal() {
		Knight knight = new Knight("Mathew", "knight","1234");
		Knight knight2 = new Knight("Johan", "knight","1234");

		HashMap knightsList = CurrentPlayers.currentPlayers;
		knightsList.clear();
		
		ArrayList parameters = new ArrayList<Object>();
		ArrayList parameters2 = new ArrayList<Object>();
		knight.setFiefdoms("Artois", true);
		knight.setFiefdoms("Flanders",true);
		knight.setLocation("Artois");
		
		knightsList.put(knight.getName(),knight);
		knightsList.put(knight2.getName(),knight2);
		SAXEncoder.knights(knightsList);
		// King buys a P Castle for a duke, count, which is not allowed.
		
		knight2 = CurrentPlayers.currentPlayers.get("Johan");
		knight2.setFiefdoms("Gascony",true);
		knight2.setRank("duke");
		
		knight = CurrentPlayers.currentPlayers.get("Mathew");
		knight.setTotalMoney(25);
		knight.setRank("king");
		ArrayList arguments = new ArrayList<String>();
		arguments.add("castlePersonal");
		arguments.add("2");
		arguments.add(knight.getName());
		arguments.add("Gascony");
		System.out.println("King tries to buy personal castle for duke");
		new GameController(new CommandBean("Purchase", arguments));

		assertEquals(25,knight.getTotalMoney());
	
		
		//Knight buys personal castle for a knight
		arguments.clear();
		knight2.setRank("knight");
		knight.setTotalMoney(25);
		knight.setRank("king");
		arguments.add("castlePersonal");
		arguments.add("2");
		arguments.add(knight.getName());
		arguments.add("Gascony");
		new GameController(new CommandBean("Purchase", arguments));

		assertEquals(5,knight.getTotalMoney());
		
		
		//King buys P castle for himself
		System.out.println("Location: " + knight.getLocation() + " / Owner: " + Map.map.get(knight.getLocation()).getOwner());
		arguments.clear();
		System.out.println("Location: " + knight.getLocation() + " / Owner: " + Map.map.get(knight.getLocation()).getOwner());
		knight.setTotalMoney(25);
		knight.setRank("king");
		arguments.add("castlePersonal");
		arguments.add("1");
		arguments.add(knight.getName());
		arguments.add(knight.getLocation());
		new GameController(new CommandBean("Purchase", arguments));

		assertEquals(15,knight.getTotalMoney());
	}
	
	public void testMercenary(){
		//A knight buy mercenaries for himself

		Knight knight = new Knight("Mathew", "knight","1234");
		Knight knight2 = new Knight("Johan", "knight","1234");

		HashMap knightsList = CurrentPlayers.currentPlayers;
		knightsList.clear();

		ArrayList parameters = new ArrayList<Object>();
		ArrayList parameters2 = new ArrayList<Object>();
		knight.setFiefdoms("Artois", true);
		knight.setFiefdoms("Flanders",true);
		knight.setLocation("Artois");

		knightsList.put(knight.getName(),knight);
		knightsList.put(knight2.getName(),knight2);
		SAXEncoder.knights(knightsList);

		knight.setTotalMoney(15);
		
		ArrayList arguments = new ArrayList<String>();
		arguments.add("mercenary");
		arguments.add("2");
		arguments.add(knight.getName());
		arguments.add(knight.getName());
		new GameController(new CommandBean("Purchase", arguments));

		System.out.println(knight.getTotalMoney());
		assertEquals(13, knight.getTotalMoney());
	}
}

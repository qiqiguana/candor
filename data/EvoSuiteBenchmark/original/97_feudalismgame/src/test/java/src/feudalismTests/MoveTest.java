package src.feudalismTests;

import java.util.ArrayList;
import java.util.HashMap;

import src.CommandBean;
import src.CurrentPlayers;
import src.GameController;
import src.Knight;
import src.Map;
import src.SAXEncoder;

import junit.framework.TestCase;

public class MoveTest extends TestCase {

	public void testPerform() {
		
		Knight knight1 = new Knight("Mathew", "knight","1234");
		Knight knight2 = new Knight("Johan", "knight","1234");

		HashMap knightsList = CurrentPlayers.currentPlayers;
		knightsList.clear();
		
		ArrayList parameters = new ArrayList<Object>();
		ArrayList parameters2 = new ArrayList<Object>();
		knight1.setFiefdoms("Artois", true);
		knight1.setFiefdoms("Flanders",true);
		knight1.setLocation("Artois");
		
		knightsList.put(knight1.getName(),knight1);
		knightsList.put(knight2.getName(),knight2);
		SAXEncoder.knights(knightsList);

		System.out.println("Number of moving vassals " + ((Knight) knightsList.get("Mathew")).getMovingVassals().size());
		parameters.add("Mathew"); //knight's name
		parameters.add(""); //king's order
		System.out.println("Location: " + ((Knight) knightsList.get("Mathew")).getLocation());
		parameters.add(((Knight) knightsList.get("Mathew")).getLocation());
		parameters.add("Flanders"); //Destination
		parameters.add(5); //number of vassals the knight decided to take
		
		//The 2 next lines must be removed after testing the Move command
		Map.map.get(knight1.getLocation()).getLoyalVassals().clear();
		Map.map.get(knight1.getLocation()).setLoyalVassals(Map.map.get(knight1.getLocation()).getValue(),true);

		System.out.println(((Knight) knightsList.get("Mathew")).getFiefdoms().size());

		new GameController(new CommandBean("Move", parameters));
		System.out.println(((Knight) knightsList.get("Mathew")).getLocation());
		// SECOND MOVE!
		
		System.out.println("------------------->SECOND MOVE<---------------------");
		parameters2.add(""); //king's order
		System.out.println("Location: " + ((Knight) knightsList.get("Mathew")).getLocation());
		parameters2.add(((Knight) knightsList.get("Mathew")).getLocation());
		parameters2.add("Artois"); //Destination
		parameters2.add(5); //number of vassals the knight decided to take
		parameters2.add("Mathew"); //knight's name
		
		new GameController(new CommandBean("Move", parameters2));
		System.out.println("Location: " + ((Knight) knightsList.get("Mathew")).getLocation());
		System.out.println("asdasdasd " + ((Knight) knightsList.get("Mathew")).getLocation());
	}
}
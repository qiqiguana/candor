package src.feudalismTests;

import java.util.ArrayList;
import java.util.HashMap;

import src.CommandBean;
import src.CurrentPlayers;
import src.GameController;
import src.Knight;
import src.SAXEncoder;
import src.XMLParser;

import junit.framework.TestCase;

public class BattleTest extends TestCase {

//	public void testVassal() {
//		XMLParser.fiefdoms();
//		ArrayList<String> arguments = new ArrayList<String>();
//		ArrayList<String> vassalRebellion = new ArrayList<String>();
//		
//		Knight knight1 = new Knight("Mathew", "knight","1234");
//		Knight knight2 = new Knight("Johan", "knight","1234");
//
//		HashMap knightsList = CurrentPlayers.currentPlayers;
//		knightsList.clear();
//		knightsList.put(knight1.getName(),knight1);
//		knightsList.put(knight2.getName(),knight2);
//		SAXEncoder.knights(knightsList);
//
//		Knight knightA = CurrentPlayers.currentPlayers.get("Mathew");
//		Knight knightB = CurrentPlayers.currentPlayers.get("Johan");
//		knightA.setFiefdoms("Artois", true);
//		knightA.setFiefdoms("Flanders",true);
//		knightA.setLocation("Artois");
//		
//		vassalRebellion.add("Artois");
//		new GameController(new CommandBean("VassalRebellion", vassalRebellion));
//
//		knightB.setFiefdoms("Vermandois",true);
//		knightB.setLocation("Vermandois");
//		knightB.addMercenaries(15);
//		knightB.allyTo("Mathew");
//		
//		arguments.add("vassal");
//		arguments.add("Mathew");
//
//		new GameController(new CommandBean("Battle", arguments));
//	}
	
	public void testPeasant(){
//		for(int i = 0 ; i < 200 ; i++){
			ArrayList<String> arguments = new ArrayList<String>();
			ArrayList<String> peasantUprising = new ArrayList<String>();
			XMLParser.fiefdoms();
			Knight knight1 = new Knight("Mathew", "knight","1234");
			Knight knight2 = new Knight("Johan", "knight","1234");
	
			HashMap knightsList = CurrentPlayers.currentPlayers;
			knightsList.clear();
			knightsList.put(knight1.getName(),knight1);
			knightsList.put(knight2.getName(),knight2);
			SAXEncoder.knights(knightsList);
	
			Knight knightA = CurrentPlayers.currentPlayers.get("Mathew");
			Knight knightB = CurrentPlayers.currentPlayers.get("Johan");
			knightA.setFiefdoms("Artois", true);
			knightA.setFiefdoms("Flanders",true);
			knightA.setLocation("Artois");
			knightB.setFiefdoms("Vermandois", true);
			knightB.setLocation("Vermandois");
			
			peasantUprising.add("Artois");
			new GameController(new CommandBean("VassalRebellion", peasantUprising));
			knightB.allyTo("Mathew");
			knightB.setFiefdoms("Vermandois",true);
			
//			knightB.addMercenaries(15);
	//		knightB.allyTo("Mathew");
			
			arguments.add("vassal");
			arguments.add("Mathew");
			System.out.println("Johan's position " + knightB.getLocation());
			System.out.println("Mathew's position " + knightA.getLocation());
			new GameController(new CommandBean("Battle", arguments));
//	}
	}
}

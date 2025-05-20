package src.feudalismTests;

import java.util.HashMap;
import java.util.Iterator;

import src.Fiefdoms;
import src.Map;
import src.XMLParser;

import junit.framework.TestCase;

public class XercesCheckerTest extends TestCase {

	public void testDOMTest() {

		HashMap map = XMLParser.fiefdoms();
		
		System.out.println("Number of Fiefdoms: " + Map.map.size());

		try{
			Fiefdoms fiefdom = (Fiefdoms) map.get("Brittany");
			System.out.println("Number of neighbors: " + ((Fiefdoms) Map.map.get("Brittany")).getNeighbors().size());
			System.out.println("Name: " + fiefdom.getName());
			System.out.println("Number of vassals: " + ((((Fiefdoms) Map.map.get("Brittany")).getLoyalVassals().size()) + ((Fiefdoms) map.get("Brittany")).getRebelliousVassals().size()));
			System.out.println("Value: " + ((Fiefdoms) Map.map.get("Brittany")).getValue());
			System.out.println("Owner: " + fiefdom.getOwner());
			System.out.println("Loyal Vassals: " + ((Fiefdoms) Map.map.get("Brittany")).getLoyalVassals().size());
			Iterator neighborIter = (Iterator) ((Fiefdoms) Map.map.get("Brittany")).getNeighbors().iterator();
			String neighbor = null;
				
			while(neighborIter.hasNext()){
				neighbor = (String) neighborIter.next();
				System.out.println("Neighbor: " + neighbor);
			}
		}
		catch(Exception e){
			System.out.println("Fiefdom not found");
		}

//		
//		HashMap knights = XMLParser.knights();
//
//		System.out.println("Number of Knights: " + knights.size());
//
//
//		try{
//			Knight knight = (Knight) knights.get("Mathew");
//			System.out.println("Number of neighbors: " + knight.getFiefdoms().size());
//			System.out.println("Name: " + knight.getName());
//			System.out.println("Number of vassals: " + knight.getFiefdomCastles());
//			System.out.println("Value: " + knight.getMoney());
//			System.out.println("Rank: " + knight.getRank());
//			Iterator neighborIter = knight.getFiefdoms().iterator();
//			Fiefdoms fiefdom = null;
//				while(neighborIter.hasNext()){
//					fiefdom = (Fiefdoms) neighborIter.next();
//					System.out.println("Fiefdom: " + fiefdom.getName());
//				}
//		}
//
//		catch(Exception e){
//			System.out.println("Knight not found");
//		}
		

//		ArrayList<XMLable> knights = new ArrayList<XMLable>();
//
//		Knight knightA = new Knight();
//		Knight knightB = new Knight();
//		Knight knightC = new Knight();
//
//		Fiefdoms fiefdomA = new Fiefdoms();
//		Fiefdoms fiefdomB = new Fiefdoms();
//		Fiefdoms fiefdomC = new Fiefdoms();
//
//		fiefdomA.setName("Maine");
//		fiefdomB.setName("Neustria");
//		fiefdomC.setName("Anjou");
//
//
//		ArrayList<Fiefdoms> fiefdomsA = new ArrayList<Fiefdoms>();
//		ArrayList<Fiefdoms> fiefdomsB = new ArrayList<Fiefdoms>();
//		ArrayList<Fiefdoms> fiefdomsC = new ArrayList<Fiefdoms>();		
//
//		fiefdomsA.add(fiefdomA);
//		fiefdomsB.add(fiefdomB);
//		fiefdomsC.add(fiefdomC);
//
//		knightA.setName("Mathew");
//		knightA.setMoney(7);
//		knightA.setDiceNumber(5);
//		knightA.setFiefdomCastles(4);
//		knightA.setPersonalCastles(2);
//		knightA.setRank("duke");
//		knightA.setFiefdoms(fiefdomsA);
//
//		knightB.setName("Shane");
//		knightB.setMoney(3);
//		knightB.setDiceNumber(8);
//		knightB.setFiefdomCastles(3);
//		knightB.setPersonalCastles(1);
//		knightB.setRank("knight");
//		knightB.setFiefdoms(fiefdomsB);
//
//		knightC.setName("Leo");
//		knightC.setMoney(8);
//		knightC.setDiceNumber(3);
//		knightC.setFiefdomCastles(2);
//		knightC.setPersonalCastles(1);
//		knightC.setRank("count");
//		knightC.setFiefdoms(fiefdomsC);
//
//		knights.add(knightA);
//		knights.add(knightB);
//		knights.add(knightC);
//
//		new XMLHandler(knights);

	}
}
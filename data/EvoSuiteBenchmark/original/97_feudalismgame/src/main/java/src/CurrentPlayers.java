package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class CurrentPlayers {
	public static HashMap<String, Knight> currentPlayers = SAXParser.knights();
	
	public synchronized static HashMap<String, Knight> getAll(){
		return currentPlayers;
	}
	
	public static Knight getKing(){
		Set playersName = currentPlayers.keySet();
		Iterator playersNameIter = playersName.iterator();
		while(playersNameIter.hasNext()){
			Knight knight = currentPlayers.get(playersNameIter.next());
			if(knight.getRank().equalsIgnoreCase("king")){
				return knight;
			}
		}
		return null;
	}
	
	public static ArrayList<String> whosThere(String fief){
		ArrayList<String> fiefPlayers = new ArrayList<String>();
		Set playersName = currentPlayers.keySet();
		Iterator playersNameIter = playersName.iterator();
		while(playersNameIter.hasNext()){
			Knight knight = currentPlayers.get(playersNameIter.next());
			if(knight.getLocation().equalsIgnoreCase(fief)){
				fiefPlayers.add(knight.getName());
			}
		}
		return fiefPlayers;
	}
	
//	public static ArrayList<ArrayList<String>> sides(String fief){
//		ArrayList<String> defenders = new ArrayList<String>();
//		ArrayList<String> attackers = new ArrayList<String>();
//		Iterator fiefPlayers = whosThere(fief).iterator();
//		ArrayList<ArrayList<String>> sides = new ArrayList<ArrayList<String>>();
//		
//		while(fiefPlayers.hasNext()){
//			Knight knight = currentPlayers.get(fiefPlayers.next());
//			if(knight.getSide().equalsIgnoreCase("attack")){
//				attackers.add(knight.getName());
//			}
//			else if(knight.getSide().equalsIgnoreCase("defend")){
//				defenders.add(knight.getName());
//			}
//			else{
//				System.out.println("Invalid Side");
//			}
//			sides.add(attackers);
//			sides.add(defenders);
//		}
//		return sides;
//	}
}

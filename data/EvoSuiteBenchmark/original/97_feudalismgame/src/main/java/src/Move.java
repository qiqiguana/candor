package src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Move extends Actions{
	private HashMap<String,Fiefdoms> allFiefdoms = Map.map;
	private HashMap<String,Knight> currentPlayers = CurrentPlayers.currentPlayers;

	public void perform(Collection col) {
/* Collection of arguments must follow the following order:
 * 1 - Player's name
 * 2 - Destination
 * 3 - Number of vassals requested by the knight
*/		
		Iterator colIter = col.iterator();
		String knightName = colIter.next().toString();
//		System.out.println("knightName " + knightName);
		String kingOrder = CurrentPlayers.currentPlayers.get(knightName).getOrder();
//		System.out.println("kingOrder " + kingOrder);
		String origin = CurrentPlayers.currentPlayers.get(knightName).getLocation();
//		System.out.println("origin " + origin);
		String destination = colIter.next().toString();
//		System.out.println("destination " + destination);
		int vassalsNo = Integer.parseInt(colIter.next().toString()); // Number of vassals the knight decided to take to war
//		System.out.println("vassalsNo " + vassalsNo);
		

		if(origin.equalsIgnoreCase(destination)){
			System.out.println("You cannot move to the actual location, " +
					"please select a different fiefdom.");
			return;
		}
		
		else if(Map.isNeighbor(origin, destination) == 0){
			move(origin, destination, vassalsNo, knightName);
		}
		else if (Map.isNeighbor(origin, destination) > 0){
			return;
		}
		else if(kingOrder.equals("king")){
			move(origin, destination, vassalsNo, knightName);
		}
		else if(!kingOrder.equals("king")){
			System.out.println("You cannot move to this destination.\n" +
					"You can only move to a neighboring fiefdom, or\n" +
					"receive orders from the king to move to a non-neighboring fiefdom");
			return;
		}
	}

	private void move(String origin, String destination, int vassalsNo, String knightName){
		Knight movingKnight = CurrentPlayers.currentPlayers.get(knightName);
//		System.out.println(movingKnight.getName() + " will be moving from " + CurrentPlayers.currentPlayers.get(knightName).getLocation() + " to " + destination);
//		System.out.println(movingKnight.getMovingVassals().size() == 0 && CurrentPlayers.currentPlayers.get(knightName).isFiefdomMine(CurrentPlayers.currentPlayers.get(knightName).getLocation()));
		if(movingKnight.getMovingVassals().size() == 0 && movingKnight.isFiefdomMine(movingKnight.getLocation())){
			System.out.println("The requested number of moving vassals is " + vassalsNo);
			movingKnight.setMovingVassals(vassalsNo);
			//Changing vassals' location (moving them to the destination fiefdom)
		}
		movingKnight.setLocation(destination);
//		System.out.println(movingKnight.getLocation());
//		System.out.println("Moved with " + movingKnight.getMovingVassals().size() + " vassals");
		GameAutoActions.saveAll(); //Uncomment when game is done
	}
}
package src;

import java.util.HashMap;

public class Map {
	public static HashMap<String, Fiefdoms> map = SAXParser.fiefdoms();

	public synchronized static HashMap<String, Fiefdoms> getAll(){
		return map;
	}
	
	public static int isNeighbor(String origin, String destination){
		Fiefdoms fiefdomOrigin = (Fiefdoms) map.get(origin);
		
		if (fiefdomOrigin.getNeighbors().contains(destination)){
			System.out.println("Fiefdoms are neighbors");
			return 0;
		}
		
		else if (!map.containsKey(origin)){
			System.out.println("Invalid origin fiefdom");
			return 1;
		}
		
		else if(!map.containsKey(destination)){
			System.out.println("Invalid destination fiefdom");
			return 2;
		}
		
		System.out.println("Fiefdoms are not neighbors");
		return 3;
	}
}
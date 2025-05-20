package src;

import java.util.HashMap;

public class GameAutoActions {
	
	public static void saveKnight(){
		HashMap<String, Knight> population = CurrentPlayers.currentPlayers;
		System.out.println("Saving... Knight");
		SAXEncoder.knights(population);
	}
	
	public static void saveFiefdoms(){
		HashMap<String, Fiefdoms> map = Map.map;
		SAXEncoder.fiefdoms(map);
	}
	
	public static void saveAll(){
		saveKnight();
		saveFiefdoms();
	}
}

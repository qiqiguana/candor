package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class RollEvent{
	Actions actionObj = null;
	
	//delete the main method when done testing
	public static void main(String[] args){
		new RollEvent(null);
	}

	public RollEvent(String fiefdomName){
		String event = null;
		ArrayList<String> args = new ArrayList<String>();
		Random rand = new Random();
		ArrayList<String> events = new ArrayList<String>();
		events.add("Peace");
		events.add("PeasantUprising");
		events.add("VassalRebellion");
		events.add("Invasion");
		
		if(fiefdomName == null){
			HashMap<String, Fiefdoms> allFiefdoms = Map.map;
			Set fiefdomsList = allFiefdoms.keySet();
			Iterator fiefListIter = fiefdomsList.iterator();
//Only those that belong to the kingdom will be added to the fiefdoms that will be rolled an event list
			while(fiefListIter.hasNext()){
				event = events.get(rand.nextInt(events.size()));
				fiefdomName = (String)fiefListIter.next();
				if(allFiefdoms.get(fiefdomName).isKingdom() || 
						allFiefdoms.get(fiefdomName).getOwner() != "" ||
						allFiefdoms.get(fiefdomName).getOwner() != null){
					args.clear();
					args.add(fiefdomName);
					callEvent(event, args);
				}
			}
		}
		else{
			event = events.get(rand.nextInt(events.size()));
			args.add(fiefdomName);
			callEvent(event, args);
		}
	}

	public void callEvent(String event, Collection args ){
		Iterator argsIter = args.iterator();
		System.out.print(argsIter.next() + " will get " + event);
		if(!event.equals("Peace")){
			try {
				Class anActionClass = Class.forName("feudalism." + event);
				try {
					actionObj = (Actions) anActionClass.newInstance();
					actionObj.perform(args);
					
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		System.out.println();
	}
}

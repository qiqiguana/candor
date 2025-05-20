package src;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class Invasion extends Actions {
	private static int vikings = 2;
	private static int magyars = 2;
	private static int muslims = 2;
	
	public void perform(Collection args){
		Iterator argsIter = args.iterator();
		Fiefdoms fiefdom = Map.map.get(argsIter.next());
		
		Random rand = new Random();
		String invaderType = fiefdom.getInvaderType().get(rand.nextInt(fiefdom.getInvaderType().size()));
		if (invaderType.equalsIgnoreCase("viking")){
			vikings++;
			fiefdom.setViking(fiefdom.getViking().size() + vikings, true);
			System.out.print(" by " + vikings + " " + invaderType + "s");
		}
		else if (invaderType.equalsIgnoreCase("magyar")){
			magyars++;
			fiefdom.setMagyar(fiefdom.getMagyar().size() + magyars, true);
			System.out.print(" by " + magyars + " " + invaderType + "s");
		}
		else if (invaderType.equalsIgnoreCase("muslim")){
			muslims++;
			fiefdom.setMuslim(fiefdom.getMuslim().size() + muslims, true);
			System.out.print(" by " + muslims + " " + invaderType + "s");
		}
	}

}
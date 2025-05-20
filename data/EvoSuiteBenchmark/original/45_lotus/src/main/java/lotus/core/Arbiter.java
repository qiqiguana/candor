package lotus.core;
import lotus.core.card.Card;
import lotus.core.decision.*;
import lotus.core.stack.Spell;

import java.util.*;

public class Arbiter
{
	public static boolean isLegal(Decision dec, Player p)
	{
		//TODO : switch on the type
		return true;
	}
	
	public static ArrayList<Decision> getLegalPlayerDecisions (Player p)
	{
		//build the object
		ArrayList<Decision> decisions = new ArrayList<Decision>();
		//add the default action
		decisions.add(new PassDecision());
		//TODO activated abilities
		//TODO combat
		
		//add the cards in hand
		for(Card c : p.hand)
		{
			//TODO : switch according to type, and create decisions accordingly
			Spell s = new Spell(c);
			decisions.add(s);
		}
		
		
		
		
		
		//parse the decisions according to isLegal
		for(Decision d : decisions)
		{
			if(!isLegal(d,p))
			{
				decisions.remove(d);
			}
		}
		return decisions;
	}
}

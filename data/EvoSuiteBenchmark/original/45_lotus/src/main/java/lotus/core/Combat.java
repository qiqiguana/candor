package lotus.core;

import java.util.*;
import lotus.core.card.Creature;
import lotus.core.cost.*;

public class Combat
{
	public Player attackingPlayer;
	public Player defendingPlayer;
	
	public ArrayList<Creature> attackingCreatures = new ArrayList<Creature>();
	public HashMap<Creature,ArrayList<Creature>> blockingCreatures = new HashMap<Creature, ArrayList<Creature>>();
	
	public Combat(Player AP, Player DP)
	{
		// init players
		attackingPlayer = AP;
		defendingPlayer = DP;
	}
}

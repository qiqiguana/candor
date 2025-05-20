package lotus.core.cost;

import lotus.core.Player;

/*
 * Abstract class for all costs. A cost is defined by the player it applies to, and two
 * functions : one defining whether the cost is payable and one effectively making the player pay 
 */
public abstract class Cost
{
	public Player player;
	public abstract boolean isPayable();
	public abstract void payCost();
}

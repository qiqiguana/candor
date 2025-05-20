package lotus.core.cost;

import lotus.core.Mana;

/*
 * A mana cost is a type of cost that is payed by paying mana
 */
public class ManaCost extends Cost
{
	public Mana manaCost;
	public ManaCost (Mana manaCost)
	{
		
	}
	@Override
	public boolean isPayable()
	{
		//TODO search BASIC lands
		return player.manaPool.canPay(manaCost);
	}

	@Override
	public void payCost()
	{
		
		// TODO Auto-generated method stub
	}
}

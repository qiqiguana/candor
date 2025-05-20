package lotus.core.cost;

import java.util.ArrayList;

/*
 * A cost intended to be used internally to represent multiple costs as one cost
 */
public class MultiCost extends Cost
{
	ArrayList<Cost> list = new ArrayList<Cost>();
	//add an effect to be resolved
	public void addCost(Cost c)
	{
		list.add(c);
	}

	@Override
	public boolean isPayable()
	{
		for(Cost c : list)
		{
			if(!c.isPayable()) 
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public void payCost()
	{
		for(Cost c : list)
		{
			c.payCost();
		}
	}

}

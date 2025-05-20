package lotus.core.decision;

import java.util.ArrayList;

/*
 * A decision intended to be used internally to represent multiple decisions as one decision
 */
public class MultiDecision implements Decision
{
	public ArrayList<Decision> list = new ArrayList<Decision>();
	//add a decision to apply
	public void addDecision(Decision d)
	{
		list.add(d);
	}

	public void applyDecision()
	{
		for(Decision d : list)
		{
			d.applyDecision();
		}
	}
}

package lotus.core.effect;

import java.util.ArrayList;

public class MultiEffect extends Effect
{
	ArrayList<Effect> list = new ArrayList<Effect>();
	//add an effect to be resolved
	public void addEffect(Effect E)
	{
		list.add(E);
	}
	@Override
	public void resolve()
	{
		for(Effect e : list)
		{
			e.resolve();
		}
	}

}

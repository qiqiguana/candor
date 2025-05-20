package lotus.core.stack;

import lotus.core.effect.Effect;
import lotus.core.*;

public abstract class StackObject
{
	public void addToStack()
	{
		Game.stack.add(this);
		//TODO : other types
		if(this instanceof Spell) ((Spell)this).card.isInStack = true;
	}
	//real execution of the action, via effect
	public abstract Effect createEffect();
}

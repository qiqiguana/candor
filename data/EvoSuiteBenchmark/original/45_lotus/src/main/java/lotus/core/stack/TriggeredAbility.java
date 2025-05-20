package lotus.core.stack;

import lotus.core.effect.Effect;

public abstract class TriggeredAbility extends StackObject
{
	public abstract boolean matches(Effect e);
}

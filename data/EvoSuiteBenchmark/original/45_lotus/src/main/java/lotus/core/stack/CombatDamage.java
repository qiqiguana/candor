package lotus.core.stack;

import lotus.core.card.Card;
import lotus.core.card.Creature;
import lotus.core.effect.Effect;

public class CombatDamage extends StackObject
{
	public int damage;
	public Creature source;
	public Creature destination;
	public boolean toPlayer;
	
	@Override
	public Effect createEffect()
	{
		// TODO damage
		return null;
	}
}

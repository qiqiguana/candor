package lotus.core.effect;

import lotus.core.Game;
import lotus.core.card.Creature;

public class BecomeAttacker extends Effect
{
	public Creature attacker;
	
	public BecomeAttacker(Creature c)
	{
		attacker = c;
	}
	@Override
	public void resolve()
	{
		Game.combat.attackingCreatures.add(attacker);
	}

}
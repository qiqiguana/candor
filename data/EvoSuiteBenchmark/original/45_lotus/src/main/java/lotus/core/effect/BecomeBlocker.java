package lotus.core.effect;

import lotus.core.Combat;
import lotus.core.Game;
import lotus.core.card.Creature;

public class BecomeBlocker extends Effect
{
	public Creature blocker;
	public Creature attacker;
	
	public BecomeBlocker(Creature blocker, Creature attacker)
	{
		this.blocker = blocker;
		this.attacker = attacker;
	}

	public void resolve()
	{
		Game.combat.blockingCreatures.get(attacker).add(blocker);
	}
}

package lotus.core.decision;

import lotus.core.card.Creature;
import lotus.core.effect.*;

public class DeclareBlockerDecision implements Decision
{
	public Creature attacker;
	public Creature blocker;
	
	public void applyDecision()
	{
		Effect e = new BecomeBlocker(blocker, attacker);
		e.resolve();
	}
}

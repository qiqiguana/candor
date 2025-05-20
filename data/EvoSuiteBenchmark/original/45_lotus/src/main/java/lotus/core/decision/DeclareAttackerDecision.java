package lotus.core.decision;

import java.util.ArrayList;

import lotus.core.Combat;
import lotus.core.Game;
import lotus.core.card.Creature;
import lotus.core.effect.*;

public class DeclareAttackerDecision implements Decision
{
	public Creature attacker;
	
	public void applyDecision()
	{
		Effect e = new BecomeAttacker(attacker);
		e.resolve();
	}
}

package lotus.core.phases;

import java.util.ArrayList;

import lotus.core.CardCollection;
import lotus.core.Game;
import lotus.core.card.*;
import lotus.core.decision.Decision;
import lotus.core.decision.MultiDecision;
import lotus.core.effect.*;

public class DeclareBlockersPhase extends Phase
{

	@Override
	public void doPhase()
	{
		//init the blockers map
		for(Creature c : Game.combat.attackingCreatures)
		{
			Game.combat.blockingCreatures.put(c, new ArrayList<Creature>());
		}
		
		Decision d = Game.combat.defendingPlayer.input.getDecision();
		d.applyDecision();
		Game.givePriorityToCurrentPlayer();
	}
}

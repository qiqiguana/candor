package lotus.core.phases;

import lotus.core.Game;
import lotus.core.card.*;
import lotus.core.decision.*;
import lotus.core.effect.*;

public class DeclareAttackersPhase extends Phase
{

	@Override
	public void doPhase()
	{
		//ask the player for attackers
		Decision d = Game.combat.attackingPlayer.input.getDecision();
		d.applyDecision();
		
		//tap the attacking creatures (cf 308.2c)
		for(Card c : Game.combat.attackingCreatures)
		{
			Effect e = new ChangeProperty(c, "tapped", true);
			e.resolve();
		}
		
		if(Game.combat.attackingCreatures.isEmpty())
		{
			//TODO : go to CombatEndPhase
		}
		
		Game.givePriorityToCurrentPlayer();
	}

}

package lotus.core.phases;

import lotus.core.Combat;
import lotus.core.Game;

public class CombatBeginningPhase extends Phase
{

	@Override
	public void doPhase()
	{
		Game.combat = new Combat(Game.getPlayingPlayer(), Game.getNonPlayingPlayer()); 
		Game.givePriorityToCurrentPlayer();
	}

}

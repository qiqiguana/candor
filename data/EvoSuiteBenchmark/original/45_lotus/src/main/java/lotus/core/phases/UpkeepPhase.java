package lotus.core.phases;

import lotus.core.Game;

public class UpkeepPhase extends Phase
{

	@Override
	public void doPhase()
	{
		//current player may play
		Game.givePriorityToCurrentPlayer();
	}

}

package lotus.core.phases;

import lotus.core.Game;

public class Main1Phase extends Phase
{
	@Override
	public void doPhase()
	{
		Game.givePriorityToCurrentPlayer();
	}
}

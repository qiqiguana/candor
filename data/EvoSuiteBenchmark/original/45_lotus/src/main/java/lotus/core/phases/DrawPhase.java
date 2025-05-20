package lotus.core.phases;

import lotus.core.Game;
import lotus.core.effect.DrawCard;

public class DrawPhase extends Phase
{

	@Override
	public void doPhase()
	{
		//the current player draws a card, then may play
		DrawCard e = new DrawCard(Game.getPlayingPlayer());
		e.resolve();
		Game.givePriorityToCurrentPlayer();
	}

}

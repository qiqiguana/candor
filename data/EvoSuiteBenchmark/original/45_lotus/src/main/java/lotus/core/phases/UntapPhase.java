package lotus.core.phases;

import lotus.core.*;
import lotus.core.card.*;
import lotus.core.effect.*;
public class UntapPhase extends Phase
{

	@Override
	public void doPhase()
	{
		//for each card in play controlled by the current player
		for(Card c : Game.getPlayingPlayer().inPlay)
		{
			//TODO : other types, make player choose which to untap
			if(c instanceof Creature)
			{
				ChangeProperty e = new ChangeProperty(c,"tapped",false);
				e.resolve();
				e = new ChangeProperty(c,"summoningSickness",false);
				e.resolve();
			}
		}
	}
}

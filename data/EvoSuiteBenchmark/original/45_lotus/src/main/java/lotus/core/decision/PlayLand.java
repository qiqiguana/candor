package lotus.core.decision;

import lotus.core.CardCollection;
import lotus.core.card.Land;
import lotus.core.effect.ChangeZone;

/*
 * The decision to play a land.
 */
public class PlayLand implements Decision
{
	public Land land;
	public PlayLand(Land l)
	{
		land = l;
	}
	public void applyDecision()
	{
		ChangeZone eff = new ChangeZone(land,land.zone,land.owner.inPlay);
		eff.resolve();
	}

}

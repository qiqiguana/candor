package lotus.core.effect;

import lotus.core.card.Land;

public class TapLandForMana extends Effect
{
	public Land land;
	public void resolve()
	{
		if(land.tapped = false)
		{
			ChangeProperty c = new ChangeProperty(land, "tapped",true);
			c.resolve();
			//TODO : add mana
		}
	}
}

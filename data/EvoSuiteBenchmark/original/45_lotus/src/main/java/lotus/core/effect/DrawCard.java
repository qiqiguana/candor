package lotus.core.effect;

import lotus.core.Player;
import lotus.core.card.Card;

public class DrawCard extends Effect
{
	public Player player;
	public DrawCard(Player p)
	{
		player = p;
	}
	@Override
	public void resolve()
	{
		try
		{
			Card c = player.library.get(0);
			player.hand.add(c);
			player.library.remove(c);
			c.zone = player.hand;
		}
		catch(IndexOutOfBoundsException e)
		{
			player.triedToDrawFromEmptyLibrary = true;
		}
	}
}

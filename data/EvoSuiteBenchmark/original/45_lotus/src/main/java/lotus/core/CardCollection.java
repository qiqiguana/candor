package lotus.core;
import lotus.core.card.*;
import java.util.ArrayList;

//needed to supress some warning about something we don't care about
@SuppressWarnings("serial")
public class CardCollection extends ArrayList<Card>
{
	public static CardCollection SearchZone(Card card)
	{
		ArrayList<CardCollection> l = new ArrayList<CardCollection>();
		l.add(Game.player1.hand);
		l.add(Game.player1.graveyard);
		l.add(Game.player1.inPlay);
		l.add(Game.player1.removedFromPlay);
		l.add(Game.player1.library);
		l.add(Game.player2.hand);
		l.add(Game.player2.graveyard);
		l.add(Game.player2.inPlay);
		l.add(Game.player2.removedFromPlay);
		l.add(Game.player2.library);
		for(CardCollection cc : l)
		{
			if(cc.contains(card)) return cc;
		}
		return null;
	}
}

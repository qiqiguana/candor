package lotus.core.effect;

import lotus.core.CardCollection;
import lotus.core.card.Card;

public class ChangeZone extends Effect
{
	Card card;
	CardCollection origin;
	CardCollection destination;
	public ChangeZone(Card card, CardCollection origin, CardCollection destination) 
	{
		this.card = card;
		this.origin = origin;
		this.destination = destination;
	}
	@Override
	public void resolve()
	{
		card.zone = destination;
		destination.add(card);
		origin.remove(card);
	}

}

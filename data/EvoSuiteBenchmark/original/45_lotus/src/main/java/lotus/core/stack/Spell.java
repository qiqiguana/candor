package lotus.core.stack;

import lotus.core.CardCollection;
import lotus.core.card.Card;
import lotus.core.card.Permanent;
import lotus.core.decision.Decision;
import lotus.core.effect.ChangeZone;
import lotus.core.effect.Effect;

// a spell is a card on the stack
public class Spell extends StackObject implements Decision
{
	public Card card;
	public Spell(Card c)
	{
		card = c;
	}
	@Override
	public Effect createEffect()
	{
		// TODO general case
		if(card instanceof Permanent)
		{
			return new ChangeZone(card,card.zone,card.owner.inPlay);
		}
		return null;
	}

	public void applyDecision()
	{
		// Apply the decision to play a card : add the spell to the stack, and remove it from the zone where it was played
		this.addToStack();
		//is not an effect, nothing can react to this
		CardCollection.SearchZone(card).remove(card);
	}
}

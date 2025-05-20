package lotus.core.card;

import java.util.ArrayList;

import lotus.core.stack.ActivatedAbility;
import lotus.core.stack.TriggeredAbility;

public abstract class Permanent extends Card
{
	public boolean tapped = false;
	public abstract ArrayList<ActivatedAbility> getActivatedAbilities();
	public abstract ArrayList<TriggeredAbility> getTriggeredAbilities();
}

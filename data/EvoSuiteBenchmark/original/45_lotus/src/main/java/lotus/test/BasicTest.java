package lotus.test;

import java.util.ArrayList;

import lotus.UI.console.*;
import lotus.core.*;
import lotus.core.card.*;
import lotus.core.phases.Phase;
import lotus.core.stack.ActivatedAbility;
import lotus.core.stack.TriggeredAbility;
public class BasicTest
{
	public static void main(String[] args)
	{
		CardCollection deck1 = new CardCollection();
		CardCollection deck2 = new CardCollection();
		for(int i = 0;i<20;i++)
		{
			deck1.add(new Bear());
			deck2.add(new Bear());
		}
		Game.init(new LocalConsoleUserInterface(), new LocalConsoleUserInterface(), "P1", "P2", deck1, deck2);
		
		//main loop
		while(true)
		{
			Game.currentPhase.doPhase();
			Phase.nextPhase();
		}
	}
}

class Bear extends Creature
{
	public Bear()
	{
		super();
		this.name = "Bear";
		this.strength = 1;
		this.toughness = 1;
		this.summoningSick = true;
		this.tapped = false;
		this.text = "osef";
	}
	@Override
	public ArrayList<ActivatedAbility> getActivatedAbilities()
	{
		return new ArrayList<ActivatedAbility>();
	}

	@Override
	public ArrayList<TriggeredAbility> getTriggeredAbilities()
	{
		return new ArrayList<TriggeredAbility>();
	}
	
}
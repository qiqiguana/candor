package lotus.core;
import lotus.core.decision.Decision;
import lotus.core.decision.PassDecision;
import lotus.core.interfaces.*;

public class Player
{
	//the interfaces used to communicate
	public UserInterface input;
	//name used to identify the player
	public String name;
	//life, starts at 20
	public int life = 20;
	//mana pool
	public Mana manaPool = new Mana(0,0,0,0,0,0);
	
	//zones
	public CardCollection hand, library, graveyard, removedFromPlay, inPlay;
	
	//indicates whether the player has tried to draw from an empty library.
	//TODO : implement
	public boolean triedToDrawFromEmptyLibrary = false;
	
	public Player(UserInterface in, String name, CardCollection deck)
	{
		this.name = name;
		input = in;
		
		library = deck;
		hand = new CardCollection();
		graveyard = new CardCollection();
		removedFromPlay = new CardCollection ();
		inPlay = new CardCollection();
	}
	
	//gives the priority to the player, in order for him to play actions, that may or may not go on stack
	boolean letPlayerSpeak()
	{
		boolean hasDoneAnything = false;
		//first we get the decision from the player
		Decision d = input.getDecision();
		//while the user has something to say
		while(!(d instanceof PassDecision))
		{
			hasDoneAnything = true;
			//apply decision
			d.applyDecision();
			d = input.getDecision();
		}
		return hasDoneAnything;
	}
}
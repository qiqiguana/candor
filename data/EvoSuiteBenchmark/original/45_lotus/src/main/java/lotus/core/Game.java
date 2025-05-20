package lotus.core;

import lotus.core.card.Card;
import lotus.core.interfaces.UserInterface;
import lotus.core.phases.*;
import lotus.core.stack.Stack;

public class Game
{
	//combat phase object
	public static Combat combat;
	//current state
	public static Phase currentPhase;
	//int containing the current player
	public static int playingPlayer;
	//the players
	public static Player player1;
	public static Player player2;
	
	public static Stack stack  = new Stack(); 

	//methods to get players
	public static Player getPlayingPlayer()
	{
		if (playingPlayer == 1) return player1;
		else return player2;
	}
	public static Player getNonPlayingPlayer()
	{
		return getOtherPlayer(getPlayingPlayer());
	}
	public static Player getOtherPlayer(Player p)
	{
		if(p == player1) return player2;
		else return player1;
	}
	
	
	
	
	//init method
	public static void init(UserInterface IP1, UserInterface IP2, String n1, String n2, CardCollection deck1, CardCollection deck2)
	{
		//init both players with the interfaces & names
		player1 = new Player(IP1,n1, deck1);
		player2 = new Player(IP2,n2, deck2);
		
		//init both UI
		IP1.init(player1);
		IP2.init(player2);
		
		//init player system
		playingPlayer = 1;
		currentPhase = new UntapPhase();
		
		//init cards
		for(Card c : deck1)
		{
			c.owner = player1;
			c.zone = player1.library;
		}
		for(Card c : deck2)
		{
			c.owner = player2;
			c.zone = player2.library;
		}
	}
	
	//gives the priority to the current player, and loops until stack is empty and no one wants to play
	public static void givePriorityToCurrentPlayer()
	{
		boolean APdone = false, NAPdone = false;
		for(;;)
		{
			if(Game.getPlayingPlayer().letPlayerSpeak()) APdone = false;
			else APdone = true;
			if (NAPdone && APdone) break;
			
			if(Game.getNonPlayingPlayer().letPlayerSpeak()) NAPdone = false;
			else NAPdone = true;
			if (NAPdone && APdone) break;
		}
		if(!Game.stack.empty())
		{
			Game.stack.resolveLast();
			givePriorityToCurrentPlayer();
		}
	}
}

package lotus.UI.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import lotus.core.Arbiter;
import lotus.core.Game;
import lotus.core.Player;
import lotus.core.card.Card;
import lotus.core.card.Permanent;
import lotus.core.decision.Decision;
import lotus.core.interfaces.UserInterface;

public class LocalConsoleUserInterface implements UserInterface
{
	//used to get inputs
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	//player this interface is attached to
	Player player;
	public Decision getDecision()
	{
		displayCurrentGame();
		ArrayList<Decision> decisions = Arbiter.getLegalPlayerDecisions(player);
		//are we done with the loop ?
		boolean done = false;
		while(!done)
		{
			done = true;
			//print the choices
			for(int i = 0;i<decisions.size();i++)
			{
				System.out.print((i+1)+" : ");
				System.out.println(decisions.get(i).toString());
			}
			//ask the user
			System.out.println("What is your choice ?");
			try
			{
				String line = input.readLine();
				return decisions.get(Integer.parseInt(line)-1);
			}
			catch (Exception e)
			{
				//loop while the user hasn't given a correct answer
				System.out.println("Invalid choice, try again");
				done = false;
			}
		}
		return null;
	}

	private void displayCurrentGame()
	{
		System.out.println("State of the game for player " + player.name);
		System.out.println("Current phase : playing player : " + Game.getPlayingPlayer().name + ", phase " + Game.currentPhase);
		//display every permanent in game, as well as its tapped status
		System.out.println("Permanents in play under your control");
		for(Card c : player.inPlay)
		{
			System.out.println(c.toString() + " is " + (((Permanent)c).tapped ? "tapped" : "untapped"));
		}
		
		//display hand
		System.out.println("Cards in your hand :");
		for(Card c : player.hand)
		{
			System.out.println(c.toString());
		}
	}

	public void init(Player p)
	{
		player = p;
	}

	public void notifyOfChange()
	{
		// TODO Auto-generated method stub
	}

}

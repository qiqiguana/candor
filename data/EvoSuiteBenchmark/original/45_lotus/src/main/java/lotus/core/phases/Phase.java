package lotus.core.phases;

import lotus.core.Game;
import lotus.core.effect.ChangePhase;

public abstract class Phase
{
	//accomplish the phase action
	public abstract void doPhase();
	
	
	private static void changePhase(Phase p)
	{
		Game.currentPhase = p;
		ChangePhase effect = new ChangePhase(p);
		effect.resolve();
	}
	
	public static void nextPhase()
	{
		if(Game.currentPhase instanceof UntapPhase) changePhase(new UpkeepPhase());
		else if(Game.currentPhase instanceof UpkeepPhase) changePhase(new DrawPhase());
		else if(Game.currentPhase instanceof DrawPhase) changePhase(new Main1Phase());
		else if(Game.currentPhase instanceof Main1Phase) changePhase(new CombatBeginningPhase());
		else if(Game.currentPhase instanceof CombatBeginningPhase) changePhase(new DeclareAttackersPhase());
		else if(Game.currentPhase instanceof DeclareAttackersPhase) changePhase(new DeclareBlockersPhase());
		else if(Game.currentPhase instanceof DeclareBlockersPhase) changePhase(new CombatDamagePhase());
		else if(Game.currentPhase instanceof CombatDamagePhase) changePhase(new CombatEndPhase());
		else if(Game.currentPhase instanceof CombatEndPhase) changePhase(new Main2Phase());
		else if(Game.currentPhase instanceof Main2Phase) changePhase(new EndOfTurnPhase());
		else if(Game.currentPhase instanceof EndOfTurnPhase) changePhase(new CleanupPhase());
		else if(Game.currentPhase instanceof CleanupPhase) changePhase(new PlayerChangePhase());
		else if(Game.currentPhase instanceof PlayerChangePhase)
		{
			//change player
			Game.playingPlayer = Game.playingPlayer % 2 + 1;
			changePhase(new UntapPhase());
		}
	}
}




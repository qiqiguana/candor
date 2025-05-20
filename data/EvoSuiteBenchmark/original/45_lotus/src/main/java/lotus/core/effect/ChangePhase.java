package lotus.core.effect;

import lotus.core.phases.Phase;

public class ChangePhase extends Effect
{
	//TODO : make the phase manager aware of this
	public Phase newPhase;
	public ChangePhase(Phase newPhase)
	{
		this.newPhase = newPhase;
	}
	@Override
	public void resolve()
	{
		//nothing in the normal course of the game happens
	}
}

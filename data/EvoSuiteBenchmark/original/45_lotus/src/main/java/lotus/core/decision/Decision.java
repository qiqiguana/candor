package lotus.core.decision;


/*
 * A decision is any action decided by a player. There should be 1 to 1 correspondence between
 * decisions made by players and Decision objects.
 * This is an interface, and many classes, which may not be in this namespace, implement it
 */
public interface Decision
{
	//apply the decision of the player
	public void applyDecision();
}
package lotus.core.effect;

/*
 * An effect is anything that affects the game. It MUST be used as an interface to make
 * modifications that may trigger abilities
 */
public abstract class Effect
{
	public abstract void resolve();
}

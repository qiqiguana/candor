package lotus.core.card;

/*
 * Creatures extends permanents. They are intended to be subclassed by every
 * creature in the game
 */
public abstract class Creature extends Permanent
{
    //Strength and thoughness of the creature
    public  int strength, toughness;
    //true iff the creature has invocation sickness (first turn it's played)
    public boolean summoningSick = true;
}
package lotus.core.card;

import java.util.ArrayList;

import lotus.core.stack.ActivatedAbility;
import lotus.core.stack.TriggeredAbility;

/*
 * Basic lands are hard coded. This class is intended to be subclassed
 * by the five basic lands, which will redefine BasicLandType
 */
public class BasicLand extends Land
{
    //the classic five basic land types
    public enum BasicLandType
    {
    	PLAINS, ISLAND, SWAMP, MOUNTAIN, FOREST
    }
    
    //a basic land has a land type
    public BasicLandType type;

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

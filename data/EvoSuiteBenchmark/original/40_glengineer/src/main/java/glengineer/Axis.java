package glengineer;

/**
 * Encapsulates the two possible layout directions:
 * the horizontal and the vertical ones.
 */
public enum Axis
{
	HORIZONTAL,
	VERTICAL;
	
	public String toString()
	{
		return this == HORIZONTAL ? "horizontal" : "vertical"; 
	}
}

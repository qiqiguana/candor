package glengineer.positions;

import glengineer.blocks.Block;
import glengineer.blocks.CharTable;

/**
 * An abstract class for horizontal and vertical words positions.
 */
public abstract class WordPosition
{
	/**
	 * Returns the coordinates of the beginning of this word.
	 */
	public abstract CharPosition1 getStart();
	
	/**
	 * Returns the coordinates of the ending of this word (exclusive).
	 */
	public abstract CharPosition2 getEnd();
	
	public abstract boolean equals(WordPosition wp);
	
	public abstract boolean isTrivial();
	
	public abstract boolean contains(int x, int y);
	
	public boolean contains(CharPosition pos) {
		return contains(pos.x, pos.y);
	}
	
	/**
	 * Verifies whether this word position lies
	 * inside of the specified char table.
	 */
	public abstract boolean liesIn(CharTable ct);
	
	/**
	 * Verifies whether this word position intersects the specified char table.
	 */
	public abstract boolean intersects(CharTable ct);
	
	/**
	 * Returns the length (width or height)
	 * of the intersection of this line with the specified block
	 * or <code>0</code> if they are not intersected.
	 */
	public abstract int getLengthInsideOf(Block block);
	
	/**
	 * Returns the text at this position on the specified char table.
	 * 
	 * The call is dispatched back to the specified char table
	 * informing it about the concrete word position type. 
	 * 
	 * @param ct	the char table from which the text must be read.
	 * 
	 * @return		the text at this position on the specified char table.
	 */
	public abstract String textAt(CharTable ct);
}

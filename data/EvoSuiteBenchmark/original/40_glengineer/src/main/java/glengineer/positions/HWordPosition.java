package glengineer.positions;

import glengineer.blocks.Block;
import glengineer.blocks.CharTable;

/**
 * Encapsulates the position (start and end coordinates)
 * of a word lying horizontally on the scheme.
 */
public class HWordPosition extends WordPosition
{
	/**
	 * The horizontal coordinate of the word start.
	 */
	public final int x1;
	
	/**
	 * The horizontal coordinate of the word end + 1.
	 */
	public final int x2;
	
	/**
	 * The vertical coordinate of the word.
	 */
	public final int y;
	
	public HWordPosition(int x1, int x2, int y) {
		if( x1 > x2 )
			throw new IllegalArgumentException(
					"wrong word position parameters" );
		this.x1 = x1;
		this.x2 = x2;
		this.y = y;
	}
	
	public HWordPosition(CharPosition start, int x2) {
		this(start.x, x2, start.y);
	}
	
	public HWordPosition(int x1, CharPosition end) {
		this(x1, end.x, end.y);
	}
	
	/**
	 * The word position is initialized
	 * representing the upper line of the specified block.
	 */
	public HWordPosition(Block block) {
		this(block.getPos1(), block.getPos2().x);
	}
	
	/**
	 * Creates a trivial word position
	 * which begins and ends at the specified character position.
	 */
	public HWordPosition(CharPosition cp) {
		this(cp.x,cp.x, cp.y);
	}
	
	/**
	 * Returns the coordinates of the beginning of this word.
	 */
	public CharPosition1 getStart() {
		return new CharPosition1(x1,y);
	}
	/**
	 * Returns the coordinates of the ending of this word (exclusive).
	 */
	public CharPosition2 getEnd() {
		return new CharPosition2(x2,y);
	}
	
	public int getWidth()
	{
		return x2 - x1;
	}
	
	public boolean equals(WordPosition wp)
	{
		if( ! (wp instanceof HWordPosition) )
			return false;
		HWordPosition hwp = (HWordPosition) wp;
		return hwp.x1 == x1 && hwp.x2 == x2 && hwp.y == y;
	}
	
	public boolean isTrivial() {
		return x1 >= x2;
	}
	
	public boolean contains(int x, int y)
	{
		return y == this.y && x1 <= x && x < x2;
	}
	
	public boolean liesIn(CharTable ct)
	{
		return x1 >= ct.x1 && x2 <= ct.x2
				&& y >= ct.y1 && y < ct.y2;
	}
	
	/**
	 * Verifies whether this word position intersects the specified char table.
	 */
	public boolean intersects(CharTable ct)
	{
		return ct.y1 <= y && y < ct.y2
				&& ct.x1 < x2 && x1 < ct.x2;
	}
	
	/**
	 * Returns the width of the intersection of this line
	 * with the specified block
	 * or <code>0</code> if they are not intersected.
	 */
	public int getLengthInsideOf(Block block)
	{
		int width = Math.min( x2, block.x2 ) - Math.max( x1, block.x1 );
		return Math.max( width, 0 );
	}
	
	public String toString() {
		return "horizontal(" + x1 + "," + x2 + ";" + y + ")";
	}
	
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
	public String textAt(CharTable ct)
	{
		return ct.textAt(this);
	}
	
}

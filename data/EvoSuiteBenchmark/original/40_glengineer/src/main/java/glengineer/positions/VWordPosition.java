package glengineer.positions;

import glengineer.blocks.Block;
import glengineer.blocks.CharTable;

/**
 * Encapsulates the position (start and end coordinates)
 * of a word lying vertically on the scheme.
 */
public class VWordPosition extends WordPosition
	implements Comparable<VWordPosition>
{
	/**
	 * The horizontal coordinate of the word.
	 */
	public final int x;
	
	/**
	 * The vertical coordinate of the word start.
	 */
	public final int y1;
	
	/**
	 * The vertical coordinate of the word end + 1.
	 */
	public final int y2;
	
	
	public VWordPosition(int x, int y1, int y2) {
		if( y1 > y2 )
			throw new IllegalArgumentException("wrong word position parameters");
		this.x = x;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public VWordPosition(CharPosition start, int y2) {
		this(start.x, start.y, y2);
	}
	
	public VWordPosition(int y, CharPosition end) {
		this(end.x, y, end.y);
	}
	
	public VWordPosition(Block block) {
		this(block.getPos1(), block.getPos2().x);
	}
	
	/**
	 * Creates a trivial word position
	 * which begins and ends at the specified character position.
	 */
	public VWordPosition(CharPosition cp) {
		this(cp.x, cp.y,cp.y);
	}
	
	/**
	 * Returns the coordinates of the beginning of this word.
	 */
	public CharPosition1 getStart() {
		return new CharPosition1(x,y1);
	}
	/**
	 * Returns the coordinates of the ending of this word (exclusive).
	 */
	public CharPosition2 getEnd() {
		return new CharPosition2(x,y2);
	}
	
	public int getHeight()
	{
		return y2 - y1;
	}
	
	public boolean equals(WordPosition wp)
	{
		if( ! (wp instanceof VWordPosition) )
			return false;
		VWordPosition hwp = (VWordPosition) wp;
		return hwp.x == x && hwp.y1 == y1 && hwp.y2 == y2;
	}
	
	public boolean isTrivial() {
		return y1 >= y2;
	}
	
	public boolean contains(int x, int y)
	{
		return x == this.x && y1 <= y && y < y2;
	}
	
	public boolean liesIn(CharTable ct)
	{
		return x >= ct.x1 && x < ct.x2
				&& y1 >= ct.y1 && y2 <= ct.y2;
	}
	
	/**
	 * Verifies whether this word position intersects the specified char table.
	 */
	public boolean intersects(CharTable ct)
	{
		return ct.x1 <= x && x < ct.x2
				&& ct.y1 < y2 && y1 < ct.y2;
	}
	
	/**
	 * Returns the height of the intersection of this line
	 * with the specified block
	 * or <code>0</code> if they are not intersected.
	 */
	public int getLengthInsideOf(Block block)
	{
		int height = Math.min( y2, block.y2 ) - Math.max( y1, block.y1 );
		return Math.max( height, 0 );
	}
	
	/**
	 * Compares this vertical position with the specified one
	 * according to their x-coordinates.
	 */
	public int compareTo(VWordPosition vLine)
	{
		int xx = vLine.x;
		if( x < xx )
			return -1;
		else if( x == xx ) 
			return 0;
		else
			return +1;
	}

	public String toString() {
		return "vertical(" + x + ";" + y1 + "," + y2 + ")";
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

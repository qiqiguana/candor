package glengineer.blocks;

import glengineer.positions.*;

import java.util.*;

/**
 * An implementation of the abstract class {@code Block}
 * used for vertical layouting.
 */
public class VerticalBlock extends Block
{
	public VerticalBlock(Scheme scheme, int x1, int y1, int x2, int y2) {
		super(scheme, x1, y1, x2, y2);
	}
	public VerticalBlock(Scheme scheme, CharPosition p1, CharPosition p2) {
		super(scheme, p1, p2);
	}
	public VerticalBlock(Scheme scheme) {
		super(scheme);
	}
	public VerticalBlock(Block b) {
		super(b.scheme, b.getPos1(), b.getPos2());
	}
	public VerticalBlock(Block block, int x1, int y1, int x2, int y2) {
		super(block.scheme, x1, y1, x2, y2);
	}
	
	public VerticalBlock subblock(int x1, int y1, int x2, int y2) {
		return new VerticalBlock(scheme, x1,y1, x2,y2);
	}
	
	public VerticalBlock cloneWith(CharPosition1 p1) {
		return new VerticalBlock(scheme, p1, getPos2());
	}
	
	/**
	 * Divides the current block into a set of sequential subblocks,
	 * optimizes these subblocks and returns them as a linked list.
	 * 
	 * @return	a list of optimized sequential subblocks.
	 */
	public List<Block> extractSequentialSubblocks()
	{
		this.optimize();
		if(isTrivial())  return new LinkedList<Block>();
		
		List<Block> result = new LinkedList<Block>();
		
		VerticalBlock firstBlock;
		VerticalBlock theRest = this;
		do {
			firstBlock = theRest.extractFirstSequentialSubblock();
			theRest =
				theRest.cloneWith( new CharPosition1(this.x1,firstBlock.y2) );
			firstBlock.optimize();
			if( ! firstBlock.isTrivial() )
				result.add( firstBlock );
			theRest.optimize();
		}
		while ( ! theRest.isTrivial() );
		return result;
	}
	
	/**
	 * Extracts the upper subblock of this block.
	 * <p>
	 * The required block is chosen so that its last exclusive horizontal line
	 * does not contain elements of vertical lines
	 * starting above the line.
	 * <p>
	 * The algorithm finds the upper entity
	 * (an element of a component, gap, or vertical line)
	 * using simple search along the scheme,
	 * and retains it as the top of the required block.
	 * Then, starting from the height of that point,
	 * it sequentially finds vertical lines
	 * end below but begin not below
	 * and moves to lower end of such lines.
	 * The lower end of the last such vertical line defines
	 * the bottom of the required block.
	 */
	private VerticalBlock extractFirstSequentialSubblock()
	{
		Set<VWordPosition> vLines = getIntersectingVLines();
		
		CharPosition blockStart = getBlockStart();
		
		int y2 = getUpperSubblockEnd( blockStart, vLines );
		
		return subblock( this.x1, blockStart.y, this.x2, y2 );
	}
	
	/**
	 * Returns the set of all vertical lines intersecting this block.
	 */
	private Set<VWordPosition> getIntersectingVLines()
	{
		Set<VWordPosition> source = scheme.getVLinesPositions();
		Set<VWordPosition> result = new HashSet<VWordPosition>();
		for (VWordPosition line : source)
			if( line.intersects(this) )
				result.add( line );
		return result;
	}
	
	/**
	 * Finds the upper entity (an element of a word or vertical line)
	 * of this block.
	 * Returns its position on the scheme.
	 * @throws	IllegalArgumentException if this block happens to be empty.
	 */
	private CharPosition getBlockStart()
	{
		int x = x1;
		int y = y1;
		for (; y < y2; y++)
			for (x = x1; x < x2; x++)
				if( entityAt(x,y) )  return new CharPosition(x,y);
		throw new IllegalArgumentException(
				"Can not extract elements from an empty block:\n" + this);
	}
	
	/**
	 * Verifies whether the character under the cell
	 * determined by the specified coordinates
	 * is a word character or a vertical line character.
	 */
	private boolean entityAt(int x, int y)
	{
		char c = charAt(x,y);
		/* 
		 * Since all symbols on the scheme are correct
		 * (which has been verified in its constructor),
		 * there is no need to use the slower method isWordChar().
		 */
		return  c != ' ' && c != '-';
	}
	/**
	 * Finds the last line of the subblock
	 * containing the specified position {@code blockStart}.
	 * Returns the coordinate {@code y2} of such block.
	 */
	private int getUpperSubblockEnd( CharPosition blockStart,
								Set<VWordPosition> vLines )
	{
		int nextY = blockStart.y;
		int currentY;
		do {
			currentY = nextY;
			nextY = y2OfAVLineCovering(currentY, vLines);
		} while( nextY > currentY && nextY < y2 - 1 );
		return Math.min( y2, nextY + 1 );
	}
	
	/**
	 * Finds a vertical line which vertical range covers
	 * the specified value {@code y}
	 * and which lower point is lower than {@code y}.
	 * Returns the position of the last element of such line (inclusive)
	 * or {@code y} if such line does not exist.
	 */
	private int y2OfAVLineCovering( int y, Set<VWordPosition> vLines )
	{
		for (VWordPosition line : vLines)
			if( line.y1 <= y && y + 1 < line.y2 )
				return line.y2 - 1;
		return y;
	}
	
	/**
	 * Divides the current block into a set of parallel subblocks,
	 * optimizes these subblocks and returns them as a linked list.
	 * <p>
	 * If the block has vertical lines,
	 * this method divides the block by the maximal vertical lines only.
	 * <p>
	 * If there are no vertical lines, the block is divided into
	 * usual parallel subblocks.
	 */
	public List<Block> extractParallelSubblocks()
	{
		this.optimize();
		if( isTrivial() )  return new LinkedList<Block>();
		
		Set<VWordPosition> vLines = getIntersectingVLines();
		
		if( vLines.size() > 0 )
			return extractParallelSubblocksSeparatedByLines( vLines );
		else
			return extractParallelSubblocksWithoutLines();
	}
	
	/**
	 * Divides the current block into a set of parallel subblocks
	 * by its most long vertical lines,
	 * optimizes these subblocks, and returns them as a linked list.
	 */
	protected List<Block> extractParallelSubblocksSeparatedByLines(
			Set<VWordPosition> vLines )
	{
		removeSmallVLinesFrom(vLines);
		return extractParallelSubblocksSeparatedBy( vLines );
	}
	
	/**
	 * Finds the longest lines in the specified set of vertical lines
	 * and then removes from it all other elements.
	 * <p>
	 * Note that, more formally, this method considers the lengths
	 * of <i>intersections</i> of the lines with this block.
	 */
	private void removeSmallVLinesFrom( Set<VWordPosition> vLines )
	{
		//determine the maximum length:
		int maxLength = 0;
		int length;
		for (VWordPosition line : vLines) {
			length = line.getLengthInsideOf(this);
			maxLength = Math.max(length,maxLength);
		}
		//remove the short lines:
		VWordPosition line;
		Iterator<VWordPosition> i = vLines.iterator();
		for(; i.hasNext(); ) {
			line = i.next();
			if( line.getLengthInsideOf(this) < maxLength )
				i.remove();
		}
	}
	
	private List<Block> extractParallelSubblocksSeparatedBy(
			Set<VWordPosition> separators )
	{
		List<Block> result = new LinkedList<Block>();
		Block subBlock;
		int leftX = x1; //the left bound of a subblock
		int rightX; //the exclusive right bound of a subblock
		HWordPosition gapAtSeparator = null; //(may exist; widens the separator)
		
		for( VWordPosition rightSeparator : sortedVLines(separators) ) {
			rightX = rightSeparator.x;
			//if the separator has a gap, the latter must be skipped:
			gapAtSeparator = scheme.gapAtLine(rightSeparator); 
			if( gapAtSeparator != null )
				rightX = gapAtSeparator.x1;
			//adding the subblock between leftX and rightX:
			if( rightX > leftX + 1 ) {
				subBlock = subblock(leftX,y1,rightX,y2);
				subBlock.optimize();
				if( !subBlock.isTrivial() )
					result.add( subBlock );
			}
			//iterating leftX:
			if( gapAtSeparator == null )
				leftX = rightX + 1;
			else
				leftX = gapAtSeparator.x2;
		}
		//on the right of the last line:
		if( leftX < x2 ) {
			subBlock = subblock(leftX,y1,x2,y2);
			subBlock.optimize();
			if( !subBlock.isTrivial() )
				result.add( subBlock );
		}
		return result;
	}
	
	/*
	 * Returns a list of the specified vertical lines
	 * sorted according to the horizontal coordinates.
	 */
	private List<VWordPosition> sortedVLines( Set<VWordPosition> vLines )
	{
		LinkedList<VWordPosition> list = new LinkedList<VWordPosition>(vLines);
		Collections.sort( list );
		return list;
	}
	
	/**
	 * Divides the current block without vertical lines
	 * into a set of (optimized) parallel subblocks.
	 * Returns them as a linked list.
	 */
	protected List<Block> extractParallelSubblocksWithoutLines()
	{
		List<Block> result = new LinkedList<Block>();
		
		Block firstBlock;
		VerticalBlock theRest = this;
		do {
			firstBlock = theRest.extractFirstParallelSubblock();
			theRest = theRest.cloneWith(
					new CharPosition1(firstBlock.x2,this.y1) );
			firstBlock.optimize();
			if( ! firstBlock.isTrivial() )
				result.add( firstBlock );
			theRest.optimize();
		} while ( ! theRest.isTrivial() );
		return result;
	}
	
	/**
	 * Extracts the left subblock of this block.
	 * <p>
	 * The required block is chosen so that its last exclusive vertical line
	 * does not contain elements of components or gaps
	 * starting on the left from the line.
	 * <p>
	 * The algorithm just finds the most left entity
	 * (an element of a component or gap)
	 * using simple search along the scheme,
	 * identifies the horizontal element containing the entity found,
	 * checks whether the last exclusive vertical line of the element
	 * matches the requirement for the boundary of the required block,
	 * and, if does not, takes the detaining horizontal element
	 * and continues the cycle.
	 */
	private Block extractFirstParallelSubblock()
	{
		final HWordPosition firstWord =
			scheme.anyHElementAt( firstWordChar() );
		
		HWordPosition lastWord = firstWord;
		
		HWordPosition nextWord = firstWord;
		for(; nextWord != null; ) {
			lastWord = nextWord;
			nextWord = findAWordExtendingSubblockToTheRightFrom( nextWord );
		}
		
		int startX = firstWord.x1;
		int endX = lastWord.x2;
		//For the case of words containing cells outside of this block:
		startX = Math.max( startX, x1 );
		endX = Math.min( endX, x2 );
		
		return subblock( startX, y1, endX, y2 );
	}
	
	/**
	 * Searches down and to the right from the beginning of this block
	 * for the first occurrence of a word character.
	 * @return	the position of the word character found.
	 * @throws	IllegalArgumentException if this block
	 * 			does not contain word characters.
	 */
	private CharPosition firstWordChar()
	{
		int y = firstWordCharBelow(x1,y1);
		if( y != y2 )
			return new CharPosition(x1,y);
		
		int x = x1 + 1;
		for (; x < x2; x++) {
			y = firstWordCharBelow(x,y1);
			if( y != y2 )  break;
		}
		
		if( x != x2 )
			return new CharPosition(x,y);
		else
			throw new IllegalArgumentException(
					"Can not extract a group from an empty block:\n" + this);
	}
	
	/**
	 * Finds the first entity below the specified position.
	 * @return	the y-coordinate of the entity found or y2 if not found.
	 */
	private int firstWordCharBelow(int x, int y)
	{
		for (; y < y2; y++)
			if( wordCharAt(x,y) )  break;
		return y;
	}
	
	/**
	 * Verifies whether the character under the cell
	 * determined by the specified coordinates
	 * is a word character.
	 * <p>
	 * Overrides the less optimal method {@code CharTable.wordCharAt()}.
	 * Since all symbols on the scheme are definitely correct
	 * (which has been verified in its constructor),
	 * there is no need to use slower method.
	 */
	public boolean wordCharAt(int x, int y)
	{
		char c = charAt(x,y);
		return c != ' ' && c != '-' && c != '|' && c != '+';
	}
	
	/**
	 * Returns a word which starts not righter than the right end of the
	 * {@code currentWord} but ends strictly righter
	 * (or null if does not exist).
	 */
	private HWordPosition findAWordExtendingSubblockToTheRightFrom(
				HWordPosition currentWord )
	{
		int x = currentWord.x2 - 1;
		int y = y1;
		int yToSkip = currentWord.y;
		
		HWordPosition result;
		
		for(; y < y2; y++) {
			if( y == yToSkip || ! wordCharAt(x,y) )
				continue;
			result = scheme.anyHElementAt(x,y);
			if( result == null ) 
				continue;
			if( result.x2 > currentWord.x2 )
				return result;
		}
		
		return null;
	}
	
}

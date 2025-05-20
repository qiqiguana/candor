package glengineer.blocks;

import glengineer.positions.*;

import java.util.*;

/**
 * An implementation of the abstract class {@code Block}
 * used for horizontal layouting.
 */
public class HorizontalBlock extends Block
{
	public HorizontalBlock(Scheme scheme, int x1, int y1, int x2, int y2) {
		super(scheme, x1, y1, x2, y2);
	}
	public HorizontalBlock(Scheme scheme, CharPosition p1, CharPosition p2) {
		super(scheme, p1, p2);
	}
	public HorizontalBlock(Scheme scheme) {
		super(scheme);
	}
	public HorizontalBlock(Block b) {
		super(b.scheme, b.getPos1(), b.getPos2());
	}
	public HorizontalBlock(Block block, int x1, int y1, int x2, int y2) {
		super(block.scheme, x1, y1, x2, y2);
	}
	
	public Block subblock(int x1, int y1, int x2, int y2) {
		return new HorizontalBlock(scheme, x1,y1, x2,y2);
	}
	
	public HorizontalBlock cloneWith(CharPosition1 p1)
	{
		return new HorizontalBlock(scheme, p1, getPos2());
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
		if( isTrivial() )  return new LinkedList<Block>();
		
		List<Block> result = new LinkedList<Block>();
		
		Block firstBlock;
		HorizontalBlock theRest = this;
		do {
			firstBlock = theRest.extractFirstSequentialSubblock();
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
	 * does not contain elements of components, gaps , or horizontal lines
	 * starting on the left from the line.
	 * <p>
	 * The algorithm just finds the most left entity
	 * (an element of a component, gap, or horizontal line)
	 * using simple search along the scheme,
	 * identifies the horizontal element containing the entity found,
	 * checks whether the last exclusive vertical line of the element
	 * matches the requirement for the boundary of the required block,
	 * and, if does not, takes the detaining horizontal element
	 * and continues the cycle.
	 */
	private Block extractFirstSequentialSubblock()
	{
		final HWordPosition firstWord =
			scheme.anyHElementAt( firstEntity() );
		
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
	 * for the first occurrence of an entity.
	 * @return	the position of the entity found.
	 * @throws	IllegalArgumentException if this block
	 * 			does not contain entities.
	 */
	private CharPosition firstEntity()
	{
		int y = firstEntityBelow(x1,y1);
		if( y != y2 )
			return new CharPosition(x1,y);
		
		int x = x1 + 1;
		for (; x < x2; x++) {
			y = firstEntityBelow(x,y1);
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
	private int firstEntityBelow(int x, int y)
	{
		for (; y < y2; y++)
			if( entityAt(x,y) )  break;
		return y;
	}
	
	/**
	 * Verifies whether the character under the cell
	 * determined by the specified coordinates
	 * is a word character or a horizontal line character.
	 */
	private boolean entityAt(int x, int y)
	{
		char c = charAt(x,y);
		/* 
		 * Since all symbols on the scheme are correct
		 * (which has been verified in its constructor),
		 * there is no need to use the slower method isWordChar().
		 */
		return c != ' ' && c != '|';
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
			if( y == yToSkip || ! entityAt(x,y) )
				continue;
			result = scheme.anyHElementAt(x,y);
			if( result == null ) 
				continue;
			if( result.x2 > currentWord.x2 )
				return result;
		}
		
		return null;
	}
	
	
	/**
	 * Divides the current block into a set of parallel subblocks,
	 * optimizes these subblocks and returns them as a linked list.
	 * <p>
	 * If the block has horizontal lines,
	 * this method divides the block by the maximal horizontal lines only.
	 * <p>
	 * If there are no horizontal lines, the block is divided into
	 * usual parallel subblocks.
	 */
	public List<Block> extractParallelSubblocks()
	{
		this.optimize();
		if( isTrivial() )
			return new LinkedList<Block>();
		
		Set<HWordPosition> hLines = getIntersectingHLines();
		
		if( hLines.size() > 0 )
			return extractParallelSubblocksSeparatedByLines( hLines );
		else
			return extractParallelSubblocksWithoutLines();
	}
	
	/**
	 * Returns the set of all horizontal lines intersecting this block.
	 */
	private Set<HWordPosition> getIntersectingHLines()
	{
		Set<HWordPosition> source = scheme.getHLinesPositions();
		Set<HWordPosition> result = new HashSet<HWordPosition>();
		for (HWordPosition line : source)
			if( line.intersects(this) )
				result.add( line );
		return result;
	}
	
	/**
	 * Divides the current block into a set of parallel subblocks
	 * by its most long horizontal lines,
	 * optimizes these subblocks, and returns them as a linked list.
	 */
	protected List<Block> extractParallelSubblocksSeparatedByLines(
			Set<HWordPosition> hLines )
	{
		removeSmallHLinesFrom(hLines);
		
		List<Integer> separators = sortedYsOf(hLines);
		
		return extractParallelSubblocksSeparatedBy( separators );
	}
	
	/**
	 * Finds the longest lines in the specified set of horizontal lines
	 * and then removes from it all other elements.
	 * <p>
	 * Note that, more formally, this method considers the lengths
	 * of <i>intersections</i> of the lines with this block.
	 */
	private void removeSmallHLinesFrom( Set<HWordPosition> hLines )
	{
		//determine the maximum length:
		int maxLength = 0;
		int length;
		for (HWordPosition line : hLines) {
			length = line.getLengthInsideOf(this);
			maxLength = Math.max(length,maxLength);
		}
		//remove the short lines:
		HWordPosition line;
		Iterator<HWordPosition> i = hLines.iterator();
		for(; i.hasNext(); ) {
			line = i.next();
			if( line.getLengthInsideOf(this) < maxLength )
				i.remove();
		}
	}
	
	/**
	 * Returns a list of the specified horizontal lines' vertical coordinates.
	 */
	private List<Integer> sortedYsOf( Set<HWordPosition> hLines )
	{
		LinkedList<Integer> linesY = new LinkedList<Integer>();
		for (HWordPosition line : hLines)
			linesY.add( new Integer(line.y) );
		Collections.sort( linesY );
		return linesY;
	}
	
	private List<Block> extractParallelSubblocksSeparatedBy(
			List<Integer> ySeparators )
	{
		List<Block> blocksList = new LinkedList<Block>();
		Block subBlock;
		int upperY = y1; //upper bound of a subblock
		
		for (int lowerY : ySeparators) {
			if( lowerY > upperY ) {
				//there is a (non-trivial?) block between two lines.
				subBlock = new HorizontalBlock(this, x1,upperY, x2,lowerY);
				subBlock.optimize();
				if( !subBlock.isTrivial() )
					blocksList.add( subBlock );
			}
			upperY = lowerY + 1;
		}
		//below the last line:
		if( upperY < y2 ) {
			subBlock = new HorizontalBlock(this, x1,upperY, x2,y2);
			subBlock.optimize();
			if( !subBlock.isTrivial() )
				blocksList.add( subBlock );
		}
		return blocksList;
	}
	
	/**
	 * Divides the current block without horizontal lines
	 * into a set of parallel subblocks, optimizes these subblocks
	 * and returns them as a linked list.
	 */
	protected List<Block> extractParallelSubblocksWithoutLines()
	{
		List<Block> result = new LinkedList<Block>();
		Block block;
		for (int y = y1; y < y2; y++) {
			block = subblock(x1,y, x2,y+1);
			block.optimize();
			if( !block.isTrivial() )
				result.add(block);
		}
		return result;
	}
	
}

package glengineer.blocks;

import glengineer.positions.*;

import java.util.*;


/**
 * Represents the layout scheme which is specified by the end user
 * as a sequence of strings.
 */
public class Scheme extends CharTable 
{
	protected final String[] lines;
	
	/**
	 * Encapsulates the sets of positions of all components, lines and gaps
	 * present on the scheme.
	 */
	private static class Positions {
		HashSet<HWordPosition> components = new HashSet<HWordPosition>();
		HashSet<HWordPosition> hLines = new HashSet<HWordPosition>();
		HashSet<VWordPosition> vLines = new HashSet<VWordPosition>();
		HashSet<HWordPosition> gaps = new HashSet<HWordPosition>();
	}
	private Positions positions;
	
	/**
	 * Encapsulates the sets of names of all components and of all gaps
	 * present on the scheme.
	 */
	private static class Names {
		HashSet<String> components = new HashSet<String>();
		HashSet<String> gaps = new HashSet<String>();
	}
	private Names names;
	
	/**
	 * Maps components and gaps positions to the corresponding names.
	 */
	private HashMap<HWordPosition,String> positionsToNames;
	
	/**
	 * Maps the y-coordinates on the scheme
	 * to sets of horizontal words having these coordinates.
	 * This map is used in the horizontal words and lines search methods.
	 */
	private Map<Integer, HashSet<HWordPosition>> hWordsAtYs;
	
	/**
	 * Maps the x-coordinates on the scheme
	 * to sets of vertical words having these coordinates.
	 * This map is used in the vertical line search method.
	 */
	private Map<Integer, HashSet<VWordPosition>> vWordsAtXs;
	
	/**
	 * Represents the correspondence between the lines on the scheme
	 * and the gaps which belong to those lines (intersect them).
	 * Both lines and gaps are represented by their positions on the scheme.
	 * <p>
	 * Note that each line may contain (may be intersected by) only one gap.
	 */
	private Map<WordPosition, HWordPosition> gapsAtLines;
	
	public Scheme(String... strings)
	{
		lines = strings;
		calculateAndCheckSize();
		recognizeContent();
		extractNamesFromPositions();
		organizeFastElementsSearch();
		recognizeGapsAtLines();
	}
	
	protected void calculateAndCheckSize()
	{
		if( lines.length == 0 )
			throw new IllegalArgumentException("The scheme has no lines.");
		x1 = 0;
		y1 = 0;
		y2 = lines.length;
		x2 = lines[0].length();
		for (int i=1; i<y2; i++)
			if( lines[i].length() != x2 )
				throw new IllegalArgumentException(
						"The strings are not of equal length.");
	}
	
	
	/**
	 * Finds each element present on the scheme,
	 * identifies it with certain type,
	 * and places the element to the corresponding set
	 * in the field {@code positions}.
	 * 
	 * @throws 	IllegalArgumentException if the scheme contains improper symbols.
	 * 			<p>
	 * 			Note that after successful scheme initialization
	 * 			there is no need to determine its characters types using
	 * 			comparation with {@code CharTable.LETTERS}. One can just
	 * 			use comparation with dots, lines, and spaces instead.
	 */
	protected void recognizeContent()
	{
		positions = new Positions();
		
		/* Find sequentially the upper-left corner
		 * of each element on the scheme,
		 * find another end of the element,
		 * identify the element,
		 * register it.
		 */
		
		//A non-trivial order of visiting cells of the scheme is possible.
		boolean[][] visited = new boolean[x2][y2];
		
		for (int y = y1; y < y2; y++)
			for (int x = x1; x < x2; x++) {
				if( visited[x][y] || charAt(x,y) == ' ' )  continue;
				visited[x][y] = true;
				trackElementStartingAt(x,y, visited);
			}
	}
	
	/**
	 * Recognizes the element containing the specified position
	 * (if such element exists).
	 */
	private void trackElementStartingAt(int x, int y, boolean[][] visited)
	{
		char c = charAt(x,y);
		if( c == '-' )
			positions.hLines.add( trackHLineStartingAt(x,y,visited) );
		else if( c == '|' )
			positions.vLines.add( trackVLineStartingAt(x,y,visited) );
		else if( isLetter(c) )
			positions.components.add( trackComponentStartingAt(x,y,visited) );
		else if( c == '.' )
			positions.gaps.add( trackGapStartingAt(x,y,visited) );
		else if( c == '+' ) {
			boolean startsHline = (firstNonPlusOnTheRightFrom(x,y) == '-');
			boolean startsVline = (firstNonPlusDownFrom(x,y) == '|');
			if( startsHline )
				positions.hLines.add( trackHLineStartingAt(x,y,visited) );
			if( startsVline )
				positions.vLines.add( trackVLineStartingAt(x,y,visited) );
			else if( !startsHline )
				throw new IllegalArgumentException(
						"Unrecognizable construction found in the scheme"
						+ " under position (" + x + "," + y + ")");
		} else throwWrongSymbolException(x,y,c);
	}
	
	private HWordPosition trackHLineStartingAt(
			int x, int y,
			boolean[][] visited )
	{
		//retain x and move x to the end of the line:
		int startX = x;
		char c;
		for (x = startX+1; x < x2; x++) {
			c = charAt(x,y);
			if( c == '-' || c == '+' )  //the line continues
				visited[x][y] = true;
			else if( c==' ' || c=='|' || c=='.' || isLetter(c) )  //line end
				return new HWordPosition(startX,x, y);
			else throwWrongSymbolException(x,y,c);
		}
		return new HWordPosition(startX,x, y);
	}
	
	private VWordPosition trackVLineStartingAt(
			int x, int y,
			boolean[][] visited )
	{
		int startY = y;
		//just in case check for pluses above:
		for (; startY - 1 >= y1 && charAt(x, startY - 1) == '+'; startY--) ;
		//retain y as startY and move y to the end of the line:
		char c;
		for (y = startY+1; y < y2; y++) {
			c = charAt(x,y);
			if( c == '|' || c == '+' )  //the line continues
				visited[x][y] = true;
			else if( c==' ' || c=='-' || c=='.' || isLetter(c) )  //line end
				return new VWordPosition(x, startY,y);
			else throwWrongSymbolException(x,y,c);
		}
		return new VWordPosition(x, startY,y);
	}
	
	private HWordPosition trackComponentStartingAt(
			int x, int y,
			boolean[][] visited )
	{
		//retain x as startX and move x to the end of the component:
		int startX = x;
		char c;
		for (x = startX+1; x < x2; x++) {
			c = charAt(x,y);
			if( isLetter(c) )  //the component continues
				visited[x][y] = true;
			else if( c == ' ' || isSplitter(c) )  //component finished
				return new HWordPosition(startX,x, y);
			else throwWrongSymbolException(x,y,c);
		}
		return new HWordPosition(startX,x, y);
	}

	private HWordPosition trackGapStartingAt(
			int x, int y,
			boolean[][] visited )
	{
		//retain x as startX and move x to the end of the gap:
		int startX = x;
		char c;
		for (x = startX + 1; x < x2; x++) {
			c = charAt(x,y);
			if( isWordChar(c) )  //the gap continues
				visited[x][y] = true;
			else if( c == '-' || c == '+' || c == ' ' )  //gap finished
				return new HWordPosition(startX,x, y);
			else throwWrongSymbolException(x,y,c);
		}
		return new HWordPosition(startX,x, y);
	}
	
	private char firstNonPlusOnTheRightFrom(int x, int y)
	{
		char c;
		for (; x < x2; x++) {
			c = charAt(x,y);
			if( c != '+' )  return c;
		}
		return ' ';
	}
	
	private char firstNonPlusDownFrom(int x, int y)
	{
		char c;
		for (; y < y2; y++) {
			c = charAt(x,y);
			if( c != '+' )  return c;
		}
		return ' ';
	}
	
	private void throwWrongSymbolException(int x, int y, char c)
	{
		throw new IllegalArgumentException(
				"A wrong symbol \'" + c + "\' was found on the scheme"
				+ " under position (" + x + "," + y + ")");
	}
	
	/**
	 * Initializes the sets {@code names.components} and {@code names.gaps}
	 * and the mapping {@code positionsToNames}
	 * using the positions of all these elements on the scheme,
	 * which are considered to be initialized earlier.
	 */
	protected void extractNamesFromPositions()
	{
		names = new Names();
		positionsToNames = new HashMap<HWordPosition, String>();
		
		String name;
		
		for (HWordPosition wp: positions.components) {
			name = textAt(wp);
			registerComponentName(name);
			positionsToNames.put(wp,name);
		}
		for (HWordPosition wp : positions.gaps) {
			name = textAt(wp);
			registerGapName(name);
			positionsToNames.put(wp,name);
		}
	}
	
	private void registerComponentName(String name)
	{
		if( !names.components.add( name ) )
			throw new IllegalArgumentException(
					"cannot register the component name \"" + name + "\"");
	}
	private void registerGapName(String name)
	{
		names.gaps.add( name );
	}
	
	/**
	 * Initializes the variables
	 * {@code hWordsAtLines} and {@code vWordsAtLines}
	 * which are used in the searching methods
	 * {@code horizontalElementAt()} and {@code verticalLineAt()}.
	 */
	protected void organizeFastElementsSearch()
	{
		hWordsAtYs =
			new HashMap<Integer, HashSet<HWordPosition>>(lines.length);
		for (int i = 0; i < lines.length; i++)
			hWordsAtYs.put(i, new HashSet<HWordPosition>());
		
		distributeHWordsByLines(positions.components);
		distributeHWordsByLines(positions.gaps);
		distributeHWordsByLines(positions.hLines);
		
		vWordsAtXs = new HashMap<Integer, HashSet<VWordPosition>>();
		
		distributeVWordsByLines(positions.vLines);
	}
	
	/**
	 * Puts the references at each word position in the specified
	 * set {@code from} into the corresponding values of the map
	 * {@code hWordsAtLines}.
	 * 
	 * @param from	the set of positions to be distributed.
	 */
	private void distributeHWordsByLines(Set<HWordPosition> from)
	{
		for (HWordPosition wp : from) {
			hWordsAtYs.get( new Integer(wp.y) ).add(wp);
		}
	}
	/**
	 * Puts the references at each word position in the specified
	 * set {@code from} into the corresponding values of the map
	 * {@code vWordsAtLines}.
	 * 
	 * @param from	the set of positions to be distributed.
	 */
	private void distributeVWordsByLines(Set<VWordPosition> from)
	{
		Integer key;
		for (VWordPosition wp : from) {
			key = new Integer(wp.x);
			if( ! vWordsAtXs.containsKey(key) )
				vWordsAtXs.put(key, new HashSet<VWordPosition>());
			vWordsAtXs.get( key ).add(wp);
		}
	}
	
	/**
	 * Investigates, which gaps must be considered as belonging to lines;
	 * then modifies the corresponding lines' positions.
	 * <p>
	 * This method initializes the field {@code gapsAtLines}
	 * and influences the fields
	 * {@code positions.hLines}, {@code positions.vLines}
	 * {@code hWordsAtYs}, and {@code vWordsAtXs}.
	 */
	protected void recognizeGapsAtLines()
	{
		gapsAtLines = new HashMap<WordPosition, HWordPosition>();
		
		recognizeHorizontalGapsAtLines();
		recognizeVerticalGapsAtLines();
	}
	
	protected void recognizeHorizontalGapsAtLines()
	{
		HashSet<HWordPosition> hLinesToRemove = new HashSet<HWordPosition>();
		HashSet<HWordPosition> hLinesToAdd = new HashSet<HWordPosition>();
		HWordPosition gapAtLine = null;
		//Searching:
		
		Iterator<HWordPosition> i = positions.hLines.iterator();
		HWordPosition hLine;
		while( i.hasNext() ) {
			hLine = i.next();
			if( hLinesToRemove.contains(hLine) ) //already visited.
				continue;
			
			int newX1 = hLine.x1;
			HWordPosition leftGap = gapOnTheLeftFrom(hLine);
			if( leftGap != null ) {
				newX1 = leftGap.x1;
				gapAtLine = leftGap;
				hLinesToRemove.add(hLine); //the current line will be changed.
				
				HWordPosition leftLine = hLineOnTheLeftFrom(leftGap);
				if( leftLine != null &&
						positions.hLines.contains(leftLine) &&
						! hLinesToRemove.contains(leftLine) ) {
					newX1 = leftLine.x1;
					hLinesToRemove.add(leftLine); //the line will be changed.
					
					if( gapOnTheLeftFrom(leftLine) != null )
						throwMultipleGapsAtYException(hLine.y);
				}
			}
			
			int newX2 = hLine.x2;
			HWordPosition rightGap = gapOnTheRightFrom(hLine);
			if( rightGap != null ) {
				if( leftGap != null ) //this is not the first gap.
					throwMultipleGapsAtYException(hLine.y);
				newX2 = rightGap.x2;
				gapAtLine = rightGap;
				hLinesToRemove.add(hLine); //the current line will be changed.
				
				HWordPosition rightLine = hLineOnTheRightFrom(rightGap);
				if( rightLine != null &&
						positions.hLines.contains(rightLine) &&
						! hLinesToRemove.contains(rightLine) ) {
					newX2 = rightLine.x2;
					hLinesToRemove.add(rightLine); //the line will be changed.
					
					if( gapOnTheRightFrom(rightLine) != null )
						throwMultipleGapsAtYException(hLine.y);
				}
			}
			
			if( newX1 != hLine.x1 || newX2 != hLine.x2 ) {
				HWordPosition
					newLine = new HWordPosition(newX1,newX2,hLine.y);
				hLinesToAdd.add(newLine);
				gapsAtLines.put(newLine,gapAtLine);
			}
		}
		
		//Applying the changes:
		
		for (HWordPosition line : hLinesToRemove) {
			positions.hLines.remove(line);
			hWordsAtYs.get(line.y).remove(line);
		}
		for (HWordPosition line : hLinesToAdd) {
			positions.hLines.add(line);
			hWordsAtYs.get(line.y).add(line);
		}
	}
	
	private HWordPosition gapOnTheLeftFrom(HWordPosition hLine)
	{
		CharPosition leftPos = hLine.getStart();
		return gapAt(leftPos.x-1,leftPos.y);
	}
	private HWordPosition hLineOnTheLeftFrom(HWordPosition hLine)
	{
		CharPosition leftPos = hLine.getStart();
		return anyHElementAt(leftPos.x-1,leftPos.y);
	}
	private HWordPosition gapOnTheRightFrom(HWordPosition hLine)
	{
		return  gapAt( hLine.getEnd() );
	}
	private HWordPosition hLineOnTheRightFrom(HWordPosition hLine)
	{
		CharPosition rightPos = hLine.getEnd();
		return anyHElementAt(rightPos.x,rightPos.y);
	}
	
	private void throwMultipleGapsAtYException(int y)
	{
		throw new IllegalArgumentException(
				"Multiple gaps on a horisontal line" +
				" at y=" + y + " found.");
	}
	
	protected void recognizeVerticalGapsAtLines()
	{
		HashSet<VWordPosition> vLinesToRemove = new HashSet<VWordPosition>();
		HashSet<VWordPosition> vLinesToAdd = new HashSet<VWordPosition>();
		HWordPosition gapAtLine = null;
		//Searching:
		
		Iterator<VWordPosition> i = positions.vLines.iterator();
		VWordPosition vLine;
		while( i.hasNext() ) {
			vLine = i.next();
			if( vLinesToRemove.contains(vLine) ) //already visited.
				continue;
			
			int newY1 = vLine.y1;
			HWordPosition topGap = gapAbove(vLine);
			if( topGap != null ) {
				newY1 = topGap.y;
				gapAtLine = topGap;
				vLinesToRemove.add(vLine); //the current line will be changed.
				
				VWordPosition topLine = vLineAbove(vLine);
				if( topLine != null &&
						positions.vLines.contains(topLine) &&
						! vLinesToRemove.contains(topLine) ) {
					newY1 = topLine.y1;
					vLinesToRemove.add(topLine); //the line will be changed.
					
					if( gapAbove(topLine) != null )
						throwMultipleGapsAtXException(vLine.x);
				}
			}
			
			int newY2 = vLine.y2;
			HWordPosition bottomGap = gapBelow(vLine);
			if( bottomGap != null ) {
				if( topGap != null ) //this is not the first gap.
					throwMultipleGapsAtXException(vLine.x);
				newY2 = bottomGap.y + 1;
				gapAtLine = bottomGap;
				vLinesToRemove.add(vLine); //the current line will be changed.
				
				VWordPosition bottomLine = vLineBelow(vLine);
				if( bottomLine != null &&
						positions.vLines.contains(bottomLine) &&
						! vLinesToRemove.contains(bottomLine) ) {
					newY2 = bottomLine.y2;
					vLinesToRemove.add(bottomLine); //the line will be changed.
					
					if( gapBelow(bottomLine) != null )
						throwMultipleGapsAtXException(vLine.x);
				}
			}
			
			if( newY1 != vLine.y1 || newY2 != vLine.y2 ) {
				VWordPosition
					newLine = new VWordPosition(vLine.x,newY1,newY2);
				vLinesToAdd.add(newLine);
				gapsAtLines.put(newLine,gapAtLine);
			}
		}
		
		//Applying the changes:
		
		//removing:
		for (VWordPosition line : vLinesToRemove) {
			positions.vLines.remove(line);
			vWordsAtXs.get(line.x).remove(line);
		}
		//adding:
		for (VWordPosition line : vLinesToAdd) {
			positions.vLines.add(line);
			vWordsAtXs.get(line.x).add(line);
		}
	}
	
	private HWordPosition gapAbove(VWordPosition vLine)
	{
		CharPosition topPos = vLine.getStart();
		return gapAt(topPos.x,topPos.y-1);
	}
	/* 
	 * Returns the vertical line lying above the specified line
	 * and the gap lying above it.
	 */
	private VWordPosition vLineAbove(VWordPosition vLine)
	{
		CharPosition topPos = vLine.getStart();
		return verticalLineAt(topPos.x,topPos.y-2);
	}
	private HWordPosition gapBelow(VWordPosition vLine)
	{
		return  gapAt( vLine.getEnd() );
	}
	/* 
	 * Returns the vertical line lying below the specified line
	 * and the gap lying below it.
	 */
	private VWordPosition vLineBelow(VWordPosition vLine)
	{
		CharPosition bottomPos = vLine.getEnd();
		return verticalLineAt(bottomPos.x,bottomPos.y+1);
	}
	
	private void throwMultipleGapsAtXException(int x)
	{
		throw new IllegalArgumentException(
				"Multiple gaps on a vertical line" +
				" at x=" + x + " found.");
	}
	
	/**
	 * Verifies whether the scheme contains the specified component name.
	 */
	public boolean containsComponentName(String name)
	{
		return names.components.contains(name);
	}
	
	/**
	 * Verifies whether the scheme contains the specified gap denotation.
	 */
	public boolean containsGapName(String name)
	{
		return names.gaps.contains(name);
	}
	
	/**
	 * Returns the symbol lying on the scheme under the specified position.
	 */
	public char charAt(int x, int y)
	{
		try {
			return lines[y].charAt(x);
		} catch (RuntimeException re) {
			throw new IllegalArgumentException("char position out of table");
		}
	}
	
	/**
	 * Returns the text lying on the scheme
	 * specified by the given word position coordinates.
	 */
	public String textAt(int x1, int x2, int y)
	{
		try {
			return lines[y].substring(x1, x2);
		} catch (RuntimeException re) {
			throw new IllegalArgumentException("Improper subline coodinates.");
		}
	}
	
	/**
	 * Returns an unmodifiable set of all positions
	 * of horizontal lines on the scheme.
	 */
	public Set<HWordPosition> getHLinesPositions()
	{
		return Collections.unmodifiableSet( positions.hLines );
	}
	
	/**
	 * Returns an unmodifiable set of all positions
	 * of vertical lines on the scheme.
	 */
	public Set<VWordPosition> getVLinesPositions()
	{
		return Collections.unmodifiableSet( positions.vLines );
	}
	
	/**
	 * Finds and returns (the position of) a horizontal element
	 * which contains the specified point.
	 * If such element does not exist, returns null.
	 * <p>
	 * Note that some points may lie at horizontal lines
	 * and at gaps belonging to that lines simultaneously.
	 * In such cases the method returns any of the two elements.
	 */
	public HWordPosition anyHElementAt(int x, int y)
	{
		if( ! contains(x,y) )
			return null;
		char c = charAt(x,y);
		if( c == ' '  || c == '|' )
			return null;
		
		Set<HWordPosition> h =  hWordsAtYs.get(new Integer(y));
		if( h != null )
			for (HWordPosition hwp : h)
				if( hwp.contains(x,y) )
					return hwp;
		
		return null;
	}
	public HWordPosition anyHElementAt(CharPosition pos) {
		return anyHElementAt(pos.x,pos.y);
	}
	
	/**
	 * Finds and returns (the position of) the gap
	 * containing the specified point
	 * or null if such gap does not exist on the scheme.
	 */
	public HWordPosition gapAt(int x, int y)
	{
		if( ! contains(x,y) )
			return null;
		char c = charAt(x,y);
		if( c == ' '  || c == '|' )
			return null;
		
		Set<HWordPosition> h =  hWordsAtYs.get(new Integer(y));
		if( h != null )
			for (HWordPosition hwp : h)
				if( hwp.contains(x,y) && positions.gaps.contains(hwp) )
					return hwp;
		
		return null;
	}
	public HWordPosition gapAt(CharPosition pos) {
		return gapAt(pos.x,pos.y);
	}
	
	/**
	 * Finds and returns (the position of) the vertical line
	 * which contains the specified point
	 * or null if such line does not exist.
	 */
	public VWordPosition verticalLineAt(int x, int y)
	{
		if( ! contains(x,y) )
			return null;
		char c = charAt(x,y);
		if( c == ' '  || c == '-' )
			return null;
		
		Set<VWordPosition> v =  vWordsAtXs.get(new Integer(x));
		if( v != null )
			for (VWordPosition vwp : v)
				if( vwp.contains(x,y) )
					return vwp;
		
		return null;
	}
	public VWordPosition verticalLineAt(CharPosition pos) {
		return verticalLineAt(pos.x,pos.y);
	}
	
	/**
	 * Returns the gap belonging to the specified horizontal or vertical line
	 * or null if such gap does not exist.
	 */
	public HWordPosition gapAtLine(WordPosition line)
	{
		if( gapsAtLines == null )  return null;
		return gapsAtLines.get(line);
	}
	
	public String toString()
	{
		StringBuilder result =
			new StringBuilder( (lines[0].length() + 1) * lines.length );
		for (int i = 0; i < lines.length; i++)
			result.append( lines[i] + '\n' );
		return result.toString();
	}
}

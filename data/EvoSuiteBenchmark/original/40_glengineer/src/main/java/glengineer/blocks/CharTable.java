package glengineer.blocks;

import glengineer.positions.*;


/**
 * An abstract class for the {@code Scheme} and {@code Block} classes.
 * Encapsulates various operations dealing with table boundaries,
 * operations classifying the type of text under specified positions,
 * and operations searching for text of specified type.
 * <p>
 * The basic text possessing operations are leaved abstract.
 */
public abstract class CharTable
{
	/**
	 * The string declares the set of characters
	 * which are allowed in the names of components.
	 */
	public static final String LETTERS =
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_1234567890";
	
	/**
	 * The string of elements for drawing horizontal and vertical lines.
	 */
	public static final String SPLITTERS = "-|+";
	
	/**
	 * This table left boundary coordinate (inclusive).
	 */
	public int x1;
	/**
	 * This table top boundary coordinate (inclusive).
	 */
	public int y1;
	/**
	 * This table right boundary coordinate (exclusive).
	 */
	public int x2;
	/**
	 * This table bottom boundary coordinate (exclusive).
	 */
	public int y2;
	
	
	public CharPosition1 getPos1() {
		return new CharPosition1(x1,y1);
	}
	public CharPosition2 getPos2() {
		return new CharPosition2(x2,y2);
	}
	
	/**
	 * Verifies whether the specified value
	 * is a proper x-coordinate for an element of this char table.
	 */
	public boolean permitsX(int x) {
		if( x1 <= x && x < x2 )  return true;
		else  return false;
	}
	/**
	 * Verifies whether the specified value
	 * is a proper y-coordinate for an element of this char table.
	 */
	public boolean permitsY(int y) {
		if( y1 <= y && y < y2 )  return true;
		else  return false;
	}
	/**
	 * Verifies whether the specified character position
	 * lies inside of this char table.
	 */
	public boolean contains(int x, int y) {
		return permitsX(x) && permitsY(y);
	}
	/**
	 * Verifies whether the specified character position
	 * lies inside of this char table.
	 */
	public boolean contains(CharPosition pos) {
		return permitsX(pos.x) && permitsY(pos.y);
	}
	
	public boolean isTrivial() {
		return (x1 >= x2) || (y1 >= y2);
	}
	
	/**
	 * ћетод возвращает символ таблицы, расположенный
	 * по координатам {@code x} и {@code y}.
	 * 
	 * @param x	координата по горизонтали.
	 * @param y	координата по вертикали.
	 * @return	соответствующий этим координатам символ.
	 */
	public abstract char charAt(int x, int y);
	/**
	 * ћетод возвращает символ таблицы, расположенный в {@code p}.
	 */	
	public char charAt(CharPosition p) {
		return charAt(p.x,p.y);
	}
	/**
	 * Verifies whether the specified character can be a part of a word
	 * (say, is a letter).
	 * 
	 * @param c	character
	 */
	public static boolean isLetter(char c) {
		return LETTERS.indexOf( c ) > -1;
	}
	
	public boolean letterAt(int x, int y) {
		return isLetter( charAt(x,y) );
	}
	
	/**
	 * Verifies whether the specified symbol is a word character,
	 * i.e. is a letter or a dot.
	 * 
	 * @param c	symbol
	 */
	public static boolean isWordChar(char c) {
		return c == '.' || LETTERS.indexOf( c ) > -1;
	}
	/**
	 * Verifies whether the symbol at the specified position {@code (x,y)}
	 * is a word character, i.e. is a letter or a dot.
	 * 
	 * @param x	x-coordinate
	 * @param y	y-coordinate
	 * @return	{@code true} if the symbol under position {@code (x,y)}
	 * 			is a word character
	 */
	public boolean wordCharAt(int x, int y) {
		return  isWordChar( charAt(x,y) );
	}
	
	/**
	 * ѕровер€ет, €вл€етс€ ли данный символ частью разделительной линии.
	 * @param c	символ.
	 */
	public static boolean isSplitter(char c) {
		return SPLITTERS.indexOf( c ) > -1;
	}
	
	/**
	 * ѕровер€ет, €вл€етс€ ли символ таблицы, расположенный в точке
	 * {@code (x,y)}, частью разделительной линии.
	 */
	public boolean splitterAt(int x, int y) {
		return isSplitter( charAt(x,y) ); 
	}
			
	/**
	 * Returns the text on the table
	 * specified by the given horizontal word position coordinates.
	 * 
	 * @param x1	the number of the first symbol
	 * 				of the required word on the table.
	 * @param x2	the number of the last symbol
	 * 				of the required word on the table + 1.
	 * @param y		the number of the line with the required word.
	 * 
	 * @return		the text lying at the specified horizontal word position.
	 */
	public abstract String textAt(int x1, int x2, int y);
	
	/**
	 * Returns the text lying at the specified word position on the table.
	 * 
	 * @param wp	the position of the word to be returned.
	 * 
	 * @return		the text lying at the specified word position.
	 */
	public String textAt(WordPosition wp)
	{
		if( wp == null )
			throw new IllegalArgumentException("Null word position.");
		return wp.textAt(this);
	}
	/**
	 * Returns the text lying at the specified
	 * horizontal word position on the table.
	 * 
	 * @param hwp	the position of the horizontal word to be returned.
	 * 
	 * @return		the text lying at the specified word position.
	 */
	public String textAt(HWordPosition hwp)
	{
		return textAt(hwp.x1, hwp.x2, hwp.y);
	}
	
	/**
	 * Returns the text lying at the specified
	 * vertical word position on the table.
	 * 
	 * @param vwp	the position of the vertical word to be returned.
	 * 
	 * @return		the text lying at the specified word position.
	 */
	public String textAt(VWordPosition vwp)
	{
		StringBuilder result = new StringBuilder( "(vertical) " );
		for (int y = vwp.y1; y < vwp.y2; y++)
			result.append(charAt(vwp.x,y));
		return result.toString();
	}
	
	/**
	 * Optimizes the boundaries by moving them close to the contained
	 * components and gaps.
	 * <p>
	 * If the optimized table remains non-trivial, it definitely contains
	 * some components or gaps.
	 */
	public void optimize() {
		optimizeX1();
		optimizeX2();
		optimizeY1();
		optimizeY2();
	}
	private void optimizeX1() {
		for (; x1 < x2 && columnContainsNoWordChars(x1); ) x1++;
	}
	private void optimizeX2() {
		for (; x1 < x2 && columnContainsNoWordChars(x2-1); ) x2--;
	}
	private void optimizeY1() {
		for (; y1 < y2 && rowContainsNoWordChars(y1); ) y1++;
	}
	private void optimizeY2() {
		for (; y1 < y2 && rowContainsNoWordChars(y2-1); ) y2--;
	}
	/**
	 * Verifies that the column under the specified number
	 * does not contain word characters.
	 */
	private boolean columnContainsNoWordChars(int x) {
		for (int y=y1; y<y2; y++)
			if( wordCharAt(x,y) )  return false;
		return true;
	}
	/**
	 * Verifies that the row under the specified number
	 * does not contain word characters.
	 */
	private boolean rowContainsNoWordChars(int y) {
		for (int x=x1; x<x2; x++)
			if( wordCharAt(x,y) )  return false;
		return true;
	}
	
	/**
	 * Returns (position of) the first horizontal word in this char table;
	 * the direction of searching is rightwards-downwards (say, horizontal).
	 */
	public HWordPosition firstWord_Horizontally() {
		return firstWordAfter_Horizontally( getPos1() );
	}
	
	/**
	 * Returns (position of) the first horizontal word after the
	 * specified word {@code wp}; the direction of searching
	 * is rightwards-downwards (say, horizontal).
	 * 
	 * If not found, returns null.
	 */
	public HWordPosition firstWordAfter_Horizontally(HWordPosition wp) {
		return firstWordAfter_Horizontally( wp.getEnd() );
	}
	
	/**
	 * Returns (position of) the first horizontal word after the
	 * specified position {@code p} (inclusive);
	 * the direction of searching is rightwards-downwards (say, horizontal).
	 * 
	 * If not found, returns null.
	 */
	private HWordPosition firstWordAfter_Horizontally(CharPosition p)
	{
		CharPosition wordStart = firstWordCharAfter_Horizontally(p);
		if( wordStart == null ) //word characters not found.
			return null;
		//wordStart is the character found.
		return new HWordPosition(
				wordStart,
				firstNonWordCharOnTheRightOf(wordStart) );
	}
	
	/**
	 * Returns the position of the first word character after the
	 * specified position {@code p} (inclusive);
	 * the direction of searching is rightwards-downwards (say, horizontal).
	 * 
	 * If not found, returns null.
	 */
	private CharPosition firstWordCharAfter_Horizontally(CharPosition p)
	{
		int x = p.x;
		int y = p.y;
		//search to the horizontal line end:
		for (x = p.x; x < x2; x++)
			if( wordCharAt(x,y) )  return new CharPosition(x,y);
		//since not found, search in other horizontal lines:
		for (y++; y < y2; y++)
			for (x = x1; x < x2; x++)
				if( wordCharAt(x,y) )
					return new CharPosition(x,y);
		return null;
	}
	
	/**
	 * Returns the x-coordinate of the first symbol which lies on the right
	 * from the specified position {@code p} and is not a word character.
	 * 
	 * If not found, returns {@code x2}.
	 */
	private int firstNonWordCharOnTheRightOf(CharPosition p) {
		int x = p.x + 1;
		int y = p.y;
		for (; x < x2 && wordCharAt(x,y); x++) ;
		return x;
	}
	
	/**
	 * Returns (position of) the first horizontal word in this char table;
	 * the direction of searching is leftwards-upwards (say, horizontal).
	 * 
	 * If not found, returns null.
	 */
	public HWordPosition lastWord_Horizontally()
	{
		CharPosition wordEnd = lastWordChar_Horizontally();
		if( wordEnd == null )
			return null;
		return new HWordPosition(
				lastWordCharOnTheLeftOf(wordEnd), wordEnd );
	}
	
	/**
	 * Returns the position of the first letter + 1
	 * when the search direction is leftwards-upwards (say, horizontal)
	 * or null if not found.
	 */
	private CharPosition lastWordChar_Horizontally() {
		int x = x2 - 1;
		int y = y2 - 1;
		for (; y >= y1; y--)
			for (x = x2-1; x >= x1; x--)
				if( wordCharAt(x,y) )
					return new CharPosition(x+1,y);
		return null;
	}
	
	/**
	 * Returns the x-coordinate of the last word character
	 * of a continuous sequence of word characters
	 * starting at the specified position {@code p} and going leftwards.
	 */
	private int lastWordCharOnTheLeftOf(CharPosition p) {
		int x = p.x - 1;
		int y = p.y;
		for (; x >= x1 && wordCharAt(x,y); x--) ;
		return x+1;
	}
	
	
	/**
	 * Returns (position of) the first horizontal word in this char table;
	 * the direction of searching is downwards-rightwards (say, vertical).
	 * If not found, returns null.
	 */
	public HWordPosition firstWord_Vertically()
	{
		CharPosition nextWordCharPos = 
			firstWordCharVertically();
		
		if( nextWordCharPos != null )
			return new HWordPosition(
					nextWordCharPos,
					firstNonWordCharOnTheRightOf(nextWordCharPos) );
		else return null;
	}
	
	/**
	 * Returns the position of the first word character;
	 * the direction of searching is downwards-rightwards (say, vertical).
	 * 
	 * If not found, returns null.
	 */
	private CharPosition firstWordCharVertically()
	{
		for (int x = x1; x < x2; x++)
			for (int y = y1; y < y2; y++)
				if( wordCharAt(x,y) )
					return new CharPosition(x,y);
		return null;
	}
	
	/**
	 * Returns (position of) the first horizontal word after the
	 * specified word {@code wp}; the direction of searching
	 * is downwards-rightwards (say, vertical).
	 * If not found, returns null.
	 * 
	 * @throws IllegalArgumentException if the specified word position is null.
	 */
	public HWordPosition firstWordAfter_Vertically(HWordPosition wp)
	{
		if( wp == null )
			throw new IllegalArgumentException(
					"Can not search for the word following the null word.");
		
		CharPosition nextWordCharPos =
			firstWordCharAfter_Vertically( wp.getStart() );
		
		while ( nextWordCharPos != null && this.contains(nextWordCharPos) ) {
			if( ! wp.contains(nextWordCharPos) ) //this is a new word
				return new HWordPosition(
						nextWordCharPos,
						firstNonWordCharOnTheRightOf(nextWordCharPos) );
			nextWordCharPos =
				firstWordCharAfter_Vertically( nextWordCharPos );
		}
		
		return null;
	}
	
	/**
	 * Returns the position of the first word character after the
	 * specified position {@code p} (inclusive);
	 * the direction of searching is downwards-rightwards (say, vertical).
	 * 
	 * If not found, returns null.
	 */
	private CharPosition firstWordCharAfter_Vertically(CharPosition p)
	{
		int x = p.x;
		int y = p.y;
		//search to the vertical line end:
		for (y++; y < y2; y++)
			if( wordCharAt(x,y) )  return new CharPosition(x,y);
		//since not found, search in other vertical lines:
		for (x++; x < x2; x++)
			for (y = y1; y < y2; y++)
				if( wordCharAt(x,y) )
					return new CharPosition(x,y);
		return null;
	}
	
}

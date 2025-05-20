package glengineer.blocks;

import glengineer.positions.*;

import java.util.*;


/**
 * Represents a rectangular area on a scheme.
 * This is an abstract class for its two concrete descendants,
 * {@code HorizontalBlock} and {@code VerticalBlock},
 * which are used for dealing with
 * the horizontal and the vertical layouts, respectively.
 */
public abstract class Block  extends CharTable
{
	/**
	 * Схема контейнера, на которой выделяется {@code Block}.
	 */
	protected Scheme scheme;
	
	/**
	 * Создаёт блок данной схемы с данными координатами начальной
	 * и конечной позиций.
	 */
	public Block(Scheme scheme, int x1, int y1, int x2, int y2)
	{
		if( x1 < 0 || y1 < 0 || x1 > x2 || y1 > y2 ||
				x2 > scheme.x2 || y2 > scheme.y2 )
			throw new IllegalArgumentException( "improper block definition" +
					": " + x1 + "," + y1 + ", " + x2 + "," + y2 + "." );
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.scheme = scheme;
	}
	public Block(Scheme scheme, CharPosition p1, CharPosition p2) {
		this(scheme, p1.x,p1.y, p2.x,p2.y);
	}
	
	/**
	 * Создаёт блок данной схемы, по размеру совпадающий со всей схемой. 
	 */
	public Block(Scheme scheme) {
		x1 = 0;
		y1 = 0;
		x2 = scheme.x2;
		y2 = scheme.y2;
		this.scheme = scheme;
	}		
	
	/**
	 * Создаёт и возвращает подблок, имеющий указанные координаты.
	 * @param x1	{@code x}-координата начала нового подблока.
	 * @param y1	{@code y}-координата начала нового подблока.
	 * @param x2	{@code x}-координата конца нового подблока.
	 * @param y2	{@code y}-координата конца нового подблока.
	 * @return		новый подблок.
	 */
	public abstract Block subblock(int x1, int y1, int x2, int y2) ;
	
	/**
	 * Метод возвращает символ, расположенный
	 * по координатам {@code x} и {@code y}.
	 * 
	 * @param x	координата по горизонтали.
	 * @param y	координата по вертикали.
	 * 
	 * @return	соответствующий этим координатам символ.
	 */
	public char charAt(int x, int y) {
		try {
			return scheme.lines[y].charAt(x);
		} catch (RuntimeException re) {
			throw new IllegalArgumentException(
				"char position " + new CharPosition(x,y) +
				" out of block.\n" + this );
		}
	}
	
	public String textAt(int x1, int x2, int y) {
		try {
			return scheme.lines[y].substring(x1,x2);
		} catch (RuntimeException re) {
			throw new IllegalArgumentException("Improper subline coodinates.");
		}
	}
	
	/**
	 * Verifies whether the block contains only one element
	 * (a component or a gap).
	 * 
	 * @return	{@code true} if only one or {@code false} if more than one.
	 * 			If there are no elements or the block is trivial,
	 * 			an exception is thrown.
	 */
	public boolean containsOnlyOneWord()
	{
		if( isTrivial() )
			throw new IllegalArgumentException(
					"cannot search in a trivial block" );
		HWordPosition firstWord = firstWord_Horizontally();
		if( firstWord.y == y2 ) //вообще нет компонентов.
			throw new IllegalArgumentException(
					"cannot verify unicity of an word in an empty block");
		WordPosition lastWord = lastWord_Horizontally();
		return firstWord.equals(lastWord);
	}
	
	/**
	 * Divides the current block into a set of sequential subblocks,
	 * optimizes these subblocks and returns them as a linked list.
	 */
	public abstract List<Block> extractSequentialSubblocks();
	
	/**
	 * Divides the current block into a set of parallel subblocks,
	 * optimizes these subblocks and returns them as a linked list.
	 */
	public abstract List<Block> extractParallelSubblocks();
	
	
	public String toString() {
		String result = "Block("+x1+","+y1+", "+x2+","+y2+"):\n";
		//подготовим строку для верха и низа рамки:
			int frameWidth = x2-x1 + 4;
			char[] line = new char[frameWidth];
			java.util.Arrays.fill(line,'#');
			String lineString = " " + new String(line);
		result = result.concat( lineString + "\n");
		for (int y=y1; y<y2; y++)
			result = result.concat(
					" # " + scheme.textAt(x1, x2,y) + " #\n" );
		result = result.concat( lineString );
		return result;
	}
	
	/**
	 * Returns the upper line of this block.
	 * <p>
	 * This method is used when the block represents a position
	 * of a horizontal word on a scheme.
	 */
	public String getUpperLine()
	{
		return textAt(x1,x2, y1);
	}
	
}


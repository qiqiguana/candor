package glengineer.positions;

import glengineer.blocks.Block;
import glengineer.blocks.CharTable;

/**
 * Encapsulates the position (start and end coordinates)
 * of a word lying horizontally on the scheme.
 */
public class HWordPosition extends WordPosition {

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
    }

    public HWordPosition(CharPosition start, int x2) {
    }

    public HWordPosition(int x1, CharPosition end) {
    }

    /**
     * The word position is initialized
     * representing the upper line of the specified block.
     */
    public HWordPosition(Block block) {
    }

    /**
     * Creates a trivial word position
     * which begins and ends at the specified character position.
     */
    public HWordPosition(CharPosition cp) {
    }

    /**
     * Returns the coordinates of the beginning of this word.
     */
    public CharPosition1 getStart();

    /**
     * Returns the coordinates of the ending of this word (exclusive).
     */
    public CharPosition2 getEnd();

    public int getWidth();

    public boolean equals(WordPosition wp);

    public boolean isTrivial();

    public boolean contains(int x, int y);

    public boolean liesIn(CharTable ct);

    /**
     * Verifies whether this word position intersects the specified char table.
     */
    public boolean intersects(CharTable ct);

    /**
     * Returns the width of the intersection of this line
     * with the specified block
     * or <code>0</code> if they are not intersected.
     */
    public int getLengthInsideOf(Block block);

    public String toString();

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
    public String textAt(CharTable ct);
}

package glengineer.blocks;

import glengineer.positions.*;
import java.util.*;

/**
 * An implementation of the abstract class {@code Block}
 * used for vertical layouting.
 */
public class VerticalBlock extends Block {

    public VerticalBlock(Scheme scheme, int x1, int y1, int x2, int y2) {
    }

    public VerticalBlock(Scheme scheme, CharPosition p1, CharPosition p2) {
    }

    public VerticalBlock(Scheme scheme) {
    }

    public VerticalBlock(Block b) {
    }

    public VerticalBlock(Block block, int x1, int y1, int x2, int y2) {
    }

    public VerticalBlock subblock(int x1, int y1, int x2, int y2);

    public VerticalBlock cloneWith(CharPosition1 p1);

    /**
     * Divides the current block into a set of sequential subblocks,
     * optimizes these subblocks and returns them as a linked list.
     *
     * @return	a list of optimized sequential subblocks.
     */
    public List<Block> extractSequentialSubblocks();

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
    private VerticalBlock extractFirstSequentialSubblock();

    /**
     * Returns the set of all vertical lines intersecting this block.
     */
    private Set<VWordPosition> getIntersectingVLines();

    /**
     * Finds the upper entity (an element of a word or vertical line)
     * of this block.
     * Returns its position on the scheme.
     * @throws	IllegalArgumentException if this block happens to be empty.
     */
    private CharPosition getBlockStart();

    /**
     * Verifies whether the character under the cell
     * determined by the specified coordinates
     * is a word character or a vertical line character.
     */
    private boolean entityAt(int x, int y);

    /**
     * Finds the last line of the subblock
     * containing the specified position {@code blockStart}.
     * Returns the coordinate {@code y2} of such block.
     */
    private int getUpperSubblockEnd(CharPosition blockStart, Set<VWordPosition> vLines);

    /**
     * Finds a vertical line which vertical range covers
     * the specified value {@code y}
     * and which lower point is lower than {@code y}.
     * Returns the position of the last element of such line (inclusive)
     * or {@code y} if such line does not exist.
     */
    private int y2OfAVLineCovering(int y, Set<VWordPosition> vLines);

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
    public List<Block> extractParallelSubblocks();

    /**
     * Divides the current block into a set of parallel subblocks
     * by its most long vertical lines,
     * optimizes these subblocks, and returns them as a linked list.
     */
    protected List<Block> extractParallelSubblocksSeparatedByLines(Set<VWordPosition> vLines);

    /**
     * Finds the longest lines in the specified set of vertical lines
     * and then removes from it all other elements.
     * <p>
     * Note that, more formally, this method considers the lengths
     * of <i>intersections</i> of the lines with this block.
     */
    private void removeSmallVLinesFrom(Set<VWordPosition> vLines);

    private List<Block> extractParallelSubblocksSeparatedBy(Set<VWordPosition> separators);

    /*
	 * Returns a list of the specified vertical lines
	 * sorted according to the horizontal coordinates.
	 */
    private List<VWordPosition> sortedVLines(Set<VWordPosition> vLines);

    /**
     * Divides the current block without vertical lines
     * into a set of (optimized) parallel subblocks.
     * Returns them as a linked list.
     */
    protected List<Block> extractParallelSubblocksWithoutLines();

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
    private Block extractFirstParallelSubblock();

    /**
     * Searches down and to the right from the beginning of this block
     * for the first occurrence of a word character.
     * @return	the position of the word character found.
     * @throws	IllegalArgumentException if this block
     * 			does not contain word characters.
     */
    private CharPosition firstWordChar();

    /**
     * Finds the first entity below the specified position.
     * @return	the y-coordinate of the entity found or y2 if not found.
     */
    private int firstWordCharBelow(int x, int y);

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
    public boolean wordCharAt(int x, int y);

    /**
     * Returns a word which starts not righter than the right end of the
     * {@code currentWord} but ends strictly righter
     * (or null if does not exist).
     */
    private HWordPosition findAWordExtendingSubblockToTheRightFrom(HWordPosition currentWord);
}

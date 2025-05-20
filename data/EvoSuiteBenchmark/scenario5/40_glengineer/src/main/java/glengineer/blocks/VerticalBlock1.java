package glengineer.blocks;

import glengineer.positions.*;
import java.util.*;

/**
 * An abstract class for the {@code Scheme} and {@code Block} classes.
 * Encapsulates various operations dealing with table boundaries,
 * operations classifying the type of text under specified positions,
 * and operations searching for text of specified type.
 * <p>
 * The basic text possessing operations are leaved abstract.
 */
public abstract class CharTable {

    /**
     * Optimizes the boundaries by moving them close to the contained
     * components and gaps.
     * <p>
     * If the optimized table remains non-trivial, it definitely contains
     * some components or gaps.
     */
    public void optimize();

    public boolean isTrivial();
}

/**
 * An implementation of the abstract class {@code Block}
 * used for vertical layouting.
 */
public class VerticalBlock extends Block {

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
    public List<Block> extractParallelSubblocks() {
        this.optimize();
        if (isTrivial())
            return new LinkedList<Block>();
        Set<VWordPosition> vLines = getIntersectingVLines();
        if (vLines.size() > 0)
            return extractParallelSubblocksSeparatedByLines(vLines);
        else
            return extractParallelSubblocksWithoutLines();
    }

    /**
     * Returns the set of all vertical lines intersecting this block.
     */
    private Set<VWordPosition> getIntersectingVLines();

    /**
     * Divides the current block into a set of parallel subblocks
     * by its most long vertical lines,
     * optimizes these subblocks, and returns them as a linked list.
     */
    protected List<Block> extractParallelSubblocksSeparatedByLines(Set<VWordPosition> vLines);

    /**
     * Divides the current block without vertical lines
     * into a set of (optimized) parallel subblocks.
     * Returns them as a linked list.
     */
    protected List<Block> extractParallelSubblocksWithoutLines();
}

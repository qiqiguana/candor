package glengineer.blocks;

import glengineer.positions.*;
import java.util.*;

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
}

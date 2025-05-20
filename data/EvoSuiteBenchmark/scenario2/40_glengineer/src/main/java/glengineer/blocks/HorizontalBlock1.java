package glengineer.blocks;

import glengineer.positions.*;
import java.util.*;

/**
 * An implementation of the abstract class {@code Block}
 * used for horizontal layouting.
 */
public class HorizontalBlock extends Block {

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
    public List<Block> extractParallelSubblocks() {
        this.optimize();
        if (isTrivial())
            return new LinkedList<Block>();
        Set<HWordPosition> hLines = getIntersectingHLines();
        if (hLines.size() > 0)
            return extractParallelSubblocksSeparatedByLines(hLines);
        else
            return extractParallelSubblocksWithoutLines();
    }
}

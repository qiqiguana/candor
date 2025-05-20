package glengineer.blocks;

import glengineer.positions.*;
import java.util.*;

/**
 * An implementation of the abstract class {@code Block}
 * used for vertical layouting.
 */
public class VerticalBlock extends Block {

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

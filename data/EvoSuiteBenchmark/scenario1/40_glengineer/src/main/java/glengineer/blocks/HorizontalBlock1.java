package glengineer.blocks;

import glengineer.positions.*;
import java.util.*;

/**
 * An implementation of the abstract class {@code Block}
 * used for horizontal layouting.
 */
public class HorizontalBlock extends Block {

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

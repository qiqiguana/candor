package glengineer.blocks;

import glengineer.positions.*;
import java.util.*;

/**
 * An implementation of the abstract class {@code Block}
 * used for vertical layouting.
 */
public class VerticalBlock extends Block {

    public List<Block> extractSequentialSubblocks() {
        this.optimize();
        if (isTrivial())
            return new LinkedList<Block>();
        List<Block> result = new LinkedList<Block>();
        VerticalBlock firstBlock;
        VerticalBlock theRest = this;
        do {
            firstBlock = theRest.extractFirstSequentialSubblock();
            theRest = theRest.cloneWith(new CharPosition1(this.x1, firstBlock.y2));
            firstBlock.optimize();
            if (!firstBlock.isTrivial())
                result.add(firstBlock);
            theRest.optimize();
        } while (!theRest.isTrivial());
        return result;
    }
}

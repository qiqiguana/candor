package glengineer.blocks;

import glengineer.positions.*;
import java.util.*;

/**
 * An implementation of the abstract class {@code Block}
 * used for horizontal layouting.
 */
public class HorizontalBlock extends Block {

    public List<Block> extractSequentialSubblocks() {
        this.optimize();
        if (isTrivial())
            return new LinkedList<Block>();
        List<Block> result = new LinkedList<Block>();
        Block firstBlock;
        HorizontalBlock theRest = this;
        do {
            firstBlock = theRest.extractFirstSequentialSubblock();
            theRest = theRest.cloneWith(new CharPosition1(firstBlock.x2, this.y1));
            firstBlock.optimize();
            if (!firstBlock.isTrivial())
                result.add(firstBlock);
            theRest.optimize();
        } while (!theRest.isTrivial());
        return result;
    }
}

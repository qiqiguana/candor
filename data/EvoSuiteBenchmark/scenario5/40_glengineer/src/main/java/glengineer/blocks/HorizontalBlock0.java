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
 * used for horizontal layouting.
 */
public class HorizontalBlock extends Block {

    /**
     * Divides the current block into a set of sequential subblocks,
     * optimizes these subblocks and returns them as a linked list.
     *
     * @return a list of optimized sequential subblocks.
     */
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

    /**
     * Extracts the left subblock of this block.
     * <p>
     * The required block is chosen so that its last exclusive vertical line
     * does not contain elements of components, gaps , or horizontal lines
     * starting on the left from the line.
     * <p>
     * The algorithm just finds the most left entity
     * (an element of a component, gap, or horizontal line)
     * using simple search along the scheme,
     * identifies the horizontal element containing the entity found,
     * checks whether the last exclusive vertical line of the element
     * matches the requirement for the boundary of the required block,
     * and, if does not, takes the detaining horizontal element
     * and continues the cycle.
     */
    private Block extractFirstSequentialSubblock();

    public HorizontalBlock cloneWith(CharPosition1 p1);
}

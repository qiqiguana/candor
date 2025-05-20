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

    public VerticalBlock cloneWith(CharPosition1 p1);
}

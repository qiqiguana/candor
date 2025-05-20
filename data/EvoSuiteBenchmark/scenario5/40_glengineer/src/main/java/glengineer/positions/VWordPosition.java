package glengineer.positions;

import glengineer.blocks.Block;
import glengineer.blocks.CharTable;

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
     * Returns the text lying at the specified
     * vertical word position on the table.
     *
     * @param vwp the position of the vertical word to be returned.
     * @return the text lying at the specified word position.
     */
    public String textAt(VWordPosition vwp);
}

/**
 * Encapsulates the position (start and end coordinates)
 * of a word lying vertically on the scheme.
 */
public class VWordPosition extends WordPosition implements Comparable<VWordPosition> {

    /**
     * Returns the text at this position on the specified char table.
     *
     * The call is dispatched back to the specified char table
     * informing it about the concrete word position type.
     *
     * @param ct the char table from which the text must be read.
     * @return the text at this position on the specified char table.
     */
    public String textAt(CharTable ct) {
        return ct.textAt(this);
    }
}

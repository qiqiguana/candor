package glengineer.positions;

import glengineer.blocks.Block;
import glengineer.blocks.CharTable;

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
    public String textAt(CharTable ct);
}

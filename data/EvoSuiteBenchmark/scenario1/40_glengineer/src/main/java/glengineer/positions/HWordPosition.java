package glengineer.positions;

import glengineer.blocks.Block;
import glengineer.blocks.CharTable;

/**
 * Encapsulates the position (start and end coordinates)
 * of a word lying horizontally on the scheme.
 */
public class HWordPosition extends WordPosition {

    public String textAt(CharTable ct) {
        return ct.textAt(this);
    }
}

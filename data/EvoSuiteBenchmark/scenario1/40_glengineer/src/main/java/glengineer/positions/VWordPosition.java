package glengineer.positions;

import glengineer.blocks.Block;
import glengineer.blocks.CharTable;

/**
 * Encapsulates the position (start and end coordinates)
 * of a word lying vertically on the scheme.
 */
public class VWordPosition extends WordPosition implements Comparable<VWordPosition> {

    public String textAt(CharTable ct) {
        return ct.textAt(this);
    }
}

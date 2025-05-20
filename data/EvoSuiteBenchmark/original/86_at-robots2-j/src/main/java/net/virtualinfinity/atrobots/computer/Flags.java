package net.virtualinfinity.atrobots.computer;

/**
 * Wrapper for CPU flags.
 *
 * @author Daniel Pitts
 */
public class Flags {
    private final MemoryCell flagCell;
    private static final short EQUAL_BIT_MASK = 1;
    private static final short LESS_BIT_MASK = 2;
    private static final short GREATER_BIT_MASK = 4;
    private static final short ZERO_BIT_MASK = 8;
    private static final short RESET_MASK = (short) 0xFF00;

    public Flags(MemoryCell flagCell) {
        this.flagCell = flagCell;
    }

    public boolean isLess() {
        return hasBitInMask(LESS_BIT_MASK);
    }

    public void setLess(boolean less) {
        optionallySetBitsInMask(less, LESS_BIT_MASK);
    }

    public boolean isGreater() {
        return hasBitInMask(GREATER_BIT_MASK);
    }

    public void setGreater(boolean greater) {
        optionallySetBitsInMask(greater, GREATER_BIT_MASK);
    }

    public boolean isEqual() {
        return hasBitInMask(EQUAL_BIT_MASK);
    }

    public void setEqual(boolean equal) {
        optionallySetBitsInMask(equal, EQUAL_BIT_MASK);
    }

    public boolean isZero() {
        return hasBitInMask(ZERO_BIT_MASK);
    }

    public void setZero(boolean zero) {
        optionallySetBitsInMask(zero, ZERO_BIT_MASK);
    }

    private boolean hasBitInMask(short bitMask) {
        return 0 != (flagCell.unsigned() & bitMask);
    }

    private void optionallySetBitsInMask(boolean shouldSet, short bitMask) {
        if (shouldSet) {
            addBitsInMask(bitMask);
        }
    }

    private void addBitsInMask(short bitMask) {
        flagCell.or(bitMask);
    }

    public void reset() {
        flagCell.and(RESET_MASK);
    }

    public String toString() {
        return uppercaseIf('e', isEqual()) +
                uppercaseIf('l', isLess()) +
                uppercaseIf('g', isGreater()) +
                uppercaseIf('z', isZero());
    }

    private String uppercaseIf(char c, boolean useUppercase) {
        return String.valueOf(useUppercase ? Character.toUpperCase(c) : c);
    }
}

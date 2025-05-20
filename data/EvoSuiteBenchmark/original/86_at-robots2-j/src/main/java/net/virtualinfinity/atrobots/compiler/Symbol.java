package net.virtualinfinity.atrobots.compiler;

/**
 * Represents a symbol microcode/value.
 *
 * @author Daniel Pitts
 */
public class Symbol {
    private final short microcode;
    private final short value;
    private final int lineNumber;

    public Symbol(short microcode, short value, int lineNumber) {
        this.microcode = microcode;
        this.value = value;
        this.lineNumber = lineNumber;
    }

    public short getValue() {
        return value;
    }

    public short getMicrocode() {
        return microcode;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}

package net.virtualinfinity.atrobots.computer;


/**
 * An {@link net.virtualinfinity.atrobots.computer.AbstractCombiningInstruction} which
 * performs a right bitwise rotation on the first operand, by the number of bits specified in the second.
 *
 * @author Daniel Pitts
 */
public class BitShiftRightInstruction extends AbstractCombiningInstruction {
    public BitShiftRightInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected int combine(short first, short second) {
        return (first & 0xFFFF) >>> second;
    }
}
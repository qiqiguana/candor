package net.virtualinfinity.atrobots.computer;


/**
 * An {@link net.virtualinfinity.atrobots.computer.AbstractCombiningInstruction} which
 * performs a left bitwise shift on the first operand, by the number of bits specified in the second.
 *
 * @author Daniel Pitts
 */
public class BitShiftLeftInstruction extends AbstractCombiningInstruction {
    public BitShiftLeftInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected int combine(short first, short second) {
        return first << second;
    }
}
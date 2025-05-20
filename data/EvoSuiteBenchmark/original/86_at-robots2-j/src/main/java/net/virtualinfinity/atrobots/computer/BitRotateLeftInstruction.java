package net.virtualinfinity.atrobots.computer;


/**
 * An {@link net.virtualinfinity.atrobots.computer.AbstractCombiningInstruction} which
 * performs a left bitwise rotation on the first operand, by the number of bits specified in the second.
 *
 * @author Daniel Pitts
 */
public class BitRotateLeftInstruction extends AbstractCombiningInstruction {
    public BitRotateLeftInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected int combine(short first, short second) {
        return (first << second) | ((first & 0xFFFF) >>> (16 - second));
    }
}
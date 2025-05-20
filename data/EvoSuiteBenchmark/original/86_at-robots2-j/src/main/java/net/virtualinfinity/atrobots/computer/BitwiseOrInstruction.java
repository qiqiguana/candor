package net.virtualinfinity.atrobots.computer;


/**
 * An {@link net.virtualinfinity.atrobots.computer.AbstractCombiningInstruction} which performs a bitwise or on the operands.
 *
 * @author Daniel Pitts
 */
public class BitwiseOrInstruction extends AbstractCombiningInstruction {
    public BitwiseOrInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected int combine(short first, short second) {
        return first | second;
    }
}

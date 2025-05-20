package net.virtualinfinity.atrobots.computer;


/**
 * An {@link net.virtualinfinity.atrobots.computer.AbstractCombiningInstruction} which performs a bitwise exclusive-or on the operands.
 *
 * @author Daniel Pitts
 */
public class BitwiseExclusiveOrInstruction extends AbstractCombiningInstruction {
    public BitwiseExclusiveOrInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected int combine(short first, short second) {
        return first ^ second;
    }
}

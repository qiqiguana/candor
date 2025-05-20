package net.virtualinfinity.atrobots.computer;

/**
 * An {@link net.virtualinfinity.atrobots.computer.AbstractCombiningInstruction} which adds the operands.
 *
 * @author Daniel Pitts
 */
public class AddInstruction extends AbstractCombiningInstruction {
    public AddInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected int combine(short first, short second) {
        return first + second;
    }
}

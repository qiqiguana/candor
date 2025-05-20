package net.virtualinfinity.atrobots.computer;

/**
 * An {@link net.virtualinfinity.atrobots.computer.AbstractCombiningInstruction} which subtracts the second operand from the first.
 *
 * @author Daniel Pitts
 */
public class SubtractInstruction extends AbstractCombiningInstruction {
    public SubtractInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected int combine(short first, short second) {
        return first - second;
    }
}

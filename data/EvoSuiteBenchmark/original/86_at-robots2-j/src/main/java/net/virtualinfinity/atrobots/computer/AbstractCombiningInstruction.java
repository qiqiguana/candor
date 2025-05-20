package net.virtualinfinity.atrobots.computer;

/**
 * An instruction that takes the value of the two operands and combines them in some way, storing the result
 * to the first operand.
 *
 * @author Daniel Pitts
 */
public abstract class AbstractCombiningInstruction extends Instruction {
    protected AbstractCombiningInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected final void perform(Computer computer) {
        computer.setOperandValue(1, (short) combine(computer.getOperandValue(1), computer.getOperandValue(2)));
    }

    /**
     * Combine the two operand values.
     *
     * @param first  the first operand value.
     * @param second the second operand value.
     * @return the result.
     */
    protected abstract int combine(short first, short second);
}

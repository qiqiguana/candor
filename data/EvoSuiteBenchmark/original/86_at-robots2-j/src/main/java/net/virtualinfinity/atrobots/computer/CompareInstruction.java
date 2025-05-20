package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which sets the flags register based on the comparison of the two operands.
 *
 * @author Daniel Pitts
 */
public class CompareInstruction extends Instruction {
    public CompareInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        final short first = computer.getOperandValue(1);
        final short second = computer.getOperandValue(2);
        computer.getFlags().reset();
        computer.getFlags().setEqual(first == second);
        computer.getFlags().setGreater(first > second);
        computer.getFlags().setLess(first < second);
        computer.getFlags().setZero(first == second && first == 0);

    }
}
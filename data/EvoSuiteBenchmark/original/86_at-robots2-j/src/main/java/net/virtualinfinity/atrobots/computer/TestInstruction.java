package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which sets the equal and zero flags according to the bitwise-and of the two operands.
 * The flags are set true if the result is the second operand and zero respectively.
 *
 * @author Daniel Pitts
 */
public class TestInstruction extends Instruction {
    public TestInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.getFlags().reset();
        final short first = computer.getOperandValue(1);
        final short second = computer.getOperandValue(2);
        computer.getFlags().setEqual((first & second) == second);
        computer.getFlags().setZero((first & second) == 0);
    }
}

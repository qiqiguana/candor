package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which sets the first operand to its bitwise negation.
 *
 * @author Daniel Pitts
 */
public class BitwiseNegationInstruction extends Instruction {
    public BitwiseNegationInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.setOperandValue(1, (short) ~computer.getOperandValue(1));
    }
}

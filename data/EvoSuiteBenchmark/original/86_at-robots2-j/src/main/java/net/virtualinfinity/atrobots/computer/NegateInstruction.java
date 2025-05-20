package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which sets the first operand to be its twos-complement negation.
 *
 * @author Daniel Pitts
 */
public class NegateInstruction extends Instruction {
    public NegateInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.setOperandValue(1, (short) -computer.getOperandValue(1));
    }
}

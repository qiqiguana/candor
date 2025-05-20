package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which copies the second operand value into the first
 *
 * @author Daniel Pitts
 */
public class MoveInstruction extends Instruction {
    public MoveInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.setOperandValue(1, computer.getOperandValue(2));
    }
}

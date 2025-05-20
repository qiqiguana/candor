package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which pushes the value of the first operand onto the stack.
 *
 * @author Daniel Pitts
 */
public class PushInstruction extends Instruction {
    public PushInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.push();
    }
}

package net.virtualinfinity.atrobots.computer;

/**
 * An instruction that sets the first operand to the value popped off the stack.
 *
 * @author Daniel Pitts
 */
public class PopInstruction extends Instruction {
    public PopInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.pop();
    }
}
package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which performs a call. This pushes the return address onto the stack and then jumps.
 *
 * @author Daniel Pitts
 */
public class CallInstruction extends Instruction {
    public CallInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.call();
    }
}

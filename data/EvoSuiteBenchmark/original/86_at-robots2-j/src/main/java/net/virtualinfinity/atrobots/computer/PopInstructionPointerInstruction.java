package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which pops the instuction pointer off the stack.
 *
 * @author Daniel Pitts
 */
public class PopInstructionPointerInstruction extends Instruction {
    public PopInstructionPointerInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.popInstructionPointer();
    }
}

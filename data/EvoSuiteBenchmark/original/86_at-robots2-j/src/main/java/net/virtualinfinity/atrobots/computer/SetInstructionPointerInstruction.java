package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which sets the instruction pointer.
 *
 * @author Daniel Pitts
 */
public class SetInstructionPointerInstruction extends Instruction {
    public SetInstructionPointerInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.jumpToLine();
    }
}

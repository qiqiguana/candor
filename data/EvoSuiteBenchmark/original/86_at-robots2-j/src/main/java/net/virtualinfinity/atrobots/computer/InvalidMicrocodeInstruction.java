package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which reports invalid microcode on the computer bus.
 *
 * @author Daniel Pitts
 */
public class InvalidMicrocodeInstruction extends Instruction {
    public InvalidMicrocodeInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.invalidMicrocodeError();
    }
}

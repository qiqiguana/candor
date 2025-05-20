package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which does nothing.
 *
 * @author Daniel Pitts
 */
public class NoOperationInstruction extends Instruction {
    public NoOperationInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
    }
}

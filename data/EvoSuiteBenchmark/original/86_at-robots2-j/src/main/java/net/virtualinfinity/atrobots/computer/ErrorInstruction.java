package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which reports a generic numbered error to the CPU bus.
 *
 * @author Daniel Pitts
 */
public class ErrorInstruction extends Instruction {
    public ErrorInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.genericError(computer.getOperandValue(1));
    }
}

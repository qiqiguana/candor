package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which increments the value of the first operand.
 *
 * @author Daniel Pitts
 */
public class IncrementInstruction extends Instruction {
    public IncrementInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.incrementOperand(1);
    }
}

package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which decrements the value of the first operand.
 *
 * @author Daniel Pitts
 */
public class DecrementInstruction extends Instruction {
    public DecrementInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.decrementOperand(1);
    }
}
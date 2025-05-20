package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which gets the value stored at the address pointed to by the second operand.
 *
 * @author Daniel Pitts
 */
public class GetInstruction extends Instruction {
    public GetInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.setOperandValue(1, computer.getMemory().get(computer.getOperandValue(2)));
    }
}
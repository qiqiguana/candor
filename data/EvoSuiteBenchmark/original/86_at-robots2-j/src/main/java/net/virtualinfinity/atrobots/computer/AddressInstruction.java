package net.virtualinfinity.atrobots.computer;

/**
 * This instruction sets the first operands value to the addres of the second operand.
 *
 * @author Daniel Pitts
 */
public class AddressInstruction extends Instruction {
    public AddressInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.setOperandValue(1, (short) computer.getOperandAddress(2));
    }
}
package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which sets the CX register to the first operand.
 *
 * @author Daniel Pitts
 */
public class SetCXInstruction extends Instruction {
    public SetCXInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.getRegisters().getCx().set(computer.getOperandValue(1));
    }
}

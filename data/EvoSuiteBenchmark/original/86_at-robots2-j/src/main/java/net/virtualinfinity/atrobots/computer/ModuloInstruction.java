package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which computes the value of the first operand modulo the second.
 *
 * @author Daniel Pitts
 */
public class ModuloInstruction extends Instruction {
    public ModuloInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        final short divisor = computer.getOperandValue(2);
        if (divisor == 0) {
            computer.divideByZeroError();
        }
        computer.setOperandValue(1, (short) (computer.getOperandValue(1) % divisor));
    }

}
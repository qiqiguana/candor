package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which divides the first operand by the second, storing the result in the first.
 *
 * @author Daniel Pitts
 */
public class DivideInstruction extends Instruction {
    public DivideInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        final short divisor = computer.getOperandValue(2);
        if (divisor == 0) {
            computer.divideByZeroError();
        }
        computer.setOperandValue(1, (short) (computer.getOperandValue(1) / divisor));
    }

}

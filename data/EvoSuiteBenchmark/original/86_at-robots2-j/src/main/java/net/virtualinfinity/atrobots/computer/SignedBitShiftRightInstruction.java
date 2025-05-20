package net.virtualinfinity.atrobots.computer;

/**
 * @author Daniel Pitts
 */
public class SignedBitShiftRightInstruction extends AbstractCombiningInstruction {
    public SignedBitShiftRightInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected int combine(short first, short second) {
        return first >> second;
    }
}
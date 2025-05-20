package net.virtualinfinity.atrobots.computer;

/**
 * @author Daniel Pitts
 */
public class JumpWhenNotZeroInstruction extends AbstractConditionalJumpInstruction {
    protected JumpWhenNotZeroInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected boolean conditionMet(Flags flags) {
        return !flags.isZero();
    }
}
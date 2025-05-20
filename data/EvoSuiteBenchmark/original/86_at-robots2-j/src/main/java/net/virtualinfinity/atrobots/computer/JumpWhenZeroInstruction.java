package net.virtualinfinity.atrobots.computer;

/**
 * @author Daniel Pitts
 */
public class JumpWhenZeroInstruction extends AbstractConditionalJumpInstruction {
    protected JumpWhenZeroInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected boolean conditionMet(Flags flags) {
        return flags.isZero();
    }
}
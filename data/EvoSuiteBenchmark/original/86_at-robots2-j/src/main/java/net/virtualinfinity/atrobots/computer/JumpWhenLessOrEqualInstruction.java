package net.virtualinfinity.atrobots.computer;

/**
 * @author Daniel Pitts
 */
public class JumpWhenLessOrEqualInstruction extends AbstractConditionalJumpInstruction {
    protected JumpWhenLessOrEqualInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected boolean conditionMet(Flags flags) {
        return flags.isLess() || flags.isEqual();
    }
}
package net.virtualinfinity.atrobots.computer;

/**
 * @author Daniel Pitts
 */
public class JumpWhenNotEqualInstruction extends AbstractConditionalJumpInstruction {
    protected JumpWhenNotEqualInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected boolean conditionMet(Flags flags) {
        return !flags.isEqual();
    }
}
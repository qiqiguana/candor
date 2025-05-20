package net.virtualinfinity.atrobots.computer;

/**
 * @author Daniel Pitts
 */
public class JumpWhenGreaterOrEqualInstruction extends AbstractConditionalJumpInstruction {
    protected JumpWhenGreaterOrEqualInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected boolean conditionMet(Flags flags) {
        return flags.isGreater() || flags.isEqual();
    }
}
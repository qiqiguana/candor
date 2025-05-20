package net.virtualinfinity.atrobots.computer;

/**
 * @author Daniel Pitts
 */
public class JumpWhenEqualInstruction extends AbstractConditionalJumpInstruction {
    protected JumpWhenEqualInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected boolean conditionMet(Flags flags) {
        return flags.isEqual();
    }
}
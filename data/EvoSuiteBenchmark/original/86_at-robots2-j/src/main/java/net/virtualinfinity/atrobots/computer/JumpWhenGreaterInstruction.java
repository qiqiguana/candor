package net.virtualinfinity.atrobots.computer;

/**
 * @author Daniel Pitts
 */
public class JumpWhenGreaterInstruction extends AbstractConditionalJumpInstruction {
    protected JumpWhenGreaterInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected boolean conditionMet(Flags flags) {
        return flags.isGreater();
    }
}
package net.virtualinfinity.atrobots.computer;

/**
 * The base class for a Jump instruction which may not execute.
 *
 * @author Daniel Pitts
 */
public abstract class AbstractConditionalJumpInstruction extends Instruction {
    public AbstractConditionalJumpInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    final protected void perform(Computer computer) {
        if (conditionMet(computer.getFlags())) {
            computer.jump();
        }
    }

    /**
     * Test whether the condition is met.
     *
     * @param flags the flags to test against.
     * @return true if the jump should be performed.
     */
    protected abstract boolean conditionMet(Flags flags);
}

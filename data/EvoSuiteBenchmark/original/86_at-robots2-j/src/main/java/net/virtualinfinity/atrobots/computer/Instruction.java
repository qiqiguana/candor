package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which can be executed.  Instruction implementations follow the flyweight pattern.
 * The instruction itself may be executed at any time against any computer.
 *
 * @author Daniel Pitts
 */
public abstract class Instruction {
    private final int baseExecutionCost;

    protected Instruction(int baseExecutionCost) {
        this.baseExecutionCost = baseExecutionCost;
    }

    /**
     * Execute this instruction on the given computer.
     *
     * @param computer the computer which is executing this instruction.
     */
    public final void execute(Computer computer) {
        computer.consumeCycles(baseExecutionCost);
        perform(computer);
    }

    /**
     * Do the work of the instruction.
     *
     * @param computer the computer which is executing this instruction.
     */
    protected abstract void perform(Computer computer);
}

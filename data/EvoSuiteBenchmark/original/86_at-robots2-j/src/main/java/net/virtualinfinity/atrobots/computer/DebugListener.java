package net.virtualinfinity.atrobots.computer;

/**
 * An interface to provide a hook into a running virtual machine.
 *
 * @author Daniel Pitts
 */
public interface DebugListener {
    /**
     * Called before an instruction is executed.  The instruction will not execute until
     * this method returns.
     *
     * @param computer the computer the instruction is about to be executed on.
     */
    void beforeInstruction(Computer computer);

    /**
     * Called after an isntruction is executed.
     *
     * @param computer the computer the instruction was executed on.
     */
    void afterInstruction(Computer computer);
}

package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.computer.DebugInfo;
import net.virtualinfinity.atrobots.computer.Program;

/**
 * The results of a compilation attempt.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompilerOutput {

    /**
     * Creates an RobotFactory based on this compiled output.
     *
     * @param name the name of the entrant to create.
     * @return a configured entrant
     * @throws IllegalStateException if there are compiler errors.
     */
    public RobotFactory createRobotFactory(String name) {
        if (hasErrors()) {
            throw new IllegalStateException("Can not create an entrant with errors. Check CompilerOutput.hasErrors() first.");
        }
        return new RobotFactory(name, getProgram(), getHardwareSpecification(), getDebugInfo(), getMaxProcessorSpeed(), getMessage());
    }

    /**
     * Check for errors.
     *
     * @return true if there are errors.
     */
    public boolean hasErrors();

    /**
     * Get the program code.
     *
     * @return the program code.
     * @throws IllegalStateException if there are compiler errors.
     */
    public Program getProgram();

    /**
     * Get the hardware specification.
     *
     * @return the hardware specification.
     * @throws IllegalStateException if there are compiler errors.
     */
    public HardwareSpecification getHardwareSpecification();

    /**
     * Get the debug info.
     *
     * @return the debug info.
     * @throws IllegalStateException if there are compiler errors.
     */
    public DebugInfo getDebugInfo();

    /**
     * Get the maximum desired processor speed.
     *
     * @return the maximum desired processor speed.
     * @throws IllegalStateException if there are compiler errors.
     */
    public int getMaxProcessorSpeed();

    /**
     * Get the message.
     *
     * @return the message.
     * @throws IllegalStateException if there are compiler errors.
     */
    public String getMessage();
}

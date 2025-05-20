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
}

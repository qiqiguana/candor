package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.computer.DebugInfo;
import net.virtualinfinity.atrobots.computer.Program;

/**
 * The results of a compilation attempt.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompilerOutput {

    public RobotFactory createRobotFactory(String name) {
        if (hasErrors()) {
            throw new IllegalStateException("Can not create an entrant with errors. Check CompilerOutput.hasErrors() first.");
        }
        return new RobotFactory(name, getProgram(), getHardwareSpecification(), getDebugInfo(), getMaxProcessorSpeed(), getMessage());
    }
}

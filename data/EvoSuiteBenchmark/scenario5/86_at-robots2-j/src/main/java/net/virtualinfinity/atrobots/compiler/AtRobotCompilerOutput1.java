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
     * Check for errors.
     *
     * @return true if there are errors.
     */
    public boolean hasErrors() {
        return errors.hasErrors();
    }
}

/**
 * Keeps track of compiler errors.
 *
 * @author Daniel Pitts
 */
public class Errors {

    public boolean hasErrors();
}

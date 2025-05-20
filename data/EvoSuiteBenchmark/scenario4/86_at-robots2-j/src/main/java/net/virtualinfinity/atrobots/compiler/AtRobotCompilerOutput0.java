package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.computer.DebugInfo;
import net.virtualinfinity.atrobots.computer.Program;

/**
 * The results of a compilation attempt.
 *
 * @author Daniel Pitts
 */
public class AtRobotCompilerOutput {

    private final Errors errors;

    private final Program program;

    private final HardwareSpecification hardwareSpecification;

    private final int maxProcessorSpeed;

    private final DebugInfo debugInfo;

    private final String message;

    /**
     * Construct an output.
     *
     * @param errors                the errors (if any). Should not be null.
     * @param program               the program produced by the compiler.
     * @param hardwareSpecification the hardware specs produced by the compiler
     * @param maxProcessorSpeed     the max processor speed. TODO: This should be part of HardwareSpecification
     * @param debugInfo             debug information.
     * @param message               the robots message.
     * @throws NullPointerException if errors is null, or if errors.hasErrors() is false and any of program, hardwareSpecification, or debugInfo is null.
     */
    public AtRobotCompilerOutput(Errors errors, Program program, HardwareSpecification hardwareSpecification, int maxProcessorSpeed, DebugInfo debugInfo, String message) {
    }

    /**
     * Creates an RobotFactory based on this compiled output.
     *
     * @param name the name of the entrant to create.
     * @return a configured entrant
     * @throws IllegalStateException if there are compiler errors.
     */
    public RobotFactory createRobotFactory(String name);

    /**
     * Get the Errors object.
     *
     * @return the Errors object.
     */
    public Errors getErrors();

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
     * Get the message.
     *
     * @return the message.
     * @throws IllegalStateException if there are compiler errors.
     */
    public String getMessage();

    /**
     * Get the hardware specification.
     *
     * @return the hardware specification.
     * @throws IllegalStateException if there are compiler errors.
     */
    public HardwareSpecification getHardwareSpecification();

    /**
     * Get the maximum desired processor speed.
     *
     * @return the maximum desired processor speed.
     * @throws IllegalStateException if there are compiler errors.
     */
    public int getMaxProcessorSpeed();

    /**
     * Get the debug info.
     *
     * @return the debug info.
     * @throws IllegalStateException if there are compiler errors.
     */
    public DebugInfo getDebugInfo();
}

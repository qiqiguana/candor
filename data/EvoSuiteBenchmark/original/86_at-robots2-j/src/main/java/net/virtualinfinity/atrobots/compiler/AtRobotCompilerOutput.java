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
        if (errors == null) {
            throw new NullPointerException("errors must not be null");
        }
        this.errors = errors;
        if (!errors.hasErrors()) {
            if (program == null) {
                throw new NullPointerException("program must not be null unless there are errors.");
            }
            if (debugInfo == null) {
                throw new NullPointerException("debugInfo must not be null unless there are errors.");
            }
            if (hardwareSpecification == null) {
                throw new NullPointerException("hardwareSpecification must not be null unless there are errors.");
            }
        }
        this.program = program;
        this.hardwareSpecification = hardwareSpecification;
        this.maxProcessorSpeed = maxProcessorSpeed;
        this.debugInfo = debugInfo;
        this.message = message;
    }

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
     * Get the Errors object.
     *
     * @return the Errors object.
     */
    public Errors getErrors() {
        return errors;
    }

    /**
     * Check for errors.
     *
     * @return true if there are errors.
     */
    public boolean hasErrors() {
        return errors.hasErrors();
    }

    /**
     * Get the program code.
     *
     * @return the program code.
     * @throws IllegalStateException if there are compiler errors.
     */
    public Program getProgram() {
        if (hasErrors()) {
            throw new IllegalStateException("Can not get the program when there are errors.");
        }
        return program;
    }

    /**
     * Get the message.
     *
     * @return the message.
     * @throws IllegalStateException if there are compiler errors.
     */
    public String getMessage() {
        if (hasErrors()) {
            throw new IllegalStateException("Can not get the message when there are errors.");
        }
        return message;
    }

    /**
     * Get the hardware specification.
     *
     * @return the hardware specification.
     * @throws IllegalStateException if there are compiler errors.
     */
    public HardwareSpecification getHardwareSpecification() {
        if (hasErrors()) {
            throw new IllegalStateException("Can not get the hardware specification when there are errors.");
        }
        return hardwareSpecification;
    }

    /**
     * Get the maximum desired processor speed.
     *
     * @return the maximum desired processor speed.
     * @throws IllegalStateException if there are compiler errors.
     */
    public int getMaxProcessorSpeed() {
        if (hasErrors()) {
            throw new IllegalStateException("Can not get the maximum processor speed when there are errors.");
        }
        return maxProcessorSpeed;
    }

    /**
     * Get the debug info.
     *
     * @return the debug info.
     * @throws IllegalStateException if there are compiler errors.
     */
    public DebugInfo getDebugInfo() {
        if (hasErrors()) {
            throw new IllegalStateException("Can not get the debug info when there are errors.");
        }
        return debugInfo;
    }
}

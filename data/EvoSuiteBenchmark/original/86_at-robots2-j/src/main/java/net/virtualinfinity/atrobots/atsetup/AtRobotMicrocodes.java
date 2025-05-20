package net.virtualinfinity.atrobots.atsetup;

/**
 * Constants for microcode values.
 *
 * @author Daniel Pitts
 */
public class AtRobotMicrocodes {
    /**
     * The operand value should be interpreted as a constant. (eg "15")
     */
    public static final short CONSTANT = 0;
    /**
     * The operand value should be interpreted as a reference (eg "@9").
     */
    public static final short REFERENCE = 1;
    /**
     * The operand value should be interpreted as a numbered label (eg ":300").
     */
    public static final short NUMBERED_LABEL = 2;
    /**
     * The operand value is invalid, because it should have pointed to a label ("!targetAcquired") which was never resolved.
     */
    public static final short UNRESOLVED_LABEL = 3;
    /**
     * The operand value is the instruction pointer value of a a label ("!targetAcquired").
     */
    public static final short RESOLVED_LABEL = 4;
    /**
     * If the microcode has this bit mask set, then the value should be interpreted by the lower 3 bits, and then
     * used as an address.
     */
    public static final short INDIRECT_REFERENCE_MASK = 8;
}

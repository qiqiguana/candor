package net.virtualinfinity.atrobots.atsetup;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * A mapping between interrupt symbol names and numbers.
 *
 * @author Daniel Pitts
 */
public enum AtRobotInterrupt implements AtRobotSymbol {
    DESTRUCT(0),
    RESET(1),
    LOCATE(2),
    KEEPSHIFT(3),
    OVERBURN(4),
    ID(5),
    TIMER(6),
    ANGLE(7),
    TARGETID(8, "TID"),
    TARGETINFO(9, "TINFO"),
    GAMEINFO(10, "GINFO"),
    ROBOTINFO(11, "RINFO"),
    COLLISIONS(12),
    RESETCOLCNT(13),
    TRANSMIT(14),
    RECEIVE(15),
    DATAREADY(16),
    CLEARCOM(17),
    KILLS(18, "DEATHS"),
    CLEARMETERS(19),;
    private static final String INTERRUPT_PREFIX = "I_";
    private static AtRobotInterrupt[] byValue = new AtRobotInterrupt[values().length];
    /**
     * The interrupt number.
     */
    public final int interruptNumber;

    /**
     * The symbol names which represent this interrupt.
     */
    public final Collection<String> names;

    static {
        for (AtRobotInterrupt instruction : values()) {
            if (byValue[instruction.interruptNumber] != null) {
                throw new IllegalStateException();
            }
            byValue[instruction.interruptNumber] = instruction;
        }
    }

    private AtRobotInterrupt(int interruptNumber) {
        this.interruptNumber = interruptNumber;
        this.names = Collections.singleton(INTERRUPT_PREFIX + name());
    }

    private AtRobotInterrupt(int interruptNumber, String alternate) {
        this.interruptNumber = interruptNumber;
        this.names = Collections.unmodifiableCollection(Arrays.asList(INTERRUPT_PREFIX + name(), INTERRUPT_PREFIX + alternate));
    }

    /**
     * Get the name of the interrupt by the value.
     *
     * @param interruptNumber the interrupt number
     * @return the name() of the corresponding interrupt, or "&lt;unknown>" if there is none.
     */
    public static String nameOf(short interruptNumber) {
        if (interruptNumber >= 0 && interruptNumber < byValue.length) {
            return INTERRUPT_PREFIX + byValue[interruptNumber].name();
        }
        return "<unknown>";
    }


    public int getSymbolValue() {
        return interruptNumber;
    }

    public Collection<String> getSymbolNames() {
        return names;
    }
}
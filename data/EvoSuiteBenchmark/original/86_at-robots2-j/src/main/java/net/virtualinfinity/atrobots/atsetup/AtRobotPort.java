package net.virtualinfinity.atrobots.atsetup;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Mapping between port symbol names and port numbers.
 *
 * @author Daniel Pitts
 */
public enum AtRobotPort implements AtRobotSymbol {
    SPEDOMETER(1),
    HEAT(2),
    COMPASS(3),
    TURRET_OFS(4),
    TURRET_ABS(5),
    DAMAGE(6, "ARMOR"),
    SCAN(7),
    ACCURACY(8),
    RADAR(9),
    RANDOM(10, "RAND"),
    THROTTLE(11),
    OFS_TURRET(12, "TROTATE"),
    ABS_TURRET(13, "TAIM"),
    STEERING(14),
    WEAP(15, "WEAPON", "FIRE"),
    SONAR(16),
    ARC(17, "SCANARC"),
    OVERBURN(18),
    TRANSPONDER(19),
    SHUTDOWN(20),
    CHANNEL(21),
    MINELAYER(22),
    MINETRIGGER(23),
    SHIELD(24, "SHIELDS");
    /**
     * The port number.
     */
    public final int portNumber;
    /**
     * The symbol names which represent this port.
     */
    public final Collection<String> names;

    private static final String PORT_SYMBOL_PREFIX = "P_";

    private AtRobotPort(int portNumber) {
        this.portNumber = portNumber;
        names = Collections.singleton(PORT_SYMBOL_PREFIX + name());
    }

    private AtRobotPort(int portNumber, String alternateName) {
        this.portNumber = portNumber;
        names = Collections.unmodifiableCollection(Arrays.asList(PORT_SYMBOL_PREFIX + name(), PORT_SYMBOL_PREFIX + alternateName));
    }

    private AtRobotPort(int portNumber, String alternateName1, String alternateName2) {
        this.portNumber = portNumber;
        names = Collections.unmodifiableCollection(Arrays.asList(PORT_SYMBOL_PREFIX + name(), PORT_SYMBOL_PREFIX + alternateName1, PORT_SYMBOL_PREFIX + alternateName2));
    }

    /**
     * Get the name of the port by the value.
     *
     * @param portNumber the port number.
     * @return the name() of the corresponding port, or "&lt;unknown>" if there is none.
     */
    public static String nameOf(short portNumber) {
        for (AtRobotPort port : values()) {
            if (port.portNumber == portNumber) {
                return PORT_SYMBOL_PREFIX + port.name();
            }
        }
        return "<unknown>";
    }

    public int getSymbolValue() {
        return portNumber;
    }

    public Collection<String> getSymbolNames() {
        return names;
    }
}


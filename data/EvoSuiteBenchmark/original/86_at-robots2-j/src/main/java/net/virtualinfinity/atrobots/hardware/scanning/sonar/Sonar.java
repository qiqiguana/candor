package net.virtualinfinity.atrobots.hardware.scanning.sonar;

import net.virtualinfinity.atrobots.hardware.scanning.ScanSource;
import net.virtualinfinity.atrobots.measures.AbsoluteAngle;
import net.virtualinfinity.atrobots.measures.AngleBracket;
import net.virtualinfinity.atrobots.measures.RelativeAngle;
import net.virtualinfinity.atrobots.ports.PortHandler;

/**
 * @author Daniel Pitts
 */
public class Sonar {
    private ScanSource scanSource;
    private double maxDistance;

    public Sonar() {
        maxDistance = (250.0);
    }

    public PortHandler getScanPort() {
        return new PortHandler() {
            public short read() {
                consumeCycles(40);
                final AbsoluteAngle angle = scan();
                if (angle == null) {
                    return Short.MIN_VALUE;
                }
                return (short) AngleBracket.around(angle, RelativeAngle.fromBygrees(16)).randomAngleBetween().getBygrees();
            }
        };
    }

    private AbsoluteAngle scan() {
        return scanSource.scan(AngleBracket.all(), maxDistance, false, false).getAngle();
    }

    public void setScanSource(ScanSource scanSource) {
        this.scanSource = scanSource;
    }
}

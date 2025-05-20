package net.virtualinfinity.atrobots.hardware.scanning.scanner;

import net.virtualinfinity.atrobots.arena.Heading;
import net.virtualinfinity.atrobots.computer.Resettable;
import net.virtualinfinity.atrobots.hardware.scanning.ScanResult;
import net.virtualinfinity.atrobots.hardware.scanning.ScanSource;
import net.virtualinfinity.atrobots.measures.AngleBracket;
import net.virtualinfinity.atrobots.measures.RelativeAngle;
import net.virtualinfinity.atrobots.ports.PortHandler;

/**
 * @author Daniel Pitts
 */
public class Scanner implements Resettable {
    private int accuracy;
    private RelativeAngle scanArc = RelativeAngle.fromBygrees(8);
    private ScanSource scanSource;
    private double maxDistance = (1500);
    private Heading heading;

    public Scanner(double maxDistance) {
        this.maxDistance = (maxDistance);
    }

    public PortHandler getScanPort() {
        return new PortHandler() {
            public short read() {
                consumeCycles(1);
                final ScanResult result = scan();
                if (!result.successful()) {
                    return Short.MAX_VALUE;
                }
                return (short) Math.round(result.getDistance());
            }
        };
    }

    private ScanResult scan() {
        final AngleBracket angleBracket = getAngleBracket();
        final ScanResult scanResult = scanSource.scan(angleBracket, maxDistance, true, true);
        if (scanResult.successful()) {
            setAccuracy(scanResult.getAccuracy());
        }
        return scanResult;
    }

    private AngleBracket getAngleBracket() {
        return AngleBracket.around(heading.getAngle(), scanArc);
    }

    public PortHandler getAccuracyPort() {
        return new PortHandler() {
            public short read() {
                consumeCycles(1);
                return (short) getAccuracy();
            }
        };
    }

    public int getAccuracy() {
        return accuracy;
    }

    public PortHandler getScanArcLatchPort() {
        return new PortHandler() {
            public short read() {
                return (short) getScanArc().getBygrees();
            }

            public void write(short value) {
                setScanArc(RelativeAngle.fromBygrees(Math.max(0, Math.min(64, value))));
            }
        };
    }

    public RelativeAngle getScanArc() {
        return scanArc;
    }

    public void setScanArc(RelativeAngle scanArc) {
        this.scanArc = scanArc;
    }

    public void reset() {
        setScanArc(RelativeAngle.fromBygrees(8));
    }

    private void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public void setScanSource(ScanSource scanSource) {
        this.scanSource = scanSource;
    }
}

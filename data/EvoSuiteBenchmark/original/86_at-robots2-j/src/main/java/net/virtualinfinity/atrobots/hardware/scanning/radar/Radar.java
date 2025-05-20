package net.virtualinfinity.atrobots.hardware.scanning.radar;

import net.virtualinfinity.atrobots.hardware.scanning.ScanResult;
import net.virtualinfinity.atrobots.hardware.scanning.ScanSource;
import net.virtualinfinity.atrobots.measures.AngleBracket;
import net.virtualinfinity.atrobots.ports.PortHandler;


/**
 * @author Daniel Pitts
 */
public class Radar {
    private ScanSource scanSource;

    public PortHandler getScanPort() {
        return new PortHandler() {
            public short read() {
                consumeCycles(3);
                final ScanResult scanResult = scanSource.scan(AngleBracket.all(), Double.POSITIVE_INFINITY, false, false);
                if (!scanResult.successful()) {
                    return Short.MAX_VALUE;
                }
                return (short) Math.round(scanResult.getDistance());
            }
        };
    }

    public void setScanSource(ScanSource scanSource) {
        this.scanSource = scanSource;
    }
}

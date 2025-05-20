package net.virtualinfinity.atrobots.hardware.scanning;

import net.virtualinfinity.atrobots.measures.AbsoluteAngle;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface ScanResult {
    double getDistance();

    AbsoluteAngle getAngle();

    boolean successful();

    int getAccuracy();
}

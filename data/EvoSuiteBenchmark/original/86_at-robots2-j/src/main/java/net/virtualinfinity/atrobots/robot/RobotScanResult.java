package net.virtualinfinity.atrobots.robot;

import net.virtualinfinity.atrobots.hardware.scanning.ScanResult;
import net.virtualinfinity.atrobots.measures.AbsoluteAngle;
import net.virtualinfinity.atrobots.measures.Vector;

/**
 * @author Daniel Pitts
 */
public class RobotScanResult implements ScanResult {
    private final Robot match;
    private final double distance;
    private final AbsoluteAngle angle;
    private final AbsoluteAngle heading;
    private final int throttle;
    private final int accuracy;

    public RobotScanResult(Robot match, double distance, AbsoluteAngle angle, int accuracy) {
        this.match = match;
        this.distance = distance;
        this.angle = angle;
        this.accuracy = accuracy;
        heading = match.getHeading().getAngle();
        throttle = match.getThrottle().getDesiredPower();
    }

    public RobotScanResult() {
        distance = Double.POSITIVE_INFINITY;
        match = null;
        angle = null;
        heading = null;
        throttle = 0;
        accuracy = 0;
    }


    public Robot getMatch() {
        return match;
    }

    public double getDistance() {
        return distance;
    }

    public AbsoluteAngle getAngle() {
        return angle;
    }

    public AbsoluteAngle getHeading() {
        return heading;
    }

    public int getThrottle() {
        return throttle;
    }

    public boolean successful() {
        return match != null;
    }

    public int getAccuracy() {
        return accuracy;
    }

    Vector getMatchPositionVector() {
        return successful() ? getMatch().getPosition().getVector() : null;
    }
}
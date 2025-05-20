package net.virtualinfinity.atrobots.snapshots;

import net.virtualinfinity.atrobots.measures.AngleBracket;
import net.virtualinfinity.atrobots.measures.Vector;

import java.awt.*;

/**
 * @author Daniel Pitts
 */
public class ScanSnapshot extends ArenaObjectSnapshot {
    private final AngleBracket angleBracket;
    private final double maxDistance;
    private final Vector matchVector;
    private final boolean accuracyAvailable;
    private final int accuracy;
    private final boolean successful;

    public ScanSnapshot(AngleBracket angleBracket, double maxDistance, boolean successful, Vector matchVector, boolean accuracyAvailable, int accuracy) {
        this.angleBracket = angleBracket;
        this.maxDistance = maxDistance;
        this.successful = successful;
        this.matchVector = matchVector;
        this.accuracyAvailable = accuracyAvailable;
        this.accuracy = accuracy;
    }

    public void visit(SnapshotVisitor visitor) {
        visitor.acceptScan(this);
    }

    public AngleBracket getAngleBracket() {
        return angleBracket;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public Vector getMatchVector() {
        return matchVector;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public Shape getScanArea() {
        return shapeFor(getAngleBracket());
    }

    private Shape shapeFor(AngleBracket bracket) {
        return bracket.toShape(getPositionVector().getX(), getPositionVector().getY(), getMaxDistance());
    }

    public Shape getAccuracyArea() {
        if (!accuracyAvailable) {
            return getScanArea();
        }
        return shapeFor(getAccuracyBracket());
    }

    public AngleBracket getAccuracyBracket() {
        return getAngleBracket().subrange(Math.max(0, .5 - (accuracy * .25 + .125)), Math.min(1, .5 - (accuracy * .25 - .125)));
    }

    public boolean isAccuracyAvailable() {
        return accuracyAvailable;
    }

    public int getAccuracy() {
        return accuracy;
    }

}

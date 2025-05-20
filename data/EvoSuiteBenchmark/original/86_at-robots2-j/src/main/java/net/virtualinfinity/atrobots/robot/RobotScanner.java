package net.virtualinfinity.atrobots.robot;

import net.virtualinfinity.atrobots.ArenaObjectVisitorAdaptor;
import net.virtualinfinity.atrobots.arena.Position;
import net.virtualinfinity.atrobots.measures.AbsoluteAngle;
import net.virtualinfinity.atrobots.measures.AngleBracket;
import net.virtualinfinity.atrobots.measures.Vector;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class RobotScanner extends ArenaObjectVisitorAdaptor {
    private final Robot source;
    private final double maxDistanceSquared;
    private final Position position;
    private final AngleBracket angleBracket;
    private final boolean calculateAccuracy;

    private Vector vectorToClosest = null;
    private double closestDistanceSquared;
    private Robot closest = null;
    private Vector counterClockwiseBound;
    private Vector clockwiseBound;
    private static final int ROBOT_RADIUS_SQUARED = 16;

    public RobotScanner(Robot source, Position position, AngleBracket angleBracket, double maxDistance, boolean calculateAccuracy) {
        this.source = source;
        this.position = position;
        this.angleBracket = angleBracket;
        this.calculateAccuracy = calculateAccuracy;
        this.counterClockwiseBound = getUnit(angleBracket.getCounterClockwiseBound());
        this.clockwiseBound = getUnit(angleBracket.getClockwiseBound());
        this.maxDistanceSquared = maxDistance * maxDistance;
        this.closestDistanceSquared = maxDistanceSquared;
    }

    private static Vector getUnit(AbsoluteAngle absoluteAngle) {
        if (absoluteAngle == null) {
            return null;
        }
        return absoluteAngle.toUnitVector();
    }

    public void visit(Robot arenaObject) {
        if (arenaObject == source) {
            return;
        }
        final Vector position = arenaObject.getPosition().getVectorTo(this.position);
        final double distanceSquared = position.getMagnitudeSquared();
        if (distanceSquared < closestDistanceSquared && withinArc(position, distanceSquared)) {
            closest = arenaObject;
            closestDistanceSquared = distanceSquared;
            vectorToClosest = position;
        }
    }

    private boolean withinArc(Vector position, double distanceSquared) {
        return angleBracket.contains(position.getAngle()) || rayIntersection(position, distanceSquared, counterClockwiseBound) || rayIntersection(position, distanceSquared, clockwiseBound);
    }

    private boolean rayIntersection(Vector circleCenter, double distanceSquared, Vector direction) {
        final double a = direction.dot(circleCenter);
        return a > 0 && a * a > distanceSquared - ROBOT_RADIUS_SQUARED;
    }

    public RobotScanResult toScanResult() {
        if (closest == null || closestDistanceSquared > maxDistanceSquared) {
            return new RobotScanResult();
        }
        final AbsoluteAngle angleToClosest = vectorToClosest.getAngle();
        final int accuracy = calculateAccuracy ? findAccuracy(angleToClosest) : 0;
        return new RobotScanResult(closest, Math.sqrt(closestDistanceSquared), angleToClosest, accuracy);
    }

    private int findAccuracy(AbsoluteAngle angleToClosest) {
        final int accuracy;
        final double v = 0.5d - angleBracket.fractionTo(angleToClosest);
        if (angleBracket.getRangeSize().getBygrees() <= 4) {
            accuracy = roundAwayFromZero(v * 2) * 2;
        } else {
            accuracy = roundAwayFromZero(v * 4);
        }
        return Math.min(2, Math.max(-2, accuracy));
    }

    private static int roundAwayFromZero(double value) {
        return (int) (value < 0 ? Math.ceil(value - 0.5d) : Math.floor(value + 0.5d));
    }
}

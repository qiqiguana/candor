package net.virtualinfinity.atrobots.measures;

/**
 * A {@link net.virtualinfinity.atrobots.measures.Vector} which is implemented via a polar representation.
 *
 * @author Daniel Pitts
 */
class PolarVector extends Vector {
    private final double magnitude;
    private final AbsoluteAngle angle;

    private PolarVector(double magnitude, AbsoluteAngle angle) {
        this.magnitude = magnitude;
        this.angle = angle;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public AbsoluteAngle getAngle() {
        return angle;
    }

    public double getX() {
        return magnitude * angle.cosine();
    }

    public double getY() {
        return magnitude * angle.sine();
    }

    public static PolarVector createPolar(AbsoluteAngle angle, double magnitude) {
        if (magnitude < 0) {
            return new PolarVector(-magnitude, angle.getSupplementary());
        }
        return new PolarVector(magnitude, angle);
    }

    public double getMagnitudeSquared() {
        return magnitude * (magnitude);
    }

    public Vector times(double v) {
        return createPolar(angle, magnitude * (v));
    }
}

package net.virtualinfinity.atrobots.measures;

/**
 * A {@link net.virtualinfinity.atrobots.measures.Vector} which is implemented via a cartesian (rectangular)
 * representation.
 *
 * @author Daniel Pitts
 */
class CartesianVector extends Vector {
    private final double x;
    private final double y;

    private CartesianVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getMagnitude() {
        return Math.hypot(x, y);
    }

    public double getMagnitudeSquared() {
        return x * x + y * y;
    }

    public AbsoluteAngle getAngle() {
        return AbsoluteAngle.fromCartesian(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector times(double v) {
        return fromCartesian(x * (v), y * (v));
    }

    public static Vector fromCartesian(double x, double y) {
        return new CartesianVector(x, y);
    }
}

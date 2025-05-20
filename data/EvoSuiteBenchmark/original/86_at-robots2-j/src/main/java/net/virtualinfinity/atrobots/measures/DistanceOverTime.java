package net.virtualinfinity.atrobots.measures;

/**
 * Represents a particular speed.
 *
 * @author Daniel Pitts
 */
public final class DistanceOverTime {
    private final double distance;
    private final Duration duration;

    public DistanceOverTime(double distance, Duration duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public double times(Duration duration) {
        return distance * (this.duration.divided(duration));
    }

    public String toString() {
        return distance + "/" + duration;
    }
}

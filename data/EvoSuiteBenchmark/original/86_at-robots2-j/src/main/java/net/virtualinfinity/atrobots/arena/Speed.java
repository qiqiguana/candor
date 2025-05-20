package net.virtualinfinity.atrobots.arena;


import net.virtualinfinity.atrobots.measures.DistanceOverTime;
import net.virtualinfinity.atrobots.measures.Duration;

/**
 * @author Daniel Pitts
 */
public class Speed {
    private DistanceOverTime distanceOverTime = new DistanceOverTime(0, Duration.ONE_CYCLE);

    public double times(Duration duration) {
        return distanceOverTime.times(duration);
    }

    public void setDistanceOverTime(double distance, Duration duration) {
        distanceOverTime = new DistanceOverTime(distance, duration);
    }

    public String toString() {
        return distanceOverTime.toString();
    }

}

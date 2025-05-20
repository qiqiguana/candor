package net.virtualinfinity.atrobots.measures;

/**
 * @author Daniel Pitts
 */
public class Temperature implements Comparable<Temperature> {
    private final double logScale;
    public static final Temperature BASE_TEMPERATURE = fromLogScale(0);

    public Temperature(double logScale) {
        this.logScale = logScale;
    }
//    private final double celsius;
//
//    public static final Temperature BASE_TEMPERATURE = fromCelsius(20);
//    private static final Temperature ROBOT_DEATH = fromCelsius(750);
//    private static final int LOG_SCALE_ROBOT_DEATH = 500;
//    private static final double LOG_ROBOT_DEATH = Math.log(ROBOT_DEATH.getCelsius());
//    private static final double LOG_SCALER = LOG_ROBOT_DEATH / LOG_SCALE_ROBOT_DEATH;
//
//    private Temperature(double celsius) {
//        this.celsius = celsius;
//    }
//
//    public int getLogScale() {
//        return (int) Math.round(Math.log(getCelsius() - BASE_TEMPERATURE.getCelsius()) / LOG_SCALER);
//    }
//
//    public static Temperature fromCelsius(double celsius) {
//        return new Temperature(celsius);
//    }
//
//    public double getCelsius() {
//        return celsius;
//    }
//
//    public static Temperature fromLogScale(int logTemp) {
//        return fromCelsius(Math.exp(logTemp * LOG_SCALER) + BASE_TEMPERATURE.getCelsius());
//    }
//
//    public Temperature minus(Temperature temperature) {
//        return fromCelsius(getCelsius() - temperature.getCelsius());
//    }
//
//    public Temperature plus(Temperature temperature) {
//        return fromCelsius(getCelsius() + temperature.getCelsius());
//    }

    public double getLogScale() {
        return logScale;
    }

    public static Temperature fromLogScale(double value) {
        return new Temperature(value);
    }

    public Temperature plus(Temperature temperature) {
        return fromLogScale(getLogScale() + temperature.getLogScale());
    }

    public Temperature minus(Temperature temperature) {
        return fromLogScale(getLogScale() - temperature.getLogScale());
    }

    public int compareTo(Temperature temperature) {
        return Double.valueOf(getLogScale()).compareTo(temperature.getLogScale());
    }

    public Temperature times(double multiplier) {
        return fromLogScale(getLogScale() * multiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Temperature that = (Temperature) o;

        if (Double.compare(that.logScale, logScale) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = logScale != +0.0d ? Double.doubleToLongBits(logScale) : 0L;
        return (int) (temp ^ (temp >>> 32));
    }
}

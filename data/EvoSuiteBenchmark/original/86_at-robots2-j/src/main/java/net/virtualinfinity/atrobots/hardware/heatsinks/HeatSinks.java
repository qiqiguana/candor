package net.virtualinfinity.atrobots.hardware.heatsinks;

import net.virtualinfinity.atrobots.measures.Heat;
import net.virtualinfinity.atrobots.measures.Temperature;

/**
 * @author Daniel Pitts
 */
public class HeatSinks implements Heat {
    private Temperature temperature = Temperature.BASE_TEMPERATURE;
    private double coolMultiplier = 1;
    private boolean blockHeat;

    public Temperature getTemperature() {
        return temperature;
    }

    public void warm(Temperature temperature) {
        this.temperature = this.temperature.plus(temperature);
    }

    public void cool(Temperature temperature) {
        if (blockHeat) {
            return;
        }
        this.temperature = this.temperature.minus(temperature.times(coolMultiplier));
        if (this.temperature.compareTo(Temperature.BASE_TEMPERATURE) < 0) {
            this.temperature = Temperature.BASE_TEMPERATURE;
        }
    }

    public void setCoolMultiplier(double coolMultiplier) {
        this.coolMultiplier = coolMultiplier;
    }

    public void blockHeat(boolean blockHeat) {
        this.blockHeat = blockHeat;
    }
}

package simulator.util;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class ParameterAttributes implements Serializable {

    /**
     * Calculates parameter's value for the Demand Curve as : elasticity * [ ln ] ( demandCurveFunction(step) )
     *
     * @param step The step for which to calculate parameters value
     * @return Parameter's value that will be summed for calculating total consumer's consumption
     */
    public float valueFor(int step);
}

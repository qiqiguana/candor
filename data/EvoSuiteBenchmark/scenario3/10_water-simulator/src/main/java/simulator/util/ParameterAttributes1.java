package simulator.util;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class ParameterAttributes implements Serializable {

    /**
     * Overrides the previous method if the input is a float number. Mostly used when the parameter is
     * social and the input value is the sum of weights.
     * Calculates parameter's value for the Demand Curve as :
     * elasticity * [ ln ] (demandCurveFunction(weights) )
     *
     * @param x The step for which to calculate parameters value
     * @return Parameter's value that will be summed for calculating total consumer's consumption
     */
    public float valueFor(float x);
}

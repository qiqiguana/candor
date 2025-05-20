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
    public float valueFor(float x) {
        float temp = demandCurveFunction.valueFor(x);
        if (temp > 0) {
            if (ln.booleanValue()) {
                this.value = new Float(elasticity.floatValue() * ((float) Math.log(temp)));
            } else {
                this.value = new Float(elasticity.floatValue() * temp);
            }
        } else {
            log.error("FATAL ERROR: Parameter was zero");
        }
        return value.floatValue();
    }
}

public abstract class Function implements Serializable {

    /**
     * It must be implemented by a child class so as for every input X (float x) to return a value Y.
     * X could be weights' sum when this Function used for socialization.
     *
     * @param x
     * @return
     */
    abstract public float valueFor(float x);
}

package simulator.util;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class ParameterAttributes implements Serializable {

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

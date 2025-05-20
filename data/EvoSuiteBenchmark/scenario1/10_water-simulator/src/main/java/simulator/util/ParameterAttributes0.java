package simulator.util;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class ParameterAttributes implements Serializable {

    public float valueFor(int step) {
        return valueFor((float) step);
    }
}

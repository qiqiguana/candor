package simulator.util;

import java.io.Serializable;
import org.apache.log4j.Logger;

public class ParameterAttributes implements Serializable {

    private static final long serialVersionUID = 6885432319261152672L;

    private Logger log = Logger.getLogger(ParameterAttributes.class);

    //Properties
    private String name;

    private Float elasticity;

    private Boolean ln;

    private Function demandCurveFunction;

    private Function socialFunction;

    private Boolean social = Boolean.FALSE;

    private Float value = new Float(0);

    /**
     * Set parameter name
     * @param name
     */
    public void setName(String name);

    /**
     * @return
     */
    public String getName();

    /**
     * Set elasticity
     * @param elasticity
     */
    public void setElasticity(float elasticity);

    /**
     * @return
     */
    public float getElasticity();

    /**
     * Set true if the value of the parameter is the logarithm of parameter's function
     * @param ln
     */
    public void setLn(boolean ln);

    /**
     * @return
     */
    public boolean getLn();

    /**
     * Set the demand curve function for this parameter. The Demand Curve Function gets the stepId
     * as input if the parameter isn't social, otherwise gets the sum of all weights received from
     * its neighbours. It returns parameter's value.
     * @param dCF One of the availabe functions
     */
    public void setDemandCurveFunction(Function dCF);

    /**
     * Set the demand curve function for this parameter
     * @return
     */
    public Function getDemandCurveFunction();

    /**
     * Set the social function for this parameter. The Social Curve Function gets the stepId
     * as input and returns a value (the weight) that the consumer agent will send in socialization
     * stage of simulation in the specified step.
     * @param sF One of the available functions
     */
    public void setSocialFunction(Function sF);

    /**
     * @return
     */
    public Function getSocialFunction();

    /**
     * Set this parameter to be social
     */
    public void setSocial(boolean b);

    /**
     * @return
     */
    public boolean isSocial();

    /**
     * Calculates parameter's value for the Demand Curve as : elasticity * [ ln ] ( demandCurveFunction(step) )
     * @param step The step for which to calculate parameters value
     * @return Parameter's value that will be summed for calculating total consumer's consumption
     */
    public float valueFor(int step);

    /**
     * Overrides the previous method if the input is a float number. Mostly used when the parameter is
     * social and the input value is the sum of weights.
     * Calculates parameter's value for the Demand Curve as :
     * elasticity * [ ln ] (demandCurveFunction(weights) )
     * @param x The step for which to calculate parameters value
     * @return Parameter's value that will be summed for calculating total consumer's consumption
     */
    public float valueFor(float x);

    public String toString();
}

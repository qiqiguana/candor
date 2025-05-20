package jahuwaldt.plot;

/**
 *  <p> This object provides linear scaling for plot axes.
 *  </p>
 *
 *  <p>  Modified by:  Joseph A. Huwaldt  </p>
 *
 * @author Joseph A. Huwaldt   Date:  September 13, 2000
 * @version January 10, 2001
 */
public class LinearAxisScale extends Object implements PlotAxisScale {

    //	Debug flag.
    private static final boolean DEBUG = false;

    /**
     *  The transformation function used to scale the data
     *  plotted against this axis.
     *  This axis uses a linear scaling function:  f(a) = a.
     */
    public final double func(double a);

    /**
     *  Method that returns the default lower bounds for
     *  this axis scale.  Returns -1.0.
     */
    public double lowerBounds();

    /**
     *  Method that returns the default upper bounds for
     *  this axis scale.  Returns 1.0.
     */
    public double upperBounds();

    /**
     *  Method that returns an AxisLimitData object that contains
     *  the preferred axis limits and tick mark spacing for the
     *  specified range of data values for this linear axis scale.
     *
     *  @param  aLB  The lower bounds of the data plotted on this axis.
     *  @param  aUB  The upper bounds of the data plotted on this axis.
     */
    public AxisLimitData findGoodLimits(double aLB, double aUB);

    /**
     *  Find the position and size (in screen coordinates) of tick
     *  marks for a given axis scale.
     *
     *  @param  quantum   Tick mark step size for the axis using this scale.
     *  @param  aLB       Lower bounds of axis using this scale.
     *  @param  aUB       Upper bounds of axis using this scale.
     *  @param  xA        Scaling coefficient for this axis.
     *  @param  xB        Scaling coefficient for this axis.
     *  @return An TickMarkData object containing the tick mark positions, lengths,
     *          and data values at each tick mark.
     */
    public TickMarkData calcTickMarks(double quantum, double aLB, double aUB, double xA, double xB);

    /**
     *  Adjust the upper and lower axis bounds, if necissary, to allow
     *  room for error bars on the specified data point.  New bounds
     *  returned in "output" object.
     *
     *  @param datum  The data point we are bounds checking.
     *  @param aUB    The current upper bounds.
     *  @param aLB    The current lower bounds.
     *  @param output An AxisLimitData structure for passing the new upper and
     *                lower bounds to the calling routine.
     */
    public void adjustForErrorBars(PlotDatum datum, double aUB, double aLB, AxisLimitData output);

    private double modceil(double f, double t);

    private double modfloor(double f, double t);
}

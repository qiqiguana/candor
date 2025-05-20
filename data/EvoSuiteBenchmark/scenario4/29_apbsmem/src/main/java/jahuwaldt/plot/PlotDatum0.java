package jahuwaldt.plot;

import java.awt.Color;

/**
 *  <p> This class represents a single data point on a plot
 *      and includes all the information required by each point.
 *  </p>
 *
 *  <p>  Modified by:  Joseph A. Huwaldt  </p>
 *
 * @author Joseph A. Huwaldt   Date:  June 1, 2000
 * @version November 20, 2000
 */
public class PlotDatum extends Object implements Cloneable, java.io.Serializable {

    /**
     *  The X and Y coordinate points for this datum.
     */
    public double x, y;

    /**
     *  The error on the Y value.
     */
    private double yErr;

    /**
     *  A flag that indicates if there is an error bar.
     */
    private boolean errBarFlg = false;

    /**
     *  Flag that indicates if this point connects to the previous point.
     */
    private boolean conFlg;

    /**
     *  The line color used to connect this point to the previous one.
     */
    private Color lineColor = Color.black;

    /**
     *  The plot symbol used by this datum.
     */
    private PlotSymbol symbol = null;

    /**
     *  Create a new datum (plot coordinate point) given the specified
     *  X and Y values.  This datum will, by default, have no error bar.
     *
     *  @param xValue  The X coordinate value for this datum point.
     *  @param yValue  The Y coordinate value for this datum point.
     *  @param connected  A flag that indicates that this datum is connected
     *                    to the previous one if true, no line is drawn
     *                    to the previous datum if false.
     */
    public PlotDatum(double xValue, double yValue, boolean connected) {
    }

    /**
     *  Create a new datum (plot coordinate point) given the specified
     *  X and Y values and the given plot symbol.  This datum will, by
     *  default, have no error bar.
     *
     *  @param xValue  The X coordinate value for this datum point.
     *  @param yValue  The Y coordinate value for this datum point.
     *  @param connected  A flag that indicates that this datum is connected
     *                    to the previous one if true, no line is drawn
     *                    to the previous datum if false.
     *  @param  symbol  The plot symbol to be used for this data point.
     */
    public PlotDatum(double xValue, double yValue, boolean connected, PlotSymbol symbol) {
    }

    /**
     *  Return the X coordinate value of this point.
     */
    public double getX();

    /**
     *  Return the Y coordinate value of this point.
     */
    public double getY();

    /**
     *  Set the X coordinate value of this point.
     */
    public void setX(double value);

    /**
     *  Set the Y coordinate value of this point.
     */
    public void setY(double value);

    /**
     *  Set the error on Y value.
     */
    public void setYError(double err);

    /**
     *  Get the error on Y value.
     */
    public double getYError();

    /**
     *  Returns true if this data point has an error bar.
     */
    public boolean hasErrorBar();

    /**
     *  Set if this datum is connected to the previous one by a line or not.
     */
    public void setConnected(boolean flag);

    /**
     *  Return true if this datum is connected to the previous one by a line
     *  and false if it is not.
     */
    public boolean connected();

    /**
     *  Set the plot symbol used for this datum.
     */
    public void setPlotSymbol(PlotSymbol symbol);

    /**
     *  Returns a reference to the plot symbol used by
     *  this datum.
     */
    public PlotSymbol getPlotSymbol();

    /**
     *  Set the color used for the line connecting this datum
     *  to the previous one.  If null is passed, the line is
     *  drawn in black.
     */
    public void setLineColor(Color color);

    /**
     *  Return the color to be used for drawing the line
     *  connecting this datum to the previous.
     */
    public Color getLineColor();

    /**
     *  Make a copy of this PlotDatum object.
     *
     *  @return  Returns a clone of this object.
     */
    public Object clone();
}

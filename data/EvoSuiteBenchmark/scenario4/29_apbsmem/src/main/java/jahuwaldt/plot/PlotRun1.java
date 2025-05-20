package jahuwaldt.plot;

import java.awt.Color;
import java.util.*;

/**
 *  <p> This class represents a single run of data in a plot.
 *      A run is an array or list of PlotDatum objects.
 *  </p>
 *
 *  <p>  Modified by:  Joseph A. Huwaldt  </p>
 *
 * @author Joseph A. Huwaldt   Date:  September 13, 2000
 * @version December 12, 2000
 */
public class PlotRun extends AbstractList implements Cloneable, java.io.Serializable {

    /**
     *  The plot datum objects are stored in an ArrayList.
     */
    private List data = new ArrayList();

    /**
     *  Create an empty run that contains no data.
     */
    public PlotRun() {
    }

    /**
     *  Create a run that contains the specified array of PlotDatum objects.
     *
     *  @param run  An array of PlotDatum objects that make up a run of data
     *              to be plotted.
     */
    public PlotRun(PlotDatum[] run) {
    }

    /**
     *  Create a run from a set of Java arrays for the X & Y data.
     *
     *  @param  xArr  An array containing the X coordinates of the data points
     *                to be plotted.
     *  @param  yArr  An array containing the Y coordinates of the data points
     *                to be plotted.
     *  @param  connectFlg  Set to true to have the points in the X & Y arrays
     *                      connected by a line, false for them to not be connected.
     *  @param  symbol      The plot symbol to use for the plotted points.
     *
     *  @throws NullPointerException if either array is null.
     *  @throws ArrayIndexOutOfBoundsException if the X and Y arrays are not the
     *          same length.
     */
    public PlotRun(double[] xArr, double[] yArr, boolean connectFlg, PlotSymbol symbol) {
    }

    /**
     *  Create a run that contains the PlotDatum objects in the specified
     *  Collection.
     *
     *  @param data  An Collection containing PlotDatum objects.
     */
    public PlotRun(Collection run) {
    }

    /**
     *  Return the minimum X value of the data contained in this run.
     */
    public double getMinX();

    /**
     *  Return the maximum X value of the data contained in this run.
     */
    public double getMaxX();

    /**
     *  Return the minimum Y value of the data contained in this run.
     */
    public double getMinY();

    /**
     *  Return the maximum Y value of the data contained in this run.
     */
    public double getMaxY();

    /**
     *  Use this method to change the plot symbol used by all
     *  the plot data points in this run.
     */
    public void setPlotSymbol(PlotSymbol symbol);

    /**
     *  Use this method to change the line color used by all
     *  the plot data points in this run.
     */
    public void setLineColor(Color color);

    /**
     *  Returns the number of PlotDatum objects in this run.
     *
     *  @return The number of plot data objects in this run.
     */
    public int size();

    /**
     *  Returns the PlotDatum object at the specified position
     *  in this run.
     *
     *  @param   index  The index of the plot data object to return.
     *  @return  The PlotDatum object at the specified position
     *           in this run.
     */
    public Object get(int index);

    /**
     *  Replaces the plot data element at the specified position
     *  in this run with the specified datum.
     *
     *  @param   index   The index of the data element to replace.
     *  @param   element The datum to be stored a the specified position.
     *  @return  The datum previously at the specified position in this run.
     *  @throws  ClassCastException - if the specified element is not a
     *                                PlotDatum type object.
     */
    public Object set(int index, Object element);

    /**
     *  Inserts the specified plot data element at the specified
     *  position in this run.  Shifts the plot data element
     *  currently at that position (if any) and any subsequent
     *  data elements to the right (adds one to their indices).
     *
     *  @param  index   Index at which the specified datum is to be
     *                  inserted.
     *  @param  element PlotDatum object to be inserted.
     *  @throws ClassCastException - if the specified element is not a
     *                               PlotDatum type object.
     */
    public void add(int index, Object element);

    /**
     *  Remove the plot data object at the specified position in
     *  this run.  Shifts any subsequent data elements
     *  to the left (subtracts one from their indices).  Returns the
     *  data element that was removed from this run.
     *
     *  @param   index  The index of the plot data element to remove.
     *  @return  The PlotDatum object previously at the specified position.
     */
    public Object remove(int index);

    /**
     *  Removes all the plot data elements from this run.
     *  The run will be empty after this call returns
     *  (unless it throws an exception).
     */
    public void clear();

    /**
     *  Return an enumeration of all the plot data elements in
     *  this run.
     *
     *  @return An interation of all the PlotDatum objects in this run.
     */
    public Iterator iterator();

    /**
     *  Make a copy of this PlotRun object.
     *
     *  @return  Returns a clone of this object.
     */
    public Object clone();
}

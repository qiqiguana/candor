package jahuwaldt.plot;

import java.util.*;

/**
 *  <p> An object used to generate a list of contour lines
 *      or paths from a set of gridded three dimensional data.
 *  </p>
 *
 *  <p> Based on contour_plot.c from NeXTcontour1.4 by Thomas H. Pulliam,
 *      pulliam@rft29.nas.nasa.gov, MS 202A-1 NASA Ames Research Center,
 *      Moffett Field, CA 94035.
 *      I don't know how the original Fortran code looked like or where it came from,
 *      other than that NeXTcontour1.4 is based on Pieter Bunings' PLOT3D package
 *      for Computational Fluid Dynamics.
 *  </p>
 *
 *  <p> Ported from C to Java by Joseph A. Huwaldt, November 16, 2000.  </p>
 *
 *  <p>  Modified by:  Joseph A. Huwaldt  </p>
 *
 * @author Joseph A. Huwaldt   Date:  November 11, 2000
 * @version November 23, 2000
 */
public class ContourGenerator {

    //	Debug flag.
    private static final boolean DEBUG = false;

    //	Error messages.
    private static final String kCancelMsg = "Method ContourGenerator.getContours() canceled by user.";

    private static final String kInconsistantArrMsg = "Inconsistant array sizes.";

    private static final String kArrSizeMsg = "Data arrays must have more than one row or column.";

    private static final String kNegLogDataMsg = "Function data must be > 0 for logarithmic intervals.";

    //	Path buffer size.
    private static final int kBufSize = 1000;

    //	The minimum number of points allowed in a contour path.
    private static final int kMinNumPoints = 3;

    //	A list of contour paths.
    private List pathList = new ArrayList();

    //	A flag to indicate that the contours have been computed or not.
    private boolean cCalculated = false;

    //	Data arrays used for generating the contours.
    private double[][] xArray, yArray, funcArray;

    //	Data arrays used when generating contours for 1D X & Y arrays.
    private double[] xArr1D, yArr1D;

    //	Array of contour attributes, one for each contour level.
    private ContourAttrib[] cAttr;

    //	The fraction of the task that is completed.
    private float fracComplete = 0;

    /**
     *  Used to indicate that the user wishes to cancel the calculation
     *  of contours.
     */
    private boolean isCanceled = false;

    //	Variables in the original FORTRAN program.
    private double[] pathbufxt, pathbufyt;

    private int[] pathbufia;

    //	lnstrt=1 indicates starting a new line.
    private int lnstrt;

    private int ignext;

    //	Current contour level index.
    private int icont;

    //	The current contour level.
    private double cont;

    //	i & j start and end index values.
    private int iss, iee, jss, jee;

    //	ima tells which boundary region we are on.
    private int ima;

    //	Index to last element in the IA list.
    private int iae;

    private int ibeg, jbeg;

    //	Indexes into data arrays.
    private int gi, gj;

    //	Data value at i,j in data array.
    private double fij;

    //	Indicates current direction.
    private int idir;

    //	Number of points in current contour line.
    private int np = 0;

    //	Starting point of a contour line.
    private double wx = 0, wy = 0;

    /**
     *  Construct a ContourGenerator object using the specified data arrays
     *  and the specified attribute array.  This constructor allows you
     *  to use data on an uneven X, Y grid.
     *
     *  @param  xArr   2D array containing the grid x coordinate data.
     *  @param  yArr   2D array containing the grid y coordinate data.
     *  @param  fArr   2D array containing the grid function (z) data.
     *  @param  cAttr  Array containing attributes of the contour levels.
     */
    public ContourGenerator(double[][] xArr, double[][] yArr, double[][] fArr, ContourAttrib[] cAttr) {
    }

    /**
     *  Construct a ContourGenerator object using the specified data arrays
     *  and the specified attribute array.  This constructor allows you
     *  to use data on an evenly spaced grid where "X" values are invarient
     *  with "Y" and "Y" values are invarient with "X".  This often occures
     *  where the data is on an evenly spaced cartesian grid.
     *
     *  @param  xArr   1D array containing the grid x coordinate data.
     *  @param  yArr   1D array containing the grid y coordinate data.
     *  @param  fArr   2D array containing the grid function (z) data.
     *  @param  cAttr  Array containing attributes of the contour levels.
     */
    public ContourGenerator(double[] xArr, double[] yArr, double[][] fArr, ContourAttrib[] cAttr) {
    }

    /**
     *  Construct a ContourGenerator object using the specified data arrays.
     *  Contour attributes, including the interval, are generated
     *  automatically.  This constructor allows you to use data on an
     *  uneven X, Y grid.
     *
     *  @param  xArr   2D array containing the grid x coordinate data.
     *  @param  yArr   2D array containing the grid y coordinate data.
     *  @param  fArr   2D array containing the grid function (z) data.
     *  @param  nc     The number of contour levels to generate.
     *  @param  logInterval  Uses a logarithmic contour interval if true, and
     *                       uses a linear interval if false.
     */
    public ContourGenerator(double[][] xArr, double[][] yArr, double[][] fArr, int nc, boolean logInterval) {
    }

    /**
     *  Construct a ContourGenerator object using the specified data arrays.
     *  Contour attributes, including the interval, are generated
     *  automatically.  This constructor allows you
     *  to use data on an evenly spaced grid where "X" values are invarient
     *  with "Y" and "Y" values are invarient with "X".  This often occures
     *  where the data is on an evenly spaced cartesian grid.
     *
     *  @param  xArr   1D array containing the grid x coordinate data.
     *  @param  yArr   1D array containing the grid y coordinate data.
     *  @param  fArr   2D array containing the grid function (z) data.
     *  @param  nc     The number of contour levels to generate.
     *  @param  logInterval  Uses a logarithmic contour interval if true, and
     *                       uses a linear interval if false.
     */
    public ContourGenerator(double[] xArr, double[] yArr, double[][] fArr, int nc, boolean logInterval) {
    }

    /**
     *  Generate the contour paths and return them as an array
     *  of ContourPath objects. If there is a lot of data, this method
     *  method may take a long time, so be patient.  Progress can be
     *  checked from another thread by calling "getProgress()".
     *
     *  @return An array of contour path objects.
     *  @throws InterruptedException if the user cancels this process
     *          (by calling "cancel()" from another thread).
     */
    public ContourPath[] getContours() throws InterruptedException;

    /**
     *  Returns true if the contour generation process is done.  False if it is not.
     */
    public boolean done();

    /**
     *  Call this method to cancel the generation of contours.
     */
    public void cancel();

    /**
     *  Returns the progress of the currently executing contour generation
     *  process: 0.0 (just starting) to 1.0 (done).
     */
    public float getProgress();

    /**
     *  Find contour intervals that are linearly spaced through the data.
     */
    private void findLinearIntervals(int nc);

    /**
     *  Find contour intervals that are logarithmically spaced through the data.
     */
    private void findLogIntervals(int nc);

    /**
     *  Computes contour lines for gridded data and stores information about
     *  those contours.  The result of this routine is a list of contour lines
     *  or paths.
     */
    private void computeContours() throws InterruptedException;

    /**
     *  Flag points in IA where the the function increases through the contour
     *  level, not including the boundaries.  This is so we have a list of at least
     *  one point on each contour line that doesn't intersect a boundary.
     */
    private void FlagContourPassings();

    /**
     *  This function represents the block of code in the original
     *  FORTRAN program that comes after line 21.
     */
    private void Routine_L21();

    /**
     *  This function represents the block of code in the original
     *  FORTRAN program that comes after line 31.
     */
    private boolean Routine_L31();

    /**
     *  This function represents the block of code in the original
     *  FORTRAN program that comes after line 41.
     */
    private boolean Routine_L41();

    /**
     *  This function represents the block of code in the original
     *  FORTRAN program that comes after line 51.
     */
    private boolean Routine_L51();

    /**
     *  Do interpolation for X, Y coordinates.
     *
     *  This function represents the block of code in the original
     *  FORTRAN program that comes after line 60.
     */
    private void doInterpolation();

    /**
     *  Accumulate contour paths, as they are generated, into
     *  an overall list of contours.
     *
     *  @param  np      The number of points in the contour path buffers.
     *  @param  icont   The index to the current contour level.
     *  @param  x,y     Buffers containing x & y coordinates of contour points.
     *  @param  cAttr   The attributes for this particular contour level.
     */
    private void accumContour(int np, int icont, double[] x, double[] y, ContourAttrib cAttr);
}

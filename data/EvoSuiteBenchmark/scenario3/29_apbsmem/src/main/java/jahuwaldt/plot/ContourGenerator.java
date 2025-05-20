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

    /**
     *  Returns true if the contour generation process is done.  False if it is not.
     */
    public boolean done();
}

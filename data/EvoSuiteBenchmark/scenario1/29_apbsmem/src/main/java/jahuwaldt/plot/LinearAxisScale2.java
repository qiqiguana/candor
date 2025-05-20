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

    public AxisLimitData findGoodLimits(double aLB, double aUB) {
        //	The lower limit and tick mark spacing being calculated.
        double s = 0., r = 0.;
        //	Make sure we don't have a degenerate case.
        if (Math.abs(aUB - aLB) <= 0.000001) {
            if (aUB > 0.) {
                aUB = 2. * aUB;
                aLB = 0.;
            } else if (aLB < 0) {
                aLB = 2. * aLB;
                aUB = 0.;
            }
            if (Math.abs(aUB - aLB) <= 0.000001) {
                aLB = lowerBounds();
                aUB = upperBounds();
            }
        }
        if (DEBUG) {
            System.out.println("In findGoodLimits()...");
            System.out.println("   aLB = " + aLB + ", aUB = " + aUB);
        }
        //	Object used to return results.
        AxisLimitData limData = new AxisLimitData();
        boolean done = false;
        while (!done) {
            done = true;
            double ub = aUB;
            double lb = aLB;
            double delta = ub - lb;
            //	Scale up by s, a power of 10, so range (delta) exceeds 1.
            s = 1.;
            while (delta * s < 10.) s *= 10.;
            //	Find power of 10 quantum, r, such that delta/10 <= r < delta.
            r = 1. / s;
            while (10. * r < delta) r *= 10.;
            //	Set r=(1,2,5)*10**n so that 3-5 quanta cover range.
            if (r >= delta / 2.)
                r /= 2.;
            else if (r < delta / 5.)
                r *= 2.;
            limData.ub = modceil(ub, r);
            limData.lb = modfloor(lb, r);
            //	If lower bound is <= r and > 0, then repeat.
            if (limData.lb <= r && limData.lb > 0.) {
                aLB = 0.;
                done = false;
                //	If upper bound >= -r and < 0, then repeat.
            } else if (limData.ub >= -r && limData.ub < 0.) {
                aUB = 0.;
                done = false;
            }
        }
        //	Save off tick mark spacing.
        limData.quantum = r;
        if (DEBUG) {
            System.out.println("    limData.lb = " + limData.lb + ", limData.ub = " + limData.ub + ", limData.quantum = " + limData.quantum);
        }
        return limData;
    }
}

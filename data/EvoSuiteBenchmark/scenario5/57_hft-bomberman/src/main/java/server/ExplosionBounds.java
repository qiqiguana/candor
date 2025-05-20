package server;

import java.awt.Point;

/**
 * This class is used by the server to define the bounds of a bomb explosion.
 * It contains four integer fields indicating the min/max pixel value
 * in the given direction that is affected by the explosion. It further offers
 * a method to test if a given point lies within the defined bounds.
 *
 * @author Steffen
 */
public class ExplosionBounds {

    /**
     * Checks whether the given point lies within the bounds of the explosion.
     *
     * @param p The Point to be checked.
     * @return True if p lies within the bounds, false if not.
     */
    public boolean contain(Point p) {
        if (up <= p.y && down >= p.y && right >= p.x && left <= p.x) {
            return true;
        } else {
            return false;
        }
    }
}

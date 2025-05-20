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

    private int up;

    private int down;

    private int right;

    private int left;

    /**
     * Creates a new ExplosionBounds object. The four integer parameters define
     * the min/max pixel value in the given direction.
     *
     * @param up
     * @param down
     * @param right
     * @param left
     */
    public ExplosionBounds(int up, int down, int right, int left) {
    }

    public int getUp();

    public int getDown();

    public int getRight();

    public int getLeft();

    /**
     * Checks whether the given point lies within the bounds of the explosion.
     *
     * @param p The Point to be checked.
     * @return True if p lies within the bounds, false if not.
     */
    public boolean contain(Point p);
}

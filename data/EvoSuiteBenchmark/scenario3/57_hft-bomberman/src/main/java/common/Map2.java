package common;

import java.awt.Point;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Map-Class with all information needed for map use of iterators tile-iterator:
 * for(Tile tile : map) - powerup-iterator: for(PowerUp powerup :
 * map.powerupiterator)
 *
 * @author christian
 */
public class Map implements Iterable<Tile>, Serializable {

    /**
     * gets start-point-coordinates of player no. if not set, return coordinates
     * of field 1/1
     *
     * @param playerNo -
     *            number of player
     * @return Point with coordinates
     */
    public Point getStartPoint(int playerNo);
}

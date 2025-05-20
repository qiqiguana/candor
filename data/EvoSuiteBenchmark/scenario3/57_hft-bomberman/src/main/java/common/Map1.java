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
     * return MapTile object at Position x, y
     *
     * @param x -
     *            number of horizontal tile
     * @param y -
     *            number of vertical tile
     * @return MapTile object
     */
    public Tile getTile(int x, int y);
}

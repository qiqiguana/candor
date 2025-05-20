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

    public Point getStartPoint(int playerNo) {
        // startpoint for player is not set, default 1/1
        if (playerNo > StartPoint.length) {
            return new Point(1 * Constants.TILE_BORDER + Constants.TILE_BORDER / 2, // field
            1 * Constants.TILE_BORDER + Constants.TILE_BORDER / 2);
            // 1/1
        } else {
            Point retPoint = StartPoint[(playerNo - 1)];
            retPoint.x = retPoint.x * Constants.TILE_BORDER + 20;
            retPoint.y = retPoint.y * Constants.TILE_BORDER + 20;
            return retPoint;
        }
    }
}

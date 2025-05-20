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

    private static final long serialVersionUID = 1L;

    // flags if tiles, powerups and startpoints need to be read and set
    private boolean settiles = true;

    private boolean setpowerups = true;

    private boolean setstartpoints = true;

    // 2 dimensional game board
    private Object[][] MapGrid;

    private Vector<PowerUp> Powerups = new Vector<PowerUp>();

    private Point[] StartPoint;

    private Point area;

    private String name;

    private String imageSet;

    private String version;

    private String author;

    private Date date;

    private int difficulty;

    private int maxPlayers;

    private String filePath;

    private int itemId = 0;

    // iterator for powerups
    public Map.PowerUpIterator powerupiterator;

    /**
     * creates map-object with empty Tile objects
     *
     * @param xmlFile -
     *            path to xml file
     */
    public Map(String xmlFile) {
    }

    /**
     * creates map-object with or without Tile objects
     *
     * @param xmlFile -
     *            path to xml file
     * @param info -
     *            Boolean (noTile) if tiles should be read (false) or not (true)
     */
    public Map(String xmlFile, boolean settiles, boolean setpowerups, boolean setstartpoints) {
    }

    /**
     * initiate map and create MapReader Object
     *
     * @param xmlFile
     */
    private void mapInit(String xmlFile);

    /**
     * overwrite specific MapTile at Index x, y
     *
     * @param x -
     *            number of horizontal tile
     * @param y -
     *            number of vertical tile
     * @param tile -
     *            MapTile object
     */
    public void setTileByIndex(int x, int y, Tile tile);

    /**
     * return MapTile object at Index x, y
     *
     * @param x -
     *            number of horizontal tile
     * @param y -
     *            number of vertical tile
     * @return MapTile object
     */
    public Tile getTileByIndex(int x, int y);

    /**
     * overwrite specific MapTile at Position x, y
     *
     * @param x -
     *            number of horizontal tile
     * @param y -
     *            number of vertical tile
     * @param tile -
     *            MapTile object
     */
    public void setTile(int x, int y, Tile tile);

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

    /**
     * Resets a tile to "empty" tile at specific position
     *
     * @param x -
     *            number of horizontal tile
     * @param y -
     *            number of vertical tile
     */
    public void resetTileByIndex(int x, int y);

    /**
     * Resets a tile to "empty" tile at specific coordinates
     *
     * @param x -
     *            coordinates of horizontal tile
     * @param y -
     *            coordinates of vertical tile
     */
    public void resetTile(int x, int y);

    /**
     * overwrite specific PowerUp at Index x, y
     *
     * @param x -
     *            number of horizontal tile
     * @param y -
     *            number of vertical tile
     * @param tile -
     *            PoweUp object
     */
    public void addPowerup(PowerUp powerup);

    /**
     * retrieves a Vector with all PowerUps
     *
     * @return
     */
    public Vector<PowerUp> getPowerups();

    /**
     * sets a bunch of properties to map
     *
     * @param mapName -
     *            Name of map
     * @param mapVersion -
     *            Version of map
     * @param mapAuthor -
     *            Name of author
     * @param mapDate -
     *            Date of map
     * @param mapDifficulty -
     *            Difficulty of map
     * @param mapImageSet -
     *            Default image set
     * @param mapMaxPlayers -
     *            Number of max players
     * @param mapFilePath -
     *            path to map xml file
     */
    public void setProperties(String mapName, String mapVersion, String mapAuthor, Date mapDate, int mapDifficulty, String mapImageSet, int mapMaxPlayers, String mapFilePath);

    public String getImageSet();

    public String getVersion();

    public String getAuthor();

    public Date getDate();

    public int getDifficulty();

    public String getName();

    public int getMaxPlayers();

    public String getFilePath();

    /**
     * sets measures of map
     *
     * @param x -
     *            number of horizontal fields
     * @param y -
     *            number of vertical fields
     */
    public void setArea(int x, int y);

    /**
     * retrieve measures of map, number of horizontal and vertical fields
     *
     * @return
     */
    public Point getArea();

    /**
     * sets the start-point-position to map
     *
     * @param StPo -
     *            array of start-point-positions
     */
    public void setStartPoints(Point[] StPo);

    /**
     * gets array of start-point positions (not coordinates)
     *
     * @return
     */
    public Point[] getStartPoints();

    /**
     * gets start-point-coordinates of player no. if not set, return coordinates
     * of field 1/1
     *
     * @param playerNo -
     *            number of player
     * @return Point with coordinates
     */
    public Point getStartPoint(int playerNo);

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
    @Override
    public Iterator<Tile> iterator();

    /**
     * iterator for PowerUp Objects in map
     *
     * @author christian
     */
    public class PowerUpIterator implements Iterable<PowerUp>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Iterator<PowerUp> iterator() {
            return Powerups.iterator();
        }
    }
}

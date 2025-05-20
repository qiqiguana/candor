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
 * 
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
		mapInit(xmlFile);
	}

	/**
	 * creates map-object with or without Tile objects
	 * 
	 * @param xmlFile -
	 *            path to xml file
	 * @param info -
	 *            Boolean (noTile) if tiles should be read (false) or not (true)
	 */
	public Map(String xmlFile, boolean settiles, boolean setpowerups,
			boolean setstartpoints) {
		this.settiles = settiles;
		this.setpowerups = setpowerups;
		this.setstartpoints = setstartpoints;
		mapInit(xmlFile);
	}

	/**
	 * initiate map and create MapReader Object
	 * 
	 * @param xmlFile
	 */
	private void mapInit(String xmlFile) {
		// create Map-Reader an read xml into map
		MapReader xml = new MapReader(xmlFile);
		area = xml.getArea(); // get map dimensions
		// MapXMLReader writes read properties to map-object

		xml.setMapProperies(this);

		// Tiles +++++++++++++++++++++++++++++++++++++++++++++++++++
		// create map grid with "empty" objects if settiles is true
		if (settiles) {
			MapGrid = new Object[area.x][area.y];
			for (int i = 0; i < area.x; i++) {
				for (int j = 0; j < area.y; j++) {
					Tile tile = new Tile(new Point(
							(area.x * Constants.TILE_BORDER),
							(area.y * Constants.TILE_BORDER)));
					MapGrid[i][j] = tile;
				}
			}

			xml.setTiles(this); // overwrite specific tiles from xml to map

			// sets tile to wall if type not set
			for (Tile tile : this) {
				if (tile.getType() == null) {
					tile.setType("wall");
				}
				tile.setId(itemId++);
			}
		}
		// Powerups ++++++++++++++++++++++++++++++++++++++++++++++++
		// create map grid with "empty" powerup objects if setpowerups is true
		if (setpowerups) {
			powerupiterator = new PowerUpIterator();
			// overwrite specific powerups from xml to map
			xml.setPowerups(this);
		}

		// Starpoints ++++++++++++++++++++++++++++++++++++++++++++++++
		// set start points to map
		if (setstartpoints) {
			xml.setStartPoints(this);
		}
	}

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
	public void setTileByIndex(int x, int y, Tile tile) {
		MapGrid[x][y] = tile;
	}

	/**
	 * return MapTile object at Index x, y
	 * 
	 * @param x -
	 *            number of horizontal tile
	 * @param y -
	 *            number of vertical tile
	 * @return MapTile object
	 */
	public Tile getTileByIndex(int x, int y) {
		return (Tile) MapGrid[x][y];
	}

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
	public void setTile(int x, int y, Tile tile) {
		MapGrid[(int) Math.floor((double) x / Constants.TILE_BORDER)][(int) Math
				.floor((double) y / Constants.TILE_BORDER)] = tile;
	}

	/**
	 * return MapTile object at Position x, y
	 * 
	 * @param x -
	 *            number of horizontal tile
	 * @param y -
	 *            number of vertical tile
	 * @return MapTile object
	 */
	public Tile getTile(int x, int y) {
		return (Tile) MapGrid[(int) Math.floor((double) x
				/ Constants.TILE_BORDER)][(int) Math.floor((double) y
				/ Constants.TILE_BORDER)];
	}

	/**
	 * Resets a tile to "empty" tile at specific position
	 * 
	 * @param x -
	 *            number of horizontal tile
	 * @param y -
	 *            number of vertical tile
	 */
	public void resetTileByIndex(int x, int y) {
		MapGrid[x][y] = new Tile(new Point(x * Constants.TILE_BORDER, y
				* Constants.TILE_BORDER));
	}

	/**
	 * Resets a tile to "empty" tile at specific coordinates
	 * 
	 * @param x -
	 *            coordinates of horizontal tile
	 * @param y -
	 *            coordinates of vertical tile
	 */
	public void resetTile(int x, int y) {
		MapGrid[(int) Math.floor((double) x / Constants.TILE_BORDER)][(int) Math
				.floor((double) y / Constants.TILE_BORDER)] = new Tile(
				new Point(x, y));
	}

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
	public void addPowerup(PowerUp powerup) {
		powerup.setId(itemId++);
		Powerups.addElement(powerup);
	}

	/**
	 * retrieves a Vector with all PowerUps
	 * 
	 * @return
	 */
	public Vector<PowerUp> getPowerups() {
		return this.Powerups;
	}

	// Getters and Setter for map properties
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
	public void setProperties(String mapName, String mapVersion,
			String mapAuthor, Date mapDate, int mapDifficulty,
			String mapImageSet, int mapMaxPlayers, String mapFilePath) {
		this.name = mapName;
		this.version = mapVersion;
		this.author = mapAuthor;
		this.date = mapDate;
		this.difficulty = mapDifficulty;
		this.imageSet = mapImageSet;
		this.maxPlayers = mapMaxPlayers;
		this.filePath = mapFilePath;
	}

	public String getImageSet() {
		return imageSet;
	}

	public String getVersion() {
		return version;
	}

	public String getAuthor() {
		return author;
	}

	public Date getDate() {
		return date;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public String getName() {
		return name;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public String getFilePath() {
		return filePath;
	}

	/**
	 * sets measures of map
	 * 
	 * @param x -
	 *            number of horizontal fields
	 * @param y -
	 *            number of vertical fields
	 */
	public void setArea(int x, int y) {
		Point area = new Point();
		area.x = x;
		area.y = y;
		this.area = area;
	}

	/**
	 * retrieve measures of map, number of horizontal and vertical fields
	 * 
	 * @return
	 */
	public Point getArea() {
		return this.area;
	}

	/**
	 * sets the start-point-position to map
	 * 
	 * @param StPo -
	 *            array of start-point-positions
	 */
	public void setStartPoints(Point[] StPo) {
		this.StartPoint = StPo;
	}

	/**
	 * gets array of start-point positions (not coordinates)
	 * 
	 * @return
	 */
	public Point[] getStartPoints() {
		return this.StartPoint;
	}

	/**
	 * gets start-point-coordinates of player no. if not set, return coordinates
	 * of field 1/1
	 * 
	 * @param playerNo -
	 *            number of player
	 * @return Point with coordinates
	 */
	public Point getStartPoint(int playerNo) {
		// startpoint for player is not set, default 1/1
		if (playerNo > StartPoint.length) {
			return new Point(1 * Constants.TILE_BORDER + Constants.TILE_BORDER
					/ 2, 1 * Constants.TILE_BORDER + Constants.TILE_BORDER / 2); // field
			// 1/1

		} else {
			Point retPoint = StartPoint[(playerNo - 1)];
			retPoint.x = retPoint.x * Constants.TILE_BORDER + 20;
			retPoint.y = retPoint.y * Constants.TILE_BORDER + 20;
			return retPoint;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Tile> iterator() {
		return new Iterator<Tile>() {

			private int idx = 0;
			int width = MapGrid.length;
			int height = MapGrid[0].length;
			private int size = width * height;

			@Override
			public boolean hasNext() {
				return idx < size;
			}

			@Override
			public Tile next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				Tile tile = (Tile) MapGrid[idx % width][idx / width];
				idx++;
				return tile;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */

	/**
	 * iterator for PowerUp Objects in map
	 * 
	 * @author christian
	 * 
	 */
	public class PowerUpIterator implements Iterable<PowerUp>, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public Iterator<PowerUp> iterator() {
			return Powerups.iterator();
		}
	}
}

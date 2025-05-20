package common;

public class Constants {

	public static final String DEFAULT_SERVER = "localhost";
	public static final int DEFAULT_SERVER_PORT = 4747;
	public static final int COUNTDOWN = 10;
	
	public static final String MAP_PATH = "resources/map/";
	public static final String PROPERTIES_DEFAULTFILE = "/properties.xml";
	public static final String PROPERTIES_FILE = System.getProperty("user.home")+"/fubarman_properties.xml";
	
	public static final int EXPLOSION_WIDTH = 30;
	public static final int EXPLOSION_LENGTH = 40;
	public static final int TILE_BORDER = 40;
	public static final int HALF_TILE = TILE_BORDER / 2;
	public static final int POWERUP_BORDER = 38;
	public static final int HALF_POWERUP = POWERUP_BORDER / 2;
	
	
	/**
	 * How long a GameRound goes.
	 */
	public static final long time = 360000;
	
	/**
	 * The maximum number of bombs a fully upgraded player can plant.
	 */
	public static final int MAX_BOMBS = 20;
	
	/**
	 * The minimum number of bombs a player carries.
	 */
	public static final int MIN_BOMBS = 1;
	
	public static final int BOMB_DIAMETER = 5;
	
}

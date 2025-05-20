package messages.global;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Data structure for transfering map information to the clients.
 * This class is used when a client has sent a GET_MAP_LIST InfoRequest
 * because it wants to create a new session.
 * 
 * @author Steffen
 * @see InfoRequestMsg
 * @see MapListMsg
 * 
 */
public class MapInfo implements Serializable {
	
	private String file;
	private String name;
	private int maxPlayers;
	private ImageIcon preview;

	public void setPreview(ImageIcon preview) {
		this.preview = preview;
	}

	/**
	 * Creates a new MapInfo object. Please use setPreview() to add a small preview
	 * image if desired.
	 * 
	 * @param name
	 * @param maxPlayers
	 */
	public MapInfo(String file, String name, int maxPlayers) {
		super();
		this.file = file;
		this.name = name;
		this.maxPlayers = maxPlayers;
	}

	/**
	 * @return The file name of the map
	 */
	public String getFile(){
		return file;
	}
	
	/**
	 * @return The name of the map.
	 */
	public String getName() {
		return name;
	}
	
	

	/**
	 * @return The maximum number of players allowed on the map.
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * @return A small(!) preview image of the map.
	 */
	public ImageIcon getPreview() {
		return preview;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + maxPlayers + ") " + name;
	}

}

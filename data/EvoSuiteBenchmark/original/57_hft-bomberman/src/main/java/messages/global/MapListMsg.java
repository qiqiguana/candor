package messages.global;

import java.util.LinkedList;
import java.util.List;

import client.BomberClient;
import client.gui.StartFrame;

/**
 * This message is sent to a client when it enters the create game (session)
 * dialog, giving it information about the maps available on the server.
 * 
 * @author Steffen, Andi
 * @see MapInfo
 */
public class MapListMsg implements GlobalServerMsg {
	
	private List<MapInfo> maps = new LinkedList<MapInfo>();

	public MapListMsg(List<MapInfo> maps) {
		super();
		this.maps = maps;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberClient bomberClient) {
		bomberClient.setAvailableMaps(maps);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + ": " + maps.size() + " maps";
	}
	
}

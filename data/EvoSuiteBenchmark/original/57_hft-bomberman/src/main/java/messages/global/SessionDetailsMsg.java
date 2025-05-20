package messages.global;

import java.util.List;

import javax.swing.ImageIcon;

import server.BomberServer;
import server.ClientInfo;
import server.ServerGameSession;

/**
 * This message is sent to the server, when a client has created a new game (session).
 * A reply in the shape of a GlobalStateMsg is sent back to the client, indicating
 * whether the creation of the game (session) was successful or not.
 * 
 * @author Steffen, Björn , Andi
 * @see GlobalStateMsg
 * 
 */
public class SessionDetailsMsg implements GlobalClientMsg {
	/**
	 * Spielname
	 * 
	 */
    private String gameName;
    
    /**
     * Mappe für das Spiel
     * 
     */
    private List<String> maps;
    
    /**
     * Number of players who are currently in this session.
     */
    private int nrOfPlayers;
    
    /**
     * Expected number of players for the session.
     */
    private int totalNrOfPlayers;
    
    /**
     * Anzahl der Runden
     * 
     */
    private int totalRounds;
    
    /**
     * Preview of the first map of the session.
     */
    private ImageIcon preview;

    /**
     * Creates a new SessionDetailsMsg. Please use setPreview() to add a small preview
     * image of the first map played in this session if desired.
     * 
     * @param gameName - Spielname
     * @param map - Mappe für das Spiel
     * @param nrOfPlayers - Anzahl der Teilnehmenden Spieler
     * @param totalNrOfPlayers - Expected number of players for this session. 
     * @param rounds - Anzahl der Runden
     */
    public SessionDetailsMsg(String gameName, List<String> maps, ImageIcon mapPreview, int nrOfPlayers, int totalNrOfPlayers, int rounds) {
        super();
        this.gameName = gameName;
        this.maps = maps;
        this.preview = mapPreview;
        this.nrOfPlayers = nrOfPlayers;
        this.totalNrOfPlayers = totalNrOfPlayers;
        this.totalRounds = rounds;
    }

	@Override
	public void execute(BomberServer bomberSrv, ClientInfo sender) {
		ServerGameSession newSession = bomberSrv.createSession(gameName, maps, preview, nrOfPlayers, totalRounds);

		// provide feedback to client whether creation of session was succesful or not
		if (newSession != null) {
			// TODO maybe remove SESSION_CREA... if unneeded
			sender.sendMsg(new GlobalStateMsg(GlobalStateMsg.SESSION_CREATION_SUCCESSFUL));
			sender.joinGameSession(newSession);
		} else {
			sender.sendMsg(new GlobalStateMsg(GlobalStateMsg.SESSION_NAME_TAKEN));
		}
	}

	public void setPreview(ImageIcon preview) {
		this.preview = preview;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return gameName;
	}



	public String getGameName() {
		return gameName;
	}



	public List<String> getMaps() {
		return maps;
	}



	public int getNrOfPlayers() {
		return nrOfPlayers;
	}



	public int getTotalNrOfPlayers() {
		return totalNrOfPlayers;
	}



	public int getTotalRounds() {
		return totalRounds;
	}



	public ImageIcon getPreview() {
		if(preview == null){
			//System.out.println("Preview ist leer");
			return new ImageIcon();
		}else{
			return preview;	
		}
		
	}
	
	
}


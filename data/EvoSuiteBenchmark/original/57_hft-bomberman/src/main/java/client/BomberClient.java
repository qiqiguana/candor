package client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import javax.swing.ImageIcon;

import messages.Message;
import messages.global.InfoRequestMsg;
import messages.global.JoinSessionMsg;
import messages.global.MapInfo;
import messages.global.SessionDetailsMsg;
import messages.round.ClientQuitRunningSessionMsg;

import org.apache.log4j.Logger;

import client.gui.StartFrame;
import client.network.ClientMsgReceiver;
import client.network.ClientMsgSender;

/**
 * This is central client class. It is responsible for globally scoped messages
 * and the creation of sessions.
 * 
 * @author andi
 * 
 */
public class BomberClient extends Observable {
	// for logging
	
	private static Logger logger = Logger.getLogger(BomberClient.class);

	/**
	 * The socket that is used to communicate with the server.
	 */
	public Socket server;

	/**
	 * The ip address of the server.
	 */
	private String serverName = "localhost";

	/**
	 * The port on the server to connect to.
	 */
	private int serverPort = 6666;

	/**
	 * The name of the player that is running this client.
	 */
	private String playerName;

	private ClientMsgReceiver msgReceiver;
	private ClientMsgSender msgSender;

	/**
	 * The id offset of this client. Generated ids will be in the range from
	 * idOffset to idOffset + 1000000.
	 */
	private int idOffset = -1;

	/**
	 * Singleton instance
	 */
	private static BomberClient instance;

	/**
	 * The currently used (and sole) session.
	 */
	private ClientGameSession currentSession;

	/**
	 * A list of sessions that are running on the server.
	 */
	private Vector<SessionDetailsMsg> availableSessions = new Vector<SessionDetailsMsg>();

	/**
	 * A list of maps that are availbale on the server.
	 */
	private Vector<MapInfo> availableMaps;

	private ClientGameSession requestedSession;

	/**
	 * @return The current game session.
	 */
	public ClientGameSession getCurrentSession() {
		return currentSession;
	}

	/**
	 * Creates a new BomberClient. Use getInstance() to aquire an instance.
	 * 
	 * @param playerName
	 *            The name of the player on this client.
	 */
	private BomberClient(String playerName) {
		this.playerName = playerName;
		availableMaps = new Vector<MapInfo>();
	}

	/**
	 * Returns the singleton.
	 * 
	 * @return The only BomberClient instance.
	 */
	public static BomberClient getInstance() {
		if (instance == null) {
			logger.info("Creating BomberClient singleton instance");
			instance = new BomberClient("client");
		}
		return instance;
	}

	/**
	 * Tries to connect to the server.
	 * 
	 * @throws RuntimeException
	 *             Thrown when the connection attempt was not successful.
	 */
	public boolean connectToSrv() {
		try {

			//System.out.println("Connecting...");
			logger.info("Establishing a connection ");
			server = new Socket(serverName, serverPort);

			msgSender = new ClientMsgSender(server);
			msgSender.start();

			msgReceiver = new ClientMsgReceiver(server, BomberClient.this);
			msgReceiver.start();

			msgSender.sendMsg(new InfoRequestMsg(
					InfoRequestMsg.GET_SESSION_LIST));

			logger.info("connected to " + serverName);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block

			// e.printStackTrace();
			logger.info("Connection failed");		
			return false;
		}
	}

	/**
	 * Sends a message to the server. The passed object will be serialized and
	 * transmitted to the server.
	 * 
	 * @param msg
	 *            The message to send.
	 */
	public void sendMsg(Message msg) {
		msgSender.sendMsg(msg);
	}

	/**
	 * Creates a new id that is guaranteed to be unique for this class.
	 * 
	 * @return The next valid id for this class.
	 */
	public int getNextId() {
		return ++idOffset;
	}

	/**
	 * Sets the id offset.
	 * 
	 * @param idOffset
	 *            to set
	 */
	public void setIdOffset(int idOffset) {
		this.idOffset = idOffset;
	}

	/**
	 * Sets the current session.
	 * 
	 * @param The
	 *            ClientGameSession to set.
	 */
	public void setCurrentSession(ClientGameSession currentSession) {
		this.currentSession = currentSession;
	}

	/**
	 * Create a new session on the server. The given arguments are rolled up
	 * into a SessionDetailsMsg and sent to the server.
	 * 
	 * @param name
	 *            The name for the new session.
	 * @param maps
	 *            A list of map names that will be used for this session
	 * @param totalPlayers
	 *            The total number of players.
	 * @param rounds
	 *            The number of rounds to play.
	 */
	public void createSession(String name, List<String> maps, ImageIcon mapPreview, int totalPlayers,
			int rounds) {
		SessionDetailsMsg sessionDetailsMsg = new SessionDetailsMsg(name, maps, mapPreview,
				totalPlayers, totalPlayers, rounds);
		this.requestedSession = new ClientGameSession(name, maps, mapPreview, totalPlayers,
				rounds, idOffset);
		msgSender.sendMsg(sessionDetailsMsg);
	}

	public void openSession() {
		requestedSession.setIdOffset(idOffset);
		setCurrentSession(requestedSession);
		logger.info("opened session: " + currentSession.getName());
	}

	/**
	 * Requests a list of active sessions on the server.
	 */
	public void requestSessionList() {
		InfoRequestMsg sessionRequest = new InfoRequestMsg(
				InfoRequestMsg.GET_SESSION_LIST);
		sendMsg(sessionRequest);
	}

	/**
	 * Requests a list of available maps from the server.
	 */
	public void requestMapList() {
		InfoRequestMsg mapRequest = new InfoRequestMsg(
				InfoRequestMsg.GET_MAP_LIST);
		sendMsg(mapRequest);
	}
	
	

	/**
	 * Returns a list of active sessions on the server. Invoke
	 * requestSessionList() to update this list.
	 * 
	 * @return
	 */
	public Vector<SessionDetailsMsg> getAvailableSessions() {
		return availableSessions;
	}

	/**
	 * @param sessionInfos
	 */
	public void setAvailableSessions(Vector<SessionDetailsMsg> sessionInfos) {
		availableSessions.clear();
		availableSessions.addAll(sessionInfos);
		// TODO observer
		StartFrame.getInstance().setAvailableSessions(availableSessions);
	}

	/**
	 * @param maps
	 */
	public void setAvailableMaps(List<MapInfo> maps) {
		availableMaps.clear();
		availableMaps.addAll(maps);
		// TODO observer
		StartFrame.getInstance().setAvailableMaps(availableMaps);
	}

	/**
	 * @return the availableMaps
	 */
	public Vector<MapInfo> getAvailableMaps() {
		return availableMaps;
	}

	/**
	 * Creates a session from the details specified in the given
	 * SessionDetailMsg.
	 * 
	 * @param session
	 *            The session to open on this client.
	 */
	public void createSession(SessionDetailsMsg sessionDetails) {
		ClientGameSession session = new ClientGameSession(sessionDetails
				.getGameName(), sessionDetails.getMaps(), sessionDetails.getPreview(), sessionDetails
				.getNrOfPlayers(), sessionDetails.getTotalRounds(), idOffset);
		requestedSession = session;
		JoinSessionMsg joinMsg = new JoinSessionMsg(session.getName(), playerName);
		msgSender.sendMsg(joinMsg);
	}

	/**
	 * Sets the server-name
	 * 
	 * @author Bj�rn
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * Sets the server-port
	 * 
	 * @author Bj�rn
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * @param sender
	 * @param msg
	 */
	public void addChatGlobal(String sender, String msg) {
		logger.info("omg asd rofl");
		StartFrame.getInstance().addChatMessageGlobal(sender, msg);
	}

	/**
	 * @param sender
	 * @param msg
	 */
	public void addChatSession(String sender, String msg) {
		StartFrame.getInstance().addChatMessageSession(sender, msg);
	}

	public void discardSessionRequest(){
		requestedSession = null;
	}
	
	/**
	 * Closes the current session.
	 */
	public void closeSession() {
		// TODO session highscore presentation
		currentSession = null;
		StartFrame sFrame = StartFrame.getInstance();
		sFrame.showGlobalLobby();
		
	}
	/**
	 * Closes the current session.
	 */
	public void closeSessionAndShowHighscores() {
		// TODO session highscore presentation
		currentSession = null;
		StartFrame sFrame = StartFrame.getInstance();
		sFrame.showHighscoreLobby();
		
	}

	/**
	 * Leave the current session.
	 */
	public void leaveSession() {
		if(currentSession != null){
			currentSession.leave();
		}
		currentSession = null;
		StartFrame sFrame = StartFrame.getInstance();
		sFrame.showGlobalLobby();
	}
	
	public void closeConnection() {
		msgReceiver.closeConnection();
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void requestGlobalScore() {
		msgSender.sendMsg(new InfoRequestMsg(
				InfoRequestMsg.GET_OVERALL_SCORE));

		
	}

	public void setGlobalscores(ArrayList scores) {
		StartFrame.getInstance().setGlobalScore(scores);
		
	}


	public void setRoundScore(HashMap<String, Integer> rndscore) {
		logger.info("setting round score");
		StartFrame.getInstance().updateRoundScoreData(rndscore);
	}

	public void setSessionScore(HashMap<String, Integer> rndscore) {
		logger.info("setting session score");
		StartFrame.getInstance().updateSessionScoreData(rndscore);
	}


    /**
     * @param i 
     * 
     */
    public void quitRunningSession(int playerId) {
        getCurrentSession().getCurrentRound().terminateRound();
        sendMsg(new ClientQuitRunningSessionMsg(playerId));
        currentSession = null;
        StartFrame.getInstance().showGlobalLobby();
    }


}

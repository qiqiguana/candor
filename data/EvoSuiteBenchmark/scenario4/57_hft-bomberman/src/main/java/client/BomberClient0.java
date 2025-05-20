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
 */
public class BomberClient extends Observable {

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
    public ClientGameSession getCurrentSession();

    /**
     * Creates a new BomberClient. Use getInstance() to aquire an instance.
     *
     * @param playerName
     *            The name of the player on this client.
     */
    private BomberClient(String playerName) {
    }

    /**
     * Returns the singleton.
     *
     * @return The only BomberClient instance.
     */
    public static BomberClient getInstance();

    /**
     * Tries to connect to the server.
     *
     * @throws RuntimeException
     *             Thrown when the connection attempt was not successful.
     */
    public boolean connectToSrv();

    /**
     * Sends a message to the server. The passed object will be serialized and
     * transmitted to the server.
     *
     * @param msg
     *            The message to send.
     */
    public void sendMsg(Message msg);

    /**
     * Creates a new id that is guaranteed to be unique for this class.
     *
     * @return The next valid id for this class.
     */
    public int getNextId();

    /**
     * Sets the id offset.
     *
     * @param idOffset
     *            to set
     */
    public void setIdOffset(int idOffset);

    /**
     * Sets the current session.
     *
     * @param The
     *            ClientGameSession to set.
     */
    public void setCurrentSession(ClientGameSession currentSession);

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
    public void createSession(String name, List<String> maps, ImageIcon mapPreview, int totalPlayers, int rounds);

    public void openSession();

    /**
     * Requests a list of active sessions on the server.
     */
    public void requestSessionList();

    /**
     * Requests a list of available maps from the server.
     */
    public void requestMapList();

    /**
     * Returns a list of active sessions on the server. Invoke
     * requestSessionList() to update this list.
     *
     * @return
     */
    public Vector<SessionDetailsMsg> getAvailableSessions();

    /**
     * @param sessionInfos
     */
    public void setAvailableSessions(Vector<SessionDetailsMsg> sessionInfos);

    /**
     * @param maps
     */
    public void setAvailableMaps(List<MapInfo> maps);

    /**
     * @return the availableMaps
     */
    public Vector<MapInfo> getAvailableMaps();

    /**
     * Creates a session from the details specified in the given
     * SessionDetailMsg.
     *
     * @param session
     *            The session to open on this client.
     */
    public void createSession(SessionDetailsMsg sessionDetails);

    /**
     * Sets the server-name
     *
     * @author Bj�rn
     */
    public void setServerName(String serverName);

    /**
     * Sets the server-port
     *
     * @author Bj�rn
     */
    public void setServerPort(int serverPort);

    /**
     * @param sender
     * @param msg
     */
    public void addChatGlobal(String sender, String msg);

    /**
     * @param sender
     * @param msg
     */
    public void addChatSession(String sender, String msg);

    public void discardSessionRequest();

    /**
     * Closes the current session.
     */
    public void closeSession();

    /**
     * Closes the current session.
     */
    public void closeSessionAndShowHighscores();

    /**
     * Leave the current session.
     */
    public void leaveSession();

    public void closeConnection();

    public String getPlayerName();

    public void setPlayerName(String playerName);

    public void requestGlobalScore();

    public void setGlobalscores(ArrayList scores);

    public void setRoundScore(HashMap<String, Integer> rndscore);

    public void setSessionScore(HashMap<String, Integer> rndscore);

    /**
     * @param i
     */
    public void quitRunningSession(int playerId);
}

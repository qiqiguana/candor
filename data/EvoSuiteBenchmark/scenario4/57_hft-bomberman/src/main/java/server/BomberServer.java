package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import messages.Message;
import messages.global.GlobalServerMsg;
import messages.global.SessionDetailsMsg;
import messages.global.SessionListMsg;
import org.apache.log4j.Logger;
import org.hsqldb.Server;
import org.hsqldb.jdbc.jdbcDataSource;
import common.Constants;

/**
 * This is the main server class. It is responsible for accepting connections
 * from clients and starting new games/session.
 *
 * @author Steffen, Bj�rn, Daniel
 */
public class BomberServer {

    private static final Logger logger = Logger.getLogger(BomberServer.class);

    /**
     * The BomberServer singleton.
     */
    private static BomberServer bomberServer;

    /**
     * The ServerSocket on which connections are accepted.
     */
    private ServerSocket srvSocket;

    /**
     * The sessions currently running on this on this server. key: session name,
     * value: corresponding session object
     */
    private HashMap<String, ServerGameSession> gameSessions = new HashMap<String, ServerGameSession>();

    /**
     * Contains all the clients who are currently in the global lobby.
     */
    private Vector<ClientInfo> globalClients = new Vector<ClientInfo>();

    /**
     * Contains all the clients who are currently connected to the Server.
     */
    private Vector<ClientInfo> allClients = new Vector<ClientInfo>();

    private static int port = Constants.DEFAULT_SERVER_PORT;

    private ServerGameSession newSession;

    /**
     * Private constructor. Creates a new BomberServer. Use the
     * getInstance-method to obtain the BomberServer singleton.
     */
    private BomberServer() {
    }

    private void startDB();

    /**
     * Main method. One optional parameter may be specified, which will be
     * interpreted as the port number the server accepts client connections on.
     *
     * @param args
     */
    public static void main(String[] args);

    /**
     * Accepts incoming client connections.
     */
    public void waitForClients();

    /**
     * Creates a new GameSession.
     *
     * @param name
     * @param totalRounds
     * @return The newly created GameSession-object, null if the session name
     *         was already taken
     */
    public ServerGameSession createSession(String name, List<String> maps, ImageIcon mapPreview, int nrOfPlayers, int totalRounds);

    /**
     * Creates a SessionListMsg from all open sessions and
     * sends it to all clients.
     */
    private void broadcastSessionList();

    /**
     * Sends the message to all clients except the original sender itself.
     *
     * @param msg
     *            The message to be sent.
     * @param sender
     *            The original sender of the msg.
     */
    public void multicastMsg(Message msg, ClientInfo sender);

    /**
     * Broadcasts a message to all connected clients.
     *
     * @param msg
     *            The message to be sent.
     */
    public void broadcastMsg(Message msg);

    /**
     * this will exit the server
     *
     * @author Bj�rn
     */
    public void stopServer();

    // shutdown database
    public static void closeDB();

    private static jdbcDataSource setupDataSource();

    /**
     * Removes the client whose ClientInfo object is passed as a parameter from
     * the global area. As a result this client will no longer receive global
     * messages.
     *
     * @param clientInfo
     */
    public void removeClientFromGlobal(ClientInfo clientInfo);

    public void addClientToGlobal(ClientInfo clientInfo);

    /**
     * Removes a client
     *
     * @param clientInfo
     */
    public void removeClient(ClientInfo clientInfo);

    public void addClient(ClientInfo clientInfo);

    /**
     * Static factory method.
     *
     * @return The BomberServer Singleton
     */
    public static BomberServer getInstance();

    public Collection<ServerGameSession> getGameSessions();

    public ServerGameSession getGameSession(String name);

    public void removeSession(String name);

    public Vector<ClientInfo> getClients();
}

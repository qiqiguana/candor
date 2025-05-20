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
 * @author Steffen, Björn, Daniel
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
		startDB();

		try {
			srvSocket = new ServerSocket(port);
		} catch (IOException e) {
			logger.fatal("Couldn't create ServerSocket", e);
		}
	}

	private void startDB() {
		String[] options = new String[] { "-database.0", "hsqldb/datenbank",
				"-dbname.0", "datenbank" };
		Server.main(options);
	}

	/**
	 * Main method. One optional parameter may be specified, which will be
	 * interpreted as the port number the server accepts client connections on.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		// try to read user-defined port if specified
		if (args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				logger
						.warn("Incorrect server port specified! Using default...");
			}
		}
		logger.info("Starting server on port: " + port);
		BomberServer bomberSrv = getInstance();
		bomberSrv.waitForClients();
	}

	/**
	 * Accepts incoming client connections.
	 */
	public void waitForClients() {
		new Thread() {
			public void run() {
				logger.info("Waiting for players...");
				while (!isInterrupted()) {
					Socket clientSocket;
					try {
						clientSocket = srvSocket.accept();
						ClientInfo clientInfo = new ClientInfo(clientSocket);
						addClientToGlobal(clientInfo);
						addClient(clientInfo);
					} catch (IOException e) {
						logger
								.fatal(
										"Couldn't accept connection on ServerSocket",
										e);
					}
				}
			}
		}.start();
	}

	/**
	 * Creates a new GameSession.
	 * 
	 * @param name
	 * @param totalRounds
	 * @return The newly created GameSession-object, null if the session name
	 *         was already taken
	 */
	public ServerGameSession createSession(String name, List<String> maps,
			ImageIcon mapPreview, int nrOfPlayers, int totalRounds) {
		if (gameSessions.containsKey(name)) {
			return null;
		} else {
			newSession = new ServerGameSession(name, maps,
					mapPreview, nrOfPlayers, totalRounds);
			gameSessions.put(name, newSession);
			newSession.broadcastMsg(new SessionListMsg());
			broadcastSessionList();
			return newSession;
		}
		
	}
	
	/**
	 * Creates a SessionListMsg from all open sessions and
	 * sends it to all clients.
	 */
	private void broadcastSessionList(){
	    SessionListMsg response = new SessionListMsg();
        for (ServerGameSession session : getGameSessions()) {
            // a session has at least one player in it (hacky)
            int nrOfPlayers = 1;
            if(session.getCurrentNrOfPlayers() != 0){
                nrOfPlayers = session.getCurrentNrOfPlayers();
            }
           
            SessionDetailsMsg sessionInfo = new SessionDetailsMsg(session
                    .getName(), session.getMaps(), session.getPreview(),
                    session.getTotalNrOfPlayers(), nrOfPlayers, session
                            .getTotalRounds());

            response.addSessionInfo(sessionInfo);
        }
        multicastMsg(response, null);
	}

	/**
	 * Sends the message to all clients except the original sender itself.
	 * 
	 * @param msg
	 *            The message to be sent.
	 * @param sender
	 *            The original sender of the msg.
	 */
	public void multicastMsg(Message msg, ClientInfo sender) {
		for (ClientInfo participant : globalClients) {
			if (participant != sender) {
				participant.sendMsg(msg);
			}
		}
	}

	/**
	 * Broadcasts a message to all connected clients.
	 * 
	 * @param msg
	 *            The message to be sent.
	 */
	public void broadcastMsg(Message msg) {
		for (ClientInfo client : globalClients) {
			client.sendMsg(msg);
		}
	}

	/**
	 * this will exit the server
	 * 
	 * @author Björn
	 */
	public void stopServer() {
		closeDB();
		logger.info("Server stopped");
		System.exit(0);
	}

	// shutdown database
	public static void closeDB() {
		jdbcDataSource dataSource = setupDataSource();
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute("SHUTDOWN");
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static jdbcDataSource setupDataSource() {
		jdbcDataSource dataSource = new jdbcDataSource();
		dataSource.setDatabase("jdbc:hsqldb:hsql://localhost/datenbank");
		dataSource.setUser("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	/**
	 * Removes the client whose ClientInfo object is passed as a parameter from
	 * the global area. As a result this client will no longer receive global
	 * messages.
	 * 
	 * @param clientInfo
	 */
	public void removeClientFromGlobal(ClientInfo clientInfo) {
		globalClients.remove(clientInfo);
	}

	public void addClientToGlobal(ClientInfo clientInfo) {
		globalClients.add(clientInfo);
	}

	/**
	 * Removes a client
	 * 
	 * @param clientInfo
	 */
	public void removeClient(ClientInfo clientInfo) {
		allClients.remove(clientInfo);
	}

	public void addClient(ClientInfo clientInfo) {
		allClients.add(clientInfo);
	}

	/**
	 * Static factory method.
	 * 
	 * @return The BomberServer Singleton
	 */
	public static BomberServer getInstance() {
		if (bomberServer == null) {
			bomberServer = new BomberServer();
		}
		return bomberServer;
	}

	public Collection<ServerGameSession> getGameSessions() {
		return gameSessions.values();
	}

	public ServerGameSession getGameSession(String name) {
		return gameSessions.get(name);
	}

	public void removeSession(String name) {
		gameSessions.remove(name);
		newSession.broadcastMsg(new SessionListMsg());
	}

	public Vector<ClientInfo> getClients() {
		return allClients;
	}
}

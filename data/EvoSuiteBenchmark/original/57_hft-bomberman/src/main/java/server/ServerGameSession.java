package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import messages.Message;
import messages.global.ParticipantInfo;
import messages.round.RoundScoreMsg;
import messages.session.InitializeRoundMsg;
import messages.session.SessionScoreMsg;

import org.apache.log4j.Logger;

import common.GameSession;

import db.DBException;
import db.DBGameUser;
import db.DBServiceFactory;

/**
 * The server representation of a game session. A session may consist of several
 * rounds.
 * 
 * @author Steffen
 * @see ServerGameRound
 */
public class ServerGameSession extends GameSession {
	/**
	 * False if the first game round hasn't begun yet, true otherwise.
	 */
	private boolean hasBegun = false;
	private static final Logger logger = Logger
			.getLogger(ServerGameSession.class);

	/**
	 * Iterates over the maps that have been selected for this session. The
	 * mapIterator is used by getNextMap().
	 */
	private Iterator<String> mapIterator;

	/**
	 * The current game round.
	 */
	private ServerGameRound currentRound;

	/**
	 * Contains the clients that have signaled that they are ready for the next
	 * round
	 */
	private Set<ClientInfo> readyClients = new HashSet<ClientInfo>();

	/**
	 * Is used by getNextIdOffset().
	 */
	private int nextIdOffset = 1000000;

	/**
	 * The ClientInfo objects of all clients parcticipating in this session.
	 */
	private Vector<ClientInfo> participants = new Vector<ClientInfo>();
	protected HashMap<String, Integer> sessionScores = new HashMap<String, Integer>();

	public HashMap<String, Integer> getSessionScores() {
		return sessionScores;
	}

	public ClientInfo getParticipantById(int id) {

		for (int i = 0; i < participants.size(); i++) {
			ClientInfo participant = participants.get(i);

			if (participant.getIdOffset() == id) {
				return participant;
			}
		}
		return null;
	}

	/**
	 * Creates a new ServerGameSession.
	 * 
	 * @param name
	 *            The session name.
	 * @param maps
	 *            A List of Strings containing the names of the maps to be
	 *            played in this session.
	 * @param totalNrOfPlayers
	 *            The total number of players that are expected to participate
	 *            in this session.
	 * @param totalRounds
	 *            The Total number of rounds to be played in this session.
	 */
	public ServerGameSession(String name, List<String> maps,
			ImageIcon mapPreview, int totalNrOfPlayers, int totalRounds) {
		super(name, maps, mapPreview, totalNrOfPlayers, totalRounds);
		this.mapIterator = maps.iterator();
	}

	/**
	 * 
	 * @param participant
	 * @return -1 if the session was already full, the idOffset if the
	 *         participant was succesfully added.
	 */
	synchronized public int addParticipant(ClientInfo participant) {
		if (participants.size() >= totalNrOfPlayers
				|| participants.contains(participant)) {
			logger.info("Couldn't add " + participant.getName());
			return -1;
		} else {
			logger.info("Adding client " + participant.getName()
					+ " to session " + name);
			participants.add(participant);
			return getNextIdOffset();
		}
	}

	public void beginRoundIfReady() {
		if (participants.size() == totalNrOfPlayers) {
			
			// for logging
			StringBuffer playerNames = new StringBuffer();
			for (ClientInfo participant : participants) {
				playerNames.append(participant.getName());
				playerNames.append(", ");
			}
			logger.info("Beginning round with players: " + playerNames.toString());
			
			hasBegun = true;
			beginRound();

			// remove the session from the global list after it started, so that
			// no more clients can try to join
			BomberServer.getInstance().removeSession(name);
		}
	}

	/**
	 * Returns the next idOffset. An idOffset is unique for within a session.
	 * Based on its idOffset each client can create unique IDs for the objects
	 * it adds to the game model.
	 * 
	 * @return The next idOffset.
	 */
	private int getNextIdOffset() {
		int result = nextIdOffset;
		nextIdOffset += 1000000;
		return result;
	}

	/**
	 * Removes the given participant.
	 * 
	 * @param participant
	 */
	synchronized public void removeParticipant(ClientInfo participant) {
		logger.info("Removing participant " + participant.getName()
				+ " from session " + name);

		participants.remove(participant);

		// If a client disconnects and the first round is currently going on or
		// has already been played, the expected number of players must be
		// adjusted, so
		// that the next round can start normally.
		if (hasBegun) {
			totalNrOfPlayers--;
		}

		if (participants.isEmpty()) {
			logger.info("Removing session " + name + "(empty)");
			BomberServer.getInstance().removeSession(name);
		}
	}

	/**
	 * Broacasts the given message to all clients in this session.
	 * 
	 * @param msg
	 */
	public void broadcastMsg(Message msg) {
		for (ClientInfo participant : participants) {
			participant.sendMsg(msg);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beginRound() {
		logger.info("Beginning new game round...");
		currentRoundNr++;
		currentRound = new ServerGameRound(this, participants, getNextMap());
		currentRound.begin();
	}

	private String getNextMap() {
		if (!mapIterator.hasNext()) {
			mapIterator = maps.iterator();
		}
		return mapIterator.next();
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
		for (ClientInfo participant : participants) {
			if (participant != sender) {
				participant.sendMsg(msg);
			}
		}
	}

	public ServerGameRound getCurrentRound() {
		return currentRound;
	}

	/**
	 * @param scores
	 */
	public void addRoundScore(HashMap<Integer, Integer> scores) {

		for (Entry<Integer, Integer> entry : scores.entrySet()) {
			int key = entry.getKey();
			int score = entry.getValue();
			int oldscore = 0;
			if (this.scores.get(key) != null) {
				oldscore = this.scores.get(key);
			}
			this.scores.put(key, oldscore + score);
		}

	}

	/**
	 * Calculates the session scores and if there are more rounds to be played
	 * initializes the wait for all players to send the ready signal.
	 */
	@Override
	public void doPostRoundProcessing() {
		logger.info("Doing post round processing...");
		logger.info("Calculating scores...");

		addRoundScore(currentRound.getScores());

		broadcastMsg(new RoundScoreMsg(currentRound.roundScores));

		currentRound = null;

		// if there are more rounds to be played wait for all players to be
		// ready
		if (currentRoundNr < totalRounds) {
			initializeWait();
			logger.info("Waiting for players to be ready for next round...");
		} else { // else end the session
			logger.info("servergamesession ended");
			doPostSessionProcessing();
			logger.info("/servergamesession ended");
		}
	}

	/**
	 * Removes all clients from the session and puts them back into the global
	 * lobby. Then removes the session object from the session list.
	 */
	@Override
	protected void doPostSessionProcessing() {

		logger.info("Doing post session processing...");
		logger.info("writing scores to db");
		aggregateScore();
		broadcastMsg(new SessionScoreMsg(sessionScores));
		BomberServer bomberSrv = BomberServer.getInstance();
		// doesn't work properly
		// TODO: participants leave right after session end
		// but they are needed for clientside highscore calculation
		while (!participants.isEmpty()) {
			logger.info("ServerGameSession -> leaving");
			ClientInfo participant = participants.firstElement();
			participant.leaveGameSession();
			bomberSrv.addClientToGlobal(participant);
			logger.info("/ServerGameSession -> leaving");

		}
		bomberSrv.removeSession(name);
	}

	/**
	 * Initializes the wait for all clients to be marked ready for the next
	 * round.
	 */
	private void initializeWait() {
		readyClients = new HashSet<ClientInfo>(participants.size());
	}

	public void aggregateScore() {

		for (int i = 0; i < participants.size(); i++) {

			ClientInfo cf = participants.get(i);
			int score = 0;
			for (Entry<Integer, Integer> entry : this.scores.entrySet()) {
				if (entry.getKey() == cf.getIdOffset()) {
					score = entry.getValue();
				}
			}
			logger.info("DB writing --> processing " + cf.getName() + " "
					+ cf.getIdOffset() + " " + score);
			try {
				sessionScores.put(cf.getName(), score);
				DBGameUser user = DBServiceFactory.getInstance().getDBUser(
						cf.getName());
				if (user.getName() != null && !user.getName().equals("")) {
					user.setScore(user.getScore() + score);

					DBServiceFactory.getInstance().updateScore(user);
				}
			} catch (DBException e) {
				logger.error("DB Error: " + e.toString());
			}
		}

	}

	/**
	 * 
	 * @return The number of clients currently in this session.
	 */
	public int getCurrentNrOfPlayers() {
		return participants.size();
	}

	/**
	 * Marks the given client ready for the next round.
	 * 
	 * @param clientInfo
	 *            The client to be marked ready.
	 */
	public void markClientReady(ClientInfo clientInfo) {
		logger.info(clientInfo.getName() + " is ready for the next round.");
		readyClients.add(clientInfo);
		if (readyClients.size() == participants.size()) {
			beginRound();
		}
	}

	public List<ParticipantInfo> getParticipantInfos() {
		List<ParticipantInfo> participantInfos = new ArrayList<ParticipantInfo>();
		for (ClientInfo participant : participants) {
			String name = participant.getName();
			int id = participant.getIdOffset();
			ParticipantInfo participantInfo = new ParticipantInfo(id, name);
			participantInfos.add(participantInfo);
		}
		return participantInfos;
	}
}

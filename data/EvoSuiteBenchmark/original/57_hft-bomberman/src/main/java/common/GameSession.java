package common;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import db.DBGameUser;

/**
 * 
 * @author Steffen
 * 
 */
public abstract class GameSession {

	private static final Logger logger = Logger.getLogger(GameSession.class);
	protected int totalRounds;
	protected int currentRoundNr = 0;
	protected int totalNrOfPlayers;
	protected String name;
	protected List<String> maps;
	protected ImageIcon mapPreview;

	/**
	 * Is used to keep track of the score of each player in this session. Key:
	 * playerId, Value: playerScore
	 */
	protected HashMap<Integer, Integer> scores = new HashMap<Integer, Integer>();

	
	public void addRoundScore(int playerid, int score) {
		logger.info("!gamesession tobi: "+playerid+" "+playerid);
		for (Entry<Integer,Integer> entry: scores.entrySet()) {
			
			if(entry.getKey()==playerid){
				
				score += entry.getValue();
				this.scores.remove(entry);
				this.scores.put(playerid, score);
			}
		}
	}
	
	
	
	
	public GameSession(String name, List<String> maps, ImageIcon mapPreview,
			int nrOfPlayers, int totalRounds) {
		if (maps.isEmpty() || nrOfPlayers < 1 || totalRounds < 1) {
			throw new IllegalArgumentException("maps empty?: " + maps.isEmpty()
					+ " nrOfPlayers: " + nrOfPlayers + " totalRounds: "
					+ totalRounds);
		}

		this.name = name;
		this.totalNrOfPlayers = nrOfPlayers;
		this.totalRounds = totalRounds;
		this.maps = maps;
		this.mapPreview = mapPreview;
	}

	//public abstract void aggregateScore();

	/**
	 * Starts a new round.
	 */
	public abstract void beginRound();

	public String getName() {
		return name;
	}

	public int getTotalRounds() {
		return totalRounds;
	}

	public int getCurrentRoundNr() {
		return currentRoundNr;
	}

	public int getTotalNrOfPlayers() {
		return totalNrOfPlayers;
	}

	public List<String> getMaps() {
		return maps;
	}

	public ImageIcon getPreview() {
		return mapPreview;
	}

	public HashMap<Integer, Integer> getScores() {
		return scores;
	}

	public abstract void doPostRoundProcessing();

	protected abstract void doPostSessionProcessing();
}

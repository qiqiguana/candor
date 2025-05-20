package messages.global;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import server.BomberServer;
import server.ClientInfo;
import server.ServerGameSession;

import common.Constants;
import common.Map;
import common.MapFilesLister;
import common.MapPreview;
import db.DBException;
import db.DBServiceFactory;

/**
 * AskForInformationMsg.java
 * 
 * Nachricht vom Client zum Server, um den Server nach Informationen zu fragen
 * 
 * @author Björn Geiger
 */
public class InfoRequestMsg implements GlobalClientMsg {

	public static final int GET_SESSION_LIST = 1;
	public static final int GET_MAP_LIST = 2;
	public static final int GET_OVERALL_SCORE = 3;

	/**
	 * Welche Information soll angefragt werden
	 * 
	 */
	private int type;

	/**
	 * Konstruktor
	 * 
	 * @param type -
	 *            Welche Information soll angefragt werden
	 * @param objectID -
	 *            ID des Objektes von dem die Infos geholt werden sollen
	 */
	public InfoRequestMsg(int typeInteger) {
		super();
		this.type = typeInteger;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberServer bomberSrv, ClientInfo sender) {
		switch (type) {
		case InfoRequestMsg.GET_SESSION_LIST:
			SessionListMsg response = new SessionListMsg();
			for (ServerGameSession session : bomberSrv.getGameSessions()) {
				SessionDetailsMsg sessionInfo = new SessionDetailsMsg(session
						.getName(), session.getMaps(), session.getPreview(),
						session.getTotalNrOfPlayers(), session
								.getCurrentNrOfPlayers(), session
								.getTotalRounds());

				response.addSessionInfo(sessionInfo);
			}
			sender.sendMsg(response);
			break;
		case InfoRequestMsg.GET_MAP_LIST:
			// create list of mapInfo objects with map-informations
			MapFilesLister mapLister = new MapFilesLister();
			List<MapInfo> maps = mapLister.getMapInfoList();
			sender.sendMsg(new MapListMsg(maps));
			break;
		case InfoRequestMsg.GET_OVERALL_SCORE:
			ArrayList l = null;
			try {
				l = DBServiceFactory.getInstance().getTopTenGameUser();
			} catch (DBException e) {
				e.printStackTrace();
			}
			sender.sendMsg(new ScoreMsg(l));
			break;
		}
	}
}

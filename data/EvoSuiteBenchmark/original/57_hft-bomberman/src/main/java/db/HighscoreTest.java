package db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class reads the top ten users from the database and writes them into the
 * console. For testing purposes only
 * 
 * @author Daniel Tunjic
 * 
 */

public class HighscoreTest {

	public static void main(String args[]) {

		Top10Highscore top10Highscore = new Top10Highscore();

		List<DBGameUser> dbplayerList = new ArrayList<DBGameUser>();

		dbplayerList = top10Highscore.getPlayerList();

		Iterator<DBGameUser> it = dbplayerList.iterator();

		// write the top 10 User into the Console

		while (it.hasNext()) {
			DBGameUser user = it.next();
//			System.out.println("Name: " + user.getName() + " Score: "
//					+ user.getScore());
		}

		// write the single user to the console + user delete

		DBGameUser user;
		try {
			user = DBServiceFactory.getInstance().getDBUser("Platz 6");
//			System.out.println(" User mit dem Namen: " + user.getName()
//					+ " und dem Score: " + user.getScore() + " wurde gelesen");
			
			//DBServiceFactory.getInstance().deleteDBUser("Platz 6");

		} catch (DBException e) {
		}

	}
}
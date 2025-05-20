package db;

/**
 * This Class retrieves the top 10 users from the database and returns 
 * them in an array list
 * 
 * @author Daniel Tunjic
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Level;

import logging.Logging;	


public class Top10Highscore {

    private ArrayList<DBGameUser> playerList = new ArrayList<DBGameUser>();
    private static Logging logger = Logging.getInstance();

    public Top10Highscore() {
        super();
        List<DBGameUser> dbplayerList = new ArrayList<DBGameUser>();
        try {
            dbplayerList = DBServiceFactory.getInstance().getTopTenGameUser();
        } catch (DBException e) {
        	logger.log(Level.ERROR, this,  e.toString());
        }
        
        /*
         * Momentan sind "user" und "player" identische Objekte und der Iterator macht
         * nicht wirklich Sinn. Da wir später aber nur den Namen und den Score der Spieler
         * brauchen, war der Gedanke einen "leichtgewichtigen player" zu erstellen, der nur
         * diese Informationen beinhaltet. 
        */
        
        Iterator<DBGameUser> it = dbplayerList.iterator();
        
        while (it.hasNext()) {
            DBGameUser user = it.next();
            DBGameUser player = new DBGameUser();
        
            player.setName(user.getName());
            player.setScore(user.getScore());
            playerList.add(player);
        }

    }

    public ArrayList<DBGameUser> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<DBGameUser> playerList) {
        this.playerList = playerList;
    }
}

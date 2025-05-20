package db;

/**
 * This class writes 15 users into the database. For testing purposes only
 * 
 * @author Daniel Tunjic
 *
 */

public class UserTest {

	
	public static void main(String[] args){

		DBGameUser user = new DBGameUser();
		int score = 100;
		
		for (int i=1; i < 15; i++){
			user.setName("Platz " + i);
			user.setScore(score);	
			
			try{
			DBServiceFactory.getInstance().saveGameUser(user);
			} catch (DBException e) {};
			
			score--;
		}	
		
	}	
}



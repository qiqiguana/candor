package db;

/**
 * This is the game user class. 
 * 
 * @author Daniel Tunjic
 *
 */

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class DBGameUser implements Serializable {
	

	@Id
	private String name;
	private Integer score; 
	private String password;
	
	
	public DBGameUser() {
    }

	
	public String getName() {
        return name;
    }
   
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        return password;
    }
   
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
    	return "DBGameUser: "+this.name+" \tScore: "+this.score;
    }

}

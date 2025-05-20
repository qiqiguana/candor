package src;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

/**
 * @author  mpretel
 */
public class Knight implements Serializable, Player{

	private String name;
	private int totalMoney;
	private ArrayList<String> fiefdoms = new ArrayList<String>();
	private boolean dead = false;
	private String rank = "knight";
	private String order = "";
	private int score = 0;
	private int hitsCounter = 0;
	private ArrayList<Mercenaries> mercenaries = new ArrayList<Mercenaries>();
	private int rebellionCounter = 0;
	private String location = "Ile-De-France";
	private int kills = 0;
	private int wounds = 0;
	private String password;
	private ArrayList<String> votes = new ArrayList<String>();
	private ArrayList<Vassals> movingVassals = new ArrayList<Vassals>();
	private ArrayList<Retainers> retainers = new ArrayList<Retainers>();
	private ArrayList<Player> allies = new ArrayList<Player>();
	private Socket socket;
	protected ArrayList<String> conversation = new ArrayList<String>();

	public Knight(){
		
	}

	public Knight(String name, String rank, String password) {
		this.name = name;
		this.rank = rank;
		this.password = password;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		if(rank.equals("knight") || rank.equals("count")
				|| rank.equals("duke") || rank.equals("king")){
			this.rank = rank;
		}
		else{
			System.out.println("Invalid rank. Ranks are case sensitive as follows: knight, count, duke, and king.");
		}
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int money) {
		this.totalMoney = money;
	}

	public void setMovingVassals(ArrayList<Vassals> movingVassals) {
		this.movingVassals = movingVassals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}
	
	public void changeName(String name){
		CurrentPlayers.currentPlayers.remove(getName());
		this.name = name;
		CurrentPlayers.currentPlayers.put(getName(), this);
		for(String fiefName : fiefdoms){
			Fiefdoms fief = Map.map.get(fiefName);
			fief.setOwner(getName());
		}
	}

	public ArrayList<String> getFiefdoms() {
		return fiefdoms;
	}

//	public void setFiefdoms(ArrayList<String> fiefdoms) {
//		fiefdoms.clear();
//		this.fiefdoms = fiefdoms;
//		System.out.println(getName() + fiefdoms.size());
//		for(String fiefdomStr : this.fiefdoms){
//			Fiefdoms fiefdomObj = Map.map.get(fiefdomStr);
//			fiefdomObj.setOwner(getName());
//		}
//	}
	public void setFiefdoms(ArrayList<String> fiefdoms) {
		this.fiefdoms = fiefdoms;
		for(String f : fiefdoms)
			Map.map.get(f).setOwner(getName());

	}

	public synchronized void setFiefdoms(String fiefdomStr, boolean add) {
		Fiefdoms fiefdomObj = null;
		fiefdomObj = Map.map.get(fiefdomStr);
		if(!Map.map.containsKey(fiefdomStr)){
			System.out.println("Innexistent fiefdom: " + fiefdomStr);
			return;
		}
		if(add){
			Iterator fiefdomIter = this.fiefdoms.iterator();
			while(fiefdomIter.hasNext()){
				String fiefListStr = (String)fiefdomIter.next();
				if(fiefListStr.equalsIgnoreCase(fiefdomStr)){
					return;
				}
			}
			this.fiefdoms.add(fiefdomStr);
			fiefdomObj.setOwner(getName());
		}
		else if(!add){
			Iterator fiefdomIter = this.fiefdoms.iterator();
			while(fiefdomIter.hasNext()){
				String fiefListStr = (String)fiefdomIter.next();
				if(fiefListStr.equalsIgnoreCase(fiefdomStr)){
					fiefdoms.remove(fiefdomStr);
				}
			}
		}
	}

	public int getHitsCounter() {
		return hitsCounter;
	}

	public void setHitsCounter(int hitsCounter) {
		this.hitsCounter = hitsCounter;
	}

	public ArrayList<Mercenaries> getMercenaries() {
		return mercenaries;
	}

	public void setMercenaries(ArrayList<Mercenaries> mercenaries) {
		this.mercenaries = mercenaries;
	}
	
	public void addMercenaries(int qty) {
		for(int i = 0; i < qty; i++)
			mercenaries.add(new Mercenaries(this.getName(), this.getLocation()));
	}

	public int getRebellionCounter() {
		return rebellionCounter;
	}

	public void setRebellionCounter(int rebellionCounter) {
		this.rebellionCounter = rebellionCounter;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public boolean setTotalMoney(int money, boolean add) {
		if(!add && getTotalMoney() - money < 0){
			return false;
		}
		if(!add){
			this.totalMoney -= money;
			return true;
		}
		if(add){
			this.totalMoney += money;
			return true;
		}
		return false;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		if(Map.map.containsKey(location)){
			this.location = location;
			for(Vassals vassal : movingVassals){
				vassal.setLocation(location);
			}
			for(Mercenaries mercenary : mercenaries){
				mercenary.setLocation(location);
			}
			for(Retainers retainer : retainers){
				retainer.setLocation(location);
			}
		}
		else{
			System.out.println("Invalid fiefdom");
		}
	}

	public int getKill() {
		return kills;
	}

	public int getwound() {
		return wounds;
	}
	
	public boolean isFiefdomMine(String fiefdom){
		Iterator fiefdomsIter = fiefdoms.iterator();
		while(fiefdomsIter.hasNext()){
			String fiefdomStr = (String)fiefdomsIter.next();
			if(fiefdomStr.equalsIgnoreCase(fiefdom)){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Vassals> getMovingVassals() {
		return movingVassals;
	}

	public void setMovingVassals(int vassalsRequested) {
		if(vassalsRequested > 0){
				Fiefdoms location = Map.map.get(getLocation());
				this.movingVassals.clear(); // clear moveVassals ArrayList and calculate number
				int vassalsAllowed = 0;
				if(getRank().equalsIgnoreCase("knight")){
					vassalsAllowed = (int) Math.ceil(location.getLoyalVassals().size()/2); //rounding up number of vassals that knight is allowed to take with him
					System.out.println("MAX calculated: " + vassalsAllowed);
				}

				else if(getRank().equalsIgnoreCase("count")){
					vassalsAllowed = (int) Math.ceil(location.getLoyalVassals().size()*.75); //rounding up number of vassals that count is allowed to take with him
				}

				else if(getRank().equalsIgnoreCase("duke") || getRank().equalsIgnoreCase("king")){
					vassalsAllowed = location.getLoyalVassals().size();
				}

				else{
					System.out.println("Unknown rank");
					return;
				}
				//Checking if number of vassals requested by the knight is larger than 
				//the limit he is allowed to take
				if(vassalsAllowed < vassalsRequested){
					System.out.println("You are allowed to take up to " + vassalsAllowed + " vassals with you.");
				}
				if(vassalsAllowed > vassalsRequested){
					vassalsAllowed = vassalsRequested;
				}
				System.out.println(vassalsAllowed + " vassals will be moving with you");
	
				//add vassals to ArrayList of vassals that will be moved to destination fiefdom
				location.setLoyalVassals(vassalsAllowed,false); //remove vassals that are moving from loyalVassals list
				for(int i = 0; i < vassalsAllowed; i++){
					movingVassals.add(new Vassals(this.getName(),this.getLocation()));
				}
			}
			else{
				System.out.println("Invalid number");
				return;
			}
		}
	
	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void kill() {
		if(getKills() + getWounds() + 1 < 3){
			kills++;
		}
		else
			die();
	}
	
	public void die(){
		Knight king = CurrentPlayers.getKing();
		Iterator fiefdomIter = this.fiefdoms.iterator();
		while(fiefdomIter.hasNext()){
			String fiefListStr = (String)fiefdomIter.next();
			Fiefdoms fiefdomObj = Map.map.get(fiefListStr);
			if(king != null)
				king.setFiefdoms(fiefdomObj.getName(), true);
			else
				fiefdomObj.setOwner(null);
		}
		this.fiefdoms.clear();
		setLocation("Ile-De-France");
		if(!getRank().equalsIgnoreCase("king") && king != null){
			king.setTotalMoney(getTotalMoney(), true);
		}
		setTotalMoney(0);
		this.setDead(true);
	}
	
	public void wound() {
		if(getWounds() + getKill() + 1 < 3){
			wounds++;
		}
		else if(getWounds() + getKill() + 1 >= 3 && getKill() > 0){
			die();
		}		
	}

	public void setWounds(int wounds) {
		this.wounds = wounds;
	}
	
	public int getWounds() {
		return wounds;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<Retainers> getRetainers() {
		return retainers;
	}

	public void setRetainers(ArrayList<Retainers> army) {
		this.retainers = army;
	}
	
	public void addRetainer(Retainers soldier){
		if(soldier.getLocation().equalsIgnoreCase(getName())){
			retainers.add(soldier);
		}
	}

	public ArrayList<Player> getAllies() {
		return allies;
	}

	public void setAllies(ArrayList<Player> allies) {
		this.allies = allies;
	}
	
	public void setAllies(Player soldier){
		allies.add(soldier);
	}
	
	public void allyTo(String knightName){
		Knight thisKnight = CurrentPlayers.currentPlayers.get(this.getName());
		Knight knightObj = CurrentPlayers.currentPlayers.get(knightName);
		if(knightObj.getLocation() != this.getLocation()){
			ArrayList<String> moveArgs = new ArrayList<String>();
			moveArgs.add(this.getName());
			moveArgs.add(knightObj.getLocation());
			moveArgs.add(JOptionPane.showInputDialog(null,"Please input the number of vassals you \n" +
					"you desire to take with you:"));
			new GameController(new CommandBean("Move", moveArgs));
		}
		if(this.getLocation().equalsIgnoreCase(knightObj.getLocation())){
			knightObj.setAllies(this);
			System.out.println("There are " + getAllies().size() + " allies for " + this.getName());
			if(getMovingVassals().size() > 0){
				
				int listSize = getRetainers().size();
				for(int i = 0; i < listSize; i++){
					knightObj.setAllies(new Vassals(this.getName(),this.getLocation()));
					this.movingVassals.remove(0);
				}
			}
			if(getRetainers().size() > 0){
				int listSize = getRetainers().size();
				for(int i = 0; i < listSize; i++){
					knightObj.setAllies(new Vassals(this.getName(),this.getLocation()));
					this.retainers.remove(0);
				}
			}
			if(this.getMercenaries().size() > 0){
				int listSize = getMercenaries().size();
				for(int i = 0; i < listSize; i++){
					knightObj.setAllies(new Mercenaries(this.getName(),this.getLocation()));
					this.mercenaries.remove(0);
				}
			}
			if(this.isFiefdomMine(this.getLocation())){
				Fiefdoms fief = Map.map.get(this.getLocation());
				int listSize = fief.getLoyalVassals().size();
				for(int i = 0; i < listSize; i++){
					knightObj.setAllies(new Vassals(this.getName(),this.getLocation()));
					fief.setLoyalVassals(1, false);
					System.out.println("There's " + getAllies().size() + " allies");
					System.out.println(fief.getLoyalVassals().size());
				}
			}
		}
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public boolean getDead(){
		return this.dead;
	}

	public void promote(String knightName, String rank){
		if(this.getRank().equalsIgnoreCase("king")){
			Knight knight = CurrentPlayers.currentPlayers.get(knightName);
			knight.setRank(rank);
		}
	}

	public ArrayList<String> getVotes() {
		return votes;
	}

	public void setVotes(ArrayList<String> votes) {
		this.votes = votes;
	}

	public void vote(String vote){
		this.votes.set(this.votes.size()-1, vote);
	}

	public void sendMessage(String knightName, String message){
		CurrentPlayers.currentPlayers.get(knightName).conversation.add(message);
	}

	public ArrayList<String> getConversation() {
		return conversation;
	}

	public void setConversation(ArrayList<String> conversation) {
		this.conversation = conversation;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
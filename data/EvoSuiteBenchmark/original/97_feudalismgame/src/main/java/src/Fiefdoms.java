package src;

import java.io.Serializable;
import java.util.ArrayList;

public class Fiefdoms implements Serializable{
	private String name = null;
	private int value = 0;
	private String owner = null;
	private int personalCastles = 0;
	private int fiefdomCastles = 0;
	private ArrayList<Intruders> viking = new ArrayList<Intruders>();
	private ArrayList<Intruders> magyar = new ArrayList<Intruders>();
	private ArrayList<Intruders> muslim = new ArrayList<Intruders>();
	byte selectioncolor[] = new byte[3];
	float shaderColor[] = new float[2];
	
	private int peasants = 0;
	public ArrayList<Vassals> loyalVassals = new ArrayList<Vassals>();
	private ArrayList<Vassals> rebelliousVassals = new ArrayList<Vassals>();
	private boolean isKingdom;
	private ArrayList<String> neighbors;
	private String conqueror;
	private ArrayList<String> invaderType;

	private ArrayList<Player> vassalsList;

	public Fiefdoms(){
	}

	public Fiefdoms(ArrayList<String>neighbors, String name, 
			String owner, int value, boolean isKingdom, ArrayList<String> invaderType){
		this.name = name;
		this.owner = owner;
		this.value = value;
		this.neighbors = neighbors;
		this.invaderType = invaderType;
		this.isKingdom = isKingdom;
		//Create vassals according to the value of the fiefdom
		if(this.owner == "" || this.owner == null)
			setLoyalVassals(value,true);
		else if(owner.equalsIgnoreCase("viking"))
			setViking(value,true);
		else if(owner.equalsIgnoreCase("magyar"))
			setMagyar(value,true);
		else if(owner.equalsIgnoreCase("muslim"))
			setMuslim(value,true);
	}

//	public Fiefdoms(String name, int value, String owner, int numberVassals){
//		this.name = name;
//		this.value = value;
//		this.owner = owner;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<String> neighbors) {
		this.neighbors = neighbors;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String newOwner) {
		if(!(getOwner() == null || getOwner().equals("") || getOwner().equals(getOwner()))){
			Knight previousOwner = CurrentPlayers.currentPlayers.get(getOwner());
			previousOwner.setFiefdoms(getName(), false); //remove fiefdom from the previous owner's posession
		}
//		if(newOwner.equals("")){
//			newOwner = null;
//		}
		this.owner = newOwner;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setPersonalCastles(boolean add) {
		if (add == true)
			this.personalCastles ++;
		if(add == false)
			this.personalCastles --;
	}

	public int getPersonalCastles() {
		return personalCastles;
	}

	public void setFiefdomCastles(boolean add) {
		if (add == true)
			this.fiefdomCastles ++;
		if(add == false)
			this.fiefdomCastles --;
	}

	public int getFiefdomCastles() {
		return fiefdomCastles;
	}

	public ArrayList<Intruders> getViking() {
		return viking;
	}
	
	public void setViking(ArrayList<Intruders> vikings) {
		this.viking = vikings;
	}

	public void setViking(int qty, boolean add) {
		if(add){
			for (int i = 0; i < qty; i++){
				this.viking.add(new Intruders(this.getName(),"viking"));
			}
		}
		else{
			if(getViking().size() - qty > 0){
				for (int i = 0; i < qty; i++){
					this.viking.remove(0);
				}
			}
			else{
				getViking().clear();
			}
		}
	}
	
	public ArrayList<Intruders> getMagyar() {
		return magyar;
	}

	public void setMagyar(ArrayList<Intruders> magyar) {
		this.magyar = magyar;
	}
	
	public void setMagyar(int qty, boolean add){
		if(add){
			for (int i = 0; i < qty; i++){
				this.magyar.add(new Intruders(this.getName(),"magyar"));
			}
		}
		else{
			if(getMagyar().size() - qty > 0){
				for (int i = 0; i < qty; i++){
					this.magyar.remove(0);
				}
			}
			else{
				getMagyar().clear();
			}
		}
	}

	public ArrayList<Intruders> getMuslim() {
		return muslim;
	}

	public void setMuslim(ArrayList<Intruders> muslim) {
		this.muslim = muslim;
	}

	public void setMuslim(int qty, boolean add){
		if(add){
				for (int i = 0; i < qty; i++){
					this.muslim.add(new Intruders(this.getName(),"muslim"));
				}
			}
		else{
			if(getMuslim().size() - qty > 0){
				for (int i = 0; i < qty; i++){
					this.muslim.remove(0);
				}
			}
			else{
				getMuslim().clear();
			}
		}
	}

	public boolean isKingdom() {
		return isKingdom;
	}

	public void setKingdom(boolean isKingdom) {
		this.isKingdom = isKingdom;
	}

	public ArrayList<Vassals> getLoyalVassals() {
		return loyalVassals;
	}

	public void setLoyalVassals(int qty, boolean add) {
//loop to add vassals to loyal vassals list
		if(add == true){
			if(getLoyalVassals().size() + qty <= getValue() * 5){
				for (int i = 0; i < qty; i++){
					this.loyalVassals.add(new Vassals(this.getOwner(),this.getName()));
//					System.out.println(i+1 + " vassals created");
				}
//			System.out.println("There are " + getLoyalVassals().size() + " vassals in " + getName());
			}
			else{
//				System.out.println(getName() + " can hold up to " + 5 * getValue() + " vassals");
			}
		}
		
//loop to remove vassals from loyal vassals list
			if(!add){
				if(getLoyalVassals().size() - qty > 0){
					for (int i = 0; i < qty; i++){
						this.loyalVassals.remove(0);
					}
				}
				else{
					getLoyalVassals().clear();
				}
			}
	}

	public void setLoyalVassals(ArrayList<Vassals> loyalVassals) {
		this.loyalVassals = loyalVassals;
	}

	public int getPeasants() {
		return peasants;
	}

	public void setPeasants(int peasants) {
		this.peasants = peasants;
	}

	public ArrayList<Vassals> getRebelliousVassals() {
		return rebelliousVassals;
	}

	public void setRebelliousVassals(ArrayList<Vassals> rebelliousVassals) {
		this.rebelliousVassals = rebelliousVassals;
	}
	
	public void setRebelliousVassals(int qty, boolean add) {
		if(add == true){
			if(qty > getLoyalVassals().size()){
//There is less vassals in the fiefdom than the value of it,
// all loyal vassals then will become rebellious. 
				qty = getLoyalVassals().size();
			}
			setLoyalVassals(qty,false);
			for (int i = 0; i < qty; i++){
				this.rebelliousVassals.add(new Vassals(this.getOwner(),this.getName()));
			}
		}
		else{
			if(qty > getRebelliousVassals().size()){
//There is less rebellious vassals than the requested number of 
// vassals to be removed from the rebellious vassals list
				qty = getRebelliousVassals().size();
			}
			for (int i = 0; i < qty; i++){
				this.rebelliousVassals.remove(i);
			}
		}
	}

	public ArrayList<Player> getVassalsList() {
		return vassalsList;
	}

	public void setVassalsList(ArrayList<Player> vassalsList) {
		this.vassalsList = vassalsList;
	}
	
	public void setFiefdomCastles(int fiefdomCastles){
		this.fiefdomCastles = fiefdomCastles;
	}
	
	
	public void buyFiefdomCastles(int qty) {
		if(qty > 0){
			if(getFiefdomCastles() + getPersonalCastles() + qty < getValue()){
				this.fiefdomCastles += qty;
			}
			else{
				System.out.println("You cannot buy more than a total of " + getValue() + " castles for " + getName() + ".");
			}
		}
		else{
			System.out.println("Invalid number");
		}
	}
	
	public void setPersonalCastles(int qty){
		this.personalCastles = qty;
	}
	
	public void buyPersonalCastles(int qty) {
		if(qty > 0){
			if(getFiefdomCastles() + getPersonalCastles() < getValue()){
				this.personalCastles += qty;
			}
			else{
				System.out.println("You cannot have more than a total of " + getValue() + " castles " + " for " + getName() + ".");
			}		
		}
		else{
			System.out.println("Invalid number");
		}
	}

	public String getConqueror() {
		return conqueror;
	}

	public void setConqueror(String conqueror) {
		this.conqueror = conqueror;
	}

	public ArrayList<String> getInvaderType() {
		return invaderType;
	}

	public void setInvaderType(ArrayList<String> invaderType) {
		this.invaderType = invaderType;
	}

	public byte[] getSelectioncolor() {
		return selectioncolor;
	}

	public void setSelectioncolor(byte[] selectioncolor) {
		this.selectioncolor = selectioncolor;
	}

	public float[] getShaderColor() {
		return shaderColor;
	}

	public void setShaderColor(float[] shaderColor) {
		this.shaderColor = shaderColor;
	}
}
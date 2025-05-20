package dsachat.share.hero;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * a Hero describes all attributes, talents, spells and weapons of an hero.
 * @author bernshausen
 *
 */
public class Hero implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4235381890809856112L;
	private Vector<Attribute> attributes;
	Vector<Talent> talents;
	Vector<FightValue> fightvalues;
	Vector<Special> specials;
	private String name;
	private int INI;
	private int currINI=0;
	private int currLEP=0;
	private int currAUP=0;
	private int currASP=0;
	private int currKAP=0;
	private Weapon selectedWeapon = null;
//	private Armor leftArm;
//	private Armor rightArm;
//	private Armor leftLeg;
//	private Armor rightLeg;
//	private Armor back;
//	private Armor stomach;
//	private Armor chest;
//	private Armor head;
	private boolean priest=false;
	private boolean magican=false;
	
	
	/**
	 * create a new hero from a xml file
	 * @param xml the file this hero was stored by helden-software
	 */
	public Hero(Document xml){
		Element root = xml.getRootElement();
		talents = new Vector<Talent>();
		attributes = new Vector<Attribute>();
		fightvalues = new Vector<FightValue>();
		specials = new Vector<Special>();
		iter(root);
		currLEP=getAttrValue("LEP");
		currAUP=getAttrValue("AUP");
		currASP=getAttrValue("ASP");
		currKAP=getAttrValue("KAP");
	}
	
	/**
	 * iteration over the document starting at root-element
	 * get all necessary data from the file
	 */
	@SuppressWarnings("unchecked")
	private void iter(Element e){
		//IDEA armor
		//TODO select if this is a magican or priest
		for(Iterator i=e.elementIterator();i.hasNext();){
			Element next=(Element)i.next();
			if(next.getName().equals("held")){
				name=next.attributeValue("name");
			}
			//set attributes
			if(next.getName().equals("eigenschaft")){
				if(next.attributeValue("name").equals("Mut")){
					attributes.addElement(new Attribute("Mut","MU",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").equals("Klugheit")){
					attributes.addElement(new Attribute("Klugheit","KL",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").equals("Intuition")){
					attributes.addElement(new Attribute("Intuition","IN",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").equals("Charisma")){
					attributes.addElement(new Attribute("Charisma","CH",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").equals("Fingerfertigkeit")){
					attributes.addElement(new Attribute("Fingerfertigkeit","FF",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").equals("Gewandtheit")){
					attributes.addElement(new Attribute("Gewandtheit","GE",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").equals("Konstitution")){
					attributes.addElement(new Attribute("Konstitution","KO",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").endsWith("perkraft")){
					attributes.addElement(new Attribute("Körperkraft","KK",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").equals("Lebensenergie")){
					attributes.addElement(new Attribute("Lebensenergie","LEP",
						Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value")) +
							(getAttrValue("KO")*2 + getAttrValue("KK"))/2));
				}
				if(next.attributeValue("name").equals("Ausdauer")){
					attributes.addElement(new Attribute("Ausdauer","AUP",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))+
							(getAttrValue("MU")+getAttrValue("GE") + getAttrValue("KO"))/2
							));
				}
				if(next.attributeValue("name").equals("Astralenergie")){
					attributes.addElement(new Attribute("Astralenergie","ASP",
							Integer.parseInt(next.attributeValue("grossemeditation"))+
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))+
							(getAttrValue("MU")+getAttrValue("IN")+getAttrValue("CH"))/2
							));
				}
				if(next.attributeValue("name").equals("Karmaenergie")){//TODO check KAP
					attributes.addElement(new Attribute("Karmaenergie","KAP",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
				}
				if(next.attributeValue("name").equals("ini")){
					attributes.addElement(new Attribute("ini","INI",
							Integer.parseInt(next.attributeValue("mod"))+
							Integer.parseInt(next.attributeValue("value"))));
					INI=Integer.parseInt(next.attributeValue("mod"))+
						Integer.parseInt(next.attributeValue("value"));
				}
				
			}
			if(next.getName().equals("vorteil")){
				//TODO DisAdvatages
			}
			//specials
			if(next.getName().equals("sonderfertigkeit")){
				String name = next.attributeValue("name");
				name = name.replace(' ', '_');
				try {
					Special s = (Special)Special.class.getDeclaredField(
							name.toUpperCase()).get(Special.FINTE);
					specials.addElement(s);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			//set talents
			if(next.getName().equals("talent")){
				String name = next.attributeValue("name");
				String chall=next.attributeValue("probe");
				int val=Integer.parseInt(next.attributeValue("value"));
				Talent t = new Talent(name,val,chall);
				talents.addElement(t);
			}
			//spells
			if(next.getName().equals("zauber")){
				String name = next.attributeValue("name");
				String chall=next.attributeValue("probe");
				int val=Integer.parseInt(next.attributeValue("value"));
				Spell s = new Spell(name,val,chall);
				talents.addElement(s);
			}
			//fight values
			if(next.getName().equals("kampfwerte")){
				String name = next.attributeValue("name");
				FightValue fv = new FightValue(name, 0,0);
				if(name.matches("(Raufen)|(Ringen)")){
					//this is mostly without a weapon. but it can make damage with his hand
					try {
						Weapon w = (Weapon)Weapon.class.getDeclaredField(
								("Hand_"+name).toUpperCase()).get(new Weapon());
						w.setType(fv);
						fv.getWeapons().addElement(w);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
				fightvalues.addElement(fv);
			}
			//set values for fight
			if(next.getName().equals("attacke")){
				searchFV(next.getParent().attributeValue("name"))
					.setAttack(Integer.parseInt(next.attributeValue("value")));
			}
			if(next.getName().equals("parade")){
				searchFV(next.getParent().attributeValue("name"))
					.setDefense(Integer.parseInt(next.attributeValue("value")));
			}
			//set the weapons
			if(next.getName().equals("ausruestungneu")){
				if(!next.attributeValue("name").equals("jagtwaffe")){
					String name = next.attributeValue("waffenname");
					//remove ( and ) from names
					if(name.matches(".*[\\(\\)].*")){
						String[] split = name.split("[\\(\\)]");
						String tmpName = "";
						for(String s : split){
							tmpName += s;
						}
						name = tmpName;
					}
					//replace dots and spaces with underscore
					name= name.replace('.', '_');
					name= name.replace(' ', '_');
					name = name.replace("ß", "ss");
					Weapon w;
					try{
						//get a static weapon.
						//the weapon in file doesn't have any parameters
						w = (Weapon)Weapon.class.getDeclaredField(
								name.toUpperCase()).get(new Weapon());
					}catch(Exception e1){
						w= new Weapon(name);
					}
					FightValue fv = searchFV(next.attributeValue("talent"));
					//TODO default fv with base at/pa
					if (fv!=null){ //if null: weapon can' be used without talent
						w.setType(fv);
						fv.getWeapons().addElement(w);
					}
				}
			}
			if(next.elements().size()!=0){
				iter(next);
			}
		}
	}
	
	/**
	 * search the fight value for a given name
	 * @param parentName the name of the fight value
	 * @return the object
	 */
	private FightValue searchFV(String parentName) {
		for(FightValue fv : fightvalues){
			if(fv.getName().equals(parentName)){
				return fv;
			}
		}
		return null;
	}
	
	public String attack(String special, int mod){
		//TODO attack
		return null;
	}
	
	public String defense(String special, int mod){
		//TODO defense
		return null;
	}
	
	public String cast(String spell, int mod){
		//TODO cast
		return null;
	}
	
	public String use(String talent, int mod){
		//TODO talents
		return null;
	}

	/**
	 * a hero is only described by his name
	 */
	public String toString(){
		return name;
	}

	/**
	 * return the name of this hero
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the initiative
	 * @return
	 */
	public int getINI() {
		return INI;
	}

	/**
	 * get all talents of this hero
	 * @return the talents
	 */
	public Vector<Talent> getTalents() {
		return talents;
	}
	
	/**
	 * return the value of the attribute described by the shortcut
	 * @param shortcut the shortcut of the attribute
	 * @return the value
	 */
	public int getAttrValue(String shortcut){
		for(int i=0; i<attributes.size(); i++){
			if(attributes.elementAt(i).getShortcut().equals(shortcut)){
				return attributes.elementAt(i).getValue();
			}
		}
		return -1;
	}

	/**
	 * get all attributes of this hero
	 * @return the attributes
	 */
	public Vector<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * get all fight values of this hero
	 * @return the fight values
	 */
	public Vector<FightValue> getFightvalues() {
		return fightvalues;
	}
	public Vector<Special> getSpecials() {
		return specials;
	}
	
	public int getCurrINI() {
		return currINI;
	}
	
	public int getCurrLEP() {
		return currLEP;
	}
	
	public int getCurrAUP() {
		return currAUP;
	}
	
	public int getCurrASP() {
		return currASP;
	}
	
	public int getCurrKAP() {
		return currKAP;
	}

	public Weapon getSelectedWeapon() {
		return selectedWeapon;
	}

	public void setSelectedWeapon(Weapon selectedWeapon) {
		this.selectedWeapon = selectedWeapon;
	}

	public void setCurrINI(int currINI) {
		this.currINI = currINI;
	}

	public boolean isPriest() {
		return priest;
	}

	public boolean isMagican() {
		return magican;
	}
}

package dsachat.share.hero;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * a Hero describes all attributes, talents, spells and weapons of an hero.
 *
 * @author bernshausen
 */
public class Hero implements Serializable {

    /**
     */
    private static final long serialVersionUID = 4235381890809856112L;

    private Vector<Attribute> attributes;

    Vector<Talent> talents;

    Vector<FightValue> fightvalues;

    Vector<Special> specials;

    private String name;

    private int INI;

    private int currINI = 0;

    private int currLEP = 0;

    private int currAUP = 0;

    private int currASP = 0;

    private int currKAP = 0;

    private Weapon selectedWeapon = null;

    //	private Armor head;
    private boolean priest = false;

    private boolean magican = false;

    /**
     * create a new hero from a xml file
     * @param xml the file this hero was stored by helden-software
     */
    public Hero(Document xml) {
    }

    /**
     * iteration over the document starting at root-element
     * get all necessary data from the file
     */
    @SuppressWarnings("unchecked")
    private void iter(Element e);

    /**
     * search the fight value for a given name
     * @param parentName the name of the fight value
     * @return the object
     */
    private FightValue searchFV(String parentName);

    public String attack(String special, int mod);

    public String defense(String special, int mod);

    public String cast(String spell, int mod);

    public String use(String talent, int mod);

    /**
     * a hero is only described by his name
     */
    public String toString();

    /**
     * return the name of this hero
     * @return the name
     */
    public String getName();

    /**
     * get the initiative
     * @return
     */
    public int getINI();

    /**
     * get all talents of this hero
     * @return the talents
     */
    public Vector<Talent> getTalents();

    /**
     * return the value of the attribute described by the shortcut
     * @param shortcut the shortcut of the attribute
     * @return the value
     */
    public int getAttrValue(String shortcut);

    /**
     * get all attributes of this hero
     * @return the attributes
     */
    public Vector<Attribute> getAttributes();

    /**
     * get all fight values of this hero
     * @return the fight values
     */
    public Vector<FightValue> getFightvalues();

    public Vector<Special> getSpecials();

    public int getCurrINI();

    public int getCurrLEP();

    public int getCurrAUP();

    public int getCurrASP();

    public int getCurrKAP();

    public Weapon getSelectedWeapon();

    public void setSelectedWeapon(Weapon selectedWeapon);

    public void setCurrINI(int currINI);

    public boolean isPriest();

    public boolean isMagican();
}

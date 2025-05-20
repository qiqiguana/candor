package dsachat.share;

import java.io.Serializable;
import java.util.Vector;
import dsachat.share.hero.Hero;
import dsachat.share.hero.Talent;
import dsachat.share.hero.Weapon;

/**
 * This class represents a challenge.<br>
 * It is created by a client and sent to the server which executes the internal task.
 * <br>
 * A challenge can be a talent, spell, attribute or weapon.
 *
 * @author bernshausen
 */
public class Challenge implements Serializable {

    private static final long serialVersionUID = 6777899032409172338L;

    private Talent t = null;

    private int attrib1;

    private int attrib2;

    private int attrib3;

    private int mod;

    private String to;

    private boolean silent;

    private String attrib = null;

    private int attrValue;

    private Weapon w = null;

    private boolean attack = false;

    private int dmgkk;

    /**
     * generate new challenge for spells and talents
     * @param tal the talents or spells name
     * @param modification difficulty
     * @param h the hero to get the parameters
     * @param silent response to sender?
     * @param response the receiver of the results
     */
    public Challenge(String tal, int modification, Hero h, boolean silent, String response) {
    }

    /**
     * create new attribute challenge
     * @param attrShortcut the shortcut for the attribute
     * @param h the hero to get the value
     * @param modification the difficulty
     * @param silent response to sender?
     * @param response the receiver
     */
    public Challenge(String attrShortcut, Hero h, int modification, boolean silent, String response) {
    }

    /**
     * create a new challenge for a fight
     * @param w the weapon
     * @param attack is attack? else defense
     * @param h the hero
     * @param modification difficulty
     * @param silent response to sender?
     * @param response the receiver
     */
    public Challenge(Weapon w, boolean attack, Hero h, int modification, boolean silent, String response) {
    }

    /**
     * get the named talent from hero
     * @param tal the name
     * @param h the hero
     * @return the talent (or spell)
     */
    private Talent getTalent(String tal, Hero h);

    /**
     * Set the attributes for a talent (spell) challenge
     * @param h the hero to get the values from
     */
    private void getAttrib(Hero h);

    /**
     * return the value of the attribute
     * @param h the hero
     * @param attr the shortcut of the attribute
     * @return the attributes value
     */
    private int getAttribFromHero(Hero h, String attr);

    /**
     * execute this challenge
     * differ between talents, attributes or fight
     * @return the result as string
     */
    public String roll();

    /**
     * roll the attack or defense with the given weapon
     * @return the results as string
     */
    private String rollFight();

    /**
     * execute talent challenge
     * @return results as string
     */
    private String rollTalent();

    /**
     * execute attribute challenge
     * @return result as string
     */
    private String rollAttr();

    /**
     * represents this challenge
     */
    public String toString();

    /**
     * roll a dice with variable sides
     * @param sides the sides this dice should have
     * @return a random number n between 1<=n<=sides
     */
    public static int dice(int sides);

    /**
     * return the receiver of the results
     * @return the receiver
     */
    public String getTo();

    /**
     * return the silent mode
     * @return the silent value
     */
    public boolean isSilent();
}

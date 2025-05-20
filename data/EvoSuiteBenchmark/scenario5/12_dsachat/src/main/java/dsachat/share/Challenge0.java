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

    /**
     * execute this challenge
     * differ between talents, attributes or fight
     *
     * @return the result as string
     */
    public String roll() {
        if (t != null)
            return rollTalent();
        if (attrib != null)
            return rollAttr();
        if (w != null) {
            return rollFight();
        }
        return " missing Talent, Spell, ...";
    }

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
     * roll the attack or defense with the given weapon
     * @return the results as string
     */
    private String rollFight();
}

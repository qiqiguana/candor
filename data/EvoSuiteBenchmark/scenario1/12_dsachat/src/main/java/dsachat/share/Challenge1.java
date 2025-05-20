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

    public static int dice(int sides) {
        int ret = -1;
        ret = (int) Math.rint((Math.random() * sides)) % sides + 1;
        return ret;
    }
}

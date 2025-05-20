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
     * return the value of the attribute described by the shortcut
     *
     * @param shortcut the shortcut of the attribute
     * @return the value
     */
    public int getAttrValue(String shortcut) {
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.elementAt(i).getShortcut().equals(shortcut)) {
                return attributes.elementAt(i).getValue();
            }
        }
        return -1;
    }
}

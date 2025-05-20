package bible.obj;

import java.util.ArrayList;

/**
 * 
 * @author  James Stauffer
 * @version $Id: IReference.java,v 1.2 2001/02/16 05:22:19 jstauffe Exp $
 */
public interface IReference {
    ArrayList getVerses(Translation[] translations);
}

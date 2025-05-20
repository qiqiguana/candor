package bible.obj;

import java.sql.SQLException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import bible.util.*;
import bible.servlet.*;

/**
 * 
 * @author  James Stauffer
 * @version $Id: ReferenceRange.java,v 1.5 2001/02/21 03:31:48 jstauffe Exp $
 */
public class ReferenceRange implements IReference {
    public static final String SEP = "-";
    public ReferenceRange(Reference begin, Reference end) {
        this.begin = begin;
        this.end = end;
    }

    public ReferenceRange(Reference begin) {
        this(begin, null);
    }

    public ArrayList getVerses(Translation[] translations) {
        return Verse.Get(translations, begin, end);
    }

    public Reference getBegin() {
        return begin;
    }

    public Reference getEnd() {
        return end;
    }

    public void setEnd(Reference ref) {
        end = ref;
    }

    public String toString() {
        return getClass().getName() + ":[" + begin + " - " + end + "]";
    }

    private Reference begin;
    private Reference end;

}


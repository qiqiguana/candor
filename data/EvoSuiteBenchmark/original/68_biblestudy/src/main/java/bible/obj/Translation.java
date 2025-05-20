package bible.obj;

import java.sql.SQLException;
import java.io.IOException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

import bible.util.*;
import bible.servlet.*;

/**
 * 
 *
 * @author  James Stauffer
 * @version $Id: Translation.java,v 1.6 2001/02/21 03:33:27 jstauffe Exp $
 */
public class Translation implements Identifible {
    public static final String KEY = "Translation";

    public int getId() {
        return id;
    }

    public String getValue() {//Identifible interface
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getAbbr() {
        return abbreviation;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isDefault() {
        return defaultId == id;
    }

    public static Translation[] GetAll() {
        return translations;
    }

    public static Translation[] Get(int[] id) {
        Translation[] trans = new Translation[id.length];
        for(int index = 0; index < id.length; index++) {
            trans[index] = Get(id[index]);
        }
        return trans;
    }

    public static Translation Get(int id) {
        return translations[id];
    }

    public static Translation[] Get(HttpServletRequest request) {
        String paramValue[] = request.getParameterValues(KEY);
        if(paramValue == null) {
            return null;
        } else {
            return Get(ServletUtil.GetIntParameters(request, KEY));
        }
    }

    public static int Normalize(int id) {
        if((id < 0) || (id >= translations.length) || (translations[id] == null)) {
            return defaultId;
        } else {
            return id;
        }
    }

    private int id;
    private String name;
    private String abbreviation;
    private String notes;
    private static Translation[] translations;
    private static int defaultId;//KJV

    private Translation(int id, String name, String abbreviation, String notes) {
        if(abbreviation.equals("KJV")) {
            defaultId = id;
        }
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.notes =  notes;
        translations[id] = this;
    }

    static {
        try {
            
            DbResult result = null;
            String query = "select max(book_id) bookMax from book";
            try {
                result = Database.Query(query);
                result.next();
                translations = new Translation[result.getInt("bookMax")];
            } catch(SQLException e) {
                Logger.Log(e, "Query=" + query);
            } finally {
                DbResult.Close(result);
            }
            
            result = null;
            query = "select Translation_ID, Translation_Name, Translation_Abr, Translation_Notes from translation";
            try {
                result = Database.Query(query);
                while(result.next()) {
                    new Translation(result.getInt("Translation_ID"), result.getString("Translation_Name"), 
                                    result.getString("Translation_Abr"), result.getString("Translation_Notes"));
                }
            } catch(SQLException e) {
                Logger.Log(e, "Query=" + query);
            } finally {
                DbResult.Close(result);
            }
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }
}


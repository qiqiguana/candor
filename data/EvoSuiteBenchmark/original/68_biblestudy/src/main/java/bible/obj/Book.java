package bible.obj;

import java.sql.SQLException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

import bible.util.*;
import bible.servlet.*;

/**
 * 
 *
 * @author  James Stauffer
 * @version $Id: Book.java,v 1.3 2001/02/21 03:07:47 jstauffe Exp $
 */
public class Book implements Identifible {
    public static final String KEY = "Book";
    public static final int BOOK_COUNT = 66;

    public static Book Get(int id) {
        return books[id];
    }

    public static Book Get(HttpServletRequest request) {
        return Get(ServletUtil.GetIntParameter(request, KEY));
    }

    public static Book Get(String userInput) throws ParseException {
        int bookIndex = -1;
        for(int index = 1; index < books.length; index++) {
            if(books[index].name.indexOf(userInput) >= 0) {//found it
                if((bookIndex == -1) //It hasn't been set yet
                    || (books[index].name.indexOf(userInput) < books[bookIndex].name.indexOf(userInput)))//current match occurs earlier in userInput
                {
                    bookIndex = index;
                }
            }
        }
        if(bookIndex == -1) {
            throw new ParseException("'" + userInput + "' not matched to any known book.", -1);
        }
        
        return books[bookIndex];
    }

    public int getId() {
        return id;
    }

    public String getValue() {//Identifible interface
        return getName();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getClass().getName() + ":[" + id + " " + name + "]";
    }

    public static Book[] GetAll() {
        return books;
    }

    private int id;
    private String name;
    private static Book[] books;

    private Book(int id, String name) {
        this.id = id;
        this.name = name;
        books[id] = this;
    }

    static {
        try {
            DbResult result = null;
            String query = "select book_id, book_name from book";
            books = new Book[BOOK_COUNT + 1];
            try {
                result = Database.Query(query);
                while(result.next()) {
                    new Book(result.getInt("book_id"), result.getString("book_name"));
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


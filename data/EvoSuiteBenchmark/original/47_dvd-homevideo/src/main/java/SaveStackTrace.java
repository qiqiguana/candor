/**
 * SaveStackTrace.java
 *
 * @author	Panagiotis Plevrakis
 *	        Dallas, TX
 * @version     Mar 15, 2002
 *
 */

import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * SaveStackTrace is a debugging class that provides one utility method.
 * The method takes an object of type Throwable as an argument and sends
 * the contents of printStackTrace to a file for better viewing.
 * Usage: Call from a catch block like this:
 *        try 
 *        {
 *	         ...
 *	  }
 *	  catch(Exception ex) 
 *        {
 *	      SaveStackTrace.printTrace(ex);
 *        }
 */
public class SaveStackTrace 
{
    private static PrintStream m_StackStream;

    /**
     * The static method that logs all exceptions to a file.
     * @param   path    location to write the error file
     * @param   e       the Throwable object
     */
    public static void printTrace(String path, Throwable e) 
    {
        try 
        {
            if(path == null)
                path = System.getProperty("user.home");
            m_StackStream = new PrintStream(new FileOutputStream(path + "/dvd-homevideo.err", true));
        }
        catch(IOException ex) 
        {
            System.err.println(e);
        }
        e.printStackTrace(m_StackStream);
    }
}
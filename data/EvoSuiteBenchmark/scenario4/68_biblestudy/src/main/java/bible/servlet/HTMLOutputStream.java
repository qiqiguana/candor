package bible.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

/**
 * A container for output streams with its own buffers
 *
 * @author Luke Samaha
 */
public class HTMLOutputStream {

    public HTMLOutputStream(HttpServletResponse response) throws IOException {
    }

    public void print(String str);

    public void print(int n);

    public void println(String str);

    public void println(int n);

    public void flush() throws IOException;

    public int getSize();

    public PrintWriter getPW();

    /**
     * @author James Stauffer
     * @return String the contents of the buffer.
     */
    public String reset();

    public String toString();

    //  Private instance variables
    private HttpServletResponse response = null;

    private boolean binary = false;

    private ByteArrayOutputStream baos = null;

    private PrintWriter pw = null;
}

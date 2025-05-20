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

    /**
     * @author James Stauffer
     * @return String the contents of the buffer.
     */
    public String reset() {
        String contents = baos.toString();
        baos.reset();
        return contents;
    }
}

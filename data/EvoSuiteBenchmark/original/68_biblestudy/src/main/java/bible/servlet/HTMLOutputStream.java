/*
 * BufferedOutputStream.java: Writes to a
 * buffer and handles ContentLength's and Types, as well as cleanup.
 * Copyright (C) 1998. All rights reserved.
 * St. Paul Software, St. Paul, MN, USA
 */

package bible.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

/**
 * A container for output streams with its own buffers
 *
 * @author  Luke Samaha
 */
public class HTMLOutputStream {

    //  Use-specific Constructors

    public HTMLOutputStream(HttpServletResponse response) throws IOException {
        this.response = response;
        baos = new ByteArrayOutputStream(4096);
        OutputStream out = baos;

        //  Initialization routines specific to content type
        pw = new PrintWriter(out, true);
    }

    //  Public client methods

    public void print(String str) {
        pw.print(str);
    }

    public void print(int n) {
        pw.print(n);
    }

    public void println(String str) {
        pw.println(str);
    }

    public void println(int n) {
        pw.println(n);
    }

    public void flush() throws IOException {
        response.setContentType("text/html");
        response.setContentLength(baos.size());
        OutputStream os = response.getOutputStream();

        baos.writeTo(os);

        os.flush();
        reset();
    }

    public int getSize() {
        return baos.size();
    }

    public PrintWriter getPW() {
        return pw;
    }

    /**
     * @author James Stauffer
     * @return String the contents of the buffer.
     */
    public String reset() {
        String contents = baos.toString();
        baos.reset();
        return contents;
    }

    public String toString() {
        return getClass().getName() +": [size=" + getSize()
            + ", response=" + response
            + ((baos != null) ? ", baos" : "")
            + ((pw != null) ? ", pw" : "")
            + "]";
    }

    //  Private instance variables
    private HttpServletResponse response = null;
    private boolean binary = false;
    private ByteArrayOutputStream baos = null;
    private PrintWriter pw = null;
}

package org.heal.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An {@link org.heal.servlet.Action} that writes data into a delimited
 * text file.  This is used for simple forms to drop form data into a file.
 * Any new lines in form data will be lost since excel was having trouble
 * handling them.
 *
 * @version 1.1
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>) 
 */
public abstract class DelimitedFileWriterAction implements Action {
    private static final String DEFAULT_DELIMITER = "|";

    private static Map locks = new HashMap();

    private String delimiter = DEFAULT_DELIMITER;

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        List data = getData(request);
        if(null != data) {
            File out = new File(servlet.getServletContext().getRealPath("/") +"../private/logs/" +getLogFilename());
            appendDataToFile(out, data);
        }
        if(getSuccessPage()!=null){
        RequestDispatcher rd = request.getRequestDispatcher(getSuccessPage());
        rd.forward(request, response);
        }
    }

    /**
     * Appends delimited data to a file.
     *
     * @param file The <code>File</code> to write to.  The file is expected to
     *      be in the /docs/logs folder.
     * @param data A <code>List</code> of Strings to delimit and write to the file.
     * @throws IOException Thrown if there are problems writing to the file.
     */
    private void appendDataToFile(File file, List data) throws IOException {
        // Constructs the delimited String to
        StringBuffer outputString = new StringBuffer();
        for(Iterator iter = data.iterator(); iter.hasNext();) {
            String atom = (String)iter.next();
            if(null != atom) {
                // Removes newline characters because excel seems resistant
                // to importing delimited data containing them
                atom = atom.replace('\n', ' ').replace('\r', ' ');
				if(-1 != atom.indexOf(getDelimiter()) || -1 != atom.indexOf("\"")) {
					atom = "\"" +atom.replaceAll("\"", "\"\"")+ "\"";
				}
                outputString.append(atom);
            }
			if(iter.hasNext()) {
            	outputString.append(getDelimiter());
			}
        }
        // Adds a line separator to denote a new record
        outputString.append(System.getProperty("line.separator"));

        Object lock;
        synchronized(locks) { // Obtains the lock object for a given File
            if(locks.containsKey(file)) {
                lock = locks.get(file);
            } else {
                lock = new Object();
                locks.put(file, lock);
            }
        }
        synchronized(lock) {
            // Prevents more than one thread to write to a File at a time
            FileWriter out = new FileWriter(file.getAbsolutePath(), true);
            out.write(outputString.toString());
            out.close();
        }
    }

    /**
     * @return The delimiter used when writing to a log file. The default
     *      delimiter is <code>'|'</code>.
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * Sets what delimiter to use in the log file.  Typically this is
     * not called, or called only in an initialization block of a
     * subclass of this class.
     *
     * @param str
     */
    public void setDelimiter(String str) {
        if(null != str) {
            delimiter = str;
        } else {
            throw new IllegalArgumentException("Delimiter cannot be null");
        }
    }

    /**
     * Convenience method to get a form parameter from the <code>HttpServletRequest</code>
     * object.  Right now all this does is convert nulls to empty strings.
     *
     * @param req
     * @param parameterName
     * @return
     */
    protected static String getFormParameter(HttpServletRequest req, String parameterName) {
        String ret = req.getParameter(parameterName);
        return (null != ret ? ret : "");
    }


    /**
     * @return The name of the file to write the delimited data to.  This file
     *      need not include path information -- it will show up in the /private/logs/
     *      folder.
     */
    public abstract String getLogFilename();

    /**
     * @return A <code>String</code> representation of the page to forward
     *      to upon a successful form submission.
     */
    public abstract String getSuccessPage();

    /**
     * @param request The <code>HttpServletRequest</code> to parse the data
     *      from.
     * @return A <code>List</code> of String data that will be saved.
     */
    public abstract List getData(HttpServletRequest request);
}

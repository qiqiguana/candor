package nu.staldal.xtree;

import java.util.*;
import java.io.*;
import java.net.URL;
import javax.xml.parsers.*;
import org.xml.sax.*;

/**
 * Build an XTree from a SAX2 event stream, or by parsing an XML document.
 *
 * The TreeBuilder ensures that the tree will not have two adjacent Text nodes.
 */
public class TreeBuilder implements ContentHandler, ErrorHandler {

    /**
     * Create an SAX InputSource from a File object.
     *
     * @param file the file
     * @return an InputSource
     * @throws FileNotFoundException if the file doesn't exist
     * @throws FileNotFoundException if some I/O error occurs
     */
    public static InputSource fileToInputSource(File file) throws FileNotFoundException, IOException;
}

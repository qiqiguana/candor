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

    public static InputSource fileToInputSource(File file) throws FileNotFoundException, IOException {
        // we should use File.toURL() here, but it's Java2
        String absPath = file.getCanonicalPath().replace(File.separatorChar, '/');
        String systemId = "file:" + ((absPath.charAt(0) == '/') ? "//" : "///") + absPath;
        if (!file.isFile())
            throw new FileNotFoundException(systemId);
        return new InputSource(systemId);
    }
}

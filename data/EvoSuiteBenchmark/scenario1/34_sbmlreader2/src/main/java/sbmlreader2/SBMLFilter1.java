package sbmlreader2;

import cytoscape.data.ImportHandler;
import cytoscape.data.readers.GraphReader;
import cytoscape.util.CyFileFilter;
import java.io.File;
import java.io.IOException;

/**
 * SBMLReader extends CyFileFilter for integration into the Cytoscape ImportHandler
 * framework.
 */
public class SBMLFilter extends CyFileFilter {

    public GraphReader getReader(String fileName) {
        System.out.println("Getting SBML reader ...");
        try {
            // Test if libsbml library is available
            System.loadLibrary("sbmlj");
            /* Extra check to be sure we have access to libSBML: */
            Class.forName("org.sbml.libsbml.libsbml");
            // Use the libSBML based parser
            System.out.println("... using 'libsbml parser'");
            return new SBMLGraphReader2(fileName);
        } catch (Exception e) {
            // If any error occurs use the classic SBML Reader
            return new SBMLGraphReader(fileName);
        }
    }
}

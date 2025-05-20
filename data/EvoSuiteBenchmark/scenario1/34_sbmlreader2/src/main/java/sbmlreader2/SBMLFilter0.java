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

    public boolean accept(File file) {
        String fileName = file.getName();
        boolean firstPass = false;
        //  First test:  file must end with one of the registered file extensions.
        for (int i = 0; i < fileExtensions.length; i++) {
            if (fileName.endsWith(fileExtensions[i])) {
                firstPass = true;
            }
        }
        if (firstPass) {
            //  Second test:  file header must contain the biopax declaration
            try {
                String header = getHeader(file);
                if (header.indexOf("www.sbml.org") > 0) {
                    return true;
                }
            } catch (IOException e) {
            }
        }
        return false;
    }
}

package common;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import messages.global.MapInfo;

/**
 * class for listing maps and returning map information
 *
 * @author christian
 */
public class MapFilesLister {

    /**
     * reads directory and returns a list of found xml-files
     *
     * @param dir -
     *            directory to be searched in
     * @return Vector<File>
     */
    public Vector<File> ListMaps(String dir);
}

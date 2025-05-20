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

    private static final Logger logger = Logger.getLogger(MapFilesLister.class);

    public MapFilesLister() {
    }

    /**
     * reads directory and returns a list of found xml-files
     *
     * @param dir -
     *            directory to be searched in
     * @return Vector<File>
     */
    public Vector<File> ListMaps(String dir);

    /**
     * retrieve list of MapInfo objects
     *
     * @return
     */
    public List<MapInfo> getMapInfoList();

    /**
     * clears unused or obsolete map preview files
     */
    public void clearPreviews();
}

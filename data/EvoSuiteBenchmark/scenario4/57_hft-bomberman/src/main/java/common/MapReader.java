package common;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.awt.Point;
import javax.xml.xpath.*;
import java.util.Date;

/**
 * Loads a XML-file and write read properties, tiles and start points to
 * map-object
 *
 * @author christian
 */
public class MapReader {

    private XmlFunctions xmlFunctions;

    private String mapFilePath;

    private static final Logger logger = Logger.getLogger(MapReader.class);

    /**
     * constructor
     *
     * @param mapFile -
     *            Path to XML-file with map information
     */
    public MapReader(String mapFile) {
    }

    /**
     * Returns a specific value in a group of nodes using XPath expressions
     *
     * @param node -
     *            node(set) in which value should be searched
     * @param XMLPath -
     *            XPath expression
     * @return String - found value
     */
    public String getXMLValue(Node node, String XMLPath);

    /**
     * Sets read properties to map-object using the setter-methods of map-object
     *
     * @param map -
     *            Map object
     */
    public void setMapProperies(Map map);

    /**
     * sets the specific tiles to map object
     *
     * @param map
     */
    public void setTiles(Map map);

    public void setPowerups(Map map);

    /**
     * automatically sets wall on the border of map set
     *
     * @param map
     */
    public void autoWall(Map map, String Type);

    /**
     * sets the start-points in map object. start points are read from xml
     *
     * @param map
     */
    public void setStartPoints(Map map);

    /**
     * gets area of map
     *
     * @return Point with x and y dimension/number of tiles
     */
    public Point getArea();
}

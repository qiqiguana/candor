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
}

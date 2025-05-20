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
    public String getXMLValue(Node node, String XMLPath) {
        try {
            // todo: exception wenn feld leer
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile(XMLPath);
            Object result = expr.evaluate(node, XPathConstants.NODE);
            Node foundNode = (Node) result;
            return foundNode.getNodeValue();
        } catch (XPathExpressionException ex) {
            logger.error(ex);
        }
        return new String();
    }
}

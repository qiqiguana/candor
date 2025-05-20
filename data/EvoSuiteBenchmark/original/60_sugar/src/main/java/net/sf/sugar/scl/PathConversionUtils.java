package net.sf.sugar.scl;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.CDATASection;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 10-Aug-2008
 * Time: 22:21:04
 *
 * @author kbishop
 * @version $Id$
 */
public class PathConversionUtils {

    static String  convertToXPath(String key) {
        StringBuffer xpath = new StringBuffer("/" + SCLParser.SCL_ROOT_ELEMENT + "/").append(key.replace(".", "/"));
        return xpath.toString();
    }

   static Element processDotDelimitedPath(Element parentElement, String propertyName) {

        String[] pathSteps = propertyName.split("\\.");

        Document doc = parentElement.getOwnerDocument();
        Element currentElement = parentElement;

        for (String step : pathSteps) {
            NodeList childNodes = currentElement.getElementsByTagName(step);
            if (childNodes.getLength() < 1) {
                Element newElement = doc.createElement(step);
                currentElement.appendChild(newElement);
                currentElement = newElement;
            } else {
                currentElement = (Element)childNodes.item(0);
            }
        }

        return currentElement;
    }
}

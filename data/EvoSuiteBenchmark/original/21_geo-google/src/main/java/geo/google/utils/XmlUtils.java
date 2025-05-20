package geo.google.utils;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XmlUtils {

  private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
  public static XPath newXPath() {
    return XPATH_FACTORY.newXPath();
  }

  /**
   * Parse an xml string into document.
   */
  public static Document parse(String xml) 
  throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    return builder.parse(stringToInputSource(xml));
  }
  
  /**
   * @return an InputSource based on the given string of xml
   */
  public static InputSource stringToInputSource(String xml) {
    return new InputSource(new StringReader(xml));
  }
  /**
   *  Selects a single value using the specified XPath. 
   *  @throws XPathExpressionException 
   */
  public static String selectValue(Node context, String xpath) throws XPathExpressionException {
    return newXPath().evaluate(xpath, context);
  }

  /**
   *  Selects a single node using the specified XPath. 
   *  @throws XPathExpressionException 
   */
  public static Node selectNode(Node context, String xpath) throws XPathExpressionException {
    return (Node)newXPath().evaluate(xpath, context, XPathConstants.NODE);
  }
  
  /**
   * Depth first traversal
   * 
   * preProcess() function in NodeProcessor is called before visiting children
   * postProcess() function in NodeProcessor is called before visiting children
   * 
   * @param node - the root of the tree for traversal
   * @param processor - process function to called when visit the node
   */
    public static void traverseTreeDepthFirst(Node node, NodeProcessor processor){
      processor.preProcess(node);
      Node child = node.getFirstChild();
      while(child != null){
        traverseTreeDepthFirst(child, processor);
        child = child.getNextSibling();
      }
      processor.postProcess(node);
    }
}

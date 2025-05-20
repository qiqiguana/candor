package framework.util;

import framework.base.BaseBean;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xerces.parsers.DOMParser;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.util.PropertyUtils;

/**
 * XML utility class
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:51 $
 */
public final class XMLUtils
{

  // Logger
  private static Log log = LogFactory.getLog(XMLUtils.class);

  /**
   * Protected constructor
   */
  protected XMLUtils()
  {
  }

  /**
   * Convert an Object into an XML DOM document
   * The hashcode is used as ID
   * @param bean The object to convert
   * @param beanName The object name
   * @return XML Document representing the object
   */
  public static Document convertDOM(Object bean, String beanName)
  throws Exception
  {
    Document doc = new DocumentImpl();
    doc.appendChild(convertDOM(doc, bean, beanName));
    return doc;
  }

  /**
   * Convert an Object into XML element
   * @param doc The XML document
   * @param bean The object to convert
   * @param beanId The object ID
   * @return XML element representing the object
   */
  public static Element convertDOM(Document doc, Object bean, String beanId)
  throws Exception
  {
    Map properties = PropertyUtils.describe(bean);

    Element object = doc.createElement("object");

    beanId = beanId!=null?beanId:String.valueOf(bean.hashCode());
    object.setAttribute("id", beanId);
    object.setAttribute("class", bean.getClass().getName());

    Iterator propertyIterator = properties.keySet().iterator();
    while (propertyIterator.hasNext()) 
    {
      String propertyName = (String)propertyIterator.next();
      Object propertyValue = properties.get(propertyName);

      Element property = doc.createElement("property");
      Element name = doc.createElement("name");
      name.appendChild(doc.createTextNode(propertyName));
      property.appendChild(name);

      Element value = doc.createElement("value");
      if (propertyValue==null) value.appendChild(doc.createTextNode(""));
      else
      {
        if (propertyValue instanceof Map)
        {
          Map values = (Map)propertyValue;
          Iterator it = values.keySet().iterator();
          while (it.hasNext()) 
          {
            String valueName = (String)it.next();
            Object valueObject = values.get(valueName);
            value.appendChild(convertDOM(doc, valueObject, valueName)); 
          }
        }
        else if (propertyValue instanceof Collection)
        {
          Collection values = (Collection)propertyValue;
          Iterator it = values.iterator();
          while (it.hasNext()) 
          {
             value.appendChild(convertDOM(doc, it.next(), null)); 
          }
        }
        else if (propertyValue instanceof BaseBean)
        {
          value.appendChild(convertDOM(doc, propertyValue, null));
        }
        else
        {
          value.appendChild(doc.createTextNode(ConvertUtils.convertString(propertyValue)));
        }
      }
      property.appendChild(value);
      object.appendChild(property);
    }

    return object;
  }

  /**
   * Read a file as a XML DOM document
   * @param fileName Full path to file
   * @return The XML Document
   */  
  public static Document readDOMFile(String fileName)
  throws Exception
  {
    DOMParser parser = new DOMParser();
    parser.parse(fileName);
    return parser.getDocument();
  }

  /**
   * Get formating options from the document
   * @param doc The XML document
   * @return The initialized OutputFormat
   */
  public static OutputFormat getXMLOutputFormat(Document doc)
  throws IOException
  {
    OutputFormat format = new OutputFormat(doc);
    format.setEncoding("ISO-8859-1");
    format.setIndenting(true);
    format.setPreserveEmptyAttributes(true);
    format.setIndent(2);
    return format;
  }
  
  /**
   * Write an XML file as a file
   * @param doc The XML document
   * @param fileName Full path to the file
   */
  public static void writeDOMFile(Document doc, String fileName)
  throws IOException
  {
    OutputFormat format = getXMLOutputFormat(doc);
//    format.setPreserveSpace(true);
//    format.setStandalone(true);
    File file = FileUtils.createFile(fileName);

//    FileUtils.checkDirectory();
    FileWriter out = new FileWriter(file);
    XMLSerializer serial = new XMLSerializer(out, format);
    serial.asDOMSerializer();
    serial.serialize(doc.getDocumentElement());

    out.flush();
    out.close();
  }

  /**
   * Convert an XML document into a String
   * @param doc The XML document to convert
   */
  public static String toString(Document doc)
  throws IOException
  {
    OutputFormat format  = getXMLOutputFormat(doc);
    StringWriter stringOut = new StringWriter();
    XMLSerializer stringSerial = new XMLSerializer(stringOut, format);
    stringSerial.asDOMSerializer();
    stringSerial.serialize(doc.getDocumentElement());
    return stringOut.toString();
  }

}
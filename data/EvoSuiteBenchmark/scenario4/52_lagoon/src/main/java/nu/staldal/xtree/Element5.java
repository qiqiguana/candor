package nu.staldal.xtree;

import java.util.Vector;
import java.net.URL;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

/**
 * An XML Element.
 */
public class Element extends NodeWithChildren {

    static final long serialVersionUID = -1804355746259349573L;

    final String namespaceURI;

    final String localName;

    URL baseURI = null;

    Vector attrName;

    Vector attrValue;

    Vector attrType;

    Vector namespacePrefixes;

    Vector namespaceURIs;

    char xmlSpaceAttribute = ' ';

    /**
     * Construct an element.
     *
     * @param namespaceURI  the namespace URI for this element,
     *                      may be the empty string
     * @param localName	the element name
     */
    public Element(String namespaceURI, String localName) {
    }

    /**
     * Construct an element.
     *
     * @param namespaceURI  the namespace URI for this element,
     *                      may be the empty string
     * @param localName	the element name
     * @param numberOfAttributes  the number of attributes this element should have
     */
    public Element(String namespaceURI, String localName, int numberOfAttributes) {
    }

    /**
     * Construct an element.
     *
     * @param namespaceURI  the namespace URI for this element,
     *                      may be the empty string
     * @param localName	the name of this element (no namespace)
     * @param numberOfAttributes  the number of attributes this element should have
     * @param numberOfChildren  the number of children this element should have
     */
    public Element(String namespaceURI, String localName, int numberOfAttributes, int numberOfChildren) {
    }

    /**
     * Get the namespace URI for this element. May be the empty string.
     */
    public String getNamespaceURI();

    /**
     * Get the name of this element.
     * The name does not include namespace URI or prefix.
     */
    public String getLocalName();

    /**
     * Lookup the index of an attribute to this element. The returned index
     * may be used as argument to other methods in this class.
     *
     * @param namespaceURI  the namespace URI, may be the empty string
     * @param localName  the name
     * @return the index of the attribute, or -1 if no such attribute exists
     *
     * @see #getAttributeValue
     * @see #getAttributeType
     * @see #removeAttribute
     */
    public int lookupAttribute(String namespaceURI, String localName);

    /**
     * Add an attribute to this element.
     *
     * The attribute type is one of the strings
     * "CDATA", "ID", "IDREF", "IDREFS", "NMTOKEN", "NMTOKENS",
     * "ENTITY", "ENTITIES", or "NOTATION" (always in upper case).
     *
     * @param namespaceURI  the namespace URI, may be the empty string
     * @param localName  the name
     * @param type  the type (use "CDATA" if the type is irrelevant)
     * @param value  the value
     */
    public void addAttribute(String namespaceURI, String localName, String type, String value);

    /**
     * Remove an attribute at the specified index.
     * This method is a bit inefficient.
     *
     * @param index  the index as returned from {@link #lookupAttribute}
     * @throws IndexOutOfBoundException  if no such attribute exist.
     */
    public void removeAttribute(int index) throws IndexOutOfBoundsException;

    /**
     * Return the number of attributes this element have.
     */
    public int numberOfAttributes();

    /**
     * Get the namespace URI for the attribute at the specified index.
     *
     * @param index  the index as returned from {@link #lookupAttribute}
     *
     * @return the namespace URI, may be (and is usually) the empty string,
     *         or <code>null</code> if index is -1
     * @throws IndexOutOfBoundsException  if no such attribute exist.
     */
    public String getAttributeNamespaceURI(int index) throws IndexOutOfBoundsException;

    /**
     * Get the name of the attribute at the specified index.
     *
     * @param index  the index as returned from {@link #lookupAttribute}
     *
     * @return the localName,
     *         or <code>null</code> if index is -1
     * @throws IndexOutOfBoundsException  if no such attribute exist.
     */
    public String getAttributeLocalName(int index) throws IndexOutOfBoundsException;

    /**
     * Get the type of the attribute at the specified index.
     *
     * The attribute type is one of the strings
     * "CDATA", "ID", "IDREF", "IDREFS", "NMTOKEN", "NMTOKENS",
     * "ENTITY", "ENTITIES", or "NOTATION" (always in upper case).
     *
     * @return the attribute type,
     *         or <code>null</code> if index is -1
     * @param index  the index as returned from {@link #lookupAttribute}
     * @throws IndexOutOfBoundsException  if no such attribute exist.
     */
    public String getAttributeType(int index) throws IndexOutOfBoundsException;

    /**
     * Get the value of the attribute at the specified index.
     *
     * @return the attribute value,
     *         or <code>null</code> if index is -1
     * @param index  the index as returned from {@link #lookupAttribute}
     * @throws IndexOutOfBoundsException  if no such attribute exist.
     */
    public String getAttributeValue(int index) throws IndexOutOfBoundsException;

    void setNamespaceMappings(Vector prefixes, Vector URIs);

    /**
     * Add a namespace mapping to this element.
     *
     * @param prefix  the prefix
     * @param URI  the namespace URI
     */
    public void addNamespaceMapping(String prefix, String URI);

    /**
     * Return the number of namespace mapping for this element.
     */
    public int numberOfNamespaceMappings();

    /**
     * Return a namespace mapping at the specified index.
     *
     * @return a String[] with [0] = prefix, [1] = namespace URI
     * @throws IndexOutOfBoundsException  if no such mapping exist.
     */
    public String[] getNamespaceMapping(int index) throws IndexOutOfBoundsException;

    public String lookupNamespaceURI(String prefix);

    public String lookupNamespacePrefix(String URI);

    /**
     * Set the baseURI property of this element.
     *
     * @param URI  the base URI, must be absolute
     */
    public void setBaseURI(URL URI);

    public URL getBaseURI();

    public boolean getPreserveSpace();

    public String getInheritedAttribute(String namespaceURI, String localName);

    /**
     * Fire the startElement event to the given SAX2 ContentHandler.
     * Will also fire startPrefixMapping events.
     */
    public void outputStartElement(ContentHandler sax) throws SAXException;

    /**
     * Fire the endElement event to the given SAX2 ContentHandler.
     * Will also fire endPrefixMapping events.
     */
    public void outputEndElement(ContentHandler sax) throws SAXException;

    public void toSAX(ContentHandler sax) throws SAXException;

    /**
     * Shortcut method for getting the value of an attribute without
     * namespace.
     *
     * @return the attrubute value, or <code>null</code>
     * 		if the attribute doesn't exist
     */
    public String getAttrValueOrNull(String localName);

    /**
     * Shortcut method for getting the value of an attribute without
     * namespace.
     *
     * @return the attrubute value, never <code>null</code>
     * @throws SAXParseException if the attribute doesn't exist
     */
    public String getAttrValue(String localName) throws SAXParseException;

    /**
     * Shortcut method for getting the value of an attribute with
     * namespace.
     *
     * @return the attrubute value, or <code>null</code>
     * 		if the attribute doesn't exist
     */
    public String getAttrValueOrNull(String namespaceURI, String localName);

    /**
     * Shortcut method for getting the value of an attribute with
     * namespace.
     *
     * @return the attrubute value, never <code>null</code>
     * @throws SAXParseException if the attribute doesn't exist
     */
    public String getAttrValue(String namespaceURI, String localName) throws SAXParseException;

    /**
     * Shortcut method for getting the text content of an Element.
     *
     * @return if there is a single Text child, return its value,
     *         if there is no children, return "",
     *         or <code>null</code>
     *         if there are more than one children or one non-Text child
     */
    public String getTextContentOrNull();

    /**
     * Shortcut method for getting the text content of an Element.
     *
     * @return if there is a single Text child, return its value,
     *         if there is no children, return "",
     *         never <code>null</code>.
     * @throws SAXParseException
     *         if there are more than one children or one non-Text child
     */
    public String getTextContent() throws SAXParseException;

    /**
     * Shortcut method for getting the first Element child with a
     * specified name.
     *
     * @return  the first child Element with the specified name,
     *          or <code>null</code> if there is no such child.
     */
    public Element getFirstChildElementOrNull(String namespaceURI, String localName);

    /**
     * Shortcut method for getting the first Element child with a
     * specified name.
     *
     * @return  the first child Element with the specified name,
     *          never <code>null</code>.
     * @throws SAXParseException
     *         if there is no such child.
     */
    public Element getFirstChildElement(String namespaceURI, String localName) throws SAXParseException;

    /**
     * Shortcut method for getting the first Element children with any name.
     *
     * @return  the first child Element
     *          or <code>null</code> if there are no Element children.
     */
    public Element getFirstChildElementOrNull();

    /**
     * Shortcut method for getting the first Element children with any name.
     *
     * @return  the first child Element
     *          never <code>null</code>.
     * @throws SAXParseException
     *         if there are no Element children.
     */
    public Element getFirstChildElement() throws SAXParseException;
}

package net.sf.xbus.base.xml;

import java.util.LinkedList;
import java.util.List;

import net.sf.xbus.base.core.XException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>XDomSupport</code> is a collection of operations on DOM trees. It
 * extends the possibilities offered in the <code>org.w3c.dom</code> package.
 * 
 * @author Stephan Düwel
 */
public abstract class XDomSupport
{

	/**
	 * <code>getChildNodesByAttrValue</code> retrieves child nodes selected by
	 * a certain value of a certain attribute. Child nodes without the specified
	 * attribute are not selected.
	 * 
	 * @param parent the node for which children are searched
	 * @param attrName the attribute to check
	 * @param attrValue the attribute's value to search for
	 * @param nodeTag the tag name for the searched child nodes, if
	 *            <code>null</code> or empty string the tag name is not used
	 *            for the selection
	 * @return the list of selected child nodes
	 * @throws IllegalArgumentException in case that <code>parent</code>,
	 *             <code>attrName</code> or <code>attrValue</code> are
	 *             <code>null</code> or <code>attrName</code> is empty
	 */
	public static List getChildNodesByAttrValue(Node parent, String attrName,
			String attrValue, String nodeTag) throws IllegalArgumentException
	{
		if (parent == null)
			throw new IllegalArgumentException("Parent node may not be null");
		if (attrName == null || attrName.length() == 0)
			throw new IllegalArgumentException(
					"Attribute name may not be empty");
		if (attrValue == null)
			throw new IllegalArgumentException(
					"Attribute value may not be null");

		LinkedList result = new LinkedList();
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); i++)
		{ // Check all children
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& ((Element) child).getAttribute(attrName).equals(
							attrValue))
				if (nodeTag != null && nodeTag.length() > 0)
					// Tag name must be checked
					if (child.getNodeName().equals(nodeTag))
						result.add(child);
					else
						result.add(child);
		} // for (int i=0; i<children.getLength(); i++)
		return result;
	} // getChildNodesByAttrValue (Node parent, String attrName, String
	// attrValue, String nodeTag)

	public static String getNodeText(Node node)
	{
		// The value is stored in child nodes of type Text
		NodeList children = node.getChildNodes();
		String foundValue = "";
		for (int j = 0; j < children.getLength(); j++)
		{ // Check all children
			Node child = children.item(j);
			if (child.getNodeType() == Node.TEXT_NODE)
			{ // Concatenate all text node contents
				foundValue = foundValue.concat(child.getNodeValue());
			} // if (child.getNodeType()==Node.TEXT_NODE)
		} // for (int j=0; j<children.getLength(); j++)
		return foundValue;
	} // getNodeText(Node node)

	/**
	 * <code>getTrimedNodeText</code> retrieves the value for a node
	 * representing the corresponding xml tag.
	 * 
	 * @param node the node representing the xml tag
	 * @return the value stored within the xml tag, the string is trimed for
	 *         leading and ending white space
	 */
	public static String getTrimedNodeText(Node node)
	{
		// The value is stored in child nodes of type Text
		NodeList children = node.getChildNodes();
		String foundValue = "";
		for (int j = 0; j < children.getLength(); j++)
		{ // Check all children
			Node child = children.item(j);
			if (child.getNodeType() == Node.TEXT_NODE)
			{ // Concatenate all text node contents
				foundValue = foundValue.concat(child.getNodeValue());
				foundValue = foundValue.trim();
				// trim in between
			} // if (child.getNodeType()==Node.TEXT_NODE)
		} // for (int j=0; j<children.getLength(); j++)
		return foundValue;
	} // getTrimedNodeText(Node node)

	/**
	 * <code>getChildElementsByValue</code> retrieves child nodes of type
	 * element selected by a certain for the value stored with them.
	 * 
	 * @param parent the node for which children are searched
	 * @param value the value to search for
	 * @param nodeTag the tag name for the searched child nodes, if
	 *            <code>null</code> or empty string the tag name is not used
	 *            for the selection
	 * @return the list of selected child nodes
	 * @throws IllegalArgumentException in case that <code>parent</code> or
	 *             <code>value</code> are <code>null</code>
	 */
	public static List getChildElementsByValue(Node parent, String value,
			String nodeTag) throws IllegalArgumentException
	{
		if (parent == null)
			throw new IllegalArgumentException("Parent node may not be null");
		if (value == null)
			throw new IllegalArgumentException("Value may not be null");

		LinkedList result = new LinkedList();
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); i++)
		{ // Check all children
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& (nodeTag == null || nodeTag.length() == 0 || child
							.getNodeName().equals(nodeTag))
					&& value.equals(getTrimedNodeText(child)))
				result.add(child);
		} // for (int i=0; i<children.getLength(); i++)
		return result;
	} // getChildElementsByValue (Node parent, String value, String nodeTag)

	/**
	 * <code>getNextNodeIndexIgnoringFormatingText</code> retrieves the
	 * position of the next node after the position <code>startPos</code>
	 * within the list <code>nodes</code> which is not a text node only
	 * containing whitespace.
	 * 
	 * @param nodes the node list
	 * @param startPos the position to start from
	 * @return the position; -1 if no node was found
	 */
	public static int getNextNodeIndexIgnoringFormatingText(NodeList nodes,
			int startPos)
	{
		int pos;
		boolean nodeNotFound = true;
		for (pos = startPos; pos < nodes.getLength() && nodeNotFound; pos++)
		{ // Search all nodes afetr position <startpos>
			Node node = nodes.item(pos);
			if (node != null
					&& (node.getNodeType() != Node.TEXT_NODE || node
							.getNodeValue().trim().length() > 0))
				nodeNotFound = false;
		}
		if (nodeNotFound)
			pos = -1;
		else
			pos--; // <pos> incremented once in for loop after finding the node
		return pos;
	} // getNextNodeIndexIgnoringFormatingText(NodeList nodes, int startPos)

	/**
	 * <code>deleteWhitespaceTextInElementNodes</code> deletes all text nodes
	 * containing only whitespace within xml tags. This operation is useful to
	 * get rid of formatting information in a parsed xml file.
	 * 
	 * @param doc the DOM tree to clean
	 * @throws XException only for syntactical reasons (use of a generic
	 *             traversal method)
	 */
	public static void deleteWhitespaceTextInElementNodes(Document doc)
			throws XException
	{
		IteratedWhitespaceInElementDeletion itop = new IteratedWhitespaceInElementDeletion();
		traversePreOrder(doc, itop);
	} // deleteWhitespaceTextInElementNodes(Document doc)

	/**
	 * <code>deleteWhitespaceTextInElementNodesAndComments</code> deletes all
	 * text nodes containing only whitespace within xml tags and all comment
	 * nodes. This operation is useful to get rid of formatting and editor
	 * information in a parsed xml file.
	 * 
	 * @param doc the DOM tree to clean
	 * @throws XException only for syntactical reasons (use of a generic
	 *             traversal method)
	 */
	public static void deleteWhitespaceTextInElementNodesAndComments(
			Document doc) throws XException
	{
		IteratedWhitespaceInElementAndCommentDeletion itop = new IteratedWhitespaceInElementAndCommentDeletion();
		traversePreOrder(doc, itop);
	} // deleteWhitespaceTextInElementNodesAndComments(Document doc)

	/**
	 * <code>traversePreOrder</code> traverses a DOM tree in pre-order and
	 * executes the specified operation on each node. The traversal may treat
	 * dynamically deleted nodes but no extensions.
	 * 
	 * @param doc the DOM tree to traverse
	 * @param itop the operation to execute
	 * @throws XException in case that <code>itop</code> throws one
	 */
	public static void traversePreOrder(Document doc, IteratedNodeOperation itop)
			throws XException
	{
		// Start at root
		Element root = null;
		root = doc.getDocumentElement();
		if (root != null)
			// Continue recursively.
			recursivePreOrder(root, itop);
	} // traversePreOrder(Document doc, IteratedNodeOperation itop)

	/**
	 * <code>recursivePreOrder</code> is the recursive heart of DOM tree
	 * pre-order traversal. It executes the specified operation on the specified
	 * node and proceeds to its children. The traversal may treat dynamically
	 * deleted nodes but no extensions.
	 * 
	 * @param node the actual node
	 * @param itop the operation to execute
	 * @throws XException in case that <code>itop</code> throws one
	 */
	private static void recursivePreOrder(Node node, IteratedNodeOperation itop)
			throws XException
	{
		itop.iteratedProcedure(node);
		// Process the actual node ...
		if (node.hasChildNodes())
		{ // ... and then all its children
			NodeList children = node.getChildNodes();
			int childCount = children.getLength();
			// Save it for the case of node deletion.
			for (int i = 0; i < childCount; i++)
				recursivePreOrder(children.item(i - childCount
						+ children.getLength()), itop);
			// with correction for node deletion
		} // if (node.hasChildNodes())
	} // recursivePreOrder(Node node, IteratedNodeOperation itop)

	/**
	 * <code>traversePostOrder</code> traverses a DOM tree in post-order and
	 * executes the specified operation on each node. The traversal may treat
	 * dynamically deleted nodes but no extensions.
	 * 
	 * @param doc the DOM tree to traverse
	 * @param itop the operation to execute
	 * @throws XException in case that <code>itop</code> throws one
	 */
	public static void traversePostOrder(Document doc,
			IteratedNodeOperation itop) throws XException
	{
		// Start at root
		Element root = null;
		root = doc.getDocumentElement();
		if (root != null)
			// Continue recursively.
			recursivePostOrder(root, itop);
	} // traversePostOrder(Document doc, IteratedNodeOperation itop)

	/**
	 * <code>recursivePostOrder</code> is the recursive heart of DOM tree
	 * pre-order traversal. It proceeds on all children of the specified node
	 * and then executes the specified operation on the node itself. The
	 * traversal may treat dynamically deleted nodes but no extensions.
	 * 
	 * @param node the actual node
	 * @param itop the operation to execute
	 * @throws XException in case that <code>itop</code> throws one
	 */
	public static void recursivePostOrder(Node node, IteratedNodeOperation itop)
			throws XException
	{
		if (node.hasChildNodes())
		{ // Process all children of the actual node ...
			NodeList children = node.getChildNodes();
			int childCount = children.getLength();
			// Save it for the case of node deletion.
			for (int i = 0; i < childCount; i++)
				recursivePostOrder(children.item(i - childCount
						+ children.getLength()), itop);
			// with correction for node deletion
		} // if (node.hasChildNodes())
		itop.iteratedProcedure(node);
		// ... and then the actual node itself
	} // recursivePostOrder(Node node, IteratedNodeOperation itop)

	/**
	 * <code>isValidTagName</code> checks if a string conforms to the
	 * conventions for xml tag names.
	 * 
	 * @param name the string to check
	 * @return <code>true</code> if the string is a well-formed tag name,
	 *         otherwise <code>false</code>
	 */
	public static boolean isValidTagName(String name)
	{
		boolean result = (name.length() > 0 && (Character.isLetter(name
				.charAt(0)) || name.charAt(0) == '_'));
		for (int i = 1; i < name.length() && result; i++)
			result = (Character.isLetter(name.charAt(i))
					|| Character.isDigit(name.charAt(i)) || name.charAt(i) == '_');
		return result;
	} // isValidTagName(String name)

	/**
	 * Removes invalid characters from tag names.
	 * 
	 * @param name the name of the tag
	 * @return the name of the tag without invalid characters
	 */
	public static String makeTagNameValid(String name)
	{
		StringBuffer retBuffer = new StringBuffer();

		for (int i = 0; i < name.length(); i++)
		{
			if ((Character.isLetter(name.charAt(i))
					|| Character.isDigit(name.charAt(i)) || name.charAt(i) == '_'))
			{
				retBuffer.append(name.charAt(i));
			}
		}

		return retBuffer.toString();
	}

} // XDomSupport

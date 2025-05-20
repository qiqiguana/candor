package net.sf.xbus.base.xml;

import java.util.LinkedList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>IteratedWhitespaceInElementAndCommentDeletion</code> implements a DOM
 * node operation to delete text nodes containing only whitespace within xml
 * tags. This operation is used in DOM tree traversal operations by
 * instantiating an object of this class.
 * 
 * @author Stephan Düwel
 */
public class IteratedWhitespaceInElementDeletion
		implements
			IteratedNodeOperation
{

	/**
	 * <code>iteratedProcedure</code> deletes all whitespace text nodes
	 * directly below the specified node if it is an element node. It is used in
	 * traversals.
	 * 
	 * @param node the actual node to process
	 */
	public void iteratedProcedure(Node node)
	{
		if (node.getNodeType() == Node.ELEMENT_NODE && node.hasChildNodes())
		{
			NodeList children = node.getChildNodes();

			// First only collect the nodes to delete to not disturb the for
			// loop.
			LinkedList toDelete = new LinkedList();
			for (int i = 0; i < children.getLength(); i++)
			{
				Node child = children.item(i);
				if (child != null && child.getNodeType() == Node.TEXT_NODE
						&& child.getNodeValue().trim().length() == 0)
					toDelete.add(child);
			} // for (int i=0; i<children.getLength(); i++)

			// Now delete the found nodes.
			for (int i = 0; i < toDelete.size(); i++)
				node.removeChild((Node) toDelete.get(i));
		} // if (node.getNodeType()==Node.ELEMENT_NODE &&
			// node.hasChildNodes())
	} // iteratedProcedure(Node node)

} // IteratedWhitespaceInElementDeletion

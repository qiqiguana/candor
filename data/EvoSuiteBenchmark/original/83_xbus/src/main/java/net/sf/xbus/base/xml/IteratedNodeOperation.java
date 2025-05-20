package net.sf.xbus.base.xml;

import net.sf.xbus.base.core.XException;

import org.w3c.dom.Node;

/**
 * <code>IteratedNodeOperation</code> defines the signatures for operations to
 * be executed while traversing a DOM tree. For using a concrete operation, this
 * interface has to be implemented. Then an object can be passed to the
 * traversal operation. Changing the the tree within the operation is dangerous.
 * It may lead into endless loops or incomplete traversals in dynamically
 * modified trees. But the traversal algorithms are built that way that the
 * operation may delete the node it was invoked on.
 * 
 * @author Stephan Düwel
 */
public interface IteratedNodeOperation
{

	/**
	 * <code>iteratedProcedure</code> is the operation to be performed on each
	 * node.
	 * 
	 * @param node the actual node in traversal
	 * @throws Exception just to allow arbitrary implementations.
	 */
	public void iteratedProcedure(Node node) throws XException;

} // IteratedNodeOperation

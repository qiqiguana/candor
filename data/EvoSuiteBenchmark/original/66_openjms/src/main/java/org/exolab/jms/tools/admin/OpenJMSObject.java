/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: OpenJMSObject.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $ *
 */
package org.exolab.jms.tools.admin;

import java.awt.Rectangle;
import java.text.SimpleDateFormat;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


/**
 * This is the base class for all nodes.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         OpenJMSConsumer
 * @see         AdminMgr
 */
public abstract class OpenJMSObject extends DefaultMutableTreeNode
    implements OpenJMSNode {

    // The name
    protected String _name;

    // Does this queue topic have any registered consumers
    protected boolean _isLeaf;

    // Whether this node has been opened and explored already.
    protected boolean _isExplored = false;

    // The popup menu for destination
    protected JPopupMenu _commands = null;

    // A flag indicating if the menu has been created yet.
    protected boolean _commandsCreated = false;

    // A date time formatter.
    static protected SimpleDateFormat _dateFormat;

    //  A reference to the tree this node belongs to.
    static protected JTree _tree = null;


    /**
     * The constructor gets its unique name for this object and a
     * reference to its parent tree.
     *
     * <P>If this is the first  call, the menu for all objects
     * is created.
     *
     * @param name This object name.
     * @param tree The parent tree this object belongs to.
     *
     */
    public OpenJMSObject(String destinationName, JTree tree) {
        _name = destinationName;
        _isLeaf = false;
        if (!_commandsCreated) {
            _tree = tree;
            createCommands();
            _commandsCreated = true;
            _dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            _dateFormat.setLenient(false);
        }
    }


    /**
     * Create the menu for all objects and set up the Action events for
     * each menu item. Since menus are shared, the callbacks called are
     * static. Once a menu is slected, the slected node can be determined
     * from the parent object.
     *
     */
    abstract protected void createCommands();

    /**
     * Children are allowed for all objects
     *
     * @return boolean Always returns true.
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * Objects are leaves iff they have nothing registered against
     * them.
     *
     * @return boolean true if no objects are registered.
     */
    public boolean isLeaf() {
        return _isLeaf;
    }

    /**
     * This node has been right clicked. The locations of this node is given
     * by the loc object. Use this location to popup the object message
     * menu.
     *
     * @param The location of this Consumer node.
     */
    public void displayCommands(Rectangle loc) {
        double x;
        double y;

        x = loc.getX();
        y = loc.getY();
        y += loc.getHeight();

        _commands.show(_tree, (int) x, (int) y);
    }

    /**
     * The unique name of this object.
     *
     * @return String the object name.
     */
    public String toString() {
        return _name;
    }

    /**
     * This node has changed. Inform the parent tree that it needs to be
     * re-drawn.
     */
    protected void refresh() {
        DefaultTreeModel model = (DefaultTreeModel) _tree.getModel();
        model.nodeStructureChanged((DefaultMutableTreeNode) this);
    }

    /**
     * Get the particular instance of the object that has been selected.
     *
     * @return the instance selected.
     */
    static protected OpenJMSObject getInstanceSelected() {
        Object loc = _tree.getLastSelectedPathComponent();
        return (OpenJMSObject) loc;
    }
}

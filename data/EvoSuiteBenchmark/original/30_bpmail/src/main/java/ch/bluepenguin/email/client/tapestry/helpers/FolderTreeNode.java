/*
 * Created on 18.01.2005
 *
 */
package ch.bluepenguin.email.client.tapestry.helpers;

import org.apache.tapestry.contrib.tree.model.IMutableTreeNode;
import org.apache.tapestry.contrib.tree.simple.TreeNode;

import ch.bluepenguin.email.client.Folder;
/**
 * @author Christian
 *
 * Tree Node containing a Folder
 */
public class FolderTreeNode extends TreeNode {

	private Folder strValue;
    /**
     */
    public Folder getFolder() {
        return strValue;
    }
	
    public void setFolder(Folder strValue) {
        this.strValue = strValue;
    }

    public FolderTreeNode( Folder strValue)
	{
		super();
		this.strValue = strValue;
		
	}

	public FolderTreeNode( Folder strValue, IMutableTreeNode parent)
	{
		super(parent);
		this.strValue = strValue;
	}

	/**
	 *  @see org.apache.tapestry.contrib.tree.simple.SimpleNodeRenderFactory
	 *  SimpleNodeRenderFactory.getRender() returns a  RenderString 
	 *  instanciated by object.toString()
	 * 
	 *  If we want anything other then the serialized object displayed
	 *  we have to overwrite toString()
	 */
	public String toString(){
		if(getFolder()==null) return null;
        return getFolder().getName().toString();
    }

    /**
     *  Overwrite hashCode to match getValue().hashCode()
     */
    public int hashCode(){
		if(getFolder()==null) return -1;
        return getFolder().hashCode();
    }

    /**
     *  Overwrite equals to match getValue().equals()
     */
    public boolean equals(Object objTarget){
        if(objTarget == this)
            return true;
        if(! (objTarget instanceof FolderTreeNode))
            return false;
        FolderTreeNode objTargetNode = (FolderTreeNode)objTarget;
        return this.getFolder().equals(objTargetNode.getFolder());
    }

}
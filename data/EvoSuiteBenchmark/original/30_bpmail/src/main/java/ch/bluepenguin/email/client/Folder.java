/*
 * Created Wed Oct 06 07:36:19 GMT+01:00 2004 by MyEclipse Hibernate Tool.
 */
package ch.bluepenguin.email.client;

import java.io.Serializable;

/**
 * A class that represents a row in the 'folder' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Folder 
    implements Serializable
{
	private AbstractUniqueID uniqueID = new AbstractUniqueID();

    /** The value of the simple parentFolderId property. */
    private java.lang.Integer parentFolderId;

    /** The value of the simple name property. */
    private java.lang.String name;
    
    private String fullName;

    /**
	 * @return Returns the uniqueID.
	 */
	public AbstractUniqueID getUniqueID() {
		return uniqueID;
	}

	/**
	 * @return Returns the fullName.
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName The fullName to set.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
    private Folder parent;

	private Account account;
    /**
     * Simple constructor of Folder instances.
     */
    public Folder()
    {
    }




    /**
     * Return the value of the parent_folder_id column.
     * @return java.lang.Integer
     */
    public java.lang.Integer getParentFolderId()
    {
        return this.parentFolderId;
    }

    /**
     * Set the value of the parent_folder_id column.
     * @param parentFolderId
     */
    public void setParentFolderId(java.lang.Integer parentFolderId)
    {
        this.parentFolderId = parentFolderId;
    }

    /**
     * Return the value of the name column.
     * @return java.lang.String
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * Set the value of the name column.
     * @param name
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }


    
    /* Add customized code below */

    public Folder getParent() {
        return parent;
    }
    public void setParent(Folder parent) {
        this.parent = parent;
        setParentFolderId(parent.getUniqueID().getId());
    }

	/**
	 * @param account
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	/**
	 * @param account
	 */
	public Account getAccount() {
		return account;
	}

}

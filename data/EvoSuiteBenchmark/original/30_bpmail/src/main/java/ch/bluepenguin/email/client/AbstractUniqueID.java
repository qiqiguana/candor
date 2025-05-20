/*
 * Created on 09.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.client;

import java.io.Serializable;

/**
 * @author Christian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AbstractUniqueID implements Serializable{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private java.lang.Integer id;
    
    /**
     * Return the simple primary key value that identifies this object.
     * @return java.lang.Integer
     */
    public final java.lang.Integer getId()
    {
        return id;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param id
     */
    public final void setId(java.lang.Integer id)
    {
        this.hashValue = 0;
        this.id = id;
    }
    /**
     * Implementation of the hashCode method conforming to the Bloch pattern with
     * the exception of array properties (these are very unlikely primary key types).
     * @return int
     */
    public int hashCode()
    {
        if (this.hashValue == 0)
        {
            int result = 17;
            int idValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + idValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}

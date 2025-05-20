/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.exolab.jms.tools.db;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * This element defines a table attribute.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class Attribute implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _type
     */
    private java.lang.String _type;

    /**
     * Field _primaryKey
     */
    private boolean _primaryKey;

    /**
     * keeps track of state for field: _primaryKey
     */
    private boolean _has_primaryKey;

    /**
     * Field _unique
     */
    private boolean _unique;

    /**
     * keeps track of state for field: _unique
     */
    private boolean _has_unique;

    /**
     * Field _notNull
     */
    private boolean _notNull;

    /**
     * keeps track of state for field: _notNull
     */
    private boolean _has_notNull;


      //----------------/
     //- Constructors -/
    //----------------/

    public Attribute() {
        super();
    } //-- org.exolab.jms.tools.db.Attribute()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteNotNull
     */
    public void deleteNotNull()
    {
        this._has_notNull= false;
    } //-- void deleteNotNull() 

    /**
     * Method deletePrimaryKey
     */
    public void deletePrimaryKey()
    {
        this._has_primaryKey= false;
    } //-- void deletePrimaryKey() 

    /**
     * Method deleteUnique
     */
    public void deleteUnique()
    {
        this._has_unique= false;
    } //-- void deleteUnique() 

    /**
     * Returns the value of field 'name'.
     * 
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Returns the value of field 'notNull'.
     * 
     * @return the value of field 'notNull'.
     */
    public boolean getNotNull()
    {
        return this._notNull;
    } //-- boolean getNotNull() 

    /**
     * Returns the value of field 'primaryKey'.
     * 
     * @return the value of field 'primaryKey'.
     */
    public boolean getPrimaryKey()
    {
        return this._primaryKey;
    } //-- boolean getPrimaryKey() 

    /**
     * Returns the value of field 'type'.
     * 
     * @return the value of field 'type'.
     */
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

    /**
     * Returns the value of field 'unique'.
     * 
     * @return the value of field 'unique'.
     */
    public boolean getUnique()
    {
        return this._unique;
    } //-- boolean getUnique() 

    /**
     * Method hasNotNull
     */
    public boolean hasNotNull()
    {
        return this._has_notNull;
    } //-- boolean hasNotNull() 

    /**
     * Method hasPrimaryKey
     */
    public boolean hasPrimaryKey()
    {
        return this._has_primaryKey;
    } //-- boolean hasPrimaryKey() 

    /**
     * Method hasUnique
     */
    public boolean hasUnique()
    {
        return this._has_unique;
    } //-- boolean hasUnique() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

    /**
     * Sets the value of field 'notNull'.
     * 
     * @param notNull the value of field 'notNull'.
     */
    public void setNotNull(boolean notNull)
    {
        this._notNull = notNull;
        this._has_notNull = true;
    } //-- void setNotNull(boolean) 

    /**
     * Sets the value of field 'primaryKey'.
     * 
     * @param primaryKey the value of field 'primaryKey'.
     */
    public void setPrimaryKey(boolean primaryKey)
    {
        this._primaryKey = primaryKey;
        this._has_primaryKey = true;
    } //-- void setPrimaryKey(boolean) 

    /**
     * Sets the value of field 'type'.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(java.lang.String type)
    {
        this._type = type;
    } //-- void setType(java.lang.String) 

    /**
     * Sets the value of field 'unique'.
     * 
     * @param unique the value of field 'unique'.
     */
    public void setUnique(boolean unique)
    {
        this._unique = unique;
        this._has_unique = true;
    } //-- void setUnique(boolean) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.tools.db.Attribute unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.tools.db.Attribute) Unmarshaller.unmarshal(org.exolab.jms.tools.db.Attribute.class, reader);
    } //-- org.exolab.jms.tools.db.Attribute unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}

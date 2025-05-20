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
import java.util.ArrayList;
import java.util.Enumeration;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * This element defines a table
 *  
 * 
 * @version $Revision$ $Date$
 */
public class Table implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * This element defines a table attribute.
     *  
     */
    private java.util.ArrayList _attributeList;

    /**
     * This element specifies a primary key on a table
     *  
     */
    private org.exolab.jms.tools.db.PrimaryKey _primaryKey;

    /**
     * This element specifies an index on a table
     *  
     */
    private java.util.ArrayList _indexList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Table() {
        super();
        _attributeList = new ArrayList();
        _indexList = new ArrayList();
    } //-- org.exolab.jms.tools.db.Table()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAttribute
     * 
     * @param vAttribute
     */
    public void addAttribute(org.exolab.jms.tools.db.Attribute vAttribute)
        throws java.lang.IndexOutOfBoundsException
    {
        _attributeList.add(vAttribute);
    } //-- void addAttribute(org.exolab.jms.tools.db.Attribute) 

    /**
     * Method addAttribute
     * 
     * @param index
     * @param vAttribute
     */
    public void addAttribute(int index, org.exolab.jms.tools.db.Attribute vAttribute)
        throws java.lang.IndexOutOfBoundsException
    {
        _attributeList.add(index, vAttribute);
    } //-- void addAttribute(int, org.exolab.jms.tools.db.Attribute) 

    /**
     * Method addIndex
     * 
     * @param vIndex
     */
    public void addIndex(org.exolab.jms.tools.db.Index vIndex)
        throws java.lang.IndexOutOfBoundsException
    {
        _indexList.add(vIndex);
    } //-- void addIndex(org.exolab.jms.tools.db.Index) 

    /**
     * Method addIndex
     * 
     * @param index
     * @param vIndex
     */
    public void addIndex(int index, org.exolab.jms.tools.db.Index vIndex)
        throws java.lang.IndexOutOfBoundsException
    {
        _indexList.add(index, vIndex);
    } //-- void addIndex(int, org.exolab.jms.tools.db.Index) 

    /**
     * Method clearAttribute
     */
    public void clearAttribute()
    {
        _attributeList.clear();
    } //-- void clearAttribute() 

    /**
     * Method clearIndex
     */
    public void clearIndex()
    {
        _indexList.clear();
    } //-- void clearIndex() 

    /**
     * Method enumerateAttribute
     */
    public java.util.Enumeration enumerateAttribute()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_attributeList.iterator());
    } //-- java.util.Enumeration enumerateAttribute() 

    /**
     * Method enumerateIndex
     */
    public java.util.Enumeration enumerateIndex()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_indexList.iterator());
    } //-- java.util.Enumeration enumerateIndex() 

    /**
     * Method getAttribute
     * 
     * @param index
     */
    public org.exolab.jms.tools.db.Attribute getAttribute(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _attributeList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (org.exolab.jms.tools.db.Attribute) _attributeList.get(index);
    } //-- org.exolab.jms.tools.db.Attribute getAttribute(int) 

    /**
     * Method getAttribute
     */
    public org.exolab.jms.tools.db.Attribute[] getAttribute()
    {
        int size = _attributeList.size();
        org.exolab.jms.tools.db.Attribute[] mArray = new org.exolab.jms.tools.db.Attribute[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.exolab.jms.tools.db.Attribute) _attributeList.get(index);
        }
        return mArray;
    } //-- org.exolab.jms.tools.db.Attribute[] getAttribute() 

    /**
     * Method getAttributeCount
     */
    public int getAttributeCount()
    {
        return _attributeList.size();
    } //-- int getAttributeCount() 

    /**
     * Method getIndex
     * 
     * @param index
     */
    public org.exolab.jms.tools.db.Index getIndex(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _indexList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (org.exolab.jms.tools.db.Index) _indexList.get(index);
    } //-- org.exolab.jms.tools.db.Index getIndex(int) 

    /**
     * Method getIndex
     */
    public org.exolab.jms.tools.db.Index[] getIndex()
    {
        int size = _indexList.size();
        org.exolab.jms.tools.db.Index[] mArray = new org.exolab.jms.tools.db.Index[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.exolab.jms.tools.db.Index) _indexList.get(index);
        }
        return mArray;
    } //-- org.exolab.jms.tools.db.Index[] getIndex() 

    /**
     * Method getIndexCount
     */
    public int getIndexCount()
    {
        return _indexList.size();
    } //-- int getIndexCount() 

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
     * Returns the value of field 'primaryKey'. The field
     * 'primaryKey' has the following description: This element
     * specifies a primary key on a table
     *  
     * 
     * @return the value of field 'primaryKey'.
     */
    public org.exolab.jms.tools.db.PrimaryKey getPrimaryKey()
    {
        return this._primaryKey;
    } //-- org.exolab.jms.tools.db.PrimaryKey getPrimaryKey() 

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
     * Method removeAttribute
     * 
     * @param vAttribute
     */
    public boolean removeAttribute(org.exolab.jms.tools.db.Attribute vAttribute)
    {
        boolean removed = _attributeList.remove(vAttribute);
        return removed;
    } //-- boolean removeAttribute(org.exolab.jms.tools.db.Attribute) 

    /**
     * Method removeIndex
     * 
     * @param vIndex
     */
    public boolean removeIndex(org.exolab.jms.tools.db.Index vIndex)
    {
        boolean removed = _indexList.remove(vIndex);
        return removed;
    } //-- boolean removeIndex(org.exolab.jms.tools.db.Index) 

    /**
     * Method setAttribute
     * 
     * @param index
     * @param vAttribute
     */
    public void setAttribute(int index, org.exolab.jms.tools.db.Attribute vAttribute)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _attributeList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _attributeList.set(index, vAttribute);
    } //-- void setAttribute(int, org.exolab.jms.tools.db.Attribute) 

    /**
     * Method setAttribute
     * 
     * @param attributeArray
     */
    public void setAttribute(org.exolab.jms.tools.db.Attribute[] attributeArray)
    {
        //-- copy array
        _attributeList.clear();
        for (int i = 0; i < attributeArray.length; i++) {
            _attributeList.add(attributeArray[i]);
        }
    } //-- void setAttribute(org.exolab.jms.tools.db.Attribute) 

    /**
     * Method setIndex
     * 
     * @param index
     * @param vIndex
     */
    public void setIndex(int index, org.exolab.jms.tools.db.Index vIndex)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _indexList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _indexList.set(index, vIndex);
    } //-- void setIndex(int, org.exolab.jms.tools.db.Index) 

    /**
     * Method setIndex
     * 
     * @param indexArray
     */
    public void setIndex(org.exolab.jms.tools.db.Index[] indexArray)
    {
        //-- copy array
        _indexList.clear();
        for (int i = 0; i < indexArray.length; i++) {
            _indexList.add(indexArray[i]);
        }
    } //-- void setIndex(org.exolab.jms.tools.db.Index) 

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
     * Sets the value of field 'primaryKey'. The field 'primaryKey'
     * has the following description: This element specifies a
     * primary key on a table
     *  
     * 
     * @param primaryKey the value of field 'primaryKey'.
     */
    public void setPrimaryKey(org.exolab.jms.tools.db.PrimaryKey primaryKey)
    {
        this._primaryKey = primaryKey;
    } //-- void setPrimaryKey(org.exolab.jms.tools.db.PrimaryKey) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.tools.db.Table unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.tools.db.Table) Unmarshaller.unmarshal(org.exolab.jms.tools.db.Table.class, reader);
    } //-- org.exolab.jms.tools.db.Table unmarshal(java.io.Reader) 

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

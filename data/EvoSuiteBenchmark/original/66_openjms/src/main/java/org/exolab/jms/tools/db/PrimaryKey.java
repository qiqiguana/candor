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
 * This element specifies a primary key on a table
 *  
 * 
 * @version $Revision$ $Date$
 */
public class PrimaryKey implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This element defines a column in an index
     *  
     */
    private java.util.ArrayList _columnList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PrimaryKey() {
        super();
        _columnList = new ArrayList();
    } //-- org.exolab.jms.tools.db.PrimaryKey()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addColumn
     * 
     * @param vColumn
     */
    public void addColumn(org.exolab.jms.tools.db.Column vColumn)
        throws java.lang.IndexOutOfBoundsException
    {
        _columnList.add(vColumn);
    } //-- void addColumn(org.exolab.jms.tools.db.Column) 

    /**
     * Method addColumn
     * 
     * @param index
     * @param vColumn
     */
    public void addColumn(int index, org.exolab.jms.tools.db.Column vColumn)
        throws java.lang.IndexOutOfBoundsException
    {
        _columnList.add(index, vColumn);
    } //-- void addColumn(int, org.exolab.jms.tools.db.Column) 

    /**
     * Method clearColumn
     */
    public void clearColumn()
    {
        _columnList.clear();
    } //-- void clearColumn() 

    /**
     * Method enumerateColumn
     */
    public java.util.Enumeration enumerateColumn()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_columnList.iterator());
    } //-- java.util.Enumeration enumerateColumn() 

    /**
     * Method getColumn
     * 
     * @param index
     */
    public org.exolab.jms.tools.db.Column getColumn(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _columnList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (org.exolab.jms.tools.db.Column) _columnList.get(index);
    } //-- org.exolab.jms.tools.db.Column getColumn(int) 

    /**
     * Method getColumn
     */
    public org.exolab.jms.tools.db.Column[] getColumn()
    {
        int size = _columnList.size();
        org.exolab.jms.tools.db.Column[] mArray = new org.exolab.jms.tools.db.Column[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.exolab.jms.tools.db.Column) _columnList.get(index);
        }
        return mArray;
    } //-- org.exolab.jms.tools.db.Column[] getColumn() 

    /**
     * Method getColumnCount
     */
    public int getColumnCount()
    {
        return _columnList.size();
    } //-- int getColumnCount() 

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
     * Method removeColumn
     * 
     * @param vColumn
     */
    public boolean removeColumn(org.exolab.jms.tools.db.Column vColumn)
    {
        boolean removed = _columnList.remove(vColumn);
        return removed;
    } //-- boolean removeColumn(org.exolab.jms.tools.db.Column) 

    /**
     * Method setColumn
     * 
     * @param index
     * @param vColumn
     */
    public void setColumn(int index, org.exolab.jms.tools.db.Column vColumn)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _columnList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _columnList.set(index, vColumn);
    } //-- void setColumn(int, org.exolab.jms.tools.db.Column) 

    /**
     * Method setColumn
     * 
     * @param columnArray
     */
    public void setColumn(org.exolab.jms.tools.db.Column[] columnArray)
    {
        //-- copy array
        _columnList.clear();
        for (int i = 0; i < columnArray.length; i++) {
            _columnList.add(columnArray[i]);
        }
    } //-- void setColumn(org.exolab.jms.tools.db.Column) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.tools.db.PrimaryKey unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.tools.db.PrimaryKey) Unmarshaller.unmarshal(org.exolab.jms.tools.db.PrimaryKey.class, reader);
    } //-- org.exolab.jms.tools.db.PrimaryKey unmarshal(java.io.Reader) 

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

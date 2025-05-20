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
 * This element defines a database. It is the root element of all
 * database
 *  schema documents.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class Database implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This element defines a table
     *  
     */
    private java.util.ArrayList _tableList;

    /**
     * This element specifies a table that is no longer required.
     *  This is a temporary workaround until a schema migration
     * tool is 
     *  developed
     *  
     */
    private java.util.ArrayList _deprecatedList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Database() {
        super();
        _tableList = new ArrayList();
        _deprecatedList = new ArrayList();
    } //-- org.exolab.jms.tools.db.Database()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDeprecated
     * 
     * @param vDeprecated
     */
    public void addDeprecated(org.exolab.jms.tools.db.Deprecated vDeprecated)
        throws java.lang.IndexOutOfBoundsException
    {
        _deprecatedList.add(vDeprecated);
    } //-- void addDeprecated(org.exolab.jms.tools.db.Deprecated) 

    /**
     * Method addDeprecated
     * 
     * @param index
     * @param vDeprecated
     */
    public void addDeprecated(int index, org.exolab.jms.tools.db.Deprecated vDeprecated)
        throws java.lang.IndexOutOfBoundsException
    {
        _deprecatedList.add(index, vDeprecated);
    } //-- void addDeprecated(int, org.exolab.jms.tools.db.Deprecated) 

    /**
     * Method addTable
     * 
     * @param vTable
     */
    public void addTable(org.exolab.jms.tools.db.Table vTable)
        throws java.lang.IndexOutOfBoundsException
    {
        _tableList.add(vTable);
    } //-- void addTable(org.exolab.jms.tools.db.Table) 

    /**
     * Method addTable
     * 
     * @param index
     * @param vTable
     */
    public void addTable(int index, org.exolab.jms.tools.db.Table vTable)
        throws java.lang.IndexOutOfBoundsException
    {
        _tableList.add(index, vTable);
    } //-- void addTable(int, org.exolab.jms.tools.db.Table) 

    /**
     * Method clearDeprecated
     */
    public void clearDeprecated()
    {
        _deprecatedList.clear();
    } //-- void clearDeprecated() 

    /**
     * Method clearTable
     */
    public void clearTable()
    {
        _tableList.clear();
    } //-- void clearTable() 

    /**
     * Method enumerateDeprecated
     */
    public java.util.Enumeration enumerateDeprecated()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_deprecatedList.iterator());
    } //-- java.util.Enumeration enumerateDeprecated() 

    /**
     * Method enumerateTable
     */
    public java.util.Enumeration enumerateTable()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_tableList.iterator());
    } //-- java.util.Enumeration enumerateTable() 

    /**
     * Method getDeprecated
     * 
     * @param index
     */
    public org.exolab.jms.tools.db.Deprecated getDeprecated(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _deprecatedList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (org.exolab.jms.tools.db.Deprecated) _deprecatedList.get(index);
    } //-- org.exolab.jms.tools.db.Deprecated getDeprecated(int) 

    /**
     * Method getDeprecated
     */
    public org.exolab.jms.tools.db.Deprecated[] getDeprecated()
    {
        int size = _deprecatedList.size();
        org.exolab.jms.tools.db.Deprecated[] mArray = new org.exolab.jms.tools.db.Deprecated[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.exolab.jms.tools.db.Deprecated) _deprecatedList.get(index);
        }
        return mArray;
    } //-- org.exolab.jms.tools.db.Deprecated[] getDeprecated() 

    /**
     * Method getDeprecatedCount
     */
    public int getDeprecatedCount()
    {
        return _deprecatedList.size();
    } //-- int getDeprecatedCount() 

    /**
     * Method getTable
     * 
     * @param index
     */
    public org.exolab.jms.tools.db.Table getTable(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _tableList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (org.exolab.jms.tools.db.Table) _tableList.get(index);
    } //-- org.exolab.jms.tools.db.Table getTable(int) 

    /**
     * Method getTable
     */
    public org.exolab.jms.tools.db.Table[] getTable()
    {
        int size = _tableList.size();
        org.exolab.jms.tools.db.Table[] mArray = new org.exolab.jms.tools.db.Table[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.exolab.jms.tools.db.Table) _tableList.get(index);
        }
        return mArray;
    } //-- org.exolab.jms.tools.db.Table[] getTable() 

    /**
     * Method getTableCount
     */
    public int getTableCount()
    {
        return _tableList.size();
    } //-- int getTableCount() 

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
     * Method removeDeprecated
     * 
     * @param vDeprecated
     */
    public boolean removeDeprecated(org.exolab.jms.tools.db.Deprecated vDeprecated)
    {
        boolean removed = _deprecatedList.remove(vDeprecated);
        return removed;
    } //-- boolean removeDeprecated(org.exolab.jms.tools.db.Deprecated) 

    /**
     * Method removeTable
     * 
     * @param vTable
     */
    public boolean removeTable(org.exolab.jms.tools.db.Table vTable)
    {
        boolean removed = _tableList.remove(vTable);
        return removed;
    } //-- boolean removeTable(org.exolab.jms.tools.db.Table) 

    /**
     * Method setDeprecated
     * 
     * @param index
     * @param vDeprecated
     */
    public void setDeprecated(int index, org.exolab.jms.tools.db.Deprecated vDeprecated)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _deprecatedList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _deprecatedList.set(index, vDeprecated);
    } //-- void setDeprecated(int, org.exolab.jms.tools.db.Deprecated) 

    /**
     * Method setDeprecated
     * 
     * @param deprecatedArray
     */
    public void setDeprecated(org.exolab.jms.tools.db.Deprecated[] deprecatedArray)
    {
        //-- copy array
        _deprecatedList.clear();
        for (int i = 0; i < deprecatedArray.length; i++) {
            _deprecatedList.add(deprecatedArray[i]);
        }
    } //-- void setDeprecated(org.exolab.jms.tools.db.Deprecated) 

    /**
     * Method setTable
     * 
     * @param index
     * @param vTable
     */
    public void setTable(int index, org.exolab.jms.tools.db.Table vTable)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _tableList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _tableList.set(index, vTable);
    } //-- void setTable(int, org.exolab.jms.tools.db.Table) 

    /**
     * Method setTable
     * 
     * @param tableArray
     */
    public void setTable(org.exolab.jms.tools.db.Table[] tableArray)
    {
        //-- copy array
        _tableList.clear();
        for (int i = 0; i < tableArray.length; i++) {
            _tableList.add(tableArray[i]);
        }
    } //-- void setTable(org.exolab.jms.tools.db.Table) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.tools.db.Database unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.tools.db.Database) Unmarshaller.unmarshal(org.exolab.jms.tools.db.Database.class, reader);
    } //-- org.exolab.jms.tools.db.Database unmarshal(java.io.Reader) 

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

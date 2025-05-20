/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.exolab.jms.config;

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
 * This element is used to bootstrap the creation of users.
 *  
 * 
 * @version $Revision$ $Date$
 */
public class Users implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * This element defines an User.
     *  
     */
    private java.util.ArrayList _userList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Users() {
        super();
        _userList = new ArrayList();
    } //-- org.exolab.jms.config.Users()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addUser
     * 
     * @param vUser
     */
    public void addUser(User vUser)
        throws java.lang.IndexOutOfBoundsException
    {
        _userList.add(vUser);
    } //-- void addUser(User) 

    /**
     * Method addUser
     * 
     * @param index
     * @param vUser
     */
    public void addUser(int index, User vUser)
        throws java.lang.IndexOutOfBoundsException
    {
        _userList.add(index, vUser);
    } //-- void addUser(int, User) 

    /**
     * Method clearUser
     */
    public void clearUser()
    {
        _userList.clear();
    } //-- void clearUser() 

    /**
     * Method enumerateUser
     */
    public java.util.Enumeration enumerateUser()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_userList.iterator());
    } //-- java.util.Enumeration enumerateUser() 

    /**
     * Method getUser
     * 
     * @param index
     */
    public User getUser(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _userList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (User) _userList.get(index);
    } //-- User getUser(int) 

    /**
     * Method getUser
     */
    public User[] getUser()
    {
        int size = _userList.size();
        User[] mArray = new User[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (User) _userList.get(index);
        }
        return mArray;
    } //-- User[] getUser() 

    /**
     * Method getUserCount
     */
    public int getUserCount()
    {
        return _userList.size();
    } //-- int getUserCount() 

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
     * Method removeUser
     * 
     * @param vUser
     */
    public boolean removeUser(User vUser)
    {
        boolean removed = _userList.remove(vUser);
        return removed;
    } //-- boolean removeUser(User) 

    /**
     * Method setUser
     * 
     * @param index
     * @param vUser
     */
    public void setUser(int index, User vUser)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _userList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _userList.set(index, vUser);
    } //-- void setUser(int, User) 

    /**
     * Method setUser
     * 
     * @param userArray
     */
    public void setUser(User[] userArray)
    {
        //-- copy array
        _userList.clear();
        for (int i = 0; i < userArray.length; i++) {
            _userList.add(userArray[i]);
        }
    } //-- void setUser(User) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static org.exolab.jms.config.Users unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.exolab.jms.config.Users) Unmarshaller.unmarshal(org.exolab.jms.config.Users.class, reader);
    } //-- org.exolab.jms.config.Users unmarshal(java.io.Reader) 

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

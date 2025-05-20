/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.exolab.jms.config.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class SchemeType.
 * 
 * @version $Revision$ $Date$
 */
public class SchemeType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The tcp type
     */
    public static final int TCP_TYPE = 0;

    /**
     * The instance of the tcp type
     */
    public static final SchemeType TCP = new SchemeType(TCP_TYPE, "tcp");

    /**
     * The tcps type
     */
    public static final int TCPS_TYPE = 1;

    /**
     * The instance of the tcps type
     */
    public static final SchemeType TCPS = new SchemeType(TCPS_TYPE, "tcps");

    /**
     * The http type
     */
    public static final int HTTP_TYPE = 2;

    /**
     * The instance of the http type
     */
    public static final SchemeType HTTP = new SchemeType(HTTP_TYPE, "http");

    /**
     * The https type
     */
    public static final int HTTPS_TYPE = 3;

    /**
     * The instance of the https type
     */
    public static final SchemeType HTTPS = new SchemeType(HTTPS_TYPE, "https");

    /**
     * The rmi type
     */
    public static final int RMI_TYPE = 4;

    /**
     * The instance of the rmi type
     */
    public static final SchemeType RMI = new SchemeType(RMI_TYPE, "rmi");

    /**
     * The embedded type
     */
    public static final int EMBEDDED_TYPE = 5;

    /**
     * The instance of the embedded type
     */
    public static final SchemeType EMBEDDED = new SchemeType(EMBEDDED_TYPE, "embedded");

    /**
     * Field _memberTable
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type
     */
    private int type = -1;

    /**
     * Field stringValue
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private SchemeType(int type, java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- org.exolab.jms.config.types.SchemeType(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerateReturns an enumeration of all possible
     * instances of SchemeType
     */
    public static java.util.Enumeration enumerate()
    {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate() 

    /**
     * Method getTypeReturns the type of this SchemeType
     */
    public int getType()
    {
        return this.type;
    } //-- int getType() 

    /**
     * Method init
     */
    private static java.util.Hashtable init()
    {
        Hashtable members = new Hashtable();
        members.put("tcp", TCP);
        members.put("tcps", TCPS);
        members.put("http", HTTP);
        members.put("https", HTTPS);
        members.put("rmi", RMI);
        members.put("embedded", EMBEDDED);
        return members;
    } //-- java.util.Hashtable init() 

    /**
     * Method toStringReturns the String representation of this
     * SchemeType
     */
    public java.lang.String toString()
    {
        return this.stringValue;
    } //-- java.lang.String toString() 

    /**
     * Method valueOfReturns a new SchemeType based on the given
     * String value.
     * 
     * @param string
     */
    public static org.exolab.jms.config.types.SchemeType valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid SchemeType";
            throw new IllegalArgumentException(err);
        }
        return (SchemeType) obj;
    } //-- org.exolab.jms.config.types.SchemeType valueOf(java.lang.String) 

}

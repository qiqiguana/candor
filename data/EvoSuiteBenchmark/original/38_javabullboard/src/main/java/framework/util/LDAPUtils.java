package framework.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;


/**
 * LDAP utility class
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:51 $
 */
public class LDAPUtils 
{

  // Logger
  private static Log log = LogFactory.getLog(FileUtils.class);

  private static String ldapurl = "ldap://localhost:389";
  private static Hashtable env = new Hashtable();
  static
  {
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, ldapurl);
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, "cn=manager");
    env.put(Context.SECURITY_CREDENTIALS, "pass");
  }

  /**
   * Protected constructor
   */
  protected LDAPUtils()
  {
  }
  
  public static Map getEnvironement() 
  {
    return env;
  }
/*
  public static void addEntry()
  throws Exception
  {
    // Create attributes to be associated with the new entry
    Attributes attrs = new BasicAttributes(true); // case-ignore
    Attribute objclass = new BasicAttribute("objectclass");
    objclass.add("top");
    objclass.add("extensibleObject");
    attrs.put(objclass);
    
    // Create the context
    Context entry = ctx.createSubcontext("cn=Sample", attrs);
  }
*/
////////////////////////////////////////////////////////////////////////////////  
  public static Map getAttributes(String path) 
  throws Exception
  {
    return getAttributes(path, ",");
  }
  
  public static Map getAttributes(String path, String valueDelimiter) 
  throws Exception
  {
    // Create initial context
    DirContext ctx = new InitialDirContext(env);

    Attributes answer = ctx.getAttributes(path);
    if (log.isDebugEnabled()) dumpAttributes(answer);

    Map result = new HashMap();

    // Each attribute
    for (NamingEnumeration ae = answer.getAll(); ae.hasMore();) 
    {
      Attribute attr = (Attribute)ae.next();
      String name = attr.getID();

      // Each value
      String values = "";
      for (NamingEnumeration e = attr.getAll(); e.hasMore(); )
      {
        values += e.next();
        if (e.hasMore()) values += valueDelimiter;
      }
      result.put(name, values);      
    }

    // Close the context when we're done
    ctx.close();

    return result;
  }

  /**
   * <br>
   * Log LDAP attributes
   *
   * @param attributes LDAP attributes
   * @exception Exception 
   */
  public static void dumpAttributes(Attributes attributes) 
  throws Exception
  {
    if (attributes==null) log.debug("dumpAttributes: No attributes");
    else
    {
      // Print each attribute
      for (NamingEnumeration ae = attributes.getAll(); ae.hasMore();) 
      {
        Attribute attr = (Attribute)ae.next();
        log.debug("dumpAttributes: attribute=" + attr.getID());
        // print each value
        for (NamingEnumeration e = attr.getAll(); e.hasMore(); log.debug("dumpAttributes: value=" + e.next()));
      }
    }
  }

}

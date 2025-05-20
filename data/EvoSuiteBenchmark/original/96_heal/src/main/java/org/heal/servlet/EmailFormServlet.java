package org.heal.servlet;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Servlet that emails form data that it recieves.
 */
public class EmailFormServlet extends HttpServlet
{  
    private Hashtable configurations;
    private String separator = "\n";

    /**
     * Special key in the Hashtable used to determine whether or not to reload
     * the configuration file.
     */
    private static final String LAST_MODIFIED = "lastModified";

    public void init(ServletConfig servletconfig) throws ServletException 
    {
        super.init(servletconfig);
        configurations = new Hashtable();
        separator = System.getProperty("line.separator");
    }

    /**
     * Gives a full HTML error page for when there's a configuration
     * problem that prevents this servlet from behaving as expected.
     *
     * @param errorMessage A message indicating the specific problem
     *      which caused this error.
     * @return A String containing HTML to display on an error page.
     */
  
    private String errorMessage(String errorMessage) 
    {
        StringBuffer ret = new StringBuffer();
        ret.append("<html>" + separator);
        ret.append("  <body>" + separator);
        ret.append("    <b>Server configuration error</b>: <em>");
        ret.append(errorMessage);
        ret.append("</em>" + separator);
        ret.append("    <hr>" + separator);
        ret.append("Please contact <a href=\"mailto:info@healcentral.org\">info@healcentral.org</a> with this error."
                +separator);
        ret.append("  </body>" + separator);
        ret.append("</html>" + separator);
        return ret.toString();
    }

    /**
     * Reads a properties file specified by the filename and dumps the
     * keys/values into a <code>Hashtable</code>.  If the file cannot be
     * found or read then a <code>Hashtable</code> with only the key/value
     * of <code>{ "lastModified" : "0" }</code> is returned.
     *
     * @param filename The file name/location of the properties file that
     *      specifies the configuration for this servlet.
     * @return A <code>Hashtable</code> representing the configuration
     *      options specified in the properties file.
     */
    private Hashtable readConfiguration(String filename) 
    {        
        Hashtable ret = new Hashtable();
        Properties props = new Properties();
        try 
        {
            FileInputStream file = new FileInputStream(filename);
            props.load(file);
            file.close();
        } catch(Exception e) { }

        Enumeration enumeration = props.propertyNames();
        while(enumeration.hasMoreElements())
        {
            String key = (String)enumeration.nextElement();
            ret.put(key, props.getProperty(key));
        }

        try 
        {
            File configFile = new File(filename);
            ret.put(LAST_MODIFIED, String.valueOf(configFile.lastModified()));
        } 
        catch(Exception e) 
        {
            ret.put(LAST_MODIFIED, String.valueOf(0L));
        }

        return ret;
    }

    private Hashtable getConfiguration(String filename) 
    {
        Hashtable ret = (Hashtable)configurations.get(filename);
        if(null == ret) 
        {
            ret = readConfiguration(filename);
            configurations.put(filename, ret);
        } 
        else 
        {
            final String lastModified = (String)ret.get(LAST_MODIFIED);
            File configFile;
            try 
            {
                configFile = new File(filename);

                // If the configuration file exists, and has a different lastModified
                // stamp, then we reload the config file
                if(!lastModified.equals(String.valueOf(configFile.lastModified()))) 
                {
                    configurations.remove(filename);
                    ret = readConfiguration(filename);
                    configurations.put(filename, ret);
                }
            } 
            catch(Exception e) { }
        }
        return ret;
    }

    /**
     * Sends an email according to the parameters indicated
     *
     * @param serverConfig A <code>Hashtable</code> representing the configuration
     *      options specified by the server.
     * @param formVariables A <code>Hashtable</code> representing the variables
     *      submitted to the servlet.
     * @return <code>true</code> if an email was sent, otherwise <code>false</code>.
     */
    private boolean sendEmail(final Hashtable serverConfig, final Hashtable formVariables) 
    {
        String smtpServer = (String)serverConfig.get("smtpServer");

        String to = (String)formVariables.get("to");
        String from = (String)formVariables.get("from");
        String subject = (String)formVariables.get("subject");
        String body = (String)formVariables.get("body");

        if(null == to) 
        {
            return false; // This field is required
        }
        if(null == from) 
        {
            from = "info@healcentral.org";
        }
        if(null == subject) 
        {
            subject = "HEALCENTRAL.ORG mailer";
        }
        if(null == body) 
        {
            body = "";
        }

        StringBuffer extra = new StringBuffer();
        Enumeration enumeration = formVariables.keys();
        while(enumeration.hasMoreElements())
        {
            String formVariableName = (String)enumeration.nextElement();
            if(formVariableName.equals("to") || formVariableName.equals("from")
                || formVariableName.equals("subject") || formVariableName.equals("body")) 
            {
                continue;
            }
            extra.append(formVariableName+ " = " +formVariables.get(formVariableName)+ "\n");
        }
        try 
        {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpServer);

            Session session = Session.getDefaultInstance(props, null);

            // Constructs the email from the form variables
            StringBuffer msg = new StringBuffer();
            msg.append(body);
            if(extra.length() > 0) {
                msg.append("\n");
                msg.append("-------\n");
                msg.append(extra.toString());
            }
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(msg.toString());

            Transport.send(message);
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }   

    /**
     * Parses the form veriables passed to this servlet into a
     * <code>Hashtable</code>.
     *
     * @param req
     * @return A <code>Hashtable</code> containing all the form variables
     *      passed to the servlet.
     */
    private Hashtable getFormVariables(HttpServletRequest req) 
    {
        Hashtable ret = new Hashtable();

        Enumeration enumeration = req.getParameterNames();
        while(enumeration.hasMoreElements())
        {
            String variableName = (String)enumeration.nextElement();
            String variableValue = req.getParameterValues(variableName)[0];
            ret.put(variableName, variableValue);
        }
        return ret;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
    {
      //  String s = req.getQueryString();
      // using Parameter instead of QueryString
        String s = req.getParameter("filename");   
       
        if (null == s || s.length() == 0) 
        {
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.print(errorMessage("Configuration file not specified"));
            out.close();
        }

        Hashtable serverConfig = getConfiguration(s);
        Hashtable formVariables = getFormVariables(req);
        boolean emailSent = sendEmail(serverConfig, formVariables);
        saveXml(serverConfig, formVariables);
        String forward;
        if(false == emailSent) 
        {
            forward = (String)serverConfig.get("forwardFailure");
        } 
        else 
        {
            forward = (String)serverConfig.get("forwardSuccess");
        }

        forward(forward, req, res);
    }

    /**
     * Saves the form data to an xml file specified by the server configuration.
     *
     * @param serverConfig
     * @param formVariables
     * @throws IOException
     */
    private synchronized void saveXml(Hashtable serverConfig, Hashtable formVariables) throws IOException 
    {
        String xmlFilename = (String) serverConfig.get("xml");
        String dtd = (String) serverConfig.get("dtd");
        boolean fileExists = false;
        File file = new File(xmlFilename);
        System.out.print("filename"+file);
        if (file.exists()) 
        {
            fileExists = true;
        }
        RandomAccessFile xmlFile = new RandomAccessFile(xmlFilename, "rw");
        xmlFile.seek(xmlFile.length());
        if(!fileExists) 
        {
            xmlFile.writeBytes("<?xml version=\"1.0\"?>" + separator);
            if (dtd == null) 
            {
                Enumeration enumeration = formVariables.keys();
                xmlFile.writeBytes("<!DOCTYPE records" + separator);
                xmlFile.writeBytes("[" + separator);
                xmlFile.writeBytes("<!ELEMENT records (record)+>" + separator);
                xmlFile.writeBytes("<!ELEMENT record (");
                StringBuffer fieldNameList = new StringBuffer();
                StringBuffer elementList = new StringBuffer();
                while(enumeration.hasMoreElements())
                {
                    String fieldName = (String)enumeration.nextElement();

                    if(fieldNameList.length() > 0) 
                    {
                        fieldNameList.append("," + fieldName);
                    } 
                    else 
                    {
                        fieldNameList.append(fieldName);
                    }
                    elementList.append("<!ELEMENT " + fieldName + " (#PCDATA)>" + separator);
                }

                xmlFile.writeBytes(fieldNameList.toString() + ")>" + separator);
                xmlFile.writeBytes(elementList.toString());
                xmlFile.writeBytes("]>" + separator);
            } 
            else 
            {
                xmlFile.writeBytes("<!DOCTYPE records SYSTEM \"" + dtd + "\">" + separator);
            }
            xmlFile.writeBytes("<records>" + separator);
        } 
        else 
        {
            xmlFile.seek(xmlFile.length() - (long) "records".length() - (long) separator.length() - 3L);
        }
        xmlFile.writeBytes("<record>" + separator);
        Enumeration enumeration = formVariables.keys();
        while(enumeration.hasMoreElements())
        {
            String fieldName = (String)enumeration.nextElement();
            String fieldValue = (String)formVariables.get(fieldName);
            xmlFile.writeBytes("<" +fieldName+ ">" +fieldValue+ "</" +fieldName+ ">" +separator);
        }
        xmlFile.writeBytes("</record>" + separator);
        xmlFile.writeBytes("</records>" + separator);
        xmlFile.close();
    }
    /**
     * forwards a request to the given relative url.
     */
    private void forward(String url,HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException 
    {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
  
}

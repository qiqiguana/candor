/*
 * Created on Dec 13, 2004
 */
package org.heal.module.oai.test;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.heal.module.oai.heal.HEALDataAccessor;
import org.heal.module.oai.heal.HEALMetadataFormat;
import org.heal.module.oai.heal.HEALProviderConfig;
import org.heal.module.oai.provider.DataAccessor;
import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIProvider;
import org.heal.module.oai.provider.nsdl_dc.NSDL_DCMetadataFormat;
import org.heal.module.oai.provider.oai_dc.OAI_DCMetadataFormat;
import org.heal.util.FileLocator;

import com.ora.jsp.sql.DataSourceWrapper;
import junit.framework.TestCase;

/**
 * @author Seth Wright
 */
public class OAIProviderTest extends TestCase {

    String requestURL = "http://www.healcentral.org/oaiprovider";
    String identifier = "oai:org.heal:8F8668CE-DA39-4A9D-B704-CDC2A55A413C";
    String badidentifier = "oai:org.heal:8F8668CE-XXXX-XXXX-B704-CDC2A55A413C";
    String resumptionToken  = "oai_dc|null|null|null|54|null|0|5";
    String from = "2003-11-17T12:00:00Z";
    String until = "2003-12-17T12:00:00Z";
    String prefix = "nsdl_dc";
    OAIMetadataFormat[] formats = null;
    {
        formats = new OAIMetadataFormat[3];
        formats[0] = new OAI_DCMetadataFormat();
        formats[0].setPrefix("oai_dc");
        formats[1] = new NSDL_DCMetadataFormat();
        formats[1].setPrefix("nsdl_dc");
        formats[2] = new HEALMetadataFormat();
        formats[2].setPrefix("heal");
    }

    public void testIdentify() throws Exception
    {
        runTest("Identify",null,null,null,null,null,null);
    }

    public void testListMetadataFormats() throws Exception
    {
        runTest("ListMetadataFormats",null,null,null,null,null,null);
    }

    public void testListMetadataFormatsWithId() throws Exception
    {
        runTest("ListMetadataFormats",identifier,null,null,null,null,null);
    }

    public void testListMetadataFormatsWithBadId() throws Exception
    {
        runTest("ListMetadataFormats",badidentifier,null,null,null,null,null);
    }

    public void testListSets() throws Exception
    {
        runTest("ListSets",null,null,null,null,null,null);
    }

    public void testGetRecord() throws Exception
    {
        runTest("GetRecord",identifier,prefix,null,null,null,null);
    }

    public void testGetRecordBadId() throws Exception
    {
        runTest("GetRecord",badidentifier,prefix,null,null,null,null);
    }
    public void testListRecords() throws Exception
    {
        runTest("ListRecords",null,prefix,null,null,null,null);
    }
    public void testListRecordsResumption() throws Exception
    {
        runTest("ListRecords",null,null,null,null,resumptionToken,null);
    }
    public void testListRecordsFrom() throws Exception
    {
        runTest("ListRecords",null,prefix,from,null,null,null);
    }
    public void testListRecordsUntil() throws Exception
    {
        runTest("ListRecords",null,prefix,null,until,null,null);
    }
    public void testListRecordsFromUntil() throws Exception
    {
        runTest("ListRecords",null,prefix,from,until,null,null);
    }
    public void testListIdentifiers() throws Exception
    {
        runTest("ListIdentifiers",null,prefix,null,null,null,null);
    }
    public void testListIdentifiersResumption() throws Exception
    {
        runTest("ListIdentifiers",null,null,null,null,resumptionToken,null);
    }
    private void runTest(String verb,String identifier,String prefix,String from,String until,String resumption,String set)
    throws Exception
    {
        /* String idPrefix,
                                int resumptionThreshold,
                                String coverage,
                                String metametadataRole,
                                String granularity,
                                String[] descriptions,
                                String[] adminEmails,
                                String healVCard,
                                String[] allowedCollections
          */
        FileLocator locator = new FileLocator();
        locator.setServerBaseURL("http://localhost:8080");
        locator.setContentDirectory("testcontent");
        HEALProviderConfig config = new HEALProviderConfig(null,10,null,null,null,null,null,null,null,null,null,formats,null);
        DataAccessor dataAccessor = getDataAccessor(config);

        PrintWriter out = new PrintWriter(System.out);
        out.println("========================================");
        OAIProvider.init(config);
        Map parameters = new HashMap();
        addParameter("verb",verb,parameters);
        addParameter("identifier",identifier,parameters);
        addParameter("metadataPrefix",prefix,parameters);
        addParameter("from",from,parameters);
        addParameter("until",until,parameters);
        addParameter("resumptionToken",resumption,parameters);
        addParameter("set",set,parameters);
        OAIProvider.processRequest(requestURL,dataAccessor,parameters,out);
        out.println("========================================");
        out.println();
        out.flush();
    }

    private void addParameter(String paramName,String paramValue,Map parameterMap) {
        if (paramValue != null) {
            String[] paramArr = new String[1];
            paramArr[0] = paramValue;
            parameterMap.put(paramName,paramArr);
        }
    }
    private DataAccessor getDataAccessor(HEALProviderConfig config) throws Exception {
        String jdbcUser = null;
        String jdbcPassword = null;

        String jdbcDriverClassName = "com.thinweb.tds.Driver";
        String jdbcURL = "jdbc:twtds:sqlserver://localhost:9229/healdev";

        if(jdbcURL == null) {
//			jdbcURL = "jdbc:odbc:heal";
        }
        if(jdbcDriverClassName == null) {
//			jdbcDriverClassName = "sun.jdbc.odbc.JdbcOdbcDriver";
        }
        if(jdbcUser == null) {
            jdbcUser = "swright";
        }
        if(jdbcPassword == null) {
            jdbcPassword = "Jaheq@#e";
        }
        // use the DataSourceWrapper from the O'Reilly JSP library
        DataSource ds = new DataSourceWrapper(jdbcDriverClassName, jdbcURL, jdbcUser, jdbcPassword);
        return new HEALDataAccessor(config,ds);
    }
}

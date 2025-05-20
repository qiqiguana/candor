/*
 *   CVS $Id: PDPadapter.java,v 1.1 2006/11/06 19:56:51 grodecki Exp $
 * 
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2005, 2006 Browsersoft Inc.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License, version 2, 
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License is available at
 *   http://www.fsf.org/licensing/licenses/gpl.html
 *
 *   Email: info@openhre.org
 *   Web:   http://www.openhre.org
 */
package com.browsersoft.aacs.xacml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.sun.xacml.ConfigurationStore;
import com.sun.xacml.Indenter;
import com.sun.xacml.PDP;
import com.sun.xacml.ParsingException;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;


/**
 * This class holds the PDP (Policy Decision Point) that is used to make
 * Access Control decisions. The PDP is configured from the XACML configuration
 * file.
 *
 * @since 1.1
 * @author seth proctor
 */
public class PDPadapter
{

    // this is the actual PDP object we'll use for evaluation
    private PDP pdp = null;
    private static org.apache.log4j.Logger cat 
      = org.apache.log4j.Logger.getLogger(PDPadapter.class.getName());

    // This creates a PDPadapter with a PDP based on the configuration 
    // defined by the runtime property com.sun.xcaml.PDPConfigFile.
    public PDPadapter(String configFile) throws Exception {
        
        // set the configuration file containing information on policies, etc
        cat.debug("Setting System property com.sun.xacml.PDPConfigFile to "
        				+configFile);
        Properties props = System.getProperties();
        props.put("com.sun.xacml.PDPConfigFile",configFile);
        System.setProperties(props);
        
        // load the configuration
        ConfigurationStore store = new ConfigurationStore();

        // use the default factories from the configuration
        store.useDefaultFactories();
        
        // get the PDP configuration's and setup the PDP
        pdp = new PDP(store.getDefaultPDPConfig());
    }

    /**
     * Evaluate the supplied Request according to the 
     * current PDP and return the Response Context.
     */
    public ResponseCtx evaluate(String requestFile)
        throws IOException, ParsingException
    {
        // setup the request based on the file
        RequestCtx request =
            RequestCtx.getInstance(new FileInputStream(requestFile));

        // evaluate the request
        return pdp.evaluate(request);
    }

    
    /**
     * Evaluate the supplied Request Context according to the 
     * current PDP and return the Access Control decision.
     */
    public int makeDecision(RequestCtx request) {
       int result_value = -1;
       
        // evaluate the request
        try {
            ResponseCtx response = pdp.evaluate(request);
            Set xacml_results = response.getResults();
            Iterator xacml_iter = xacml_results.iterator();
            while (xacml_iter.hasNext()) {
                Result xacml_result = (Result)xacml_iter.next();
                xacml_result.encode(System.out, new Indenter());
                result_value = xacml_result.getDecision();
            }
            
        } catch (Exception e) {
            cat.error(e);
        }
        
        return result_value;
        
    }    
    
}

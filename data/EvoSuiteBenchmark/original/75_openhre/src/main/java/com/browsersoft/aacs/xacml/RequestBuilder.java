/*
 *   CVS $Id: RequestBuilder.java,v 1.1 2006/11/06 19:56:51 grodecki Exp $
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

import com.sun.xacml.EvaluationCtx;
import com.sun.xacml.Indenter;

import com.sun.xacml.attr.AnyURIAttribute;
import com.sun.xacml.attr.RFC822NameAttribute;
import com.sun.xacml.attr.StringAttribute;

import com.sun.xacml.ctx.Attribute;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.Subject;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Class to build and generate an XACMLRequest. 
 * This is a major part of what a PEP does.
 *
 */
public class RequestBuilder
{
    private static org.apache.log4j.Logger cat 
    = org.apache.log4j.Logger.getLogger(RequestBuilder.class.getName());

    /**
     * Sets up the Subject section of the request. This Request only has
     * one Subject section, and it uses the default category. To create a
     * Subject with a different category, you simply specify the category
     * when you construct the Subject object.
     *
     */
    public static Set setupSubjects(String subject, Set roles, Set groups) 
    		throws URISyntaxException {
        HashSet attributes = new HashSet();

        // setup the id and value for the requesting subject
        URI subjectId =
            new URI("urn:oasis:names:tc:xacml:1.0:subject:subject-id");
        RFC822NameAttribute value =
            new RFC822NameAttribute(subject);

        // create the subject section with three attributes, the first with
        // the subject's identity...
        attributes.add(new Attribute(subjectId, null, null, value));
        
        Iterator it = null;
        
        // ...and the second with the subject's role membership(s)
        if (roles == null) {
        	cat.warn("Subject "+subject+" has null roles");
        } else {
	        it = roles.iterator();
	        while (it.hasNext())
	            attributes.add(new Attribute(new URI("urn:openhre:names:security:1.0:role"),
	                    null, null,
	                    new StringAttribute((String)(it.next()))));
        }

//      ...and the third with the subject's group membership(s)
        if (groups == null) {
        	cat.warn("Subject "+subject+" has null groups");
        } else {
	        it = groups.iterator();
	        while (it.hasNext())
	            attributes.add(new Attribute(new URI("urn:openhre:names:security:1.0:group"),
	                    null, null,
	                    new StringAttribute((String)(it.next()))));
        }
                
        // bundle the attributes in a Subject with the default category
        HashSet subjects = new HashSet();
        subjects.add(new Subject(attributes));

        return subjects;
    }

    /**
     * Creates a Resource specifying the resource-id, a required attribute.
     *
     * @return a Set of Attributes for inclusion in a Request
     *
     * @throws URISyntaxException if there is a problem with a URI
     */
    public static Set setupResource(String resName) throws URISyntaxException {
        HashSet resource = new HashSet();

        /*
        // the resource being requested
        AnyURIAttribute value =
            new AnyURIAttribute(new URI(resName));

        // create the resource using a standard, required identifier for
        // the resource being requested
        resource.add(new Attribute(new URI(EvaluationCtx.RESOURCE_ID),
                                   null, null, value));
        */
        
        URI resId =
            new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
        
        resource.add(new Attribute(resId, null, null,
                new StringAttribute(resName)));
        
        return resource;
    }

    /**
     * Creates an Action specifying the action-id, an optional attribute.
     *
     * @return a Set of Attributes for inclusion in a Request
     *
     * @throws URISyntaxException if there is a problem with a URI
     */
    public static Set setupAction(String actionName) throws URISyntaxException {
        HashSet action = new HashSet();

        // this is a standard URI that can optionally be used to specify
        // the action being requested
        URI actionId =
            new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");

        // create the action
        action.add(new Attribute(actionId, null, null,
                                 new StringAttribute(actionName)));

        return action;
    }
   
}

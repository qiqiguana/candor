/*
 * @(#)FilePolicyModule.java
 *
 * Copyright 2003-2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistribution of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 * 
 *   2. Redistribution in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for use in
 * the design, construction, operation or maintenance of any nuclear facility.
 */

package com.browsersoft.aacs.xacml;

import com.browsersoft.aacs.Login;
import com.sun.xacml.AbstractPolicy;
import com.sun.xacml.EvaluationCtx;
import com.sun.xacml.MatchResult;
import com.sun.xacml.ParsingException;
import com.sun.xacml.Policy;

import com.sun.xacml.ctx.Status;
import com.sun.xacml.PolicyReference;
import com.sun.xacml.PolicySet;

import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.PolicyFinderModule;
import com.sun.xacml.finder.PolicyFinderResult;
import com.sun.xacml.PolicyMetaData;
import com.sun.xacml.PolicyReference;
import com.sun.xacml.PolicySet;
import com.sun.xacml.VersionConstraints;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import com.sun.xacml.support.finder.PolicyReader;



/**
 * This module represents a collection of files containing polices,
 * each of which will be searched through when trying to find a
 * policy that is applicable to a specific request. It does not support
 * policy references.
 * <p>
 * Note that this class used to be provided in the
 * <code>com.sun.xacml.finder.impl</code> package with a warning that it
 * would move out of the core packages eventually. This is partly because
 * this class doesn't represent standard functionality, and partly because
 * it isn't designed to be generally useful as anything more than an
 * example. Because so many people have used this class, however, it stayed
 * in place until the 2.0 release.
 * <p>
 * As of the 2.0 release, you may still use this class (in its new location),
 * but you are encouraged to migrate to the new support modules that are
 * much richer and designed for general-purpose use. Also, note that the
 * <code>loadPolicy</code> methods that used to be available from this class
 * have been removed. That functionality has been replaced by the much more
 * useful <code>PolicyReader</code> class. If you need to load policies
 * directly, you should consider that new class.
 *
 * @since 1.0
 * @author Seth Proctor
 */
public class FilePolicyModule 
		extends com.sun.xacml.support.finder.FilePolicyModule {

    // the reader used to load all policies
    private PolicyReader reader;

    // the Policy directory
    private String policyDir;

    // the schema file we're using, if any
    private File schemaFile = null;

    // the logger we'll use for all messages
    private static final Logger logger =
        Logger.getLogger(FilePolicyModule.class.getName());

    /**
     * Constructor which retrieves the schema file to validate policies against
     * from the <code>PolicyReader.POLICY_SCHEMA_PROPERTY</code>. If the
     * retrieved property is null, then no schema validation will occur.
     */
    public FilePolicyModule() {
    	super();

        String schemaName =
            System.getProperty(PolicyReader.POLICY_SCHEMA_PROPERTY);

        if (schemaName != null)
            schemaFile = new File(schemaName);
    }

    /**
     * Constructor that uses the specified <code>File</code> as the schema
     * file for XML validation. If schema validation is not desired, a null
     * value should be used.
     *
     * @param schemaFile the schema file to validate policies against,
     *                   or null if schema validation is not desired.
     */
    public FilePolicyModule(File schemaFile) {
    	super(schemaFile);
    	this.schemaFile = schemaFile;
    }

    /**
     * Constructor that uses the specified <code>String</code> as the schema
     * file for XML validation. If schema validation is not desired, a null
     * value should be used.
     *
     * @param schemaFile the schema file to validate policies against,
     *                   or null if schema validation is not desired.
     */
    public FilePolicyModule(String schemaFile) {
    	super(schemaFile);
        this.schemaFile = ((schemaFile != null) ? new File(schemaFile) : null);    
    }

    /**
     * Constructor that specifies a set of initial policy files to use. This
     * retrieves the schema file to validate policies against from the
     * <code>PolicyReader.POLICY_SCHEMA_PROPERTY</code>. If the retrieved
     * property is null, then no schema validation will occur.
     *
     * @param fileNames a <code>List</code> of <code>String</code>s that
     *                  identify policy files
     */
    public FilePolicyModule(List fileNames) {
    	super(fileNames);

        String schemaName =
            System.getProperty(PolicyReader.POLICY_SCHEMA_PROPERTY);

        if (schemaName != null)
            schemaFile = new File(schemaName);
    }

    /**
     * Constructor that specifies a set of initial policy files to use and
     * the schema file used to validate the policies. If schema validation is
     * not desired, a null value should be used.
     *
     * @param fileNames a <code>List</code> of <code>String</code>s that
     *                  identify policy files
     * @param schemaFile the schema file to validate policies against,
     *                   or null if schema validation is not desired.
     */
    public FilePolicyModule(List fileNames, String schemaFile) {
        super(fileNames,schemaFile);
        this.schemaFile = ((schemaFile != null) ? new File(schemaFile) : null);    
    }

    /**
     * Initializes the <code>FilePolicyModule</code> by loading
     * the policies contained in the collection of files associated
     * with this module. This method also uses the specified 
     * <code>PolicyFinder</code> to help in instantiating PolicySets.
     * 
     * init() also gets the Policy file directory to be used for
     * resolving references.
     *
     * @param finder a PolicyFinder used to help in instantiating PolicySets
     */
    public void init(PolicyFinder finder) {
    	super.init(finder);
    	reader = new PolicyReader(finder, logger, schemaFile);
    	policyDir = Login.getProps().getProperty(
    		"XACMLPolicyDir", "webapps/share/WEB-INF/classes/xacml/policy/");
    }

    /**
     * Always returns <code>true</code> since this module does support
     * finding policies based on reference.
     *
     * @return true
     */
    public boolean isIdReferenceSupported() {
        return true;
    }

    /**
     * Attempts to find a policy by reference, based on the provided
     * parameters. Specifically, this module will try to treat the reference
     * as a URL, and resolve that URL directly. If the reference is not
     * a valid URL, cannot be resolved, or does not resolve to an XACML
     * policy, then no matching policy is returned. This method never
     * returns an error.
     *
     * @param idReference an identifier specifying some policy
     * @param type type of reference (policy or policySet) as identified by
     *             the fields in <code>PolicyReference</code>
     * @param constraints any optional constraints on the version of the
     *                    referenced policy (this will never be null, but
     *                    it may impose no constraints, and in fact will
     *                    never impose constraints when used from a pre-2.0
     *                    XACML policy)
     * @param parentMetaData the meta-data from the parent policy, which
     *                       provides XACML version, factories, etc.
     *
     * @return the result of looking for a matching policy
     */
    public PolicyFinderResult findPolicy(URI idReference, int type,
                                         VersionConstraints constraints,
                                         PolicyMetaData parentMetaData) {
        // see if the URI is in fact a URL
        URL url = null;
        try {
            url = new URL("file:"+policyDir+idReference.toString()+".xml");
        } catch (MalformedURLException murle) {
            // it's not a URL, so we can't handle this reference
            return new PolicyFinderResult();
        }

        // try resolving the URL
        AbstractPolicy policy = null;
        try {
            policy = reader.readPolicy(url);
        } catch (ParsingException pe) {
            // An error loading the policy could be many things (the URL
            // doesn't actually resolve a policy, the server is down, the
            // policy is invalid, etc.). This could be interpreted as an
            // error case, or simply as a case where no applicable policy
            // is available (as is done when we pre-load policies). This
            // module chooses the latter interpretation.
            return new PolicyFinderResult();
        }

        // check that we got the right kind of policy...if we didn't, then
        // we can't handle the reference
        if (type == PolicyReference.POLICY_REFERENCE) {
            if (! (policy instanceof Policy))
                return new PolicyFinderResult();
        } else {
            if (! (policy instanceof PolicySet))
                return new PolicyFinderResult();
        }

        // finally, check that the constraints match ... note that in a more
        // powerful module, you could actually have used the constraints to
        // construct a more specific URL, passed the constraints to the
        // server, etc., but this example module is staying simple
        if (! constraints.meetsConstraint(policy.getVersion()))
            return new PolicyFinderResult();

        // if we got here, then we successfully resolved a policy that is
        // the correct type, so return it
        return new PolicyFinderResult(policy);
    }

}

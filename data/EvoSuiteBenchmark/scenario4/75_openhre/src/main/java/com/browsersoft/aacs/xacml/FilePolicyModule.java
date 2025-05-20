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
public class FilePolicyModule extends com.sun.xacml.support.finder.FilePolicyModule {

    // the reader used to load all policies
    private PolicyReader reader;

    // the Policy directory
    private String policyDir;

    // the schema file we're using, if any
    private File schemaFile = null;

    // the logger we'll use for all messages
    private static final Logger logger = Logger.getLogger(FilePolicyModule.class.getName());

    /**
     * Constructor which retrieves the schema file to validate policies against
     * from the <code>PolicyReader.POLICY_SCHEMA_PROPERTY</code>. If the
     * retrieved property is null, then no schema validation will occur.
     */
    public FilePolicyModule() {
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
    public void init(PolicyFinder finder);

    /**
     * Always returns <code>true</code> since this module does support
     * finding policies based on reference.
     *
     * @return true
     */
    public boolean isIdReferenceSupported();

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
    public PolicyFinderResult findPolicy(URI idReference, int type, VersionConstraints constraints, PolicyMetaData parentMetaData);
}

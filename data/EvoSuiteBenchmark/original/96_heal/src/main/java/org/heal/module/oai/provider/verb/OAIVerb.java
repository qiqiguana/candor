/*
 * Created on Mar 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.heal.module.oai.provider.verb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.heal.module.oai.provider.DataAccessor;
import org.heal.module.oai.provider.OAIErrorCode;
import org.heal.module.oai.provider.OAIProvider;
import org.heal.module.oai.provider.OAIProviderConfig;
import org.heal.module.oai.provider.ValidatedInput;

/**
 * @author Seth Wright
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class OAIVerb {
	private static final HashMap verbs = new HashMap();
	public static final OAIVerb GET_RECORD = new GetRecord();
	public static final OAIVerb IDENTIFY = new Identify();
	public static final OAIVerb LIST_IDENTIFIERS = new ListIdentifiers();
	public static final OAIVerb LIST_METADATA_FORMATS = new ListMetadataFormats();
	public static final OAIVerb LIST_RECORDS = new ListRecords();
	public static final OAIVerb LIST_SETS = new ListSets();

	private final String name;
	protected OAIVerb(final String name) {
		if (verbs.get(name)!=null) {
			throw new IllegalArgumentException("Duplicate declaration of OAIVerb '"+name+"'");
		}
		this.name = name;
		verbs.put(this.name,this);
	}
	public static OAIVerb getVerb(final String verbName) {
		return (OAIVerb)verbs.get(verbName);
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return name;
	}
	public int hashCode() {
		return name.hashCode();
	}
	public boolean equals(Object obj) {	
		return (obj instanceof OAIVerb && name.equals(((OAIVerb)obj).name));
	}
	public int compareTo(Object obj) {
		return (name.compareTo(((OAIVerb)obj).name));
	}
	
	public abstract void execute(final ValidatedInput input,final OAIProviderConfig config,final DataAccessor dataAccessor,final PrintWriter writer) throws IOException;
	public abstract void validateInput(final ValidatedInput input,final Map parameters);
	public String getExtraArgumentError(final String argumentName,final String argumentValue) {
		return OAIProvider.getErrorString(OAIErrorCode.BAD_ARGUMENT,"The parameter '"+argumentName+"' (value='"+argumentValue+"') is not allowed in "+getName()+" requests.");
	}

	public String getMissingArgumentError(final String argumentName) {
		return OAIProvider.getErrorString(OAIErrorCode.BAD_ARGUMENT,"OAI "+getName()+" requests require the "+argumentName+" parameter but it was missing from this request.");
	}
}

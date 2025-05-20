/*
 * Created on Mar 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.heal.module.oai.provider;

import java.util.ArrayList;
import java.util.Date;

import org.heal.module.oai.provider.verb.OAIVerb;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ValidatedInput {
	public OAIVerb verb = null;
	public OAIResumptionToken resumptionToken = null;
	public OAIMetadataFormat format = null;
	public String identifier = null;
	public String set = null;
	public Date from = null;
	public Date until = null;
	public boolean hadError = false;
	public ArrayList errorList = new ArrayList();
}

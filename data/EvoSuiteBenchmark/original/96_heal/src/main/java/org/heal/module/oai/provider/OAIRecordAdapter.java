/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider;

import org.w3c.dom.Document;

/**
 * @author Seth Wright
 *
 */
public interface OAIRecordAdapter {
	//this is an interface that is only used to allow an abstract
	//call to OAIRecord.
	
	//XXX TODO: we need to implement support for multiple abouts
	public boolean hasOAIAbout();
	public boolean hasCustomAbout();
	public void addCustomAbout(Document doc);
	
}

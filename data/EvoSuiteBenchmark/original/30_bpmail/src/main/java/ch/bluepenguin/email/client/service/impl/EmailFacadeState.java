/*
 * Created on 29.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.client.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Christian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmailFacadeState {
	private HashMap states;
	
	public EmailFacadeState() {
		states = new HashMap();
		
	}
	
	public void setState(Integer ID, boolean dirtyFlag) {
		states.put(ID,new Boolean(dirtyFlag));
	}
	
	public void clear() {
		states.clear();
	}
	
	/**
	 * 
	 * @return true if the boolean flag is true or if the id
	 * does not exist in the map
	 */
	public boolean isDirty(Integer ID) {
		Boolean dirty = (Boolean)states.get(ID);
		if(dirty==null) return true;
		return dirty.booleanValue();
	}

	public void setAll(boolean dirtyFlag) {
		Iterator keys = states.keySet().iterator();
		while(keys.hasNext()) {
			Integer key = (Integer)keys.next();
			setState(key, dirtyFlag);
		}
	}

	public boolean isAllClean() {
		//empty is pristine state
		if(states.size()==0) return false;
		Iterator keys = states.keySet().iterator();
		while(keys.hasNext()) {
			Integer key = (Integer)keys.next();
			if(isDirty(key)) {
				return false;
			}
		}
		return true;
	}
}

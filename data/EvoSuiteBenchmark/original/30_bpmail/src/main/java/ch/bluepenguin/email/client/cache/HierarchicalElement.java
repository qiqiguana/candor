/*
 * Created on 29.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.client.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import net.sf.ehcache.Element;

/**
 * @author Christian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HierarchicalElement extends Element {
	private List childKeys;
	/**
	 * @param arg0
	 * @param arg1
	 */
	public HierarchicalElement(Serializable parentKey, Serializable parentObject, List childKeys) {
		super(parentKey, parentObject);
		this.childKeys = childKeys;
		// TODO Auto-generated constructor stub
	}
	
	public List getChildKeys() {
		return childKeys;
	}

}

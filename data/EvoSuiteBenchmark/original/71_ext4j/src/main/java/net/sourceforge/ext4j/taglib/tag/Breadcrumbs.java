/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author luc
 *
 */
public class Breadcrumbs implements Iterable<Breadcrumb> {
	
	private List<Breadcrumb> mCrumbs = new ArrayList<Breadcrumb>();

	public Iterator<Breadcrumb> iterator() {
		return mCrumbs.iterator();
	}
	
	public Breadcrumbs add(String pName, String pURL) {
		mCrumbs.add(new Breadcrumb(pName, pURL));
		return this;
	}
	
	public Breadcrumbs add(String pName) {
		mCrumbs.add(new Breadcrumb(pName));
		return this;
	}
	

}

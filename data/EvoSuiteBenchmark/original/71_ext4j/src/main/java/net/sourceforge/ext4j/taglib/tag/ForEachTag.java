/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag;

import javax.servlet.jsp.JspTagException;


/**
 * @author luc
 *
 */
public class ForEachTag extends org.apache.taglibs.standard.tag.rt.core.ForEachTag {

	private static final long serialVersionUID = -5666834612868162504L;

	@Override
	protected ForEachIterator supportedTypeForEachIterator(Object pItems)
			throws JspTagException {
		if (Iterable.class.isAssignableFrom(pItems.getClass())) {
			return toForEachIterator(((Iterable) pItems).iterator());
		} else {
			return super.supportedTypeForEachIterator(pItems);
		}
	}
	
}

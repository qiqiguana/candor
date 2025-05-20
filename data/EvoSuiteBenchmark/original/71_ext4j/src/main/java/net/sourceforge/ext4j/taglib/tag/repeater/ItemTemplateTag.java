/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag.repeater;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Renders current item.
 * Port of ASP.NET Control "Repeater" element.
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class ItemTemplateTag extends TagSupport {

	private static final long serialVersionUID = -5366648898715981947L;

	@Override
	public int doStartTag() throws JspException {
		
		if (!(getParent() instanceof LoopTag)) {
			throw new JspException("Must be used inside a LoopTag.");			
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	
	
}

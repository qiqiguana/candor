/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag.repeater;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Renders body only when dealing with first element from loop.
 * Advantage is that it won't render anything if there are no iteration (e.g. empty list).
 * Port of ASP.NET Control "Repeater" element.
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class HeaderTemplateTag extends TagSupport {

	private static final long serialVersionUID = -3077174534867551778L;

	@Override
	public int doStartTag() throws JspException {
				
		if (!(getParent() instanceof LoopTag)) {
			throw new JspException("Must be used inside a LoopTag.");			
		}
		LoopTag parent = (LoopTag) getParent();
		if (parent.getLoopStatus() == null) {
			throw new JspException("'varStatus' must be specified to be able to use it. Consider using RepeaterTag or set 'varStatus' attribute to your loop tag.");
		}
		if (parent.getLoopStatus().isFirst()) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}		
	}
}

/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag.repeater;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Renders body only when dealing with last element from loop.
 * Port of ASP.NET Control "Repeater" element.
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class FooterTemplateTag extends TagSupport {

	private static final long serialVersionUID = -2810849883535324606L;

	@Override
	public int doStartTag() throws JspException {
		if (!(getParent() instanceof RepeaterTag)) {
			throw new JspException("Must be used inside a RepeaterTag.");			
		}
		LoopTag parent = (LoopTag) getParent();
		if (parent.getLoopStatus() == null) {
			throw new JspException("'varStatus' must be specified to be able to use it. Consider using RepeaterTag or set 'varStatus' attribute to your loop tag.");
		}
		if (parent.getLoopStatus().isLast()) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}
}

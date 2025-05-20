/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author luc
 *
 */
public abstract class BaseSingleBox extends BodyTagSupport {
	
	private static final long serialVersionUID = 172403171446868250L;
	private Log mLogger = LogFactory.getLog(this.getClass());
	
	/**
	 * 
	 */
	public BaseSingleBox() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			renderTag(pageContext.getOut());
		} catch (IOException oIOE) {
			mLogger.error("Couldn't render tag", oIOE);
		} catch (Throwable t) {
			mLogger.fatal("Error while rendering tag", t);
		}
		return EVAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doInitBody()
	 */
	@Override
	public void doInitBody() throws JspException {
		super.doInitBody();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#setPageContext(javax.servlet.jsp.PageContext)
	 */
	@Override
	public void setPageContext(PageContext pContext) {
		pageContext = pContext;
	}	
	
	public abstract void renderTag(JspWriter pOut) throws JspException, IOException;
	

}

/**
 * @author luc
 */
package net.sourceforge.ext4j.taglib.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sourceforge.ext4j.taglib.bo.TagUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author luc
 *
 */
public class SimpleBreadcrumbsTag extends BodyTagSupport {
	
	private static final long serialVersionUID = 7704402082885653130L;
	
	private Log mLogger = LogFactory.getLog(this.getClass());
	private List<Breadcrumb> mBreadcrumbs;
	private String mSeparator;
	
	public static final String DEFAULT_TEMPLATE_LAST_CRUMB = "<a class='current'>%s</a>";
	public static final String DEFAULT_TEMPLATE_CRUMB_WITHOUT_LINK = "<a>%s</a>";
	public static final String DEFAULT_TEMPLATE_CRUMB_WITH_LINK = "<a href='%s'>%s</a>";
	
	public static final String PROP_TEMPLATE_LAST_CRUMB = "SimpleBreadcrumbsTag.Template.LastCrumb";
	public static final String PROP_TEMPLATE_CRUMB_WITH_LINK = "SimpleBreadcrumbsTag.Template.CrumbWithLink";
	public static final String PROP_TEMPLATE_CRUMB_WITHOUT_LINK = "SimpleBreadcrumbsTag.Template.CrumbWithoutLink";
	
	
	/**
	 * 
	 */
	public SimpleBreadcrumbsTag() {
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

	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.tag.BaseSingleBox#renderTag(javax.servlet.jsp.JspWriter)
	 */
	public void renderTag(JspWriter pOut) throws JspException, IOException {
		if (mBreadcrumbs != null) {
			StringBuilder oBuf = new StringBuilder();
			for (int i = 0; i < mBreadcrumbs.size(); i++) {
				Breadcrumb oCrumb = mBreadcrumbs.get(i);
				String oCrumbLabel = Functions.capFirstLetters(oCrumb.getName());
				if (i == (mBreadcrumbs.size() - 1)) {
					oBuf.append(String.format(TagLibConfig.getProperty(PROP_TEMPLATE_LAST_CRUMB, DEFAULT_TEMPLATE_LAST_CRUMB), oCrumbLabel));
				} else {
					if (oCrumb.getURL() != null) {
						oBuf.append(String.format(TagLibConfig.getProperty(PROP_TEMPLATE_CRUMB_WITH_LINK, DEFAULT_TEMPLATE_CRUMB_WITH_LINK), createURL(oCrumb), oCrumbLabel));
					} else {
						oBuf.append(String.format(TagLibConfig.getProperty(PROP_TEMPLATE_CRUMB_WITHOUT_LINK, DEFAULT_TEMPLATE_CRUMB_WITHOUT_LINK), oCrumbLabel));
					}
				}
				oBuf.append(mSeparator);				
			}
			pOut.write(oBuf.toString());
		}
	}

	private String createURL(Breadcrumb pCrumb) {
		try {
			String oURL = TagUtil.resolveUrl(pCrumb.getURL(), null, pageContext);
			oURL = new TagUtil.ParamManager().aggregateParams(oURL);
			HttpServletResponse response = ((HttpServletResponse) pageContext.getResponse());
			oURL = response.encodeURL(oURL);
			return oURL;
		} catch (Exception e) {
			mLogger.error("Error creating link for url \"" + pCrumb.getURL() + "\".", e);
		}
		return null;
	}
	
	/**
	 * @return Returns the breadcrumbs.
	 */
	public List<Breadcrumb> getBreadcrumbs() {
		return mBreadcrumbs;
	}

	/**
	 * @param pBreadcrumbs The breadcrumbs to set.
	 */
	public void setBreadcrumbs(List<Breadcrumb> pBreadcrumbs) {
		mBreadcrumbs = pBreadcrumbs;
	}
	
	/**
	 * @return Returns the separator.
	 */
	public String getSeparator() {
		return mSeparator;
	}

	/**
	 * @param pSeparator The separator to set.
	 */
	public void setSeparator(String pSeparator) {
		mSeparator = pSeparator;
	}

}

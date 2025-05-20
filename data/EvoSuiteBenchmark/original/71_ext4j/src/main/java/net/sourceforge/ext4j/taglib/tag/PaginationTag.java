/**
 *
 */
package net.sourceforge.ext4j.taglib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sourceforge.ext4j.taglib.bo.DefaultResourceLoader;
import net.sourceforge.ext4j.taglib.bo.TagUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tripfilms.os.exttaglib.pagination.datatype.Page;
import com.tripfilms.os.exttaglib.pagination.datatype.Pages;
import com.tripfilms.os.exttaglib.pagination.datatype.Pagination;
import com.tripfilms.os.exttaglib.pagination.datatype.StaticPage;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class PaginationTag extends BaseSingleBox {

	private static final String DEFAULT_XSL = "classpath:/net/sourceforge/ext4j/taglib/simple_pagination.xsl";
	private static final long serialVersionUID = 4722503210539035774L;
	private static final String EQUALS = "=";
	private static final String AMPERSAND = "&";
	private static final String QUESTION_MARK = "?";
	private static final String DEFAULT_FROM_PARAM_NAME = "from";
	private static final String DEFAULT_TO_PARAM_NAME = "to";

	private int mFrom;
	private int mTotal;
	private int mByPage;
	private int mPages;
	private String mUrl;
	private String mFromParamName = DEFAULT_FROM_PARAM_NAME;
	private String mToParamName = DEFAULT_TO_PARAM_NAME;
	private boolean mUseFromToOnFirstPage = false;
	private Log mLogger = LogFactory.getLog(PaginationTag.class);

	private static JAXBContext mJAXBContext;
	private static Templates mTemplate;

	static {
		try {
			mJAXBContext = JAXBContext.newInstance("com.tripfilms.os.exttaglib.pagination.datatype");
		} catch (JAXBException e) {
			throw new RuntimeException("Error initializing JAXB for PaginationTag.", e);
		}
		try {
			mTemplate = loadTemplate(DEFAULT_XSL);
		} catch (Exception e) {
			throw new RuntimeException("Error loading XSL for PaginationTag.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.tripfilms.os.exttaglib.BaseSingleBox#renderTag(javax.servlet.jsp.
	 * JspWriter)
	 */
	@Override
	public void renderTag(JspWriter pOut) throws JspException, IOException {
		Pagination oPagination = getPagination();
		try {
			Marshaller oMarshaller = mJAXBContext.createMarshaller();
			TransformerFactory oTxFactory = TransformerFactory.newInstance();
			Result oResult = new StreamResult(pOut);
			TransformerHandler oHandler = ((SAXTransformerFactory) oTxFactory).newTransformerHandler(mTemplate);
			oHandler.setResult(oResult);

			oMarshaller.marshal(oPagination, oHandler);
		} catch (JAXBException e) {
			mLogger.fatal("Error initializing JAXBContext to create Pagination XML.", e);
			throw new JspException(e);
		} catch (TransformerConfigurationException e) {
			mLogger.fatal("Error with SAX.", e);
			throw new JspException(e);
		} catch (Exception e) {
			mLogger.fatal("Unexpected error.", e);
			throw new JspException(e);
		}
	}

	/**
	 * Helper to set XSL template to a different one than the default one.
	 * WARNING: This will set the XSL template for all PaginationTag (static).
	 *
	 * @param pTemplateLocation
	 */
	public void setTemplate(String pTemplateLocation) throws Exception {
		Templates oNewTemplate = null;
		try {
			oNewTemplate = loadTemplate(pTemplateLocation);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		mTemplate = oNewTemplate;
	}

	private static Templates loadTemplate(String pTemplateLocation) throws TransformerFactoryConfigurationError, TransformerConfigurationException {
		TransformerFactory oTxFactory = TransformerFactory.newInstance();
		DefaultResourceLoader oResourceLoader = new DefaultResourceLoader();
		Source oSource = new StreamSource(
			oResourceLoader.getResource(pTemplateLocation).toExternalForm()
		);
	    return oTxFactory.newTemplates(oSource);
	}

	public Pagination getPagination() throws JspException {
		Pagination oResult = new Pagination();
		if (mTotal <= 0)
			return oResult;
		if (mByPage < 1)
			mByPage = 10;
		if (mFrom > mTotal) // then we consider it's the last page
			mFrom = mTotal - 1;
		if (mFrom < 1)
			mFrom = 1;

		int oNbPages = mTotal / mByPage;
		if (mTotal % mByPage > 0) // if any reminder...
			oNbPages++; // ...we'll include them in an extra page

		// number of pages to display
		if (oNbPages < mPages) {
			mPages = oNbPages;
		}
		oResult.setTotalPages(oNbPages);

		int oCurrentPage = (mFrom - 1) / mByPage + 1;
		oResult.setCurrentPage(oCurrentPage);

		// first page to display in the list
		int oFirstLink = oCurrentPage - (mPages / 2);
		if (oFirstLink < 1)
			oFirstLink = 1;
		if (oCurrentPage > oNbPages - (mPages / 2))
			oFirstLink = oNbPages - mPages + 1;

		String oLink = null;
		// First & Prev
		oLink = addFromTo(mUrl, 1, mByPage);
		if (oCurrentPage > 1) {
			oResult.setFirstPage(newStaticPage(oLink));
			oLink = addFromTo(mUrl, mByPage * (oCurrentPage - 1 - 1) + 1,
					mByPage * (oCurrentPage - 1));
			oResult.setPreviousPage(newStaticPage(oLink));
		}

		// Pages
		oResult.setPages(new Pages());
		for (int i = oFirstLink; i < (oFirstLink + mPages); i++) {
			oLink = addFromTo(mUrl, i * mByPage - mByPage + 1, i * mByPage);
			oResult.getPages().getPage().add(newPage(i, oLink));
		}

		// Next & Last
		if (oCurrentPage < oNbPages) {
			oLink = addFromTo(mUrl, mByPage * (oCurrentPage + 1 - 1) + 1,
					mByPage * (oCurrentPage + 1));
			oResult.setNextPage(newStaticPage(oLink));
			oLink = addFromTo(mUrl, (oNbPages - 1) * mByPage + 1, mByPage
					* oNbPages);
			oResult.setLastPage(newStaticPage(oLink));
		}

		return oResult;
	}

	private Page newPage(int pNumber, String pLink) throws JspException {
		Page oResult = new Page();
		oResult.setN(pNumber);
		oResult.setUrl(TagUtil.rewriteURL(pLink, pageContext));
		return oResult;
	}

	private StaticPage newStaticPage(String pLink) throws JspException {
		StaticPage oResult = new StaticPage();
		oResult.setUrl(TagUtil.rewriteURL(pLink, pageContext));
		return oResult;
	}

	private String addFromTo(String pURL, int pFrom, int pTo) {
		String oResult = pURL;
		if (pFrom <= 1 && !mUseFromToOnFirstPage)
			return oResult;
		if (oResult.indexOf(QUESTION_MARK) < 0)
			oResult += QUESTION_MARK;
		oResult += AMPERSAND + mFromParamName + EQUALS + pFrom + AMPERSAND
				+ mToParamName + EQUALS + pTo;
		return oResult;
	}

	public int getFrom() {
		return mFrom;
	}

	public void setFrom(int pFrom) {
		mFrom = pFrom;
	}

	public int getTotal() {
		return mTotal;
	}

	public void setTotal(int pTotal) {
		mTotal = pTotal;
	}

	public int getByPage() {
		return mByPage;
	}

	public void setByPage(int pByPage) {
		mByPage = pByPage;
	}

	public int getPages() {
		return mPages;
	}

	public void setPages(int pPages) {
		mPages = pPages;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String pUrl) {
		mUrl = pUrl;
	}

	public String getFromParamName() {
		return mFromParamName;
	}

	public void setFromParamName(String pFromParamName) {
		mFromParamName = pFromParamName;
	}

	public String getToParamName() {
		return mToParamName;
	}

	public void setToParamName(String pToParamName) {
		mToParamName = pToParamName;
	}

	public boolean isUseFromToOnFirstPage() {
		return mUseFromToOnFirstPage;
	}

	public void setUseFromToOnFirstPage(boolean pUseFromToOnFirstPage) {
		mUseFromToOnFirstPage = pUseFromToOnFirstPage;
	}
}

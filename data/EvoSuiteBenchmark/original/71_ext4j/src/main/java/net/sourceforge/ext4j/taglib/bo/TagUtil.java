/**
 * @author luc
 */
package net.sourceforge.ext4j.taglib.bo;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

/**
 * @author luc
 * 
 */
public class TagUtil {

	private static final String SLASH = "/";

	/**
	 * (from ImportTag, standard taglib 1.0, apache)
	 * <p>
	 * Valid characters in a scheme.
	 * </p>
	 * <p>
	 * RFC 1738 says the following:
	 * </p>
	 * <blockquote> Scheme names consist of a sequence of characters. The lower
	 * case letters "a"--"z", digits, and the characters plus ("+"), period
	 * ("."), and hyphen ("-") are allowed. For resiliency, programs
	 * interpreting URLs should treat upper case letters as equivalent to lower
	 * case in scheme names (e.g., allow "HTTP" as well as "http").
	 * </blockquote>
	 * <p>
	 * We treat as absolute any URL that begins with such a scheme name,
	 * followed by a colon.
	 * </p>
	 */
	public static final String VALID_SCHEME_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+.-";

	/**
	 * (from ImportTag, standard taglib 1.0, apache) Returns <tt>true</tt> if
	 * our current URL is absolute, <tt>false</tt> otherwise.
	 */
	public static boolean isAbsoluteUrl(String url) {
		// a null URL is not absolute, by our definition
		if (url == null)
			return false;

		// do a fast, simple check first
		int colonPos;
		if ((colonPos = url.indexOf(":")) == -1)
			return false;

		// if we DO have a colon, make sure that every character
		// leading up to it is a valid scheme character
		for (int i = 0; i < colonPos; i++)
			if (VALID_SCHEME_CHARS.indexOf(url.charAt(i)) == -1)
				return false;

		// if so, we've got an absolute url
		return true;
	}

	public static String resolveUrl(String pURL, String pContext, PageContext pPageContext) throws JspException {
		if (pPageContext == null) return pURL;
		
		// don't touch absolute URLs
		if (isAbsoluteUrl(pURL))
			return pURL;

		// normalize relative URLs against a context root
		HttpServletRequest request = (HttpServletRequest) pPageContext
				.getRequest();
		if (pContext == null) {
			if (pURL.startsWith(SLASH)) {
				return (request.getContextPath() + pURL);
			} else {
				return pURL;
			}
		} else {
			if (!pContext.startsWith(SLASH) || !pURL.startsWith(SLASH))
				throw new JspTagException("Invalid context");
			return (pContext + pURL);
		}
	}

	public static String rewriteURL(String pURL, PageContext pPageContext) throws JspException {
		if (pPageContext == null) return pURL;
		
		// add (already encoded) parameters
		String oBaseUrl = resolveUrl(pURL, null, pPageContext);
		String oResult = oBaseUrl;
		// String oResult = params.aggregateParams(baseUrl);

		// if the URL is relative, rewrite it
		if (!isAbsoluteUrl(oResult)) {
			HttpServletResponse response = ((HttpServletResponse) pPageContext
					.getResponse());
			oResult = response.encodeURL(oResult);
		}
		return oResult;
	}

	public static class ParamManager {

		private static final String QUESTION_MARK = "?";
		private static final String AMPERSAND = "&";
		private static final String EMPTY = "";
		private List<String> mNames = new LinkedList<String>();
		private List<String> mValues = new LinkedList<String>();
		private boolean mDone = false;

		/**
		 * Adds a new parameter to the list.
		 * 
		 * @param pName
		 * @param pValue
		 */
		public void addParameter(String pName, String pValue) {
			if (mDone)
				throw new IllegalStateException();
			if (pName != null) {
				mNames.add(pName);
				if (pValue != null)
					mValues.add(pValue);
				else
					mValues.add(EMPTY);
			}
		}

		/**
		 * Produces a new URL with the stored parameters, in the appropriate
		 * order.
		 */
		public String aggregateParams(String pURL) {
			/*
			 * Since for efficiency we're destructive to the param lists, we
			 * don't want to run multiple times.
			 */
			if (mDone)
				throw new IllegalStateException();
			mDone = true;

			// // reverse the order of our two lists
			// Collections.reverse(this.names);
			// Collections.reverse(this.values);

			// build a string from the parameter list
			StringBuffer oNewParams = new StringBuffer();
			for (int i = 0; i < mNames.size(); i++) {
				oNewParams.append(mNames.get(i) + "=" + mValues.get(i));
				if (i < (mNames.size() - 1))
					oNewParams.append(AMPERSAND);
			}

			// insert these parameters into the URL as appropriate
			if (oNewParams.length() > 0) {
				int oQuestionMark = pURL.indexOf(QUESTION_MARK);
				if (oQuestionMark == -1) {
					return (pURL + QUESTION_MARK + oNewParams);
				} else {
					StringBuffer oWorkingUrl = new StringBuffer(pURL);
					oWorkingUrl.insert(oQuestionMark + 1,
							(oNewParams + AMPERSAND));
					return oWorkingUrl.toString();
				}
			} else {
				return pURL;
			}
		}
	}

}

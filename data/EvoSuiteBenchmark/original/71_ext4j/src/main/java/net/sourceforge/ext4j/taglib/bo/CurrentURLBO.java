/**
 * 
 */
package net.sourceforge.ext4j.taglib.bo;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.ext4j.taglib.bo.IRequest;
import net.sourceforge.ext4j.taglib.bo.Request;
import net.sourceforge.ext4j.taglib.bo.RequestParam;


/**
 * @author luc
 *
 */
public class CurrentURLBO {
	
	private static final String STRING_EMPTY = "";
	private static final String STRING_QMARK = "?";

	/* (non-Javadoc)
	 * @see com.tripfilms.web.module.IWebModule#register(javax.servlet.http.HttpServletRequest)
	 */
	public static IRequest getCurrentURL(HttpServletRequest pRequest) {
		String oURL = pRequest.getRequestURI() + ((pRequest.getQueryString() != null)?STRING_QMARK + pRequest.getQueryString():STRING_EMPTY);
		IRequest oReq = new Request(oURL);		
		return new RootRequest(oReq);
	}
	
	/**
	 * Returns clone to so we can keep using the same variable on the page.
	 * WARNING: calls to getParam():RequestParam won't return a clone and thus will affect the page variable.
	 * @author luc
	 */
	static class RootRequest implements IRequest  {
		
		private IRequest mRoot;
		
		public RootRequest(IRequest pOriginal) {
			mRoot = pOriginal;
		}

		public IRequest addParam(String pName, String pValue) {
			return mRoot.clone().addParam(pName, pValue);
		}

		public IRequest cleanEmptyParams() {
			return mRoot.cleanEmptyParams();
		}

		public String getBaseURL() {
			return mRoot.getBaseURL();
		}

		public String getHashValue() {
			return mRoot.getHashValue();
		}

		public RequestParam getParam(String pName, boolean pIgnoreCase) {
			return mRoot.getParam(pName, pIgnoreCase);
		}

		public RequestParam getParam(String pName) {
			return mRoot.getParam(pName);
		}

		public Collection<RequestParam> getParams() {
			return mRoot.getParams();
		}

		public String getQueryString() {
			return mRoot.getQueryString();
		}

		public int getValue(String pName, int pDefault) {
			return mRoot.getValue(pName, pDefault);
		}

		public String getValue(String pName, String pDefault) {
			return mRoot.getValue(pName, pDefault);
		}

		public boolean isWebRequest() {
			return mRoot.isWebRequest();
		}

		public IRequest removeParam(String pName) {
			return mRoot.clone().removeParam(pName);
		}

		public IRequest setBaseURL(String pBaseURL) {
			return mRoot.clone().setBaseURL(pBaseURL);
		}

		public IRequest setHashValue(String pValue) {
			return mRoot.clone().setHashValue(pValue);
		}

		public IRequest setParam(RequestParam pParam) {
			return mRoot.clone().setParam(pParam);
		}

		public IRequest setParam(String pName, String pValue) {
			return mRoot.clone().setParam(pName, pValue);
		}

		public IRequest toBaseURL() {
			return mRoot.toBaseURL();
		}

		public IRequest clone() {
			return mRoot;
		}
		
		@Override
		public String toString() {
			return mRoot.toString();
		}
	}
}

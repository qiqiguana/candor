package net.sourceforge.ext4j.taglib.bo;

import java.util.Collection;

public interface IRequest extends Cloneable {

	public abstract IRequest cleanEmptyParams();

	/**
	 * @return Returns the baseURL.
	 */
	public abstract String getBaseURL();

	/**
	 * @param pBaseURL The baseURL to set.
	 */
	public abstract IRequest setBaseURL(String pBaseURL);

	/**
	 * @return Returns the isWebRequest.
	 */
	public abstract boolean isWebRequest();

	public abstract Collection<RequestParam> getParams();

	public abstract RequestParam getParam(String pName);

	public abstract RequestParam getParam(String pName, boolean pIgnoreCase);

	public abstract IRequest toBaseURL();

	public abstract String getValue(String pName, String pDefault);

	public abstract int getValue(String pName, int pDefault);

	public abstract String getHashValue();

	public abstract IRequest setHashValue(String pValue);

	public abstract IRequest removeParam(String pName);

	public abstract IRequest addParam(String pName, String pValue);

	public abstract IRequest setParam(String pName, String pValue);

	public abstract IRequest setParam(RequestParam pParam);

	public abstract String getQueryString();

	public abstract String toString();

	public abstract boolean equals(Object pObj);

	public abstract int hashCode();

	public abstract IRequest clone();

}
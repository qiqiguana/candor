/*
 * This file is part of the QuickServer library 
 * Copyright (C) QuickServer.org
 *
 * Use, modification, copying and distribution of this software is subject to
 * the terms and conditions of the GNU Lesser General Public License. 
 * You should have received a copy of the GNU LGP License along with this 
 * library; if not, you can download a copy from <http://www.quickserver.org/>.
 *
 * For questions, suggestions, bug-reports, enhancement-requests etc.
 * visit http://www.quickserver.org
 *
 */
package org.quickserver.net.client;

import java.net.URL;
import java.net.UnknownHostException;

/**
 * @since 1.4.8
 * @author Akshathkumar Shetty
 */
public class HttpHost extends SocketBasedHost {
	private String uri = "/";
	private String httpStatusCode = "200";
	private URL url;
	
	public HttpHost() {}
	
	public HttpHost(String url) throws Exception {
		this(new URL(url));
	}
	
	public void setUrl(URL url) throws UnknownHostException {
		int port = url.getPort();
		this.url = url;
		
		if("https".equals(url.getProtocol())) {
			setSecure(true);
		}
		setUri(url.getPath());
		if(url.getQuery()!=null) {
			setUri(getUri()+"?"+url.getQuery());
		}
		
		if(port==-1) {
			if(isSecure()) {
				port = 443;
			} else {
				port = 80;
			}
		}
		
		setInetSocketAddress(url.getHost(), port);
	}
	
	public HttpHost(URL url) throws UnknownHostException {		
		setUrl(url);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(getName()!=null)sb.append(getName());
		sb.append("[");
		if(isSecure()) {
			sb.append("https://");
		} else {
			sb.append("http://");
		}
		sb.append(getInetSocketAddress());
		sb.append(getUri());
		sb.append("; Status:");
		sb.append(getStatus());
		sb.append("]");
		return sb.toString();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {		
		this.uri = uri;
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public URL getUrl() {
		return url;
	}	
}

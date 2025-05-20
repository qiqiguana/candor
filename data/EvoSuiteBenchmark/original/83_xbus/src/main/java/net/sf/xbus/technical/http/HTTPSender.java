package net.sf.xbus.technical.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.io.IOUtils;

/**
 * The <code>HTTPSender</code> sends a message to an URL.
 * <p>
 * For sending <code>SOAPMessages</code> there are two special features:
 * <ul>
 * <li>The HTTP Header contains an additional entry <code>SOAPAction</code>,
 * referencing the function.</li>
 * <li>When the other side sends a returncode other than <code>OK</code>,
 * the <code>HTTPSender</code> reads the error stream as the response.</li>
 * </ul>
 */
public class HTTPSender implements Sender, TextSender
{

	XBUSSystem mDestination = null;

	String mContentType = null;

	/**
	 * Stores the destination for later use.
	 */
	public HTTPSender(XBUSSystem destination)
	{
		mDestination = destination;
	}

	/**
	 * Sends the <code>callData</code> to the system. <code>function</code>
	 * is ignored.
	 * 
	 * @return <code>null</code>
	 */
	public String execute(String function, String callData) throws XException
	{
		PostMethod method = initialize(function, null);

		if (callData == null)
			callData = "";

		method.setRequestEntity(new StringRequestEntity(callData));
		// method.setRequestContentLength(callData.length());
		// method.setRequestBody(callData);

		return sendMessage(method);
	}

	protected PostMethod initialize(String function, String url)
			throws XException
	{
		Configuration config = Configuration.getInstance();

		/*
		 * HTTPParameterSender doesn`t have a Content-Type, all other
		 * HTTPxxxSenders must have that parameter
		 */
		if (!(this instanceof HTTPParameterSender))
		{
			mContentType = config.getValue(Constants.CHAPTER_SYSTEM,
					mDestination.getName(), "Content-Type");
		}

		/*
		 * If no URL is given, we must have it in the configuration
		 */
		if (url == null)
		{
			url = config.getValue(Constants.CHAPTER_SYSTEM, mDestination
					.getName(), "URL");
		}
		url = mDestination.replaceAllMarkers(url)[0];

		setKeystore(url);

		/*
		 * Self-signed certificates SHOULD NOT be used for productive systems
		 * due to security reasons, unless it is a concious decision and you are
		 * perfectly aware of security implications of accepting self-signed
		 * certificates.
		 */
		boolean allowSelfSignedCertificate = config.getValueAsBooleanOptional(
				Constants.CHAPTER_SYSTEM, mDestination.getName(),
				"AllowSelfSignedCertificate");
		if (allowSelfSignedCertificate)
		{
			Protocol.registerProtocol("https", new Protocol("https",
					new EasySSLProtocolSocketFactory(), 8443));
		}

		PostMethod method = new PostMethod(url);

		setRequestHeaders(function, method);

		return method;
	}

	protected void setRequestHeaders(String function, PostMethod method)
			throws XException
	{
		if (mContentType != null)
		{
			method.setRequestHeader("Content-Type", mContentType);
		}

		if (isSOAPMessage())
		{
			method.setRequestHeader("SOAPAction", function);
		}
	}

	protected String sendMessage(PostMethod method) throws XException
	{
		String response = null;
		int statusCode = 0;

		try
		{
			HttpClient client = new HttpClient();
			setAuthentication(client, method);
			statusCode = client.executeMethod(method);

			response = IOUtils.toString(method.getResponseBodyAsStream(),
					method.getResponseCharSet());

			method.releaseConnection();

			if (((statusCode < 200) || statusCode > 299) && (!isSOAPMessage()))
			{
				List params = new Vector();
				params.add(String.valueOf(statusCode));
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_HTTP, "1", params);
			}
		}
		catch (HttpException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_HTTP, "0", e);
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_HTTP, "0", e);
		}
		return response;
	}

	private boolean isSOAPMessage() throws XException
	{
		Configuration config = Configuration.getInstance();

		String message = config.getValue(Constants.CHAPTER_SYSTEM, mDestination
				.getName(), "Message");
		boolean isSOAPMessage = ("SOAPMessage".equals(message));

		return isSOAPMessage;
	}

	private void setKeystore(String urlName) throws XException
	{
		Configuration config = Configuration.getInstance();

		try
		{
			URL url = new URL(urlName);

			if ("https".equals(url.getProtocol())
					&& (System.getProperty("javax.net.ssl.trustStore") == null))
			{
				System.setProperty("javax.net.ssl.trustStore",
						Constants.XBUS_ETC
								+ config.getValue("Connection", "HTTP",
										"Keystore"));
			}
		}
		catch (MalformedURLException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_HTTP, "0", e);
		}
	}

	private void setAuthentication(HttpClient client, HttpMethod method)
			throws XException
	{
		Configuration config = Configuration.getInstance();
		String user = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "User");
		if (user != null)
		{
			String realm = config.getValueOptional(Constants.CHAPTER_SYSTEM,
					mDestination.getName(), "Realm");
			String password = config.getValueOptional(Constants.CHAPTER_SYSTEM,
					mDestination.getName(), "Password");
			HttpState state = client.getState();
			state.setCredentials(
					new AuthScope(client.getHostConfiguration().getHost(),
							client.getHostConfiguration().getPort(), realm),
					new UsernamePasswordCredentials(user, password));
			HttpClientParams params = new HttpClientParams();
			params.setAuthenticationPreemptive(config.getValueAsBooleanOptional(
					Constants.CHAPTER_SYSTEM, mDestination.getName(),
					"AuthenticationPreemptive"));
			client.setParams(params);
		}
	}

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}

package net.sourceforge.jwbf.core.actions;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jwbf.JWBF;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.CookieException;
import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

/**
 * The main interaction class.
 *
 * @author Thomas Stock
 */
@Slf4j
public class HttpActionClient {

    private HttpClient client;

    private String path = "";

    private HttpHost host;

    private int prevHash;

    public HttpActionClient(final URL url) {
    }

    /**
     * @param client
     *            a
     * @param url
     *            like "http://host/of/wiki/"
     */
    public HttpActionClient(final HttpClient client, final URL url) {
    }

    /**
     * @param contentProcessable
     *            a
     * @return message, never null
     * @throws ActionException
     *             on problems with http, cookies and io
     * @throws ProcessException on inner problems
     */
    public synchronized String performAction(ContentProcessable contentProcessable) throws ActionException, ProcessException;

    private void modifyRequestParams(HttpRequestBase request, HttpAction httpAction);

    private String post(HttpRequestBase requestBase, ContentProcessable contentProcessable, HttpAction ha) throws IOException, CookieException, ProcessException;

    /**
     * Process a GET Message.
     *
     * @param requestBase
     *            a
     * @param cp
     *            a
     * @return a returning message, not null
     * @throws IOException on problems
     * @throws CookieException on problems
     * @throws ProcessException on problems
     */
    private String get(HttpRequestBase requestBase, ContentProcessable cp, HttpAction ha) throws IOException, CookieException, ProcessException;

    private HttpResponse execute(HttpRequestBase requestBase) throws IOException, ClientProtocolException, ProcessException;

    /**
     * Process a GET Message.
     * @param get
     *            a
     * @return a returning message, not null
     * @throws IOException on problems
     * @throws CookieException on problems
     * @throws ProcessException on problems
     */
    public byte[] get(Get get) throws IOException, CookieException, ProcessException;

    private Map<String, String> cookieTransform(List<Cookie> ca);

    /**
     * send the cookies to the logger.
     *
     * @param client
     *            a
     *            @deprecated is a bit too chatty
     */
    @Deprecated
    private void showCookies();

    private void debug(HttpUriRequest e, HttpAction ha, ContentProcessable cp);

    /**
     * @return the
     */
    public String getHostUrl();
}

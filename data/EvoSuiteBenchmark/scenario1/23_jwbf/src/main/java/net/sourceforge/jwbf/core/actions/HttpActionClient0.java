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

    public synchronized String performAction(ContentProcessable contentProcessable) throws ActionException, ProcessException {
        String out = "";
        while (contentProcessable.hasMoreMessages()) {
            HttpRequestBase httpRequest = null;
            try {
                HttpAction httpAction = contentProcessable.getNextMessage();
                final String request;
                if (path.length() > 1) {
                    request = path + httpAction.getRequest();
                } else {
                    request = httpAction.getRequest();
                }
                log.debug(request);
                if (httpAction instanceof Get) {
                    httpRequest = new HttpGet(request);
                    modifyRequestParams(httpRequest, httpAction);
                    // do get
                    out = get(httpRequest, contentProcessable, httpAction);
                } else if (httpAction instanceof Post) {
                    httpRequest = new HttpPost(request);
                    modifyRequestParams(httpRequest, httpAction);
                    // do post
                    out = post(httpRequest, contentProcessable, httpAction);
                }
            } catch (IOException e1) {
                throw new ActionException(e1);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                throw new ActionException(e2);
            }
        }
        return out;
    }
}

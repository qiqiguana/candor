package geo.google;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

public interface UserAgent{
    /**
     * Downloads the input url and returns its content as a string 
     * @param url
     * @return
     */
    public String getUrlAsString(String url) throws HttpException, IOException;
}
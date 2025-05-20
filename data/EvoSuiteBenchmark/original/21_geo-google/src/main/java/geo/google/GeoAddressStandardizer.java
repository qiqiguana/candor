package geo.google;

import geo.google.datamodel.GeoAddress;
import geo.google.datamodel.GeoCoordinate;
import geo.google.datamodel.GeoStatusCode;
import geo.google.datamodel.GeoUsAddress;
import geo.google.mapping.MappingUtils;
import geo.google.mapping.XmlMappingFunctor;
import geo.google.mapping.XmlToAddressFunctor;
import geo.google.mapping.XmlToUsAddressFunctor;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.io.IOUtils;

/**
 * Address Standardizer class. 
 * 
 * Note: The http connection is synchronized in this class! 
 * you need to create multiple standardizer if you need concurrency.
 * <br/>
 * This class provides a set of methods for standardizing an address.  
 * <br/>
 * <p>
 * Note that this class standardizes the input address by sending a http request to 
 * google's geocoder service (http://www.google.com/apis/maps/documentation/). 
 * This service requires an ApiKey which you need to sign up for before you can use this class.
 * </p>
 * <p>
 * There is a geocoding speed limit (from http://googlemapsapi.blogspot.com/2007/09/coming-soon-ip-based-geocode-limiting.html):
 * <p>
 * "In the coming week, the Maps API geocode limit will change from a key-based system to an IP-based system, with a new limit of 15,000 queries per day. If you're a developer with a website that's using client-side geocoding via the GClientGeocoder object, this change means that each of your website visitors will now be subject to their own 15K quota. However, if you're a developer using the HTTP geocoder, this change means that all the geocodes from your script will be subject to the same 15K quota (your web server will send the same IP to us with each geocode). We've made this change in our geocoder due to the number of developers who've had issues with the GClientGeocoder and going over quota in times of high mashup user volume." 
 * </p>
 * That means if you run at a rate faster than the equivalent of 
 * 15000 requests per day (5.769 seconds per request) <b>per IP address</b> for several minutes, then Google 
 * will block you for a day. <b>This class automatically enforces this limit by only sending out
 * request in 5.769 second interval</b>. You can change the value of this time interval via the constructor. 
 * </p>
 * <pre>
 *  long timeTilNextStart = _rateLimitInterval - ( System.currentTimeMillis() - _lastRequestTime);
 *  if(timeTilNextStart > 0){
 *      Thread.sleep(timeTilNextStart); //sleep for some time
 *  }
 *  _lastRequestTime = System.currentTimeMillis();
 * </pre>
 * For more information about this service, see http://www.google.com/apis/maps/index.html
 * @author jliang
 *
 */
public class GeoAddressStandardizer{
  private static final String BASE_URL = "http://maps.google.com/maps/geo?q={0}&output={1}&key={2}";
  private static final String XML = "xml", CSV = "csv";
  private String _apiKey;
  private long _rateLimitInterval = 5769L;
  private long _lastRequestTime = System.currentTimeMillis() - _rateLimitInterval;
  private HttpClientParams _httpClientParams = null;
  
  private static HttpConnectionManager _connectionManager = new MultiThreadedHttpConnectionManager();
  
  private UserAgent _userAgent = new UserAgent(){

    public String getUrlAsString(String url) throws HttpException, IOException{
        GetMethod get = null; 
        try {
           get = new GetMethod(url);
           get.setFollowRedirects(true);
           _httpClient.executeMethod(get);
           return IOUtils.toString(get.getResponseBodyAsStream(), get.getRequestCharSet());
        } finally {
           if (get != null){
               get.abort();
               get.releaseConnection();
           }
        }
    }
      
  };

  
  public void setUserAgent(UserAgent userAgent){
      _userAgent = userAgent;
  }
  
  /**
   * The httpClient in combination with the {@link MultiThreadedHttpConnectionManager} is
   * thread-safe. See: <a href="http://hc.apache.org/httpclient-3.x/threading.html">HttpClient - Threading</a>
   */
  private static HttpClient _httpClient = new HttpClient(_connectionManager);


  /**
   * Sets the {@link HttpConnectionManager} to be used for connecting to the geocoding service
   */
  public static synchronized void setConnectionManager(HttpConnectionManager manager) {
	_connectionManager = manager;
	_httpClient = new HttpClient(_connectionManager);
  }
   /**
   * Sets the {@link HttpClient} to be used for connecting to the geocoding service
   * @param client
   */
  public static synchronized void setHttpClient(HttpClient client) {
    _httpClient = client;
  }
/**
   * Parameters for controlling the http connection.
   * http://jakarta.apache.org/commons/httpclient/preference-api.html#HTTP_parameters
   * @return
   */
  public HttpClientParams getHttpClientParams() {
    return _httpClientParams;
  }
  public void setHttpClientParams(HttpClientParams httpClientParams) {
    _httpClientParams = httpClientParams;
    if(_httpClientParams != null && _httpClient != null){
    	_httpClient.setParams(_httpClientParams);
      }
  }
  /**
   * Register a google geocoding API key at 
   * http://www.google.com/apis/maps/signup.html
   */
  public GeoAddressStandardizer(String apiKey){
    _apiKey = apiKey;
  }
  /**
   * Register a google geocoding API key at 
   * http://www.google.com/apis/maps/signup.html
   */
  public GeoAddressStandardizer(String apiKey, long rateIntervalInMillis){
    this(apiKey);
    if(rateIntervalInMillis < 0){
      throw new IllegalArgumentException("rateInterval cannot be negative");
    }
    _rateLimitInterval = rateIntervalInMillis;
  }
  
  
  /**
   * Standardize an address using google's geocoding service;
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
   * @deprecated Use {@link #standardizeToGeoAddresses(String)} instead. This method only returns the first
   * return geocoded address, which is not always the best standardization. 
   */
  public GeoAddress standardizeToGeoAddress(GeoUsAddress usAddress) throws GeoException{
    return standardizeToGeoAddress(usAddress.toAddressLine());
  }
  /**
   * Standardize an address using google's geocoding service;
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
   * @deprecated Use {@link #standardizeToUsGeoAddresses(String)} instead. This method only returns the first
   * return geocoded address, which is not always the best standardization. 
   */
  public GeoUsAddress standardizeToGeoUsAddress(GeoUsAddress usAddress) throws GeoException{
    return standardizeToGeoUsAddress(usAddress.toAddressLine());
  }
  /**
   * Standardize an address using google's geocoding service; 
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
   * @deprecated Use {@link #standardizeToGeoAddresses(String)} instead. This method only returns the first
   * return geocoded address, which is not always the best standardization. 
   */
  public GeoAddress standardizeToGeoAddress(String addressLine) throws GeoException{
    List<GeoAddress> ret = standardize(addressLine, XmlToAddressFunctor.getInstance()); 
    return CollectionUtils.isEmpty(ret)?null:ret.get(0);
  }
  /**
   * Standardize an address using google's geocoding service; 
   * <br/>
   * This method returns the <b>FIRST</b> returned geocoded address. 
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
   * @deprecated Use {@link #standardizeToGeoUsAddresses(String)} instead. This method only returns the first
   * return geocoded address, which is not always the best standardization.
   */
  public GeoUsAddress standardizeToGeoUsAddress(String addressLine) throws GeoException{
    List<GeoUsAddress> ret = standardize(addressLine, XmlToUsAddressFunctor.getInstance()); 
    return CollectionUtils.isEmpty(ret)?null:ret.get(0);
  }
  /**
   * Standardize an address using google's geocoding service;
   * @param addressLine
   * @return zero or more {@link GeoAddress} objects in a {@link List}. 
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
   */
  public List<GeoUsAddress> standardizeToGeoUsAddresses(String addressLine) throws GeoException{
    return standardize(addressLine, XmlToUsAddressFunctor.getInstance());
  }
  /**
   * Standardize an address using google's geocoding service;
   * @param addressLine
   * @return zero or more {@link GeoAddress} objects in a {@link List}. 
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
   */
  public List<GeoAddress> standardizeToGeoAddresses(String addressLine) throws GeoException{
    return standardize(addressLine, XmlToAddressFunctor.getInstance());
  }
  /**
   * Standardize an address using google's geocoding service;
   * @param addressLine
   * @return zero or more {@link GeoAddress} objects in a {@link List}. 
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
   */
  public List<GeoUsAddress> standardizeToGeoUsAddresses(GeoUsAddress usAddress) throws GeoException{
    return standardize(usAddress.toAddressLine(), XmlToUsAddressFunctor.getInstance());
  }
  /**
   * Standardize an address using google's geocoding service;
   * @param addressLine
   * @return zero or more {@link GeoAddress} objects in a {@link List}. 
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
   */
  public List<GeoAddress> standardizeToGeoAddresses(GeoUsAddress usAddress) throws GeoException{
    return standardize(usAddress.toAddressLine(), XmlToAddressFunctor.getInstance());
  }  
  /**
   * Standardize an address using google's geocoding service;
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request. 
   */
  public GeoCoordinate standardizeToGeoCoordinate(String addressLine) throws GeoException{
    try {
      HttpURL url = new HttpURL(MessageFormat.format(BASE_URL, addressLine, CSV, _apiKey));
      String res = getServerResponse(url.toString());
      return MappingUtils.stringToCoordinate(res);
    }
    catch (RuntimeException re){
      throw re;
    }catch (GeoException e) {
      throw e;
    }catch (Exception e) {
      throw new GeoException(e.getMessage());
    }
  }
  /**
   * Standardize an address using google's geocoding service;
   * @param mappingFunction - a mapping function that converts the kml string returned by google's 
   * geocoding service to any other object type.
   * @throws GeoException Indicates something unexpected occurs.
   * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request. 
   */
  public <ReturnType> ReturnType standardize(String addressLine, 
          XmlMappingFunctor<ReturnType> mappingFunction) throws GeoException{
    try {
      HttpURL url = new HttpURL(MessageFormat.format(BASE_URL, addressLine, XML, _apiKey));
      String res = getServerResponse(url.toString());
      return mappingFunction.execute(res);
    }
    catch (RuntimeException re){
      throw re;
    }catch (GeoException e) {
      throw e;
    }catch (Exception e) {
      throw new GeoException(e.getMessage());
    }
  }
  private synchronized String getServerResponse(String url) throws Exception{	  
    GetMethod get = null; 
    try {
       long timeTilNextStart = _rateLimitInterval - ( System.currentTimeMillis() - _lastRequestTime);
       if(timeTilNextStart > 0){
          Thread.sleep(timeTilNextStart); //sleep for some time
       }
       _lastRequestTime = System.currentTimeMillis();
       return _userAgent.getUrlAsString(url);
    } finally {
       if (get != null) get.releaseConnection();
    }
  }
  
  public String getApiKey() {
    return _apiKey;
  }

  public void setApiKey(String apiKey) {
    _apiKey = apiKey;
  }

  public long getRateLimitInterval() {
    return _rateLimitInterval;
  }

  public void setRateLimitInterval(long rateLimitInterval) {
    _rateLimitInterval = rateLimitInterval;
  }
  
  
}

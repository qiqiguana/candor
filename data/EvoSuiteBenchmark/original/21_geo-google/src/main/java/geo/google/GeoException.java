package geo.google;

import geo.google.datamodel.GeoStatusCode;
/**
 * Indicates something unexpected occurs.
 * It also includes a {@link GeoStatusCode} to signal problems about the status of the geocoding request.
 * @author jliang
 *
 */
public class GeoException extends Exception{
  private static final long serialVersionUID = 1L;

  private GeoStatusCode _status = GeoStatusCode.G_GEO_UNKOWN_STATUS;

  public GeoStatusCode getStatus() {
    return _status;
  }
  public GeoException(String message, GeoStatusCode status){
    super(message);
    _status = status;
  }
  
  public GeoException(String message, Throwable cause, GeoStatusCode status){
    super(message, cause);
    _status = status;
  }
  
  public GeoException(String message) {
    super(message);
  }
  
  public GeoException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public GeoException(Throwable cause) {
    super(cause);
  }
}
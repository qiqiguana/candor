package geo.google.datamodel;
/**
 * GGeoStatusCode Enum class
 * http://www.google.com/apis/maps/documentation/reference.html#GGeoStatusCode
 * @author jliang
 *
 */
public enum GeoStatusCode{
  G_GEO_SUCCESS(200, "No errors occurred; the address was successfully parsed and its geocode has been returned. (Since 2.55)"),
  G_GEO_BAD_REQUEST (400, "A directions request could not be successfully parsed. (Since 2.81)"),
  G_GEO_SERVER_ERROR (500, "A geocoding or directions request could not be successfully processed, yet the exact reason for the failure is not known. (Since 2.55)"),
  G_GEO_MISSING_QUERY (601, "The HTTP q parameter was either missing or had no value. For geocoding requests, this means that an empty address was specified as input. For directions requests, this means that no query was specified in the input. (Since 2.81)"),
  G_GEO_UNKNOWN_ADDRESS (602, "No corresponding geographic location could be found for the specified address. This may be due to the fact that the address is relatively new, or it may be incorrect. (Since 2.55)"),
  G_GEO_UNAVAILABLE_ADDRESS (603, "The geocode for the given address or the route for the given directions query cannot be returned due to legal or contractual reasons. (Since 2.55)"),
  G_GEO_UNKNOWN_DIRECTIONS (604, "The GDirections object could not compute directions between the points mentioned in the query. This is usually because there is no route available between the two points, or because we do not have data for routing in that region. (Since 2.81)"),
  G_GEO_BAD_KEY (610,  "The given key is either invalid or does not match the domain for which it was given. (Since 2.55)"),
  G_GEO_TOO_MANY_QUERIES(620, "The given key has gone over the requests limit in the 24 hour period. "),
  G_GEO_UNKOWN_STATUS (-1,  "Uknown Status");
  
  private String _description;
  private int _code;
  private GeoStatusCode(int code, String description){
    _code = code;
    _description = description;
  }
  public int getCode() {
    return _code;
  }
  public String getCodeName() {
    return this.name();
  }
  public String getDescription() {
    return _description;
  }
  /**
   * Retrun the corresponding GGeoStatusCode based from the input code.
   */
  public static GeoStatusCode getStatusCode(int code){
    switch (code) {
      case 200:
        return G_GEO_SUCCESS;
      case 400:
        return G_GEO_BAD_REQUEST;
      case 500:
        return G_GEO_SERVER_ERROR;
      case 601:
        return G_GEO_MISSING_QUERY;
      case 602:
        return G_GEO_UNKNOWN_ADDRESS;
      case 603:
        return G_GEO_UNAVAILABLE_ADDRESS;
      case 604:
        return G_GEO_UNKNOWN_DIRECTIONS;
      case 610:
        return G_GEO_BAD_KEY;
      case 620:
        return G_GEO_TOO_MANY_QUERIES; 
      default:
        return G_GEO_UNKOWN_STATUS;
    }
  }
}
package geo.google.datamodel;

/**
 * An attribute indicating how accurately we were able to geocode the given address.
 * @see http://www.google.com/apis/maps/documentation/reference.html#GGeoAddressAccuracy
 * @author jliang
 *
 */
public enum GeoAddressAccuracy{
  UNKNOWN_LOCATION(0),
  COUNTRY_LEVEL(1),
  REGION_LEVEL(2),
  SUB_REGION_LEVEL(3),
  TOWN_LEVEL(4),
  POST_CODE_LEVEL(5),
  STREET_LEVEL(6),
  INTERSECTION_LEVEL(7),
  ADDRESS_LEVEL(8);
  private int _code = -1;
  private GeoAddressAccuracy(int code){
    _code = code;
  }
  public int getCode() {
    return _code;
  }
  public String getName(){
    return this.name();
  }
  public static GeoAddressAccuracy getAccuracyByCode(int code){
    switch (code) {
      case 0:
        return UNKNOWN_LOCATION;
      case 1:
        return COUNTRY_LEVEL;
      case 2:
        return REGION_LEVEL;
      case 3:
        return SUB_REGION_LEVEL;
      case 4:
        return TOWN_LEVEL;
      case 5:
        return POST_CODE_LEVEL;
      case 6:
        return STREET_LEVEL;
      case 7:
        return INTERSECTION_LEVEL;
      case 8:
        return ADDRESS_LEVEL;        
      default:
        return UNKNOWN_LOCATION;
    }
  }
}
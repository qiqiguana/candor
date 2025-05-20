package geo.google.mapping;

import geo.google.GeoException;
import geo.google.datamodel.GeoAltitude;
import geo.google.datamodel.GeoCoordinate;
import geo.google.datamodel.GeoStatusCode;

public class MappingUtils{
  private MappingUtils(){}
  /**
   * Convert a string representation of {@link GeoCoordinate} to an {@link GeoCoordinate} object.
   * @param coord - this must be in the format of statusCode(optional), latitude, longitude, altitude
   * @return - null if <code>coord</code> is null.
   * @throws GeoException 
   */
  public static GeoCoordinate stringToCoordinate(String coord) throws GeoException{
    if(coord == null){
      return new GeoCoordinate();
    }
    String[] items = coord.split(",");
    if(items.length == 3){
      GeoCoordinate ret = new GeoCoordinate(Double.parseDouble(items[0]), 
              Double.parseDouble(items[1]),
              new GeoAltitude(Double.parseDouble(items[2])));
      return ret;
    }else if(items.length == 4){
      int code = Integer.parseInt(items[0]);
      GeoStatusCode status = GeoStatusCode.getStatusCode(code);
      if(status != GeoStatusCode.G_GEO_SUCCESS){
    	  throw new GeoException("Error Status Code: "+status.getCodeName(), status);
      }else{
    	  return new GeoCoordinate(Double.parseDouble(items[3]), 
                  Double.parseDouble(items[2]), new GeoAltitude());
      }
    }else{
      throw new GeoException("Standardizer received unknown value: "+coord);
    }
  }
}
package geo.google.mapping;

import geo.google.GeoException;
import geo.google.datamodel.GeoAltitude;
import geo.google.datamodel.GeoCoordinate;
import geo.google.datamodel.GeoAltitude.GeoAltitudeMode;

import com.google.earth.kml._2.AltitudeModeType;
import com.google.earth.kml._2.PointType;

/**
 * Mapping function that maps a {@link PointType} object to a {@link GeoCoordinate} object.
 * @author jliang
 *
 */
public final class PointToCoordinateFunctor implements Functor<GeoCoordinate, PointType, GeoException>{
  private static final PointToCoordinateFunctor _instance = new PointToCoordinateFunctor ();
  public static PointToCoordinateFunctor getInstance(){
    return _instance;
  }
  public GeoCoordinate execute(PointType point) throws GeoException {
    GeoCoordinate ret = new GeoCoordinate();
    String coor = point.getCoordinates();
    double alt = -1d;
    if(coor != null){
      ret = MappingUtils.stringToCoordinate(coor);
    }
    AltitudeModeType mode = point.getAltitudeMode();
    GeoAltitude altitude = new GeoAltitude(alt, GeoAltitudeMode.CLAMP_TO_GROUND);
    if(mode != null){
      switch (mode) {
        case CLAMP_TO_GROUND:
          altitude = new GeoAltitude(alt, GeoAltitudeMode.CLAMP_TO_GROUND);
          break;
        case RELATIVE_TO_GROUND:
          altitude = new GeoAltitude(alt, GeoAltitudeMode.RELATIVE_TO_GROUND);
          break;
        case ABSOLUTE:
          altitude = new GeoAltitude(alt, GeoAltitudeMode.ABSOLUTE);
          break;          
        default:
          altitude = new GeoAltitude(alt, GeoAltitudeMode.CLAMP_TO_GROUND);
          break;
      }
    }
    ret.setAltitude(altitude);
    
    return ret;
  }
  
}
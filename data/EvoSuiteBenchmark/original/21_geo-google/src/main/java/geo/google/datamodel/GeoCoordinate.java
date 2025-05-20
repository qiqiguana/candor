package geo.google.datamodel;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * A geographic location defined by longitude, latitude, and (optional) altitude.
 * {@link http://code.google.com/apis/kml/documentation/kml_tags_21.html} 
 * @author jliang
 *
 */
public class GeoCoordinate implements Serializable, Cloneable{
  private static final long serialVersionUID = 1060639455041268729L;
  private double _latitude = -1d;
  private double _longitude = -1d;
  private GeoAltitude _altitude = new GeoAltitude();

  public GeoCoordinate() {}
  
  public GeoCoordinate(double longitude, double latitude, GeoAltitude altitude) {
    _latitude = latitude;
    _longitude = longitude;
    _altitude = altitude;
  }
  public double getLatitude() {
    return _latitude;
  }
  public void setLatitude(double latitude) {
    _latitude = latitude;
  }
  public double getLongitude() {
    return _longitude;
  }
  public void setLongitude(double longitude) {
    _longitude = longitude;
  }
  /**
   * Altitude in meters above sea level.
   * @return
   */
  public GeoAltitude getAltitude() {
    return _altitude;
  }
  public void setAltitude(GeoAltitude altitude) {
    _altitude = altitude;
  }
  
  /**
   * Returns the distance (in miles) between this geo coordinate and another geo coordinate.
   * (This uses spherical law of cosines formula in {@link GeoUtils} )
   * <br>
   * Note the altitude is not being considered.
   */
  public double distanceTo(GeoCoordinate other){
    return GeoUtils.distanceBetweenInMiles(this, other);
  }
  @Override
  public GeoCoordinate clone(){
   return new GeoCoordinate(_longitude, _latitude, _altitude.clone());
  }
  
  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
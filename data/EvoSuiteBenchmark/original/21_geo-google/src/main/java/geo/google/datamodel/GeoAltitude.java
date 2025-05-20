package geo.google.datamodel;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Altitude class.
 * @author jliang
 *
 */
public class GeoAltitude implements Serializable, Cloneable{
  private static final long serialVersionUID = 1646700721957062689L;
  private double _altitude = 0;
  private GeoAltitudeMode _mode = GeoAltitudeMode.CLAMP_TO_GROUND;
  public GeoAltitude() {}
  public GeoAltitude(double altitude){
    _altitude = altitude;
  }
  public GeoAltitude(double altitude, GeoAltitudeMode mode) {
    this(altitude);
    _mode = mode;
  }

  /**
   * Meters above sea level.
   * @return
   */
  public double getAltitude() {
    return _altitude;
  }

  public void setAltitude(double altitude) {
    _altitude = altitude;
  }
  /**
   * Specifies how altitude components in the <coordinates> element are interpreted. Possible values are
   * clampToGround - (default) Indicates to ignore an altitude specification (for example, in the <coordinates> tag).
   * relativeToGround - Sets the altitude of the element relative to the actual ground elevation of a particular location. For example, if the ground elevation of a location is exactly at sea level and the altitude for a point is set to 9 meters, then the elevation for the icon of a point placemark elevation is 9 meters with this mode. However, if the same coordinate is set over a location where the ground elevation is 10 meters above sea level, then the elevation of the coordinate is 19 meters. A typical use of this mode is for placing telephone poles or a ski lift.
   * absolute - Sets the altitude of the coordinate relative to sea level, regardless of the actual elevation of the terrain beneath the element. For example, if you set the altitude of a coordinate to 10 meters with an absolute altitude mode, the icon of a point placemark will appear to be at ground level if the terrain beneath is also 10 meters above sea level. If the terrain is 3 meters above sea level, the placemark will appear elevated above the terrain by 7 meters. A typical use of this mode is for aircraft placement.
   * {@link GeoAltitude.GeoAltitudeMode}
   * @return
   */
  public GeoAltitudeMode getMode() {
    return _mode;
  }

  public void setMode(GeoAltitudeMode mode) {
    _mode = mode;
  }
  public enum GeoAltitudeMode{
    //default
    CLAMP_TO_GROUND("(default) Indicates to ignore an altitude specification"),
    RELATIVE_TO_GROUND("Sets the altitude of the element relative to the actual ground elevation of a particular location. For example, if the ground elevation of a location is exactly at sea level and the altitude for a point is set to 9 meters, then the elevation for the icon of a point placemark elevation is 9 meters with this mode. However, if the same coordinate is set over a location where the ground elevation is 10 meters above sea level, then the elevation of the coordinate is 19 meters. A typical use of this mode is for placing telephone poles or a ski lift."),
    ABSOLUTE("Sets the altitude of the coordinate relative to sea level, regardless of the actual elevation of the terrain beneath the element. For example, if you set the altitude of a coordinate to 10 meters with an absolute altitude mode, the icon of a point placemark will appear to be at ground level if the terrain beneath is also 10 meters above sea level. If the terrain is 3 meters above sea level, the placemark will appear elevated above the terrain by 7 meters. A typical use of this mode is for aircraft placement.");
    private String _description;
    private GeoAltitudeMode(String desc){
      _description = desc;
    }
    public String getName(){
      return CLAMP_TO_GROUND.name();
    }
    public String getDescription(String description) {
      return _description;
    }
    
  }
  @Override
  public GeoAltitude clone(){
   return new GeoAltitude(_altitude, _mode);
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
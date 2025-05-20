package geo.google.datamodel;


import java.io.Serializable;

import oasis.names.tc.ciq.xsdschema.xal._2.AddressDetails;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * Data class for address. 
 * @author jliang
 *
 */
public class GeoAddress implements Serializable{
  private static final long serialVersionUID = 2701695885973529100L;
  private GeoCoordinate _coordinate;
  private AddressDetails _addressDetails;
  private String _addressLine = StringUtils.EMPTY;
  private GeoAddressAccuracy _accuracy = GeoAddressAccuracy.UNKNOWN_LOCATION;
  
  /**
   * A structured address, formatted as xAL, or eXtensible Address Language, 
   * an international standard for address formatting. 
   * http://www.oasis-open.org/committees/ciq/ciq.html#6
   * @return
   */
  public AddressDetails getAddressDetails() {
    return _addressDetails;
  }
  public void setAddressDetails(AddressDetails addressDetails) {
    _addressDetails = addressDetails;
  }
  /**
   * A string value representing an unstructured address written as a standard street, 
   * city, state address, and/or as a postal code. 
   * @return
   */
  public String getAddressLine() {
    return _addressLine;
  }
  public void setAddressLine(String addressLine) {
    _addressLine = addressLine;
  }
  /**
   * A geographic location defined by longitude, latitude, and (optional) altitude.
   * @return
   */
  public GeoCoordinate getCoordinate() {
    return _coordinate;
  }
  public void setCoordinate(GeoCoordinate coordinate) {
    _coordinate = coordinate;
  }
  public GeoAddressAccuracy getAccuracy() {
    return _accuracy;
  }
  public void setAccuracy(GeoAddressAccuracy accuracy) {
    _accuracy = accuracy;
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
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}

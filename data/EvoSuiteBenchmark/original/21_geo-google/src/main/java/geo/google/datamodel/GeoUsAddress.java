package geo.google.datamodel;


import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * A simple data class for US address info.
 * @see GeoAddress
 * 
 * @author jliang
 *
 */
public class GeoUsAddress implements Serializable{
  private static final long serialVersionUID = 2701695885973529100L;
  private String _addressLine1 = StringUtils.EMPTY;
  private String _addressLine2 = StringUtils.EMPTY;
  private String _city = StringUtils.EMPTY;
  private String _county = StringUtils.EMPTY;
  private String _state = StringUtils.EMPTY;
  private String _postalCode = StringUtils.EMPTY;
  private String _country = StringUtils.EMPTY;
  private GeoCoordinate _coordinate = new GeoCoordinate();
  private GeoAddressAccuracy _accuracy = GeoAddressAccuracy.UNKNOWN_LOCATION;
  
  public GeoUsAddress() {
  }

  public GeoUsAddress(String addressLine1, String addressLine2, String city, 
          String county, String state, String postalCode, String country, 
          GeoCoordinate coordinate, GeoAddressAccuracy accuracy) {
    _addressLine1 = addressLine1;
    _addressLine2 = addressLine2;
    _city = city;
    _county = county;
    _state = state;
    _postalCode = postalCode;
    _country = country;
    _coordinate = coordinate;
  }

  public String getAddressLine1() {
    return _addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    _addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return _addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    _addressLine2 = addressLine2;
  }

  public String getCity() {
    return _city;
  }

  public void setCity(String city) {
    _city = city;
  }

  public String getCounty() {
    return _county;
  }

  public void setCounty(String county) {
    _county = county;
  }

  public String getPostalCode() {
    return _postalCode;
  }

  public void setPostalCode(String postalCode) {
    _postalCode = postalCode;
  }

  public String getState() {
    return _state;
  }

  public void setState(String state) {
    _state = state;
  }
  public String getCountry() {
    return _country;
  }

  public void setCountry(String country) {
    _country = country;
  }

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
  
  /**
   * Return a single line representation of this address in the format of:
   * <pre>
   * &lt;addressLine1&gt;, &lt;city&gt;, &lt;state&gt; &lt;postalCode&gt;
   * </pre>
   * The standardizer will send this address line string to google's geocode service. 
   * Extend this class and override this method if it needs to be encoded differently. 
   */
  public String toAddressLine(){
    StringBuilder sb = new StringBuilder();
    sb.append(_addressLine1)
      .append(", ")
      .append(_city)
      .append(", ")
      .append(_state)
      .append(" ")
      .append(_postalCode);
    return sb.toString();
  }
  
}

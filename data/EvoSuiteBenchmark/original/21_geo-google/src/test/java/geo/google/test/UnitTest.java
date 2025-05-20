package geo.google.test;

import geo.google.GeoAddressStandardizer;
import geo.google.datamodel.GeoAddress;
import geo.google.datamodel.GeoCoordinate;
import geo.google.datamodel.GeoUsAddress;
import geo.google.datamodel.GeoUtils;
import geo.google.mapping.AddressToUsAddressFunctor;
import geo.google.mapping.FunctionChain;
import geo.google.mapping.Functor;
import geo.google.mapping.MappingUtils;
import geo.google.mapping.PointToCoordinateFunctor;
import geo.google.mapping.XmlToAddressFunctor;
import geo.google.mapping.XmlToUsAddressFunctor;
import geo.google.utils.XmlUtils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.google.earth.kml._2.KmlType;
import com.google.earth.kml._2.PointType;
/**
 * TODO: wirte more
 * 
 * @author jliang
 *
 */
@SuppressWarnings("deprecation")
public class UnitTest extends TestCase{
  @Test
  public void testMultipleAddresses() throws Exception{
    String xml2 = getXml2();
    assertTrue("expected 10 addresses", XmlToAddressFunctor.getInstance().execute(xml2).size()==10);
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testUnmarshalling() throws Exception{
    String xml = getXml();
    Unmarshaller u = JAXBContext.newInstance(XmlToAddressFunctor.JAXB_CONTEXT).createUnmarshaller();
    JAXBElement<KmlType> o = (JAXBElement<KmlType>)u.unmarshal(XmlUtils.stringToInputSource(xml));
    KmlType kml = o.getValue();
    assertNotNull(kml);
    assertNotNull(kml.getResponse());
    assertNotNull(kml.getResponse().getStatus());
    assertNotNull(kml.getResponse().getPlacemark());
    assertNotNull(kml.getResponse().getPlacemark().get(0).getAddressDetails());
    assertNotNull(kml.getResponse().getPlacemark().get(0).getAddress());
    assertNotNull(kml.getResponse().getPlacemark().get(0).getId());
    assertNotNull(kml.getResponse().getPlacemark().get(0).getPoint());
    assertNotNull(kml.getResponse().getPlacemark().get(0).getAddressDetails().getAccuracy());
    assertNotNull(kml.getResponse().getPlacemark().get(0).getAddressDetails().getCountry());
    assertNotNull(kml.getResponse().getPlacemark().get(0).getAddressDetails().getCountry().getAdministrativeArea());

    PointType point = kml.getResponse().getPlacemark().get(0).getPoint();
    GeoCoordinate coord = PointToCoordinateFunctor.getInstance().execute(point);
    assertNotNull(coord);
  }
  @Test
  public void testMapping() throws Exception{
    String xml = getXml();
    GeoAddress geoAddr = XmlToAddressFunctor.getInstance().execute(xml).get(0);
    assertNotNull(geoAddr);
    assertNotNull(geoAddr.getAddressDetails());
    assertNotNull(geoAddr.getAddressLine());
    assertNotNull(geoAddr.getCoordinate());
    assertNotNull(geoAddr.getAddressDetails().getAccuracy());
    assertNotNull(geoAddr.getAddressDetails().getCountry());
    assertNotNull(geoAddr.getAddressDetails().getCountry().getAdministrativeArea());
    
    GeoUsAddress usAddr = XmlToUsAddressFunctor.getInstance().execute(xml).get(0);
    GeoUsAddress usAddr2 = AddressToUsAddressFunctor.getInstance().execute(geoAddr);
    assertEquals(usAddr, usAddr2);
    
    GeoCoordinate coord = MappingUtils.stringToCoordinate("123, 123, 123");
    assertEquals(123d, coord.getLatitude());
    assertEquals(123d, coord.getLongitude());
    assertEquals(123d, coord.getAltitude().getAltitude());
  }
  
  @Test
  public void testFunctionChain() throws Exception{
    Functor<Integer, Integer, RuntimeException> add1 = new Functor<Integer, Integer, RuntimeException>(){
      public Integer execute(Integer arg) {
        return arg+1;
      }
    };
    
    FunctionChain<Integer> add2 = new FunctionChain<Integer>(new Functor[]{add1, add1});
    FunctionChain<Integer> add3 = new FunctionChain<Integer>(add1, add2);
    assertEquals(2, add1.execute(1).intValue());
    assertEquals(3, add2.execute(1).intValue());
    assertEquals(4, add3.execute(1).intValue());
  }
  
  @Test
  public void testGeoUtils() throws Exception{
    String xml = getXml();
    GeoUsAddress usAddr = XmlToUsAddressFunctor.getInstance().execute(xml).get(0);
    assertEquals(GeoUtils.distanceBetweenInKm(usAddr.getCoordinate(), usAddr.getCoordinate()), 0.0d);
    assertEquals(GeoUtils.distanceBetweenInMiles(usAddr.getCoordinate(), usAddr.getCoordinate()), 0.0d);
    assertEquals(GeoUtils.haversineDistanceBetweenInKm(usAddr.getCoordinate(), usAddr.getCoordinate()), 0.0d);
    assertEquals(GeoUtils.haversineDistanceBetweenInMiles(usAddr.getCoordinate(), usAddr.getCoordinate()), 0.0d);
  }
  
  @SuppressWarnings("deprecation")
  @Test
  public void testHttpRequest(){
     //XXX: This key is private, please do not use on your own project!!
    GeoAddressStandardizer st = new GeoAddressStandardizer("ABQIAAAA-tvs7mmRVTWDoWpueqmbxxQHzalAZ5Y6D1YaNOuTaGHJq0KnzhT3vzWvzhLyq7wL0dXsjFaoUxEc8g");
    GeoUsAddress addr;
    HttpClientParams params = new HttpClientParams();
    params.setSoTimeout((int)DateUtils.MILLIS_PER_MINUTE);
    try {
      addr = st.standardizeToGeoUsAddress("1600 Amphitheatre Parkway, california ");
      st.setHttpClientParams(params);
      assertEquals("94043", addr.getPostalCode());
      st.standardizeToGeoUsAddress("649 ROUTE 206,UNIT 20, , BELLE MEAD NJ");
    } catch (Exception e) {
      //XXX: this test requires a network connection, so it could fail
      e.printStackTrace();
      System.out.println("this test requires a network connection, so it could fail");
    }
    
    
    
  }

  @Test
  public void testHttpRequest3(){
    GeoAddressStandardizer st = new GeoAddressStandardizer("ABQIAAAA-tvs7mmRVTWDoWpueqmbxxQHzalAZ5Y6D1YaNOuTaGHJq0KnzhT3vzWvzhLyq7wL0dXsjFaoUxEc8g");
    HttpClientParams params = new HttpClientParams();
    params.setSoTimeout((int)DateUtils.MILLIS_PER_MINUTE);
    try {
      GeoCoordinate coord = st.standardizeToGeoCoordinate("Greenwich, United Kingdom");
      st.setHttpClientParams(params);
      assertNotNull(coord);
      assertEquals(51, Math.round(coord.getLatitude()));
      assertEquals(0, Math.round(coord.getLongitude()));
    } catch (Exception e) {
      //XXX: this test requires a network connection, so it could fail
      e.printStackTrace();
      System.out.println("this test requires a network connection, so it could fail");
    }  
  }
  
  private String getXml2() throws IOException{
    InputStream stream = null;
    try{
      stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("geo/google/test/instance2.xml");
      String xml = IOUtils.toString(stream);
      return xml; 
    }finally{
      IOUtils.closeQuietly(stream);
    }
  }
  
  private String getXml() throws IOException{
    InputStream stream = null;
    try{
      stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("geo/google/test/instance.xml");
      String xml = IOUtils.toString(stream);
      return xml; 
    }finally{
      IOUtils.closeQuietly(stream);
    }
  }
  
}
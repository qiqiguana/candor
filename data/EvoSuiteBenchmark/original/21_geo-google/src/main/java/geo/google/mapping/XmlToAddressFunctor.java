package geo.google.mapping;

import geo.google.GeoException;
import geo.google.datamodel.GeoAddress;
import geo.google.datamodel.GeoAddressAccuracy;
import geo.google.datamodel.GeoStatusCode;
import geo.google.utils.XmlUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;

import com.google.earth.kml._2.KmlType;
import com.google.earth.kml._2.PlacemarkType;
import com.google.earth.kml._2.ResponseType;

/**
 * Maps a kml string returns by google's geocode service to a {@link GeoAddress} object.
 * @author jliang
 *
 */
public final class XmlToAddressFunctor implements XmlMappingFunctor<List<GeoAddress>>{
  private static final XmlToAddressFunctor _instance = new XmlToAddressFunctor ();
  public static XmlToAddressFunctor getInstance(){
    return _instance;
  }
  public static final String JAXB_CONTEXT = "com.google.earth.kml._2";
  private static final Set<GeoStatusCode> SUCCESS = EnumSet.of(GeoStatusCode.G_GEO_SUCCESS);
  private static Unmarshaller _u = null;
  @SuppressWarnings("unchecked")
  public List<GeoAddress> execute(String xml) throws GeoException {
    List<GeoAddress> ret = Collections.EMPTY_LIST;
    try {
      Unmarshaller u = getUnmarshaller();
      JAXBElement<KmlType> o = (JAXBElement<KmlType>)u.unmarshal(XmlUtils.stringToInputSource(xml));
      ResponseType res = o.getValue().getResponse();
      GeoStatusCode status = GeoStatusCode.getStatusCode(res.getStatus().getCode());
      if(!SUCCESS.contains(status)){
        throw new GeoException("Error Status Code: "+status.getCodeName(), status);
      }
      List<PlacemarkType> placemarks = res.getPlacemark();
      
      if(CollectionUtils.isNotEmpty(placemarks)){
        ret = new ArrayList<GeoAddress>(placemarks.size());
        for(PlacemarkType placemark : placemarks){
          GeoAddress addr = new GeoAddress();
          addr.setAddressDetails(placemark.getAddressDetails());
          addr.setAddressLine(placemark.getAddress());
          addr.setCoordinate(PointToCoordinateFunctor.getInstance().execute(placemark.getPoint()));
          if(addr.getAddressDetails()!= null && addr.getAddressDetails().getAccuracy()!=null){
            addr.setAccuracy(GeoAddressAccuracy.getAccuracyByCode(addr.getAddressDetails().getAccuracy().intValue()));
          }
          ret.add(addr);
        }
      }
      return ret;
    }
    catch (JAXBException e) {
      throw new RuntimeException("Unable to parse xml string using JAXB", e);
    }
    
  }
  
  private Unmarshaller getUnmarshaller() throws JAXBException{
    if(_u==null){
      _u = JAXBContext.newInstance(JAXB_CONTEXT).createUnmarshaller();
    }
    return _u;
  }
}
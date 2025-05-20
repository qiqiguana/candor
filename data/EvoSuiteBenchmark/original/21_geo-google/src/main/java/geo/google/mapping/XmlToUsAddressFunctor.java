package geo.google.mapping;

import geo.google.GeoException;
import geo.google.datamodel.GeoAddress;
import geo.google.datamodel.GeoUsAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps a kml string returns by google's geocode service to a {@link GeoUsAddress} object.
 * @author jliang
 *
 */
public final class XmlToUsAddressFunctor implements XmlMappingFunctor<List<GeoUsAddress>>{
  private static final XmlToUsAddressFunctor _instance = new XmlToUsAddressFunctor ();
  public static XmlToUsAddressFunctor getInstance(){
    return _instance;
  }
  public List<GeoUsAddress> execute(String xml) throws GeoException {
    try{
      List<GeoAddress> geoAddrs = XmlToAddressFunctor.getInstance().execute(xml);
      List<GeoUsAddress> ret = new ArrayList<GeoUsAddress>(geoAddrs.size());
      for(GeoAddress geoAddr : geoAddrs){
        ret.add(AddressToUsAddressFunctor.getInstance().execute(geoAddr));
      }
      return ret;
    }catch (GeoException e) {
      throw e;
    }catch (Exception e) {
      throw new GeoException(e);
    }
  }
}
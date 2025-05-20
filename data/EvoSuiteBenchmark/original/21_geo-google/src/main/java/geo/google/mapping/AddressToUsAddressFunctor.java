package geo.google.mapping;

import geo.google.GeoException;
import geo.google.datamodel.GeoAddress;
import geo.google.datamodel.GeoUsAddress;

import java.util.List;

import oasis.names.tc.ciq.xsdschema.xal._2.AdministrativeArea;
import oasis.names.tc.ciq.xsdschema.xal._2.Locality;
import oasis.names.tc.ciq.xsdschema.xal._2.PostalCode;
import oasis.names.tc.ciq.xsdschema.xal._2.ThoroughfareNameType;
import oasis.names.tc.ciq.xsdschema.xal._2.AddressDetails.Country;
import oasis.names.tc.ciq.xsdschema.xal._2.AddressDetails.Country.CountryNameCode;
import oasis.names.tc.ciq.xsdschema.xal._2.AdministrativeArea.AdministrativeAreaName;
import oasis.names.tc.ciq.xsdschema.xal._2.AdministrativeArea.SubAdministrativeArea;
import oasis.names.tc.ciq.xsdschema.xal._2.AdministrativeArea.SubAdministrativeArea.SubAdministrativeAreaName;
import oasis.names.tc.ciq.xsdschema.xal._2.Locality.LocalityName;
import oasis.names.tc.ciq.xsdschema.xal._2.PostalCode.PostalCodeNumber;

import org.apache.commons.collections.CollectionUtils;

/**
 * Mapping function that maps a {@link GeoAddress} object to a {@link GeoUsAddress} object.
 * @author jliang
 *
 */
public final class AddressToUsAddressFunctor implements Functor<GeoUsAddress, GeoAddress, GeoException>{
  private static final AddressToUsAddressFunctor _instance = new AddressToUsAddressFunctor ();
  public static AddressToUsAddressFunctor getInstance(){
    return _instance;
  }
  public GeoUsAddress execute(GeoAddress addr) {
    GeoUsAddress ret = new GeoUsAddress();
    Country country = addr.getAddressDetails().getCountry();
    if(country != null){
      //country
      List<CountryNameCode> countryNames = country.getCountryNameCode();
      if(CollectionUtils.isNotEmpty(countryNames)){
        ret.setCountry(countryNames.get(0).getContent());
      }

      AdministrativeArea adminArea = country.getAdministrativeArea();
      if(adminArea != null){
        //  state
        List<AdministrativeAreaName> names = adminArea.getAdministrativeAreaName();
        if(CollectionUtils.isNotEmpty(names)){
          ret.setState(names.get(0).getContent());
        }
        SubAdministrativeArea subAdminArea = adminArea.getSubAdministrativeArea();
        Locality locality = subAdminArea == null ? adminArea.getLocality() : subAdminArea.getLocality();
        if(subAdminArea != null){
          //county
          List<SubAdministrativeAreaName> subNames = subAdminArea.getSubAdministrativeAreaName();
          if(CollectionUtils.isNotEmpty(subNames)){
            ret.setCounty(subNames.get(0).getContent());
          }
        }
        if(locality != null){
          List<LocalityName> localNames = locality.getLocalityName();
          if(CollectionUtils.isNotEmpty(localNames)){
            ret.setCity(localNames.get(0).getContent());
          }
          
          //postal code
          PostalCode postalCode = locality.getPostalCode();
          List<PostalCodeNumber> numbers = postalCode.getPostalCodeNumber();
          if(CollectionUtils.isNotEmpty(numbers)){
            ret.setPostalCode(numbers.get(0).getContent());
          }    
          if(locality.getThoroughfare() != null){
            //addresslines
            List<ThoroughfareNameType> thoroughfares = 
              locality.getThoroughfare().getThoroughfareName();
            if(CollectionUtils.isNotEmpty(thoroughfares)){
              ret.setAddressLine1(thoroughfares.get(0).getContent());
              if(thoroughfares.size() > 1){
                ret.setAddressLine2(thoroughfares.get(1).getContent());
              }
            }              
          }
        }        
      }
    }

    //need to clone
    if(addr.getCoordinate() != null){
      ret.setCoordinate(addr.getCoordinate().clone());
    }
    ret.setAccuracy(addr.getAccuracy());
    return ret;
  }
}
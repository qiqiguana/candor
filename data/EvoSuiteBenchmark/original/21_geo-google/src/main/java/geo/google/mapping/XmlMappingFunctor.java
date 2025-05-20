package geo.google.mapping;

import geo.google.GeoException;


/**
 * A function that maps a kml string to an user-defined object.
 * 
 * @author jliang
 *
 * @param <ReturnType> - any user defined object.
 */
public interface  XmlMappingFunctor<ReturnType> extends Functor<ReturnType, String, GeoException>{}
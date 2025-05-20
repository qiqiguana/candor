package geo.google.datamodel;


public class GeoUtils{
  private GeoUtils(){}
  private static final double KM_IN_ONE_MILE = 1.609344d;
//magic number = EARTH_CIRCUMFERENCE = 24872.4d / Degree in circle = 360
  private static final double MAGIC_NUMBER = 60d*1.1515;
  /**
   * Calculate the distance Between Geo Coordinates points in miles using 
   * spherical law of cosines formula.
   * http://mathworld.wolfram.com/SphericalTrigonometry.html
   * @return distance between <code>c1</code> and <code>c2</code> in miles.
   */
  public static double distanceBetweenInMiles(GeoCoordinate c1, GeoCoordinate c2){
    return distanceInMiles(c1.getLatitude(), c2.getLatitude(), c1.getLongitude(), c2.getLongitude());
  }
  
  /**
   * Calculate the distance Between Geo Coordinates points in kilometers using 
   * spherical law of cosines formula.
   * http://mathworld.wolfram.com/SphericalTrigonometry.html
   * @return distance between <code>c1</code> and <code>c2</code> in kilometers.
   */
  public static double distanceBetweenInKm(GeoCoordinate c1, GeoCoordinate c2){
    return distanceInMiles(c1.getLatitude(), c2.getLatitude(), 
            c1.getLongitude(), c2.getLongitude())*KM_IN_ONE_MILE;
  }
  
  //java port from  http://www.freevbcode.com/ShowCode.asp?ID=5532
  private static double distanceInMiles(double lat1, double lat2, double lon1, double lon2){
    double theta = lon1 - lon2;
    double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
    dist = Math.acos(dist);
    dist = Math.toDegrees(dist);
    return dist * MAGIC_NUMBER;
  }
  
  /**
   * Calculate the distance Between Geo Coordinates points in kilimeters using 
   * Haversine formula. Supposedly this formula has better precisions (need further investigations). 
   * However, it's a much slower function that the spherical law of cosines formula.
   * <br>
   * http://en.wikipedia.org/wiki/Haversine_formula
   * @return distance between <code>c1</code> and <code>c2</code> in kilimeters.
   */
  public static double haversineDistanceBetweenInKm(GeoCoordinate c1, GeoCoordinate c2){
    return distanceHaversineForumla(c1.getLatitude(), c2.getLatitude(), c1.getLongitude(), c2.getLongitude());
  }
  
  /**
   * Calculate the distance Between Geo Coordinates points in miles using 
   * Haversine formula. Supposedly this formula has better precisions (need further investigations). 
   * However, it's a much slower function that the spherical law of cosines formula.
   * <br>
   * http://en.wikipedia.org/wiki/Haversine_formula
   * @return distance between <code>c1</code> and <code>c2</code> in miles.
   */
  public static double haversineDistanceBetweenInMiles(GeoCoordinate c1, GeoCoordinate c2){
    return distanceHaversineForumla(c1.getLatitude(), c2.getLatitude(), c1.getLongitude(), c2.getLongitude())/KM_IN_ONE_MILE;
  }
  
  private static final double EARTH_RADIUS = 6371d; //in km
  //formula found on http://www.movable-type.co.uk/scripts/latlong.html
  private static double distanceHaversineForumla(double lat1, double lat2, double lon1, double lon2){
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a = Math.pow(Math.sin(dLat/2d), 2) +
               Math.cos(lat1) * Math.cos(lat2) * 
               Math.pow(Math.sin(dLon/2d), 2);
    double c = 2d * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    return EARTH_RADIUS * c;
  }
}
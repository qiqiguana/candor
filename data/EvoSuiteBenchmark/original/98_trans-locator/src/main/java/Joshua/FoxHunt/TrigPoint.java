//©2003 Joshua Job
/*    This file is part of Trans-Locator.

    Trans-Locator is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Trans-Locator is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Trans-Locator; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package Joshua.FoxHunt;

public class TrigPoint {
  double x;
  double y;
  double theta;
  boolean fox = false;

  public TrigPoint(double lat,double lon, double rad) {
    x = lon;
    y = lat;
    theta = rad;
  }
  public TrigPoint(double lat, double lon, int deg) {
    x = lon;
    y = lat;
    theta = Math.toRadians((double)deg);
  }
  public TrigPoint(double lat, double lon, int deg, boolean fox) {
    x = lon;
    y = lat;
    theta = Math.toRadians((double)deg);
    this.fox = fox;
  }
  public TrigPoint(double lat,double lon, double rad, boolean fox) {
    x = lon;
    y = lat;
    theta = rad;
    this.fox = fox;
  }
  public double getY(){
    return y;
  }
  public double getX() {
    return x;
  }
  public double getTheta() {
    return theta;
  }
  public boolean isFox() {
    return(fox);
  }
}

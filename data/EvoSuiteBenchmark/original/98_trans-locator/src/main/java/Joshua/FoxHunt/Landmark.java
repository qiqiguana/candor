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
import javax.swing.ImageIcon;

public class Landmark {
  double lat;
  double lon;
  ImageIcon icon;

  public Landmark(double lat, double lon, ImageIcon icon){
    this.lat = lat;
    this.lat = lon;
    this.icon = icon;
  }
  public void setLat(double lat) {
    this.lat = lat;
  }
  public void setLon(double lon) {
    this.lon = lon;
  }
  public void setIcon(ImageIcon icon) {
    this.icon = icon;
  }
  public double getLat() {
    return lat;
  }
  public double getLon() {
    return lon;
  }
  public ImageIcon getIcon() {
    return icon;
  }
}

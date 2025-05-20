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

public class FoxCalc {
  public static TrigPoint calcFox(TrigPoint A, TrigPoint C) {
    double x = A.getX() - C.getX();
    double y = A.getY() - C.getY();
    double x2 = Math.pow(x, 2);
    double y2 = Math.pow(x, 2);
    double b = Math.sqrt(x2 + y2);
    double tan1 = x/y;
    double theta1 = Math.atan(tan1);
    double theta2 = Math.PI - (theta1 + A.getTheta());
    double B = Math.PI -(A.getTheta() + C.getTheta());
    double c1 = b*Math.sin(C.getTheta());
    double c2 = Math.sin(B);
    double c = c1/c2;
    double theta2Sine = Math.sin(theta2);
    double theta2Cosine = Math.cos(theta2);
    double opp = c * theta2Sine;
    double adj = c * theta2Cosine;
    double X = opp + A.getX();
    double Y = adj + A.getY();
    return new TrigPoint(Y,X,B,true);
  }
}

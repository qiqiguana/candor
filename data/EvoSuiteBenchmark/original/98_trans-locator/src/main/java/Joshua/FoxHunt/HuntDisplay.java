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

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class HuntDisplay extends JComponent{
  Vector points;
  Vector landmarks;
  Dimension minSize,prefSize;
  public HuntDisplay() {
    super();
    points = new Vector();
    landmarks = new Vector();
    setBackground(Color.WHITE);
    minSize = new Dimension(50,50);
    prefSize = new Dimension(200,200);
  }
  public Dimension getPreferredSize() {
    return prefSize;
  }
  public Dimension getMinimumSize() {
    return minSize;
  }
  public void addPoint(TrigPoint point) {
    points.add(new TrigPoint((point.getY()/90),(point.getX()/180),point.getTheta(),point.isFox()));
    repaint();
  }
  public void addLandMark(Landmark landmark) {
    landmarks.add(new Landmark((landmark.getLat()/90),(landmark.getLon()/180),landmark.getIcon()));
    repaint();
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Color point = Color.BLACK;
    Color fox = Color.GREEN;
    TrigPoint[] trigpoints = new TrigPoint[points.size()];
    for (int a = 0; a < points.size(); a++){
      trigpoints[a] = (TrigPoint) points.elementAt(a);
    }
    Landmark[] landMarks = new Landmark[landmarks.size()];
    if (landmarks.size() != 0) {
      for (int z = 0; z < points.size(); z++){
        landMarks[z] = (Landmark) landmarks.elementAt(z);
      }
    }
    double trigPointAvgX = 0;
    for (int b = 0; b < trigpoints.length; b++) {
      trigPointAvgX += trigpoints[b].getX();
    }
    trigPointAvgX /= trigpoints.length;
    double trigPointAvgY = 0;
    for (int b = 0; b < trigpoints.length; b++) {
      trigPointAvgY += trigpoints[b].getY();
    }
    trigPointAvgY /= trigpoints.length;
    double xAdd = .5 - (trigPointAvgX);
    double yAdd = .5 - (trigPointAvgY);
    for ( int t = 0; t < trigpoints.length; t++) {
      if(trigpoints[t].isFox()) {
        g.setColor(fox);
      } else {
        g.setColor(point);
      }
      int X = (int)((trigpoints[t].getX()+xAdd)*getWidth());
      int Y = (int)((trigpoints[t].getY()+yAdd)*getHeight());
      g.fillOval(X,Y, 5, 5);
    }
    g.setColor(getForeground());
    if (landmarks.size() != 0 ) {
      for (int l = 0; l < landMarks.length; l++) {
        int X = (int)((landMarks[l].getLon() + xAdd) * ((double) getWidth()));
        int Y = (int)((landMarks[l].getLat() + yAdd) *((double) getHeight()));
        Image image = landMarks[l].getIcon().getImage();
        g.drawImage(image,X,Y, 10, 10,getBackground(),this);
      }
    }
  }
}

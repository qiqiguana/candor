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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.text.*;

public class FoxHuntFrame extends JFrame implements ActionListener{
  //GUI components
  JTextField theta, lat, lon,zoom;
  JButton add;
  JTable pos;
  FoxTableModel posModel;
  HuntDisplay display;
  JPanel panel1,panel2,panel3,panel;

  //Data containers
  Vector points;
  Vector fox;
  public FoxHuntFrame() {
    super("Triangulation");
    JFrame.setDefaultLookAndFeelDecorated(true);
    points = new Vector();
    fox = new Vector();
    textPrep();
    tablePrep();
    displayPrep();
    finalPrep();
    ImageIcon icon = new ImageIcon("icon.gif");
    this.setIconImage(icon.getImage());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().add(panel,BorderLayout.CENTER);
    this.pack();
    this.setState(JFrame.MAXIMIZED_BOTH);
    this.setVisible(true);
  }
  public void actionPerformed(ActionEvent e) {
    addPoint();
  }
  public void addPoint() {
    int angle;
    double LAT, LON;
    TrigPoint point;
    angle = Integer.parseInt(theta.getText());
    LAT = Double.parseDouble(lat.getText());
    LON = Double.parseDouble(lon.getText());
    point = new TrigPoint(LAT,LON,angle);
    points.add(point);
    display.addPoint(point);
    if(points.size() > 1) {
      TrigPoint C = (TrigPoint) points.elementAt(points.size()-2);
      TrigPoint B = FoxCalc.calcFox(point, C);
      fox.add(B);
      posModel.fireTableDataChanged();
      display.addPoint(B);
    }
  }
  private void finalPrep() {
    zoom = new JTextField("1");
    zoom.addActionListener(this);
    JLabel zoomL = new JLabel("Zoom");
    JPanel zoomP = new JPanel(new GridLayout(1,2));
    zoomP.add(zoomL);
    zoomP.add(zoom);
    panel3 = new JPanel(new BorderLayout());
    panel3.add(zoom, BorderLayout.NORTH);
    panel3.add(display, BorderLayout.CENTER);
    JSplitPane map, controls;
    panel = new JPanel(new BorderLayout());
    controls = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1,panel2);
    map = new JSplitPane(JSplitPane.VERTICAL_SPLIT,controls,panel3);
    map.setDividerLocation(.25);
    panel = new JPanel(new BorderLayout());
    panel.add(map,BorderLayout.CENTER);
  }
  private void textPrep() {
    JLabel thetaL,latL,lonL;
    theta = new JTextField(3);
    lat = new JTextField(9);
    lon = new JTextField(10);
    add = new JButton("Add It!!!");
    add.addActionListener(this);
    thetaL = new JLabel("Theta in Degrees");
    latL = new JLabel("Lat. dd*mm.mmm");
    lonL = new JLabel("lon. ddd*mm.mmm");
    panel1 = new JPanel(new GridLayout(4,2));
    panel1.add(thetaL);
    panel1.add(theta);
    panel1.add(latL);
    panel1.add(lat);
    panel1.add(lonL);
    panel1.add(lon);
    panel1.add(add);
  }
  private void tablePrep(){
    posModel = new FoxTableModel();
    pos = new JTable(posModel);
    JScrollPane scroll = new JScrollPane(pos);
    pos.setPreferredScrollableViewportSize(new Dimension(500, 70));
    panel2 = new JPanel(new BorderLayout());
    panel2.add(scroll, BorderLayout.CENTER);
  }
  private void displayPrep() {
    display = new HuntDisplay();
    loadLandmarks();
  }
  private void loadLandmarks() {
   try{
    double[] lat,lon;
    String[] iconFile;
    File file = new File("./landmarks.cfg");
    FileInputStream inFile = new FileInputStream(file);
    BufferedReader in = new BufferedReader(new InputStreamReader(inFile));
    String input = "";
    int NUM = 0;
    if ((input = in.readLine()) != null) {
      String num = input.substring(4);
      NUM = Integer.parseInt(num);
    }
    int numAtFile = 0;
    int numAtLat = 0;
    int numAtLon = 0;
    iconFile = new String[NUM];
    lat = new double[NUM];
    lon = new double[NUM];
    while ((input = in.readLine()) != null) {
      if (input.startsWith("FILE")) {
       iconFile[numAtFile] = input.substring(4);
       numAtFile++;
      } else if (input.startsWith("LAT")) {
       lat[numAtLat] = Double.parseDouble(input.substring(4));
       numAtLat++;
      } else if (input.startsWith("LON")) {
       lon[numAtLon] = Double.parseDouble(input.substring(4));
       numAtLon++;
      }
    }
    for (int a = 0; a < NUM; a++) {
      display.addLandMark(new Landmark(lat[a],lon[a],new ImageIcon(iconFile[a])));
    }
    in.close();
    inFile.close();
   } catch (FileNotFoundException e) {
    error("Can't Find the file");
   } catch (IOException e) {
    error("Error communicating with the file");
   }
  }
  private void error(String text) {
    Object[] options = {"Abort", "Continue"};

    int ans = JOptionPane.showOptionDialog(this,text,"Error",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE,null,options,options[0]);
    if (ans == 0 | ans == JOptionPane.CLOSED_OPTION) {
      System.exit(1);
    }
  }
  class FoxTableModel extends AbstractTableModel{
    String[] colNames = {"Lattitude","Longitude"};
    public int getColumnCount() {
      return colNames.length;
    }
    public int getRowCount() {
      return fox.size();
    }
    public String getColumnName(int col) {
      return colNames[col];
    }
    public Object getValueAt(int row, int col) {
      DecimalFormat formatter = new DecimalFormat("###.##############");
      TrigPoint point = (TrigPoint) fox.elementAt(row);
      String output;
      if (col == 0) {
       output = formatter.format(point.getY());
       return output;
      } else {
       output = formatter.format(point.getX());
       return output;
      }
    }
    public Class getColumnClass(int col) {
      return getValueAt(0,col).getClass();
    }
    public boolean isCellEditable(int row, int col) {
      return false;
    }
  }
}

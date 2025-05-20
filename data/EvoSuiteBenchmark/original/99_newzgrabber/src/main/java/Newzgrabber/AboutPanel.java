package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import javax.swing.*;

public class AboutPanel extends JPanel
{

 public AboutPanel()
 {
  this.setLayout(new GridLayout(3,1));
  String info = new String("Newzgrabber Application 1.1\n" +
	"Author: Jeffee Kiser\n" +
	"June 2003");
  this.add(new MultiLineLabel(info));
  this.setVisible(false);
 }

}

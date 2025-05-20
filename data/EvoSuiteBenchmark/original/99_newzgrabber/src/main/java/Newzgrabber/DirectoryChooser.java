package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DirectoryChooser extends JFileChooser implements ActionListener
{
 
 public DirectoryChooser()
 {
  super();
  this.setFileSelectionMode(DirectoryChooser.DIRECTORIES_ONLY);
  this.addActionListener(this);
 }

 public String getSelectedDirectory()
 {
  int Val = this.showDialog(this.getParent(),"Choose Dir");
  return(this.getSelectedFile().getAbsolutePath());
 }

 public void actionPerformed(ActionEvent ae)
 {
  System.out.println("There was an action performed");
 }

}
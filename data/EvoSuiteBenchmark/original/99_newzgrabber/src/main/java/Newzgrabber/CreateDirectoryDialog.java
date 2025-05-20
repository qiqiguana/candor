package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class CreateDirectoryDialog extends JDialog implements ActionListener
{

 private JButton OKButton;
 private JButton CancelButton;
 private JTextField DirectoryField;
 private String DirectoryText;

 public CreateDirectoryDialog(JDialog parent)
 {
  super(parent,"Subdirectory Dialog",true);
  this.getContentPane().setLayout(new BorderLayout());
  JPanel mainpanel = new JPanel(new GridLayout(1,2));
  JPanel buttonpanel = new JPanel(new GridLayout(1,2));
  DirectoryField = new JTextField();
  mainpanel.add(new Label("Subdirectory:"));
  mainpanel.add(DirectoryField);
  OKButton = new JButton("OK");
  OKButton.setActionCommand("OK");
  OKButton.addActionListener(this);
  CancelButton = new JButton("Cancel");
  CancelButton.setActionCommand("CANCEL");
  CancelButton.addActionListener(this);
  buttonpanel.add(OKButton);
  buttonpanel.add(CancelButton);
  this.getContentPane().add(mainpanel,BorderLayout.CENTER); 
  this.getContentPane().add(buttonpanel,BorderLayout.SOUTH); 
  this.setSize(350,90);
  this.setVisible(true);
 }

 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("OK"))
  {
   DirectoryText = DirectoryField.getText().trim();
   this.setVisible(false);
  }
  else if(ae.getActionCommand().equals("CANCEL"))
  {
   DirectoryText = "";
   this.setVisible(false);
  }
 }

 public String getDirectory()   
 {
  return DirectoryText;
 }

}
 
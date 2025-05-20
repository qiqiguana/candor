package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MessageDialog extends JDialog implements ActionListener
{
 private JButton OkButton;
 private String Message;
 protected int WIDTH = 200;
 protected int HEIGHT = 200;

 public MessageDialog(Dialog d,String m,int w,int h)
 {
  super(d,"Message Dialog");
  Message = m;
  WIDTH = w;
  HEIGHT = h;
  this.createDialog();
 }
 
 public MessageDialog(Dialog d,String m)
 {
  this(d,m,200,200); 
 }

 public MessageDialog(Frame p,String m,int w, int h)
 {
  super(p,"Message Dialog");
  Message = m;
  WIDTH = w;
  HEIGHT = h;
  this.createDialog();
 }

 public MessageDialog(Frame p,String m)
 {
  super(p,"Message Dialog");
  Message = m;
  this.createDialog();
  this.pack();
 }

 private void createDialog()
 {
  OkButton = new JButton("OK");
  OkButton.addActionListener(this);
  OkButton.setActionCommand("OK"); 
  this.getContentPane().setLayout(new BorderLayout());
  this.getContentPane().add(new MultiLineLabel(Message),BorderLayout.CENTER);
  this.getContentPane().add(OkButton,BorderLayout.SOUTH); 
  this.setSize(WIDTH,HEIGHT);
  this.setVisible(true);
 }

 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("OK"))
  {
   this.setVisible(false);
   this.dispose();
  }
 }

}
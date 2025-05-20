package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class StatsDialog extends JDialog implements ActionListener
{

 private JButton OKButton;
 private String Group;
 private JLabel ErrorLabel;
 private String Username;
 private String Password;
 
 public StatsDialog(JDialog parent,String g)
 {
  super(parent,"Statistics Dialog");
  this.getContentPane().setLayout(new BorderLayout());
  Username = null;
  Password = null;
  JPanel mainpanel = new JPanel(new GridLayout(5,2,10,10));
  ErrorLabel = new JLabel();
  JLabel NameLabel = new JLabel();
  JLabel FirstLabel = new JLabel();
  JLabel LastLabel = new JLabel();
  JLabel TotalLabel = new JLabel();
  JButton OKButton = new JButton("OK");
  OKButton.setActionCommand("OK");
  OKButton.addActionListener(this);
  mainpanel.add(new JLabel("Newsgroup"));
  mainpanel.add(NameLabel);
  mainpanel.add(new JLabel("First Article ID"));
  mainpanel.add(FirstLabel);
  mainpanel.add(new JLabel("Last Article ID"));
  mainpanel.add(LastLabel);
  mainpanel.add(new JLabel("Total Number of Articles"));
  mainpanel.add(TotalLabel);
  mainpanel.add(ErrorLabel);
  this.getContentPane().add(mainpanel,BorderLayout.CENTER);
  this.getContentPane().add(OKButton,BorderLayout.SOUTH);
  Group = g;
  Username = OptionsPanel.UsernameText.getText().trim();
  String pTemp = new String(OptionsPanel.PasswordText.getPassword());
  Password = pTemp.trim();
  try
  {
   NNTP news = Newzgrabber.nf.getNewsSocket();
//   if(Username.length() > 0 && Password.length() > 0)
//	news.authInfo(Username,Password);
   String[] info = news.genericCommand("group " + g);
   news.quit();
   StringTokenizer st = new StringTokenizer(info[0]);
   try
   {
    st.nextToken();
    TotalLabel.setText(st.nextToken());
    FirstLabel.setText(st.nextToken());
    LastLabel.setText(st.nextToken());
    NameLabel.setText(st.nextToken());
   }
   catch(Exception ste) {}
  }
  catch(Exception e)
  {
   TotalLabel.setText("");
   FirstLabel.setText("");
   LastLabel.setText("");
   NameLabel.setText("");
   ErrorLabel.setText("Error getting info from server!");
  }
//  this.setSize(400,400);
  this.pack();
  this.setVisible(true);
 }

 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("OK"))
  {
   this.setVisible(false);
  }
 }

}
   
package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

public class OptionsPanel extends JPanel implements ActionListener
{

 protected static String OptionFilename = "Newzgrabber.ini";
 protected static String JarFilename = "Newz.jar";
 JTextField ServerText;
 JTextField ServerPort;
 JTextField ThreadsText;
 static JTextField UsernameText;
 JTextField DirectoryText;
 static JPasswordField PasswordText;
 JButton DirectoryButton;
 JButton BuildListButton;
 JLabel GroupsLabel;
 JRadioButton GetAll;
 JRadioButton GetNew;
 ButtonGroup SearchType;
 Frame Parent;
 final static String GroupsFilename = "Newsgroups.ini";
 
 public OptionsPanel(Frame p)
 {
  super();
  this.setLayout(new BorderLayout());
  Parent = p;
//  System.out.println("The parent is " + Parent.toString());
  JPanel mainoptions = new JPanel();
  mainoptions.setLayout(new GridLayout(8,2,20,20));
  BuildListButton = new JButton("Build Group List");
  BuildListButton.addActionListener(this);
  BuildListButton.setActionCommand("BUILD");
  GetAll = new JRadioButton("Search all articles in groups");
//  GetAll.setSelected(true);
  GetNew = new JRadioButton("Skip articles I've already seen");
  GetAll.setSelected(true);
  GetNew.setSelected(false);
  DirectoryButton = new JButton("Download Directory");
  DirectoryButton.addActionListener(this);
  DirectoryButton.setActionCommand("BROWSE");
  ServerText = new JTextField();
  ServerPort = new JTextField("119");
  ThreadsText = new JTextField();
  UsernameText = new JTextField();
  DirectoryText = new JTextField();
  GroupsLabel = new JLabel("0");
  PasswordText = new JPasswordField();
  PasswordText.setEchoChar('*');
  JPanel GroupInfoPanel = new JPanel(new GridLayout(1,2,5,5));
  GroupInfoPanel.add(new JLabel("Number of Groups:"));
  GroupInfoPanel.add(GroupsLabel);
  SearchType = new ButtonGroup();
  SearchType.add(GetAll);
  SearchType.add(GetNew);
  this.initializeOptions();
  mainoptions.add(new JLabel("News Server")); 
  mainoptions.add(ServerText);
  mainoptions.add(new JLabel("News Port"));
  mainoptions.add(ServerPort);
  mainoptions.add(new JLabel("Username"));
  mainoptions.add(UsernameText);
  mainoptions.add(new JLabel("Password"));
  mainoptions.add(PasswordText);
  mainoptions.add(new JLabel("Threads"));
  mainoptions.add(ThreadsText);
  mainoptions.add(DirectoryButton);
  mainoptions.add(DirectoryText);
  mainoptions.add(BuildListButton);
  mainoptions.add(GroupInfoPanel);
  mainoptions.add(GetAll);
  mainoptions.add(GetNew);
  this.add(mainoptions,BorderLayout.CENTER);
  this.setVisible(true);
 }
 
 public boolean searchAll()
 {
  return GetAll.isSelected();
 }

 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("BROWSE"))
  {
   DirectoryDialog dd = null;
   boolean HaveDirectory = false;
   if(DirectoryText.getText() != null)
   {
    if(DirectoryText.getText().trim().length() > 0)
    {
     File d = new File(DirectoryText.getText().trim());
     if(d.isDirectory())
     {
      dd = new DirectoryDialog(Parent,"Directory Dialog",d);
      HaveDirectory = true;
     }
    }
   }
   if(HaveDirectory == false)
	dd = new DirectoryDialog(Parent);
   dd.setVisible(true);
   dd.setBackground(Parent.getBackground());
   DirectoryText.setText(dd.getDirectory().getAbsolutePath());
   dd.setVisible(false);
  }
  else if(ae.getActionCommand().equals("BUILD"))
  {
   try
   {
    final String Server = ServerText.getText().trim();
    if(Server.trim().length() < 1)
    {
     MessageDialog md = new MessageDialog(Parent,
	"You must specify a News Server!");
    }
    else
    {
     final String Port = ServerPort.getText().trim();
     final JTextField ServerFieldCopy = ServerText;
     final String Username = UsernameText.getText().trim();
     final String Password = new String(PasswordText.getPassword());
     final JLabel GroupsLabelCopy = GroupsLabel;
     final Frame PF = Parent;
     Thread build = new Thread()
     {
 	public void run()
 	{
 	 NNTP n = null;
 	 try
  	 {	
 	  n = Newzgrabber.nf.getNewsSocket(Server,Integer.parseInt(Port));
/*
 	  if(Username != null && Password != null)
 	  {
 	   n.authInfo(Username,Password);
 	  }
*/
	  if(n != null)
	  {
 	   BufferedCustomInputStream br = n.getReader();
 	   PrintWriter pw = n.getWriter();
	   if(System.getProperty("os.name").toLowerCase().indexOf("windows") != -1)
		pw.println("list");
	   else
		pw.print("list\r\n");
 	   pw.flush();
 	   Vector vGroups = new Vector();
 	   String NextLine = null;
 	   JDialog jd = new JDialog(PF,"Building Groups");
 	   jd.getContentPane().setLayout(new GridLayout(2,1,20,20));
 	   JPanel CountPanel = new JPanel(new GridLayout(1,2,5,5));
 	   JLabel tmpGroups = new JLabel("0");	  
 	   CountPanel.add(new JLabel("Groups Found:"));
 	   CountPanel.add(tmpGroups);
 	   jd.getContentPane().add(new JLabel("Gathering groups...Please wait"));
 	   jd.getContentPane().add(CountPanel);
 	   jd.setSize(200,100);
 	   jd.setVisible(true);
 	   jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
 	   while(true)
 	   {
 	    NextLine = br.readLine();
 	    if(NextLine.equals(".")) break;
 	    try
 	    {
 	     StringTokenizer st = new StringTokenizer(NextLine);
 	     String tmpgroup = st.nextToken();
 	     if(tmpgroup.indexOf(".") < 0) continue;
 	     vGroups.add(tmpgroup);
 	    }
 	    catch(Exception ve) { continue; }
 	    if((vGroups.size() % 10) == 0)
 	 	tmpGroups.setText(String.valueOf(vGroups.size()));
 	   }
 	   jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
 	   jd.setVisible(false);
 	   n.quit();
           n = null;
 	   GroupsLabelCopy.setText(String.valueOf(vGroups.size()));
 	   StringSorter ss = new StringSorter(true);
 	   for(int i=0 ; i<vGroups.size() ; i++)
 	   { 
 	    ss.addString((String)vGroups.elementAt(i));
 	   }
 	   String[] SortedGroups = ss.getSortedArray();
 	   PrintWriter fw = new PrintWriter(new FileWriter(
 		Newzgrabber.Newzdirectory + 
 		System.getProperty("file.separator") +
 		GroupsFilename)); 
 	   for(int j=0 ; j<SortedGroups.length ; j++)
 	   {
 	    fw.println(SortedGroups[j]);
 	   }
 	   fw.flush();
 	   fw.close();
 	  }
	  else
	  {
	   MessageDialog emd = new MessageDialog(Parent,
		"Could not connect to " + Server + " on port " + Port + "!");
	   ServerFieldCopy.setText("");
  	  }
	 }
 	 catch(Exception te)
 	 {
	  MessageDialog emd = new MessageDialog(Parent,
		"Could not connect to " + Server + " on port " + Port + "!");
	  ServerFieldCopy.setText("");
 	  if(n != null)
    	  {
 	   try
  	   {
 	    n.quit();
 	   } catch(Exception quitE) {}
 	  } 
 	  System.err.println(te.getMessage());
 	 }
 	}
     }; 
     build.start();
    }
   }
   catch(Exception me)
   {
    System.err.println(me.getMessage());
   }
  }
 }

 public void updateIni()
 {
  try
  {
   Ini ini = new Ini(
	Newzgrabber.Newzdirectory + 
	System.getProperty("file.separator") + OptionFilename,
	"Options");
   LinkedList ll = new LinkedList();
   if(ServerText != null)
   {
    String[] s = {"server",ServerText.getText().trim()};
    ll.add(s);
   }
   if(ServerPort != null)
   {
    String[] s = {"port",ServerPort.getText().trim()};
    ll.add(s);
   }
   if(UsernameText != null)
   {
    String[] s = {"username",UsernameText.getText().trim()};
    ll.add(s);
   }
   if(PasswordText != null)
   {
    char[] pass = PasswordText.getPassword();
    String PString = new String(pass);
    String encpass = PassEnc.encodePassword(PString.trim());
    String[] s = {"password",encpass};
    ll.add(s);
   }
   if(GetAll != null)
   {
    boolean searchall = GetAll.isSelected();
    String[] s = new String[2];
    s[0] = "searchall";
    if(searchall) s[1] = "true";
    else s[1] = "false";
    ll.add(s);
   }
   if(ThreadsText != null)
   {
    String[] s = {"threads",ThreadsText.getText().trim()};
    ll.add(s);
   }
   else 
   {
    String[] s = {"threads","2"};
    ll.add(s);
   }
   if(DirectoryText != null)
   {
    File d = new File(DirectoryText.getText().trim());
    if(d.isDirectory())
    {
     String[] s = {"directory",d.getAbsolutePath()};
     ll.add(s);
    }
   }
   if(GroupsLabel != null)
   {
    String[] s = {"groups",GroupsLabel.getText().trim()};
    ll.add(s);
   }
   ini.writeIni(ll);
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
   e.printStackTrace();
  }
 }


 private void initializeOptions()
 {
  try
  {
   Ini ini = new Ini(
	Newzgrabber.Newzdirectory + 
	System.getProperty("file.separator") + OptionFilename,
	"Options");
   Hashtable options = ini.readIni();
   if(options.containsKey("server"))
	ServerText.setText((String)options.get("server"));
   if(options.containsKey("port"))
	ServerPort.setText((String)options.get("port"));
   if(options.containsKey("password"))
   {
    String pass = (String)options.get("password");
    PasswordText.setText(PassEnc.decodePassword(pass));
   }
   if(options.containsKey("username"))
	UsernameText.setText((String)options.get("username"));
   if(options.containsKey("threads"))
   {
	String ThreadsString = (String)options.get("threads");
	ThreadsText.setText(ThreadsString);
	try
	{
		Newzgrabber.nf.setThreadLimit(
			Integer.parseInt(ThreadsString.trim()));
	}
	catch(Exception te) {
		Newzgrabber.nf.setThreadLimit(2);
	}
   }
   if(options.containsKey("directory"))
   {
	String dirname = (String)options.get("directory");
	File filedir = new File(dirname);
	if(filedir.exists() && filedir.isDirectory())
		DirectoryText.setText(filedir.getAbsolutePath());
	else
	{
	 DirectoryText.setText(System.getProperty("user.home"));
 	}
   }
   if(options.containsKey("groups"))
	GroupsLabel.setText((String)options.get("groups"));
   if(options.containsKey("searchall"))
   {
	String searchvalue = (String)options.get("searchall");
	if(searchvalue.toLowerCase().trim().equals("false"))
	{
		GetAll.setSelected(false);
		GetNew.setSelected(true);
	}
	else
	{
		GetAll.setSelected(true);
		GetNew.setSelected(false);
	}
   }
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }

}

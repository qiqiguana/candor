package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class DirectoryDialog extends JDialog implements FilenameFilter,
	ListSelectionListener,ActionListener,WindowListener
{
 protected static String OK_TITLE = "OK";
 protected static String CANCEL_TITLE = "Cancel";
 protected static String CREATE_TITLE = "Create";
 private JButton OkButton;
 private JButton CancelButton;
 private JButton CreateButton;
 private String DirectoryString;
 private File CurrentDirectory;
 private JList FileList;
 private JScrollPane jsp;
 private JLabel DirLabel;
 private JLabel ErrLabel;
 private File Directory;
 private boolean root = false;
 private DefaultListModel dlm;
 private boolean Setdir = false;

 public DirectoryDialog(Frame parent,String title,File d)
 {
  super(parent,title);
  Directory = d;
  this.setModal(true);
  this.getContentPane().setLayout(new BorderLayout());
  this.setBackground(parent.getBackground());
  CurrentDirectory = d;
  this.addWindowListener(this);
  dlm = new DefaultListModel();
  FileList = new JList(dlm);
  FileList.addListSelectionListener(this);
  JScrollPane jsp = new JScrollPane(FileList,
	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  DirLabel = new JLabel();
  ErrLabel = new JLabel();
  JPanel mainpanel = new JPanel(new BorderLayout());
  mainpanel.add(jsp,BorderLayout.CENTER);
  mainpanel.add(DirLabel,BorderLayout.NORTH);
  mainpanel.add(ErrLabel,BorderLayout.SOUTH);
  JPanel buttonpanel = new JPanel(new GridLayout(1,2,20,20));
  OkButton = new JButton(OK_TITLE);
  OkButton.setActionCommand("OK");
  OkButton.addActionListener(this);
  CancelButton = new JButton(CANCEL_TITLE);
  CancelButton.setActionCommand("CANCEL");
  CancelButton.addActionListener(this);
  CreateButton = new JButton(CREATE_TITLE);
  CreateButton.setActionCommand("CREATE");
  CreateButton.addActionListener(this);
  buttonpanel.add(OkButton);
  buttonpanel.add(CancelButton);
  buttonpanel.add(CreateButton);
  this.getContentPane().add(mainpanel,BorderLayout.CENTER);
  this.getContentPane().add(buttonpanel,BorderLayout.SOUTH);
  this.showDirectories();
  this.setSize(300,300);
 }

 public String getCurrentDirectory()
 {
  return(Directory.getAbsolutePath());
 }

 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("OK"))
  {
   Directory = CurrentDirectory;
//   System.out.println("Set the current directory");
   this.dispose();
  }
  else if(ae.getActionCommand().equals("CANCEL"))
  {
   this.dispose();
  }
  else if(ae.getActionCommand().equals("CREATE"))
  {
   CreateDirectoryDialog cdd = new CreateDirectoryDialog(this);
   String newdir = cdd.getDirectory().trim();
   if(newdir.length() > 0)
   {
    try
    {
     String cdir = CurrentDirectory.getAbsolutePath();
     File createdir = new File(cdir + System.getProperty("file.separator") +
	newdir);
     if(createdir.mkdirs())
     {
      dlm.addElement(newdir);
     }
     else
     {
      MessageDialog md = new MessageDialog(this,
	"Can not create subdirectory!");
     }
    }
    catch(Exception cdde)
    {
     MessageDialog md = new MessageDialog(this,"ERROR: " + cdde.getMessage());
    }
   }
  }
 }

 public DirectoryDialog(Frame parent,String title)
 {
  this(parent,title,new File(System.getProperty("user.dir")));
 }

 public DirectoryDialog(Frame parent)
 {
  this(parent,"Directory Dialog",new File(System.getProperty("user.dir")));
 }

// public synchronized void valueChanged(ListSelectionEvent ie)
 public void valueChanged(ListSelectionEvent ie)
 {
  if(Setdir == false)
  {
   try
   {
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    if(root)
    {
     try
     {
      CurrentDirectory = new File((String)FileList.getSelectedValue());
      root = false;
     }
     catch(Exception ce)
     {
      root = true;
     }
    }
    else if(FileList.getSelectedIndex() == 0)
    {
     File tmpDir = CurrentDirectory.getParentFile();
//     System.out.println("Getting parent for " + tmpDir.toString());
     root = false;
     if(tmpDir != null)
  	CurrentDirectory = tmpDir;
     else
  	root = true;
    }
    else
    {
     root = false;
     String subdir = (String)FileList.getSelectedValue();
     if(subdir != null) 
     {
      String dirstring = CurrentDirectory.getAbsolutePath();
//      System.out.println("Appending " + subdir + " to " + dirstring);
      CurrentDirectory = new File(dirstring + 
  	System.getProperty("file.separator") + subdir);
     }
     else
     {
      ie = null;
     }
//     System.out.println("Showing the directories");
    }
    this.showDirectories();
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//    System.out.println("Done!");
   }
   catch(Exception mainE) {}
  }
  else
  {
   Setdir = false;
  }
 }

 protected void showDirectories()
 {
  Setdir = true;
  ErrLabel.setText("");
  String[] tmpdirs = null;
  String[] dirs = null;
  if(root)
  {
   File[] RootDrives = File.listRoots();
   tmpdirs = new String[RootDrives.length];
   for(int i=0 ; i<RootDrives.length ; i++)
   {
    tmpdirs[i] = RootDrives[i].toString();
   }
  }
  else
  {
   if(CurrentDirectory.isDirectory() == false)
   {
	ErrLabel.setText("Can not change to " + CurrentDirectory.toString());
	CurrentDirectory = Directory;
   }
   tmpdirs = CurrentDirectory.list(this);
  }
  dlm.removeAllElements();
  if(root == false) dlm.addElement("[Up to parent directory]");
  StringSorter ss = new StringSorter(true);
  for(int i=0 ; i<tmpdirs.length ; i++)
  {
   ss.addString(tmpdirs[i]);
  }
  dirs = ss.getSortedArray();
  for(int i=0 ; i<tmpdirs.length ; i++)
  {
   dlm.addElement(dirs[i]);
  }
  DirLabel.setText("Current Directory:" + 
	CurrentDirectory.getAbsolutePath());
//  this.pack();
 }

 public void setDirectory(File d)
 {
  Directory = d;
 }

 public File getDirectory()
 {
  return Directory;
 }


 public boolean accept(File dir,String n)
 {
  try
  {
   String dirstring = dir.getAbsolutePath();
   String checkdir = new String(dirstring + 
	System.getProperty("file.separator") + n);
   File check = new File(dirstring + System.getProperty("file.separator") + n);
   if(check.isDirectory()) return true;
   return false;
  }
  catch(Exception e) 
  {
   return false;
  }
 }

 public void windowOpened(WindowEvent we) {}
 public void windowClosed(WindowEvent we) {}
 public void windowActivated(WindowEvent we) {}
 public void windowDeactivated(WindowEvent we) {}
 public void windowIconified(WindowEvent we) {}
 public void windowDeiconified(WindowEvent we) {}

 public void windowClosing(WindowEvent we)
 {
  this.dispose();
 }

}

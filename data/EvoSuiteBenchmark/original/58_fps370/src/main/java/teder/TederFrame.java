package teder;

/*
 * TederFrame - Game tile editor frame
 *
 * By: Wood Harter - Feb 22, 2006
 * Created for cpsc370 at Chapman University
 * http://www.gamedev370.com
 * (c) copyright 2006 - W. Wood Harter
 *
 * Licensed under GNU General Public License
 * http://www.gnu.org
 *
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class TederFrame extends JFrame implements ActionListener,WindowListener
{
  public static final int DEFAULT_WIDTH  = 800;
  public static final int DEFAULT_HEIGHT = 600;
  
  public TederData ttd;
  public TederPanel ttp;
  
  // swing componenet for the menu bar
  public JMenuBar mbar;
  public JMenu fileMenu;
  public JMenuItem openMenuItem;
  public JMenuItem saveMenuItem;
  public JMenuItem saveAsMenuItem;
  public JMenuItem exitMenuItem;
  
  public JMenu mapMenu;
  public JMenuItem resizeMenuItem;
  
  public JMenu tileMenu;
  public JCheckBoxMenuItem tileItems[];
  
  public JScrollPane spMain;
  
  // the path to the last file operation
  public String lastPath;
  
  public TederFrame()
  {
    // get the program startup location
    lastPath = System.getProperty("user.dir");
    
    ttd = new TederData(30,30,64,64,"images/tiles.gif",this);
    ttp = new TederPanel(ttd);
    
    createMenuBar();
    
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(this);
    setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    
    //spMain = new JScrollPane(ttp,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
    //                             JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    spMain = new JScrollPane(ttp);
    spMain.setPreferredSize(new Dimension(200,200));
    
    
    // add the panel to this frame
    //add(ttp);
    add(spMain);
  }
  
  /*------------------- WindowListener ---------------------*/
  public void windowActivated(WindowEvent e)
  {
    
  }
  public void windowClosed(WindowEvent e)
  {
    System.exit(0);
  }
  public void windowClosing(WindowEvent e)
  {
    if (okayToClose())
    {
      System.exit(0);
    }
  }
  public void windowDeactivated(WindowEvent e)
  {
    
  }
  public void windowDeiconified(WindowEvent e)
  {
    
  }
  public void windowIconified(WindowEvent e)
  {
    
  }
  public void windowOpened(WindowEvent e)
  {
    
  }
  /*------------------- WindowListener ---------------------*/
  
  public boolean okayToClose()
  {
    if (ttd.getHasChanged())
    {
      // need to pop up a dialog to see if it is okay to close
      //Custom button text
      Object[] options = {"Yes",
      "No",
      "Cancel"};
      int n = JOptionPane.showOptionDialog(this,
                "The map has changed would you like to save the file before proceeding?",
                "File Has Changed",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
      if (n==0)
      {
        return doSaveFile();
      }
      else  if ( n==2)
      {
        return false;
      }
    }
    return true;
  }
  public void createMenuBar()
  {
    JMenuItem itmTmp;
    
    mbar =  new JMenuBar();
    
    /*     FILE MENU     */
    fileMenu = new JMenu("File");
    
    // new is one way to do it where we check for the string in the listener
    itmTmp = new JMenuItem("New");  // create the item
    itmTmp.addActionListener(this); // add the event listener
    fileMenu.add(itmTmp);           // add the item to the menu
    
    // the other way to do it is to save references to each item.
    // this is better since you do less string processing, but it
    // takes more memory. Always a trade off.
    openMenuItem = new JMenuItem("Open...");
    openMenuItem.addActionListener(this);
    fileMenu.add(openMenuItem);
    saveMenuItem = new JMenuItem("Save");
    saveMenuItem.addActionListener(this);
    fileMenu.add(saveMenuItem);
    saveAsMenuItem = new JMenuItem("Save As...");
    saveAsMenuItem.addActionListener(this);
    fileMenu.add(saveAsMenuItem);
    
    // the last way is not demonstrated, that is a special object
    // just for the individual item event handling. All the books show
    // it as the correct way to handle events. It is the most
    // memory/disk intensive. Not shown here.
    
    // exit is the other way, we keep the object reference and check for that
    exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.addActionListener(this);
    fileMenu.add(exitMenuItem);
    
    mbar.add(fileMenu);
    
    /*     EDIT MENU     */
    
    /*     MAP MENU */
    mapMenu = new JMenu("Map");
    resizeMenuItem = new JMenuItem("Resize");
    resizeMenuItem.addActionListener(this);
    mapMenu.add(resizeMenuItem);
    
    mbar.add(mapMenu);

    /*     TILES MENU    */
    tileMenu = new JMenu("Tile");
    tileItems = new JCheckBoxMenuItem[TederData.NUM_TILES];
    int i;
    for (i=0;i<TederData.NUM_TILES;i++)
    {
      tileItems[i] = new JCheckBoxMenuItem("Tile "+i,new ImageIcon("images/tile_icons/tile"+i+".gif"));
      tileItems[i].addActionListener(this);
      tileMenu.add(tileItems[i]);
    }
    mbar.add(tileMenu);
    
    
    //mbar.addActionListener(this);
    adjustMenus();
    this.setJMenuBar(mbar);
  }
  
  
  // action listener handling
  public void actionPerformed(ActionEvent evt)
  {
    //System.out.println("evt="+evt);
    if (evt.getActionCommand().equals("New"))
    {
      System.out.println("New command selected");
    }
    else if (evt.getSource()==exitMenuItem)
    {
      System.out.println("Exit selected");
      if (okayToClose())
        System.exit(0);
    }
    else if (evt.getSource()==saveMenuItem)
    {
      this.doSaveFile();
      ttd.doSave();
    }
    else if (evt.getSource()==saveAsMenuItem)
    {
      this.doSaveAsFile();
    }
    else if (evt.getSource()==openMenuItem)
    {
      doLoadFile();
      ttp.resetPreferredSize();
      this.repaint();
    }
    else if (evt.getSource()==resizeMenuItem)
    {
      doMapSizeDialog();
      this.repaint();
    }
    else
    {
      // may be one of the tiles
      int i;
      for (i=0;i<TederData.NUM_TILES;i++)
      {
        if (evt.getSource()==tileItems[i])
        {
          ttp.curTile = i;
        }
      }
      adjustMenus();
    }
  }
  
  public void adjustMenus()
  {
    int i;
    for (i=0;i<TederData.NUM_TILES;i++)
    {
      if (i==ttp.curTile)
        tileItems[i].setState(true);
      else
        tileItems[i].setState(false);
      
    }
  }
  
  public void doLoadFile()
  {
    JFileChooser chooser;
    
    if (!okayToClose())
      return;
    
    if (lastPath==null)
    {
      chooser = new JFileChooser();
    }
    else
    {
      File fiTmp = new File(lastPath);
      chooser = new JFileChooser(fiTmp);
    }
    //chooser.setDialogType(JFileChooser.OPEN_DIALOG);
    MapFileFilter filter = new MapFileFilter();
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(this);
    if(returnVal == JFileChooser.APPROVE_OPTION)
    {
      File fiSelected = chooser.getSelectedFile();
      lastPath = fiSelected.getPath();
      ttd.setFileName(fiSelected.getAbsolutePath());
      ttd.doLoad();
      ttd.setHasChanged(false);
    }
  }
  
  public boolean doSaveAsFile()
  {
    JFileChooser chooser;
    if (lastPath==null)
    {
      chooser = new JFileChooser();
    }
    else
    {
      File fiTmp = new File(lastPath);
      chooser = new JFileChooser(fiTmp);
    }
    //chooser.setDialogType(JFileChooser.SAVE_DIALOG);
    //System.out.println("Save file type = SAVE_DIALOG");
    MapFileFilter filter = new MapFileFilter();
    chooser.setFileFilter(filter);
    int returnVal = chooser.showSaveDialog(this);
    
    if(returnVal == JFileChooser.APPROVE_OPTION)
    {
      File fiSelected = chooser.getSelectedFile();
      if (fiSelected.exists())
      {
        // make sure they want to overwrite
        // need to pop up a dialog to see if it is okay to close
        //Custom button text
        Object[] options = {"Yes",
        "No"};
        int n = JOptionPane.showOptionDialog(this,
                  "The file already exists, would you like to overwrite?",
                  "File Exists",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  options,
                  options[1]);
        if ( n==1)
        {
          return false;
        }
      } // exists
      lastPath = fiSelected.getPath();
      ttd.setFileName(fiSelected.getAbsolutePath());
      ttd.doSave();
      ttd.setHasChanged(false);
      return true;
    }
    // user pressed cancel, return false that it did not save
    return false;
  }
  public boolean doSaveFile()
  {
    if (ttd.isValidFile())
    {
      ttd.doSave();
    }
    else
    {
      return doSaveAsFile();
    }
    return true;
  }
  
  public void setMapSize(int w, int h)
  {
    ttd.resize(w,h);
    ttp.resetPreferredSize();
  }
  public void doMapSizeDialog()
    {
      MapSizeDialog msp = new MapSizeDialog(this,ttd.getWidth(),ttd.getHeight());
      msp.setVisible(true);
      /*
      JDialog dialog = new JDialog(this,
                                    "Click a button",
                                    true);      
      dialog.setContentPane(msp);


      dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      dialog.pack();
      dialog.setLocationRelativeTo(this);
      dialog.setVisible(true);
       */
    }
  
  class MapFileFilter extends javax.swing.filechooser.FileFilter
  {
    public MapFileFilter()
    {
      super();
    }
    public boolean accept(File f)
    {
      if ((f.getName().endsWith(".dat")) || (f.isDirectory()))
      {
        return true;
      }
      return false;
    }
    public String getDescription()
    {
      return "Map .dat files";
    }
  }
 
  
  class MapSizeDialog extends JDialog
  {
   
    TederFrame parent;
    JButton okButton;
    JButton cancelButton;
    JTextField jtfWidth;
    JTextField jtfHeight;
    
    public MapSizeDialog(TederFrame parentInit, int wid, int hei)
    {
      super(parentInit,true);  // create this as a modal dialog
      
      parent = parentInit;
      
      // create all the elements for the pane
      JPanel inputPane = new JPanel();
      inputPane.setLayout(new GridLayout(0,2));
      
      inputPane.add(new JLabel("Width:"));
      jtfWidth = new JTextField(""+wid);
      inputPane.add(jtfWidth);
      inputPane.add(new JLabel("Height"));
      jtfHeight = new JTextField(""+hei);
      inputPane.add(jtfHeight);
      inputPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
      buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

      buttonPane.add(Box.createHorizontalGlue());
      cancelButton = new JButton("Cancel");
      cancelButton .addActionListener(new ActionListener() 
            {
              public void actionPerformed(ActionEvent e)
              {
                //pick one of many
                if (e.getSource()==cancelButton)
                {
                  setVisible(false);
                  dispose();
                }
                  
              }   
            });      
      buttonPane.add(cancelButton);
       
      buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
      okButton = new JButton("Ok");
      okButton.addActionListener(new ActionListener() 
            {
              public void actionPerformed(ActionEvent e)
              {
                //pick one of many
                if (e.getSource()==okButton)
                {
                  try
                  {
                    int wid = Integer.parseInt(jtfWidth.getText());
                    int hei = Integer.parseInt(jtfHeight.getText());
                    parent.setMapSize(wid,hei);
                  }
                  catch (NumberFormatException ex)
                  {
                    ex.printStackTrace();
                  }
                  
                  setVisible(false);
                  dispose();
                }
                  
              }   
            });
      buttonPane.add(okButton); 

      Container contentPane = getContentPane();
      contentPane.add(inputPane, BorderLayout.CENTER);
      contentPane.add(buttonPane, BorderLayout.PAGE_END);
      this.pack();
    }
  }
}

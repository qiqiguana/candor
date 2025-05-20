package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class DirectoryFilter extends javax.swing.filechooser.FileFilter
{
 
 public boolean accept(File f)
 {
  return(f.isDirectory());
 }

 public String getDescription()
 {
  return "Directories";
 }

}
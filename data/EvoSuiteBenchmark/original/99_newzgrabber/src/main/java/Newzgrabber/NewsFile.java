package Newzgrabber;
import Newzgrabber.*;

import java.io.*;

public class NewsFile
{
 
 public static String renameFile(String Filename)
 {
  try
  {
   File original = new File(Filename);
   String directory = original.getParent();
   String name = original.getName();
   String subname = getName(name);
   String extension = getExtension(name);
   boolean ChangedFile = false;
   File f = new File(directory,name);
   int appender = 1;
   while(f.exists())
   {
    ChangedFile = true;
    StringBuffer newname = new StringBuffer(subname);
    newname.append("_" + appender);
    if(extension != null)
	newname.append("." + extension); 
    f = new File(directory,newname.toString());
    appender++;
   }
   if(ChangedFile)
	System.out.println("Changed " + Filename + " to " + f.getAbsolutePath());
   return f.getAbsolutePath();
  }
  catch(Exception e) { return Filename; }
 }

 public static String getName(String Filename)
 {
  try
  {
   String name = Filename.substring(0,Filename.lastIndexOf("."));
   return name;
  }
  catch(Exception e) { return Filename; }
 }

 public static String getExtension(String Filename)
 {
  try
  {
   if(Filename.indexOf('.') == -1) return null;
   String extension = Filename.substring(Filename.lastIndexOf(".")+1,
	Filename.length());
   return extension;
  }
  catch(Exception e) { return null; }
 }

 public static void main(String[] args) 
 {
  try
  {
   String testname = null;
   if(args.length > 0) 
	testname = "/home/jeffee/Newzgrabber/test/" + args[0];
   else
	testname = "/home/jeffee/Newzgrabber/test/test.out";
   testname = renameFile(testname);
   System.out.println("The new name is " + testname);
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }
 
}
   
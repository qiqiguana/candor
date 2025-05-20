package framework.util;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.zip.ZipOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.net.URL;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;


/**
 * File system utility class
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:51 $
 */
public final class FileUtils
{

  // Logger
  private static Log log = LogFactory.getLog(FileUtils.class);

  /**
   * Protected constructor
   */
  protected FileUtils()
  {
  }

  /**
   * Get the full path to classes directory
   * @return The full path to classes in String format
   */
  public static String getClassLoaderRootDirectory()
  throws Exception
  {
    return getCLFileDirectory("/");
  }

  /**
   * Return the full directory to the file
   * The file must be visible from FileUtils ClassLoader
   * @param  fileName File name
   * @return The file path
   */
  public static String getCLFileDirectory(String fileName)
  throws Exception
  {
    return getCLFileDirectory(null, fileName);
  }
  
  /**
   * Return the full directory to the file
   * The file must be visible from referenceClass ClassLoader
   * Default referenceClass is FileUtils
   * @param  referenceClass The reference class for classLoader
   * @param  fileName File name
   * @return The file path
   */
  public static String getCLFileDirectory(Class referenceClass, String fileName)
  throws Exception
  {
    URL fileURL = getCLFileURL(referenceClass, fileName);
    if (fileURL!=null) return fileURL.getFile();
    return null;
  }

  /**
   * Return the file URL
   * The file must be visible from referenceClass ClassLoader
   * Default referenceClass is FileUtils
   * @param  referenceClass The reference class for classLoader
   * @param  fileName File name
   * @return The file URL
   */
  public static URL getCLFileURL(Class referenceClass, String fileName)
  throws Exception
  {
    if (referenceClass==null) referenceClass=FileUtils.class;
    ClassLoader cl = referenceClass.getClassLoader();
    Enumeration enume = cl.getResources(fileName);
    URL fileURL = null;
    while (enume.hasMoreElements()) fileURL = (URL)enume.nextElement();  
    return fileURL;
  }

  /**
   * Return the URL from a file path
   * @param fullPathTofile Full path to the file
   * @exception Exception
   * @return URL of the file
   */
  public static URL getFileURL(String fullPathTofile)
  throws Exception
  {
    return (new File(fullPathTofile)).toURL();
//    return new URL("file://"+fullPathTofile); DO NOT WORK WITH DIGESTER .....
  }
  
  /**
   * Rename a file
   * Full source and target paths must be specified
   * @param sSourcePath Path to source file
   * @param sTargetPath Path to target file
   * @return boolean: true if operation successful, else false
   */
  public static boolean renameFile(String sSourcePath, String sTargetPath)
  {
    File sourceFile = new File(sSourcePath);
    File targetFile = new File(sTargetPath);
    boolean result = sourceFile.renameTo(targetFile);
    if (!result) log.error("renameFile: Could not rename file '"+sSourcePath+"' to '"+sTargetPath+"'");
    return result;
  }

  /**
   * Delete a file
   * Full path must be specified
   * @param sPath Path to the file
   * @return boolean: true if operation successful, else false
   */
  public static boolean deleteFile(String sPath)
  {
    File aFile = new File(sPath);
    boolean result = aFile.delete();
    if (!result) log.error("deleteFile: Could not delete file '"+sPath+"'");
    return result;
  }

  /**
   * Check if a file exist
   * Full path must be specified
   * @param sPath Path to file
   * @return boolean: true if the file exist, else false
   */
  public static boolean checkFile(String sPath)
  {
    File aFile = new File(sPath);
    return (aFile.exists() && (aFile.isFile()));
  }

  /**
   * This method does not actually create a file but return a handler on a file
   * and create the whole path to the file if needed
   * @param sPath Path to file
   * @return File ready to be written
   */
  public static File createFile(String sPath)
  throws IOException
  {
    File file = new File(sPath);   
    if (!file.getParentFile().exists()) 
    {
      boolean result = FileUtils.createDirectory(String.valueOf(file.getParentFile()));
      if (!result) throw new IOException("Could not create file "+sPath+": Could not create parent directory "+file.getParentFile());
    }
    return file;
  }

  /**
   * Check if a directory exist
   * Full path must be specified
   * @param sPath Path to directory
   * @return boolean: true if the file exist, else false
   */
  public static boolean checkDirectory(String sPath)
  {
    File aFile = new File(sPath);
    return (aFile.exists() && (aFile.isDirectory()));
  }
  
  /**
   * Create a directory
   * Full path must be specified
   * @param sPath Path to directory
   * @return boolean: true if operation successful, else false
   */
  public static boolean createDirectory(String sPath)
  {
    File file = new File(sPath);
    return file.mkdirs();
  }

  /**
   * Delete a directory
   * Full path must be specified
   * @param sPath Path to directory
   * @return boolean: true if operation successful, else false
   */
  public static boolean deleteDirectory(String sPath)
  {
    File unDirectory = new File(sPath);
    File desFiles[] = unDirectory.listFiles();

    if (desFiles!=null)
    {
      for (int i=0; i<desFiles.length; i++) desFiles[i].delete();
    }
    
    boolean result = unDirectory.delete();
    if (!result) log.error("deleteDirectory: Could not delete directory '"+sPath+"'");
    
    return result;
  }

  /**
   * Create a zip file of the specified path
   * Full path must be specified
   * @param sPath Path to file or directory
   */
  public static void zipFile(String sPath)
  {
    int sChunk = 8192;

    try 
    {
      FileOutputStream out = new FileOutputStream(sPath+".zip");
      ZipOutputStream zipout = new ZipOutputStream(out);

      zipout.setLevel(Deflater.BEST_COMPRESSION);
      zipout.putNextEntry(new ZipEntry(sPath));

      byte[] buffer = new byte[sChunk];

      FileInputStream in = new FileInputStream(sPath);
      int length;
      while ((length = in.read(buffer, 0, sChunk)) != -1)
      {
        zipout.write(buffer, 0, length);
      }
      in.close();
      zipout.finish();
      zipout.close();
    }
    catch (Exception e) 
    {
      log.error("zipFile:", e);
    }
  }

  /**
   * Merge all files contained in a directory into a single file
   * @param inputDirectory directory containing the files to merge
   * @param outputFile target file
   */
  public static void mergeDirectoryFiles(String inputDirectory, String outputFile)
  {
    mergeDirectoryFiles(inputDirectory, outputFile, null);
  }
  
  /**
   * Merge all files contained in a directory into a single file
   * @param inputDirectory directory containing the files to merge
   * @param outputFile target file
   * @param pattern Pattern filter
   */
  public static void mergeDirectoryFiles(String inputDirectory, String outputFile, String pattern)
  {
    try
    {
      if (!checkDirectory(inputDirectory))
      {
        log.error("mergeDirectoryFiles: Could not find directory "+inputDirectory);
        return;
      }
      
      File rep = new File(inputDirectory);
      File[] files = rep.listFiles();

      File newFile = new File(outputFile);
      FileOutputStream fos = new FileOutputStream(newFile);
      PrintWriter out = new PrintWriter(fos);
                    
      mergeFiles(files, out, pattern);
            
      out.close();
    }
    catch(Exception e)
    {
      log.error("mergeDirectoryFiles:", e);
    }                 
  }

  /**
   * Merge files into a PrintWriter
   * @param listFiles Array of files to merge
   * @param out Output writer
   */
  private static void mergeFiles(File[] listFiles, PrintWriter out, String pattern) 
  throws Exception
  {  
    for (int i=0; i<listFiles.length; i++)
    {
      String line;
      if (log.isDebugEnabled()) log.debug("mergeFiles: Merging '"+listFiles[i].getName()+"'");

      if (listFiles[i].isDirectory())
      {
        mergeFiles(listFiles[i].listFiles(), out, pattern);
      } 
      else 
      {
        if (!StringUtils.exists(pattern) || 
            (StringUtils.matchPattern(pattern, listFiles[i].getName(), false)))
        {
          BufferedReader in = new BufferedReader(new FileReader(listFiles[i]));
          while ((line = in.readLine()) != null)
          {
            out.println(line);
          }
          out.print("\n");
          in.close();
        }
      }
    }
  }

  /**
   * Return all files matching the pattern from a path (and its subdirectories)
   * @param path The path to begin the search
   * @param pattern The pattern to filter files
   * @param isCaseSensitive true for case-sensitive search, else false
   */
  public static Collection listFiles(String path, String pattern, boolean isCaseSensitive)
  throws Exception
  {
    Collection result = new ArrayList();
    File root = new File(path);

    File[] files = root.listFiles();
    for (int i=0; i<files.length; i++)
    {
      String filePath = files[i].getPath();
      String fileName = files[i].getName();
      if (FileUtils.checkDirectory(filePath)) result.addAll(listFiles(filePath, pattern, false));
      else
      {
        boolean matchPattern = true;
        String tmpPattern = isCaseSensitive?pattern:pattern.toUpperCase();
        String tmpFileName = isCaseSensitive?fileName:fileName.toUpperCase();
        
        if (StringUtils.exists(pattern)) matchPattern = StringUtils.matchPattern(tmpPattern, tmpFileName, true);
//        if (matchPattern) result.add(new File(filePath));
        if (matchPattern) result.add(files[i]);
      }
    }

    return result;
  }

  /**
   * Return a file as a String
   * @param file File to convert
   * @return String representation of the file
   */
  public static String toString(File file) 
  throws Exception
  {  
    StringBuffer result = new StringBuffer();
    String line = null;
    BufferedReader in = new BufferedReader(new FileReader(file));
    while ((line = in.readLine()) != null) result.append(line).append("\n");
    return result.toString();
  }

////////////////////////////////////////////////////////////////////////////////
//  REPLACE
////////////////////////////////////////////////////////////////////////////////

  /**
   * Replace occurrences of the input patterns by their replacement 
   * in the given in file
   * @param in The file to process
   * @param out The output file
   * @param patternReplacements Map containing patterns and their replacements
   */
  public static void replacePatternInFile(File in, File out, Map patternReplacements)
  throws Exception
  {
    String inString = toString(in);
    String result = StringUtils.replacePattern(inString, patternReplacements);
    PrintWriter pw = new PrintWriter(new FileOutputStream(out), true);
    pw.print(result);    
    pw.close();    
  }

  /**
   * Replace occurrences of the input pattern by its replacement 
   * in the given in file
   * @param root The root path to start recursive search
   * @param filePattern The pattern to filter files
   * @param isCaseSensitive true for case-sensitive search, else false
   * @param patternToReplace The pattern to replace in the files
   * @param replacement The replacement of the pattern
   * @param doBackup Keep a backup of the original file ?
   */
  public static void replacePatternInFile(String root, 
                                          String filePattern, 
                                          boolean isCaseSensitive,
                                          String patternToReplace, 
                                          String replacement, 
                                          boolean doBackup)
  throws Exception
  {
    Map patternReplacements = new HashMap();
    patternReplacements.put(patternToReplace, replacement);
    replacePatternInFile(root, filePattern, isCaseSensitive, patternReplacements, doBackup);
  }
  
  /**
   * Replace occurrences of the input patterns by their replacement 
   * in the given in file
   * @param root The root path to start recursive search
   * @param filePattern The pattern to filter files
   * @param isCaseSensitive true for case-sensitive search, else false
   * @param doBackup Keep a backup of the original file ?
   * @param patternReplacements Map containing patterns and their replacements
   */
  public static void replacePatternInFile( String root, 
                                    String filePattern, 
                                    boolean isCaseSensitive,
                                    Map patternReplacements, 
                                    boolean doBackup)
  throws Exception
  {
    // Get the file list
    Collection files = listFiles(root, filePattern, isCaseSensitive);
    Iterator it = files.iterator();
    while (it.hasNext())
    {
      File inFile = (File)it.next();

      String directory = String.valueOf(inFile.getParentFile());
      String fileName = inFile.getName();
      String backupFileName = "~"+inFile.getName()+".bak";
      String tempFileName = "~"+inFile.getName()+".tmp";

      // Processing temporary file
      File tmpFile = new File(directory+File.separator+tempFileName);
      replacePatternInFile(inFile, tmpFile, patternReplacements);
      
      if (doBackup)
      {
        // Renaming original file for backup
        File bakFile = new File(directory+File.separator+backupFileName);
        if (!inFile.renameTo(bakFile)) throw new Exception("Could not create backup file "+bakFile);
      }
      else 
      {
        // Delete original file
        if (!inFile.delete()) throw new Exception("Could not delete the origin file "+inFile);
      }

      // Rename the temporary file to the original file name
      File outFile = new File(directory+File.separator+fileName);
      if (!tmpFile.renameTo(outFile)) throw new Exception("Could not rename temp file "+tmpFile+" to "+outFile);
    }
  }


}
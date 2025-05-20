package framework.util;

import java.awt.Image;
import java.awt.Frame;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;


/**
 * Image utility class
 * 
 * @author Laurent BOIS
 * @version $Revision: 1.6 $ $Date: 2004/06/17 23:28:51 $
 */
public final class ImageUtils 
{

  // Logger
  private static Log log = LogFactory.getLog(FileUtils.class);

  /**
   * Protected constructor
   */
  protected ImageUtils()
  {
  }
  
  public static String[] getImageMetrics(File f) 
  {
    Image image = null;
    int  imageWidth = 1;//imageFace.getWidth(srcPanel);
    int imageHeight = 1;
    String[] metrics = new String[2];
    Frame _frame = null;  
    _frame = new Frame();
    _frame.addNotify();
    
    MediaTracker mt = new MediaTracker(_frame);
    
    if (log.isDebugEnabled()) log.debug("getting image... ");
    try 
    {
      image = getImage(f);  
    } 
    catch (IOException ex) 
    {
      log.error("Error while getting image "+ex.getMessage());
      return null;
    }
    
    mt.addImage(image, 0);
    
    try 
    {
      mt.waitForAll();
    } 
    catch (InterruptedException ex) 
    {
      log.error(ex);
    } 
    
    imageWidth = image.getWidth(_frame);//imageFace.getWidth(srcPanel);
    imageHeight = image.getHeight(_frame);//imageFace.getHeight(null);   
    metrics[0] = new String(String.valueOf(imageWidth));
    metrics[1] = new String(String.valueOf(imageHeight));  
    
    return metrics;
  }  

  public static Image getImage(File f) 
  throws IOException
  {
    Image image = getImageObject(f);
    return image;
  }  
  
  private static Image getImageObject(File f) 
  {
    Image myImage = null;
    try 
    {
      myImage = Toolkit.getDefaultToolkit().getImage(f.getAbsolutePath()); 
    } 
    catch (Exception ex) 
    {
      //ex.printStackTrace();
      log.error("ImageUtils.getImageObject() :: Exception: "+ex.getMessage());
    } 
    return myImage;
  }
  
  public static void resizeImage(String srcFilename, double scale, String destFilename) 
  throws IOException
  {
    ScaledImage image = new ScaledImage(srcFilename, scale);
    image.write(destFilename, destFilename.substring(destFilename.lastIndexOf(".")+1));
  }  

}



class ScaledImage  
{
  public static final String JPEG = "jpeg";
  public static final String JPE = "jpe";
  public static final String JPG = "jpg";  
  public static final String GIF = "gif";  
  public static final String PNG = "png";  
  
  private BufferedImage bimage;
  private boolean resized=false;
  private int srcHeight = 0;
  private int srcWidth = 0;
  private int height = 0;
  private int width = 0;
  private double scale = 0.0;  

  protected  ScaledImage(String srcFilename) 
  {
    this(srcFilename, 1.0);
  }  

  protected ScaledImage(String srcFilename, double scale) 
  {
    if (srcFilename == null || srcFilename.length() == 0) 
    {
      throw new RuntimeException("No filename!");
    }
    File src = new File(srcFilename);
    if (!src.exists()) 
    {
      throw new RuntimeException("File don't exist!");
    }

    try 
    {
      bimage = ImageIO.read(src);
    } 
    catch (Exception ex) 
    {
      throw new RuntimeException(ex);
    }

    srcHeight = bimage.getHeight();
    srcWidth = bimage.getWidth();
    setScale(scale);
  }  

  public void write(String destFilename, String format)
  {
    if (destFilename == null || destFilename.length() == 0) 
    {
      throw new RuntimeException("No filename!");
    }

    if (!JPEG.equals(format) && !JPG.equals(format) && !JPE.equals(format) && !PNG.equals(format)) 
    {
      throw new RuntimeException("Wrong file format!");
    }    

    BufferedImage bimage2 = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = (Graphics2D)bimage2.getGraphics();
    if (resized) 
    {
      g2d.scale((double) width / srcWidth, (double)height / srcHeight);
    } 
    else 
    {
      g2d.scale(scale, scale);
    }

    g2d.drawImage(bimage, 0, 0, new JFrame());    
    try
    {
      ImageIO.write(bimage2, format, new File(destFilename));
    } 
    catch (Exception ex) 
    {
      throw new RuntimeException(ex);
    }
  } 

  public void resize(int width, int height) {
  if (width < 1) {
  width = 1;
  }
  if (height < 1) {
  height = 1;
  }
  this.width = width;
  this.height = height;
  
  resized =  true;
  }  
  
  public double getScale() 
  {
    if (resized) 
    {
      return 0.0;
    }
    return scale;
  }  
  
  public void setScale(double scale) 
  {
    if (scale < 0.01) 
    {
      scale = 0.01;
    } 
    else if (scale > 16.0) 
    {
      scale = 16.0;
    }
  
    this.scale = scale;
    height = (int) (srcHeight * scale);
    width = (int) (srcWidth * scale);
    resized = false;
  }  

  public int getHeight() 
  {
    return height;
  }  
  
  public int getWidth() 
  {
    return width;
  }  
  
  public int getSrcHeight() 
  {
    return srcHeight;
  }  
  
  public int getSrcWidth() 
  {
    return srcWidth;
  }  

}
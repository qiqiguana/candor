package teder;

/*
 * TederData - Game tile editor data
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
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class TederData
{  
  // height and width in tiles
  private int gWidth;
  private int gHeight;
  
  private int tileWidth;
  private int tileHeight;

  ArrayList layers;
  
  public static final int NUM_TILES = 10;
  Image tileImage; // a vertical row of images

  public int hwalls[];
  public int vwalls[];
  
  public boolean validFile=false;
  public String fileName;
  public boolean hasChanged=false;
  
  public TederData(int width,int height,int tileWidthInit,int tileHeightInit,String tileImageName,Component forTracking)
    {
    gWidth = width;
    gHeight = height;
    tileWidth = tileWidthInit;
    tileHeight = tileHeightInit;
    
    layers = new ArrayList();

    
    resize(gWidth,gHeight);
    
    Toolkit tk = Toolkit.getDefaultToolkit();
    tileImage = tk.getImage(tileImageName);
	 // set up the media tracker to wait for the image
    MediaTracker tracker = new MediaTracker(forTracking);
    tracker.addImage(tileImage,0);

    // wait for the image to load
    try
      {
      tracker.waitForID(0);
      }
    catch (InterruptedException e)
      {
      e.printStackTrace();
      }
    }

  public int getWidth()
    {
	return gWidth;
	}
  public int getHeight()
    {
    return gHeight;
    }
  public int size()
    {
    return gWidth*gHeight;
    }
  public void resize(int w,int h)
    {
    // loop through the layers and set the size on each layer
    Iterator it = layers.iterator();
    while (it.hasNext())
      {
      TederLayer ttl = (TederLayer) it.next();
      ttl.resize(w,h);
      }
    gWidth = w;
    gHeight = h;

    hwalls = new int[gWidth*(gHeight+1)];
    vwalls = new int[(gWidth+1)*gHeight];
    
    hasChanged = true;
    }

  public TederLayer addLayer()
    {
    TederLayer ttl = new TederLayer(gWidth,gHeight);
    layers.add(ttl);
    return ttl;
    }
  
  public ArrayList getLayers()
    {
    return layers;
    }

  public int getTileWidth()
    {
    return tileWidth;
    }
  public int getTileHeight()
    {
    return tileHeight;
    }
  public int getMapPixelWidth()
  {
    return gWidth*tileWidth;
  }
  public int getMapPixelHeight()
  {
    return gHeight*tileHeight;
  }
  public void drawTileImage(Graphics g, int x, int y,int tnum,ImageObserver obs)
    {
    if ((tileImage!=null) && (tileImage.getWidth(obs)>0))
      {
      //System.out.println("tileImage = "+tileImage+" wid="+tileImage.getWidth(obs)+" x="+x+" y="+y+" twid="+tileWidth+" thei="+tileHeight);
      g.drawImage(tileImage, x,y,x+tileWidth,y+tileHeight,0,tnum*tileHeight,tileWidth,tnum*tileHeight+tileHeight,obs);
      }
    }

  public void setHasChanged(boolean b)
  {
    hasChanged = b;
  }
  public boolean getHasChanged()
  {
    return hasChanged;
  }
  public boolean isValidFile()
  {
    return validFile;
  }
  
  public void setFileName(String fname)
  {
    fileName = fname;
  }
  
  public void doSave()
    {
    int i;
    try
      {
      FileOutputStream fos = new FileOutputStream(fileName);
      DataOutputStream dos = new DataOutputStream(fos);

      TederLayer tl = (TederLayer) layers.get(0);
      dos.writeInt(tl.wid);
      dos.writeInt(tl.hei);

      for (i=0;i<tl.wid*tl.hei;i++)
        {
        dos.writeByte(tl.tiles[i]);
        }
      for (i=0;i<tl.wid*tl.hei;i++)
        {
        dos.writeInt(hwalls[i]);
        }
      for (i=0;i<tl.wid*tl.hei;i++)
        {
        dos.writeInt(vwalls[i]);
        }
      fos.close();
      dos.close();
      }
    catch (Exception e)
      {
      e.printStackTrace();
      }
    }

  public void doLoad()
    {
    doLoad(fileName);
    }
  public void doLoad(String fname)
    {
    TederLayer tl = (TederLayer) layers.get(0);
    doLoadSingleLayer(tl,fname);
    
    validFile = true;
    }
  public void doLoadSingleLayer(TederLayer tl, String fname)
    {
    int i;
    try
      {
      FileInputStream fis = new FileInputStream(fname);
      DataInputStream dis = new DataInputStream(fis);

      tl.wid = dis.readInt();
      tl.hei = dis.readInt();
      gWidth = tl.wid;
      gHeight = tl.hei;
      
      resize(tl.wid,tl.hei);
      
      for (i=0;i<tl.wid*tl.hei;i++)
        {
        tl.tiles[i] = dis.readByte();
        }
      for (i=0;i<tl.wid*tl.hei;i++)
        {
        hwalls[i] = dis.readInt();
        }
      for (i=0;i<tl.wid*tl.hei;i++)
        {
        vwalls[i] = dis.readInt();
        }

      fis.close();
      dis.close();
      }
    catch (Exception e)
      {
      e.printStackTrace();
      }    
    }
}

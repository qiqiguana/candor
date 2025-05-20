package teder;

/*
 * TederLayer - Game tile editor - tile layer
 *
 * By: Wood Harter - Feb 22, 2006
 * Created for cpsc370 at Chapman University
 * http://www.gamedev370.com
 * (c) copyright 2006 - W. Wood Harter
 *
 * Licensed under GNU General Public License
 * http://www.gnu.org *
 * 
 */

public class TederLayer
  {
  public int wid;
  public int hei;

  public byte tiles[];

  public TederLayer(int width, int height)
    {
    wid = width;
    hei = height;

    tiles = new byte[wid*hei];
    int i;
    for (i=0;i<tiles.length;i++)
      {
        tiles[i] = -1;
      }
    }

  public void resize(int width, int height)
  {
    // WARNING - Resize loses all tile data
    //         - need more robustness
    wid = width;
    hei = height;
    tiles = new byte[wid*hei];
    
    int i;
    for (i=0;i<wid*hei;i++)
    {
      tiles[i] = -1;
    }
  }
 
  public void setTileNum(int idx,int tnum)
    {
    tiles[idx] = (byte) tnum;
    }
  public int getTileNum(int idx)
    {
    return (int) tiles[idx];
    }
  
  }

package teder;

/*
 * TederPanel - Game tile editor panel
 *                This is the MVC view
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
import java.util.*;
import javax.swing.*;

public class TederPanel extends JPanel implements MouseListener,Scrollable

  {
  // x and y offsets in screen space
  int offx;
  int offy;

  // the model information
  TederData ttd;

  // the default layer
  TederLayer ttlDef;

  public int curTile=0;

  public TederPanel(TederData ttdInit)
    {
    ttd = ttdInit;

    // default testing
    ttlDef = ttd.addLayer();
    int idx;
    for (idx=0;idx<9;idx++)
      {
      ttlDef.setTileNum(idx,idx);
      }
    // set a diagnol
    int i;
    idx = ttd.getWidth()+1;
    for (i=0;i<9;i++)
      {
      ttlDef.setTileNum(idx,i);
      idx = idx + ttd.getWidth()+1;
      }

    ttd.hwalls[5] = 1;
    ttd.hwalls[70] = 1;
    ttd.setHasChanged(false);

    addMouseListener(this);
    
    resetPreferredSize();

    }
  public void resetPreferredSize()
  {
    setPreferredSize(new Dimension(ttd.getMapPixelWidth()+ttd.getTileWidth()*2,
              ttd.getMapPixelHeight()+ttd.getTileHeight()*2));
    revalidate();
  }
  /* ------ Scrollable ------ */
 
  public Dimension getPreferredScrollableViewportSize()  
    {
    Dimension dim = new Dimension(800,600);
    return dim;
    }
 
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
    {
    return ttd.getTileWidth();
    }
 
  public boolean getScrollableTracksViewportHeight() 
    {
    return false;
    }
    
  public boolean getScrollableTracksViewportWidth() 
    {
    return false;
    }
    
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
    {
    return ttd.getTileWidth();
    }
  
  /* ------ Scrollable ------ */

  /* ------ MouseListener ------ */
  public void mouseClicked(MouseEvent e)
    {
    //System.out.println("clicked x="+e.getX()+" y="+e.getY());
    // what is the actual tile??
    int col = e.getX()/ttd.getTileWidth();
    int row = e.getY()/ttd.getTileHeight();

    ttlDef.setTileNum(row*ttd.getWidth()+col,curTile);
    
    ttd.setHasChanged(true);

    repaint();
    }
  public void mouseEntered(MouseEvent e)
    {
    }
  public void mouseExited(MouseEvent e)
    {
    }
  public void mousePressed(MouseEvent e)
    {
    }
  public void mouseReleased(MouseEvent e)
    {
    }

  /* ------ MouseListener ------ */

  public void paintComponent(Graphics g)
    {
    int i;

    // clear the panel
    g.setColor(Color.black);
    g.fillRect(0,0,getWidth(),getHeight());

    // draw a white line around the tile area
    g.setColor(Color.white);
    g.drawRect(0,0,ttd.getMapPixelWidth(),ttd.getMapPixelHeight());
    //System.out.println("ttp painting "+this.getWidth()+" bounds="+this.bounds());
    //g.drawLine(0,0,500,500);
    ArrayList layers = ttd.getLayers();
    Iterator it = layers.iterator();
    while (it.hasNext())
      {
      TederLayer ttl = (TederLayer) it.next();
      // draw all the tiles on the layer
      int idx;
      for (idx=0;idx<ttd.size();idx++)
        {
        int tilenum = ttl.getTileNum(idx);
        // negative tiles are transparent
        if (tilenum>=0)
          {
          int xpos = (idx%ttd.getWidth())*ttd.getTileWidth()+offx;
          int ypos = (idx/ttd.getWidth())*ttd.getTileHeight()+offy;
          //System.out.println("drawing tile num="+tilenum);
          //System.out.println("col="+(idx%ttd.getWidth())+" col="+(idx/ttd.getHeight()));

          ttd.drawTileImage(g,xpos,ypos,tilenum,null);
          }
        }
      } // while
      for (i=0;i<ttd.size();i++)
        {
        if (ttd.hwalls[i] == 1)
          {
          int row = i / ttd.getWidth();
          int col = i % ttd.getWidth();

          g.setColor(Color.red);
          g.fillRect(col*64,row*64,64,20);
          }
        }
    } // paint
  }

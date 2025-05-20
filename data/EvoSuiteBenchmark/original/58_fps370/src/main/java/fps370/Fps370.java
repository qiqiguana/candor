/**
 * Fps370
 * 3D game template
 *
 * Created for cpsc370 at Chapman University
 * http://www.gamedev370.com
 * (c) copyright 2006 - W. Wood Harter
 *
 * Licensed under GNU General Public License
 * http://www.gnu.org
 *
 * This is the main application for fps370
 */
package fps370;

import java.awt.*;
import javax.swing.*;

public class Fps370 extends JFrame
  {
  
  public Fps370()
    {
    super("Fps370");
 
    // get the container for the frame
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    
    // create our panel
    Fps370Panel p = new Fps370Panel(this);
 
    // add out panel to the container
    c.add(p, BorderLayout.CENTER);

    // set frame properties, pack and show
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
    }
  
  public static void main(String[] args)
    {
    // create the jframe
    Fps370 app = new Fps370();
    }
  }
  
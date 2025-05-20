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
 * This class wakes up on a regular interval for game updates
 */
package fps370;

import java.util.*;
import javax.media.j3d.*;

public class Fps370Ticker extends Behavior
  {
  private int delay;
  private WakeupCondition timer;
  private Fps370Panel panel;

  public Fps370Ticker(int delayInit, Fps370Panel panelInit)
    {
	panel = panelInit;
	delay = delayInit;
	timer = new WakeupOnElapsedTime(delay);
	}

  public void initialize()
    {
	wakeupOn(timer);
	}
  public void processStimulus( Enumeration criteria)
    {
	panel.tick();    // send the event to update

	wakeupOn(timer); // reset the timer
    }
  }
/*
 * Created on 08.01.2005
 *
 */
package ch.bluepenguin.email.client.tapestry;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;

/**
 * @author Christian
 * 
 */
public class Home extends BasePage {
   public void listFolders(IRequestCycle cycle) {
   	 Visit visit = (Visit)this.getVisit();
   	 visit.setUserID("unkown");
   	 
     cycle.activate("MessageView");
   }
   
}

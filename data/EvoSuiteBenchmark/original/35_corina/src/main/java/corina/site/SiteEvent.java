//
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package corina.site;

import java.util.EventObject;

/**
   An event object which indicates that a Site was modified.

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: SiteEvent.java,v 1.2 2004/01/18 18:10:30 aaron Exp $
*/

public class SiteEvent extends EventObject {
    /**
       Construct a new site event which indicates that a particular
       site has been modified.

       @param source the site which was modified
    */
    public SiteEvent(Site source) {
        super(source);
    }

    /**
       Return the site that was modified; this is simply a shortcut
       for <code>(Site) e.getSource()</code>.

       @return the site which was modified
    */
    public Site getSite() {
	return (Site) getSource();
    }
}

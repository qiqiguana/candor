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

package corina;

import java.util.EventObject;

/**
   An event signifying that a Sample has changed.  The Sample class's
   fire-* methods create these automatically, and no other class
   should have reason to create them.

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: SampleEvent.java,v 1.3 2004/01/18 17:53:47 aaron Exp $
*/
public class SampleEvent extends EventObject {
    /**
       Construct a new SampleEvent with the specified source.

       @param source the Sample this event is about
    */
    public SampleEvent(Sample source) {
	super(source);
    }
}

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

import java.util.EventListener;

/**
   An interface for views-of-a-samples to implement.  When a Sample is
   modified, one (or more) of these methods will be called, so the
   view can update itself.

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: SampleListener.java,v 1.2 2004/01/18 17:53:47 aaron Exp $
*/
public interface SampleListener extends EventListener {
    /**
       The starting/ending dates of this sample have changed.

       @param e the Event object
    */
    public void sampleRedated(SampleEvent e);

    /**
       One or more of the numbers of the dataset have changed.

       @param e the Event object
    */
    public void sampleDataChanged(SampleEvent e);

    /**
       One or more of the metadata fields has been changed (the
       element changed, or a key was added or removed).

       @param e the Event object
    */
    public void sampleMetadataChanged(SampleEvent e);
    // TODO: indicate which, if just one/some?

    /**
       The elements were changed.

       @param e the Event object
    */
    public void sampleElementsChanged(SampleEvent e);

    // 
    // -> add other types of events here
    // 
}

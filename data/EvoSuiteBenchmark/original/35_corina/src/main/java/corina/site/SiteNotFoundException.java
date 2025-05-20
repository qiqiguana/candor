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
// Copyright 2003 Ken Harris <kbh7@cornell.edu>
//

package corina.site;

/**
   An exception thrown when a search for a site turns up no matches.
   When searching for multiple sites, returning an empty list is
   apporpriate, but when searching for a single site by code (for
   example), <a
   href="http://c2.com/cgi/wiki?NoNullBeyondMethodScope">simply
   returning null is bad</a>, so methods should throw (and catch) this
   exception instead.

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: SiteNotFoundException.java,v 1.2 2004/01/18 18:10:30 aaron Exp $
*/
public class SiteNotFoundException extends Exception {
    /** Make a new site not found exception. */
    public SiteNotFoundException() {
        // nothing needed (this only exists for the javadoc tag)
    }
}

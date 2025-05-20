/*
	MyGrid - platform for distributed grid computing
	
	Copyright (C) 2004 Kevin Ashley
	e-mail: kashliki@yahoo.com
	Web: http://mygrid.sourceforge.net

	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; either version 2
	of the License, or (at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
	
*/

/*
 * Created on Feb 20, 2004
 *
 */
 
/**
 * @author Kevin Ashley
 *
 */

package mygrid;

import java.lang.Runtime;

public class Performance {

  public static long ram() { //	Report memory usage statistics.
  	Runtime runtime = Runtime.getRuntime();
  	return runtime.freeMemory()/1024;
  }
  
  public static long cpu(){
	Runtime runtime = Runtime.getRuntime();
	long freemem = runtime.freeMemory();
	long totalmem = runtime.totalMemory();
	return totalmem-freemem;
  }


}

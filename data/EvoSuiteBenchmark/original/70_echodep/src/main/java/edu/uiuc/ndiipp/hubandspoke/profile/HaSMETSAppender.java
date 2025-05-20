/**
 * HaSMETSAppender.java
 * 
 * $Revision: 846 $
 * 
 * $Date: 2009-04-22 18:45:17 +0100 (Wed, 22 Apr 2009) $
 * 
 * Copyright (c) 2009, University Library, University of Illinois at 
 * Urbana-Champaign. All rights reserved. 
 * 
 * Developed by: The Hub and Spoke Project Group 
 *               University of Illinois Urbana-Champaign Library
 *               http://dli.grainger.uiuc.edu/echodep/hands/ 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to 
 * the following conditions: 
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimers. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimers in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the names of The Hub and Spoke Project Group, University of 
 * Illinois Urbana-Champaign Library, nor the names of its contributors may 
 * be used to endorse or promote products derived from this Software 
 * without specific prior written permission. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE. 
 */

package edu.uiuc.ndiipp.hubandspoke.profile;

import java.util.ArrayList;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.xml.XMLLayout;

/**
 * This class will collect log4j events for possible later use 
 * in PREMIS events or other sections of the METS file.  The events
 * use the XMLLayout
 * 
 * @author thabing
 *
 */
public class HaSMETSAppender extends AppenderSkeleton {
	ArrayList<LoggingEvent> events = null;
	
	public HaSMETSAppender() {
		super.closed=false;
		events = new ArrayList<LoggingEvent>();
		super.layout=new XMLLayout();
		super.name = "HaSMETSAppender";
	}
	
	public HaSMETSAppender(String name) {
		super.closed=false;
		events = new ArrayList<LoggingEvent>();
		super.layout=new XMLLayout();
		super.name = name;
	}

	@Override
	protected synchronized void  append(LoggingEvent event) {
		events.add(event);
	}

	@Override
	public void close() {
		super.closed=true;
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}
	
	public ArrayList<LoggingEvent> getEvents(){
		return events;
	}
	
	public void clearEvents(){
		events.clear();
	}
	
	public String getAllEventsAsXmlText(){
		String ret="<log4j:eventSet xmlns:log4j='http://jakarta.apache.org/log4j/' version='1.2' includesLocationInfo='false'>\n";
		for(int i=0;i<events.size();i++){
			ret = ret.concat(this.layout.format(events.get(i)).concat("\n"));
		}
		return ret.concat("</log4j:eventSet>\n");
	}
	
	public boolean hasEvents(){
		return !events.isEmpty();
	}
	
	public boolean hasErrors(){
		for(int i=0;i<events.size();i++){
			if(events.get(i).getLevel().equals(Level.ERROR))
				return true;
		}
		return false;
	}

}

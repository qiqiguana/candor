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
 * @author ashleyke
 *
 */

package mygrid;
import mygrid.web.ArrayOfContextElement;
import mygrid.web.ContextElement;
import mygrid.web.Job;

import org.apache.log4j.Logger;

public abstract class JobProcessor {
	protected static Logger logger = Logger.getLogger(JobProcessor.class);
	protected IJobProcessorEvent event;
	
	public JobProcessor(){}
	
	public JobProcessor(IJobProcessorEvent event) {
		this.event = event;
	}
	public abstract void run(Job job);

	protected static String getContextByName(Job job, String name) {
		for (int i = 0; i < job.getContext().getContextElement().length; i++) {
			if (job.getContext().getContextElement(i).getName().equals(name)) {
				return job.getContext().getContextElement(i).getContent();
			}
		}
		return null;
	}

	protected static void setContextElement(Job job, String name, String val) {
		// check if this element already exists
		for (int i = 0; i < job.getContext().getContextElement().length; i++) {
			ContextElement el = job.getContext().getContextElement()[i];
			if (el.getName() == name) {
				// element exists, just set it
				el.setContent(val);
				return;
			}
		}
		// need to add this element
		int oldContextLength = job.getContext().getContextElement().length;
		ContextElement[] newContext = new ContextElement[oldContextLength + 1];
		System.arraycopy(
			job.getContext().getContextElement(),
			0,
			newContext,
			0,
			oldContextLength);
		ContextElement newEl = new ContextElement();
		newEl.setName(name);
		newEl.setContent(val);
		newContext[oldContextLength] = newEl;
		ArrayOfContextElement cl = new ArrayOfContextElement();
		cl.setContextElement(newContext);
		job.setContext(cl);
	}

	/**
	 * @return Returns the event.
	 */
	public IJobProcessorEvent getEvent() {
		return event;
	}
	/**
	 * @param event The event to set.
	 */
	public void setEvent(IJobProcessorEvent event) {
		this.event = event;
	}
}

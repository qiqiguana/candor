/**
 * 
 */
package bierse.controller;

import bierse.model.Model;

/**
 * @author Rainer Friesen
 *
 */
public class TriggerThread extends Thread {

	private Model model;

	/**
	 * Constructor
	 */
	public TriggerThread(String name, Model model) {
		this.model = model;
		
	}

	/**
	 * Triggers the recalculation of the prices in the given interval
	 */
	public void run() {
		model.getLog().debug("Thread started");
		int timeGone = 1;
		while(model.isRunning()) {
			try {
				if(timeGone > model.getSettings().getTimeInterval() + 1) {
					model.recalculate();
					timeGone = 0;
				} else {
					sleep(1000);
				}
				model.setTimeLeft(model.getSettings().getTimeInterval()-timeGone);
				timeGone++;
			} catch(InterruptedException e) {
				model.getLog().warn(this, e);
			}
		}
		
	}



}

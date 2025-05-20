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

package mygrid;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.rpc.ServiceException;

import mygrid.web.GridJobStatus;
import mygrid.web.Job;
import mygrid.web.MyGridServiceLocator;
import mygrid.web.MyGridServiceSoap;
import mygrid.web._Complete;
import mygrid.web._Fail;
import mygrid.web._Logon;
import mygrid.web._Progress;
import mygrid.web._Request;
import mygrid.web._SetEngineInfo;

import org.apache.log4j.Logger;

/**
 * Class containing MyGrid.Engine implementation in Java.
 * 
 * @author ashleyke
 *
 */
public class Engine {

	static Logger logger = Logger.getLogger(Engine.class);

	MyGridServiceSoap grid; // proxy to remote MyGrid.Cluster
	Timer timer; // polling timer
	Hashtable jobsProcessors = new Hashtable();
	Hashtable processors = new Hashtable(); // actual processors loaded
	
	// holds jobs and associated processors	
	mygrid.web.Engine engine; // proxy to remote MyGrid.Engine
	String engineName; // current engine name (optional)
	long pollClusterTimeout = 0; // means no timeout
	PollJobs poller; // inner class for acquiring jobs
	boolean available = true; // availability

	class PollJobs extends TimerTask implements IJobProcessorEvent {

		Logger logger = Logger.getLogger(PollJobs.class);

		public synchronized void JobProcessorResponse(
			GridJobStatus status,
			Job job) {

			boolean makeAvailable = true;

			logger.debug("[JOB PROCESSOR EVENT] " + status);
			
			try {

				if (status == GridJobStatus.Complete) {
					_Complete args = new _Complete();
					args.setJob(job);
					grid.complete(args);
				} else if (status == GridJobStatus.Failed) {
					_Fail args = new _Fail();
					args.setJob(job);
					grid.fail(args);
				} else if (status == GridJobStatus.Progress) {
					makeAvailable = false;
					_Progress args = new _Progress();
					args.setJob(job);
					grid.progress(args);
				} else {
					logger.debug(
						"[JOB PROCESSOR EVENT] Unknown response from job processor.");
				}
			} catch (RemoteException ex) {
				logger.warn(
					"[ENGINE] Cannot communicate job status to the cluster.");
			}

			if (makeAvailable) {
				// remove the job and the processor from the hashtable
				logger.info("*** make available ");
				jobsProcessors.remove(job.getId());
				logger.info(
					"[ENGINE] job \""
						+ job.getName()
						+ "\" removed from internal queue.");
				available = true;
				notifyAll();
			}

		}

		boolean isAllowedForJob(Job job) {

			logger.info("[ENGINE] checking job compilance...");
			boolean allowed = false;
			try {
				if (job.getDiscriminators().getAllowedEngines() != null
					&& job
						.getDiscriminators()
						.getAllowedEngines()
						.getString()
						.length
						!= 0) {
					for (int i = 0;
						i
							< job
								.getDiscriminators()
								.getAllowedEngines()
								.getString()
								.length;
						i++) {
						String engineDiscriminator =
							job
								.getDiscriminators()
								.getAllowedEngines()
								.getString()[i];
						Pattern rx = Pattern.compile(engineDiscriminator);
						Matcher m = rx.matcher(engineName);
						if (m.matches()) {
							allowed = true;
							break;
						}
					}
				} else
					allowed = true;

				// engine is allowed if nothing is specified
				if (allowed) {
					logger.debug("[ENGINE] discriminator: engine name OK.");
				} else {
					logger.info("[ENGINE] discriminator: engine name " + job.getName());
					return false;
				}

				if (processors
					.containsKey(
						job.getDiscriminators().getProcessor().getType())) {
					try {
						// store it in the hashtable with Job as the key
						jobsProcessors.put(job.getId(), processors.get(job.getDiscriminators().getProcessor().getType()));
						allowed = true;
						logger.debug("[ENGINE] discriminator: job processor OK.");
					} catch (Exception ex) {
						logger.info(
							"[ENGINE] discriminator: job processor loading problem: job \""
								+ job.getName()
								+ "\" cannot run.",
							ex);
						return false;
					}
				} else {
					logger.info(
						"[ENGINE] discriminator: job processor: job \""
							+ job.getName()
							+ "\" cannot run.");
					return false;
				}

				//				/// restrictions, or "discriminators"
				//				engine.CPU = performance.getCurrentCpuUsage();
				//				engine.RAM = performance.getAvailableRAM();
				//
				//				if(viaWebService)
				//					webservice.BeginSetEngineInfo(engine.Name, engine.CPU, engine.RAM, engine.OS, null, null);
				//
				//				if(	engine.CPU <= job.Discriminators.CPU && // i.e. current CPU usage on the machine is less than Maximum required by the Job
				//					engine.RAM >= job.Discriminators.RAM &&
				//					(engine.OS == job.Discriminators.OS || job.Discriminators.OS == null) // same os, or os-independent
				//					)
				//				{
				//					logger.Log(Logger.Level.Debug, "[ENGINE] Discriminators. OK");
				//					allowed = true;
				//				}
				//				else
				//				{
				//					logger.Log(Logger.Level.Info, string.Format("[ENGINE] Discriminators: job \"{0}\" cannot run.", job.Name));
				//					return false;
				//				}

			} catch (Exception ex) {
				logger.info(
					"[ENGINE] job \"" + job.getName() + "\" cannot run.",
					ex);
				return false;
			}
			return allowed;
		}

		public void run() {

			logger.info("[ENGINE] polling jobs...");

			while (available == false) {
				try {
					synchronized(this){
						// wait for job processor to put value
						logger.debug("*** waiting... ");
						wait();
						logger.debug("*** availability signalled ");
					}
				} catch (InterruptedException e) {
				}
			}
			available = false;

			// poll jobs
			try {
				Job[] jobs =
					grid.availableJobs(null).getAvailableJobsResult().getJob();
				for (int i = 0; i < jobs.length; i++) {
					Job job = jobs[i];
					logger.info(
						"[ENGINE] acquiring cluster job: "
							+ job.getName()
							+ " "
							+ job.getId());
					if (isAllowedForJob(job)) {
						job.setCurrentEngineId(engine.getId());
						// request the job from the cluster
						_Request r = new _Request();
						r.setJob(job);
						if (grid.request(r).isRequestResult()) {
							// invoke the job processor (already loaded and stored) 
							(
								(JobProcessor) jobsProcessors.get(
									job.getId())).run(
								job);
						}
					}
				}
				logger.debug("*** finished polling for jobs " + available);
				available = true;
				//notifyAll();
			} catch (RemoteException ex) {
				logger.debug(
					"[ENGINE] Polling for jobs failed. Retrying...",
					ex);
			}

		}
	}

	public Engine(String name, int pollClusterSec) {
		engineName = name;
		pollClusterTimeout = pollClusterSec * 1000;
		logger.info("MyGrid.Engine (for Java) mygrid.sourceforge.net \n (c) Kevin Ashley 2004 \n GPL (GNU Public License)");
		poller = new PollJobs();
		probeJobProcessors();
	}

	void probeJobProcessors() {
		ResourceBundle bundle = ResourceBundle.getBundle("mygrid.engine") ;
		Enumeration bundleKeys = bundle.getKeys();
		
		while (bundleKeys.hasMoreElements()) {
		    String key = (String)bundleKeys.nextElement();
		    
		    if(key.startsWith("processor")){
		    	 String value = bundle.getString(key);
		    	 try
			      {
		    	 	JobProcessor processor = (JobProcessor)Class.forName(value).newInstance();
		    	 	processor.setEvent(poller);
			        processors.put(processor.getClass().getName(), processor) ;
			        logger.info( "+ processor: " + processor.getClass().getName() );
			      }
			      catch( Exception ex ) // InstantiationException or IllegalAccessException or ClassNotFoundException
			      {
			        logger.info( "Error " + ex + " trying to instantiate " + key ) ;
			        ex.printStackTrace();
  			      }
		    }
		}
	}

	public void Start(
		String user,
		String password,
		String webServiceUrl,
		String clusterUrl) {
		try {
			MyGridServiceLocator gridService = new MyGridServiceLocator();
			gridService.setMaintainSession(true); // CookieContainer in .NET
			grid = gridService.getMyGridServiceSoap();
			_Logon logon = new _Logon();
			logon.setUser(user);
			logon.setPassword(password);
			logon.setClusterUrl(clusterUrl);
			if (grid.logon(logon).isLogonResult()) {
				// we're logged onto the grid
				engine = grid.getEngine(null).getGetEngineResult();
				// discriminators
				engine.setRAM(Performance.ram());
				engine.setOS(System.getProperty("os.name"));
				engine.setName(engineName);
				_SetEngineInfo ei = new _SetEngineInfo();
				ei.setOs(engine.getOS());
				ei.setRam(engine.getRAM());
				ei.setName(engine.getName());
				grid.setEngineInfo(ei);
				timer = new Timer(); // daemon
				timer.schedule(poller, 0, pollClusterTimeout);
			}
		} catch (RemoteException ex) {
			logger.error("[ENGINE] Connection Error", ex);
		} catch (ServiceException ex) {
			logger.error("[ENGINE] Connection Error", ex);
		}
	}

}
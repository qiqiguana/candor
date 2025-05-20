package net.sourceforge.beanbin.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;
import org.apache.tools.ant.input.InputRequest;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.optional.PropertyFile;
import org.apache.tools.ant.taskdefs.optional.PropertyFile.Entry;

public class InstallBeanBin extends Task implements TaskContainer {
	private String propertyFile;
	private String rootDir;
	private String developmentMode;
	
	public void execute() throws BuildException {
		try {
			run();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException("SetJBossVariablesTask: " + e.getMessage(), e);
		}
	}

	private void run() throws Exception {
		String jbosshome = setJBossHome();
		List<String> servers = getServers(jbosshome);
		String installServers = getProject().getProperty("jboss.home.servers");
		if(installServers != null && !installServers.equals("")) {
			if(!isInstallServersValid(jbosshome, installServers)) {
				installServers = getInstallServers(jbosshome, servers);
			}
		} else {
			installServers = getInstallServers(jbosshome, servers);
		}				
		
		for(String server : getInstallServers(installServers)) {
			if(jbosshome.endsWith("/")) {
				jbosshome = jbosshome.substring(0, jbosshome.length() - 1);
			}
			copy(jbosshome, server, "beanbin.jar", "deploy");
			copy(jbosshome, server, "beanbin-aop.xml", "deploy");
			copy(jbosshome, server, "beanbin-ds.xml", "deploy");
			copy(jbosshome, server, "beanbin.properties", "conf");
			copy(jbosshome, server, "lucene-core-2.2.0.jar", "lib");
			if(developmentMode != null && developmentMode.equalsIgnoreCase("true")) {
				copy(jbosshome, server, "testbeanbin.jar", "lib");
			}
		}
	}

	private void copy(String jbosshome, String server, String file, String dir) {
		Copy copy = new Copy();
		copy.setTaskName("copy");
		copy.setProject(getProject());			
		copy.setTofile(new File(jbosshome + "/server/" + server + "/" + dir + "/" + file));			
		copy.setFile(new File(getRootDir() + "/" + file));
		copy.execute();
	}

	private String getInstallServers(String jbosshome, List<String> servers) {
		String installServers = getJBossServers("Which servers you wish to install to:", servers);
		if(isInstallServersValid(jbosshome, installServers)) {
			setBuildProperties("jboss.home.servers", installServers);	
		} else {
			installServers = getJBossServers("Servers entered were invalid. Try again:", servers);
			if(isInstallServersValid(jbosshome, installServers)) {
				setBuildProperties("jboss.home.servers", installServers);
			} else {
				fail("Servers entered were invalid: " + installServers);
			}
		}
		return installServers;
	}
	
	private boolean isInstallServersValid(String jbosshome, String servers) {		
		File dir = getServerDir(jbosshome);
		servers = servers.replaceAll("\\(", "");
		servers = servers.replaceAll("\\)", "");
		StringTokenizer tokenizer = new StringTokenizer(servers, ",\t ");
		if(!tokenizer.hasMoreTokens()) {
			return false;
		}
		
		while(tokenizer.hasMoreTokens()) {
			String server = tokenizer.nextToken();
			ServerFilenameFilter filter = new ServerFilenameFilter(server);
			File[] files = dir.listFiles(filter);
			if(files.length == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	private List<String> getInstallServers(String installServers) {
		List<String> servers = new ArrayList<String>();
		
		installServers = installServers.replaceAll("\\(", "");
		installServers = installServers.replaceAll("\\)", "");
		StringTokenizer tokenizer = new StringTokenizer(installServers, ",\t ");
		while(tokenizer.hasMoreTokens()) {
			servers.add(tokenizer.nextToken());		
		}
		
		return servers;
	}
	
	private class ServerFilenameFilter implements FilenameFilter {
		private String server;
		public ServerFilenameFilter(String server) {
			this.server = server;
		}
		
		public boolean accept(File dir, String name) {
			return name.equals(server);
		}
	}
	
	private List<String> getServers(String jbosshome) {
		File dir = getServerDir(jbosshome);
		if(dir.exists()) {
			List<String> servers = new ArrayList<String>();
			for(File file : dir.listFiles()) {
				if(file.isDirectory()) {
					servers.add(file.getName());
				}
			}
			if(servers.isEmpty()) {
				fail("No servers were found in directory " + dir.getAbsolutePath());
				return null;				
			} else {
				return servers;	
			}
		} else {
			fail("No servers were found in directory " + dir.getAbsolutePath());
			return null;
		}
	}
	
	private File getServerDir(String jbosshome) {
		String path = jbosshome + (jbosshome.endsWith("/") ? "server/" : "/server/");
		return new File(path);		
	}

	private String setJBossHome() throws IOException {
		String jbosshome = getProject().getProperty("jboss.home");
		if(jbosshome == null || jbosshome.equals("")) {
			jbosshome = getJBossHome("Please enter the location of your JBoss application server..");
		} else {
			if(isJBossHomeValid(jbosshome)) {
				return jbosshome;
			}
		}
		
		if(!isJBossHomeValid(jbosshome)) {
			jbosshome = getJBossHome("Please enter a valid 4.0.4.GA JBoss directory.");
		}
		if(!isJBossHomeValid(jbosshome)) {			
			fail("Go to http://www.beanbin.org and download the JBoss bundle release. No valid JBoss was found.");
			return null;
		}
		getProject().setProperty("jboss.home", jbosshome);
		setBuildProperties("jboss.home", jbosshome);
		return jbosshome;
	}

	private void fail(String message) {
		throw new BuildException(message);
	}
	
	private boolean isJBossHomeValid(String jbosshome) throws IOException {
		File dir = new File(jbosshome);
		if(dir.exists()) {
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.equals("readme.html");
				}
			};
			File[] files = dir.listFiles(filter);
			if(files.length == 1) {
				BufferedReader reader = new BufferedReader(new FileReader(files[0]));
				String line = null;
				while((line = reader.readLine()) != null) {
					if(line.indexOf("<title>") != -1) {
						if(line.indexOf("4.0.4.GA") != -1) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}

	private String getJBossHome(String prompt) {
		InputRequest req = new InputRequest(prompt);
		getProject().getInputHandler().handleInput(req);
		String jbosshome = req.getInput();
		return jbosshome;
	}
	
	private String getJBossServers(String message, List<String> servers) {
		String serversString = "(";
		for(String server : servers) {
			serversString += server += ",";
		}
		serversString = serversString.substring(0, serversString.length() - 1) + ")";
		InputRequest req = new InputRequest(message + " " + serversString);
		getProject().getInputHandler().handleInput(req);
		return req.getInput();
	}

	private void setBuildProperties(String property, String value) {
		PropertyFile pfile = new PropertyFile();
		pfile.setTaskName("propertyfile");		
		File file = new File("build.properties");
		pfile.setFile(file);
		Entry entry = pfile.createEntry();
		entry.setKey(property);
		entry.setValue(value);
		pfile.setProject(getProject());
		pfile.execute();
	}

	public void addTask(Task task) {
		task.execute();
//		getProject().getInputHandler().
	}

	public String getPropertyFile() {
		return propertyFile;
	}

	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}

	public String getDevelopmentMode() {
		return developmentMode;
	}

	public void setDevelopmentMode(String developmentMode) {
		this.developmentMode = developmentMode;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
}

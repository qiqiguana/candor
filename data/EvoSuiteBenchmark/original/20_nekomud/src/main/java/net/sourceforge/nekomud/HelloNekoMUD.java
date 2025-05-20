/*
 * HelloNekoMUD.java
 * Copyright 2008-2009 Patrick Meade
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.nekomud;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * HelloNekoMUD application. Displays a hello message to the standard output.
 * @author patrick
 */
public class HelloNekoMUD
{
	private static final Logger logger = LoggerFactory.getLogger(HelloNekoMUD.class);
	
	/**
	 * The hello message to be displayed to the standard output
	 */
	public static final String HELLO_MESSAGE = "Hello, NekoMUD!";
	
	/**
	 * Application entry point for HelloNekoMUD. Displays a hello message
	 * to the standard output and then terminates.
	 * @param args command-line arguments; ignored
	 */
	public static void main(String[] args)
	{
		try {
			PropertyConfigurator.configure("conf/log4j.properties");
			// log the fact that execution began
			logger.info("---------- Start nekomud: {} --------", new Date());
			// load up the Spring application context
			ApplicationContext applicationContext =
//				new FileSystemXmlApplicationContext("xml/nekomud-hello.xml");
				new FileSystemXmlApplicationContext(
					new String[] {
						"xml/nekomud-config.xml",
						"xml/nekomud-hello.xml",
						"xml/nekomud-logging.xml",
						"xml/nekomud-nio.xml",
						"xml/nekomud-reflect.xml"
					});
			// obtain a bean from Spring implementing the MessageWriter interface
			MessageWriter messageWriter = (MessageWriter)applicationContext.getBean("helloMessage");
			// wrap the System's PrintStream into a PrintWriter object
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));
			// write the message to the standard output stream
			messageWriter.writeMessage(printWriter);
			// flush the system-wrapping PrintWriter
			printWriter.flush();
			
//			NetworkService networkService = (NetworkService)applicationContext.getBean("networkService");
//			networkService.start();
		}
		finally {
			logger.info("---------- End nekomud: {} ----------", new Date());
		}
	}
}

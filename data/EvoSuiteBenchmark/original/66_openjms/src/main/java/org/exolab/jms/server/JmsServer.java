/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsServer.java,v 1.6 2006/02/23 11:17:40 tanderson Exp $
 */
package org.exolab.jms.server;

import java.io.PrintStream;
import javax.naming.NamingException;

import org.apache.log4j.xml.DOMConfigurator;

import org.exolab.jms.authentication.AuthenticationMgr;
import org.exolab.jms.authentication.UserManager;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.ConfigurationLoader;
import org.exolab.jms.config.LoggerConfiguration;
import org.exolab.jms.events.BasicEventManager;
import org.exolab.jms.gc.GarbageCollectionService;
import org.exolab.jms.lease.LeaseManager;
import org.exolab.jms.messagemgr.ConsumerManagerImpl;
import org.exolab.jms.messagemgr.DestinationCacheFactory;
import org.exolab.jms.messagemgr.DestinationConfigurator;
import org.exolab.jms.messagemgr.DestinationManagerImpl;
import org.exolab.jms.messagemgr.MessageMgr;
import org.exolab.jms.messagemgr.ResourceManager;
import org.exolab.jms.messagemgr.DestinationBinder;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.scheduler.Scheduler;
import org.exolab.jms.service.ServiceException;
import org.exolab.jms.service.ServiceManager;
import org.exolab.jms.service.Services;
import org.exolab.jms.service.ServiceThreadListener;
import org.exolab.jms.util.CommandLine;
import org.exolab.jms.util.Version;
import org.exolab.jms.common.threads.DefaultThreadPoolFactory;


/**
 * This class contains the main line for instantiating the JMS Server. It
 * dynamically detemrines, from the configuration information which of the
 * servers to implement and then calls the init method on them.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.6 $ $Date: 2006/02/23 11:17:40 $
 */
public class JmsServer {

    /**
     * The service manager.
     */
    private Services _services = new ServiceManager();

    /**
     * The configuration of this server.
     */
    private Configuration _config;


    /**
     * Construct a new <code>JmsServer</code>.
     *
     * @param config the server configuration
     */
    public JmsServer(Configuration config) {
        version();
        _config = config;
    }

    /**
     * Construct a new <code>JmsServer</code>, configured from the specified
     * configuration file.
     *
     * @param file configuration file name
     * @throws ServerException if the server cannot be created
     */
    public JmsServer(String file) throws ServerException {
        version();

        ConfigurationLoader loader = new ConfigurationLoader();
        try {
            _config = loader.load(file);
        } catch (Exception exception) {
            throw new ServerException("Failed to read configuration: " + file,
                                      exception);
        }
    }

    /**
     * Initialise the server
     *
     * @throws NamingException  if administered objects cannot be bound in JNDI
     * @throws ServiceException if the server cannot be initialised
     */
    public void init() throws NamingException, ServiceException {
        // initialise the logger
        LoggerConfiguration log = _config.getLoggerConfiguration();

        // @todo - need to do this in main(), to allow pluggable log factories
        // when embedding OpenJMS
        DOMConfigurator.configure(log.getFile());

        // initialise the services, and start them
        try {
            registerServices();
            _services.start();
        } catch (ServiceException exception) {
            throw new ServerException(
                    "Failed to start services", exception);
        }
    }

    /**
     * This is the main line for the JMS Server. It processes the command line
     * argument, instantiates an instance of the server class and calls the init
     * routine on it.
     */
    public static void main(String[] args) {
        try {
            CommandLine cmdline = new CommandLine(args);

            boolean helpSet = cmdline.exists("help");
            boolean versionSet = cmdline.exists("version");
            boolean configSet = cmdline.exists("config");

            if (helpSet) {
                usage();
            } else if (versionSet) {
                version();
            } else if (!configSet && args.length != 0) {
                // invalid argument specified
                usage();
            } else {
                String config = cmdline.value("config",
                                              getOpenJMSHome()
                                              + "/config/openjms.xml");
                JmsServer server = new JmsServer(config);
                server.init();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            // force termination of any threads
            System.exit(-1);
        }
    }

    public static void version() {
        System.err.println(Version.TITLE + " " + Version.VERSION);
        System.err.println(Version.COPYRIGHT);
        System.err.println(Version.VENDOR_URL);
    }

    /**
     * Print out information on running this sevice
     */
    protected static void usage() {
        PrintStream out = System.out;

        out.println("\n\n");
        out.println("=====================================================");
        out.println("Usage information for org.exolab.jms.server.JmsServer");
        out.println("=====================================================");
        out.println("\norg.exolab.jms.server.JmsServer");
        out.println("    [-config <xml config file> | -version | -help]\n");
        out.println("\t-config  file name of xml-based config file\n");
        out.println("\t-version displays OpenJMS version information\n");
        out.println("\t-help    displays this screen\n");
    }

    /**
     * Returns the services
     *
     * @return ServiceManager
     * @deprecated no replacement
     */
    protected Services getServices() {
        return _services;
    }

    /**
     * Initialise the services
     */
    protected void registerServices() throws ServiceException {
        _services.addService(_config);
        _services.addService(ServiceThreadListener.class);
        _services.addService(DefaultThreadPoolFactory.class);
        _services.addService(BasicEventManager.class);
        _services.addService(DatabaseService.class);
        _services.addService(Scheduler.class);
        _services.addService(LeaseManager.class);
        _services.addService(GarbageCollectionService.class);
        _services.addService(AuthenticationMgr.class);
        _services.addService(UserManager.class);
        _services.addService(DestinationCacheFactory.class);
        _services.addService(DestinationManagerImpl.class);
        _services.addService(ConsumerManagerImpl.class);
        _services.addService(ServerConnectionManagerImpl.class);
        _services.addService(ResourceManager.class);
        _services.addService(AdminConnectionFactory.class);
        _services.addService(AdminConnectionManager.class);
        _services.addService(MessageMgr.class);
        _services.addService(NameService.class);
        _services.addService(DestinationBinder.class);
        _services.addService(DestinationConfigurator.class);
        _services.addService(ConnectorService.class);
    }

    /**
     * Returns the value of the <code>openjms.home</code> system property. If
     * none is set, returns the value of the <code>user.dir</code> property.
     *
     * @return the value of the openjms.home system property
     */
    private static String getOpenJMSHome() {
        return System.getProperty("openjms.home",
                                  System.getProperty("user.dir"));
    }

}

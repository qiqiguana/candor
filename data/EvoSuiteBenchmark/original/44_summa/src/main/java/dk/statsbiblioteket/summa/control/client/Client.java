/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.control.client;

import dk.statsbiblioteket.summa.common.Logging;
import dk.statsbiblioteket.summa.common.configuration.Configurable.ConfigurationException;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.rpc.RemoteHelper;
import dk.statsbiblioteket.summa.common.shell.VoidShellContext;
import dk.statsbiblioteket.summa.common.util.DeferredSystemExit;
import dk.statsbiblioteket.summa.common.util.Security;
import dk.statsbiblioteket.summa.control.api.*;
import dk.statsbiblioteket.summa.control.api.bundle.BundleRepository;
import dk.statsbiblioteket.summa.control.bundle.*;
import dk.statsbiblioteket.util.Files;
import dk.statsbiblioteket.util.Logs;
import dk.statsbiblioteket.util.Strings;
import dk.statsbiblioteket.util.Zips;
import dk.statsbiblioteket.util.console.ProcessRunner;
import dk.statsbiblioteket.util.qa.QAInfo;
import dk.statsbiblioteket.util.rpc.ConnectionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * <p>Core class for running ClientManager clients.</p>
 *
 * <p>The client talks to the ClientManager server via a
 * {@link ControlConnection}.
 * Itself exposes a {@link ClientConnection} over RMI.</p>
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.QA_NEEDED,
        author = "mke")
public class Client extends UnicastRemoteObject implements ClientMBean {
    /** The serial version UID. */
    private static final long serialVersionUID = 68791684985L;
    /** Logger instance. */
    private static Log log = LogFactory.getLog(Client.class);

    /** Extension to use for old packages, used for rollback purposes.
     * Package ids are not allowed to end with this reserved string. */
    public static final String OLD_PKG_EXTENSION = ".old";

    /** <p>Integer property defining the timeout when waiting for services
     * to come up. The value is in seconds.</p>
     * <p><b>Important:</b> The service will be monitored synchronously
     * so don't set this value to high. 5-10 ought to do it. 30 should be
     * absolute max.</p>
     */
    public static final String CONF_SERVICE_TIMEOUT =
            "summa.control.client.servicetimeout";
    /** Default value for {@link #CONF_SERVICE_TIMEOUT}. */
    public static final int DEFAULT_SERVICE_TIMEOUT = 5;

    /**
     * If this property is {@code true} a backup will be stored when
     * removing services via the {@link #removeService(String)} method.
     * The backup is stored in the service's {@code artifacts} directory.
     * <p/>
     * The default value is {@code false}.
     */
    public static final String CONF_STORE_ARTIFACTS =
            "summa.control.client.storeartifacts";

    /**
     * Default value for {@link #CONF_STORE_ARTIFACTS} property.
     */
    public static final boolean DEFAULT_STORE_ARTIFACTS = false;

    private Status status;
    private BundleRepository repository;
    private BundleLoader loader;
    private ServiceManager serviceMan;
    private String id;
    private String hostname;
    private String basePath;
    private String tmpPath;
    private String servicePath;
    private String artifactPath; // Removed service packages
    private String persistentPath;
    private int serviceTimeout;
    private boolean storeArtifacts;


    // RMI-related data
    private String registryHost, clientId;
    private int registryPort, clientPort;

    private enum StartAction {
        RETRY,
        STARTED,
        BOOTSTRAP
    }

    /**
     * Create a client from a {@link Configuration} and expose it over RMI.
     * The various RMI properties needed are read from the {@link Configuration}
     * as defined in {@link ClientConnection}.
     * @param conf The configuration from which to extract RMI  properties.
     * @throws IOException If there is an error exposing the RMI service.
     */
    public Client(Configuration conf) throws IOException {
        super (getServicePort (conf));
        log.debug("Constructing client");
        log.trace("Home dir: " + new File(".").getAbsolutePath());

        Security.checkSecurityManager();

        registryHost = conf.getString(ClientConnection.CONF_REGISTRY_HOST, "localhost");
        registryPort = conf.getInt(ClientConnection.CONF_REGISTRY_PORT,
                                   ClientConnection.DEFAULT_REGISTRY_PORT);
        clientId = System.getProperty(ClientConnection.CONF_CLIENT_ID);
        clientPort = conf.getInt(ClientConnection.CONF_CLIENT_PORT, ClientConnection.DEFAULT_CLIENT_PORT);
        id = clientId;
        storeArtifacts = conf.getBoolean(CONF_STORE_ARTIFACTS,
                                         DEFAULT_STORE_ARTIFACTS);

        if (clientId == null) {
            throw new BadConfigurationException("System property '"
                                                + ClientConnection.CONF_CLIENT_ID
                                                + "' not set");
        }

        basePath = System.getProperty("user.dir");
        if (clientId.equals(new File(basePath).getName())) {
            // Good, client id and parent dir match as it should
            log.debug ("Client '" + id + "' using basePath '" + basePath + "'");
        } else {
            String msg = "Client id and working directory mismatch. Id is "
                         + clientId + " and working directory is "
                         + new File(basePath);
            Logging.fatal(log, "Client.constructor", msg);
            throw new ConfigurationException(msg);
        }

        tmpPath = basePath + File.separator + "tmp";
        servicePath = basePath + File.separator + "services";
        artifactPath = basePath + File.separator + "artifacts";
        persistentPath = new File(basePath + File.separator
                                  + ".." + File.separator
                                  + "persistent").getCanonicalPath();

        /* Create repository */
        Class<? extends BundleRepository> repositoryClass =
                                    conf.getClass(
                                        ClientConnection.CONF_REPOSITORY_CLASS,
                                            BundleRepository.class,
                                            RemoteURLRepositoryClient.class);
        repository = Configuration.create(repositoryClass, conf);

        /* Create bundle loader */
        loader = Configuration.create(BundleLoader.class, conf);

        validateConfiguration();

        /* Service related setup */
        serviceTimeout = conf.getInt(CONF_SERVICE_TIMEOUT,
                                     DEFAULT_SERVICE_TIMEOUT);
        serviceMan = new ServiceManager(conf);

        /* Find client hostname */
        hostname = RemoteHelper.getHostname();
        log.debug ("Found hostname: '" + hostname + "'");

        setStatus(Status.CODE.constructed,
                  "Setting up remote interfaces (rmi,jmx)",
                  Logging.LogLevel.DEBUG);

        RemoteHelper.exportRemoteInterface(this, registryPort, clientId);
        RemoteHelper.exportMBean(this);

        setStatus(Status.CODE.constructed, "Remote interfaces up",
                  Logging.LogLevel.DEBUG);

        // Make sure all needed directories are created
        String[] dirs = new String[]{tmpPath, servicePath, artifactPath,
                                     persistentPath};
        for (String dir: dirs) {
            File dirFile = new File(dir);
            if(!dirFile.mkdirs()) {
                log.warn("Directory '" + dirFile + "' was not created");
            }
            if (!dirFile.exists()) {
                throw new IOException("Could not create directory '"
                                      + dirFile.getAbsoluteFile() + "'");
            }
        }

        runAutoStartServices ();
    }

    /**
     * Private helper method, that starts all services in the bundle which has
     * the auto-start flag set to true.
     */
    private void runAutoStartServices () {
        setStatusRunning("Starting auto-start services");
        for (String instanceId : serviceMan) {
            BundleSpecBuilder spec = serviceMan.getBundleSpec(instanceId);

            if (spec == null) {
                log.error("Failed to read bundle spec for '"
                          + instanceId + "'");
                continue;
            }

            if (spec.isAutoStart()) {
                try {
                    log.info("Auto-starting service '" + instanceId + "'");

                    /* This method call will only start the service if it isn't
                     * already running: */
                    startService(instanceId, null);
                } catch (Exception e) {
                    log.error("Failed to auto-start service '" + instanceId
                              + "': " + e.getMessage(), e);
                }
            } else {
                log.debug("Service '" + instanceId + "' not scheduled for "
                          + "auto-start");
            }
        }

        setStatusIdle();
    }

    /**
     * Check that the states set by the configuration are good.
     * Throw an BadConfigurationException if something is ascrew.
     * @throws BadConfigurationException If the configuration provided to the
     * client is insufficient.
     */
    private void validateConfiguration() throws BadConfigurationException {
        if ("".equals(registryHost)) {
            throw new BadConfigurationException (this + ", "
                                                 + ClientConnection.CONF_REGISTRY_HOST
                                                 + " is empty");
        } else if (registryPort < 0) {
            throw new BadConfigurationException (this + ", "
                                                 + ClientConnection.CONF_REGISTRY_PORT
                                                + " < 0. Value "
                                                + registryPort);
        } else if ("".equals(clientId)) {
            throw new BadConfigurationException (this + ", " + ClientConnection.CONF_CLIENT_ID
                                                 + " is empty");
        } else if (clientPort < 0) {
            throw new BadConfigurationException (this + ", " + clientPort
                                                + " < 0. Value " + clientPort);
        } else if ("".equals(id)) {
            throw new BadConfigurationException (this + ", " + ClientConnection.CONF_CLIENT_ID
                                                 + " is empty");
        }  else if ("".equals(basePath)) {
            throw new BadConfigurationException (this + ", "
                                                 + ClientConnection.CONF_CLIENT_BASEPATH
                                                 + " is empty");
        }
    }

    /**
     * Private helper method, creates a valid RMI address from the given RMI
     * values registry host, registry port and client ID. 
     * @return A RMI address.
     */
    private String getRMIAddress () {
        return "//"+registryHost+":"+registryPort+"/"+ clientId;
    }

    /**
     * Read the port on which the client should expose its rmi service.
     *
     * This method is mainly here to be able to retrieve the service
     * port in the super() call in the constructor.
     *
     * @param conf The configuration from which to read
     * {@link #CONF_CLIENT_PORT}.
     * @return The port.
     * @throws ConfigurationException If {@link #CONF_CLIENT_PORT} cannot be
     * read.
     */
    private static int getServicePort (Configuration conf) {
        try {
            return conf.getInt(ClientConnection.CONF_CLIENT_PORT, ClientConnection.DEFAULT_CLIENT_PORT);
        } catch (Exception e) {
            Logging.fatal(log, "Client.getServicePort",
                          "Unable to read " + ClientConnection.CONF_CLIENT_PORT+ " from configuration", e);
            throw new ConfigurationException(
                "Unable to read " + ClientConnection.CONF_CLIENT_PORT + "from configuration", e);
        }
    }

    /**
     * Stopping all services. Which means killing all service by their ID.
     * Trying to un-export all remote interface and MBeans.
     * Finally stopping JVM.
     */
    @Override
    public void stop() {
        ConnectionContext<Service> connCtx = null;

        setStatus(Status.CODE.stopping, "Killing all services", Logging.LogLevel.INFO);
        for (String serviceId : serviceMan) {
            try {
                log.debug("Trying to kill service '" + serviceId + "'");
                connCtx = serviceMan.get(serviceId);
                connCtx.getConnection().kill();
                log.info("Service '" + serviceId + "' killed");
            } catch (Exception e) {
                log.error("Could not kill service '" + serviceId + "': " + e.getMessage(), e);
            } finally {
                if (connCtx != null) {
                    connCtx.unref();
                }
            }
        }
        log.info("Retracting remote interfaces");

        try {
            RemoteHelper.unExportRemoteInterface(id, registryPort);
        } catch (IOException e) {
            log.error("Failed to unexport RMI interface: " + e.getMessage(), e);
        }

        try {
            RemoteHelper.unExportMBean(this);
        } catch (Exception e) {
            String msg = "Failed to unregister MBean. Going on anyway. Error was: " + e.getMessage();
            if (log.isTraceEnabled()) {
                log.warn (msg, e);
            } else {
                log.warn(msg);
            }
        }


        setStatus(Status.CODE.stopped,
                  "All services down. Stopping JVM in " + DeferredSystemExit.DEFAULT_DELAY/1000 + "s",
                  Logging.LogLevel.WARN);
        new DeferredSystemExit(0);
    }

    /**
     * Returning status for this client.
     * @return This clients status.
     */
    @Override
    public Status getStatus() {
        if (log.isTraceEnabled()) {
            log.trace("Getting status for client");
        }
        return status;
    }

    /**
     * Deploy a service.
     * @param bundleId The bundle ID for the deployed service.
     * @param instanceId The instance ID.
     * @param configLocation The configuration location for the service which
     * should be deployed.
     * @return Instance ID.
     * @throws ServiceDeploymentException If there already exists a service
     * with the given instance ID.
     * @throws BundleLoadingException If error retrieve the bundle ID from the
     * repository. 
     */
    @Override
    public String deployService(String bundleId,
                                String instanceId,
                                String configLocation) {

        if (serviceMan.knows(instanceId)) {
            throw new ServiceDeploymentException("A service with instance id '" + instanceId + "' already exists");
        }

        setStatusRunning("Deploying service '" + bundleId + "' with config " + configLocation + ", and instanceId '"
                        + instanceId + "'");
        File tmpBundleFile;

        try {
            tmpBundleFile = repository.get(bundleId);
        } catch (IOException e) {
            setStatusIdle ();
            throw new BundleLoadingException(
                "Failed to retrieve '" + bundleId + "' from repository: " + e.getMessage (), e);
        }

        deployServiceFromLocalFile(instanceId, tmpBundleFile, configLocation);
        
        setStatusIdle();
        return instanceId;
    }

    /**
     * <p>If this call completes it is guaranteed that
     * {@link ServiceManager#getServiceDir} returns an existing bundle file.</p>
     *
     * <p>The local file is unpacked to {@code servicePath/bundleId}</p>
     *
     * @param instanceId The instanceId under which to deploy the service.
     * @param localFile The file to deploy.
     * @param configLocation Location for configuration, either an URL,
     *                       rmi address, or file path.
     * @throws BundleLoadingException If error while deploy files, eg. deploy
     * files that already exists.
     */
    public void deployServiceFromLocalFile(String instanceId, File localFile,
                                           String configLocation) {

        if (servicePath.equals(localFile.getParent())) {
            throw new BundleLoadingException(
                "Trying to deploy " + localFile + " which is already in the service directory "
                + servicePath +". Aborting deploy.");
        } else if (!localFile.exists()) {
            throw new BundleLoadingException("Trying to deploy non-existing file " + localFile  + ", aborting deploy.");
        }

        setStatusRunning ("Deploying '" + instanceId + "' from " + localFile);

        File tmpPkg = new File(tmpPath, instanceId);

        // Assert that we don't have collisions in the tmp dir
        if (tmpPkg.exists()) {
            try {
                log.debug ("Deleting temporary file '" + tmpPkg + "' to avoid"
                         + " collisions");
                Files.delete (tmpPkg);
            } catch (IOException e) {
                throw new BundleLoadingException(
                    "Failed to delete temporary file '" + tmpPkg + "' blocking the way. Bailing out on deploy.", e);
            }
        }

        // Unzip the file into the tmp directory, and set the instance id
        // in the spec file, as well as expanding the members of
        // <publicApi/> into the system property {@code java.rmi.server.codebase}
        try {
            Zips.unzip (localFile.toString(), tmpPkg.toString(), false);
            File specFile = new File(tmpPkg, "service.xml");
            BundleSpecBuilder builder = BundleSpecBuilder.open (specFile);

            builder.setInstanceId(instanceId);
            builder.expandCodebase(repository);

            builder.write (specFile);

        } catch (IOException e) {
            try {
                Files.delete(tmpPkg);
                throw new BundleLoadingException(
                    "Error deploying " + localFile + ". Purged " + tmpPkg + " from tmp dir", e);
            } catch (IOException ee) {
                log.error ("Failed to clean up after buggy deploy", ee);
                throw new BundleLoadingException(
                    "Error deleting file '" + tmpPkg + "' when cleaning up buggy deploy", e);
            }
        }

        // Check if the service is already deployed, ie if there already
        // is a service with the same instanceId
        File pkgFile = serviceMan.getServiceDir(instanceId);
        if (pkgFile.exists()) {
            reDeployService(instanceId, localFile);
        }

        // Move service bundle in place in services/<instanceid>
        log.trace("Moving '" + tmpPkg + "' to '" + pkgFile + "'");
        if(!tmpPkg.renameTo(pkgFile)) {
            log.warn("'" + tmpPkg + "' was not renamed to '" + pkgFile + "'");
        }

        // FIXME: There is a race condition here, where the JMX files are
        //        readable after unpacking, but before we set read-only
        //        permissions
        log.debug ("Setting file permissions for service " + instanceId);
        checkPermissions(instanceId);
        
        setStatusIdle();
    }

    /**
     * Start a service.
     * @param instanceId The instance ID.
     * @param configLocation The configuration location for the service.
     * @throws RemoteException If error occur while doing RMI work.
     */
    @Override
    public void startService(String instanceId, String configLocation)
                                                        throws RemoteException {
        if (!serviceMan.knows(instanceId)) {
            throw new NoSuchServiceException(this, instanceId, "startService");
        }

        ConnectionContext<Service> connCtx;

        setStatusRunning ("Starting service " + instanceId);
        connCtx = serviceMan.get(instanceId);

        /* If the service is running but start() has not been called
         * on it, just call Service.start() */
        if (connCtx != null) {

            Service service = connCtx.getConnection();
            try {
                StartAction action =
                              conditionalServiceStart (service, configLocation);
                switch (action) {
                    case BOOTSTRAP:
                        bootStrapService(instanceId, configLocation);
                        break;
                    case RETRY:
                        startService(instanceId, configLocation);
                        break;
                    case STARTED:
                        log.info ("Service '" + instanceId + "' started");
                        break;
                }
            } catch (RemoteException e) {
                serviceMan.reportError(connCtx, e);
            } finally {
                connCtx.unref();
            }

            setStatusIdle();
            return;
        }

        /* If we reach this point we need to start a new JVM for the service.
         * And when we have that running connect to the Service and call
         * Service.start() */
        log.info ("No connection to service '" + instanceId + "', commencing "
                  + "bootstrap");
        bootStrapService(instanceId, configLocation);
    }

    /**
     * Start a service up from scratch, booting its JVM and calling start
     * on its Service interface when it is up.
     *
     * @param instanceId The instance ID to bootstrap.
     * @param confLocation The configuration location.
     * @throws RemoteException If error while connecting to instance ID.
     */
    private void bootStrapService (String instanceId, String confLocation)
                                                    throws RemoteException {
        log.info ("Bootstrapping service '" + instanceId + "'");
        File serviceFile = serviceMan.getServiceDir(instanceId);
        BundleStub stub;

        try {
            log.debug("Calling load for serviceFile '" + serviceFile + "'");
            stub = loader.load (serviceFile);
        } catch (IOException e) {
            setStatusIdle();
            throw new ServicePackageException (this, instanceId,
                    "Error loading service '"
                            + instanceId
                            + "', from file " + serviceFile,
                    e);
        }

        stub.addSystemProperty(ClientConnection.CONF_CLIENT_PERSISTENT_DIR, persistentPath);
        stub.addSystemProperty(ClientConnection.CONF_CLIENT_ID, id);
        stub.addSystemProperty(Service.CONF_SERVICE_ID, instanceId);
        stub.addSystemProperty(Service.CONF_SERVICE_BASEPATH,
                serviceFile.getParent());

        if (confLocation != null) {
            log.info("Overriding default system configuration for "
                     + instanceId + ", using " + confLocation);
            stub.addSystemProperty("summa.configuration", confLocation);
        }


        if (log.isDebugEnabled()) {
            log.debug ("Launching '" + instanceId + "' with command line:\n"
                    + Strings.join(stub.buildCommandLine(), " "));
        }

        /* Try and run the service JVM */
        ProcessRunner runner = new ProcessRunner(stub.buildCommandLine());
        Thread processThread = new Thread (runner, "Client thread");
        processThread.setDaemon (true); // Allow JVM to exit
        runner.setStartingDir(serviceMan.getServiceDir(instanceId));
        processThread.start();
        try {
            processThread.join (3000);
        } catch (InterruptedException e) {
            log.info ("Interrupted while waiting for service process for "
                    + "service '" + instanceId + "'");
            return;
        }

        if (!processThread.isAlive()) {
            String errorMessage = runner.getProcessErrorAsString();
            throw new ClientException(this, "Service '" + instanceId
                    + "' exited prematurely with exit code "
                    + runner.getReturnCode()
                    + (errorMessage != null ?
                    ", and error: " + errorMessage :
                    ""));
        }


        log.trace("Registering service");
        registerService (stub, confLocation);

        // Wait for a connection to the service
        ConnectionContext<Service> connCtx = serviceMan.get(instanceId);
        if (connCtx != null) {
            connCtx.unref ();
        } else {
            log.error ("Failed to connect to bootstrapped service "
                       + instanceId);
            throw new InvalidServiceStateException(this, instanceId, "start",
                                                   "Client can not establish "
                                                   + "a connection");
        }

        // Service should be up. Call start() on the Service interface
        startService(instanceId, confLocation);

        setStatusIdle();
    }

    /**
     * Start a service given a connection to it. The returned action hints
     * what further action the caller must take.
     *
     * @param service The service.
     * @param confLocation The configuration location.
     * @throws RemoteException If an error occur while calling remote service.
     * @return The action status.
     */
    private StartAction conditionalServiceStart(Service service,
                                   String confLocation) throws RemoteException {
        Status status = service.getStatus();
        String instanceId = service.getId();
        StatusMonitor mon;

        switch (status.getCode()) {
            case constructed:
                log.debug("Calling start() on constructed service '"
                        + instanceId + "'");
                service.start();
                log.debug("Service started");
                return StartAction.STARTED;
            case stopped:
                log.debug("Calling start() on stopped service '"
                        + instanceId + "'");
                service.start();
                log.debug("Stopped service started");
                return StartAction.STARTED;
            case stopping:
                mon = new StatusMonitor(service, 10,
                        new VoidShellContext(),
                        Status.CODE.stopping);
                log.info ("Service is stopping, waiting for it to "
                        + "completely stop before retrying start");
                mon.run(); // Waits until service leaves stopping state
                return StartAction.RETRY;
            case idle:
            case running:
            case startingUp:
                log.info ("Ignoring start request but service is "
                        + "already running");
                return StartAction.STARTED;
            case recovering:
                mon = new StatusMonitor(service, 10,
                        new VoidShellContext(),
                        Status.CODE.recovering);
                log.info ("Service is recovering, waiting for it to "
                        + "come back before retrying start");
                mon.run(); // Waits until service leaves stopping state
                return StartAction.RETRY;
            case crashed:
                log.warn ("Service " + instanceId + " has crashed."
                        + "Killing it before restart");
                service.kill ();
                mon = new StatusMonitor(service, 10,
                        new VoidShellContext(),
                        Status.CODE.not_instantiated);
                log.info("Waiting for " + instanceId + " to shut down");
                mon.run();
                return StartAction.RETRY;
            case not_instantiated:
                log.info ("Service '" + instanceId + "' not running. Requesting"
                          + " bootstrap");
                return StartAction.BOOTSTRAP;
        }
        log.error ("Unmatched service state: " + status + ". This is a bug"
                   + " in Summa's Client implementation Pretending that "
                   + " service '" + instanceId + "' is started");
        return StartAction.STARTED;
    }

    /**
     * Private helper method, to register an service.
     * @param stub The bundle stub for this service.
     * @param configLocation The configuration location.
     */
    private void registerService(BundleStub stub, String configLocation) {
        log.debug ("Registering service '" + stub.getInstanceId() + "'");

        String instanceId = stub.getInstanceId();

        if (serviceMan.knows(instanceId)) {
            log.warn ("Trying to register service '" + instanceId + "', but it"
                    + " is already registered. Ignoring request.");
            return;
        }

        // If this is a relative path, expand it to the absolute path
        // so we can load it as a file
        if (!configLocation.startsWith("/") &&
            !configLocation.contains("://")) {

            File configFile = stub.findResource(configLocation);
            if (configFile == null) {
                log.error ("Failed to find config file '" + configLocation
                           + "' in service '" + instanceId + "'s"
                           + " classpath. Failed registration.\n"
                           + "Bundle dir was: " + stub.getBundleDir() + "\n"
                           + "Bundle classpath was: "
                           + Strings.join(stub.getClassPath(), ":"));
                return;
            }
            configLocation = configFile.getAbsolutePath();
        }

        log.trace ("Absolute service config location: " + configLocation);

        Configuration serviceConf = Configuration.load(configLocation);
        int registryPort = serviceConf.getInt(Service.CONF_REGISTRY_PORT);
        String serviceName = stub.getInstanceId();
        String serviceUrl = "//localhost:" + registryPort + "/" + serviceName;

        log.trace ("Pinging service '" + instanceId +"' at '"
                   + serviceUrl + "'");
        Service service = null;
        Status status = null;
        for (int tick = 0; tick < serviceTimeout; tick++) {
            try {
                service = (Service) Naming.lookup (serviceUrl);
                status = service.getStatus();
            } catch (NotBoundException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    log.warn ("Interrupted while waiting for service '"
                              + stub.getInstanceId() + "' to come up.");
                    break;
                }
                // keep waiting on interface
            } catch (MalformedURLException e) {
                log.error ("Malformed URL for service '" + instanceId
                           + "'. Not registering", e);
            } catch (RemoteException e) {
                log.error ("Error connecting to '" + instanceId
                           + "'. Not registering", e);
            }
        }
        if (service == null){
            log.error ("Service '" + instanceId + "' on '" + serviceUrl
                    + "' never came up. It probably crashed.");
        } else {
            log.info ("Service '" + instanceId
                      + "' registered. Status was " + status);
            serviceMan.register(instanceId);
        }

    }

    /**
     * Stops a given service.
     * @param id The ID of the service to stop.
     * @throws RemoteException If error occur over RMI, while connecting to
     * service.
     */
    @Override
    public void stopService(String id) throws RemoteException {
        setStatusRunning ("Stopping service " + id);

        if (!serviceMan.knows(id)) {
            log.warn ("Can not stop service '" + id + "'. Service not "
                      + "known");
            throw new NoSuchServiceException(this, id, "stopService");
        }

        ConnectionContext<Service> connCtx;
        connCtx = serviceMan.get (id);

        if (connCtx == null) {
            if (serviceMan.getServiceDir(id).exists()) {
                log.error ("Cannot stop service. Service '" + id
                           + "' not running");
                throw new InvalidServiceStateException(this, id, "stopService",
                        "Not running");
            }
        } else {
            log.trace ("Calling stop() method on service '" + id + "'");
            boolean serviceIsDead = false;
            try {
                Service s = connCtx.getConnection();
                s.stop();


                // Wait for service to die
                for (int tick = 0; tick < serviceTimeout; tick++) {
                    try {
                        Thread.sleep(1000);
                        Status status = s.getStatus();
                        log.debug("Waiting for '" + id + "' to die. Service"
                                  + " status " + status);
                        if (Status.CODE.stopped == status.getCode()) {
                            // The service is stopped, but the RMI
                            // connection is still alive. Keep the
                            // connection around
                            setStatusIdle ();
                            return;
                        }
                    } catch (InterruptedException e) {
                        // Stop waiting for service to die
                        break;
                    } catch (RemoteException e) {
                        log.info("Service ping to '" + id + "'failed. It is"
                                 + " probably down.");
                        serviceIsDead = true;
                    }
                }
            } finally {
                connCtx.unref();
            }

            if (!serviceIsDead) {
                throw new InvalidServiceStateException(this, id, "stop",
                                                       "Service should be dead,"
                                                       + " but is still"
                                                       + " responding");
            }

        }
        setStatusIdle ();
    }

    /**
     * Return the service status.
     * @param id The services ID.
     * @return The status of the service with the given ID.
     * @throws RemoteException If error occur handling RMI connection.
     */
    @Override
    public Status getServiceStatus(String id) throws RemoteException {
        log.trace("Getting service status for " + id);

        if (id == null) {
            throw new NullPointerException("id is null");
        }

        if (!serviceMan.knows(id)) {
            throw new NoSuchServiceException(this, id, "getStatus");
        }

        ConnectionContext<Service> connCtx = serviceMan.get (id);

        if (connCtx == null) {
            throw new InvalidServiceStateException(this, id,
                                                   "getStatus", "not running");
        } else {
            Service s = connCtx.getConnection();
            try {
                return s.getStatus();
            } catch (Exception e) {
                serviceMan.reportError(connCtx, e);
                throw new InvalidServiceStateException(this, id,
                                                       "getServiceStatus",
                                                       "Connection broken");
            } finally {
                connCtx.unref();
            }
        }
    }

    /**
     * Get connection to the service specified by the ID.
     * @param id The instance id of the service to connect to.
     * @return A connection to the service.
     * @throws RemoteException If error occur when connection to service.
     */
    @Override
    public Service getServiceConnection(String id) throws RemoteException {
        return getServiceConnection(id, 0);
    }

    /**
     * This method will call it self up to 10 times trying to reconnect
     * if the client connection fails.
     * @param id The instance id of the service to connect to.
     * @param numRetries External callers should pass 0 here.
     * @return A connection to the service.
     * @throws RemoteException If error occur when connection to service.
     */
    private Service getServiceConnection(String id, int numRetries)
                                                        throws RemoteException {
        final int maxRetries = 5;

        log.trace("Getting service connection for " + id
                  + (numRetries == 0 ? "" : ". Retry: " + numRetries));

        if (numRetries >= maxRetries) {
            throw new InvalidServiceStateException(this, id,
                                                   "getServiceConnection",
                                                   "Giving up after "
                                                   + maxRetries + " retries");
        }

        if (id == null) {
            throw new NullPointerException("ID is null");
        }

        if (!serviceMan.knows(id)) {
            throw new NoSuchServiceException(this, id, "getServiceConnection");
        }

        ConnectionContext<Service> connCtx = serviceMan.get (id);

        if (connCtx == null) {
            throw new InvalidServiceStateException(this, id,
                                                   "getServiceConnection",
                                                   "not running");
        } else {
            Service s = connCtx.getConnection();
            connCtx.unref();

            try {
                log.trace ("Testing service connection '" + id + "'");
                String tmpId = s.getId();

                if (!id.equals(tmpId)) {
                    log.warn("Service '" + id + "' reports id '" + tmpId + "'");
                    throw new InvalidServiceStateException(this, id,
                                                   "getServiceConnection",
                                                   "service reports unexpected "
                                                    + "id '" + tmpId + "'");
                }
                log.trace ("Connection to '" + id + "' good");                
            } catch (Exception e) {
                // Something is bad. Mark the connection broken wait a bit,
                // and try to establish a new connection
                log.info("Connection to '" + id + "' broken: "
                           + e.getMessage());
                log.debug(e, e);

                serviceMan.reportError(connCtx, e);

                try {
                    Thread.sleep (serviceMan.getLingerTime() * 1000);
                } catch (InterruptedException ie) {
                    log.debug ("Interrupted while waiting for grace time of "
                               + "'" + id + "' to expire");
                    throw new InvalidServiceStateException(this, id,
                                                  "getServiceConnection",
                                                  "Connection to service broken"
                                                  + " interrupedt while waiting"
                                                  + " for new connection");
                }

                // Retry connection
                log.debug ("Retrying connection to '" + id + "'");
                return getServiceConnection(id, numRetries + 1);
            }
            return s;
        }
    }

    /**
     * Getting a list of all services attached to this clien.
     * @return list of services.
     */
    @Override
    public List<String> getServices() {
        log.trace("Getting list of services");

        List<String> serviceList = serviceMan.getServices();

        log.trace("Found services: "
                  + Logs.expand(serviceList, serviceList.size()));

        return serviceList;
    }

    /**
     * Return this clients ID.
     * @return The ID of this client.
     */
    @Override
    public String getId() {
        log.trace("Getting id");
        return id;
    }

    /**
     * Return the repository used by this client to access the bundles. 
     * @return The bundle repository for this class.
     * @throws RemoteException If the repository is an RMI repository and error
     * occurs when connection to it.
     */
    @Override
    public BundleRepository getRepository() throws RemoteException {
        log.trace("getRepository() called");

        /* We special case the case of RMI repositories and return
         * a connection to the server. This allows the consumer
         * to use the repository.list() method */
        if (repository instanceof RemoteURLRepositoryClient) {
            log.debug ("Returning connection to remote repository");
            try {
                return
              ((RemoteURLRepositoryClient)repository).getRepositoryConnection();
            } catch (IOException e) {
                throw new RemoteException("Failed to connect to remote "
                                          + "repository: " + e.getMessage(),
                                          e);
            }

        }
        return repository;
    }

    /**
     * Return the specification of the instance, identified by the given ID.
     * @param instanceId The instance ID.
     * @return The specification for the instance.
     * @throws RemoteException If error occur while connection to instance over
     * RMI.
     */
    @Override
    public String getBundleSpec(String instanceId) throws RemoteException {
        File specFile;

        if (instanceId == null) {
            // Get bundle spec of the client
            specFile = new File(basePath, "client.xml");
        } else {
            if (!serviceMan.knows(instanceId)) {
                throw new NoSuchServiceException(this, instanceId,
                                                 "getBundleSpec");
            }

            specFile = serviceMan.getServiceFile(instanceId);
        }

        try {
            BundleSpecBuilder builder = BundleSpecBuilder.open(specFile);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            builder.write(out);
            return new String(out.toByteArray());
        } catch (IOException e) {
            throw new RemoteException("Failed to read bundle spec " + specFile
                                      +" for '"
                                      + (instanceId == null ? id : instanceId)
                                      + "': " + e.getMessage(), e);
        }
    }

    /**
     * Get error report on the specified instance ID.
     * @param id The instance ID.
     * @throws RemoteException If error occur while connection to the instance
     * over RMI.
     */
    @Override
    public void reportError(String id) throws RemoteException {
        log.debug ("Got error report on '" + id + "'");

        try {
            ConnectionContext<Service> connCtx = serviceMan.get(id);
            if (connCtx == null) {
                log.debug("When reporting error on '" + id + "', service"
                           + " unreachable");
                return;
            }

            serviceMan.reportError(connCtx, "Reported broken by 3rd party");
        } catch (Exception e) {
            log.warn ("Failed to mark connection to '" + id + "' broken", e);
        }
    }

    /**
     * Set status.
     * @param code The status code. See {@link Status#code}.
     * @param msg The message to attach to this status change.
     * @param level The log level.
     */
    private void setStatus(Status.CODE code, String msg,
                            Logging.LogLevel level) {
        status = new Status(code, msg);
        Logging.log(this +" status: "+ status, log, level);
    }

    /**
     * Set status idle.
     */
    private void setStatusIdle() {
        setStatus(Status.CODE.idle, "ready", Logging.LogLevel.DEBUG);
    }

    /**
     * Set status running.
     * @param msg A message to attach to the status.
     */
    private void setStatusRunning(String msg) {
        setStatus(Status.CODE.running, msg, Logging.LogLevel.INFO);
    }

    /**
     * Pretty prints this method.
     * @return A string representation of this method.
     */
    @Override
    public String toString() {
        return "["+id+"@"+getRMIAddress()+"]";
    }

    /**
     * Deploy a local package over another (possibly running) service.
     * @param instanceId Id of the package to redeploy.
     * @param tmpPkgFile The downloaded package which to deploy instead of the
     *                   existing service.
     */
    private void reDeployService(String instanceId, File tmpPkgFile) {
        File pkgFile = new File(servicePath, instanceId);

        setStatusRunning("Redeploying service '" + instanceId
                         + "' from " + tmpPkgFile);

        try {
            removeService (instanceId);
            Files.copy(tmpPkgFile, pkgFile, false);
            //FIXME: We should really unzip to the location instead
        } catch (RemoteException re){
            log.error("Error removing service '" + instanceId
                      + "', aborting redeploy", re);
        } catch (IOException e) {
            log.error("Error redeploying service '" + instanceId + "' from "
                                   + tmpPkgFile + " to " + pkgFile, e);
        }
        setStatusIdle();
    }

    /**
     * Stop a service and move its package file to artifacts.
     * @param id The service to stop and remove.
     * @throws RemoteException Upon communication errors with the service.
     */
    @Override
    public void removeService(String id) throws RemoteException {
        log.info("Removing service '" + id +"'");

        ConnectionContext<Service> connCtx;
        File pkgFile = serviceMan.getServiceDir(id);
        String artifactPkgPath;

        if (!serviceMan.knows(id)) {
            throw new NoSuchServiceException(this, id, "removeService");
        }

        connCtx = serviceMan.get (id);

        /* Close the service if it is running */
        if (connCtx != null) {
            try {
                log.info("Killing service '" + id + "' before removal");
                connCtx.getConnection().kill ();
            } catch(Exception e) {
                log.warn("Problems connecting to service '" + id + "' when "
                         +"killing it. Removing it anyway", e);
                serviceMan.reportError(connCtx, e);
            } finally {
                connCtx.unref();
            }
        } else {
            log.info("Service '" + id + "', not running. Good to remove");
        }

        if (storeArtifacts) {
            /* Find an available file name in the artifacts dir */
            int availNum = 1;
            artifactPkgPath = artifactPath +File.separator
                              + pkgFile.getName() + ".old.0";
            while (new File (artifactPkgPath).exists()) {
                artifactPkgPath =  artifactPath +File.separator
                                   + pkgFile.getName() + ".old." + availNum;
                availNum++;
            }

            log.info("Removing service '" + id + "', backup kept as: "
                     + artifactPkgPath);
            if(!pkgFile.renameTo(new File(artifactPkgPath))) {
                log.warn("'" + pkgFile + "' was not rename to '"
                         + artifactPkgPath + "'");
            }
        } else {
            /* We are not configured to store artifacts,
             * simply delete the service */
            try {
                log.info("Removed service '" + id + "' without backup");
                Files.delete(pkgFile);
            } catch (IOException e) {
                throw new ServiceDeploymentException("Failed to delete service"
                                                     + " dir " + pkgFile, e);
            }
        }
    }

    /**
     * Set the file permissions correctly for various know trouble makers.
     * For instance JMX access and password files need read-only permissions.
     * @param id The id of the service to set permissions for.
     */
    private void checkPermissions(String id) {
        File bundleDir = serviceMan.getServiceDir(id);
        File policy = new File(bundleDir, BundleStub.POLICY_FILE);
        File password = new File(bundleDir, BundleStub.JMX_PASSWORD_FILE);
        File access = new File(bundleDir, BundleStub.JMX_ACCESS_FILE);

        for (File file: new File[]{policy, password, access}) {
            if (file.exists()) {
                log.trace("Setting " + file + " read only");
                boolean failedSet = false;
                // disallow all reading
                failedSet = (!file.setReadable(false, false) || failedSet);
                // allow user reading
                failedSet = (!file.setReadable(true) || failedSet);
                // disallow all writing
                failedSet = (!file.setWritable(false, false) || failedSet);
                if(failedSet) {
                    log.warn("Setting user read only permission on '"
                             + file + "'");
                }
            }
        }
    }

    /**
     * Main method for the client shell.
     * @param args Commandline argument. None is needed.
     */
    public static void main(String[] args) {
        try {
            Configuration conf = Configuration.getSystemConfiguration(true);
            new Client(conf);
            // The spawned server thread for RMI will cause the JVM to not exit
        } catch (Throwable e) {
            Logging.fatal(log, "Client.main", "Caught toplevel exception, bailing out.", e);
            System.exit(1);
        }
    }
}
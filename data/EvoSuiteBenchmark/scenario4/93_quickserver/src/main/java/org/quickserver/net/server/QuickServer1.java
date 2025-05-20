package org.quickserver.net.server;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.*;
import org.quickserver.net.*;
//v1.1
import org.quickserver.net.qsadmin.*;
//v1.2
import java.util.logging.*;
//v1.3
import org.quickserver.util.pool.*;
import org.quickserver.util.pool.thread.*;
import org.apache.commons.pool.*;
import org.quickserver.util.xmlreader.*;
import org.quickserver.sql.*;
//v1.3.1
import java.util.logging.Formatter;
import java.util.*;
//v1.3.2
import org.quickserver.util.*;
//v1.3.3
import org.quickserver.security.*;
//v1.4.0
import javax.net.ssl.*;
import javax.net.*;
import java.security.*;
import java.security.cert.*;
//v1.4.5
import java.nio.channels.*;
import org.quickserver.net.server.impl.*;

/**
 * Main class of QuickServer library. This class is used to create
 * multi client servers quickly.
 * <p>
 * Ones a client is connected, it creates {@link ClientHandler} object,
 * which is run using any thread available from the pool of threads
 * maintained by {@link org.quickserver.util.pool.thread.ClientPool}, which
 * handles the client. <br/>
 * QuickServer divides the application logic of its developer over eight
 * class, <br>
 * 	<ul>
 * 	<li>ClientEventHandler<br>
 * 		   &nbsp;Handles client events [Optional Class].
 * 		<li>ClientCommandHandler [#]<br>
 * 		   &nbsp;Handles client character/string commands.
 * 		<li>ClientObjectHandler [#]<br>
 * 		   &nbsp;Handles client interaction - Object commands.
 * 	<li>ClientBinaryHandler [#]<br>
 * 		   &nbsp;Handles client interaction - binary data.
 * 	<li>ClientWriteHandler [Optional Class]<br>
 * 		   &nbsp;Handles client interaction - writing data (Only used in non-blocking mode).
 * 		<li>ClientAuthenticationHandler [Optional Class]<br>
 * 			&nbsp;Used to Authencatet a client.
 * 		<li>ClientData [Optional Class]<br>
 * 			&nbsp;Client data carrier (support class)
 * 	<li>ClientExtendedEventHandler [Optional Class]<br>
 * 		   &nbsp;Handles extended client events.
 * 	</ul>
 *
 * [#] = Any one of these have to be set based on default DataMode for input.
 * The default DataMode for input is String so if not changes you will
 * have to set ClientCommandHandler.
 * </p>
 * <p>
 *  Eg:
 * <code><BLOCKQUOTE><pre>
 * package echoserver;
 *
 * import org.quickserver.net.*;
 * import org.quickserver.net.server.*;
 *
 * import java.io.*;
 *
 * public class EchoServer {
 * 	public static void main(String args[])	{
 * 		String cmdHandle = "echoserver.EchoCommandHandler";
 *
 * 		QuickServer myServer = new QuickServer();
 * 		myServer.setClientCommandHandler(cmdHandle);
 * 		myServer.setPort(4123);
 * 		myServer.setName(Echo Server v1.0");
 * 		try {
 * 			myServer.startServer();
 * 		} catch(AppException e) {
 * 			System.err.println("Error in server : "+e);
 * 			e.printStackTrace();
 * 		}
 * 	}
 * }
 * </pre></BLOCKQUOTE></code></p>
 *
 * @version 1.4.8
 * @author Akshathkumar Shetty
 */
public class QuickServer implements Runnable, Service, Cloneable, Serializable {

    //change also in QSAdminMain
    private final static String VER = "2.0.0 RC1";

    private final static String NEW_LINE;

    private final static String pid;

    static {
    }

    private String serverBanner;

    //v1.4.6
    private String clientAuthenticationHandlerString;

    //v1.4.6
    private String clientEventHandlerString;

    //v1.4.6
    private String clientExtendedEventHandlerString;

    private String clientCommandHandlerString;

    //v1.2
    private String clientObjectHandlerString;

    //v1.4
    private String clientBinaryHandlerString;

    //v1.4.5
    private String clientWriteHandlerString;

    private String clientDataString;

    private Authenticator authenticator;

    //v1.4.6
    private ClientAuthenticationHandler clientAuthenticationHandler;

    //v1.4.6
    private ClientEventHandler clientEventHandler;

    //v1.4.6
    private ClientExtendedEventHandler clientExtendedEventHandler;

    private ClientCommandHandler clientCommandHandler;

    //v1.2
    private ClientObjectHandler clientObjectHandler;

    //v1.4
    private ClientBinaryHandler clientBinaryHandler;

    //v1.4.5
    private ClientWriteHandler clientWriteHandler;

    private ClientData clientData;

    protected Class clientDataClass;

    private int serverPort = 9876;

    //Main thread
    private Thread t;

    private ServerSocket server;

    private String serverName = "QuickServer";

    private long maxConnection = -1;

    //1 min socket timeout
    private int socketTimeout = 60 * 1000;

    private String maxConnectionMsg = "-ERR Server Busy. Max Connection Reached";

    private String timeoutMsg = "-ERR Timeout";

    private String maxAuthTryMsg = "-ERR Max Auth Try Reached";

    //v1.2
    private int maxAuthTry = 5;

    static {
    }

    //--v1.1
    private InetAddress ipAddr;

    private boolean stopServer;

    private Object[] storeObjects;

    private QSAdminServer adminServer;

    //Logger for QuickServer
    private static final Logger logger = Logger.getLogger(QuickServer.class.getName());

    //Logger for the application using this QuickServer
    private Logger appLogger;

    //backup
    private long suspendMaxConnection;

    //backup
    private String suspendMaxConnectionMsg;

    private int serviceState = Service.UNKNOWN;

    static {
    }

    //--v1.3
    private QuickServerConfig config = new QuickServerConfig();

    private String consoleLoggingformatter;

    private String consoleLoggingLevel = "INFO";

    private ClientPool pool;

    private ObjectPool clientHandlerPool;

    private ObjectPool clientDataPool;

    private DBPoolUtil dBPoolUtil;

    //--v1.3.1
    private String loggingLevel = "INFO";

    //--v1.3.2
    private boolean skipValidation = false;

    private boolean communicationLogging = true;

    //--v1.3.3
    private String securityManagerClass;

    private AccessConstraintConfig accessConstraintConfig;

    private ClassLoader classLoader;

    private String applicationJarPath;

    private ServerHooks serverHooks;

    private ArrayList listOfServerHooks;

    static {
    }

    //--v1.4.0
    private Secure secure;

    private BasicServerConfig basicConfig = config;

    private SSLContext sslc;

    private KeyManager[] km = null;

    private TrustManager[] tm = null;

    private boolean runningSecure = false;

    private SecureStoreManager secureStoreManager = null;

    private Exception exceptionInRun = null;

    //--v1.4.5
    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private boolean blockingMode = true;

    private ObjectPool byteBufferPool;

    private java.util.Date lastStartTime;

    private ClientIdentifier clientIdentifier;

    private GhostSocketReaper ghostSocketReaper;

    private PoolManager poolManager;

    private QSObjectPoolMaker qsObjectPoolMaker;

    //--v1.4.6
    private DataMode defaultDataModeIN = DataMode.STRING;

    private DataMode defaultDataModeOUT = DataMode.STRING;

    //-v1.4.7
    private Throwable serviceError;

    private Map registerChannelRequestMap;

    //v-1.4.8
    private boolean rawCommunicationLogging = false;

    private int rawCommunicationMaxLength = 100;

    static {
    }

    /**
     * Returns the version of the library.
     */
    public static final String getVersion();

    /**
     * Returns the numerical version of the library.
     * @since 1.2
     */
    public static final float getVersionNo();

    /**
     * Returns the numerical version of the library.
     * @since 1.4.5
     */
    public static final float getVersionNo(String ver);

    /**
     * Returns the new line string used by QuickServer.
     * @since 1.2
     */
    public static String getNewLine();

    /**
     * Returns the Server name : port of the QuickServer.
     */
    public String toString();

    /**
     * Creates a new server without any configuration.
     * Make sure you configure the QuickServer, before
     * calling startServer()
     * @see org.quickserver.net.server.ClientEventHandler
     * @see org.quickserver.net.server.ClientCommandHandler
     * @see org.quickserver.net.server.ClientObjectHandler
     * @see org.quickserver.net.server.ClientBinaryHandler
     * @see org.quickserver.net.server.ClientWriteHandler
     * @see org.quickserver.net.server.ClientAuthenticationHandler
     * @see org.quickserver.net.server.ClientHandler
     * @see #configQuickServer
     * @see #initService
     * @see #setPort
     * @see #setClientCommandHandler
     * @since 1.2
     */
    public QuickServer() {
    }

    /**
     * Creates a new server with the specified
     * <code>commandHandler</code> has it {@link ClientCommandHandler}.
     * @param commandHandler the fully qualified name of the
     *  desired class that implements {@link ClientCommandHandler}
     *
     * @see org.quickserver.net.server.ClientCommandHandler
     * @see org.quickserver.net.server.ClientAuthenticationHandler
     * @see org.quickserver.net.server.ClientHandler
     * @see #setPort
     */
    public QuickServer(String commandHandler) {
    }

    /**
     * Creates a new server at <code>port</code> with the specified
     * <code>commandHandler</code> has it {@link ClientCommandHandler}.
     *
     * @param commandHandler fully qualified name of the class that
     * implements {@link ClientCommandHandler}
     * @param port to listen on.
     *
     * @see org.quickserver.net.server.ClientCommandHandler
     * @see org.quickserver.net.server.ClientAuthenticationHandler
     * @see org.quickserver.net.server.ClientHandler
     */
    public QuickServer(String commandHandler, int port) {
    }

    /**
     * Starts the QuickServer.
     *
     * @exception org.quickserver.net.AppException
     *  if Server already running or if it could not load the classes
     *  [ClientCommandHandler, ClientAuthenticationHandler, ClientData].
     * @see #startService
     */
    public void startServer() throws AppException;

    /**
     * Stops the QuickServer.
     *
     * @exception org.quickserver.net.AppException
     *  if could not stop server
     * @since 1.1
     * @see #stopService
     */
    public void stopServer() throws AppException;

    /**
     * Restarts the QuickServer.
     *
     * @exception org.quickserver.net.AppException
     *  if could not stop server or if it could not start the server.
     * @since 1.2
     */
    public void restartServer() throws AppException;

    /**
     * Returns the name of the QuickServer. Default is 'QuickServer'.
     * @see #setName
     */
    public String getName();

    /**
     * Sets the name for the QuickServer
     * @param name for the QuickServer
     * @see #getName
     */
    public void setName(String name);

    /**
     * Returns the Server Banner of the QuickServer
     * @see #setServerBanner
     */
    public String getServerBanner();

    /**
     * Sets the serverBanner for the QuickServer
     * that will be displayed on the standard output [console]
     * when server starts. <br>&nbsp;<br>
     * To set welcome message to your client
     * {@link ClientEventHandler#gotConnected}
     * @param banner for the QuickServer
     * @see #getServerBanner
     */
    public void setServerBanner(String banner);

    /**
     * Sets the port for the QuickServer to listen on.
     * If not set, it will run on Port 9876
     * @param port to listen on.
     * @see #getPort
     */
    public void setPort(int port);

    /**
     * Returns the port for the QuickServer.
     * @see #setPort
     */
    public int getPort();

    /**
     * Sets the ClientCommandHandler class that interacts with
     * client sockets.
     * @param handler the fully qualified name of the class that
     *  implements {@link ClientCommandHandler}
     * @see #getClientCommandHandler
     */
    public void setClientCommandHandler(String handler);

    /**
     * Returns the ClientCommandHandler class that interacts with
     * client sockets.
     * @see #setClientCommandHandler
     * @since 1.1
     */
    public String getClientCommandHandler();

    /**
     * Sets the ClientAuthenticationHandler class that
     * handles the authentication of a client.
     * @param authenticator the fully qualified name of the class
     * that implements {@link ClientAuthenticationHandler}.
     * @see #getClientAuthenticationHandler
     * @since 1.4.6
     */
    public void setClientAuthenticationHandler(String authenticator);

    /**
     * Returns the ClientAuthenticationHandler class that
     * handles the authentication of a client.
     * @see #setClientAuthenticationHandler
     * @since 1.4.6
     */
    public String getClientAuthenticationHandler();

    /**
     * Sets the Authenticator class that
     * handles the authentication of a client.
     * @param authenticator the fully qualified name of the class
     * that implements {@link Authenticator} or {@link ClientAuthenticationHandler}.
     * @see #getAuthenticator
     * @deprecated since 1.4.6 use setClientAuthenticationHandler
     * @since 1.3
     */
    public void setAuthenticator(String authenticator);

    /**
     * Returns the Authenticator class that
     * handles the authentication of a client.
     * @see #setAuthenticator
     * @deprecated since 1.4.6 use getClientAuthenticationHandler
     * @since 1.3
     */
    public String getAuthenticator();

    /**
     * Sets the ClientData class that carries client data.
     * @param data the fully qualified name of the class that
     * extends {@link ClientData}.
     * @see #getClientData
     */
    public void setClientData(String data);

    /**
     * Returns the ClientData class string that carries client data
     * @return the fully qualified name of the class that
     * implements {@link ClientData}.
     * @see #setClientData
     */
    public String getClientData();

    /**
     * Sets the client socket's timeout.
     * @param time client socket timeout in milliseconds.
     * @see #getTimeout
     */
    public void setTimeout(int time);

    /**
     * Returns the Client socket timeout in milliseconds.
     * @see #setTimeout
     */
    public int getTimeout();

    /**
     * Sets max allowed login attempts.
     * @since 1.2
     * @see #getMaxAuthTry
     */
    public void setMaxAuthTry(int authTry);

    /**
     * Returns max allowed login attempts. Default is <code>5</code>.
     * @since 1.2
     * @see #setMaxAuthTry
     */
    public int getMaxAuthTry();

    /**
     * Sets message to be displayed when maximum allowed login
     * attempts has reached.
     * Default is : -ERR Max Auth Try Reached
     * @see #getMaxAuthTryMsg
     */
    public void setMaxAuthTryMsg(String msg);

    /**
     * Returns message to be displayed when maximum allowed login
     * attempts has reached.
     * @see #getMaxAuthTryMsg
     */
    public String getMaxAuthTryMsg();

    /**
     * Sets timeout message.
     * Default is : -ERR Timeout
     * @see #getTimeoutMsg
     */
    public void setTimeoutMsg(String msg);

    /**
     * Returns timeout message.
     * @see #setTimeoutMsg
     */
    public String getTimeoutMsg();

    private TheClient initTheClient();

    public void run();

    /**
     * Sets the maximum number of client connection allowed.
     * @since 1.1
     * @see #getMaxConnection
     */
    public void setMaxConnection(long maxConnection);

    /**
     * Returns the maximum number of client connection allowed.
     * @since 1.1
     * @see #setMaxConnection
     */
    public long getMaxConnection();

    /**
     * Returns number of clients connected.
     * @since 1.1
     */
    public long getClientCount();

    /**
     * Sets the message to be sent to any new client connected after
     * maximum client connection has reached.
     * Default is : <code>-ERR Server Busy. Max Connection Reached</code>
     * @since 1.1
     * @see #getMaxConnectionMsg
     */
    public void setMaxConnectionMsg(String maxConnectionMsg);

    /**
     * Returns the message to be sent to any new client connected
     * after maximum client connection has reached.
     * @since 1.1
     * @see #setMaxConnectionMsg
     */
    public String getMaxConnectionMsg();

    /**
     * Sets the Ip address to bind to.
     * @param bindAddr argument can be used on a multi-homed host for a
     * QuickServer that will only accept connect requests to one
     * of its addresses. If not set, it will default accepting
     * connections on any/all local addresses.
     * @exception java.net.UnknownHostException if no IP address for
     * the host could be found
     * @since 1.1
     * @see #getBindAddr
     */
    public void setBindAddr(String bindAddr) throws UnknownHostException;

    /**
     * Returns the IP address binding to.
     * @since 1.1
     * @see #setBindAddr
     */
    public InetAddress getBindAddr();

    /**
     * Sets the store of objects to QuickServer, it is an array of objects
     * that main program or the class that created QuickServer passes to
     * the QuickServer.
     * @param storeObjects array of objects
     * @see #getStoreObjects
     * @since 1.1
     */
    public void setStoreObjects(Object[] storeObjects);

    /**
     * Returns store of objects from QuickServer, if nothing was set will
     * return <code>null</code>.
     * @see #setStoreObjects
     * @since 1.1
     */
    public Object[] getStoreObjects();

    /**
     * Set the port to run QSAdminServer on.
     * @since 1.2
     */
    public void setQSAdminServerPort(int port);

    /**
     * Returns the port to run QSAdminServer on.
     * @since 1.2
     */
    public int getQSAdminServerPort();

    /**
     * Set the ClientAuthenticationHandler class of
     * QSAdminServer that handles the authentication of a client.
     * @since 1.2
     */
    public void setQSAdminServerAuthenticator(String authenticator);

    /**
     * Returns the Authenticator or ClientAuthenticationHandler class of
     * QSAdminServer that handles the authentication of a client.
     * @since 1.2
     */
    public String getQSAdminServerAuthenticator();

    /**
     * Starts QSAdminServer for this QuickServer.
     * @see org.quickserver.net.qsadmin.QSAdminServer
     * @param authenticator sets the ClientAuthenticationHandler class that
     *   handles the authentication of a client,
     *   if null uses {@link org.quickserver.net.qsadmin.Authenticator}.
     * @param port to run QSAdminServer on
     * @exception org.quickserver.net.AppException
     *  if Server already running or if it could not load the classes
     *  [ClientCommandHandler, ClientAuthenticationHandler, ClientData].
     * @since 1.1
     */
    public void startQSAdminServer(int port, String authenticator) throws AppException;

    /**
     * Starts QSAdminServer for this QuickServer.
     * @see org.quickserver.net.qsadmin.QSAdminServer
     * @since 1.2
     */
    public void startQSAdminServer() throws AppException;

    /**
     * Returns {@link QSAdminServer} associated with this QuickServer
     * @since 1.1
     */
    public QSAdminServer getQSAdminServer();

    /**
     * Sets {@link QSAdminServer} associated with this QuickServer
     * @since 1.3.3
     */
    public void setQSAdminServer(QSAdminServer adminServer);

    /**
     * Returns the closed state of the QuickServer Socket.
     * @since 1.1
     */
    public boolean isClosed();

    /**
     * Returns the application logger associated with QuickServer.
     * If it was not set will return QuickServer's own logger.
     * @since 1.2
     */
    public Logger getAppLogger();

    /**
     * Sets the application logger associated with QuickServer
     * @since 1.2
     */
    public void setAppLogger(Logger appLogger);

    /**
     * Sets the ClientObjectHandler class that interacts with
     * client sockets to handle java objects.
     * @param handler object the fully qualified name of the class that
     *  implements {@link ClientObjectHandler}
     * @see #getClientObjectHandler
     * @since 1.2
     */
    public void setClientObjectHandler(String handler);

    /**
     * Returns the ClientObjectHandler class that interacts with
     * client sockets.
     * @see #setClientObjectHandler
     * @since 1.2
     */
    public String getClientObjectHandler();

    /**
     * Sets the console log handler formatter.
     * @param formatter fully qualified name of the class that implements
     * {@link java.util.logging.Formatter}
     * @since 1.2
     */
    public void setConsoleLoggingFormatter(String formatter) throws ClassNotFoundException, InstantiationException, IllegalAccessException;

    /**
     * Gets the console log handler formatter.
     * @since 1.3
     */
    public String getConsoleLoggingFormatter();

    /**
     * Sets the console log handler formater to
     * {@link org.quickserver.util.logging.MiniFormatter}
     * @since 1.2
     */
    public void setConsoleLoggingToMini();

    /**
     * Sets the console log handler formater to
     * {@link org.quickserver.util.logging.MicroFormatter}
     * @since 1.2
     */
    public void setConsoleLoggingToMicro();

    /**
     * Sets the console log handler level.
     * @since 1.2
     */
    public void setConsoleLoggingLevel(Level level);

    /**
     * Gets the console log handler level.
     * @since 1.3
     */
    public String getConsoleLoggingLevel();

    /**
     * Sets the level for all log handlers.
     * @since 1.3.1
     */
    public void setLoggingLevel(Level level);

    /**
     * Returns service error if any.
     * @since 1.4.7
     */
    public Throwable getServiceError();

    /**
     * Initialise and create the service.
     * @param param of the xml configuration file.
     * @since 1.2
     */
    public synchronized boolean initService(Object[] param);

    /**
     * Initialise and create the service.
     * @param qsConfig QuickServerConfig object.
     * @since 1.4.6
     */
    public synchronized boolean initService(QuickServerConfig qsConfig);

    /**
     * Start the service.
     * @return true if serivce was stopped from Running state.
     * @since 1.2
     */
    public boolean startService();

    /**
     * Stop the service.
     * @return true if serivce was stopped from Running state.
     * @since 1.2
     */
    public boolean stopService();

    /**
     * Suspends the service.
     * @return true if service was suspended from resumed state.
     * @since 1.2
     */
    public boolean suspendService();

    /**
     * Resume the service.
     * @return true if service was resumed from suspended state.
     * @since 1.2
     */
    public boolean resumeService();

    /**
     * Information about the service.
     * @since 1.2
     */
    public String info();

    /**
     * Initialise and create the server.
     * @param param of the xml configuration file.
     * @exception AppException if QuickServerConfig creation failed from the xml config file.
     * @since 1.4.7
     */
    public synchronized void initServer(Object[] param) throws AppException;

    /**
     * Initialise and create the service.
     * @param qsConfig QuickServerConfig object.
     * @since 1.4.7
     */
    public synchronized void initServer(QuickServerConfig qsConfig) throws AppException;

    /**
     * Returns the state of the process
     * As any constant of {@link Service} interface.
     * @since 1.2
     */
    public int getServiceState();

    /**
     * Sets the state of the process
     * As any constant of {@link Service} interface.
     * @since 1.2
     */
    public void setServiceState(int state);

    private void configConsoleLoggingLevel(QuickServer qs, String temp);

    /**
     * Configures QuickServer based on the passed QuickServerConfig object.
     * @since 1.2
     */
    public void configQuickServer(QuickServerConfig config) throws Exception;

    /**
     * Configures QSAdminServer based on the passed QuickServerConfig object.
     * @since 1.2
     */
    public void configQuickServer(QSAdminServerConfig config) throws Exception;

    /**
     * Configures QSAdminServer and QuickServer based on the
     * internal QuickServerConfig object.
     * @since 1.3
     */
    public void configQuickServer() throws Exception;

    /**
     * Usage: QuickServer [-options]<br/>
     * Where options include:<br/>
     *   -about		Opens About Dialogbox<br/>
     *   -load <xml_config_file> [options]	Loads the server from xml file.
     * where options include:
     *    -fullXML2File <new_file_name>
     */
    public static void main(String[] args);

    /**
     * Loads the server from the xml file name passed.
     * @since 1.4.7
     */
    public static QuickServer load(String xml) throws AppException;

    /**
     * Prints usage
     */
    private static String printUsage();

    private static void handleOptions(String[] args, QuickServer quickserver);

    /**
     * Cleans all Object and Thread pools
     * @since 1.3
     */
    public void clearAllPools() throws Exception;

    /**
     * Closes all Object and Thread pools
     * @since 1.3
     */
    public void closeAllPools() throws Exception;

    /**
     * Initialise all Object and Thread pools.
     * @since 1.3
     */
    public void initAllPools() throws Exception;

    /**
     * Returns {@link org.quickserver.util.pool.thread.ClientPool} class that
     * managing the pool of threads for handling clients.
     * @exception IllegalStateException if pool is not created yet.
     * @since 1.3
     */
    public ClientPool getClientPool();

    /**
     * Makes the pool of ClientHandler
     * @since 1.3
     */
    private void makeClientHandlerPool(PoolConfig opConfig) throws Exception;

    /**
     * Returns ObjectPool of {@link org.quickserver.net.server.ClientHandler}
     * class.
     * @exception IllegalStateException if pool is not created yet.
     * @since 1.3
     */
    public ObjectPool getClientHandlerPool();

    /**
     * Sets the configuration of the QuickServer.
     * @since 1.3
     */
    public void setConfig(QuickServerConfig config);

    /**
     * Returns the configuration of the QuickServer.
     * @since 1.3
     */
    public QuickServerConfig getConfig();

    /**
     * Makes the pool of ClientData
     * @since 1.3
     */
    private void makeClientDataPool(PoolableObjectFactory factory, PoolConfig opConfig) throws Exception;

    /**
     * Returns ObjectPool of {@link org.quickserver.net.server.ClientData}
     * class. If ClientData was not poolable will return  null.
     * @since 1.3
     */
    public ObjectPool getClientDataPool();

    /**
     * Returns {@link org.quickserver.sql.DBPoolUtil} object if
     * {@link org.quickserver.util.xmlreader.DBObjectPoolConfig} was set.
     * @return DBPoolUtil object if object could be loaded, else will return <code>null</code>
     * @since 1.3
     */
    public DBPoolUtil getDBPoolUtil();

    /**
     * Sets {@link org.quickserver.util.xmlreader.DBObjectPoolConfig}
     * @since 1.3
     */
    public void setDBObjectPoolConfig(DBObjectPoolConfig dBObjectPoolConfig);

    /**
     * Makes the pool of Database Objects
     * @since 1.3
     */
    private void makeDBObjectPool() throws Exception;

    /**
     *  Tries to find the Client by the Id passed.
     *  <p>
     *  Note: This command is an expensive so do use it limitedly and
     *  cache the returned object. But before you start sending message to the
     *  cached object do validate that ClientHandler with you is currently
     *  connected and is pointing to the same clinet has it was before.
     *  This can be done as follows. <pre>
     * 	foundClientHandler.isConnected(); //this method will through SocketException if not connected
     * 	Date newTime = foundClientHandler.getClientConnectedTime();
     * 	if(oldCachedTime!=newTime) {
     * 		//Client had disconnected and ClientHandler was reused for
     * 		//someother client, so write code to again find ur client
     * 		foundClientHandler = handler.getServer().findFirstClientById("friendsid");
     * 		...
     * 	}</pre>
     *  </p>
     *  @see ClientIdentifiable
     *  @return ClientHandler object if client was found else <code>null</code>
     *  @since 1.3.1
     */
    public ClientHandler findFirstClientById(String id);

    /**
     *  Returns an iterator containing all the
     *  {@link org.quickserver.net.server.ClientHandler} that
     *  are currently handling clients.
     *  It is recommended not to change the collection under an iterator.
     *
     *  It is imperative that the user manually synchronize on the returned collection
     *  when iterating over it:
     *  <code><pre>
     *    Eg:
     *
     * 	ClientData foundClientData = null;
     * 	Object syncObj = quickserver.getClientIdentifier().getObjectToSynchronize();
     * 	synchronized(syncObj) {
     * 		Iterator iterator = quickserver.findAllClient();
     * 		while(iterator.hasNext()) {
     * 			foundClientHandler = (ClientHandler) iterator.next();
     * 			....
     * 		}
     * 	}
     *
     * 	//OR
     *
     * 	ClientData foundClientData = null;
     * 	ClientIdentifier clientIdentifier = quickserver.getClientIdentifier();
     * 	synchronized(clientIdentifier.getObjectToSynchronize()) {
     * 		Iterator iterator = clientIdentifier.findAllClient();
     * 		while(iterator.hasNext()) {
     * 			foundClientHandler = (ClientHandler) iterator.next();
     * 			....
     * 		}
     * 	}
     *    </code></pre>
     *  @since 1.3.1
     */
    public Iterator findAllClient();

    /**
     *  Tries to find the Client by the matching pattern passed to the Id.
     *  <p>
     *  Note: This command is an expensive so do use it limitedly and
     *  cache the returned object. But before you start sending message to the
     *  cached object do validate that ClientHandler with you is currently
     *  connected and is pointing to the same client has it was before.
     *  This can be done as follows. <pre>
     * 	foundClientHandler.isConnected(); //this method will through SocketException if not connected
     * 	Date newTime = foundClientHandler.getClientConnectedTime();
     * 	if(oldCachedTime!=newTime) {
     * 		//Client had disconnected and ClientHandler was reused for
     * 		//someother client, so write code to again find ur client
     * 		foundClientHandler = handler.getServer().findFirstClientById("friendsid");
     * 		...
     * 	}</pre>
     *  </p>
     *  @see ClientIdentifiable
     *  @return ClientHandler object if client was found else <code>null</code>
     *  @since 1.3.2
     */
    public Iterator findAllClientById(String pattern);

    /**
     *  Tries to find the Client by the Key passed.
     *  <p>
     *  Note: This command is an expensive so do use it limitedly and
     *  cache the returned object. But before you start sending message to the
     *  cached object do validate that ClientHandler with you is currently
     *  connected and is pointing to the same client has it was before.
     *  This can be done as follows. <pre>
     * 	foundClientHandler.isConnected(); //this method will through SocketException if not connected
     * 	Date newTime = foundClientHandler.getClientConnectedTime();
     * 	if(oldCachedTime!=newTime) {
     * 		//Client had disconnected and ClientHandler was reused for
     * 		//someother client, so write code to again find ur client
     * 		foundClientHandler = handler.getServer().findClientByKey("friendskey");
     * 		...
     * 	}</pre>
     *  </p>
     *  @see ClientIdentifiable
     *  @return ClientHandler object if client was found else <code>null</code>
     *  @since 1.3.1
     */
    public ClientHandler findClientByKey(String key);

    /**
     *  Tries to find the Client by the matching pattern passed to the key.
     *  <p>
     *  Note: This command is an expensive so do use it limitedly and
     *  cache the returned object. But before you start sending message to the
     *  cached object do validate that ClientHandler with you is currently
     *  connected and is pointing to the same client has it was before.
     *  This can be done as follows. <pre>
     * 	foundClientHandler.isConnected(); //this method will through SocketException if not connected
     * 	Date newTime = foundClientHandler.getClientConnectedTime();
     * 	if(oldCachedTime!=newTime) {
     * 		//Client had disconnected and ClientHandler was reused for
     * 		//some other client, so write code to again find ur client
     * 		foundClientHandler = handler.getServer().findFirstClientByKey("friendsid");
     * 		...
     * 	}</pre>
     *  </p>
     *  @see ClientIdentifiable
     *  @return ClientHandler object if client was found else <code>null</code>
     *  @since 1.4
     */
    public Iterator findAllClientByKey(String pattern);

    /**
     * Sets next client has a trusted client.
     * <p>This will skip any authentication and will not set any timeout.</p>
     * @since 1.3.2
     */
    public void nextClientIsTrusted();

    /**
     * @since 1.3.2
     */
    private synchronized boolean getSkipValidation();

    /**
     * @since 1.3.2
     */
    private synchronized void setSkipValidation(boolean validation);

    /**
     * Sets the communication logging flag.
     * @see #getCommunicationLogging
     * @since 1.3.2
     */
    public void setCommunicationLogging(boolean communicationLogging);

    /**
     * Returns the communication logging flag.
     * @see #setCommunicationLogging
     * @since 1.3.2
     */
    public boolean getCommunicationLogging();

    /**
     * Sets the SecurityManager class
     * @param securityManagerClass the fully qualified name of the class
     * that extends {@link java.lang.SecurityManager}.
     * @see #getSecurityManagerClass
     * @since 1.3.3
     */
    public void setSecurityManagerClass(String securityManagerClass);

    /**
     * Returns the SecurityManager class
     * @see #setSecurityManagerClass
     * @since 1.3.3
     */
    public String getSecurityManagerClass();

    public SecurityManager getSecurityManager() throws AppException;

    /**
     * Sets the Access constraints
     * @since 1.3.3
     */
    public void setAccessConstraintConfig(AccessConstraintConfig accessConstraintConfig);

    /**
     * Returns Access constraints if present else <code>null</code>.
     * @since 1.3.3
     */
    public AccessConstraintConfig getAccessConstraintConfig();

    /**
     * Sets the classloader to be used to load the dynamically resolved
     * classes
     * @since 1.3.3
     */
    public void setClassLoader(ClassLoader classLoader);

    /**
     * Gets the classloader used to load the dynamically resolved
     * classes.
     * @since 1.4.6
     */
    public ClassLoader getClassLoader();

    /**
     * Utility method to load a class
     * @since 1.3.3
     */
    public Class getClass(String name, boolean reload) throws ClassNotFoundException;

    /**
     * Sets the applications jar/s path. This can be either absolute or
     * relative(to config file) path to the jar file or the directory containing
     * the jars needed by the application.
     * @see #getApplicationJarPath
     * @since 1.3.3
     */
    protected void setApplicationJarPath(String applicationJarPath);

    /**
     * Returns the applications jar/s path. This can be either absolute or
     * relative(to config file) path to the jar file or the directory containing the
     * jars needed by the application.
     * @see #setApplicationJarPath
     * @since 1.3.3
     */
    public String getApplicationJarPath();

    /**
     * Sets the ServerHooks
     * @since 1.3.3
     */
    public void setServerHooks(ServerHooks serverHooks);

    /**
     * Returns ServerHooks if present else <code>null</code>.
     * @since 1.3.3
     */
    public ServerHooks getServerHooks();

    /**
     * @since 1.3.3
     */
    private void loadServerHooksClasses();

    /**
     * @since 1.3.3
     */
    private void processServerHooks(int event);

    /**
     * Creates and returns a copy of this object.
     * @since 1.3.3
     */
    public Object clone();

    /**
     * Sets the Secure setting for QuickServer
     * @since 1.4.0
     */
    public void setSecure(Secure secure);

    /**
     * Returns Secure setting for QuickServer
     * @since 1.4.0
     */
    public Secure getSecure();

    /**
     * <p>Returns if the server is running in Secure mode [SSL or TLS].</p>
     * @since 1.4.0
     */
    public boolean isRunningSecure();

    /**
     * <p>Sets the server mode if its running in Secure mode [SSL or TLS].</p>
     * @since 1.4.0
     */
    public void setRunningSecure(boolean runningSecure);

    private File makeAbsoluteToConfig(String fileName);

    /**
     * Returns a ServerSocket object to be used for listening.
     * @since 1.4.0
     */
    protected void makeServerSocket() throws BindException, IOException;

    /**
     * Sets the basic configuration of the QuickServer.
     * @since 1.4.0
     */
    public void setBasicConfig(BasicServerConfig basicConfig) throws Exception;

    /**
     * Returns the basic configuration of the QuickServer.
     * @since 1.4.0
     */
    public BasicServerConfig getBasicConfig();

    /**
     * Loads the <code>SSLContext</code> from Secure configuring if set.
     * @see #setSecure
     * @since 1.4.0
     */
    public void loadSSLContext() throws IOException;

    /**
     * Returns the <code>SSLContext</code> from Secure configuring.
     * @see #loadSSLContext
     * @since 1.4.0
     */
    public SSLContext getSSLContext() throws IOException, NoSuchAlgorithmException, KeyManagementException;

    /**
     * Returns the <code>SSLContext</code> object that implements the specified
     * secure socket protocol from Secure configuring.
     * @see #loadSSLContext
     * @param protocol the standard name of the requested protocol. If <code>null</code> will use the protocol set in secure configuration of the server.
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @since 1.4.0
     */
    public SSLContext getSSLContext(String protocol) throws IOException, NoSuchAlgorithmException, KeyManagementException;

    /**
     * Returns a SSLSocketFactory object to be used for creating SSLSockets.
     * Secure socket protocol will be picked from the Secure configuring.
     * @see #setSecure
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @since 1.4.0
     */
    public SSLSocketFactory getSSLSocketFactory() throws IOException, NoSuchAlgorithmException, KeyManagementException;

    /**
     * Returns a SSLSocketFactory object to be used for creating SSLSockets.
     * @see #setSecure
     * @param protocol the standard name of the requested protocol. If
     * <code>null</code> will use the protocol set in secure configuration
     * of the server.
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @since 1.4.0
     */
    public SSLSocketFactory getSSLSocketFactory(String protocol) throws IOException, NoSuchAlgorithmException, KeyManagementException;

    /**
     * Sets the ClientBinaryHandler class that interacts with
     * client sockets to handle binary data.
     * @param handler object the fully qualified name of the class that
     *  implements {@link ClientBinaryHandler}
     * @see #getClientBinaryHandler
     * @since 1.4
     */
    public void setClientBinaryHandler(String handler);

    /**
     * Returns the ClientBinaryHandler class that interacts with
     * client sockets.
     * @see #setClientBinaryHandler
     * @since 1.4
     */
    public String getClientBinaryHandler();

    /**
     * Sets the Selector (NIO).
     * @since 1.4.5
     */
    public void setSelector(Selector selector);

    /**
     * Returns the Selector (NIO),if any.
     * @since 1.4.5
     */
    public Selector getSelector();

    /**
     * Starts server in blocking mode.
     * @since 1.4.5
     */
    private void runBlocking(TheClient theClient) throws Exception;

    /**
     * Starts server in non-blocking mode.
     * @since 1.4.5
     */
    private void runNonBlocking(TheClient theClient) throws Exception;

    private boolean checkAccessConstraint(Socket socket);

    /**
     * Register the given channel for the given operations. This adds the request
     * to a list and will be processed after selector select wakes up.
     * @return boolean flag to indicate if new entry was added to the list to register.
     * @since 1.4.5
     */
    public boolean registerChannel(SocketChannel channel, int ops, Object att) throws IOException, ClosedChannelException;

    /**
     * Makes the pool of ByteBuffer
     * @since 1.4.5
     */
    private void makeByteBufferPool(PoolConfig opConfig);

    /**
     * Returns ObjectPool of java.nio.ByteBuffer class.
     * @since 1.4.5
     */
    public ObjectPool getByteBufferPool();

    /**
     * Makes the pool of ByteBuffer
     * @since 1.4.5
     */
    private void makeClientPool(PoolConfig opConfig) throws Exception;

    /**
     * Sets the ClientWriteHandler class that interacts with
     * client sockets to handle data write (only used in non-blocking mode).
     * @param handler object the fully qualified name of the class that
     *  implements {@link ClientWriteHandler}
     * @see #getClientWriteHandler
     * @since 1.4.5
     */
    public void setClientWriteHandler(String handler);

    /**
     * Returns the ClientWriteHandler class that interacts with
     * client sockets (only used in non-blocking mode).
     * @see #setClientWriteHandler
     * @since 1.4.5
     */
    public String getClientWriteHandler();

    /**
     * Returns the date/time when the server was last started.
     * @return last started time. Will be <code>null</code> if never started.
     * @since 1.4.5
     */
    public java.util.Date getLastStartTime();

    /**
     * Sets the debug flag to ByteBufferOutputStream and
     * ByteBufferInputStream class that are used in non-blcking mode
     * @since 1.4.5
     */
    public static void setDebugNonBlockingMode(boolean flag);

    /**
     * Returns the implementation that is used to do Client Identification.
     * @since 1.4.5
     */
    public ClientIdentifier getClientIdentifier();

    /**
     * Makes QSObjectPool from ObjectPool
     * @since 1.4.5
     */
    private QSObjectPool makeQSObjectPool(ObjectPool objectPool) throws Exception;

    /**
     * Returns the current blocking mode of the server.
     * @since 1.4.6
     */
    public boolean getBlockingMode();

    /**
     * Loads all the Business Logic class
     * @since 1.4.6
     */
    protected void loadBusinessLogic() throws Exception;

    /**
     * Sets the ClientEventHandler class that gets notified of
     * client events.
     * @param handler the fully qualified name of the class that
     *  implements {@link ClientEventHandler}
     * @see #getClientEventHandler
     * @since 1.4.6
     */
    public void setClientEventHandler(String handler);

    /**
     * Returns the ClientEventHandler class that gets notified of
     * client events.
     * @see #setClientEventHandler
     * @since 1.4.6
     */
    public String getClientEventHandler();

    /**
     * Sets the default {@link DataMode} for the ClientHandler
     * @since 1.4.6
     */
    public void setDefaultDataMode(DataMode dataMode, DataType dataType) throws IOException;

    /**
     * Sets the default {@link DataMode} for the ClientHandler
     * @since 1.4.6
     */
    public void setDefaultDataMode(DefaultDataMode defaultDataMode) throws IOException;

    /**
     * Returns the default {@link DataMode} for the ClientHandler
     * @since 1.4.6
     */
    public DataMode getDefaultDataMode(DataType dataType);

    /**
     * Sets the ClientExtendedEventHandler class that gets notified of
     * extended client events.
     * @param handler the fully qualified name of the class that
     *  implements {@link ClientExtendedEventHandler}
     * @see #getClientExtendedEventHandler
     * @since 1.4.6
     */
    public void setClientExtendedEventHandler(String handler);

    /**
     * Returns the ClientExtendedEventHandler class that gets notified of
     * extended client events.
     * @see #setClientExtendedEventHandler
     * @since 1.4.6
     */
    public String getClientExtendedEventHandler();

    /**
     * If Application Jar Path was set, load the jars
     * @since 1.4.6
     */
    private void loadApplicationClasses() throws Exception;

    /**
     * Returns PID of the JVM
     * @return PID of the JVM
     * @since 1.4.8
     */
    public static String getPID();

    public boolean isRawCommunicationLogging();

    public void setRawCommunicationLogging(boolean rawCommunicationLogging);

    public int getRawCommunicationMaxLength();

    public void setRawCommunicationMaxLength(int rawCommunicationMaxLength);
}

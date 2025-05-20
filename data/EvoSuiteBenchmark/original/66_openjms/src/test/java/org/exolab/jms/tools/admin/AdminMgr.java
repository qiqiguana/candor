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
 * Copyright 2000-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: AdminMgr.java,v 1.3 2006/02/23 11:02:57 tanderson Exp $
 *
 * Date         Author  Changes
 */


package org.exolab.jms.tools.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.exolab.jms.config.AdminConfiguration;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.ConfigurationLoader;
import org.exolab.jms.util.CommandLine;


/**
 * This class is the Gui controller for the JMS administration. It displays data
 * as a hierarchical set of tree nodes.
 * <p/>
 * <P>The Root is all the contactable JMS servers, idealy there can be several
 * of these all on different ports, with one common admin port they all listen
 * to. A user selects a JMSServer then connects via the menu item. This allows
 * the admin GUI to connect to the server via the main port and begin displaying
 * all its Queue/Topics and registered consumers.
 * <p/>
 * If there are no queue/topics for a consumer the node will be empty. Selecting
 * a consumer allows a user to see what its details are, i.e last message
 * received and acked, whether the consumer is currently active/deactive paused
 * etc.
 * <p/>
 * <P>The Gui can also be used to add/remove queue/topics and consumer
 * registrations.
 * <p/>
 * Reliable Topics are read only, since they cannot be persisted, they simply
 * display a snapshot at the time of connection.
 * <p/>
 * <P>For the moment this is not truly dynamic, that is a refresh needs to be
 * activated on the Gui to cause an update (other than changes made through the
 * Gui istelf).
 *
 * @author <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @version $Revision: 1.3 $ $Date: 2006/02/23 11:02:57 $
 */
public class AdminMgr extends JFrame {

    private JMenu _file;
    private JMenuItem _exit;
    private JMenu _connections;
    private JMenuItem _refresh;
    private JMenuItem _online;
    private JMenuItem _offline;
    private JMenuItem _disconnect;
    private JMenuItem _startup;
    private JMenuItem _shutdown;
    private JTree _serverProperties;
    private JTextField _messageArea;

    private Configuration _config;

    // If this Admin object is connected to any OpenJMS
    private boolean _connected = false;

    /**
     * The server start command
     */
    private static String _serverStart = null;

    /**
     * The server configuration file path
     */
    private static String _serverConfig = null;

    // redirect stream to local console.
    private StreamRedirect _output = null;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(AdminMgr.class);


    public AdminMgr(String path) throws Exception {
        _config = new ConfigurationLoader().load(path);
        initComponents(path);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * All the GUI objects are created, and callbacks registered.
     */
    private void initComponents(String title) {
        JMenuBar menuBar = new JMenuBar();
        _file = new JMenu();
        _exit = new JMenuItem();
        JMenu actions = new JMenu();
        _connections = new JMenu();
        _refresh = new JMenuItem();
        _online = new JMenuItem();
        _offline = new JMenuItem();
        _disconnect = new JMenuItem();
        JSeparator separator = new JSeparator();
        _startup = new JMenuItem();
        _shutdown = new JMenuItem();
        JScrollPane jmsServers = new JScrollPane();
        JComboBox jmsCombo = new JComboBox();
        _serverProperties = new JTree();
        setTitle("OpenJMS Administrator: " + title);
        DefaultTreeModel serverModel =
                OpenJMSServer.createServerList(_serverProperties);
        _serverProperties.setModel(serverModel);
        AdminInfo info = new AdminInfo();
        _serverProperties.setCellRenderer(info);

        _messageArea = new JTextField();
        _file.setText("File");
        _exit.setToolTipText("Exit administration");
        _exit.setText("Exit");
        _exit.setMnemonic('x');

        _serverProperties.setRootVisible(false);
        _serverProperties.setShowsRootHandles(true);
        _serverProperties.putClientProperty("JTree.lineStyle", "Angled");
        _serverProperties.setCellEditor(new OpenJMSEditor(_serverProperties,
                jmsCombo));
        _serverProperties.setEditable(false);
        setupCallbacks();
        _file.add(_exit);
        _file.setMnemonic('F');
        menuBar.add(_file);
        actions.setText("Actions");
        actions.setMnemonic('A');
        _connections.setText("Connections");
        _connections.setMnemonic('C');
        _refresh.setToolTipText("Refresh the display");
        _online.setToolTipText("Connect to a running OpenJMS Server");
        _offline.setToolTipText("Connect directly to an OpenJMS database");
        _refresh.setText("Refresh");
        _refresh.setMnemonic('R');
        actions.add(_refresh);

        _online.setText("Online");
        _online.setMnemonic('O');
        _offline.setText("Offline");
        _offline.setMnemonic('f');
        _connections.add(_online);
        _connections.add(_offline);
        actions.add(_connections);
        _disconnect.setToolTipText
                ("Disconnect from any connected OpenJMS Servers");
        _disconnect.setText("Disconnect");
        _disconnect.setMnemonic('D');
        actions.add(_disconnect);

        actions.add(separator);
        _startup.setToolTipText("Start the OpenJMS server");
        _startup.setText("Start OpenJMS");
        _startup.setMnemonic('S');
        _shutdown.setToolTipText("Shutdown the connected OpenJMS server");
        _shutdown.setText("Shutdown OpenJMS");
        _shutdown.setMnemonic('h');
        actions.add(_startup);
        actions.add(_shutdown);
        menuBar.add(actions);

        jmsServers.setViewportView(_serverProperties);


        getContentPane().add(jmsServers, BorderLayout.CENTER);

        _messageArea.setToolTipText("Message Area");
        _messageArea.setEditable(false);
        _messageArea.setForeground(java.awt.Color.red);
        _messageArea.setText("Not Connected");
        _messageArea.setHorizontalAlignment(SwingConstants.CENTER);


        getContentPane().add(_messageArea, BorderLayout.SOUTH);
        setJMenuBar(menuBar);
        _startup.setEnabled(true);
        _shutdown.setEnabled(false);
        _refresh.setEnabled(false);
        _disconnect.setEnabled(false);
    }

    /**
     * The exit method for the application, when the user shutdowns the form.
     */
    private void exitAdmin() {
        System.exit(0);
    }

    /**
     * Exit the Application when a user selects File->Exit from the menu
     */
    private void exitForm() {
        System.exit(0);
    }

    /**
     * Refresh the display, and repaint all items.
     */
    private void refresh() {
        if (AbstractAdminConnection.instance() instanceof OnlineConnection) {
            setConnected(false, null);
            setConnected(true, "Connected - Online Mode");
            _startup.setEnabled(false);
            _shutdown.setEnabled(true);

        } else {
            ((OpenJMSServer) (_serverProperties.getModel().getRoot()
            )).refresh();
        }
    }

    /**
     * Start the OpenJMSServer.
     */
    private void startup() {

        try {
            String args[] = getStartCommand();

            System.out.print("running ");
            for (int i = 0, j = args.length; i < j; i++) {
                System.out.print(args[i] + " ");
            }
            System.out.println();

            if (_output != null) {
                // Stop the old redirect if any.
                _output.interrupt();
            }
            Process proc = Runtime.getRuntime().exec(args);
            // Redirect output
            _output = new StreamRedirect(proc.getInputStream());
            // kick it off
            _output.start();
        } catch (Exception err) {
            JOptionPane.showMessageDialog
                    (this, "Failed to Startup OpenJMSServer:\n" + err
                            + "\nCheck config file",
                            "OpenJMSServer Startup", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * When a user wishes to connect to all known OpenJMSServers. Attempt to
     * create an RMI connection to the OpenJMSServer. If the server is not
     * running, this will fail. The user can start the server through the start
     * server command, and attempt to re-connect, or use the offline method
     * below.
     */
    private void onlineConnect() {
        try {
            // if online.
            new OnlineConnection(this, _config);
            _startup.setEnabled(false);
            _shutdown.setEnabled(true);
            setConnected(true, "Connected - Online Mode");
        } catch (Exception err) {
            JOptionPane.showMessageDialog
                    (this, err.getMessage(), "Online Connection Error",
                            JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Connect to the database in offline mode.
     */
    private void offlineConnect() {
        try {
            // if online.
            new OfflineConnection(this, _config);
            _startup.setEnabled(false);
            _shutdown.setEnabled(false);
            setConnected(true, "Connected - OFFLine Mode");
        } catch (Exception err) {
            error("Database error", err.getMessage(), err);
        }
    }

    /**
     * Disconnect from a connected OpenJMSServer. Close the database, set the
     * connected flag to false, stop displaying the OpenJMS folder.
     */
    private void disconnect() {
        try {
            AbstractAdminConnection.instance().close();
            setConnected(false, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog
                    (this, e.getMessage(), "Database Close Error",
                            JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * A conveniance routine to open/close all database connections, and fix up
     * the display.
     * <p/>
     * <P>When connecting, show the root object, get any persistent queue/topic
     * names currently in the db, and display them. Turn off the connection
     * menu, enable the disconnection and shutdown and the context menus. Set
     * the message area text to connected.
     * <p/>
     * <P>When disconnecting, turn off the root, destroy all Gui objects close
     * the db connection, turn off all context sensitive menus, disable
     * disconnection menu and enable connection. Set the message text to
     * disconnected.
     *
     * @param c a flag inidication if this is a connection or disconnection.
     */
    private void setConnected(boolean c, String st) {
        if (c) {
            _serverProperties.setRootVisible(true);
            ((OpenJMSServer)
                    (_serverProperties.getModel().getRoot())).displayConnections();
            _connections.setEnabled(false);
            _refresh.setEnabled(true);
            // _shutdown.setEnabled(true);
            _disconnect.setEnabled(true);
            // _startup.setEnabled(false);
            _messageArea.setForeground(java.awt.Color.green.darker().darker());
            _messageArea.setText(st);
            _connected = true;
        } else {
            _serverProperties.setRootVisible(false);
            OpenJMSServer root =
                    (OpenJMSServer) _serverProperties.getModel().getRoot();
            root.removeAllChildren();
            DefaultTreeModel model =
                    (DefaultTreeModel) _serverProperties.getModel();
            model.nodeStructureChanged((DefaultMutableTreeNode) root);
            _connections.setEnabled(true);
            _startup.setEnabled(true);
            _shutdown.setEnabled(false);
            _refresh.setEnabled(false);
            _disconnect.setEnabled(false);
            _messageArea.setForeground(java.awt.Color.red);
            _messageArea.setText("Not Connected");
            _connected = false;
        }
    }

    /**
     * Set up all Action menu callbacks, and mouse events for the tree and its
     * nodes. Check all mose 2 key presses on a node, select the node, then call
     * the nodes appropriate display methos to display its specific popup
     * menus.
     * <p/>
     * <P>When first connected, all queue/topics displayed are not expaned. This
     * is just a performance saver, since their could potentially be hundreds of
     * objects. A callback is set up, so that when a queue/topic is expanded, it
     * is lokked up only then to determine what consumers are registered with
     * it.
     */
    private void setupCallbacks() {

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
                exitForm();
            }
        }
        );


        _serverProperties.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (!_connected) {
                    return;
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    int selRow = _serverProperties.getRowForLocation
                            (e.getX(), e.getY());

                    _serverProperties.setSelectionRow(selRow);
                    Object loc =
                            _serverProperties.getLastSelectedPathComponent();
                    if (loc instanceof OpenJMSNode) {
                        OpenJMSNode node = (OpenJMSNode) loc;
                        node.displayCommands
                                (_serverProperties.getRowBounds(selRow));
                    } else if (loc instanceof OpenJMSServer) {
                        ((OpenJMSServer) loc).displayCommands
                                (_serverProperties.getRowBounds(selRow));
                    }
                }
            }
        }
        );

        _serverProperties.addTreeExpansionListener(new TreeExpansionListener() {

            public void treeCollapsed(TreeExpansionEvent e) {
                // todo Anything.....
            }

            public void treeExpanded(TreeExpansionEvent e) {
                TreePath path = e.getPath();
                Object loc = path.getLastPathComponent();
                if (loc instanceof OpenJMSNode) {
                    OpenJMSNode node = (OpenJMSNode) loc;
                    node.update();
                }
            }
        }
        );

        /**
         _serverProperties.addTreeSelectionListener(new TreeSelectionListener()
         {
         public void valueChanged(TreeSelectionEvent e)
         {
         TreePath path = e.getPath();
         Object loc = path.getLastPathComponent();
         if (loc instanceof OpenJMSNode)
         {
         OpenJMSNode node = (OpenJMSNode)loc;
         System.out.println(node);
         }
         }
         }
         );

         **/
        _exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                exitAdmin();
            }
        }
        );


        _refresh.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                refresh();
            }
        }
        );


        _online.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                onlineConnect();
            }
        }
        );

        _offline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                offlineConnect();
            }
        }
        );

        _disconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                disconnect();
            }
        }
        );

        _startup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startup();
            }
        }
        );

        _shutdown.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                try {
                    AbstractAdminConnection.instance().stopServer();
                    setConnected(false, null);
                } catch (NullPointerException err) {
                    JOptionPane.showMessageDialog
                            (_file, "Must connect with online mode \nto "
                                    + "shutdown server", "Shutdown Error",
                                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        );
    }

    /**
     * The main entry point for this admin gui. The main form and any support
     * dialogs are created. An initial size is given, and the gui placed in the
     * middle of the screen
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            CommandLine cmdline = new CommandLine(args);

            boolean helpSet = cmdline.exists("help");
            boolean configSet = cmdline.exists("config");
            boolean stopServer = cmdline.exists("stopServer");
            String username = cmdline.value("u");
            String password = cmdline.value("p");

            if (helpSet) {
                usage();
            } else if (!configSet && !stopServer && args.length != 0) {
                // invalid argument specified
                usage();
            } else {
                String configFile = cmdline.value("config");
                if (configFile == null) {
                    String home = getOpenJMSHome();
                    configFile = home + "/config/openjms.xml";
                }
                Configuration config = new ConfigurationLoader().load(configFile);
                String path = config.getLoggerConfiguration().getFile();
                if (path != null) {
                    DOMConfigurator.configure(path);
                }
                AdminConfiguration adminConfig = null;

                adminConfig = config.getAdminConfiguration();
                _serverStart = adminConfig.getScript();
                _serverConfig = adminConfig.getConfig();
                if (_serverConfig == null) {
                    _serverConfig = configFile;
                }

                if (stopServer) {
                    // this is a special mode that will just attempt
                    // a connection to the server and stop it. No GUI

                    new OnlineConnection(username, password, config);
                    AbstractAdminConnection.instance().stopServer();
                } else {
                    AdminMgr admin = new AdminMgr(configFile);
                    QueryDialog.create(admin);
                    CreateQueueDialog.create(admin);
                    CreateTopicDialog.create(admin);
                    CreateLogonDialog.create(admin);
                    CreateUserDialog.create(admin);
                    ChangePasswordDialog.create(admin);
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    // About center of screen
                    admin.setLocation(screen.width / 2 - 150, screen.height / 2 - 150);
                    admin.setSize(300, 300);
                    admin.invalidate();
                    admin.show();
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
            System.err.println("Failed to initialize AdminMgr.\nExiting....");
        }
    }

    /**
     * Print out information on running this sevice
     */
    static protected void usage() {
        PrintStream out = System.out;

        out.println("\n\n");
        out.println("=====================================================");
        out.println("Usage information for " + AdminMgr.class.getName());
        out.println("=====================================================");
        out.println("\n" + AdminMgr.class.getName());
        out.println("    [-help | -config <xml config file>]\n");
        out.println("\t-help   displays this screen\n");
        out.println("\t-config file name of xml-based config file\n");
    }

    /**
     * A simple class to re-direct the output stream from JMS to the local
     * console
     */
    class StreamRedirect extends Thread {

        InputStream is_;

        StreamRedirect(InputStream is) {
            is_ = is;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is_);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private String[] getStartCommand() throws Exception {
        ArrayList args = new ArrayList();

        if (_serverStart != null) {
            Perl5Compiler compiler = new Perl5Compiler();
            Pattern pattern = compiler.compile("'.*'|[^\\s]*");
            Perl5Matcher matcher = new Perl5Matcher();
            PatternMatcherInput input = new PatternMatcherInput(_serverStart);

            while (matcher.contains(input, pattern)) {
                String arg = matcher.getMatch().toString();
                if (arg.startsWith("'") && arg.endsWith("'")) {
                    arg = arg.substring(1, arg.length() - 1);
                }
                args.add(arg);
            }
        }

        args.add("-config");
        args.add(_serverConfig);

        return (String[]) args.toArray(new String[0]);
    }

    /**
     * Returns the value of the openjms.home environment variable
     */
    private static String getOpenJMSHome() {
        return System.getProperty("openjms.home",
                System.getProperty("user.dir"));
    }

    /**
     * Display and log an error
     *
     * @param title
     * @param message
     * @param exception
     */
    private void error(String title, String message, Exception exception) {
        _log.error(message, exception);
        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.ERROR_MESSAGE);
    }

} //-- AdminMgr


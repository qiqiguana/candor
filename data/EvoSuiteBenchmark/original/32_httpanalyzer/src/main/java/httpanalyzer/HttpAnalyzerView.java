/*
 * GNU GENERAL PUBLIC LICENSE
 * Version 3, 29 June 2007
 *
 * Copyright (C) 2010, vlad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * HttpAnalyzerView.java
 */
package httpanalyzer;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.apache.http.*;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.jdesktop.application.Task;

/**
 * The application's main frame.
 */
public class HttpAnalyzerView extends FrameView {

    HttpResponse httpResponse;
    String targetHost;      // Hostname
    int targetPort;
    String targetPath;
    String referer;
    String proxyHost;
    int proxyPort;

    public HttpAnalyzerView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        // My pre-init
        String proxyEnv = new InitBasicParams().getProxyEnv();
        if (proxyEnv != null) {
            proxyField.setText(proxyEnv);
        }
        initRequestHeaders();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = HttpAnalyzerApp.getApplication().getMainFrame();
            aboutBox = new HttpAnalyzerAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        HttpAnalyzerApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        mainPanel = new javax.swing.JPanel();
        urlLabel = new javax.swing.JLabel();
        urlCombo = new javax.swing.JComboBox();
        goButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        methodPanel = new javax.swing.JPanel();
        getRadio = new javax.swing.JRadioButton();
        postRadio = new javax.swing.JRadioButton();
        headRadio = new javax.swing.JRadioButton();
        paramsButton = new javax.swing.JButton();
        clearURL = new javax.swing.JButton();
        mainTabbedPane = new javax.swing.JTabbedPane();
        replayDataScrollPane = new javax.swing.JScrollPane();
        replayDataPane = new javax.swing.JTextPane();
        optionsPanel = new javax.swing.JPanel();
        proxyTypesPanel = new javax.swing.JPanel();
        proxyNoneRadioButton = new javax.swing.JRadioButton();
        proxyHttpRadioButton = new javax.swing.JRadioButton();
        proxySocksRadioButton = new javax.swing.JRadioButton();
        proxySetsPanel = new javax.swing.JPanel();
        proxyField = new javax.swing.JTextField();
        proxyHostLabel = new javax.swing.JLabel();
        proxyUserLabel = new javax.swing.JLabel();
        proxyAuthCheckBox = new javax.swing.JCheckBox();
        proxyPassLabel = new javax.swing.JLabel();
        proxyUserTextField = new javax.swing.JTextField();
        proxyPassPasswordField = new javax.swing.JPasswordField();
        httpOptionsPanel = new javax.swing.JPanel();
        httpVerLabel = new javax.swing.JLabel();
        httpVersionComboBox = new javax.swing.JComboBox();
        httpUserLabel = new javax.swing.JLabel();
        httpPassLabel = new javax.swing.JLabel();
        httpPassPasswordField = new javax.swing.JPasswordField();
        httpUserTextField = new javax.swing.JTextField();
        httpAuthCheckBox = new javax.swing.JCheckBox();
        basicHeadersPanel = new javax.swing.JPanel();
        customRefererCheckBox = new javax.swing.JCheckBox();
        customRefererTextField = new javax.swing.JTextField();
        customCookieCheckBox = new javax.swing.JCheckBox();
        customCookieTextField = new javax.swing.JTextField();
        agentsSeparator = new javax.swing.JSeparator();
        agentsLabel = new javax.swing.JLabel();
        firefoxRadioButton = new javax.swing.JRadioButton();
        ie6RadioButton = new javax.swing.JRadioButton();
        ie7RadioButton = new javax.swing.JRadioButton();
        ie8RadioButton = new javax.swing.JRadioButton();
        iPhoneRadioButton = new javax.swing.JRadioButton();
        googleBotRadioButton = new javax.swing.JRadioButton();
        msnRadioButton = new javax.swing.JRadioButton();
        yahooRadioButton = new javax.swing.JRadioButton();
        customHeaderPanel = new javax.swing.JPanel();
        customHeadersScrollPane = new javax.swing.JScrollPane();
        customHeaders = new javax.swing.JTextArea();
        useCustomHeadersCheckBox = new javax.swing.JCheckBox();
        debugPanel = new javax.swing.JPanel();
        debugDataScrollPane = new javax.swing.JScrollPane();
        debugDataPane = new javax.swing.JTextPane();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        clearParams = new javax.swing.JButton();
        clipboardButton = new javax.swing.JButton();
        paramsComboBox = new javax.swing.JComboBox();
        paramsLabel = new javax.swing.JLabel();
        mainSeparator = new javax.swing.JSeparator();
        intelSaveCheckBox = new javax.swing.JCheckBox();
        toFileTextField = new javax.swing.JTextField();
        toFileButton = new javax.swing.JButton();
        toFileCheckBox = new javax.swing.JCheckBox();
        mainMenuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        saveMenuItem = new javax.swing.JMenuItem();
        clearMenuItem1 = new javax.swing.JMenuItem();
        filesSeparator1 = new javax.swing.JPopupMenu.Separator();
        saveTemplateItem = new javax.swing.JMenuItem();
        loadTemplateItem = new javax.swing.JMenuItem();
        filesSeparator2 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        notesMenuItem = new javax.swing.JMenuItem();
        toolsSeparator = new javax.swing.JPopupMenu.Separator();
        cryptMenuItem = new javax.swing.JMenuItem();
        md5MenuItem = new javax.swing.JMenuItem();
        base64MenuItem = new javax.swing.JMenuItem();
        toolsSeparator2 = new javax.swing.JPopupMenu.Separator();
        optionsMenu = new javax.swing.JMenu();
        mergeInfoCheckBox = new javax.swing.JCheckBoxMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        methodGroups = new javax.swing.ButtonGroup();
        proxyTypesGroup = new javax.swing.ButtonGroup();
        userAgentsGroup = new javax.swing.ButtonGroup();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(httpanalyzer.HttpAnalyzerApp.class).getContext().getResourceMap(HttpAnalyzerView.class);
        urlLabel.setFont(resourceMap.getFont("urlLabel.font")); // NOI18N
        urlLabel.setText(resourceMap.getString("urlLabel.text")); // NOI18N
        urlLabel.setFocusable(false);
        urlLabel.setName("urlLabel"); // NOI18N

        urlCombo.setEditable(true);
        urlCombo.setFont(resourceMap.getFont("urlCombo.font")); // NOI18N
        urlCombo.setToolTipText(resourceMap.getString("urlCombo.toolTipText")); // NOI18N
        urlCombo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        urlCombo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        urlCombo.setName("urlCombo"); // NOI18N
        urlCombo.setNextFocusableComponent(paramsComboBox);
        urlCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                urlComboItemStateChanged(evt);
            }
        });
        urlCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlComboActionPerformed(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(httpanalyzer.HttpAnalyzerApp.class).getContext().getActionMap(HttpAnalyzerView.class, this);
        goButton.setAction(actionMap.get("RequestRun")); // NOI18N
        goButton.setFont(resourceMap.getFont("goButton.font")); // NOI18N
        goButton.setIcon(resourceMap.getIcon("goButton.icon")); // NOI18N
        goButton.setText(resourceMap.getString("goButton.text")); // NOI18N
        goButton.setName("goButton"); // NOI18N

        clearButton.setAction(actionMap.get("ClearDataPanel")); // NOI18N
        clearButton.setFont(resourceMap.getFont("clearButton.font")); // NOI18N
        clearButton.setIcon(resourceMap.getIcon("clearButton.icon")); // NOI18N
        clearButton.setText(resourceMap.getString("clearButton.text")); // NOI18N
        clearButton.setToolTipText(resourceMap.getString("clearButton.toolTipText")); // NOI18N
        clearButton.setName("clearButton"); // NOI18N

        saveButton.setAction(actionMap.get("fileChoose")); // NOI18N
        saveButton.setFont(resourceMap.getFont("saveButton.font")); // NOI18N
        saveButton.setIcon(resourceMap.getIcon("saveButton.icon")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setToolTipText(resourceMap.getString("saveButton.toolTipText")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        methodPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), resourceMap.getString("methodPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("methodPanel.border.titleFont"))); // NOI18N
        methodPanel.setName("methodPanel"); // NOI18N

        methodGroups.add(getRadio);
        getRadio.setFont(resourceMap.getFont("headRadio.font")); // NOI18N
        getRadio.setSelected(true);
        getRadio.setText(resourceMap.getString("getRadio.text")); // NOI18N
        getRadio.setBorder(null);
        getRadio.setName("getRadio"); // NOI18N

        methodGroups.add(postRadio);
        postRadio.setFont(resourceMap.getFont("headRadio.font")); // NOI18N
        postRadio.setText(resourceMap.getString("postRadio.text")); // NOI18N
        postRadio.setBorder(null);
        postRadio.setName("postRadio"); // NOI18N

        methodGroups.add(headRadio);
        headRadio.setFont(resourceMap.getFont("headRadio.font")); // NOI18N
        headRadio.setText(resourceMap.getString("headRadio.text")); // NOI18N
        headRadio.setBorder(null);
        headRadio.setName("headRadio"); // NOI18N

        javax.swing.GroupLayout methodPanelLayout = new javax.swing.GroupLayout(methodPanel);
        methodPanel.setLayout(methodPanelLayout);
        methodPanelLayout.setHorizontalGroup(
            methodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(methodPanelLayout.createSequentialGroup()
                .addGroup(methodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(getRadio)
                    .addComponent(postRadio)
                    .addComponent(headRadio))
                .addContainerGap(4, Short.MAX_VALUE))
        );
        methodPanelLayout.setVerticalGroup(
            methodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(methodPanelLayout.createSequentialGroup()
                .addComponent(getRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headRadio))
        );

        paramsButton.setFont(resourceMap.getFont("paramsButton.font")); // NOI18N
        paramsButton.setText(resourceMap.getString("paramsButton.text")); // NOI18N
        paramsButton.setToolTipText(resourceMap.getString("paramsButton.toolTipText")); // NOI18N
        paramsButton.setName("paramsButton"); // NOI18N
        paramsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paramsButtonActionPerformed(evt);
            }
        });

        clearURL.setFont(resourceMap.getFont("clearURL.font")); // NOI18N
        clearURL.setText(resourceMap.getString("clearURL.text")); // NOI18N
        clearURL.setToolTipText(resourceMap.getString("clearURL.toolTipText")); // NOI18N
        clearURL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        clearURL.setName("clearURL"); // NOI18N
        clearURL.setNextFocusableComponent(urlCombo);
        clearURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearURLActionPerformed(evt);
            }
        });

        mainTabbedPane.setFont(resourceMap.getFont("mainTabbedPane.font")); // NOI18N
        mainTabbedPane.setName("mainTabbedPane"); // NOI18N

        replayDataScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), resourceMap.getString("replayDataScrollPane.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, resourceMap.getFont("replayDataScrollPane.border.titleFont"))); // NOI18N
        replayDataScrollPane.setName("replayDataScrollPane"); // NOI18N

        replayDataPane.setBackground(resourceMap.getColor("replayDataPane.background")); // NOI18N
        replayDataPane.setName("replayDataPane"); // NOI18N
        replayDataScrollPane.setViewportView(replayDataPane);

        mainTabbedPane.addTab(resourceMap.getString("replayDataScrollPane.TabConstraints.tabTitle"), replayDataScrollPane); // NOI18N

        optionsPanel.setName("optionsPanel"); // NOI18N

        proxyTypesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("proxyTypesPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("proxyTypesPanel.border.titleFont"))); // NOI18N
        proxyTypesPanel.setName("proxyTypesPanel"); // NOI18N

        proxyTypesGroup.add(proxyNoneRadioButton);
        proxyNoneRadioButton.setFont(resourceMap.getFont("proxySocksRadioButton.font")); // NOI18N
        proxyNoneRadioButton.setSelected(true);
        proxyNoneRadioButton.setText(resourceMap.getString("proxyNoneRadioButton.text")); // NOI18N
        proxyNoneRadioButton.setName("proxyNoneRadioButton"); // NOI18N

        proxyTypesGroup.add(proxyHttpRadioButton);
        proxyHttpRadioButton.setFont(resourceMap.getFont("proxySocksRadioButton.font")); // NOI18N
        proxyHttpRadioButton.setText(resourceMap.getString("proxyHttpRadioButton.text")); // NOI18N
        proxyHttpRadioButton.setName("proxyHttpRadioButton"); // NOI18N

        proxyTypesGroup.add(proxySocksRadioButton);
        proxySocksRadioButton.setFont(resourceMap.getFont("proxySocksRadioButton.font")); // NOI18N
        proxySocksRadioButton.setText(resourceMap.getString("proxySocksRadioButton.text")); // NOI18N
        proxySocksRadioButton.setName("proxySocksRadioButton"); // NOI18N

        javax.swing.GroupLayout proxyTypesPanelLayout = new javax.swing.GroupLayout(proxyTypesPanel);
        proxyTypesPanel.setLayout(proxyTypesPanelLayout);
        proxyTypesPanelLayout.setHorizontalGroup(
            proxyTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proxyTypesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proxyTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proxyNoneRadioButton)
                    .addComponent(proxyHttpRadioButton)
                    .addComponent(proxySocksRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proxyTypesPanelLayout.setVerticalGroup(
            proxyTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proxyTypesPanelLayout.createSequentialGroup()
                .addComponent(proxyNoneRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proxyHttpRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proxySocksRadioButton)
                .addContainerGap(4, Short.MAX_VALUE))
        );

        proxySetsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("proxySetsPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("proxySetsPanel.border.titleFont"))); // NOI18N
        proxySetsPanel.setName("proxySetsPanel"); // NOI18N

        proxyField.setText(resourceMap.getString("proxyField.text")); // NOI18N
        proxyField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        proxyField.setName("proxyField"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, proxyNoneRadioButton, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), proxyField, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        proxyHostLabel.setFont(resourceMap.getFont("proxyHostLabel.font")); // NOI18N
        proxyHostLabel.setText(resourceMap.getString("proxyHostLabel.text")); // NOI18N
        proxyHostLabel.setName("proxyHostLabel"); // NOI18N

        proxyUserLabel.setFont(resourceMap.getFont("proxyUserLabel.font")); // NOI18N
        proxyUserLabel.setText(resourceMap.getString("proxyUserLabel.text")); // NOI18N
        proxyUserLabel.setName("proxyUserLabel"); // NOI18N

        proxyAuthCheckBox.setFont(resourceMap.getFont("proxyAuthCheckBox.font")); // NOI18N
        proxyAuthCheckBox.setText(resourceMap.getString("proxyAuthCheckBox.text")); // NOI18N
        proxyAuthCheckBox.setToolTipText(resourceMap.getString("proxyAuthCheckBox.toolTipText")); // NOI18N
        proxyAuthCheckBox.setName("proxyAuthCheckBox"); // NOI18N

        proxyPassLabel.setFont(resourceMap.getFont("proxyPassLabel.font")); // NOI18N
        proxyPassLabel.setText(resourceMap.getString("proxyPassLabel.text")); // NOI18N
        proxyPassLabel.setName("proxyPassLabel"); // NOI18N

        proxyUserTextField.setText(resourceMap.getString("proxyUserTextField.text")); // NOI18N
        proxyUserTextField.setName("proxyUserTextField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, proxyAuthCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), proxyUserTextField, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        proxyPassPasswordField.setText(resourceMap.getString("proxyPassPasswordField.text")); // NOI18N
        proxyPassPasswordField.setName("proxyPassPasswordField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, proxyAuthCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), proxyPassPasswordField, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout proxySetsPanelLayout = new javax.swing.GroupLayout(proxySetsPanel);
        proxySetsPanel.setLayout(proxySetsPanelLayout);
        proxySetsPanelLayout.setHorizontalGroup(
            proxySetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proxySetsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proxySetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proxySetsPanelLayout.createSequentialGroup()
                        .addGroup(proxySetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proxyHostLabel)
                            .addComponent(proxyAuthCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(proxySetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(proxySetsPanelLayout.createSequentialGroup()
                                .addComponent(proxyUserLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(proxyUserTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                            .addComponent(proxyField, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)))
                    .addGroup(proxySetsPanelLayout.createSequentialGroup()
                        .addComponent(proxyPassLabel)
                        .addGap(21, 21, 21)
                        .addComponent(proxyPassPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)))
                .addContainerGap())
        );
        proxySetsPanelLayout.setVerticalGroup(
            proxySetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proxySetsPanelLayout.createSequentialGroup()
                .addGroup(proxySetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proxyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proxyHostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proxySetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proxyUserLabel)
                    .addComponent(proxyAuthCheckBox)
                    .addComponent(proxyUserTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proxySetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proxyPassPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proxyPassLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        httpOptionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        httpOptionsPanel.setName("httpOptionsPanel"); // NOI18N

        httpVerLabel.setFont(resourceMap.getFont("httpVerLabel.font")); // NOI18N
        httpVerLabel.setText(resourceMap.getString("httpVerLabel.text")); // NOI18N
        httpVerLabel.setName("httpVerLabel"); // NOI18N

        httpVersionComboBox.setFont(resourceMap.getFont("httpVersionComboBox.font")); // NOI18N
        httpVersionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1.1", "1.0" }));
        httpVersionComboBox.setName("httpVersionComboBox"); // NOI18N

        httpUserLabel.setFont(resourceMap.getFont("httpUserLabel.font")); // NOI18N
        httpUserLabel.setText(resourceMap.getString("httpUserLabel.text")); // NOI18N
        httpUserLabel.setName("httpUserLabel"); // NOI18N

        httpPassLabel.setFont(resourceMap.getFont("httpPassLabel.font")); // NOI18N
        httpPassLabel.setText(resourceMap.getString("httpPassLabel.text")); // NOI18N
        httpPassLabel.setName("httpPassLabel"); // NOI18N

        httpPassPasswordField.setName("httpPassPasswordField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, httpAuthCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), httpPassPasswordField, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        httpUserTextField.setName("httpUserTextField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, httpAuthCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), httpUserTextField, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        httpAuthCheckBox.setFont(resourceMap.getFont("httpAuthCheckBox.font")); // NOI18N
        httpAuthCheckBox.setText(resourceMap.getString("httpAuthCheckBox.text")); // NOI18N
        httpAuthCheckBox.setFocusable(false);
        httpAuthCheckBox.setName("httpAuthCheckBox"); // NOI18N

        basicHeadersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("basicHeadersPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("basicHeadersPanel.border.titleFont"))); // NOI18N
        basicHeadersPanel.setName("basicHeadersPanel"); // NOI18N

        customRefererCheckBox.setFont(resourceMap.getFont("customRefererCheckBox.font")); // NOI18N
        customRefererCheckBox.setText(resourceMap.getString("customRefererCheckBox.text")); // NOI18N
        customRefererCheckBox.setName("customRefererCheckBox"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), customRefererCheckBox, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        customRefererTextField.setText(resourceMap.getString("customRefererTextField.text")); // NOI18N
        customRefererTextField.setName("customRefererTextField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, customRefererCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), customRefererTextField, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        customCookieCheckBox.setFont(resourceMap.getFont("customCookieCheckBox.font")); // NOI18N
        customCookieCheckBox.setText(resourceMap.getString("customCookieCheckBox.text")); // NOI18N
        customCookieCheckBox.setName("customCookieCheckBox"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), customCookieCheckBox, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        customCookieTextField.setName("customCookieTextField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, customCookieCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), customCookieTextField, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        agentsSeparator.setName("agentsSeparator"); // NOI18N

        agentsLabel.setFont(resourceMap.getFont("agentsLabel.font")); // NOI18N
        agentsLabel.setText(resourceMap.getString("agentsLabel.text")); // NOI18N
        agentsLabel.setName("agentsLabel"); // NOI18N

        userAgentsGroup.add(firefoxRadioButton);
        firefoxRadioButton.setFont(resourceMap.getFont("googleBotRadioButton.font")); // NOI18N
        firefoxRadioButton.setText(resourceMap.getString("firefoxRadioButton.text")); // NOI18N
        firefoxRadioButton.setName("firefoxRadioButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), firefoxRadioButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        userAgentsGroup.add(ie6RadioButton);
        ie6RadioButton.setFont(resourceMap.getFont("ie6RadioButton.font")); // NOI18N
        ie6RadioButton.setSelected(true);
        ie6RadioButton.setText(resourceMap.getString("ie6RadioButton.text")); // NOI18N
        ie6RadioButton.setName("ie6RadioButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), ie6RadioButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        userAgentsGroup.add(ie7RadioButton);
        ie7RadioButton.setFont(resourceMap.getFont("googleBotRadioButton.font")); // NOI18N
        ie7RadioButton.setText(resourceMap.getString("ie7RadioButton.text")); // NOI18N
        ie7RadioButton.setName("ie7RadioButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), ie7RadioButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        userAgentsGroup.add(ie8RadioButton);
        ie8RadioButton.setFont(resourceMap.getFont("googleBotRadioButton.font")); // NOI18N
        ie8RadioButton.setText(resourceMap.getString("ie8RadioButton.text")); // NOI18N
        ie8RadioButton.setName("ie8RadioButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), ie8RadioButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        userAgentsGroup.add(iPhoneRadioButton);
        iPhoneRadioButton.setFont(resourceMap.getFont("googleBotRadioButton.font")); // NOI18N
        iPhoneRadioButton.setText(resourceMap.getString("iPhoneRadioButton.text")); // NOI18N
        iPhoneRadioButton.setName("iPhoneRadioButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), iPhoneRadioButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        userAgentsGroup.add(googleBotRadioButton);
        googleBotRadioButton.setFont(resourceMap.getFont("googleBotRadioButton.font")); // NOI18N
        googleBotRadioButton.setText(resourceMap.getString("googleBotRadioButton.text")); // NOI18N
        googleBotRadioButton.setName("googleBotRadioButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), googleBotRadioButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        userAgentsGroup.add(msnRadioButton);
        msnRadioButton.setFont(resourceMap.getFont("msnRadioButton.font")); // NOI18N
        msnRadioButton.setText(resourceMap.getString("msnRadioButton.text")); // NOI18N
        msnRadioButton.setName("msnRadioButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), msnRadioButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        userAgentsGroup.add(yahooRadioButton);
        yahooRadioButton.setFont(resourceMap.getFont("yahooRadioButton.font")); // NOI18N
        yahooRadioButton.setText(resourceMap.getString("yahooRadioButton.text")); // NOI18N
        yahooRadioButton.setName("yahooRadioButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), yahooRadioButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout basicHeadersPanelLayout = new javax.swing.GroupLayout(basicHeadersPanel);
        basicHeadersPanel.setLayout(basicHeadersPanelLayout);
        basicHeadersPanelLayout.setHorizontalGroup(
            basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(customCookieCheckBox)
                            .addComponent(customRefererCheckBox))
                        .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customRefererTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
                            .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(customCookieTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))))
                    .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                        .addComponent(agentsLabel)
                        .addGap(18, 18, 18)
                        .addComponent(agentsSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
                    .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                        .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ie6RadioButton)
                            .addComponent(ie7RadioButton)
                            .addComponent(firefoxRadioButton))
                        .addGap(18, 18, 18)
                        .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(googleBotRadioButton)
                            .addComponent(ie8RadioButton))
                        .addGap(2, 2, 2)
                        .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yahooRadioButton)
                            .addComponent(iPhoneRadioButton)))
                    .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(msnRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)))
                .addContainerGap())
        );
        basicHeadersPanelLayout.setVerticalGroup(
            basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customRefererCheckBox)
                    .addComponent(customRefererTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customCookieTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customCookieCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(agentsLabel)
                    .addComponent(agentsSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                        .addComponent(ie8RadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(basicHeadersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(googleBotRadioButton)
                            .addComponent(yahooRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msnRadioButton))
                    .addGroup(basicHeadersPanelLayout.createSequentialGroup()
                        .addComponent(firefoxRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ie6RadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ie7RadioButton))
                    .addComponent(iPhoneRadioButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout httpOptionsPanelLayout = new javax.swing.GroupLayout(httpOptionsPanel);
        httpOptionsPanel.setLayout(httpOptionsPanelLayout);
        httpOptionsPanelLayout.setHorizontalGroup(
            httpOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(httpOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(httpOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(basicHeadersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(httpOptionsPanelLayout.createSequentialGroup()
                        .addComponent(httpVerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(httpVersionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(httpUserLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(httpUserTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, httpOptionsPanelLayout.createSequentialGroup()
                        .addComponent(httpAuthCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(httpPassLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(httpPassPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)))
                .addContainerGap())
        );
        httpOptionsPanelLayout.setVerticalGroup(
            httpOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(httpOptionsPanelLayout.createSequentialGroup()
                .addGroup(httpOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(httpVerLabel)
                    .addComponent(httpVersionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(httpUserLabel)
                    .addComponent(httpUserTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(httpOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(httpPassPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(httpPassLabel)
                    .addComponent(httpAuthCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(basicHeadersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(httpOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addComponent(proxyTypesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proxySetsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(proxyTypesPanel, 0, 106, Short.MAX_VALUE)
                    .addComponent(proxySetsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(httpOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainTabbedPane.addTab(resourceMap.getString("optionsPanel.TabConstraints.tabTitle"), optionsPanel); // NOI18N

        customHeaderPanel.setName("customHeaderPanel"); // NOI18N
        customHeaderPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                customHeaderPanelComponentShown(evt);
            }
        });

        customHeadersScrollPane.setName("customHeadersScrollPane"); // NOI18N

        customHeaders.setColumns(20);
        customHeaders.setFont(resourceMap.getFont("customHeaders.font")); // NOI18N
        customHeaders.setLineWrap(true);
        customHeaders.setRows(5);
        customHeaders.setText(resourceMap.getString("customHeaders.text")); // NOI18N
        customHeaders.setName("customHeaders"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomHeadersCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), customHeaders, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        customHeadersScrollPane.setViewportView(customHeaders);

        useCustomHeadersCheckBox.setFont(resourceMap.getFont("useCustomHeadersCheckBox.font")); // NOI18N
        useCustomHeadersCheckBox.setText(resourceMap.getString("useCustomHeadersCheckBox.text")); // NOI18N
        useCustomHeadersCheckBox.setFocusable(false);
        useCustomHeadersCheckBox.setName("useCustomHeadersCheckBox"); // NOI18N

        javax.swing.GroupLayout customHeaderPanelLayout = new javax.swing.GroupLayout(customHeaderPanel);
        customHeaderPanel.setLayout(customHeaderPanelLayout);
        customHeaderPanelLayout.setHorizontalGroup(
            customHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customHeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customHeadersScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                    .addComponent(useCustomHeadersCheckBox))
                .addContainerGap())
        );
        customHeaderPanelLayout.setVerticalGroup(
            customHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customHeaderPanelLayout.createSequentialGroup()
                .addComponent(useCustomHeadersCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customHeadersScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab(resourceMap.getString("customHeaderPanel.TabConstraints.tabTitle"), customHeaderPanel); // NOI18N

        debugPanel.setName("debugPanel"); // NOI18N

        debugDataScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), resourceMap.getString("debugDataScrollPane.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, resourceMap.getFont("debugDataScrollPane.border.titleFont"))); // NOI18N
        debugDataScrollPane.setName("debugDataScrollPane"); // NOI18N

        debugDataPane.setBackground(resourceMap.getColor("debugDataPane.background")); // NOI18N
        debugDataPane.setName("debugDataPane"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mergeInfoCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), debugDataPane, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        debugDataScrollPane.setViewportView(debugDataPane);

        javax.swing.GroupLayout debugPanelLayout = new javax.swing.GroupLayout(debugPanel);
        debugPanel.setLayout(debugPanelLayout);
        debugPanelLayout.setHorizontalGroup(
            debugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
            .addGroup(debugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(debugDataScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE))
        );
        debugPanelLayout.setVerticalGroup(
            debugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE)
            .addGroup(debugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(debugDataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab(resourceMap.getString("debugPanel.TabConstraints.tabTitle"), debugPanel); // NOI18N

        upButton.setFont(resourceMap.getFont("upButton.font")); // NOI18N
        upButton.setIcon(resourceMap.getIcon("upButton.icon")); // NOI18N
        upButton.setText(resourceMap.getString("upButton.text")); // NOI18N
        upButton.setName("upButton"); // NOI18N
        upButton.setNextFocusableComponent(replayDataPane);
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });

        downButton.setFont(resourceMap.getFont("downButton.font")); // NOI18N
        downButton.setIcon(resourceMap.getIcon("downButton.icon")); // NOI18N
        downButton.setName("downButton"); // NOI18N
        downButton.setNextFocusableComponent(replayDataPane);
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });

        clearParams.setFont(resourceMap.getFont("clearParams.font")); // NOI18N
        clearParams.setText(resourceMap.getString("clearParams.text")); // NOI18N
        clearParams.setToolTipText(resourceMap.getString("clearParams.toolTipText")); // NOI18N
        clearParams.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        clearParams.setName("clearParams"); // NOI18N
        clearParams.setNextFocusableComponent(paramsComboBox);
        clearParams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearParamsActionPerformed(evt);
            }
        });

        clipboardButton.setIcon(resourceMap.getIcon("clipboardButton.icon")); // NOI18N
        clipboardButton.setToolTipText(resourceMap.getString("clipboardButton.toolTipText")); // NOI18N
        clipboardButton.setName("clipboardButton"); // NOI18N
        clipboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clipboardButtonActionPerformed(evt);
            }
        });

        paramsComboBox.setEditable(true);
        paramsComboBox.setFont(resourceMap.getFont("paramsComboBox.font")); // NOI18N
        paramsComboBox.setMaximumRowCount(10);
        paramsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        paramsComboBox.setName("paramsComboBox"); // NOI18N
        paramsComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                paramsComboBoxItemStateChanged(evt);
            }
        });
        paramsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paramsComboBoxActionPerformed(evt);
            }
        });

        paramsLabel.setFont(resourceMap.getFont("paramsLabel.font")); // NOI18N
        paramsLabel.setText(resourceMap.getString("paramsLabel.text")); // NOI18N
        paramsLabel.setFocusable(false);
        paramsLabel.setName("paramsLabel"); // NOI18N

        mainSeparator.setName("mainSeparator"); // NOI18N

        intelSaveCheckBox.setFont(resourceMap.getFont("intelSaveCheckBox.font")); // NOI18N
        intelSaveCheckBox.setText(resourceMap.getString("intelSaveCheckBox.text")); // NOI18N
        intelSaveCheckBox.setToolTipText(resourceMap.getString("intelSaveCheckBox.toolTipText")); // NOI18N
        intelSaveCheckBox.setName("intelSaveCheckBox"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toFileCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), intelSaveCheckBox, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        toFileTextField.setText(resourceMap.getString("toFileTextField.text")); // NOI18N
        toFileTextField.setName("toFileTextField"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, intelSaveCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), toFileTextField, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        toFileButton.setFont(resourceMap.getFont("toFileButton.font")); // NOI18N
        toFileButton.setText(resourceMap.getString("toFileButton.text")); // NOI18N
        toFileButton.setToolTipText(resourceMap.getString("toFileButton.toolTipText")); // NOI18N
        toFileButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toFileButton.setFocusable(false);
        toFileButton.setName("toFileButton"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, intelSaveCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), toFileButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        toFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toFileButtonActionPerformed(evt);
            }
        });

        toFileCheckBox.setFont(resourceMap.getFont("toFileCheckBox.font")); // NOI18N
        toFileCheckBox.setText(resourceMap.getString("toFileCheckBox.text")); // NOI18N
        toFileCheckBox.setToolTipText(resourceMap.getString("toFileCheckBox.toolTipText")); // NOI18N
        toFileCheckBox.setName("toFileCheckBox"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, intelSaveCheckBox, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), toFileCheckBox, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mainSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(urlLabel)
                                .addGap(10, 10, 10)
                                .addComponent(clearURL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(urlCombo, 0, 349, Short.MAX_VALUE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(paramsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearParams)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paramsComboBox, 0, 330, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addComponent(toFileCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toFileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toFileButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(intelSaveCheckBox)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(goButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(saveButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(paramsButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(methodPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(upButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(downButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(clipboardButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goButton)
                    .addComponent(urlCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urlLabel)
                    .addComponent(clearURL, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(paramsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(paramsLabel)
                        .addComponent(clearParams, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(paramsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(methodPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton)
                        .addGap(18, 18, 18)
                        .addComponent(upButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downButton)
                        .addGap(18, 18, 18)
                        .addComponent(clipboardButton)
                        .addGap(159, 159, 159))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(intelSaveCheckBox)
                            .addComponent(toFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toFileCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))))
        );

        mainMenuBar.setFont(resourceMap.getFont("mainMenuBar.font")); // NOI18N
        mainMenuBar.setName("mainMenuBar"); // NOI18N

        fileMenu.setAction(actionMap.get("fileChoose")); // NOI18N
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setFont(resourceMap.getFont("fileMenu.font")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        saveMenuItem.setAction(actionMap.get("fileChoose")); // NOI18N
        saveMenuItem.setFont(resourceMap.getFont("saveMenuItem.font")); // NOI18N
        saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        clearMenuItem1.setAction(actionMap.get("ClearDataPanel")); // NOI18N
        clearMenuItem1.setFont(resourceMap.getFont("clearMenuItem1.font")); // NOI18N
        clearMenuItem1.setText(resourceMap.getString("clearMenuItem1.text")); // NOI18N
        clearMenuItem1.setName("clearMenuItem1"); // NOI18N
        fileMenu.add(clearMenuItem1);

        filesSeparator1.setName("filesSeparator1"); // NOI18N
        fileMenu.add(filesSeparator1);

        saveTemplateItem.setFont(resourceMap.getFont("saveTemplateItem.font")); // NOI18N
        saveTemplateItem.setText(resourceMap.getString("saveTemplateItem.text")); // NOI18N
        saveTemplateItem.setName("saveTemplateItem"); // NOI18N
        saveTemplateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTemplateItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveTemplateItem);

        loadTemplateItem.setFont(resourceMap.getFont("saveTemplateItem.font")); // NOI18N
        loadTemplateItem.setText(resourceMap.getString("loadTemplateItem.text")); // NOI18N
        loadTemplateItem.setName("loadTemplateItem"); // NOI18N
        loadTemplateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTemplateItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadTemplateItem);

        filesSeparator2.setName("filesSeparator2"); // NOI18N
        fileMenu.add(filesSeparator2);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setFont(resourceMap.getFont("exitMenuItem.font")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        mainMenuBar.add(fileMenu);

        toolsMenu.setText(resourceMap.getString("toolsMenu.text")); // NOI18N
        toolsMenu.setFont(resourceMap.getFont("toolsMenu.font")); // NOI18N
        toolsMenu.setName("toolsMenu"); // NOI18N

        notesMenuItem.setFont(resourceMap.getFont("notesMenuItem.font")); // NOI18N
        notesMenuItem.setMnemonic('n');
        notesMenuItem.setText(resourceMap.getString("notesMenuItem.text")); // NOI18N
        notesMenuItem.setToolTipText(resourceMap.getString("notesMenuItem.toolTipText")); // NOI18N
        notesMenuItem.setName("notesMenuItem"); // NOI18N
        notesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notesMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(notesMenuItem);

        toolsSeparator.setName("toolsSeparator"); // NOI18N
        toolsMenu.add(toolsSeparator);

        cryptMenuItem.setFont(resourceMap.getFont("cryptMenuItem.font")); // NOI18N
        cryptMenuItem.setMnemonic('d');
        cryptMenuItem.setText(resourceMap.getString("cryptMenuItem.text")); // NOI18N
        cryptMenuItem.setToolTipText(resourceMap.getString("cryptMenuItem.toolTipText")); // NOI18N
        cryptMenuItem.setName("cryptMenuItem"); // NOI18N
        cryptMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cryptMenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(cryptMenuItem);

        md5MenuItem.setFont(resourceMap.getFont("md5MenuItem.font")); // NOI18N
        md5MenuItem.setMnemonic('m');
        md5MenuItem.setText(resourceMap.getString("md5MenuItem.text")); // NOI18N
        md5MenuItem.setToolTipText(resourceMap.getString("md5MenuItem.toolTipText")); // NOI18N
        md5MenuItem.setName("md5MenuItem"); // NOI18N
        md5MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                md5MenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(md5MenuItem);

        base64MenuItem.setFont(resourceMap.getFont("notesMenuItem.font")); // NOI18N
        base64MenuItem.setMnemonic('b');
        base64MenuItem.setText(resourceMap.getString("base64MenuItem.text")); // NOI18N
        base64MenuItem.setToolTipText(resourceMap.getString("base64MenuItem.toolTipText")); // NOI18N
        base64MenuItem.setName("base64MenuItem"); // NOI18N
        base64MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base64MenuItemActionPerformed(evt);
            }
        });
        toolsMenu.add(base64MenuItem);

        toolsSeparator2.setName("toolsSeparator2"); // NOI18N
        toolsMenu.add(toolsSeparator2);

        optionsMenu.setMnemonic('o');
        optionsMenu.setText(resourceMap.getString("optionsMenu.text")); // NOI18N
        optionsMenu.setFont(resourceMap.getFont("optionsMenu.font")); // NOI18N
        optionsMenu.setName("optionsMenu"); // NOI18N

        mergeInfoCheckBox.setFont(resourceMap.getFont("mergeInfoCheckBox.font")); // NOI18N
        mergeInfoCheckBox.setText(resourceMap.getString("mergeInfoCheckBox.text")); // NOI18N
        mergeInfoCheckBox.setToolTipText(resourceMap.getString("mergeInfoCheckBox.toolTipText")); // NOI18N
        mergeInfoCheckBox.setName("mergeInfoCheckBox"); // NOI18N
        optionsMenu.add(mergeInfoCheckBox);

        toolsMenu.add(optionsMenu);

        mainMenuBar.add(toolsMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setFont(resourceMap.getFont("helpMenu.font")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setFont(resourceMap.getFont("aboutMenuItem.font")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        mainMenuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 303, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(mainMenuBar);
        setStatusBar(statusPanel);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void urlComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlComboActionPerformed
        // TODO make Combo to work:
        if (evt.getActionCommand().equals("comboBoxEdited")) {
            //Object item = urlCombo.getSelectedItem();
            // 
            // urlCombo.hidePopup();
            SwingTools urlCheck = new SwingTools(this.getFrame());
            if (!urlCheck.checkAddComboItem(urlCombo)) {
                urlCombo.addItem(urlCombo.getSelectedItem());
            }
        }

    }//GEN-LAST:event_urlComboActionPerformed

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {
        /*
         * Copy information from ParamsDialog to
         * ParamsTextField
         */
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (RequestParamsDialog.isDataChanged()) {
                    paramsComboBox.setSelectedItem(RequestParamsDialog.getRequestParams());
                }
            }
        });

    }

    private void paramsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paramsButtonActionPerformed
        /*
         * Show Params dialog
         * and set listener in hidden event
         */
        if (requestParams == null) {
            // Initialize Options dialog
            JFrame mainFrame = HttpAnalyzerApp.getApplication().getMainFrame();
            requestParams = new RequestParamsDialog(mainFrame, true);

            SwingUtilities.invokeLater(new Runnable() {
                //SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    requestParams.setVisible(true);
                }
            });
            requestParams.addComponentListener(new java.awt.event.ComponentAdapter() {

                @Override
                public void componentHidden(java.awt.event.ComponentEvent evt) {
                    formComponentHidden(evt);
                }
            });
        } else {
            requestParams.setVisible(true);
        }
    }//GEN-LAST:event_paramsButtonActionPerformed

    private void clearURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearURLActionPerformed
        /* 
         * Clear URL Combo
         */
        urlCombo.setSelectedItem("");
        clearURL.transferFocus();
    }//GEN-LAST:event_clearURLActionPerformed

    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
        /*
         * Move caret to top position
         */
        upButton.transferFocus();
        replayDataPane.setCaretPosition(0);
    }//GEN-LAST:event_upButtonActionPerformed

    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
        /*
         * Move caret to bottom position
         */
        downButton.transferFocus();
        replayDataPane.setCaretPosition(replayDataPane.getText().length());
    }//GEN-LAST:event_downButtonActionPerformed

    private void notesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notesMenuItemActionPerformed
        /*
         * Show Note Dialog
         */
        if (notesDialog == null) {
            // Initialize Notes dialog
            JFrame mainFrame = HttpAnalyzerApp.getApplication().getMainFrame();
            notesDialog = new NotesDialog(mainFrame, false);

            SwingUtilities.invokeLater(new Runnable() {
                //SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    notesDialog.setVisible(true);
                }
            });
        } else {
            notesDialog.setVisible(true);
        }
    }//GEN-LAST:event_notesMenuItemActionPerformed

    private void clearParamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearParamsActionPerformed
        /*
         * Clear Params (Text field)
         */
        paramsComboBox.setSelectedItem("");
        clearParams.transferFocus();
    }//GEN-LAST:event_clearParamsActionPerformed

    private void customHeaderPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_customHeaderPanelComponentShown
        /*
         * Write headers options to CustomHeader (TextArea)
         */
        initRequestHeaders();
        customHeaders.setText(HeaderSettings.getHeaders());
    }//GEN-LAST:event_customHeaderPanelComponentShown

    private void base64MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base64MenuItemActionPerformed
        /*
         * Show Base64 Dialog
         */
        if (base64Dialog == null) {
            JFrame mainFrame = HttpAnalyzerApp.getApplication().getMainFrame();
            base64Dialog = new Base64Transformer(mainFrame, false);

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    base64Dialog.setVisible(true);
                }
            });
        } else {
            base64Dialog.setVisible(true);
        }

    }//GEN-LAST:event_base64MenuItemActionPerformed

    private void md5MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_md5MenuItemActionPerformed
        /*
         * Show MD5 Dialog
         */
        if (md5Dialog == null) {
            JFrame mainFrame = HttpAnalyzerApp.getApplication().getMainFrame();
            md5Dialog = new MD5Dialog(mainFrame, false);

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    md5Dialog.setVisible(true);
                }
            });
        } else {
            md5Dialog.setVisible(true);
        }
    }//GEN-LAST:event_md5MenuItemActionPerformed

    private void cryptMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cryptMenuItemActionPerformed
        /*
         * Show Crypt/DES Dialog
         */
        if (desDialog == null) {
            // Initialize Options dialog
            JFrame mainFrame = HttpAnalyzerApp.getApplication().getMainFrame();
            desDialog = new CryptDesDialog(mainFrame, false);

            SwingUtilities.invokeLater(new Runnable() {
                //SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    desDialog.setVisible(true);
                }
            });
        } else {
            desDialog.setVisible(true);
        }
    }//GEN-LAST:event_cryptMenuItemActionPerformed

    private void clipboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clipboardButtonActionPerformed
        String seletedText = replayDataPane.getSelectedText();
        StringSelection stringSelection = new StringSelection(seletedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
    }//GEN-LAST:event_clipboardButtonActionPerformed

    private void paramsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paramsComboBoxActionPerformed
        if (evt.getActionCommand().equals("comboBoxEdited")) {
            // urlCombo.hidePopup();
            SwingTools urlCheck = new SwingTools(this.getFrame());
            if (!urlCheck.checkAddComboItem(paramsComboBox)) {
                paramsComboBox.addItem(paramsComboBox.getSelectedItem());
            }
        }
    }//GEN-LAST:event_paramsComboBoxActionPerformed

    private void paramsComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_paramsComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            SwingTools urlCheck = new SwingTools(this.getFrame());
            if (!urlCheck.checkAddComboItem(paramsComboBox)) {
                paramsComboBox.addItem(paramsComboBox.getSelectedItem());
            }
        }
    }//GEN-LAST:event_paramsComboBoxItemStateChanged

    private void urlComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_urlComboItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            SwingTools urlCheck = new SwingTools(this.getFrame());
            if (!urlCheck.checkAddComboItem(urlCombo)) {
                urlCombo.addItem(urlCombo.getSelectedItem());
            }
        }
    }//GEN-LAST:event_urlComboItemStateChanged

    private void toFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toFileButtonActionPerformed
        HttpFileUtils yourChoice = new HttpFileUtils();
        yourChoice.fillToFileField(this, this.getFrame());
    }//GEN-LAST:event_toFileButtonActionPerformed

    private void loadTemplateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTemplateItemActionPerformed
        HttpPreference myPrefs = new HttpPreference();
        if (notesDialog != null) {
            notesDialog.putNotes(myPrefs.loadPreference(this, this.getFrame()));
        } else {
            JFrame mainFrame = HttpAnalyzerApp.getApplication().getMainFrame();
            notesDialog = new NotesDialog(mainFrame, false);
            notesDialog.putNotes(myPrefs.loadPreference(this, this.getFrame()));
            /*SwingUtilities.invokeLater(new Runnable() {
            //SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
            notesDialog.setVisible(false);
            }
            });*/
        }

    }//GEN-LAST:event_loadTemplateItemActionPerformed

    private void saveTemplateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTemplateItemActionPerformed
        initRequestHeaders();
        customHeaders.setText(HeaderSettings.getHeaders());
        HttpPreference myPrefs = new HttpPreference();
        if (notesDialog != null) {
            myPrefs.savePreference(this, this.getFrame(), notesDialog.getNotes());
        } else {
            myPrefs.savePreference(this, this.getFrame(), null);
        }
    }//GEN-LAST:event_saveTemplateItemActionPerformed

    private void initRequestHeaders() {
        if (customRefererCheckBox.isSelected()) {
            referer = customRefererTextField.getText();
        } else {
            try {
                referer = URIUtils.createURI("http", targetHost, targetPort, targetPath, null, null).toString();
            } catch (URISyntaxException ex) {
                Logger.getLogger(HttpAnalyzerView.class.getName()).log(Level.SEVERE, null, ex);
                new SwingTools(this.getFrame()).showErrorDialog("Referer incorrect", ex.getLocalizedMessage());
            }
        }
        if (customCookieCheckBox.isSelected()) {
            HeaderSettings.setCookie(customCookieTextField.getText());
        } else {
            HeaderSettings.clearCookie();
        }
        if (useCustomHeadersCheckBox.isSelected()) {
            HeaderSettings.initHeaders(customHeaders.getText());
        } else if (firefoxRadioButton.isSelected()) {
            HeaderSettings.initHeaders(HeaderSettings.FIREFOX, referer);
        } else if (ie6RadioButton.isSelected()) {
            HeaderSettings.initHeaders(HeaderSettings.IE6, referer);
        } else if (ie7RadioButton.isSelected()) {
            HeaderSettings.initHeaders(HeaderSettings.IE7, referer);
        } else if (ie8RadioButton.isSelected()) {
            HeaderSettings.initHeaders(HeaderSettings.IE8, referer);
        } else if (googleBotRadioButton.isSelected()) {
            HeaderSettings.initHeaders(HeaderSettings.GOOGLEBOT, referer);
        } else if (msnRadioButton.isSelected()) {
            HeaderSettings.initHeaders(HeaderSettings.MSNBOT, referer);
        } else if (iPhoneRadioButton.isSelected()) {
            HeaderSettings.initHeaders(HeaderSettings.IPHONE, referer);
        } else if (yahooRadioButton.isSelected()) {
            HeaderSettings.initHeaders(HeaderSettings.YAHOOBOT, referer);
        }
    }

    private String initUrl() {
        if (urlCombo.getSelectedItem() != null) {
            String url = urlCombo.getSelectedItem().toString();
            if ((url != null) && (!url.isEmpty())) {
                String[] partsUrl = new ParamsUrlTools().splitUrl(url, this.getFrame());
                targetHost = partsUrl[0];
                targetPort = Integer.decode(partsUrl[1]);
                targetPath = partsUrl[2];
                return partsUrl[3];
            }
        }
        return null;
    }

    public class ProxyAuth extends Authenticator {

        private PasswordAuthentication auth;

        private ProxyAuth(String user, String password) {
            auth = new PasswordAuthentication(user, password == null ? new char[]{} : password.toCharArray());
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return auth;
        }
    }

    /**
     * Make connection use proxy server
     * @param HttpClient
     */
    void setProxy(DefaultHttpClient myClient) {
        String textField = proxyField.getText();
        int indexStr = textField.indexOf(":");
        proxyHost = textField.substring(0, indexStr);
        proxyPort = Integer.decode(textField.substring(indexStr + 1));
        if (proxyHttpRadioButton.isSelected()) {
            System.setProperty("socksProxySet", "false");
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            myClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            //Proxy Auth
            if (proxyAuthCheckBox.isSelected()) {
                String username = proxyUserTextField.getText();
                String password = String.valueOf(proxyPassPasswordField.getPassword());
                UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
                myClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort), creds);
            }
        } else if (proxySocksRadioButton.isSelected()) {
            /*System.out.println("User =" + System.getProperty("java.net.socks.username"));
            System.out.println("Pass =" + System.getProperty("java.net.socks.password"));*/
            System.setProperty("socksProxySet", "true");
            System.setProperty("socksProxyHost", proxyHost);
            System.setProperty("socksProxyPort", Integer.toString(proxyPort));
            String username = proxyUserTextField.getText();
            String password = String.valueOf(proxyPassPasswordField.getPassword());
            Authenticator.setDefault(new ProxyAuth(username, password));
            /*System.getProperties().put("socksProxyHost", proxyHost);
            System.out.println("User ="+System.getProperty("java.net.socks.username"));
            System.out.println("Pass ="+System.getProperty("java.net.socks.password"));*/
        }
    }

    void showIntelSaveDialog(String message) {
        /*
         * Show intelligence save dialog
         */
        if (intelSaveDialog == null) {
            // Initialize Options dialog
            JFrame mainFrame = HttpAnalyzerApp.getApplication().getMainFrame();
            intelSaveDialog = new IntelSaveDialog(mainFrame, true);
            IntelSaveDialog.setMessage(message);
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    @Override
                    public void run() {
                        intelSaveDialog.setVisible(true);
                    }
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(HttpAnalyzerView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(HttpAnalyzerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            IntelSaveDialog.setMessage(message);
            intelSaveDialog.setVisible(true);
        }
    }

    @Action
    public Task RequestRun() {
        return new RequestRunTask(getApplication());
    }

    private class RequestRunTask extends org.jdesktop.application.Task<Object, Void> {

        RequestRunTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to RequestRunTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            // Check URL
            String workURL = initUrl();
            if (workURL == null) {
                return null;
            }

            setProgress(0, 0, 100);
            // Prepare HTTP method
            HttpParams httpParams = new BasicHttpParams();
            InitBasicParams myInitParams = new InitBasicParams();
            myInitParams.initHttpParams(httpParams, HttpAnalyzerView.this);
            initRequestHeaders();
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
            setProxy(httpClient);
            SwingTools swingTool = new SwingTools(HttpAnalyzerView.this, HttpAnalyzerApp.getApplication().getMainFrame());
            swingTool.setColorTextDoc(new Date() + "\n", swingTool.DATE_COLOR, true);

            if (!mergeInfoCheckBox.isSelected()) {
                swingTool.setColorTextDoc(new Date() + "\n", swingTool.DATE_COLOR, false);
            }
            setMessage("Connect to " + targetHost);
            goButton.setEnabled(false);
            // HTTP Auth
            if (httpAuthCheckBox.isSelected()) {
                String username = httpUserTextField.getText();
                String password = String.valueOf(httpPassPasswordField.getPassword());
                UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
                httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
            }

            try {
                if (getRadio.isSelected()) {
                    /*
                     * GET Request section
                     */
                    List<NameValuePair> qparams = null;
                    URI uri = null;
                    if (!paramsComboBox.getSelectedItem().toString().isEmpty()) {
                        qparams = new ParamsUrlTools().getPairParams(paramsComboBox.getSelectedItem().toString());
                        //qparams = RequestParamsDialog.getRequestParams();
                        try {
                            uri = URIUtils.createURI("http", targetHost, targetPort, targetPath, URLEncodedUtils.format(qparams, "UTF-8"), null);
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(HttpAnalyzerView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            uri = URIUtils.createURI("http", targetHost, targetPort, targetPath, null, null);
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(HttpAnalyzerView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    // HttpGet httpGet = new HttpGet(workURL);
                    HttpGet httpGet = new HttpGet(uri);
                    if (useCustomHeadersCheckBox.isSelected()) {
                        System.out.println("Headers:\n");
                    }
                    httpGet.setHeaders(HeaderSettings.setHeaders());
                    // Show request
                    swingTool.spliterOut();
                    String getRequest = httpGet.getMethod() + " " + httpGet.getURI() + "\n";
                    swingTool.setColorTextDoc(getRequest, swingTool.REQUEST_COLOR, true);

                    if (!mergeInfoCheckBox.isSelected()) {
                        swingTool.setColorTextDoc(getRequest, swingTool.REQUEST_COLOR, false);
                    }
                    swingTool.setColorTextDoc(HeaderSettings.showHeaders(httpGet),
                            swingTool.REQUEST_COLOR, false);

                    // Response header
                    setMessage("GET request to " + targetHost);
                    httpResponse = httpClient.execute(httpGet);
                    setProgress(30, 0, 100);
                } else if (postRadio.isSelected()) {
                    /*
                     * POST Request section
                     */
                    HttpPost httpPost = new HttpPost(workURL);
                    httpPost.setHeaders(HeaderSettings.setHeaders());
                    if (!paramsComboBox.getSelectedItem().toString().isEmpty()) {
                        ByteArrayEntity postEntityParams = null;
                        byte[] params = paramsComboBox.getSelectedItem().toString().getBytes();
                        postEntityParams = new ByteArrayEntity(params);
                        postEntityParams.setContentType("application/x-www-form-urlencoded");
                        postEntityParams.setChunked(false);
                        httpPost.setEntity(postEntityParams);
                    }
                    // Show request
                    swingTool.spliterOut();
                    String postRequest = httpPost.getMethod() + " " + httpPost.getURI() + "\n";
                    swingTool.setColorTextDoc(postRequest,
                            swingTool.REQUEST_COLOR, true);
                    if (!mergeInfoCheckBox.isSelected()) {
                        swingTool.setColorTextDoc(postRequest,
                                swingTool.REQUEST_COLOR, false);
                    }
                    swingTool.setColorTextDoc(HeaderSettings.showHeaders(httpPost),
                            swingTool.REQUEST_COLOR, false);
                    //spliterOut();
                    //spliterOut();
                    setMessage("POST request to " + targetHost);
                    httpResponse = httpClient.execute(httpPost);
                    setProgress(30, 0, 100);
                } else if (headRadio.isSelected()) {
                    /*
                     * HEAD Request section
                     */
                    HttpHead httpHead = new HttpHead(workURL);
                    httpHead.setHeaders(HeaderSettings.setHeaders());
                    // Show request
                    swingTool.spliterOut();
                    String headRequest = httpHead.getMethod() + " " + httpHead.getURI() + "\n";
                    swingTool.setColorTextDoc(headRequest, swingTool.REQUEST_COLOR, true);
                    if (!mergeInfoCheckBox.isSelected()) {
                        swingTool.setColorTextDoc(headRequest, swingTool.REQUEST_COLOR, false);
                    }
                    //spliterOut();
                    //spliterOut();
                    swingTool.setColorTextDoc(HeaderSettings.showHeaders(httpHead),
                            swingTool.REQUEST_COLOR, false);

                    //spliterOut();
                    setMessage("HEAD request to " + targetHost);
                    httpResponse = httpClient.execute(httpHead);
                    setProgress(30, 0, 100);
                }
                setMessage("Wait answer ... ");
                swingTool.setColorTextDoc(httpResponse.getStatusLine().toString() + "\n",
                        swingTool.RESPONSE_COLOR, true);
                swingTool.setColorTextDoc(HeaderSettings.showHeaders(httpResponse.getAllHeaders()) + "\n",
                        swingTool.REQUEST_COLOR, true);
                HttpEntity entity = httpResponse.getEntity();
                swingTool.spliterOut();
                setProgress(50, 0, 100);
                // Response entity
                swingTool.spliterOut();
                if (entity != null) {
                    if (intelSaveCheckBox.isSelected()) {
                        // Intelligence mode is ON
                        setProgress(80, 0, 100);
                        String contentType = entity.getContentType().getValue();
                        if (!contentType.startsWith("text/htm")) {
                            // Binary content
                            long sizeEntity = entity.getContentLength();
                            String message = contentType + ". Size is " + sizeEntity
                                    + " bytes.";
                            showIntelSaveDialog(message);
                            int status = IntelSaveDialog.getStatus();
                            if (status == 2) {
                                String filename = IntelSaveDialog.getFilename();
                                HttpFileUtils myUtils = new HttpFileUtils();
                                long size = myUtils.saveEntity(entity, filename);
                                swingTool.setColorTextDoc("Save " + size + " bytes into "
                                        + filename + "\n", swingTool.DATE_COLOR, true);
                            } else if (status == 1) {
                                swingTool.showEntity(entity);
                            } else {
                                swingTool.setColorTextDoc("Entity is ignored\n",
                                        swingTool.DATE_COLOR, true);
                            }
                            System.out.println("Status =" + IntelSaveDialog.getStatus());
                        } else {
                            swingTool.showEntity(entity);
                        }
                    } else if ((toFileCheckBox.isSelected())
                            && (!toFileTextField.getText().isEmpty())) {
                        setProgress(80, 0, 100);
                        String filename = toFileTextField.getText();
                        HttpFileUtils myUtils = new HttpFileUtils();
                        System.out.println("Base Entity =" + entity.getContentLength());
                        long size = myUtils.saveEntity(entity, filename);
                        swingTool.setColorTextDoc("Save " + size + " bytes into "
                                + filename + "\n", swingTool.DATE_COLOR, true);
                    } else {
                        setProgress(80, 0, 100);
                        swingTool.showEntity(entity);
                    }
                    swingTool.spliterOut();
                } else {
                    swingTool.setColorTextDoc("Entity is NULL\n",
                            swingTool.DATE_COLOR, true);
                }
                setProgress(100, 0, 100);
                replayDataPane.setCaretPosition(replayDataPane.getText().length());
                setMessage("Done.");
            } catch (IOException ex) {
                Logger.getLogger(HttpAnalyzerView.class.getName()).log(Level.SEVERE, null, ex);
                new SwingTools(HttpAnalyzerApp.getApplication().getMainFrame()).showErrorDialog("IO Error", ex.toString());
                setMessage("<html><font color=#CC0000>Error!</font></html>");
            } finally {
                goButton.setEnabled(true);
                // When HttpClient instance is no longer needed,
                // shut down the connection manager to ensure
                // immediate deallocation of all system resources
                httpClient.getConnectionManager().shutdown();
            }
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public Task ClearDataPanel() {
        return new ClearDataPanelTask(getApplication());
    }

    private class ClearDataPanelTask extends org.jdesktop.application.Task<Object, Void> {

        ClearDataPanelTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to ClearDataPanelTask fields, here.
            super(app);
            replayDataPane.setText(null);
            replayDataPane.requestFocus();
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public void fileChoose() {
        HttpFileUtils infoSaver = new HttpFileUtils();
        infoSaver.saveSessionInfo(this, this.getFrame());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel agentsLabel;
    private javax.swing.JSeparator agentsSeparator;
    private javax.swing.JMenuItem base64MenuItem;
    private javax.swing.JPanel basicHeadersPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JMenuItem clearMenuItem1;
    private javax.swing.JButton clearParams;
    private javax.swing.JButton clearURL;
    private javax.swing.JButton clipboardButton;
    private javax.swing.JMenuItem cryptMenuItem;
    public javax.swing.JCheckBox customCookieCheckBox;
    public javax.swing.JTextField customCookieTextField;
    private javax.swing.JPanel customHeaderPanel;
    public javax.swing.JTextArea customHeaders;
    private javax.swing.JScrollPane customHeadersScrollPane;
    public javax.swing.JCheckBox customRefererCheckBox;
    public javax.swing.JTextField customRefererTextField;
    public javax.swing.JTextPane debugDataPane;
    private javax.swing.JScrollPane debugDataScrollPane;
    private javax.swing.JPanel debugPanel;
    private javax.swing.JButton downButton;
    private javax.swing.JPopupMenu.Separator filesSeparator1;
    private javax.swing.JPopupMenu.Separator filesSeparator2;
    public javax.swing.JRadioButton firefoxRadioButton;
    public javax.swing.JRadioButton getRadio;
    private javax.swing.JButton goButton;
    public javax.swing.JRadioButton googleBotRadioButton;
    public javax.swing.JRadioButton headRadio;
    public javax.swing.JCheckBox httpAuthCheckBox;
    private javax.swing.JPanel httpOptionsPanel;
    private javax.swing.JLabel httpPassLabel;
    public javax.swing.JPasswordField httpPassPasswordField;
    private javax.swing.JLabel httpUserLabel;
    public javax.swing.JTextField httpUserTextField;
    private javax.swing.JLabel httpVerLabel;
    public javax.swing.JComboBox httpVersionComboBox;
    public javax.swing.JRadioButton iPhoneRadioButton;
    public javax.swing.JRadioButton ie6RadioButton;
    public javax.swing.JRadioButton ie7RadioButton;
    public javax.swing.JRadioButton ie8RadioButton;
    public javax.swing.JCheckBox intelSaveCheckBox;
    private javax.swing.JMenuItem loadTemplateItem;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSeparator mainSeparator;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JMenuItem md5MenuItem;
    public javax.swing.JCheckBoxMenuItem mergeInfoCheckBox;
    private javax.swing.ButtonGroup methodGroups;
    private javax.swing.JPanel methodPanel;
    public javax.swing.JRadioButton msnRadioButton;
    private javax.swing.JMenuItem notesMenuItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JButton paramsButton;
    public javax.swing.JComboBox paramsComboBox;
    private javax.swing.JLabel paramsLabel;
    public javax.swing.JRadioButton postRadio;
    private javax.swing.JProgressBar progressBar;
    public javax.swing.JCheckBox proxyAuthCheckBox;
    public javax.swing.JTextField proxyField;
    private javax.swing.JLabel proxyHostLabel;
    public javax.swing.JRadioButton proxyHttpRadioButton;
    public javax.swing.JRadioButton proxyNoneRadioButton;
    private javax.swing.JLabel proxyPassLabel;
    public javax.swing.JPasswordField proxyPassPasswordField;
    private javax.swing.JPanel proxySetsPanel;
    public javax.swing.JRadioButton proxySocksRadioButton;
    private javax.swing.ButtonGroup proxyTypesGroup;
    private javax.swing.JPanel proxyTypesPanel;
    private javax.swing.JLabel proxyUserLabel;
    public javax.swing.JTextField proxyUserTextField;
    public javax.swing.JTextPane replayDataPane;
    private javax.swing.JScrollPane replayDataScrollPane;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem saveTemplateItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton toFileButton;
    public javax.swing.JCheckBox toFileCheckBox;
    public javax.swing.JTextField toFileTextField;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JPopupMenu.Separator toolsSeparator;
    private javax.swing.JPopupMenu.Separator toolsSeparator2;
    private javax.swing.JButton upButton;
    public javax.swing.JComboBox urlCombo;
    private javax.swing.JLabel urlLabel;
    public javax.swing.JCheckBox useCustomHeadersCheckBox;
    private javax.swing.ButtonGroup userAgentsGroup;
    public javax.swing.JRadioButton yahooRadioButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private RequestParamsDialog requestParams;
    private NotesDialog notesDialog;
    private Base64Transformer base64Dialog;
    private MD5Dialog md5Dialog;
    private CryptDesDialog desDialog;
    private IntelSaveDialog intelSaveDialog;
}

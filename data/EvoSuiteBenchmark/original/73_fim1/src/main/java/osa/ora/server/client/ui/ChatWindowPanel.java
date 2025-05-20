/*
 * ChatWindowPanel.java
 *
 * Created on October 29, 2009, 7:33 PM
 */
package osa.ora.server.client.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Calendar;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import osa.ora.server.beans.BinaryMessage;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.IConstant;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.TextMessage;
import osa.ora.server.beans.User;
import osa.ora.server.client.*;
import osa.ora.server.client.threads.SendChatThread;
import osa.ora.server.client.threads.SendFileThread;
import osa.ora.server.client.ui.utils.FileTransferHandler;

/**
 *
 * @author  ooransa
 */
public class ChatWindowPanel extends ParentPanel {

    ChatClientApp chatApp;
    private User user;
    private Group group;
    private Room room;
    private int chat_type;
    private JPopupMenu editPopupMenu;
    private JMenuItem copyMenuItem;
    private JMenuItem cutMenuItem;
    private JMenuItem pasteMenuItem;
    private JMenuItem selectAllMenuItem;
    private JMenuItem sendMenuItem;
    private JPopupMenu savePopupMenu;
    private JMenuItem copyFormattedMenuItem;
    private JMenuItem saveFormattedMenuItem;
    private JMenuItem saveUnFormattedMenuItem;
    private JMenuItem clearChatMenuItem;
    private JMenuItem clearChatMenuItem1;
    private HTMLEditorKit editorHTMLKit;
    Font sourceFont;
    Font destFont;
    Color sourceColor;
    Color destColor;
    String sourceFontStyleOpen;
    String destFontStyleOpen;
    String sourceFontStyleClose;
    String destFontStyleClose;

    public ChatWindowPanel(ChatClientApp chatApp, Group group) {
        this.group = group;
        this.chatApp = chatApp;
        this.chat_type = IConstant.GROUP_CHAT;
        initComponents();
        initSettings(true);
    }

    public ChatWindowPanel(ChatClientApp chatApp, User user) {
        this.user = user;
        this.chatApp = chatApp;
        this.chat_type = IConstant.USER_CHAT;
        initComponents();
        initSettings(true);
    }

    public ChatWindowPanel(ChatClientApp chatApp, Room room) {
        this.room = room;
        this.chatApp = chatApp;
        this.chat_type = IConstant.ROOM_CHAT;
        initComponents();
        initSettings(true);
    }
    private void initSettings(boolean firstTime){
        sourceFont=new Font(chatApp.getClientSettingBean().getSourceFontName(),Integer.parseInt(chatApp.getClientSettingBean().getSourceFontStyle()),Integer.parseInt(chatApp.getClientSettingBean().getSourceFontSize()));
        destFont=new Font(chatApp.getClientSettingBean().getDestFontName(),Integer.parseInt(chatApp.getClientSettingBean().getDestFontStyle()),Integer.parseInt(chatApp.getClientSettingBean().getDestFontSize()));
        sourceColor=new Color(Integer.parseInt(chatApp.getClientSettingBean().getSourceFontColor()));
        destColor=new Color(Integer.parseInt(chatApp.getClientSettingBean().getDestFontColor()));
        jEditorPane2.setFont(sourceFont);
        jEditorPane1.setFont(destFont);
        jEditorPane2.setForeground(sourceColor);
        jEditorPane1.setForeground(destColor);
        if(sourceFont.getStyle()==3){
            sourceFontStyleOpen="<B><I>";
            sourceFontStyleClose="</B></I>";
        }else if(sourceFont.getStyle()==2){
            sourceFontStyleOpen="<I>";
            sourceFontStyleClose="</I>";
        }else if(sourceFont.getStyle()==1){
            sourceFontStyleOpen="<B>";
            sourceFontStyleClose="</B>";
        }else{
            sourceFontStyleOpen="";
            sourceFontStyleClose="";
        }
        if(destFont.getStyle()==3){
            destFontStyleOpen="<B><I>";
            destFontStyleClose="</B></I>";
        }else if(destFont.getStyle()==2){
            destFontStyleOpen="<I>";
            destFontStyleClose="</I>";
        }else if(destFont.getStyle()==1){
            destFontStyleOpen="<B>";
            destFontStyleClose="</B>";
        }else{
            destFontStyleOpen="";
            destFontStyleClose="";
        }
        if(firstTime){
            Calendar cal=Calendar.getInstance();
            int month=cal.get(Calendar.MONTH)+1;
            String today="Chat window open at "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+" "+cal.get(Calendar.DATE)+"/"+month+"/"+cal.get(Calendar.YEAR);
            addTextChat(today);
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        sendEmotionButton = new javax.swing.JButton();
        setFontColorButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        sendFileButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane2 = new javax.swing.JEditorPane();
        jEditorPane2.setTransferHandler(new FileTransferHandler(this));
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setMaximumSize(new java.awt.Dimension(100, 35));
        jPanel3.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        sendEmotionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/smile.png"))); // NOI18N
        sendEmotionButton.setToolTipText("Add Emoticons");
        sendEmotionButton.setBorderPainted(false);
        sendEmotionButton.setContentAreaFilled(false);
        sendEmotionButton.setFocusPainted(false);
        sendEmotionButton.setPreferredSize(new java.awt.Dimension(25, 25));
        sendEmotionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendEmotionButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 2, 0);
        jPanel3.add(sendEmotionButton, gridBagConstraints);

        setFontColorButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/font.jpg"))); // NOI18N
        setFontColorButton.setToolTipText("Change Font and Color");
        setFontColorButton.setBorderPainted(false);
        setFontColorButton.setContentAreaFilled(false);
        setFontColorButton.setFocusPainted(false);
        setFontColorButton.setPreferredSize(new java.awt.Dimension(25, 25));
        setFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setFontColorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 2, 0);
        jPanel3.add(setFontColorButton, gridBagConstraints);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.jpg"))); // NOI18N
        saveButton.setToolTipText("Save Chat");
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new java.awt.Dimension(25, 25));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel3.add(saveButton, new java.awt.GridBagConstraints());

        sendFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/send.jpg"))); // NOI18N
        sendFileButton.setToolTipText("Send File");
        sendFileButton.setBorderPainted(false);
        sendFileButton.setContentAreaFilled(false);
        sendFileButton.setFocusPainted(false);
        sendFileButton.setPreferredSize(new java.awt.Dimension(25, 25));
        sendFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 2, 93);
        jPanel3.add(sendFileButton, gridBagConstraints);

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jEditorPane2.setDragEnabled(true);
        //jEditorPane2.setLineWrap(true);
        jEditorPane2.setMaximumSize(new java.awt.Dimension(106, 50));
        jEditorPane2.setMinimumSize(new java.awt.Dimension(106, 50));
        jEditorPane2.setPreferredSize(new java.awt.Dimension(106, 50));
        jEditorPane2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jEditorPane2KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jEditorPane2);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jEditorPane1.setEditable(false);
        editorHTMLKit=new HTMLEditorKit();
        jEditorPane1.setEditorKit(editorHTMLKit);
        jEditorPane1.setDoubleBuffered(true);
        jEditorPane1.setMinimumSize(new java.awt.Dimension(106, 250));
        jEditorPane1.setPreferredSize(new java.awt.Dimension(106, 250));
        jEditorPane1.setRequestFocusEnabled(false);
        jEditorPane1.addHyperlinkListener(new ActiveLink());
        jScrollPane1.setViewportView(jEditorPane1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
        copyMenuItem = new JMenuItem("Copy Selected",new javax.swing.ImageIcon(getClass().getResource("/images/copy.png")));
        copyMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selection = getJEditorPane2().getSelectedText();
                    StringSelection data = new StringSelection(selection);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
                } catch (Exception ex) {
                    System.out.println("Copy error!");
                }
            }
        });
        cutMenuItem = new JMenuItem("Cut Selected",new javax.swing.ImageIcon(getClass().getResource("/images/cut.png")));
        cutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selection = getJEditorPane2().getSelectedText();
                    StringSelection data = new StringSelection(selection);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
                    cutText();
                } catch (Exception ex) {
                    System.out.println("Cut error!"+ex.getMessage());
                }
            }
        });
        pasteMenuItem = new JMenuItem("Paste",new javax.swing.ImageIcon(getClass().getResource("/images/paste.png")));
        pasteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					Transferable clipData=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(Toolkit.getDefaultToolkit().getSystemClipboard());
					if(clipData!=null){
					    	if(clipData.isDataFlavorSupported(DataFlavor.stringFlavor)){
			      				String s=(String)clipData.getTransferData(DataFlavor.stringFlavor);
					      		getJEditorPane2().replaceSelection(s);
					      	}else{
					      		System.out.println("No thing to Paste!");
							}
					}
                } catch (Exception ex) {
                    System.out.println("Paste error!");
                }
            }
        });
        selectAllMenuItem = new JMenuItem("Select All",new javax.swing.ImageIcon(getClass().getResource("/images/selectAll.png")));
        selectAllMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    getJEditorPane2().selectAll();
                } catch (Exception ex) {
                    System.out.println("select All error!");
                }
            }
        });
        sendMenuItem = new JMenuItem("Send",new javax.swing.ImageIcon(getClass().getResource("/images/submit.png")));
        sendMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendChat();
            }
        });
        clearChatMenuItem1=new JMenuItem("Clear",new javax.swing.ImageIcon(getClass().getResource("/images/trash.png")));
        clearChatMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				jEditorPane2.setText("");
            }
        });
        editPopupMenu=new JPopupMenu();
        editPopupMenu.add(copyMenuItem);
        editPopupMenu.add(cutMenuItem);
        editPopupMenu.add(pasteMenuItem);
        editPopupMenu.add(clearChatMenuItem1);
        editPopupMenu.add(selectAllMenuItem);
        editPopupMenu.addSeparator();
        editPopupMenu.add(sendMenuItem);
        jEditorPane2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    editPopupMenu.show(jEditorPane2, e.getX(), e.getY());
                }
            }
        });
        savePopupMenu=new JPopupMenu();
        saveFormattedMenuItem=new JMenuItem("Save Chat Formated Text",new javax.swing.ImageIcon(getClass().getResource("/images/save.png")));
        saveUnFormattedMenuItem=new JMenuItem("Save Chat Text",new javax.swing.ImageIcon(getClass().getResource("/images/save.png")));
        saveFormattedMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSaveChatDialog(true);
            }
        });
        saveUnFormattedMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSaveChatDialog(false);
            }
        });
        copyFormattedMenuItem=new JMenuItem("Copy Chat Text",new javax.swing.ImageIcon(getClass().getResource("/images/copy.png")));
        copyFormattedMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selection = jEditorPane1.getDocument().getText(0,jEditorPane1.getDocument().getLength());
                    StringSelection data = new StringSelection(selection);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
                } catch (Exception ex) {
                    System.out.println("Copy error!");
                }
            }
        });
        clearChatMenuItem=new JMenuItem("Clear",new javax.swing.ImageIcon(getClass().getResource("/images/trash.png")));
        clearChatMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				jEditorPane1.setText("");
            }
        });
        savePopupMenu.add(copyFormattedMenuItem);
        savePopupMenu.add(clearChatMenuItem);
        savePopupMenu.addSeparator();
        savePopupMenu.add(saveUnFormattedMenuItem);
        savePopupMenu.add(saveFormattedMenuItem);
        jEditorPane1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    savePopupMenu.show(jEditorPane1, e.getX(), e.getY());
                }
            }
        });
		sendEmotionButton.setFocusable(false);
		setFontColorButton.setFocusable(false);
		sendFileButton.setFocusable(false);
		saveButton.setFocusable(false);

    }// </editor-fold>//GEN-END:initComponents

    public void cutText() {
        int start = jEditorPane2.getSelectionStart();
        int end = jEditorPane2.getSelectionEnd();
        if (start >= 0 && end > 0) {
            String content = jEditorPane2.getText();
            jEditorPane2.setText(content.substring(0, start) + content.substring(end, content.length()));
        }
    }

    private String replaceLinks(String input, int startPos) {
        int start = input.indexOf("http://", startPos);
        if (start == -1) {
            return input;
        } else {
            int end = input.indexOf(' ', start);
            if (end == -1) {
                end = input.length();
            }
            String link = input.substring(start, end);
            input = input.substring(0, start) + "<a href='" + link + "'>" + link + "</a>" + input.substring(end, input.length());
            if (input.indexOf("http://", start + (2 * link.length()) + 14) == -1) {
                return input;
            } else {
                return replaceLinks(input, start + (2 * link.length()) + 14);
            }
        }
    }

    private String replaceEmotionsAndLinks(String input) {
        input = replaceLinks(input, 0);
        boolean loop = true;
        int c = input.indexOf(':', 0);
        if (c == -1) {
            loop = false;
        }
        while (loop) {
            if (c < input.length()) {
                String obj = (String) ChatClientApp.getEmotionImages().get(input.substring(c, c + 2));
                if (obj != null) {
                    String newPart = "<img width=24 height=24 src='file://" + chatApp.getPath() + "images/" + obj + "'>";
                    input = input.substring(0, c) + newPart + input.substring(c + 2, input.length());
                }
            }
            if (c + 1 < input.length()) {
                c = input.indexOf(':', c + 1);
            }
            if (c == -1) {
                loop = false;
            }
        }
        loop = true;
        c = input.indexOf(";)", 0);
        if (c == -1) {
            loop = false;
        }
        while (loop) {
            if (c < input.length()) {
                String obj = (String) ChatClientApp.getEmotionImages().get(input.substring(c, c + 2));
                if (obj != null) {
                    String newPart = "<img width=24 height=24 src='file://" + chatApp.getPath() + "images/" + obj + "'>";
                    input = input.substring(0, c) + newPart + input.substring(c + 2, input.length());
                }
            }
            if (c + 1 < input.length()) {
                c = input.indexOf(";)", c + 1);
            }
            if (c == -1) {
                loop = false;
            }
        }
        return input;
    }
    private void jEditorPane2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEditorPane2KeyReleased
        if (evt.getKeyCode() == 10 && !evt.isShiftDown() && jEditorPane2.getCaretPosition() >= jEditorPane2.getDocument().getLength()) {
            sendChat();
        }
    }//GEN-LAST:event_jEditorPane2KeyReleased
    public void sendChat() {
        String result = getJEditorPane2().getText();
        if (result != null && !result.equals("")) {
            addTextChatForMe(result);
            TextMessage textMessage = new TextMessage();
            textMessage.setMessage(result);
            if (getChat_type() == IConstant.USER_CHAT) {
                textMessage.setToUserId(user.getId());
                textMessage.setTargetType(IConstant.USER_CHAT);
            } else if (getChat_type() == IConstant.GROUP_CHAT) {
                textMessage.setToUserId(group.getId());
                textMessage.setTargetType(IConstant.GROUP_CHAT);
            } else if (getChat_type() == IConstant.ROOM_CHAT) {
                textMessage.setToUserId(room.getId());
                textMessage.setTargetType(IConstant.ROOM_CHAT);
            }
            SendChatThread sendChatThread = new SendChatThread(this, textMessage, chatApp);
            sendChatThread.start();
        }
    }

    public void addTextChat(TextMessage msg) {
        try {
            Document doc = jEditorPane1.getDocument();
            EditorKit ek = jEditorPane1.getEditorKit();
            ((HTMLEditorKit) ek).insertHTML((HTMLDocument) doc,
                            doc.getEndPosition().getOffset() - 1,
                            "<font face="+destFont.getFamily()+" size="+destFont.getSize()+"pt' color="+Integer.toHexString( destColor.getRGB() & 0x00ffffff )+">"+destFontStyleOpen + msg.getTitle() + ": " + replaceEmotionsAndLinks(msg.getMessage()) + destFontStyleClose+"</font><br>",
                            /*int popDepth*/ 1,
                            /*int pushDepth*/ 0,
                            null /* javax.swing.text.html.HTML.Tag.CONTENT**/);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        jEditorPane1.setCaretPosition(jEditorPane1.getDocument().getLength() - 1);
    }

    public void addTextChatForMe(String result) {
        try {
            Document doc = jEditorPane1.getDocument();
            EditorKit ek = jEditorPane1.getEditorKit();
            ((HTMLEditorKit) ek).insertHTML((HTMLDocument) doc,
                            doc.getEndPosition().getOffset() - 1,
                            "<font face="+sourceFont.getFamily()+" size='"+sourceFont.getSize()+"pt' color="+Integer.toHexString( sourceColor.getRGB() & 0x00ffffff )+">"+ sourceFontStyleOpen+ chatApp.getUser().getName() + ": " + replaceEmotionsAndLinks(result) + sourceFontStyleClose+ "</font><br>",
                            /*int popDepth*/ 1,
                            /*int pushDepth*/ 0,
                            null /* javax.swing.text.html.HTML.Tag.CONTENT**/);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        jEditorPane1.setCaretPosition(jEditorPane1.getDocument().getLength() - 1);
        getJEditorPane2().setText("");
    }

    public void addTextChat(String msg) {
        try {
            Document doc = jEditorPane1.getDocument();
            EditorKit ek = jEditorPane1.getEditorKit();
            ((HTMLEditorKit) ek).insertHTML((HTMLDocument) doc,
                            doc.getEndPosition().getOffset() - 1,
                            "<font face="+destFont.getFamily()+" size='"+destFont.getSize()+"pt' color="+Integer.toHexString( destColor.getRGB() & 0x00ffffff )+">" + destFontStyleOpen+ msg +destFontStyleClose+ "</font><br>",
                            /*int popDepth*/ 1,
                            /*int pushDepth*/ 0,
                            null /* javax.swing.text.html.HTML.Tag.CONTENT**/);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        jEditorPane1.setCaretPosition(jEditorPane1.getDocument().getLength() - 1);
    }

    public void saveBinaryFile(BinaryMessage msg) {
        try {
            Document doc = jEditorPane1.getDocument();
            EditorKit ek = jEditorPane1.getEditorKit();
            ((HTMLEditorKit) ek).insertHTML((HTMLDocument) doc,
                            doc.getEndPosition().getOffset() - 1,
                            "<font face="+destFont.getFamily()+" size='"+destFont.getSize()+"pt' color="+Integer.toHexString( destColor.getRGB() & 0x00ffffff )+">" + destFontStyleOpen+ msg.getTitle() + ": " + msg.getDesc() + destFontStyleClose+"</font>",
                            /*int popDepth*/ 1,
                            /*int pushDepth*/ 0,
                            null /* javax.swing.text.html.HTML.Tag.CONTENT**/);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        jEditorPane1.setCaretPosition(jEditorPane1.getDocument().getLength() - 1);
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

    public Room getRoom() {
        return room;
    }

    public int getChat_type() {
        return chat_type;
    }

    public javax.swing.JEditorPane getJEditorPane2() {
        return jEditorPane2;
    }

    public javax.swing.JEditorPane getJEditorPane1() {
        return jEditorPane1;
    }
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        openSaveChatDialog(true);
    }//GEN-LAST:event_saveButtonActionPerformed
    public void openSendFileDialog() {
        int resultValue = chatApp.getJfm().showOpenDialog(this);
        if (resultValue == JFileChooser.APPROVE_OPTION) {
            File temp = chatApp.getJfm().getSelectedFile();
            sendFileInit(temp);
        }
    }

    private void openSaveChatDialog(boolean formatted) {
        chatApp.getJfm().setDialogTitle("Select File To Save Chat Log In");
        int resultValue = chatApp.getJfm().showSaveDialog(this);
        chatApp.getJfm().setDialogTitle("Select File To Send");
        if (resultValue == JFileChooser.APPROVE_OPTION) {
            File temp = chatApp.getJfm().getSelectedFile();
            saveChatInFile(temp, formatted);
        }
    }

    private void saveChatInFile(File temp, boolean formatted) {
        FileOutputStream fos = null;
        try {
            if(temp.exists()){
                int n = JOptionPane.showConfirmDialog(this, "File Already Exist!, Overwrite it?", "Warning", JOptionPane.OK_CANCEL_OPTION);
                if (n != JOptionPane.OK_OPTION) {
                    return;
                }
            }
            temp.createNewFile();
            fos = new FileOutputStream(temp);
            if (formatted) {
                fos.write(jEditorPane1.getText().getBytes());
            } else {
                fos.write(jEditorPane1.getDocument().getText(0, jEditorPane1.getDocument().getLength()).getBytes());
            }
            JOptionPane.showMessageDialog(this, "Chat Log Saved Successfully!", "Chat Log Saving", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed To Save Chat Log!", "Error", JOptionPane.ERROR_MESSAGE, chatApp.getErrorIcon());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFileInit(File temp) {
        if (chat_type == IConstant.USER_CHAT) {
            BinaryMessage bm = new BinaryMessage();
            bm.setToUserId(user.getId());
            bm.setTargetType(IConstant.USER_CHAT);
            bm.setDesc(temp.getName());
            addTextChatForMe("Request sending the file [" + temp.getName() + "], sent.");
            SendFileThread sendFileThread = new SendFileThread(temp, this, bm, chatApp);
            sendFileThread.start();
        } else if (chat_type == IConstant.GROUP_CHAT) {
            Group targetGroup = null;
            for (int l = 0; l <
                    chatApp.getGroups().size(); l++) {
                if (chatApp.getGroups().get(l).getId() == group.getId()) {
                    targetGroup = chatApp.getGroups().get(l);
                    break;
                }
            }
            if (targetGroup != null) {
                for (int i = 0; i <
                        targetGroup.getUsers().size(); i++) {
                    if (((User) targetGroup.getUsers().get(i)).getId() != chatApp.getUser().getId()) {
                        User childUser = (User) targetGroup.getUsers().get(i);
                        BinaryMessage bm = new BinaryMessage();
                        bm.setToUserId(childUser.getId());
                        bm.setTargetType(IConstant.USER_CHAT);
                        bm.setDesc(temp.getName());
                        addTextChatForMe("Request sending the file [" + temp.getName() + "] sent.");
                        SendFileThread sendFileThread = new SendFileThread(temp, this, bm, chatApp);
                        sendFileThread.start();
                    }
                }
            }
        }
        //no send file to Room
    }
    private void setFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setFontColorButtonActionPerformed
        chatApp.getFontFrame().setLocationRelativeTo(setFontColorButton);
        chatApp.getFontFrame().setVisible(true);
        initSettings(false);
    }//GEN-LAST:event_setFontColorButtonActionPerformed

    private void sendEmotionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendEmotionButtonActionPerformed
        chatApp.getEmotionPanel().setChatWindowPanel(this);
        chatApp.getEmotionFrame().setLocationRelativeTo(sendEmotionButton);
        chatApp.getEmotionFrame().setVisible(true);
    }//GEN-LAST:event_sendEmotionButtonActionPerformed

    private void sendFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFileButtonActionPerformed
        openSendFileDialog();
    }//GEN-LAST:event_sendFileButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton sendEmotionButton;
    private javax.swing.JButton sendFileButton;
    private javax.swing.JButton setFontColorButton;
    // End of variables declaration//GEN-END:variables

    class ActiveLink implements HyperlinkListener {

        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                JEditorPane pane = (JEditorPane) e.getSource();
                if (e instanceof HTMLFrameHyperlinkEvent) {
                    HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
                    HTMLDocument doc = (HTMLDocument) pane.getDocument();
                    doc.processHTMLFrameHyperlinkEvent(evt);
                } else {
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    Runtime rm = Runtime.getRuntime();
                    String temp = "";
                    if (e.getDescription().toString().indexOf('@') != -1) {
                        temp = "mailto:" + (e.getDescription().toString());
                        Desktop desktop = null;
                        if (Desktop.isDesktopSupported()) {
                            try {
                                desktop = Desktop.getDesktop();
                                desktop.mail(new URI(temp));
                            } catch (Exception ex) {
                            }
                        }
                    } else {
                        temp = e.getDescription().toString();
                        Desktop desktop = null;
                        if (Desktop.isDesktopSupported()) {
                            desktop = Desktop.getDesktop();
                            try {
                                desktop = Desktop.getDesktop();
                                desktop.browse(new URI(temp));
                            } catch (Exception ex) {
                            }
                        }
                    }
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            } else if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}

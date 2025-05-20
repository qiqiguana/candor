/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.server.client.ui;

/**
 *
 * @author ooransa
 */
import java.awt.*;
import java.awt.event.*;
import osa.ora.server.beans.IConstant;
import osa.ora.server.client.ChatClientApp;
import osa.ora.server.client.threads.SendMyStatusThread;

public class ChatSysTray {

    ChatClientApp chatApp;
    PopupMenu popup;
    MenuItem showWindowMenu;
    MenuItem setStatusOnOffMenu;
    MenuItem setStatusBusyOffMenu;
    MenuItem signOutMenu;
    MenuItem exitMenu;
    MenuItem[] itemStatus;
    TrayIcon trayIcon;
    private Image statusImage = null;

    private void handleClick(int clickCount, int button, int x, int y) {
        if (button == MouseEvent.BUTTON1) {
            if (clickCount == 2) {
                if (chatApp.getChatClientFrame().isVisible()) {
                    hideChatApp();
                } else {
                    showChatApp();
                }

            }
        }
    }

    public void updateMenuOptions() {
        int st = chatApp.getUser().getStatus_id();
        if (st == IConstant.ONLINE) {
            setStatusOnOffMenu.setLabel("Change Status To Offline");
            setStatusBusyOffMenu.setLabel("Change Status To Busy");
            statusImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/online.png"));
            trayIcon.setImage(getStatusImage());
            chatApp.getChatClientFrame().setIconImage(getStatusImage());
            chatApp.getChatClientFrame().repaint();
        } else if (st == IConstant.OFFLINE) {
            setStatusOnOffMenu.setLabel("Change Status To Online");
            setStatusBusyOffMenu.setLabel("Change Status To Busy");
            statusImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/offline.png"));
            trayIcon.setImage(getStatusImage());
            chatApp.getChatClientFrame().setIconImage(getStatusImage());
            chatApp.getChatClientFrame().repaint();
        } else {
            setStatusOnOffMenu.setLabel("Change Status To Online");
            setStatusBusyOffMenu.setLabel("Change Status To Offline");
            if (st == IConstant.BUSY) {
                statusImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/busy.png"));
                trayIcon.setImage(getStatusImage());
                chatApp.getChatClientFrame().setIconImage(getStatusImage());
                chatApp.getChatClientFrame().repaint();
            } else {
                statusImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/away.png"));
                chatApp.getChatClientFrame().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/away.png")));
                trayIcon.setImage(getStatusImage());
                chatApp.getChatClientFrame().setIconImage(getStatusImage());
                chatApp.getChatClientFrame().repaint();
            }
        }
    }

    public void logOut() {
        setStatusOnOffMenu.setEnabled(false);
        setStatusBusyOffMenu.setEnabled(false);
        signOutMenu.setEnabled(false);
        trayIcon.setToolTip("Free Instant Messenger");
        statusImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/groups.png"));
        trayIcon.setImage(getStatusImage());
        chatApp.getChatClientFrame().setIconImage(getStatusImage());
        chatApp.getChatClientFrame().repaint();
    }

    public void login() {
        setStatusOnOffMenu.setEnabled(true);
        setStatusBusyOffMenu.setEnabled(true);
        signOutMenu.setEnabled(true);
        trayIcon.setToolTip("Free Instant Messenger - " + chatApp.getUser().getName());
    }

    public ChatSysTray(ChatClientApp chatApp) {
        this.chatApp = chatApp;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/groups.png"));
            MouseListener mouseListener = new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    handleClick(e.getClickCount(), e.getButton(), e.getX(), e.getY());
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }
            };
            popup = new PopupMenu();
            showWindowMenu = new MenuItem("Open Free Instant Messenger");
            setStatusOnOffMenu = new MenuItem("Change Status To Online");
            setStatusBusyOffMenu = new MenuItem("Change Status To Busy");
            signOutMenu = new MenuItem("Log Out");
            exitMenu = new MenuItem("Exit");
            popup.add(showWindowMenu);
            popup.add(setStatusOnOffMenu);
            popup.add(setStatusBusyOffMenu);
            popup.add(signOutMenu);
            popup.add(exitMenu);
            if (chatApp.getUser() == null) {
                setStatusOnOffMenu.setEnabled(false);
                setStatusBusyOffMenu.setEnabled(false);
                signOutMenu.setEnabled(false);
            }
            exitMenu.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    closeApp();
                }
            });
            showWindowMenu.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    showChatApp();
                }
            });
            setStatusOnOffMenu.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (setStatusOnOffMenu.getLabel().indexOf("Online") != -1) {
                        changeStatus(IConstant.ONLINE);
                    } else {
                        changeStatus(IConstant.OFFLINE);
                    }
                }
            });
            setStatusBusyOffMenu.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (setStatusBusyOffMenu.getLabel().indexOf("Busy") != -1) {
                        changeStatus(IConstant.BUSY);
                    } else {
                        changeStatus(IConstant.OFFLINE);
                    }
                }
            });
            signOutMenu.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    changeStatus(IConstant.SIGN_OUT);
                }
            });
            trayIcon = new TrayIcon(image,"Free Instant Messenger", popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(mouseListener);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        } else {
            System.err.println("System tray is currently not supported.");
        }
    }

    public void showMessage(String title) {
        trayIcon.displayMessage("Incoming Chat", title, TrayIcon.MessageType.INFO);
    }

    private void closeApp() {
        if (chatApp.getUser() == null) {
            SystemTray.getSystemTray().remove(trayIcon);
            System.exit(0);
        } else {
            SystemTray.getSystemTray().remove(trayIcon);
            SendMyStatusThread sendMyStatusThread = new SendMyStatusThread(chatApp, SendMyStatusThread.SIGN_OUT_AND_EXIT);
            sendMyStatusThread.start();
        }
    }

    private void showChatApp() {
        chatApp.getChatClientFrame().setVisible(true);
        chatApp.getChatClientFrame().toFront();
        chatApp.getChatClientFrame().requestFocusInWindow();
    }

    private void hideChatApp() {
        chatApp.getChatClientFrame().setVisible(false);
        chatApp.getChatClientFrame().toBack();
    }

    private void changeStatus(int newStatus) {
        chatApp.getChatClientFrame().updateUserStatus(newStatus);
    }

    /**
     * @return the statusImage
     */
    public Image getStatusImage() {
        return statusImage;
    }
}

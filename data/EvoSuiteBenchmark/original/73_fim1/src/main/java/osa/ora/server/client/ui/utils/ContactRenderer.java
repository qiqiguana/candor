/*
 * ContactRenderer.java
 *
 * Created on October 29, 2009, 10:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package osa.ora.server.client.ui.utils;

/**
 *
 * @author ooransa
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import osa.ora.server.beans.Group;
import osa.ora.server.beans.IConstant;
import osa.ora.server.beans.Room;
import osa.ora.server.beans.User;

public class ContactRenderer implements TreeCellRenderer {

    private static Font ROOT_FONT = new Font("Dialog", Font.BOLD, 13);
    private static Font GROUP_FONT = new Font("Dialog", Font.BOLD, 12);
    private static Font ONLINE_USER_FONT = new Font("Dialog", Font.PLAIN, 12);
    private static Font OFFLINE_USER_FONT = new Font("Dialog", Font.ITALIC, 12);
    private JLabel returnLabel = new JLabel();
    private ImageIcon group = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/groups.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon online = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/online.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon offline = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/offline.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon busy = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/busy.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon away = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/away.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private ImageIcon room = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/room.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));

    public ContactRenderer() {
        super();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        ImageIcon icon = null;
        returnLabel.setOpaque(true);
        returnLabel.setBackground(Color.white);
        returnLabel.setFont(ONLINE_USER_FONT);
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) value;
        if (selectedNode.getUserObject() instanceof Group) {
            icon = group;
            returnLabel.setFont(GROUP_FONT);
        } else if (selectedNode.getUserObject() instanceof User) {
            if (((User) selectedNode.getUserObject()).getStatus_id() == IConstant.ONLINE) {
                icon = online;
                returnLabel.setFont(ONLINE_USER_FONT);
            } else if (((User) selectedNode.getUserObject()).getStatus_id() == IConstant.BUSY) {
                icon = busy;
                returnLabel.setFont(ONLINE_USER_FONT);
            } else if (((User) selectedNode.getUserObject()).getStatus_id() == IConstant.AWAY) {
                icon = away;
                returnLabel.setFont(ONLINE_USER_FONT);
            } else {
                icon = offline;
                returnLabel.setFont(OFFLINE_USER_FONT);
            }
        } else if (selectedNode.getUserObject() instanceof Room) {
            icon = room;
            returnLabel.setFont(GROUP_FONT);
        } else {
            icon = new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/chat.jpg")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            returnLabel.setFont(ROOT_FONT);
        }
        if (selected == true) {
            returnLabel.setForeground(Color.WHITE);
            returnLabel.setBackground(Color.BLUE);
        } else {
            returnLabel.setForeground(Color.BLACK);
            returnLabel.setBackground(Color.WHITE);
        }
        returnLabel.setText(value.toString());
        returnLabel.setIcon(icon);
        returnLabel.setPreferredSize(new Dimension(200, 20));
        return returnLabel;
    }
}

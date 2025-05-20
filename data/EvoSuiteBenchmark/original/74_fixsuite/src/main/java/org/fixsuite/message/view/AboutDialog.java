/**
 * AboutDialog.java 12:16:14 PM May 1, 2008
 * 
 * <PRE>
 * Copyright (c) 2008, Jan Amoyo
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 'AS IS';
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * </PRE>
 */

package org.fixsuite.message.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.fixsuite.message.LibrarySuite;

/**
 * @author jramoyo
 */
public class AboutDialog extends JDialog implements ActionListener,
        MouseListener {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;

    private JPanel buttonPanel;

    private JPanel infoPanel;

    private JLabel nameLabel;

    private JLabel descriptionLabel;

    private JLabel linkLabel;

    private JLabel authorLabel;

    private JButton closeButton;

    public AboutDialog() {
        super();
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        setTitle("About");
        setIconImage(new ImageIcon(".\\resources\\gray.gif").getImage());
        setModal(true);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel = new JPanel();
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        nameLabel = new JLabel(LibrarySuite.TITLE + " (" + LibrarySuite.VERSION
                + ")", new ImageIcon(".\\resources\\logo.gif"),
                SwingConstants.LEFT);
        nameLabel
                .setFont(new Font(nameLabel.getFont().getName(), Font.BOLD, 14));
        descriptionLabel = new JLabel(LibrarySuite.TITLE
                + " is part of the FixSuite package", new ImageIcon(
                ".\\resources\\bullet.gif"), SwingConstants.LEFT);
        authorLabel = new JLabel("Copyright (c) 2008, Jan Amoyo (BSD License)",
                new ImageIcon(".\\resources\\bullet.gif"), SwingConstants.LEFT);
        linkLabel = new JLabel(LibrarySuite.URL, new ImageIcon(
                ".\\resources\\bullet.gif"), SwingConstants.LEFT);
        linkLabel.addMouseListener(this);
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.setForeground(Color.BLUE);
        infoPanel.add(nameLabel);
        infoPanel.add(descriptionLabel);
        infoPanel.add(authorLabel);
        infoPanel.add(linkLabel);
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(closeButton)) {
            setVisible(false);
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(linkLabel)) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                invokeBrowser(LibrarySuite.URL);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    private boolean invokeBrowser(String url) {
        boolean flag = operatingSystem();
        String s1 = null;
        try {
            if (flag) {
                s1 = "rundll32 url.dll,FileProtocolHandler " + url;
                Runtime.getRuntime().exec(s1);
            } else {
                s1 = "netscape -remote openURL(" + url + ")";
                Process process1 = Runtime.getRuntime().exec(s1);
                try {
                    int i = process1.waitFor();
                    if (i != 0) {
                        s1 = "netscape " + url;
                        Runtime.getRuntime().exec(s1);
                    }
                } catch (InterruptedException ex) {
                    System.err.println("Error bringing up browser, cmd='" + s1
                            + "'");
                    System.err.println("Caught: " + ex);
                    return false;
                }
            }
        } catch (IOException ex) {
            System.err.println("Could not invoke browser, command=" + s1);
            System.err.println("Caught: " + ex);
            return false;
        }
        return true;
    }

    private boolean operatingSystem() {
        String name = System.getProperty("os.name");
        return ((name != null) && (name.startsWith("Windows")));
    }

}

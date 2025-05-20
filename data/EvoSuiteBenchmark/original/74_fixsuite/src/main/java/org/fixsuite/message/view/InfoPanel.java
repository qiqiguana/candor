/**
 * InfoPanel.java 9:02:25 PM Apr 24, 2008
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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author jramoyo
 */
public class InfoPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel label;

    private JLabel value;

    public InfoPanel(String label, String value) {
        initGUI();
        this.label.setText(label + ": ");
        this.value.setText(value);
    }

    private void initGUI() {
        setLayout(new GridLayout(1, 2, 10, 10));
        label = new JLabel();
        label.setFont(new Font(label.getFont().getName(), label.getFont()
                .getStyle(), 20));
        value = new JLabel();
        value.setFont(new Font(value.getFont().getName(), label.getFont()
                .getStyle(), 20));
        value.setForeground(Color.BLUE);

        add(label);
        add(value);
    }

    public void setValue(String value) {
        if (value != null && !value.equals("")) {
            this.value.setText(value);
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

}

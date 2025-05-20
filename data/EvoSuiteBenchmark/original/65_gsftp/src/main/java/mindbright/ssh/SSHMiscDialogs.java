/******************************************************************************
 *
 * Copyright (c) 1998,99 by Mindbright Technology AB, Stockholm, Sweden.
 *                 www.mindbright.se, info@mindbright.se
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *****************************************************************************
 * $Author: webjabber $
 * $Date: 2006/06/13 18:43:42 $
 * $Name:  $
 *****************************************************************************
 *
 * Modified by ISNetworks
 *
 *****************************************************************************/
package mindbright.ssh;

import java.awt.*;
import java.awt.event.*;

import mindbright.util.AWTConvenience;

public final class SSHMiscDialogs {

    private static Dialog alertDialog = null;
    private static Label  alertLabel;
    private static Button okAlertBut;
    public static void alert(String title, String message, Frame parent) {

	if(alertDialog == null) {
		alertDialog = new Dialog(parent, title, true);

	    GridBagLayout       grid  = new GridBagLayout();
	    GridBagConstraints  gridc = new GridBagConstraints();

	    alertDialog.setLayout(grid);

	    gridc.fill = GridBagConstraints.HORIZONTAL;
	    gridc.weightx   = 1.0;
	    gridc.weighty   = 1.0;
	    gridc.gridwidth = GridBagConstraints.REMAINDER;
	    gridc.anchor    = GridBagConstraints.CENTER;
	    gridc.insets    = new Insets(8, 4, 4, 8);

	    gridc.gridy = 0;
	    alertLabel = new Label();
	    grid.setConstraints(alertLabel, gridc);
	    alertDialog.add(alertLabel);

	    okAlertBut = new Button("OK");
	    okAlertBut.addActionListener(new AWTConvenience.CloseAction(alertDialog));
	    gridc.fill = GridBagConstraints.NONE;
	    gridc.gridy = 1;
	    grid.setConstraints(okAlertBut, gridc);
	    alertDialog.add(okAlertBut);

	    alertDialog.addWindowListener(new AWTConvenience.CloseAdapter(okAlertBut));

	    AWTConvenience.setBackgroundOfChildren(alertDialog);

	    alertDialog.setResizable(true);
	}

	alertDialog.setTitle(title);

	alertDialog.remove(alertLabel);
	alertLabel.setText(message);
	alertDialog.add(alertLabel);
	alertDialog.pack();

	AWTConvenience.placeDialog(alertDialog);
	okAlertBut.requestFocus();
	alertDialog.setVisible(true);
    }

	/** Original method */
    public static String password(String title, String message, Frame parent) {
		return password( title, message, parent, '*', "", "Password:" );
    }

	/** Create a dialog box with a title, a text field, and label for the text field */
    public static String textfield(String title, String message, Frame parent) {
		return password( title, "", parent, (char)0, "", message );
    }

	/** Create a dialog box with a title, a text field with a default value, and label for the text field */
    public static String textfield(String title, String message, Frame parent, String defaultValue) {
		return password( title, "", parent, (char)0, defaultValue, message );
    }

    private static Dialog  passwordDialog = null;
    private static Label   pwdMsgLabel;
	private static Label   pwdTextBoxLabel;
    private static String  pwdAnswer;
    private static TextField pwdPassword;
    public static String password(String title, String message, Frame parent, char echo, String defaultValue, String textBoxLabel ) {

	if(passwordDialog == null) {
	    passwordDialog = new Dialog(parent, title, true);

	    GridBagLayout       grid  = new GridBagLayout();
	    GridBagConstraints  gridc = new GridBagConstraints();
	    ActionListener      al;
	    passwordDialog.setLayout(grid);
	    Label lbl;

	    gridc.fill = GridBagConstraints.HORIZONTAL;
	    gridc.gridwidth = GridBagConstraints.REMAINDER;
	    gridc.anchor    = GridBagConstraints.CENTER;
	    gridc.insets    = new Insets(8, 4, 4, 8);

	    gridc.gridy = 0;
	    pwdMsgLabel = new Label();
	    grid.setConstraints(pwdMsgLabel, gridc);
	    passwordDialog.add(pwdMsgLabel);

	    gridc.gridy = 1;
	    gridc.gridwidth = 1;
	    gridc.anchor    = GridBagConstraints.WEST;
	    pwdTextBoxLabel = new Label("Password:");
	    grid.setConstraints(pwdTextBoxLabel, gridc);
	    passwordDialog.add(pwdTextBoxLabel);

	    pwdPassword = new TextField();
	    gridc.gridwidth = GridBagConstraints.REMAINDER;
	    grid.setConstraints(pwdPassword, gridc);
	    passwordDialog.add(pwdPassword);

	    Panel bp = new Panel(new FlowLayout());

	    Button okBut, cancBut;
	    bp.add(okBut = new Button("OK"));

	    okBut.addActionListener(al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if(e.getActionCommand().equals("OK")) {
			pwdAnswer = pwdPassword.getText();
		    } else {
			pwdAnswer = null;
		    }
		    passwordDialog.setVisible(false);
		}
	    });

	    bp.add(cancBut = new Button("Cancel"));
	    cancBut.addActionListener(al);

	    gridc.gridy = 2;
	    gridc.gridwidth = GridBagConstraints.REMAINDER;
	    grid.setConstraints(bp, gridc);
	    passwordDialog.add(bp);

	    passwordDialog.addWindowListener(new AWTConvenience.CloseAdapter(cancBut));

	    AWTConvenience.setKeyListenerOfChildren(passwordDialog,
						    new AWTConvenience.OKCancelAdapter(okBut, cancBut),
						    null);

	    AWTConvenience.setBackgroundOfChildren(passwordDialog);

	    passwordDialog.setResizable(true);
	}

	passwordDialog.setTitle(title);

	passwordDialog.remove(pwdMsgLabel);
	pwdMsgLabel.setText(message);
	pwdPassword.setText(defaultValue);
    pwdPassword.setEchoChar(echo);
	pwdPassword.setColumns( 40 );
	passwordDialog.add(pwdMsgLabel);
	passwordDialog.pack();
	pwdTextBoxLabel.setText(textBoxLabel);
	passwordDialog.setSize( 450, 150 );
	AWTConvenience.placeDialog(passwordDialog);

	passwordDialog.setVisible(true);

	return pwdAnswer;
    }

    private static Dialog  setPasswordDialog = null;
    private static Label   setPwdMsgLabel;
    private static String  setPwdAnswer;
    private static TextField setPwdText, setPwdText2;
    public static String setPassword(String title, String message, Frame parent) {

	if(setPasswordDialog == null) {
	    setPasswordDialog = new Dialog(parent, title, true);

	    GridBagLayout       grid  = new GridBagLayout();
	    GridBagConstraints  gridc = new GridBagConstraints();
	    ActionListener      al;
	    setPasswordDialog.setLayout(grid);
	    Label lbl;

	    gridc.fill = GridBagConstraints.HORIZONTAL;
	    gridc.gridwidth = GridBagConstraints.REMAINDER;
	    gridc.anchor    = GridBagConstraints.CENTER;
	    gridc.insets    = new Insets(8, 4, 4, 8);

	    gridc.gridy = 0;
	    setPwdMsgLabel = new Label();
	    grid.setConstraints(setPwdMsgLabel, gridc);
	    setPasswordDialog.add(setPwdMsgLabel);

	    gridc.gridy = 1;
	    gridc.gridwidth = 1;
	    gridc.anchor    = GridBagConstraints.WEST;
	    lbl = new Label("Password:");
	    grid.setConstraints(lbl, gridc);
	    setPasswordDialog.add(lbl);

	    setPwdText = new TextField("", 12);
	    grid.setConstraints(setPwdText, gridc);
	    setPwdText.setEchoChar('*');
	    setPasswordDialog.add(setPwdText);

	    gridc.gridy = 2;
	    lbl = new Label("Password again:");
	    grid.setConstraints(lbl, gridc);
	    setPasswordDialog.add(lbl);

	    setPwdText2 = new TextField("", 12);
	    grid.setConstraints(setPwdText2, gridc);
	    setPwdText2.setEchoChar('*');
	    setPasswordDialog.add(setPwdText2);

	    Panel bp = new Panel(new FlowLayout());

	    Button okBut, cancBut;
	    bp.add(okBut = new Button("OK"));

	    okBut.addActionListener(al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if(e.getActionCommand().equals("OK")) {
			setPwdAnswer = setPwdText.getText();
			if(!setPwdAnswer.equals(setPwdText2.getText())) {
			    setPwdText.setText("");
			    setPwdText2.setText("");
			    return;
			}
		    } else {
			setPwdAnswer = null;
		    }
		    setPasswordDialog.setVisible(false);
		}
	    });

	    bp.add(cancBut = new Button("Cancel"));
	    cancBut.addActionListener(al);

	    gridc.gridy = 3;
	    gridc.gridwidth = GridBagConstraints.REMAINDER;
	    grid.setConstraints(bp, gridc);
	    setPasswordDialog.add(bp);

	    setPasswordDialog.addWindowListener(new AWTConvenience.CloseAdapter(cancBut));

	    AWTConvenience.setKeyListenerOfChildren(setPasswordDialog,
						    new AWTConvenience.OKCancelAdapter(okBut, cancBut),
						    null);

	    AWTConvenience.setBackgroundOfChildren(setPasswordDialog);

	    setPasswordDialog.setResizable(true);
	}

	setPasswordDialog.setTitle(title);

	setPasswordDialog.remove(setPwdMsgLabel);
	setPwdMsgLabel.setText(message);
	setPwdText.setText("");
	setPwdText2.setText("");
	setPasswordDialog.add(setPwdMsgLabel);
	setPasswordDialog.pack();

	AWTConvenience.placeDialog(setPasswordDialog);

	setPasswordDialog.setVisible(true);

	return setPwdAnswer;
    }

    private static Dialog  confirmDialog = null;
    private static Label   confirmLabel;
    private static boolean confirmRet;
    private static Button  yesBut, noBut;
    public static boolean confirm(String title, String message, boolean defAnswer,
				  Frame parent) {

	if(confirmDialog == null) {
	    confirmDialog = new Dialog(parent, title, true);

	    GridBagLayout       grid  = new GridBagLayout();
	    GridBagConstraints  gridc = new GridBagConstraints();
	    ActionListener      al;
	    confirmDialog.setLayout(grid);

	    gridc.fill = GridBagConstraints.HORIZONTAL;
	    gridc.gridwidth = GridBagConstraints.REMAINDER;
	    gridc.anchor    = GridBagConstraints.CENTER;
	    gridc.insets    = new Insets(8, 4, 4, 8);

	    gridc.gridy = 0;
	    confirmLabel = new Label();
	    grid.setConstraints(confirmLabel, gridc);
	    confirmDialog.add(confirmLabel);

	    Panel bp = new Panel(new FlowLayout());

	    bp.add(yesBut = new Button("Yes"));

	    yesBut.addActionListener(al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if(e.getActionCommand().equals("Yes"))
		    confirmRet = true;
		    else
		    confirmRet = false;
		    confirmDialog.setVisible(false);
		}
	    });

	    bp.add(noBut = new Button("No"));
	    noBut.addActionListener(al);

	    gridc.gridy = 1;
	    gridc.gridwidth = GridBagConstraints.REMAINDER;
	    grid.setConstraints(bp, gridc);
	    confirmDialog.add(bp);

	    confirmDialog.addWindowListener(new AWTConvenience.CloseAdapter(noBut));

	    AWTConvenience.setBackgroundOfChildren(confirmDialog);

	    confirmDialog.setResizable(true);
	}

	confirmDialog.remove(confirmLabel);
	confirmLabel.setText(message);
	confirmDialog.add(confirmLabel);
	confirmDialog.pack();

	AWTConvenience.placeDialog(confirmDialog);

	if(defAnswer)
	    yesBut.requestFocus();
	else
	    noBut.requestFocus();

	confirmDialog.setVisible(true);

	return confirmRet;
    }

    public static void notice(String title, String text, int rows, int cols,
			      boolean scrollbar, Frame parent) {
	Dialog   textDialog = null;
	TextArea textArea;
	Button   okTextBut;

	textDialog = new Dialog(parent, title, true);

	GridBagLayout       grid  = new GridBagLayout();
	GridBagConstraints  gridc = new GridBagConstraints();

	textDialog.setLayout(grid);

	gridc.fill      = GridBagConstraints.NONE;
	gridc.gridwidth = GridBagConstraints.REMAINDER;
	gridc.anchor    = GridBagConstraints.CENTER;
	gridc.insets    = new Insets(4, 4, 4, 4);

	textArea = new TextArea(text, rows, cols,
				scrollbar ? TextArea.SCROLLBARS_VERTICAL_ONLY : TextArea.SCROLLBARS_NONE);
	grid.setConstraints(textArea, gridc);
	textDialog.add(textArea);
	textArea.setEditable(false);

	okTextBut = new Button("OK");
	okTextBut.addActionListener(new AWTConvenience.CloseAction(textDialog));
	gridc.fill = GridBagConstraints.NONE;
	grid.setConstraints(okTextBut, gridc);
	textDialog.add(okTextBut);

	textDialog.addWindowListener(new AWTConvenience.CloseAdapter(okTextBut));

	AWTConvenience.setBackgroundOfChildren(textDialog);

	textDialog.setResizable(true);
	textDialog.pack();

	AWTConvenience.placeDialog(textDialog);
	okTextBut.requestFocus();
	textDialog.setVisible(true);
    }

}

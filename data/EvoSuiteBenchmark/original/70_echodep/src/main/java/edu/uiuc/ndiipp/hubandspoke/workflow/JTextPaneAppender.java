package edu.uiuc.ndiipp.hubandspoke.workflow;

import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class JTextPaneAppender extends AppenderSkeleton {

	private JTextPane _jtextpane = null;

	public Style _styleNormal;
	public Style _styleBold;
	public Style _styleRed;
	public Style _styleBlue;

	private Layout _pl = new FlexibleLayout();

	private JTextPaneAppender() {
	}

	public JTextPaneAppender(JTextPane j) {
		super();
		_jtextpane = j;

		StyledDocument doc = _jtextpane.getStyledDocument();

		_styleNormal = doc.addStyle("regular", null);
		StyleConstants.setForeground(_styleNormal, Color.BLACK);

		_styleBlue = doc.addStyle("blue", null);
		StyleConstants.setForeground(_styleBlue, Color.BLUE);

		_styleBold = doc.addStyle("bold", _styleNormal);
		StyleConstants.setBold(_styleBold, true);

		_styleRed = doc.addStyle("red", _styleBold);
		StyleConstants.setForeground(_styleRed, Color.RED);
	}

	@Override
	protected void append(LoggingEvent event) {
		String toLog = _pl.format(event);
		if (_jtextpane != null) {
			if (event.getLevel().equals(Level.ERROR))
				logError(toLog);
			else if (event.getLevel().equals(Level.FATAL))
				logFatal(toLog);
			else if (event.getLevel().equals(Level.WARN))
				logWarning(toLog);
			else
				logNormal(toLog);
		}
	}

	private void logNormal(String s) {
		log(s, _styleNormal);
	}

	private void logWarning(String s) {
		log(s, _styleBlue);
	}

	private void logFatal(String s) {
		logError(s);
	}

	private void logError(String s) {
		log(s, _styleRed);
	}

	private void log(String s, Style style) {
		if (s == null)
			return;
		if (_jtextpane == null)
			return;
		StyledDocument doc = _jtextpane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), s, style);
			_jtextpane.setCaretPosition(_jtextpane.getDocument().getLength());
		} catch (BadLocationException e) {
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}

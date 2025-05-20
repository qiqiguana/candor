package ghm.follow.search;

import ghm.follow.gui.FileFollowingPane;
import ghm.follow.FollowApp;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FindDialog extends JDialog
{
	private JButton findButton;
	private JButton clearButton;
	private JButton closeButton;
	private JLabel statusBar;
	private JScrollPane resultPane;
	private String resultsLabel;
	private JTextField findField;
	private JCheckBox regEx;
	private JCheckBox caseSensitive;

	// instance of action that created this dialog
	private Find findAction;

	public FindDialog(Find find)
	{
		super(find.getApp().getFrame(), FollowApp.getResourceString("dialog.Find.title"), false);
		// keep a reference for use later
		findAction = find;
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					setVisible(false);
				}
			}
		});
		setResizable(false);
		JComponent contentPane = (JComponent) getContentPane();
		contentPane.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JPanel findPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		//
		// add the find field & label
		//
		gbc.ipadx = 4;
		findPanel.add(new JLabel(FollowApp.getResourceString("dialog.Find.findText.label")), gbc);
		findField = new JTextField(15);
		findField.setHorizontalAlignment(JTextField.LEFT);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.ipadx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		findPanel.add(findField);
		//
		// add the case sensitive check box
		//
		caseSensitive = new JCheckBox(FollowApp.getResourceString("dialog.Find.caseSensitive.label"));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		caseSensitive.setHorizontalAlignment(JCheckBox.LEFT);
		findPanel.add(caseSensitive, gbc);
		//
		// add the regular expression check box
		//
		regEx = new JCheckBox(FollowApp.getResourceString("dialog.Find.regularExpression.label"));
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		regEx.setHorizontalAlignment(JCheckBox.LEFT);
		findPanel.add(regEx, gbc);
		//
		// add the find button
		//
		findButton = new JButton(FollowApp.getResourceString("dialog.Find.findButton.label"));
		findButton.setMnemonic(FollowApp.getResourceString("dialog.Find.findButton.mnemonic").charAt(0));
		findButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				findButton_clicked(e);
				findField.grabFocus();
				findField.selectAll();
			}
		});

		// add the clear button
		clearButton = new JButton(FollowApp.getResourceString("dialog.Find.clearButton.label"));
		clearButton.setMnemonic(FollowApp.getResourceString("dialog.Find.clearButton.mnemonic").charAt(0));
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				clearButton_clicked(e);
				findField.grabFocus();
				findField.selectAll();
			}
		});

		// add the close button
		closeButton = new JButton(FollowApp.getResourceString("dialog.Find.closeButton.label"));
		closeButton.setMnemonic(FollowApp.getResourceString("dialog.Find.closeButton.mnemonic").charAt(0));
		closeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		// add the buttons to the dialog
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// create buttons
		buttonPanel.add(findButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(closeButton);

		// create status bar
		JPanel statusPanel = new JPanel(new BorderLayout());
		resultPane = new JScrollPane();
		resultPane.setVisible(false);
		statusBar = new JLabel(" ");
		statusBar.setFont(new Font("Arial", Font.PLAIN, 10));
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.add(statusBar, BorderLayout.CENTER);
		statusPanel.add(resultPane, BorderLayout.SOUTH);

		// add everything to the content pane
		contentPane.add(findPanel, BorderLayout.NORTH);
		contentPane.add(buttonPanel, BorderLayout.CENTER);
		contentPane.add(statusPanel, BorderLayout.SOUTH);
	}

	public void initFocus()
	{
		findField.grabFocus();
		findField.selectAll();
	}

	/**
	 * Override method to add ESCAPE key action for window close
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 */
	protected JRootPane createRootPane()
	{
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		JRootPane rootPane = new JRootPane();
		rootPane.registerKeyboardAction(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				closeButton.doClick();
			}
		}, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		rootPane.registerKeyboardAction(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				findButton.doClick();
			}
		}, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}

	private void findButton_clicked(ActionEvent e)
	{
		findAction.getApp().setCursor(Cursor.WAIT_CURSOR);
		clearResults();
		List<LineResult> results = doFind();
		if (results != null)
		{
			if (results.size() == 0)
			{
				statusBar.setText("Search term not found.");
			}
			else
			{
				JList resultList = showResults(results);
				resultList.addListSelectionListener(new ListSelectionListener()
				{
					/**
					 * Catches selection events and sets the caret within the view so that the
					 * screen scrolls
					 * 
					 * @author Carl Hall (carl.hall@gmail.com)
					 */
					public void valueChanged(ListSelectionEvent ev)
					{
						resultList_changed(ev);
					}
				});
			}
		}
		findAction.getApp().setCursor(Cursor.DEFAULT_CURSOR);
	}

	private void clearButton_clicked(ActionEvent e)
	{
		findAction.getApp().setCursor(Cursor.WAIT_CURSOR);
		// get the current selected tab
		FileFollowingPane pane = findAction.getApp().getSelectedFileFollowingPane();
		// clear the highlights from the searched tab
		SearchableTextPane textArea = pane.getTextPane();
		textArea.removeHighlights();
		// clear the status bar and result list
		clearResults();
		findAction.getApp().setCursor(Cursor.DEFAULT_CURSOR);
	}

	/**
	 * Show results some results by creating a list and updating the status bar
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 * @param results
	 * @return
	 */
	private JList showResults(List<LineResult> results)
	{
		// create a list of the results
		JList resultList = new JList(results.toArray());
		resultList.setFont(new Font("Arial", Font.PLAIN, 10));

		// set the status bar
		;
		statusBar.setText(" " + countResults(results) + " " + resultsLabel);

		// show the result list
		resultPane.getViewport().setView(resultList);
		resultPane.setVisible(true);

		// resize the dialog
		pack();
		return resultList;
	}

	private void resultList_changed(ListSelectionEvent ev)
	{
		if (!ev.getValueIsAdjusting())
		{
			JList list = (JList) ev.getSource();
			int pos = list.getSelectedIndex();
			if (pos >= 0)
			{
				// get the result associated to the
				// selected position
				LineResult result = (LineResult) list.getModel().getElementAt(pos);

				// get the current selected tab
				// and text area
				FileFollowingPane ffp = findAction.getApp().getSelectedFileFollowingPane();
				SearchableTextPane tp = ffp.getTextPane();
				// move the caret to the chosen text
				tp.setCaretPosition(result.getFirstWordPosition());
			}
		}
	}

	private int countResults(List<LineResult> results)
	{
		int count = 0;
		for (LineResult result : results)
		{
			count += result.getWordResults().size();
		}
		return count;
	}

	/**
	 * Clear out the results list and status bar.
	 * 
	 * @author Carl Hall (carl.hall@gmail.com)
	 */
	private void clearResults()
	{
		// clear the status bar
		statusBar.setText(" ");

		// clear and hide the result list
		resultPane.getViewport().setView(null);
		resultPane.setVisible(false);

		// resize the dialog
		pack();
	}

	private List<LineResult> doFind()
	{
		// get the current selected tab
		FileFollowingPane pane = findAction.getApp().getSelectedFileFollowingPane();
		// search the tab with the given text
		SearchableTextPane textArea = pane.getTextPane();
		int flags = 0;

		if (caseSensitive.isSelected())
		{
			flags |= SearchEngine.CASE_SENSITIVE;
		}
		if (regEx.isSelected())
		{
			flags |= SearchEngine.REGEX;
		}
		List<LineResult> results = textArea.highlight(findField.getText(), flags);
		// select search term for convenience
		findField.grabFocus();
		findField.selectAll();
		return results;
	}
}
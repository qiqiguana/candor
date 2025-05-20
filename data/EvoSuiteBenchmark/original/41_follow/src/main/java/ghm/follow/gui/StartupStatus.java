/* 
Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)

This file is part of Follow (http://follow.sf.net).

Follow is free software; you can redistribute it and/or modify
it under the terms of version 2 of the GNU General Public
License as published by the Free Software Foundation.

Follow is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Follow; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ghm.follow.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

/**
 * Window which displays a progress bar during startup.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class StartupStatus extends JWindow
{

	public StartupStatus(ResourceBundle resourceBundle)
	{
		LOAD_SYSTEM_FONTS = new Task(2, resourceBundle.getString("startupStatus.loadSystemFonts"));
		allTasks.add(LOAD_SYSTEM_FONTS);

		CREATE_WIDGETS = new Task(2, resourceBundle.getString("startupStatus.createWidgets"));
		allTasks.add(CREATE_WIDGETS);

		int taskWeightSummation = 0;
		for (Task task : allTasks)
		{
			taskWeightSummation += task.weight;
		}
		progressBar = new JProgressBar(0, taskWeightSummation);
		progressBar.setStringPainted(true);
		progressBar.setString(allTasks.get(0).inProgressMessage);

		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(6);
		JPanel panel = new JPanel(borderLayout);
		panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 11, 11));
		JLabel label = new JLabel(resourceBundle.getString("startupStatus.label"));
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label, BorderLayout.NORTH);
		panel.add(progressBar, BorderLayout.SOUTH);
		this.getContentPane().add(panel);
	}

	private int currentTask;

	public void markDone(final Task task)
	{
		if (allTasks.indexOf(task) != currentTask)
		{
			throw new RuntimeException(
					"Programmatic error: tasks should be marked done sequentially");
		}
		progressBar.setValue(progressBar.getValue() + task.weight);
		currentTask++;
		if (currentTask < allTasks.size())
		{
			progressBar.setString(((Task) allTasks.get(currentTask)).inProgressMessage);
		}
	}

	private JProgressBar progressBar = new JProgressBar();

	// Must be final to force clients to use the Tasks declared 'final' when
	// marking Tasks as done
	private final List<Task> allTasks = new ArrayList<Task>();

	// Complete set of Tasks which need to be completed to start the Follow app
	public final Task LOAD_SYSTEM_FONTS;
	public final Task CREATE_WIDGETS;

	/**
	 * Instances of this class represent significant tasks which must be accomplished in order to
	 * start the Follow application.
	 */
	static class Task
	{
		// private to prevent instantiation by clients
		private Task(int weight, String inProgressMessage)
		{
			this.weight = weight;
			this.inProgressMessage = inProgressMessage;
		}

		// final to prevent modification by clients
		final private int weight;
		final private String inProgressMessage;
	}

}

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

import ghm.follow.FileFollower;
import ghm.follow.io.JTextComponentDestination;
import ghm.follow.io.OutputDestination;
import ghm.follow.search.SearchableTextPane;

import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * A component which allows one to view a text file to which information is being asynchronously
 * appended.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class FileFollowingPane extends JScrollPane
{
	private Logger log = Logger.getLogger(FileFollowingPane.class.getName());
	/** FileFollower used to print to this component */
	protected FileFollower fileFollower;

	/** Text area into which followed file's contents are printed */
	protected SearchableTextPane textArea;

	/** OutputDestination used w/FileFollower */
	protected JTextComponentDestination destination;

	/**
	 * @param file
	 *            text file to be followed
	 * @param bufferSize
	 *            size of the character buffer inside the FileFollower used to follow the supplied
	 *            file
	 * @param latency
	 *            latency of the FileFollower used to follow the supplied file
	 */
	public FileFollowingPane(File file, int bufferSize, int latency, boolean autoPositionCaret,
			Font font, int tabSize)
	{
		textArea = new SearchableTextPane(font, tabSize);
		textArea.setEditable(false);
		textArea.setUI(new LineTextUI());
		destination = new JTextComponentDestination(textArea, autoPositionCaret);
		fileFollower = new FileFollower(file, bufferSize, latency,
				new OutputDestination[] { destination });
		add(textArea);
		setViewportView(textArea);
	}

	/**
	 * Returns the text area to which the followed file's contents are being printed.
	 * 
	 * @return text area containing followed file's contents
	 */
	public SearchableTextPane getTextPane()
	{
		return textArea;
	}

	/**
	 * Returns whether caret is automatically repositioned to the end of the text area when text is
	 * appended to the followed file
	 * 
	 * @return whether caret is automatically repositioned on append
	 */
	public boolean autoPositionCaret()
	{
		return destination.autoPositionCaret();
	}

	/**
	 * Sets whether caret is automatically repositioned to the end of the text area when text is
	 * appended to the followed file
	 * 
	 * @param value
	 *            whether caret is automatically repositioned on append
	 */
	public void setAutoPositionCaret(boolean value)
	{
		destination.setAutoPositionCaret(value);
	}

	/**
	 * Returns the FileFollower which is being used to print information in this component.
	 * 
	 * @return FileFollower used by this component
	 */
	public FileFollower getFileFollower()
	{
		return fileFollower;
	}

	/**
	 * Convenience method; equivalent to calling getFileFollower().getFollowedFile()
	 */
	public File getFollowedFile()
	{
		return fileFollower.getFollowedFile();
	}

	/**
	 * Convenience method; equivalent to calling getFileFollower().start()
	 */
	public void startFollowing()
	{
		fileFollower.start();
	}

	/**
	 * Convenience method; equivalent to calling getFileFollower().stop()
	 */
	public void stopFollowing()
	{
		fileFollower.stop();
	}

	/**
	 * Convenience method; equivalent to calling getFileFollower().restart()
	 */
	public void restartFollowing()
	{
		fileFollower.restart();
	}

	/**
	 * Convenience method; equivalent to calling getFileFollower().pause()
	 */
	public void pauseFollowing()
	{
		fileFollower.pause();
	}

	/**
	 * Convenience method; equivalent to calling getFileFollower().unpause()
	 */
	public void unpauseFollowing()
	{
		fileFollower.unpause();
	}

	/**
	 * Convenience method; equivalent to calling getFileFollower().isPaused()
	 * 
	 * @return
	 */
	public boolean isFollowingPaused()
	{
		return fileFollower.isPaused();
	}

	/**
	 * Convenience method; equivalent to calling getFileFollower().stopAndWait()
	 */
	public void stopFollowingAndWait() throws InterruptedException
	{
		fileFollower.stopAndWait();
	}

	/**
	 * Convenience method; equivalent to called getFileFollower().isBeingFollowed()
	 * 
	 * @return
	 */
	public boolean isFollowing()
	{
		return fileFollower.isBeingFollowed();
	}

	/**
	 * Clears the contents of this FileFollowingPane synchronously.
	 */
	public void clear() throws IOException
	{
		if (fileFollower.getFollowedFile().length() == 0L)
		{
			return;
		}
		synchronized (fileFollower)
		{
			try
			{
				fileFollower.stopAndWait();
			}
			catch (InterruptedException interruptedException)
			{
				// Handle this better later
				log.log(Level.SEVERE, "InterrupedException in FileFollowingPane", interruptedException);
			}

			// This has the effect of clearing the contents of the followed file
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileFollower
					.getFollowedFile()));
			bos.close();

			// Update textarea contents to reflect freshly cleared file
			Document doc = textArea.getDocument();
			try
			{
				doc.remove(0, doc.getLength());
			}
			catch (BadLocationException e)
			{
				// Handle this better later
				log.log(Level.WARNING, "BadLocationException in FileFolloingPane", e);
			}

			fileFollower.start();
		}
	}
}
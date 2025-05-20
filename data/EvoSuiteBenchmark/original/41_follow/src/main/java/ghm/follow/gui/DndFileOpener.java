/*
 * Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)
 * 
 * This file is part of Follow (http://follow.sf.net).
 * 
 * Follow is free software; you can redistribute it and/or modify it under the
 * terms of version 2 of the GNU General Public License as published by the Free
 * Software Foundation.
 * 
 * Follow is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Follow; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA
 */

package ghm.follow.gui;

import ghm.follow.FollowApp;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Implementation of <code>java.awt.dnd.DropTargetListener</code> which opens files dropped on the
 * Follow application's tabbed pane.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class DndFileOpener implements DropTargetListener
{

	public DndFileOpener(FollowApp app)
	{
		this.app = app;
	}

	/**
	 * If the DropTargetDropEvent's DataFlavor is javaFileListFlavor, it opens the List of dropped
	 * files in the Follow application. No other DataFlavors are supported.
	 * 
	 * @param e
	 *            "drop" event
	 * @see java.awt.dnd.DropTargetListener#drop(DropTargetDropEvent)
	 */
	public void drop(DropTargetDropEvent e)
	{
		DataFlavor[] flavors = e.getCurrentDataFlavors();
		int numFlavors = (flavors != null) ? flavors.length : 0;
		for (int i = 0; i < numFlavors; i++)
		{
			// Ignore all flavors except javaFileListType
			if (flavors[i].isFlavorJavaFileListType())
			{
				e.acceptDrop(DnDConstants.ACTION_COPY);
				boolean dropCompleted = false;
				Transferable transferable = e.getTransferable();
				try
				{
					List<File> fileList = (List<File>) transferable.getTransferData(flavors[i]);
					for (File file : fileList)
					{
						app.openFile(file);
					}
					dropCompleted = true;
				}
				catch (UnsupportedFlavorException ufException)
				{
					// do nothing
				}
				catch (IOException ioException)
				{
					// do nothing
				}
				finally
				{
					e.dropComplete(dropCompleted);
				}
			}
		}
	}

	/** Does nothing. */
	public void dragEnter(DropTargetDragEvent e)
	{
	}

	/** Does nothing. */
	public void dragOver(DropTargetDragEvent e)
	{
	}

	/** Does nothing. */
	public void dragExit(DropTargetEvent e)
	{
	}

	/** Does nothing. */
	public void dragScroll(DropTargetDragEvent e)
	{
	}

	/** Does nothing. */
	public void dropActionChanged(DropTargetDragEvent e)
	{
	}

	FollowApp app;

}

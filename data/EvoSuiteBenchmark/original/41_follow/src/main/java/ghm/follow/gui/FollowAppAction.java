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
import ghm.follow.InvalidVkException;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * Base class for all actions in the Follow application.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 * @author <a href="mailto:carl.hall@gmail.com">Carl Hall</a>
 */
public abstract class FollowAppAction extends AbstractAction
{
	public enum ActionContext
	{
		MULTI_FILE, SINGLE_FILE, APP;
	}

	private static Logger log = Logger.getLogger(FollowAppAction.class.getName());

	private FollowApp app;
	private int mnemonic;
	private KeyStroke accelerator;
	private ActionContext context;

	public FollowAppAction(FollowApp app, String name, String mnemonic, String accelerator,
			ActionContext type)
	{
		super(name);
		init(app, mnemonic, accelerator, type);
	}

	public FollowAppAction(FollowApp app, String name, String mnemonic, String accelerator,
			ImageIcon icon, ActionContext type)
	{
		super(name, icon);
		init(app, mnemonic, accelerator, type);
	}

	public FollowApp getApp()
	{
		return app;
	}

	private void init(FollowApp app, String mnemonic, String accelerator, ActionContext type)
	{
		this.app = app;
		context = type;
		try
		{
			setMnemonic(mnemonic);
		}
		catch (InvalidVkException e)
		{
			log.log(Level.WARNING, "Invalid mnemonic", e);
		}
		try
		{
			setAccelerator(accelerator);
		}
		catch (InvalidVkException e)
		{
			log.log(Level.WARNING, "Invalid accelerator", e);
		}
	}

	/**
	 * Set the icon for this action.
	 * 
	 * @param icon
	 */
	protected void setIcon(Icon icon)
	{
		putValue(SMALL_ICON, icon);
	}

	protected Icon getIcon()
	{
		return (Icon) getValue(SMALL_ICON);
	}

	int getMnemonic()
	{
		return mnemonic;
	}

	void setMnemonic(int mnemonic)
	{
		this.mnemonic = mnemonic;
	}

	void setMnemonic(String mnemonic) throws InvalidVkException
	{
		if (mnemonic != null && mnemonic.length() > 0)
		{
			setMnemonic(mnemonic.charAt(0));
		}
	}

	public ActionContext getContext()
	{
		return context;
	}

	KeyStroke getAccelerator()
	{
		return accelerator;
	}

	void setAccelerator(KeyStroke accelerator)
	{
		this.accelerator = accelerator;
	}

	void setAccelerator(String accelerator) throws InvalidVkException
	{
		if (accelerator != null && accelerator.length() > 0)
		{
			setAccelerator(KeyStroke.getKeyStroke(findKeyEventVk(accelerator), KeyEvent.CTRL_MASK));
		}
	}

	private int findKeyEventVk(String key) throws InvalidVkException
	{
		if (!key.startsWith("VK_"))
		{
			key = "VK_" + key;
		}
		try
		{
			Field field = KeyEvent.class.getDeclaredField(key.toUpperCase());
			return field.getInt(KeyEvent.class);
		}
		catch (NoSuchFieldException e)
		{
			throw new InvalidVkException("Unable to match mnemonic to a field in KeyEvent", e);
		}
		catch (IllegalAccessException e)
		{
			throw new InvalidVkException(e.getMessage(), e);
		}
	}
}
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Action which closes the currently followed file.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 * @author <a href="mailto:murali_ca_us@hotmail.com">Murali Krishnan</a>
 */
public class ExternalEditor extends Object
{
	private Logger log = Logger.getLogger(ExternalEditor.class.getName());
	// ------------------------------------------------------------
	// - Constructor

	public ExternalEditor(String string)
	{
		cmdString = string;
	}

	// ------------------------------------------------------------
	// - Member Variables

	private String cmdString = "";

	// ------------------------------------------------------------
	// - Accessors

	protected String getCmdString()
	{
		return (cmdString);
	}

	// ------------------------------------------------------------
	// - Internal Utilities.

	public String[] toCmdArray(String file)
	{
		String string = (getCmdString() == null) ? "" : getCmdString().trim();
		String[] result = new String[0]; // Pessimistic.

		if (!string.equals(""))
		{
			string = string + " "; // space terminate the last part.
			List<String> list = new ArrayList<String>();
			boolean inQuoteSingle = false;
			boolean inQuoteDouble = false;
			boolean inWhitespace = false;
			StringBuffer buffer = new StringBuffer();
			char[] chArray = string.toCharArray();
			for (int i = 0; i < chArray.length; i++)
			{
				char ch = chArray[i];
				if (inQuoteSingle)
				{
					if (ch == '\'')
					{
						inQuoteSingle = false;
					}
					else
					{
						buffer.append(ch);
					}
				}
				else if (inQuoteDouble)
				{
					if (ch == '"')
					{
						inQuoteDouble = false;
					}
					else
					{
						buffer.append(ch);
					}
				}
				else if (inWhitespace)
				{
					if (!Character.isWhitespace(ch))
					{
						inWhitespace = false;
						--i; // Re-process this character.
					}
				}
				else
				{
					if (ch == '\'')
					{
						inQuoteSingle = true;
					}
					else if (ch == '"')
					{
						inQuoteDouble = true;
					}
					else if (Character.isWhitespace(ch))
					{
						inWhitespace = true;
						list.add(buffer.toString());
						buffer = new StringBuffer();
					}
					else
					{
						buffer.append(ch);
					}
				}
			}

			list.add(file);

			result = (String[]) list.toArray(result);
		}

		return (result);
	}

	// ------------------------------------------------------------
	// - Public API

	public void exec(File file)
	{
		String fullPath = file.getAbsolutePath();
		String[] cmd = toCmdArray(fullPath);
		log.info("Exec'ing " + Arrays.asList(cmd) + ".");

		try
		{
			Runtime.getRuntime().exec(cmd);
		}
		catch (IOException ioe)
		{
			String errmsg = "Could not exec [" + getCmdString() + "] with [" + fullPath + "].";
			log.log(Level.SEVERE, errmsg, ioe);
		}
	}
}
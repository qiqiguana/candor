/*
 * ConfigurableMessageWriter.java
 * Copyright 2008-2009 Patrick Meade
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.nekomud;

import java.io.PrintWriter;

/**
 * ConfigurableMessageWriter holds the message that it will write to the
 * PrintWriter object.
 * @author patrick
 */
public class ConfigurableMessageWriter implements MessageWriter
{
	public ConfigurableMessageWriter(String message) {
		this.message = message;
	}

	public void writeMessage(PrintWriter printWriter)
	{
		printWriter.print(message);
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	private String message;
}

/*
 * Created on 20-Jun-2007
 * Copyright (C) 2007 by Andrea Vacondio.
 *
 *
 * This library is provided under dual licenses.
 * You may choose the terms of the Lesser General Public License version 2.1 or the General Public License version 2
 * License at your discretion.
 * 
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * 
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; 
 * either version 2 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, write to the Free Software Foundation, Inc., 
 *  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.pdfsam.console.exceptions.console;

import org.pdfsam.console.exceptions.BasicPdfsamException;

/**
 * Generic console Exception
 * @author Andrea Vacondio
 */
public class ConsoleException extends BasicPdfsamException {

	public static final int ERR_ZERO_LENGTH = 0x01;
	public static final int CMD_LINE_HANDLER_NULL = 0x02;
	public static final int EMPTY_FILENAME = 0x03;
	public static final int CMD_LINE_VALIDATOR_NULL = 0x04;
	public static final int ERR_BAD_COMMAND = 0x05;
	public static final int CMD_LINE_EXECUTOR_NULL = 0x06;
	public static final int CMD_LINE_NULL = 0x07;
	public static final int PREFIX_REQUEST_NULL = 0x08;
	public static final int UNABLE_TO_RENAME = 0x09;
	public static final int UNABLE_TO_OVERWRITE = 0x0A;
	public static final int OVERWRITE_IS_FALSE = 0x0B;
	
	private static final long serialVersionUID = -853792961862291208L;

	public ConsoleException(final int exceptionErrorCode, final String[] args, final Throwable e) {
		super(exceptionErrorCode, args, e);
	}

	public ConsoleException(final int exceptionErrorCode, final Throwable e) {
		super(exceptionErrorCode, e);
	}

	public ConsoleException(final int exceptionErrorCode) {
		super(exceptionErrorCode);
	}

	public ConsoleException(final Throwable e) {
		super(e);
	}

	public ConsoleException(final int exceptionErrorCode, final String[] args) {
		super(exceptionErrorCode, args);
	}

}

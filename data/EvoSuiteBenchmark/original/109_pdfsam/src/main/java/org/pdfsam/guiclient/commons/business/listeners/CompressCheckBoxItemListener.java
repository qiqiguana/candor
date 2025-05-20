/*
 * Created on 20-Dec-2007
 * Copyright (C) 2007 by Andrea Vacondio.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; 
 * either version 2 of the License.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, write to the Free Software Foundation, Inc., 
 *  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.pdfsam.guiclient.commons.business.listeners;

import org.pdfsam.console.business.dto.commands.AbstractParsedCommand;
import org.pdfsam.guiclient.commons.components.JPdfVersionCombo;
/**
 * ItemListener used to disable or enable items in JPdfVersionCombo
 * @author Andrea Vacondio
 *
 */
public class CompressCheckBoxItemListener extends VersionFilterCheckBoxItemListener {

	private static Integer versionFilter = new Integer(""+AbstractParsedCommand.VERSION_1_5);
	
	/**
	 * @param versionCombo version Combo 
	 */
	public CompressCheckBoxItemListener(JPdfVersionCombo versionCombo) {
		super(versionCombo, versionFilter);
	}
}

/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.jdbacl.swing;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.databene.commons.SystemInfo;

/**
 * {@link JComboBox} which recognizes the configured environments and lets the user choose one.<br/><br/>
 * Created: 06.11.2011 19:26:37
 * @since 0.7.0
 * @author Volker Bergmann
 */
@SuppressWarnings("serial")
public class EnvironmentSelector extends JComboBox {

	public EnvironmentSelector() {
		super(new EnvironmentModel());
	}
	
	@Override
	public String getSelectedItem() {
		return (String) super.getSelectedItem();
	}
	
	public static class EnvironmentModel extends AbstractListModel implements ComboBoxModel {

		private String selectedItem;
		private List<String> environments;
		
		EnvironmentModel() {
			File databeneFolder = new File(SystemInfo.getUserHome(), "databene");
			String[] fileNames = databeneFolder.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return (name.toLowerCase().endsWith(".env.properties"));
				}
			});
			environments = new ArrayList<String>(fileNames.length); 
			for (String fileName : fileNames)
				environments.add(fileName.substring(0, fileName.length() - ".env.properties".length()));
		}

		public Object getElementAt(int index) {
			return environments.get(index);
		}

		public int getSize() {
			return environments.size();
		}

		public Object getSelectedItem() {
			return selectedItem;
		}

		public void setSelectedItem(Object item) {
			this.selectedItem = (String) item;
		}

	}

}

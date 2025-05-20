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

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;

import org.databene.commons.ConnectFailedException;
import org.databene.commons.ImportFailedException;
import org.databene.commons.ui.swing.SwingTreeModelAdapter;
import org.databene.jdbacl.DatabaseTreeModel;
import org.databene.jdbacl.model.DBMetaDataImporter;
import org.databene.jdbacl.model.DBObject;
import org.databene.jdbacl.model.Database;
import org.databene.jdbacl.model.cache.CachingDBImporter;
import org.databene.jdbacl.model.jdbc.JDBCDBImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link JPanel} which displays database information and the hierarchy tree.<br/><br/>
 * Created: 07.11.2011 16:46:33
 * @since 0.7.0
 * @author Volker Bergmann
 */
@SuppressWarnings("serial")
public class DatabasePane extends JPanel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabasePane.class);
	
	private JScrollPane scrollPane;
	private DatabaseTree tree;
	private DBMetaDataImporter importer;
	private TextFieldValueProvider exclusionPatternProvider;

	public DatabasePane(TextFieldValueProvider exclusionPatternProvider) {
		super(new BorderLayout());
		this.exclusionPatternProvider = exclusionPatternProvider;
		this.scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void setEnvironment(String environment) throws ConnectFailedException, ImportFailedException {
		if (importer != null) {
			try {
				importer.close();
			} catch (IOException e) {
				LOGGER.error("Error closing " + getClass().getName(), e);
			}
			if (tree != null)
				scrollPane.remove(tree);
		}
		new Thread(new Importer(environment)).start();
	}

	class Importer implements Runnable {
		String environment;
		public Importer(String environment) {
			this.environment = environment;
		}
		public void run() {
			try {
				JDBCDBImporter parser = new JDBCDBImporter(environment);
				parser.setExcludeTables(exclusionPatternProvider.getValue());
				parser.setImportingChecks(true);
				parser.setImportingIndexes(true);
				parser.setImportingSequences(true);
				parser.setImportingTriggers(true);
				parser.setImportingPackages(true);
				parser.setImportingUKs(true);
				CachingDBImporter importer = new CachingDBImporter(parser, environment);
				Database database = importer.importDatabase();
				DatabasePane.this.importer = importer;
				final TreeModel model = new SwingTreeModelAdapter<DBObject>(new DatabaseTreeModel(database));
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						tree = new DatabaseTree(model);
						scrollPane.setViewportView(tree);
					}
				});
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}

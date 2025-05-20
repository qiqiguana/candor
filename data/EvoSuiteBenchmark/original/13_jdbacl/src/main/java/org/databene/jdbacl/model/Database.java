/*
 * (c) Copyright 2006-2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
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

package org.databene.jdbacl.model;

import java.util.Date;
import java.util.List;

import org.databene.commons.version.VersionNumber;

/**
 * Represents a database.<br/><br/>
 * Created: 06.01.2007 18:34:20
 * @author Volker Bergmann
 */
public interface Database extends CompositeDBObject<DBCatalog>, TableHolder, SequenceHolder {
	
	public String getDatabaseProductName();
	public VersionNumber getDatabaseProductVersion();
	public Date getImportDate();
	public String getUser();
	public String getTableInclusionPattern();
	public String getTableExclusionPattern();
	public boolean isImportedChecks();
	public boolean isImportedUKs();
	public boolean isImportedIndexes();
	public boolean isImportedSequences();
	
    public List<DBCatalog> getCatalogs();
    public DBCatalog getCatalog(String catalogName);
    public void addCatalog(DBCatalog catalog);
    public void removeCatalog(DBCatalog catalog);

	public DBSchema getSchema(String schemaName);

	public List<DBTable> getTables();
    public DBTable getTable(String name);
    public DBTable getTable(String name, boolean required);
	public void removeTable(String tableName);

	public List<DBSequence> getSequences();
	public List<DBTrigger> getTriggers();
	public List<DBPackage> getPackages();
}

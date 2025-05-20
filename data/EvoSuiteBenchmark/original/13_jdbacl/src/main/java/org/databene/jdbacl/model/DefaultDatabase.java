/*
 * (c) Copyright 2010-2011 by Volker Bergmann. All rights reserved.
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

package org.databene.jdbacl.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.databene.commons.ObjectNotFoundException;
import org.databene.commons.StringUtil;
import org.databene.commons.collection.OrderedNameMap;
import org.databene.commons.version.VersionNumber;

/**
 * Default implementation of the {@link Database} interface.<br/><br/>
 * Created: 05.12.2010 16:18:57
 * @since 0.6.4
 * @author Volker Bergmann
 */
public class DefaultDatabase extends AbstractCompositeDBObject<DBCatalog> implements Database {
	
	private static final long serialVersionUID = -1873203097942961523L;
	
	private String productName;
	private VersionNumber productVersion;
	private Date importDate;
	private String user;
	private String tableInclusionPattern;
	private String tableExclusionPattern;
	private boolean importedChecks;
	private boolean importedUKs;
	private boolean importedIndexes;
	private boolean importedSequences;
	
	private OrderedNameMap<DBCatalog> catalogs;
	
    // constructors ----------------------------------------------------------------------------------------------------

    public DefaultDatabase(String name, String productName, VersionNumber productVersion) {
        super(name, "database");
        this.productName = productName;
        this.productVersion = productVersion;
        this.catalogs = OrderedNameMap.createCaseInsensitiveMap();
    }
    
    public String getDatabaseProductName() {
    	return productName;
    }
    
    public VersionNumber getDatabaseProductVersion() {
    	return productVersion;
    }
    
	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTableInclusionPattern() {
		return tableInclusionPattern;
	}

	public void setTableInclusionPattern(String tableInclusionPattern) {
		this.tableInclusionPattern = tableInclusionPattern;
	}

	public String getTableExclusionPattern() {
		return tableExclusionPattern;
	}

	public void setTableExclusionPattern(String tableExclusionPattern) {
		this.tableExclusionPattern = tableExclusionPattern;
	}

	public boolean isImportedChecks() {
		return importedChecks;
	}

	public void setImportedChecks(boolean importedChecks) {
		this.importedChecks = importedChecks;
	}

	public boolean isImportedUKs() {
		return importedUKs;
	}

	public void setImportedUKs(boolean importedUKs) {
		this.importedUKs = importedUKs;
	}

	public boolean isImportedIndexes() {
		return importedIndexes;
	}

	public void setImportedIndexes(boolean importedIndexes) {
		this.importedIndexes = importedIndexes;
	}

	public boolean isImportedSequences() {
		return importedSequences;
	}

	public void setImportedSequences(boolean importedSequences) {
		this.importedSequences = importedSequences;
	}

    // CompositeDBObject implementation --------------------------------------------------------------------------------

	public List<DBCatalog> getComponents() {
		return catalogs.values();
	}
	
    // catalog operations ----------------------------------------------------------------------------------------------

    public List<DBCatalog> getCatalogs() {
        return getComponents();
    }

    public DBCatalog getCatalog(String catalogName) {
        return catalogs.get(catalogName);
    }

    public void addCatalog(DBCatalog catalog) {
        catalog.setDatabase(this);
        catalogs.put(catalog.getName(), catalog);
    }

    public void removeCatalog(DBCatalog catalog) {
        catalogs.remove(catalog.getName());
        catalog.setOwner(null);
    }

    // schema operations -----------------------------------------------------------------------------------------------

	public DBSchema getSchema(String schemaName) {
        for (DBCatalog catalog : getCatalogs()) {
            DBSchema schema = catalog.getSchema(schemaName);
            if (schema != null)
            	return schema;
        }
        throw new ObjectNotFoundException("Table '" + name + "'");
	}

    // table operations ------------------------------------------------------------------------------------------------

	public List<DBTable> getTables() {
		return getTables(true);
	}

	public List<DBTable> getTables(boolean recursive) {
		if (!recursive)
			return new ArrayList<DBTable>();
    	List<DBTable> tables = new ArrayList<DBTable>();
        for (DBCatalog catalog : getCatalogs())
            for (DBTable table : catalog.getTables())
            	tables.add(table);
        return tables;
    }

	public DBTable getTable(String name) {
		return getTable(name, true);
	}

    public DBTable getTable(String name, boolean required) {
        for (DBCatalog catalog : getCatalogs())
            for (DBTable table : catalog.getTables())
            	if (StringUtil.equalsIgnoreCase(table.getName(), name))
            		return table;
        if (required)
        	throw new ObjectNotFoundException("Table '" + name + "'");
        else
        	return null;
    }
    
	public void removeTable(String tableName) {
		DBTable table = getTable(tableName, true);
		table.getSchema().removeTable(table);
    }

	public List<DBSequence> getSequences() {
		return getSequences(true);
	}

	public List<DBSequence> getSequences(boolean recursive) {
		if (!recursive)
			return new ArrayList<DBSequence>();
    	List<DBSequence> sequences = new ArrayList<DBSequence>();
        for (DBCatalog catalog : getCatalogs())
            for (DBSequence table : catalog.getSequences())
            	sequences.add(table);
        return sequences;
	}

	public List<DBTrigger> getTriggers() {
    	List<DBTrigger> triggers = new ArrayList<DBTrigger>();
        for (DBCatalog catalog : getCatalogs())
            for (DBSchema schema : catalog.getSchemas())
            	triggers.addAll(schema.getTriggers());
        return triggers;
	}

	public List<DBPackage> getPackages() {
    	List<DBPackage> packages = new ArrayList<DBPackage>();
        for (DBCatalog catalog : getCatalogs())
            for (DBSchema schema : catalog.getSchemas())
            	packages.addAll(schema.getPackages());
        return packages;
	}

}
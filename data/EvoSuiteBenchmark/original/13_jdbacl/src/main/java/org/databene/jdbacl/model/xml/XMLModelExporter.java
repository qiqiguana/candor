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

package org.databene.jdbacl.model.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.databene.commons.Encodings;
import org.databene.commons.IOUtil;
import org.databene.commons.xml.SimpleXMLWriter;
import org.databene.jdbacl.SQLUtil;
import org.databene.jdbacl.model.DBPackage;
import org.databene.jdbacl.model.DBProcedure;
import org.databene.jdbacl.model.DBTrigger;
import org.databene.jdbacl.model.FKChangeRule;
import org.databene.jdbacl.model.DBCatalog;
import org.databene.jdbacl.model.DBCheckConstraint;
import org.databene.jdbacl.model.DBColumn;
import org.databene.jdbacl.model.DBForeignKeyConstraint;
import org.databene.jdbacl.model.DBIndex;
import org.databene.jdbacl.model.DBMetaDataExporter;
import org.databene.jdbacl.model.DBPrimaryKeyConstraint;
import org.databene.jdbacl.model.DBSchema;
import org.databene.jdbacl.model.DBSequence;
import org.databene.jdbacl.model.DBTable;
import org.databene.jdbacl.model.DBUniqueConstraint;
import org.databene.jdbacl.model.Database;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import static org.databene.commons.xml.SimpleXMLWriter.*;

/**
 * Saves a database meta data model as XML file.<br/><br/>
 * Created: 28.11.2010 06:30:25
 * @since 0.6.4
 * @author Volker Bergmann
 */
public class XMLModelExporter implements DBMetaDataExporter {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String encoding;
	private File file;
	
	// constructors ----------------------------------------------------------------------------------------------------
	
	public XMLModelExporter(File file) {
		this(file, Encodings.UTF_8);
	}

	public XMLModelExporter(File file, String encoding) {
		this.file = file;
		this.encoding = encoding;
	}
	
	// interface -------------------------------------------------------------------------------------------------------

	public void export(Database database) throws IOException, SQLException {
		OutputStream out = new FileOutputStream(file);
		SimpleXMLWriter writer = null;
		try {
			writer = new SimpleXMLWriter(out, encoding, true);
			writer.startDocument();
			exportDatabase(database, writer);
			writer.endDocument();
		} catch (SAXException e) {
			throw new RuntimeException("Error exporting database " + database, e);
		} finally {
			IOUtil.close(writer);
		}
	}
	
	// private helper methods ------------------------------------------------------------------------------------------

	private void exportDatabase(Database database, SimpleXMLWriter writer)
			throws SAXException {
		AttributesImpl attribs = createAttributes("name", database.getName());
		addAttribute("databaseProductName", database.getDatabaseProductName(), attribs);
		addAttribute("databaseProductVersion", database.getDatabaseProductVersion().toString(), attribs);
		addAttribute("importDate", sdf.format(database.getImportDate()), attribs);
		addAttribute("user", database.getUser(), attribs);
		addAttribute("tableInclusionPattern", database.getTableInclusionPattern(), attribs);
		addAttribute("tableExclusionPattern", database.getTableExclusionPattern(), attribs);
		addAttribute("importedChecks", String.valueOf(database.isImportedChecks()), attribs);
		addAttribute("importedUKs", String.valueOf(database.isImportedUKs()), attribs);
		addAttribute("importedIndexes", String.valueOf(database.isImportedIndexes()), attribs);
		addAttribute("importedSequences", String.valueOf(database.isImportedSequences()), attribs);
		writer.startElement("database", attribs);
		for (DBCatalog catalog : database.getCatalogs())
			exportCatalog(catalog, writer);
		writer.endElement("database");
	}

	private void exportCatalog(DBCatalog catalog, SimpleXMLWriter writer) throws SAXException {
		writer.startElement("catalog", createAttributes("name", catalog.getName()));
		for (DBSchema schema : catalog.getSchemas())
			exportSchema(schema, writer);
		writer.endElement("catalog");
	}

	private void exportSchema(DBSchema schema, SimpleXMLWriter writer) throws SAXException {
		AttributesImpl atts = createAttributes("name", schema.getName());
		writer.startElement("schema", atts);
		for (DBTable table : schema.getTables())
			exportTable(table, writer);
		for (DBSequence sequence : schema.getSequences(true))
			exportSequence(sequence, writer);
		for (DBTrigger trigger : schema.getTriggers())
			exportTrigger(trigger, writer);
		for (DBPackage pkg : schema.getPackages())
			exportPackage(pkg, writer);
		writer.endElement("schema");
	}

	private void exportTable(DBTable table, SimpleXMLWriter writer) throws SAXException {
		AttributesImpl atts = createAttributes("name", table.getName());
		writer.startElement("table", atts);
		for (DBColumn column : table.getColumns())
			exportColumn(column, writer);
		DBPrimaryKeyConstraint pk = table.getPrimaryKeyConstraint();
		if (pk != null)
			exportPK(pk, writer);
		exportFks(table.getForeignKeyConstraints(), writer);
		exportUKs(table.getUniqueConstraints(false), writer);
		exportChecks(table.getCheckConstraints(), writer);
		exportIndexes(table.getIndexes(), writer);
		writer.endElement("table");
	}

	private void exportColumn(DBColumn column, SimpleXMLWriter writer) throws SAXException {
		AttributesImpl atts = createAttributes("name", column.getName());
		addAttribute("default", column.getDefaultValue(), atts);
		addAttribute("jdbcType", String.valueOf(column.getType().getJdbcType()), atts);
		addAttribute("type", SQLUtil.renderColumnTypeWithSize(column), atts);
		addAttribute("nullable", (column.isNullable() ? null : "false"), atts);
		writer.startElement("column", atts);
		writer.endElement("column");
	}

	private void exportPK(DBPrimaryKeyConstraint pk, SimpleXMLWriter writer) throws SAXException {
		AttributesImpl pkAtts = createAttributes("name", pk.getName());
		String[] pkColumnNames = pk.getColumnNames();
		if (pkColumnNames.length == 1)
			addAttribute("column", pkColumnNames[0], pkAtts);
		writer.startElement("pk", pkAtts);
		if (pkColumnNames.length > 1)
			writeColumnGroup(pkColumnNames, writer);
		writer.endElement("pk");
	}

	private void exportUKs(Set<DBUniqueConstraint> uks, SimpleXMLWriter writer) 
			throws SAXException {
		for (DBUniqueConstraint uk : uks) {
			if (uk instanceof DBPrimaryKeyConstraint)
				continue;
			AttributesImpl atts = createAttributes("name", uk.getName());
			String[] columnNames = uk.getColumnNames();
			if (columnNames.length == 1)
				addAttribute("column", columnNames[0], atts);
			writer.startElement("uk", atts);
			if (columnNames.length > 1)
				writeColumnGroup(columnNames, writer);
			writer.endElement("uk");
		}
	}

	private void exportChecks(List<DBCheckConstraint> checks, SimpleXMLWriter writer) 
			throws SAXException {
		for (DBCheckConstraint check : checks) {
			AttributesImpl atts = createAttributes("name", check.getName());
			addAttribute("definition", check.getConditionText(), atts);
			writer.startElement("check", atts);
			writer.endElement("check");
		}
	}

	private void exportFks(Set<DBForeignKeyConstraint> fks, SimpleXMLWriter writer) 
			throws SAXException {
		for (DBForeignKeyConstraint fk : fks) {
			AttributesImpl atts = createAttributes("name", fk.getName());
			String[] columnNames = fk.getColumnNames();
			if (columnNames.length == 1)
				addAttribute("column", columnNames[0], atts);
			addAttribute("refereeTable", fk.getRefereeTable().getName(), atts);
			String[] refereeColumns = fk.getRefereeColumnNames();
			if (refereeColumns.length == 1)
				addAttribute("refereeColumn", refereeColumns[0], atts);
			if (fk.getUpdateRule() != FKChangeRule.NO_ACTION)
				addAttribute("updateRule", fk.getUpdateRule().name(), atts);
			if (fk.getDeleteRule() != FKChangeRule.NO_ACTION)
				addAttribute("deleteRule", fk.getDeleteRule().name(), atts);
			writer.startElement("fk", atts);
			if (columnNames.length > 1) {
				writer.startElement("columns");
				for (String columnName : columnNames) {
					AttributesImpl colAtts = createAttributes("name", columnName);
					addAttribute("refereeColumn", fk.columnReferencedBy(columnName), colAtts);
					writer.startElement("column", colAtts);
					writer.endElement("column");
				}
				writer.endElement("columns");
			}
			writer.endElement("fk");
		}
	}

	private void exportIndexes(List<DBIndex> indexes, SimpleXMLWriter writer) throws SAXException {
		for (DBIndex index : indexes) {
			AttributesImpl atts = createAttributes("name", index.getName());
			addAttribute("unique", (index.isUnique() ? "true" : null), atts);
			addAttribute("nameDeterministic", (index.isNameDeterministic() ? null : "false"), atts);
			String[] columnNames = index.getColumnNames();
			if (columnNames.length == 1)
				addAttribute("column", columnNames[0], atts);
			writer.startElement("index", atts);
			if (columnNames.length > 1)
				writeColumnGroup(columnNames, writer);
			writer.endElement("index");
		}
	}

	public void writeColumnGroup(String[] pkColumnNames, SimpleXMLWriter writer) throws SAXException {
		writer.startElement("columns");
		for (String pkColumnName : pkColumnNames) {
			AttributesImpl colAtts = createAttributes("name", pkColumnName);
			writer.startElement("column", colAtts);
			writer.endElement("column");
		}
		writer.endElement("columns");
	}

	private void exportSequence(DBSequence sequence, SimpleXMLWriter writer) throws SAXException {
		AttributesImpl atts = createAttributes("name", sequence.getName());
		addIfNotNull("start", sequence.getStartIfNotDefault(), atts);
		addIfNotNull("increment", sequence.getIncrementIfNotDefault(), atts);
		addIfNotNull("maxValue", sequence.getMaxValueIfNotDefault(), atts);
		addIfNotNull("minValue", sequence.getMinValueIfNotDefault(), atts);
		addIfNotNull("cycle", sequence.isCycle(), atts);
		addIfNotNull("cache", sequence.getCache(), atts);
		addIfNotNull("order", sequence.isOrder(), atts);
		writer.startElement("sequence", atts);
		writer.endElement("sequence");
	}

	private void exportTrigger(DBTrigger trigger, SimpleXMLWriter writer) throws SAXException {
		AttributesImpl atts = createAttributes("name", trigger.getName());
		addIfNotNull("triggerType", trigger.getTriggerType(), atts);
		addIfNotNull("triggeringEvent", trigger.getTriggeringEvent(), atts);
		addIfNotNull("tableOwner", trigger.getTableOwner(), atts);
		addIfNotNull("baseObjectType", trigger.getBaseObjectType(), atts);
		addIfNotNull("tableName", trigger.getTableName(), atts);
		addIfNotNull("columnName", trigger.getColumnName(), atts);
		addIfNotNull("referencingNames", trigger.getReferencingNames(), atts);
		addIfNotNull("whenClause", trigger.getWhenClause(), atts);
		addIfNotNull("status", trigger.getStatus(), atts);
		addIfNotNull("description", trigger.getDescription(), atts);
		addIfNotNull("actionType", trigger.getActionType(), atts);
		addIfNotNull("triggerBody", trigger.getTriggerBody(), atts);
		writer.startElement("trigger", atts);
		writer.endElement("trigger");
	}

	private void exportPackage(DBPackage pkg, SimpleXMLWriter writer) throws SAXException {
		AttributesImpl atts = createAttributes("name", pkg.getName());
		addIfNotNull("subObjectName", pkg.getSubObjectName(), atts);
		addIfNotNull("objectId", pkg.getObjectId(), atts);
		addIfNotNull("dataObjectId", pkg.getDataObjectId(), atts);
		addIfNotNull("objectType", pkg.getObjectType(), atts);
		addIfNotNull("status", pkg.getStatus(), atts);
		writer.startElement("package", atts);
		exportPackageProcedures(pkg, writer);
		writer.endElement("package");
	}

	private void exportPackageProcedures(DBPackage pkg, SimpleXMLWriter writer) throws SAXException {
		for (DBProcedure procedure : pkg.getProcedures()) {
			AttributesImpl atts = createAttributes("name", procedure.getName());
			addIfNotNull("objectId", procedure.getObjectId(), atts);
			addIfNotNull("subProgramId", procedure.getSubProgramId(), atts);
			addIfNotNull("overload", procedure.getOverload(), atts);
			writer.startElement("procedure", atts);
			writer.endElement("procedure");
		}
	}

	private void addIfNotNull(String name, Object value, AttributesImpl atts) {
		if (value != null)
			addAttribute(name, value.toString(), atts);
	}

}

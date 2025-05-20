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

package org.databene.jdbacl.model;

import org.databene.commons.NullSafeComparator;
import org.databene.commons.anno.Nullable;

/**
 * Represents a database trigger.<br/><br/>
 * Created: 07.11.2011 14:46:14
 * @since 0.7.0
 * @author Volker Bergmann
 */
public class DBTrigger extends AbstractDBObject implements ContainerComponent {

	private static final long serialVersionUID = -183721433730785529L;
	
	private String triggerType; // "after statement", "before statement", "before each row"
	private String triggeringEvent; // "insert or update or delete"
	private String tableOwner;
	private String baseObjectType;
	private String tableName;
	private @Nullable String columnName;
	private String referencingNames;
	private @Nullable String whenClause;
	private String status;
	private String description;
	private String actionType;
	private String triggerBody;
	

	public DBTrigger(String name, DBSchema owner) {
		super(name, "trigger", owner);
	}
	
	@Override
	public void setOwner(CompositeDBObject<?> owner) {
		super.setOwner(owner);
        if (owner != null) {
    		DBSchema schema = (DBSchema) owner;
    		schema.addTrigger(this);
        }
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getTriggeringEvent() {
		return triggeringEvent;
	}

	public void setTriggeringEvent(String triggeringEvent) {
		this.triggeringEvent = triggeringEvent;
	}

	public String getTableOwner() {
		return tableOwner;
	}

	public void setTableOwner(String tableOwner) {
		this.tableOwner = tableOwner;
	}

	public String getBaseObjectType() {
		return baseObjectType;
	}

	public void setBaseObjectType(String baseObjectType) {
		this.baseObjectType = baseObjectType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getReferencingNames() {
		return referencingNames;
	}

	public void setReferencingNames(String referencingNames) {
		this.referencingNames = referencingNames;
	}

	public String getWhenClause() {
		return whenClause;
	}

	public void setWhenClause(String whenClause) {
		this.whenClause = whenClause;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getTriggerBody() {
		return triggerBody;
	}

	public void setTriggerBody(String triggerBody) {
		this.triggerBody = triggerBody;
	}
	
	/**
	 * ignores description
	 */
	public boolean isIdentical(DBObject object) {
		if (!(object instanceof DBTrigger))
			return false;
		DBTrigger that = (DBTrigger) object;
		return this.triggerType.equals(that.triggerType)
			&& this.triggeringEvent.equals(that.triggeringEvent)
			&& this.baseObjectType.equals(that.baseObjectType)
			&& NullSafeComparator.equals(this.tableName, that.tableName)
			&& NullSafeComparator.equals(this.columnName, that.columnName)
			&& NullSafeComparator.equals(this.referencingNames, that.referencingNames)
			&& NullSafeComparator.equals(this.whenClause, that.whenClause)
			&& this.status.equals(that.status)
			&& this.actionType.equals(that.actionType)
			&& this.triggerBody.trim().equals(that.triggerBody.trim());
	}

}

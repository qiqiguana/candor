package macaw.system;

import java.util.Date;

/**
 * <p></p>
 * <hr>
 * Copyright 2010 Medical Research Council Unit for Lifelong Health and Ageing
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  
 * <hr>
 * @author Kevin Garwood (kgarwood@users.sourceforge.net)
 * @version 1.0	
 */

/*
 * Code Road Map:
 * --------------
 * Code is organised into the following sections.  Wherever possible, 
 * methods are classified based on an order of precedence described in 
 * parentheses (..).  For example, if you're trying to find a method 
 * 'getName(...)' that is both an interface method and an accessor 
 * method, the order tells you it should appear under interface.
 * 
 * Order of 
 * Precedence     Section
 * ==========     ======
 * (1)            Section Constants
 * (2)            Section Properties
 * (3)            Section Construction
 * (7)            Section Accessors and Mutators
 * (6)            Section Errors and Validation
 * (5)            Section Interfaces
 * (4)            Section Overload
 *
 */

public class MacawChangeEvent {
	
	// ==========================================
	// Section Constants
	// ==========================================
		
	// ==========================================
	// Section Properties
	// ==========================================
	private ChangeEventType changeType;
	private int changedObjectIdentifier;
	private Integer variableOwnerID;
	private String changeMessage;
	private String userID;
	private Date date;
	
	// ==========================================
	// Section Construction
	// ==========================================
	
	public MacawChangeEvent() {
		changeMessage = "";
		userID = "";
		changeType = ChangeEventType.VARIABLE;
		date = new Date(System.currentTimeMillis());
	}
	
	public MacawChangeEvent(ChangeEventType changeType,
							String changeMessage,
							String userID) {

		this.changeMessage = changeMessage;
		this.userID = userID;
		variableOwnerID = null;
		this.changeType = changeType;
		date = new Date();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public String getChangeMessage() {
		return changeMessage;
	}

	public void setChangeMessage(String changeMessage) {
		this.changeMessage = changeMessage;
	}
	
	public Integer getVariableOwnerID() {
		return variableOwnerID;
	}
	
	public void setVariableOwnerID(int identifier) {
		this.variableOwnerID = new Integer(identifier);
	}
	
	public void setChangedObjectIdentifier(int changedObjectIdentifier) {
		this.changedObjectIdentifier = changedObjectIdentifier;
	}
	
	public int getChangedObjectIdentifier() {
		return changedObjectIdentifier;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public ChangeEventType getChangeType() {
		return changeType;
	}

	public void setChangeType(ChangeEventType changeType) {
		this.changeType = changeType;
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}


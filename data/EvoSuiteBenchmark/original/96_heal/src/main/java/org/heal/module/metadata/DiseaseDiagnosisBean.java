package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a disease diagnosis.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class DiseaseDiagnosisBean implements Serializable {
    // Properties
    private String diseaseDiagnosisId;
    private String metadataId;
    private String diseaseDiagnosis;

    /**
     * Returns the diseaseDiagnosisId property value.
     */
    public String getDiseaseDiagnosisId() {
	return this.diseaseDiagnosisId;
    }

    /**
     * Sets the diseaseDiagnosisId property value.
     */
    public void setDiseaseDiagnosisId(String newDiseaseDiagnosisId) {
	this.diseaseDiagnosisId = newDiseaseDiagnosisId;
    }

    /**
     * Returns the metadataId property value.
     */
    public String getMetadataId() {
	return this.metadataId;
    }

    /**
     * Sets the metadataId property value.
     */
    public void setMetadataId(String newMetadataId) {
	this.metadataId = newMetadataId;
    }

    /**
     * Returns the diseaseDiagnosis property value.
     */
    public String getDiseaseDiagnosis() {
	return this.diseaseDiagnosis;
    }

    /**
     * Sets the diseaseDiagnosis property value.
     */
    public void setDiseaseDiagnosis(String newDiseaseDiagnosis) {
	this.diseaseDiagnosis = newDiseaseDiagnosis;
    }

    public String toString() {
	return "DiseaseDiagnosis: ID:"+diseaseDiagnosisId+
	    " metadataId:"+metadataId+
	    " diseaseDiagnosis:"+diseaseDiagnosis;
    }

}






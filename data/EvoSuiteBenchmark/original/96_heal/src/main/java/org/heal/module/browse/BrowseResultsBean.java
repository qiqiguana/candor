package org.heal.module.browse;

import java.io.*;
import java.util.*;

/**
 * The class contains the browse results for a given ID.
 * Ex: Mesh ID = A01.X.X
 * @author (Jason Varghese)
 */


public class BrowseResultsBean implements Serializable {
    
    private String id = null;
    private Vector subCategories = null;
    private Vector shortMetadataBeans = null;
    private Vector trail = null;
    private int count = 0;
    private Vector resultsId = null;
    
    public String getId() {
        return this.id;  
    }
    
    public Vector getSubCategories() {
        return this.subCategories;
    }
    
    public Vector getResultsId() {
        return this.resultsId;
    }

    public Vector getShortMetadataBeans() {
        return this.shortMetadataBeans;  
    }

    public Vector getTrail() {
        return this.trail;  
    }

    public int getCount() {
        return this.count;  
    }

    public void setId(String id) {
        this.id= id;
    }
    
    public void setResultsId(Vector resultsId) {
        this.resultsId= resultsId;
    }
    
    public void setSubCategories(Vector subCategories) {
        this.subCategories= subCategories;
    }

    public void setTrail(Vector trail) {
        this.trail= trail;
    }

    public void setShortMetadataBeans(Vector smb) {
        this.shortMetadataBeans= smb;
    }

    public void setCount(int count) {
        this.count = count;
    }  
}






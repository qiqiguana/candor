package org.heal.module.browse;

import java.io.*;
import java.util.*;

/**
 * @author Jason Varghese
 * The class is used to hold the Taxonomy information for a particular record
 * Ex: categoryId = Mesh ID, categorName = Mesh topic
 */

public class CategoryBean implements Serializable {
    
    private String categoryName = "";
    private String categoryId = "";
    private int count = 0;

    public String getCategoryName() {
        return this.categoryName;  
    }
    
    public String getCategoryId() {
        return this.categoryId;
    }

    public int getCount() {
        return this.count;  
    }


    public void setCount(int count) {
        this.count = count;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName= categoryName;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}






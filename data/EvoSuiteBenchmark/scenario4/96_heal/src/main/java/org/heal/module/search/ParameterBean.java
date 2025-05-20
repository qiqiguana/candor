package org.heal.module.search;

import java.util.*;

public class ParameterBean {

    private ArrayList searchParameters = null;

    private ArrayList tableList = null;

    private String[] filterArray = null;

    private String[] sourceCollection = null;

    private String[] publicationNames = null;

    private String[] publicationIds = null;

    private String rights = null;

    private String[] primaryArray = null;

    private boolean hidden = false;

    private String[] imaging = null;

    private String[] disease = null;

    /**
     * Constructor
     */
    public ParameterBean() {
    }

    /**
     * New constructor
     */
    public ParameterBean(String keywordString) {
    }

    /**
     * Accessor method, returns the parameterNode at specified position
     *
     * @param position
     *
     * @return ParameterNode
     */
    public ParameterNode getParameters(int position);

    /**
     * Accessor method, returns the searchParameter size
     *
     * @return int
     */
    public int size();

    /**
     * return the filterArray
     *
     * @return
     */
    public String[] getFilterArray();

    /**
     * return the diseasePrecess array
     * @return disease
     */
    public String[] getDisease();

    /**
     * return the imaging technique Array
     * @return imaging
     */
    public String[] getImaging();

    /**
     * return the Source collection Array
     *
     * @return sourceCollection
     */
    public String[] getSourceCollection();

    /**
     * return the usage rights array
     *
     * @return rights
     */
    public String getUsageRight();

    /**
     * return the primary audience Array
     *
     * @return primary
     */
    public String[] getPrimaryArray();

    /**
     * Return the value for hidden
     *
     * @return
     */
    public boolean getHidden();

    /**
     * Adds parameterNode into the ArrayList
     *
     * @param input
     */
    public void addParameters(ParameterNode input);

    /**
     * Adds filter array into the parameterBean
     *
     * @param filter
     */
    public void setFilterArray(String[] filter);

    /**
     * Adds source collection array into the parameterBean
     *
     * @param source
     */
    public void setSourceCollection(String[] source);

    /**
     * Adds usage rights array into the parameterBean
     *
     * @param rts
     */
    public void setUsageRights(String rts);

    /**
     * Adds primary audience array into the parameterBean
     *
     * @param primary
     */
    public void setPrimaryArray(String[] primary);

    /**
     * Adds imaging technique array into the parameterBean
     * @param source
     */
    public void setImaging(String[] imgs);

    /**
     * Adds disease precess array into the parameterBean
     * @param rts
     */
    public void setDisease(String[] dis);

    /**
     * sets the value for hidden
     *
     * @param value
     */
    public void setHidden(boolean value);

    /**
     * This is a wrapper that calls for functions to parse the table name.
     * If the tableList is empty, it calls for function to create the tableList,
     * else it returns the tableList as ArrayList.
     *
     * @return ArrayList of tableName
     */
    public ArrayList getTableList();

    /**
     * Checks if the table name already exists in the tableList. If not, adds the
     * table name into the tableList.
     *
     * @param table
     *
     * @return boolean
     */
    private boolean tableNotFound(String table);

    /**
     * main function to create the tableList. Parses through the searchParameter
     * one by one and checks for the table name.
     */
    private void makeTableList();

    public void setPublicationNames(String[] names);

    public String[] getPublicationNames();

    public void setPublicationIds(String[] publicationIds);

    public String[] getPublicationIds();

    public ParameterBean makeParameterBean(String keywordString);
}

package org.heal.module.search;

import java.util.*;

/**
 * This ParameterBean class stores an ArrayList of searchParameters and
 * tableList The search Parameters stores a array of ParameterNode that contains
 * information on user input in the format of columnInfo, value and relation to
 * next ParameterNode.
 * The tableList stores a list of tables that are needed to form the query. It
 * is parsed from the searchParameters.
 *
 * @author Julie Zhu
 *         Modify by Grace: Added String[] sourceCollection, rights and PrimaryArray
 *         Modified by Julie: Added Boolean variable for Hidden;
 */

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
        searchParameters = new ArrayList();
        tableList = new ArrayList();
    }
  /**
     * New constructor
     */
  public ParameterBean(String keywordString)
  {
    searchParameters = new ArrayList();
    tableList = new ArrayList();
    makeParameterBean(keywordString);
  }
    /**
     * Accessor method, returns the parameterNode at specified position
     *
     * @param position
     *
     * @return ParameterNode
     */
    public ParameterNode getParameters(int position) {
        return (ParameterNode)searchParameters.get(position);
    }

    /**
     * Accessor method, returns the searchParameter size
     *
     * @return int
     */
    public int size() {
        return searchParameters.size();
    }

    /**
     * return the filterArray
     *
     * @return
     */
    public String[] getFilterArray() {
        return filterArray;
    }
    /**
     * return the diseasePrecess array
     * @return disease
     */
    public String[] getDisease() {
        return disease;
    }
  /**
     * return the imaging technique Array
     * @return imaging
     */
    public String[] getImaging() {
        return imaging;
    }

    /**
     * return the Source collection Array
     *
     * @return sourceCollection
     */
    public String[] getSourceCollection() {
        return sourceCollection;
    }

    /**
     * return the usage rights array
     *
     * @return rights
     */
    public String getUsageRight() {
        return rights;
    }

    /**
     * return the primary audience Array
     *
     * @return primary
     */
    public String[] getPrimaryArray() {
        return primaryArray;
    }

    /**
     * Return the value for hidden
     *
     * @return
     */
    public boolean getHidden() {
        return hidden;
    }

    /**
     * Adds parameterNode into the ArrayList
     *
     * @param input
     */
    public void addParameters(ParameterNode input) {
        searchParameters.add(input);
    }

    /**
     * Adds filter array into the parameterBean
     *
     * @param filter
     */
    public void setFilterArray(String[] filter) {
        filterArray = filter;
    }

    /**
     * Adds source collection array into the parameterBean
     *
     * @param source
     */
    public void setSourceCollection(String[] source) {
        sourceCollection = source;
    }

    /**
     * Adds usage rights array into the parameterBean
     *
     * @param rts
     */
    public void setUsageRights(String rts) {
        rights = rts;
    }

    /**
     * Adds primary audience array into the parameterBean
     *
     * @param primary
     */
    public void setPrimaryArray(String[] primary) {
        primaryArray = primary;
    }
  /**
     * Adds imaging technique array into the parameterBean
     * @param source
     */
    public void setImaging(String[] imgs) {
        imaging = imgs;
    }
  /**
     * Adds disease precess array into the parameterBean
     * @param rts
     */
    public void setDisease(String[] dis) {
        disease = dis;
    }

    /**
     * sets the value for hidden
     *
     * @param value
     */
    public void setHidden(boolean value) {
        hidden = value;
    }

    /**
     * This is a wrapper that calls for functions to parse the table name.
     * If the tableList is empty, it calls for function to create the tableList,
     * else it returns the tableList as ArrayList.
     *
     * @return ArrayList of tableName
     */
    public ArrayList getTableList() {
        if(tableList.isEmpty()) {
            makeTableList();
        }
        return tableList;
    }

    /**
     * Checks if the table name already exists in the tableList. If not, adds the
     * table name into the tableList.
     *
     * @param table
     *
     * @return boolean
     */
    private boolean tableNotFound(String table) {
        int temp = tableList.size();
        int i = 0;
        boolean notFound = true;
        while((i < temp) && (notFound)) {
            if(((String)tableList.get(i)).compareTo(table) == 0) {
                notFound = false;
            } else {
                i++;
            }
        }
        return notFound;
    }

    /**
     * main function to create the tableList. Parses through the searchParameter
     * one by one and checks for the table name.
     */
    private void makeTableList() {
        int temp = searchParameters.size();
        for(int i = 0; i < temp; i++) {
            ParameterNode input = (ParameterNode)searchParameters.get(i);
            String table = input.getTableName();
            if(tableNotFound(table)) {
                tableList.add(input.getTableName());
            }
        }
    }

    public void setPublicationNames(String[] names) {
        publicationNames = names;
    }

    public String[] getPublicationNames() {
        return publicationNames;
    }

    public void setPublicationIds(String[] publicationIds) {
        this.publicationIds = publicationIds;
    }

    public String[] getPublicationIds() {
        return publicationIds;
    }
    
	
  public ParameterBean makeParameterBean(String keywordString)
  {
    ParameterBean param=new ParameterBean(); 
    ParameterNode pam = new ParameterNode();
    String keywords ="";
    boolean stype = false;
    if (keywordString.equals("")) 
    {
      keywordString = "%";
      //System.out.print(keywordString);
    } 
	  String relation="AND";
    String columInfo="ALL.ALL"; //dummy variable
    this.setHidden(false);	
    keywordString=keywordString.trim();	
    //mapping the terms from the interface to the database tablename and columns
    if (keywordString.length()>0)
    {     			 
      keywordString=keywordString.toLowerCase(); //switch to lower case			  
      keywordString=keywordString.replaceAll("\'", "''");//make ' into two 's so that it does not cause error for database		
      //System.out.println(keywordString);
      keywordString=keywordString.replaceAll("\"", " \" "); //make " separate so that it becomes a token
      StringBuffer buf=new StringBuffer();
		  StringTokenizer tk=new StringTokenizer(keywordString);
		  String key;
		  while (tk.hasMoreTokens())
		  {
			  key=tk.nextToken();
			  if (key.compareTo("\"")==0) //if " means exact match
			  {
				  buf.delete(0, buf.length());
				  boolean end=false;
				  while (tk.hasMoreTokens()&& end==false) //attach the string until the ending " is found
				  {
					  key=tk.nextToken();			
					  if (key.compareTo("\"")==0)
					  {              
						  end=true;
						  key=buf.toString();
              stype = true;
					  }
					  else buf.append(key+" ");
				  }
				  key=buf.toString();
				  key=key.trim();
				  if (end) //if there is ending ", take as exact match
				  {					  
            pam = new ParameterNode(columInfo, key, relation,stype);
					  this.addParameters(pam);
           
				  }
				  else  //if there is no ending ", take as default "and" and parse the word
				  {
					  StringTokenizer token=new StringTokenizer(key);
					  while (token.hasMoreTokens())
					  {
					  	key=token.nextToken();
					  	if (key.compareTo("or")==0)
					  	{
							  relation="OR";
						  }
					  	else if (key.compareTo("and")==0)
					  	{
							  relation="AND";
						  }
						  else if (key.compareTo("NOT")==0)
						  {
							  relation="NOT";
						  }
					  	else 
					  	{
						  	pam=new ParameterNode(columInfo, key, relation);
						  	this.addParameters(pam);
						  	relation="AND";
					  	}
				    }
        	}					
        }
			  else if (key.compareTo("or")==0)
		  	{
				  relation="OR";
			  }
			  else if (key.compareTo("and")==0)
			  {
			  	relation="AND";
			  }
			  else if (key.compareTo("not")==0)
			  {
			  	relation="NOT";
			  }
			  else //treat as individual keyword and passes it into the parameterbean
		  	{
			  	pam=new ParameterNode(columInfo, key, relation);
		  		this.addParameters(pam);
			  	relation="AND";
      	}//end of if else
    	}//end of while
  	}	
   return param;
  }
}



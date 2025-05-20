package org.heal.module.search;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import com.ora.jsp.sql.*;
import org.heal.module.metadata.*;

/**
 * @author Grace/Julie 
 * @version 0.1
 */
 
public class SimpleSearchDAO implements Serializable
{
  public SimpleSearchDAO()
  {
  }
  private DataSource dataSource;   
  /**
   * Sets the dataSource property value. 
   */
  public void setDataSource(DataSource dataSource) 
  {
    this.dataSource = dataSource;
  }         
  /**
   * Here is the process by which the simple search works.
   * First, traverses through the ParameterBean passed from 
   * the search action page, and builds a single sql query from the 
   * information in the Bean. Than we search the following table and fields.
   * Metadata Table: Title, Description, SourceCollection, and MetadataID
   * Keywords Table: Keyword
   * Formats Table: Format
   * Copyrights and CopyrightTexts Tables: CopyrightTextID and CopyrightText 
   * CopyrightHolders and Vcards Tables: VcardID and Vcard
   * Contributors and Vcards Tables: VcardID and Vcard
   * TaxonPaths and Taxons Tables: TaxonPathID and Entry
   * The results that are left are packaged into a SearchResultBean and returned.
   * If filterHidden is set to true, then the results will not contain
   * any metadata that is marked as hidden.
   * @param param
   * @return results 
   */
  public SearchResultBean simpleSearch(ParameterBean param, String consumer) throws SQLException 
  {           
    SearchResultBean results = new SearchResultBean();
    results.setShortRecords(null);
    List metaIDs = new ArrayList();
    Vector rows=null;
    Row row = null;
    String metadataId=""; 
    ShortMetadataResultBean[] metadata = null;            
    Connection conn = dataSource.getConnection();                   
    try 
    { 
      rows = getQueryMetaID(param,consumer,conn);  
      // get the MetadataID and set it to the ShortMetadataBean and ShortMetadataResultBean
      if (rows != null && rows.size() > 0) 
      {
       // found matches...
        Iterator rowIterator = rows.iterator();     
        metadata = new ShortMetadataResultBean[rows.size()];
        int i=0;                 
        while (rowIterator.hasNext()&& i < metadata.length) 
        {
          row = (Row) rowIterator.next();
          try 
          {
            metadataId = row.getString("MetadataID");
            metaIDs.add(metadataId);
            ShortMetadataBean shortMetadata=new ShortMetadataBean();
            shortMetadata.setMetadataId(metadataId);
            ShortMetadataResultBean smb = new ShortMetadataResultBean();
            smb.setMetadataId(metadataId);                           
            metadata[i]=smb;
            i++;                   
          }
          catch (NoSuchColumnException ex) 
          {
            throw new SQLException(ex.toString());
          }
        }
      }
      results.setShortRecords(metadata);
      results.setMetaIDs(metaIDs);
    }finally 
    {
      try 
      {
        conn.close();
      } catch (SQLException ex) 
      {
      //ignore...
      }
    }       
    return results;        
  }
  /** Excute the SQL statement and return the set of rows  
    * @param param,conn
    * @return rows 
    */
  private Vector getQueryMetaID(ParameterBean param,String consumer,Connection conn)throws SQLException 
  {
    SQLCommandBean sqlCommandBean = new SQLCommandBean();
    sqlCommandBean.setConnection(conn);
    String sqls=makeSimpleQuery(param,consumer);
    sqlCommandBean.setSqlValue(sqls);      
    Vector rows = null;
    try 
    {
      rows = sqlCommandBean.executeQuery();
    }
    catch (UnsupportedTypeException e) 
    {
      throw new SQLException(e.toString());
    } 
    return rows;
  }      
  /**
   * This is the function that traverses through the ParameterBean passed from 
   * the search action page, and builds a single sql query from the 
   * information in the Bean. The query building process follows the following
   * steps: 1.Select MetadataID from each table
   *        2.UNION all the result
   *        3.If search more than one word, put the relation between each union.
   *          if relation is "AND", put "AND MetadataID IN" between two words statement.
   *          if relation is "OR", put "AND MetadataID NOT IN" between two statement.
   *          if relation is "NOT", put "UNION" between two union statement.
   * @param param
   * @return String
   */
   /**
   * This method takes in a string and adds default connetives "or" into
   * necessary positions.
   */
  public String addDefaultConnectives(String query)
  {
    StringTokenizer tk=new StringTokenizer(query);
    String previouskey="";
    String key;
    StringBuffer buff=new StringBuffer();
    boolean connect=false;    
    while (tk.hasMoreTokens())
    {
      key=tk.nextToken();
      if (key.compareTo("not")==0 || key.compareTo("or")==0 
      || key.compareTo("and")==0)
      {
        buff.append(key+" ");
        previouskey=key;
      }
      else if (previouskey.compareTo("(")!=0 && previouskey.compareTo("")!=0 && key.compareTo(")")!=0
      && previouskey.compareTo("not")!=0 && previouskey.compareTo("and")!=0 && previouskey.compareTo("or")!=0 )
      {
        buff.append(" and "+key+" ");
        previouskey=key;
      }
      else
      {
        buff.append(key+" ");
        previouskey=key;
      }      
    }
    return buff.toString();
  }
  /**
   * This method forms the query sentence for zero or one level of join. 
   * That is, for searches on metadata table and keyword tables. 
   * It assumes that query string is in the correct acceptance format:
   * keyword " keyword AND keyword " NOT ( keyword OR keyword )
   * The final sql statement should be in the following format:
   * SELECT metadataID FROM tablename WHERE column like '%keyword%' AND
   * metadatID IN (Select metadataID from tablename WHERE column like '%keyword%'
   * @param query
   * @param column
   * @param table
   * @return bf.toString()
   */
  public String buildCommonQuery(String query, String column, String table)
  {
    String origQuery=null;
    origQuery=addDefaultConnectives(query);
    StringTokenizer tk=new StringTokenizer(origQuery);
    String searchKey=null;
    StringBuffer bf=new StringBuffer();
    boolean isNOT=false;
    boolean isOR=false;
    boolean isAND=false;
    boolean isFirst=true;
    boolean isExact=false;
    bf.append("SELECT "+table+".metadataID from "+table+" WHERE ");
    while (tk.hasMoreTokens())
    {
      searchKey=tk.nextToken();
      if (searchKey.compareTo("\"")==0)
      {
        StringBuffer exact=new StringBuffer();
        searchKey=tk.nextToken();
        while(tk.hasMoreTokens() && searchKey.compareTo("\"")!=0)
        {
          exact.append(searchKey+" ");
          searchKey=tk.nextToken();
        }
        searchKey=exact.toString();
        searchKey=searchKey.trim();
        isExact=true;
      }
      if (searchKey.compareTo("(")==0 || searchKey.compareTo(")")==0)
      {
           bf.append(searchKey+" ");  
      }
      else if (searchKey.compareTo("or")==0)
      {
         bf.append(searchKey+" ");
         isOR=true;
      }
      else if (searchKey.compareTo("not")==0)
      {
        bf.append(" AND Metadata.metadataID NOT IN (SELECT "+table+".MetadataID from "+table+" where ");
        isNOT=true;
      }
      else if (searchKey.compareTo("and")==0)
      {
        bf.append(" AND Metadata.metadataID IN (SELECT "+table+".MetadataID from "+table+" where ");
        isAND=true;
      }
      else 
      {
        if (isAND || isNOT)
        {
          if (!isExact)
          {
            bf.append(column+" like '"+searchKey+ "%' OR " +column+" like '% "+searchKey+"%' )");
          }
          else
          {
             bf.append(column+" like '% "+searchKey+" %' OR "+column+" like '% "+searchKey+"%' OR "+column+" like '%"+searchKey+" %')"); 
          }
          isAND=false; isNOT=false; isFirst=false; isExact=false;
        }
        else if (isOR || isFirst)
        {
          if (!isExact)
          {
            bf.append(column+" like '"+searchKey+ "%' OR " +column+" like '% "+searchKey+"%' ");
          }
          else
          {
             bf.append(column+" like '% "+searchKey+" %' OR "+column+" like '% "+searchKey+"%' OR "+column+" like '%"+searchKey+" %'"); 
          }
          isFirst=false; isOR=false; isExact=false;
        }
        else
        {
          if (!isExact)
          {
              bf.append(" AND Metadata.metadataID IN (SELECT Metadata.MetadataID from metadata where "+column+" like '"+searchKey+ "%' OR " +column+" like '% "+searchKey+"%' )");
          }
          else
          {
            bf.append(" AND Metadata.metadataID IN (SELECT Metadata.MetadataID from metadata where "+column+" like '% "+searchKey+" %' OR "+column+" like '% "+searchKey+"%' OR "+column+" like '%"+searchKey+" %')");
          }
          isAND=false; isFirst=false; isExact=false;
        }
      }
    }
    return bf.toString();
  }
  public String buildMutiQuery(String query, String column, String table, String subTable, String subColumn)
  {
    String origQuery=null;
    origQuery=addDefaultConnectives(query);
    StringTokenizer tk=new StringTokenizer(origQuery);
    String searchKey=null;
    StringBuffer bf=new StringBuffer();
    boolean isNOT=false;
    boolean isOR=false;
    boolean isAND=false;
    boolean isFirst=true;
    boolean isExact=false;
    bf.append("SELECT "+table+".metadataID from "+table+" WHERE "+column+" IN (Select "+column+" from "+subTable+" WHERE ");
    while (tk.hasMoreTokens())
    {
      searchKey=tk.nextToken();
      if (searchKey.compareTo("\"")==0)
      {
        StringBuffer exact=new StringBuffer();
        searchKey=tk.nextToken();
        while(tk.hasMoreTokens() && searchKey.compareTo("\"")!=0)
        {
          exact.append(searchKey+" ");
          searchKey=tk.nextToken();
        }
        searchKey=exact.toString();
        searchKey=searchKey.trim();
        isExact=true;
      }
      if (searchKey.compareTo("(")==0 || searchKey.compareTo(")")==0)
      {
        bf.append(searchKey+" ");
      }
      else if (searchKey.compareTo("or")==0)
      {
        bf.append(searchKey+" ");
        isOR=true;
      }
      else if (searchKey.compareTo("not")==0)
      {
        bf.append(" AND "+column+" NOT IN (SELECT "+column+" FROM "+subTable+" WHERE ");
        isNOT=true;
      }
      else if (searchKey.compareTo("and")==0)
      {
        bf.append(" AND "+column+" IN (SELECT "+column+" FROM "+subTable+" WHERE ");
        isAND=true;
      }
      else 
      {
        if (isAND || isNOT)
        {
          if (!isExact)
          {
            bf.append(subColumn+" like '"+searchKey+ "%' OR " +subColumn+" like '% "+searchKey+"%' )");
          }
          else
          {
            bf.append(subColumn+" like '% "+searchKey+" %' OR "+subColumn+" like '% "+searchKey+"%' OR "+subColumn+" like '% "+searchKey+" %')");
          }
          isAND=false; isNOT=false; isExact=false;
        }
        else if (isOR || isFirst)
        {
          if (!isExact)
          {
            bf.append(subColumn+" like '"+searchKey+ "%' OR " +subColumn+" like '% "+searchKey+"%' ");
          }
          else
          {
            bf.append(subColumn+" like '% "+searchKey+" %' OR "+subColumn+" like '% "+searchKey+"%' OR "+subColumn+" like '% "+searchKey+" %' ");
          }
          isFirst=false; isOR=false; isExact=false;
        }
        else
        {
          if (!isExact)
          {
            bf.append(" AND "+column+" IN (SELECT "+column+" FROM "+subTable+" WHERE "+subColumn+" like '"+searchKey+ "%' OR " +subColumn+" like '% "+searchKey+"%' )");
          }
          else
          {
            bf.append(" AND "+column+" IN (SELECT "+column+" FROM "+subTable+" WHERE "+subColumn+" like '% "+searchKey+" %' OR "+subColumn+" like '% "+searchKey+"%' OR "+subColumn+" like '% "+searchKey+" %')");
          }
        }
      }
    }
    bf.append(")");
    return bf.toString();
  }  
  public String makeSimpleQuery(ParameterBean param, String consumer)
  { 
    StringBuffer sql = new StringBuffer();
    boolean exact=false;
    String relat=null; //stores the AND, OR relation
    int i=0; //counter
  
    sql.append("select distinct(Metadata.metadataID), Title FROM Metadata WHERE ");
    sql.append ("(Metadata.CatalogDate IS NOT NULL AND Metadata.ApproveDate IS NOT NULL AND ");
    if (param.getHidden()==false)
    {
      sql.append(" Metadata.Private='0'");
    }
    if (param.getUsageRight() == null) {
      sql.append(" AND Metadata.SourceCollection <> 'PEIR - University of Alabama at Birmingham Department of Radiology' AND Metadata.SourceCollection<> 'PEIR - University of Alabama at Birmingham Department of Pathology'");
    }
    if(consumer == null){
      sql.append(" AND Metadata.MetadataID NOT IN (select MetadataID from TargetUserGroups where (TargetUserGroup = 'Consumer Health/Patient Education' or TargetUserGroup = 'K-12'))"); 
    }
    
    sql.append(") \n");
    String start=sql.toString();
    for (i=0; i<param.size(); i++)
    {
      ParameterNode tempNode=param.getParameters(i);
      String key=tempNode.getKeyWord();
      relat=tempNode.getRelation();
      exact=tempNode.getExact(); 
      if (relat.compareTo("AND")==0 || relat.compareTo("")==0) //if AND or OR relation
      {
        sql.append("AND Metadata.MetadataID IN \n");
      }
      else if (relat.compareTo("NOT")==0)
      {
        sql.append("AND Metadata.MetadataID NOT IN \n");
      }
      else if (relat.compareTo("OR")==0)
      {
        sql.append("UNION \n"+start+"AND Metadata.MetadataID IN \n ");
      }        
      if (!exact)
      {
        sql.append("("+buildCommonQuery(key, "title", "Metadata"));
          sql.append("\nUNION "+buildCommonQuery(key, "description", "Metadata")); 
          sql.append(" \nUNION "+buildCommonQuery(key, "SourceCollection", "Metadata"));
          sql.append(" \nUNION "+buildCommonQuery(key, "metadataID", "Metadata"));
          sql.append(" \nUNION "+buildCommonQuery(key, "LearningResourceType", "Metadata"));
          sql.append(" \nUNION "+buildCommonQuery(key, "keyword", "Keywords"));
          sql.append(" \nUNION "+buildCommonQuery(key, "tag", "Tags"));          
          sql.append(" \nUNION "+buildMutiQuery(key, "CopyrightTextID", "CopyRights", "CopyrightTexts", "CopyrightText"));
          sql.append(" \nUNION "+buildMutiQuery(key, "TaxonPathID", "TaxonPaths", "Taxons", "Entry"));
          sql.append(" \nUNION "+buildMutiQuery(key, "vcardID", "Contributors", "Vcards", "vcard"));
          sql.append(" \nUNION "+buildMutiQuery(key, "vcardID", "CopyrightHolders", "Vcards", "vcard"));
          sql.append(")\n");
      }
      else //if it is exact search
      {
        sql.append(" (Select Metadata.MetadataID from Metadata where"); 
        sql.append(" (title like '"+key+"' OR title like '% "+key+" %' OR title like '"+key+" %' OR title like '% "+key+"' OR title like '% "+key+".%' OR title like '% "+key+",%'\n");
        sql.append("OR description like '"+key+"' OR description like '% "+key+" %' OR description like '"+key+" %' OR description like '% "+key+"' OR description like '% "+key+".%' OR description like '% "+key+",%'\n");
        sql.append("OR SourceCollection like '"+key+"' OR SourceCollection like '% "+key+" %' OR SourceCollection like '% "+key+"' OR SourceCollection like '"+key+" %' OR SourceCollection like '% "+key+".%' OR SourceCollection like '% "+key+",%'\n");
        sql.append("OR LearningResourceType like '"+key+"' OR LearningResourceType like '% "+key+" %' OR LearningResourceType like '% "+key+"' OR LearningResourceType like '"+key+" %' OR LearningResourceType like '% "+key+".%' OR LearningResourceType like '% "+key+",%'\n");
        sql.append("OR metadataID like '"+key+"' )\n");
        sql.append("UNION Select keywords.metadataID FROM keywords where keyword like '"+key+"' OR keyword LIKE '% "+key+" %' OR keyword like '"+key+" %' OR keyword like '% "+key+"' OR keyword like '% "+key+".%' OR keyword like '% "+key+",%'\n");
        sql.append("UNION Select Copyrights.metadataID from Copyrights WHERE copyrightTextID IN (Select copyrightTextID from CopyrightTexts WHERE ");
        sql.append("copyrightText like '"+key+"' OR copyrightText LIKE '% "+key+" %' OR copyrightText like '"+key+" %' OR copyrightText like '% "+key+"' OR copyrightText like '% "+key+".%')\n");
      //  sql.append("UNION Select TaxonPaths.metadataID from TaxonPaths where taxonpathid in (select taxonpathid from taxons where ");
      //  sql.append("Entry like '"+key+"' OR Entry LIKE '% "+key+" %' OR Entry like '"+key+" %' OR Entry like '% "+key+"' OR Entry like '% "+key+".%' OR Entry like '% "+key+",%')\n");
        sql.append("UNION select copyrightHolders.metadataID from copyrightHolders where vcardID in (select vcardID from vcards where ");
        sql.append("vCard like '"+key+"' OR vCard LIKE '% "+key+" %' OR vCard like '"+key+" %' OR vCard like '% "+key+"' OR vCard like '% "+key+".%' OR vCard like '% "+key+",%')\n");
        sql.append("UNION select Contributors.metadataID from Contributors where vcardID in (select vcardID from vcards where ");
        sql.append("vCard like '"+key+"' OR vCard LIKE '% "+key+" %' OR vCard like '"+key+" %' OR vCard like '% "+key+"' OR vCard like '% "+key+".%' OR vCard like '% "+key+",%') )\n");        
      }
    }
    sql.append(" order by Title");
    String output=sql.toString();
    System.out.println(output);
    return output;    
  }    
}

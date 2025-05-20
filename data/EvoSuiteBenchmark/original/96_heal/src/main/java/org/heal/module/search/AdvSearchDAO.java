package org.heal.module.search;

import com.ora.jsp.sql.NoSuchColumnException;
import com.ora.jsp.sql.Row;
import com.ora.jsp.sql.SQLCommandBean;
import com.ora.jsp.sql.UnsupportedTypeException;
import org.heal.module.metadata.ShortMetadataBean;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Grace/Julie
 * @version 0.1
 */

public class AdvSearchDAO implements Serializable 
{
  public AdvSearchDAO() 
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
	 * Given a set of parameters and a list of filters, performs a search
	 * for all metadata that match all of the criteria set out in the
	 * parameters and also have a format in the presented list.
	 * The results are returned in a SearchResultBean.
	 * It should be noted that unless a metadata matches _ALL_ of the
	 * parameters laid out in the search paremters, it will not be in the
	 * result set.
	 *
	 * @param param
	 *
	 * @return results
	 */
	public SearchResultBean AdvSearch(ParameterBean param) throws SQLException 
  {
		SearchResultBean results = new SearchResultBean();
		Connection conn = dataSource.getConnection();
		try {
			List metaIDs = new ArrayList();
			Vector rows = getQueryMetaID(param, conn);
			ShortMetadataResultBean[] metadata = null;
			// get the MetadataID and set it to the ShortMetadataBean and ShortMetadataResultBean
			if(rows != null && rows.size() > 0) {
				// found matches...
				metadata = new ShortMetadataResultBean[rows.size()];
				int currentRowIndex = 0;
				for(Iterator rowIterator = rows.iterator(); rowIterator.hasNext();) {
					Row row = (Row)rowIterator.next();
					try {
						String metadataId = row.getString("ID");
						metaIDs.add(metadataId);
						ShortMetadataBean shortMetadata = new ShortMetadataBean();
						shortMetadata.setMetadataId(metadataId);
						ShortMetadataResultBean smb = new ShortMetadataResultBean();
						smb.setMetadataId(metadataId);
						metadata[currentRowIndex] = smb;
						currentRowIndex++;
					} catch(NoSuchColumnException ex) {
						throw new SQLException(ex.toString());
					}
				}
			}
			results.setShortRecords(metadata);
			results.setMetaIDs(metaIDs);
		} finally {
			try {
				conn.close();
			} catch(SQLException ex) {
				//ignore...
			}
		}
		return results;
	}
	/**
	 * Excute the SQL statement and return the set of rows
	 *
	 * @param param
	 * @param conn
	 *
	 * @return rows
	 */
	private Vector getQueryMetaID(ParameterBean param, Connection conn) throws SQLException 
  {
		SQLCommandBean sqlCommandBean = new SQLCommandBean();
		sqlCommandBean.setConnection(conn);
		String sqls = makeQuery(param);
		sqlCommandBean.setSqlValue(sqls);
		Vector rows;
		try 
    {
			rows = sqlCommandBean.executeQuery();
		} catch(UnsupportedTypeException e) 
    {
			throw new SQLException(e.toString());
		}
		return rows;
	}
  /**
   * This method takes in a string and adds default connetives "or" into
   * necessary positions.
   */
  public String addDefaultConnectives(String query)
  {
    //System.out.println(query);
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
    //System.out.println(bf.toString());
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
    //System.out.println(bf.toString());
    return bf.toString();
  }  
  public String getMultiSelection(String[] values, String column)
  {
    StringBuffer buffer=new StringBuffer();
    for(int i=0; i<values.length; i++)
    {
      if(i==0)
      {
        buffer.append(column+" like '%"+values[i]+"%' ");
      }
      else
      {
        buffer.append("OR "+column+" like '%"+values[i]+"%' ");
      }                 
    }
    return buffer.toString();
  }
  /**
   * This is the function that traverses through the ParameterBean passed from 
   * the advanced search action page, and builds a single sql query from the 
   * information in the Bean. for OR relation, the function uses UNION to
   * combine the select statements, for AND relation, the funcion uses
   * "and metadataid in" statement. For the multi-selection and drop down windows
   * that has fixed AND relation, they are parsed before the boolean keyword
   * selections, and the results will be attached at the begining for every 
   * Union boolean relation.
   * @param param
   * @return String
   */
  public String makeQuery(ParameterBean param)
  { 
    String[] filter=param.getFilterArray();
    String[] source=param.getSourceCollection();
    String[] publicationNames = param.getPublicationNames();
    String[] publicationIds = param.getPublicationIds();
    //String[] rights=param.getRightsArray();
    String[] primary=param.getPrimaryArray();
    String[] imaging = param.getImaging();
    String[] diseasep = param.getDisease();     
    String relat=null; //stores the AND, OR relation
    String in_relat=null; //stores relation segments needed in the query
    String start; //stores the begining select statements and multi-selection
                  //conditions needed at the begining of the query and
                  //at the begining of every Union selection
    int i=0; //counter
    StringBuffer sql = new StringBuffer();
    StringBuffer union=new StringBuffer();
    sql.append("select distinct(Metadata.metadataID) AS ID, Title FROM Metadata WHERE ");
    sql.append ("(Metadata.CatalogDate IS NOT NULL AND Metadata.ApproveDate IS NOT NULL AND ");      
    if (param.getHidden()==false)
    {
      sql.append(" Metadata.Private='0'");
    }
    sql.append(") \n"); 
    if (filter!=null && (filter[0].compareTo("all")!=0))
    {                 
      sql.append(" AND (");
			sql.append(" "+getMultiSelection(filter, "LearningResourceType"));
			sql.append(")\n");                              
    }
    if(imaging != null && imaging[0].compareTo("all")!=0) 
    {
      sql.append(" AND (");
			sql.append(" "+getMultiSelection(imaging, "RadiographType"));
			sql.append(")\n");
		}
    if(diseasep != null && diseasep[0].compareTo("all")!=0) 
    {
			sql.append(" AND (");
			sql.append(" "+getMultiSelection(diseasep, "DiseaseProcess"));
			sql.append(")\n");
		}
    //parse out the multi-selection arrays, and combine them into the query
    if(source!=null && source[0].compareTo("all")!=0)
    {
      sql.append(" AND (");
      sql.append(" "+getMultiSelection(source, "SourceCollection"));
      sql.append(" )\n");
    }
    if(null != publicationNames && !"all".equals(publicationNames[0])) 
    {
      sql.append(" AND (");
      for(int counter = 0; counter < publicationNames.length; ++counter) {
        if(0 != counter) {
          sql.append(" OR ");
        }
        sql.append("Metadata.PublicationId IN (SELECT Publications.PublicationId FROM Publications WHERE PublicationName LIKE '" + publicationNames[counter] + "%')");
      }
      sql.append(")\n");
    }
    if(null != publicationIds && !"all".equals(publicationIds[0])) 
    {
      sql.append(" AND (");
      for(int counter = 0; counter < publicationIds.length; ++counter) {
        if(0 != counter) {
          sql.append(" OR ");
        }
        sql.append("Metadata.PublicationId = '" + publicationIds[counter] + "'");
      }
      sql.append(")\n");
    }
    
    if(primary!=null && primary[0].compareTo("all")!=0 && primary[0].compareTo("notall")!=0)
    { 
        sql.append("AND Metadata.MetadataID IN (SELECT TargetUserGroups.MetadataID from TargetUserGroups WHERE");
        sql.append(" "+getMultiSelection(primary, "TargetUserGroup"));
        sql.append(") \n");   
    } 
    else if(primary[0].compareTo("notall")== 0){       
        sql.append(" AND Metadata.MetadataID NOT IN (select MetadataID from TargetUserGroups where (TargetUserGroup = 'Consumer Health/Patient Education' or TargetUserGroup = 'K-12') AND MetadataID NOT IN (select MetadataID from TargetUserGroups where TargetUserGroup = 'Higher Education' or TargetUserGroup ='Health Profession Education'))");
    }
    else {}
    start=sql.toString(); //The statement parsed above are saved in the start parameter
    //string and attached at the beginning of the Union statement
    for (i=0; i<param.size(); i++)
    {
      ParameterNode tempNode=param.getParameters(i);
      String key=tempNode.getKeyWord();
      relat=tempNode.getRelation();
      String column=tempNode.getColumnName();
      String table=tempNode.getTableName(); 
      boolean exact=tempNode.getExact();
      //skip nodes with the three column below, because they have been taken care of 
      if (column.compareTo("SpecimenType")!=0 && column.compareTo("RadiographType")!=0 && column.compareTo("DiseaseProcess")!=0)
      {   //attach segments correspondingly 
        if (relat.compareTo("AND")==0 && i!=0 ) //if AND relation
        {
          sql.append("AND Metadata.MetadataID IN \n");
        }
        else if (relat.compareTo("NOT")==0 && i!=0) //if not relation
        {
          sql.append("AND Metadata.MetadataID NOT IN \n");
        }
        else if (relat.compareTo("OR")==0 && i!=0) //if or relation
        {
          sql.append("UNION \n"+start+"AND Metadata.MetadataID IN \n ");
        } 
        else if (i==0) //if the first node, attach default segment
        {
          sql.append("AND Metadata.MetadataID IN \n");
          in_relat="";
        }
        //attach segments according to different table name
        if (table.compareTo("Metadata")==0)
        { 
          sql.append("("+buildCommonQuery(key, column, table)+" )\n");
        }
        if (table.compareTo("Keywords")==0)
        {
          sql.append(in_relat);
          sql.append("("+buildCommonQuery(key, column, table)+")\n");
        }
        // added by Zhen 6/12/08 for searching tags table.
        if (table.compareTo("Tags")==0)
        {
          sql.append(in_relat);
          sql.append("("+buildCommonQuery(key, column, table)+")\n");
        }
        if (table.compareTo("CopyrightHolders")==0)
        {
          sql.append(in_relat);
          sql.append("( "+buildMutiQuery(key, "vcardID", table, "vcards", column)+" )\n");
        }
        if (table.compareTo("Contributors")==0)
        {
          sql.append(in_relat);
          sql.append("( "+buildMutiQuery(key, "vcardID", table, "vcards", column)+" )\n");
        }
        if (table.compareTo("TaxonPaths")==0)
        {
          sql.append(in_relat);
          sql.append("( "+buildMutiQuery(key, "taxonpathID", table, "taxons", column)+" )\n");
        }         
        if (table.compareTo("ALL")==0)
        {
          sql.append(in_relat );
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
      }
    }
    sql.append(" order by Title");
    String output=sql.toString();
    System.out.println("query: " + output);
    return output;    
  }    
}
package org.heal.module.search;

import com.ora.jsp.sql.NoSuchColumnException;
import com.ora.jsp.sql.Row;
import com.ora.jsp.sql.SQLCommandBean;
import com.ora.jsp.sql.UnsupportedTypeException;
import com.ora.jsp.sql.value.StringValue;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.lang.String;

/**
 * @author Grace
 * @version 0.1
 */
 
public class ConceptMappingDAO 
{
  public ConceptMappingDAO()
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
  public ConceptMappingBean ConceptMapping(String term) throws SQLException 
  { 
    ConceptMappingBean result = null;
    Connection conn = dataSource.getConnection();  
    if (term != null)
    {
        if (term.indexOf("'s") > 0)
        {
          term = term.replaceAll("'s","");
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM ConceptMapping WHERE InputTerms = ?");
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(term));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch(UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        }
        if(rows != null && rows.size() > 0) {
          try {
            //just take the first row, there shouldn't be
            //multiple matches...
            Row row = (Row)rows.firstElement();
            result = new ConceptMappingBean();
            result.setConceptId(row.getString("ConceptId"));
            result.setInputTerms("InputTerms");
            result.setConcept(row.getString("Concept"));
            result.setMapping(row.getString("Mapping"));
           
          } catch(NoSuchColumnException ex) {
            throw new SQLException(ex.toString());
          }
        }
        else 
        result = null;
      }
      return result;
    }
}
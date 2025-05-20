package framework.persistence.jdbc;

import java.beans.SimpleBeanInfo;
import java.beans.PropertyDescriptor;
import java.beans.IntrospectionException;

/**
 * ViewBeanInfo provide a View property descriptors
 * This is a workaround to avoid reflection call to unwanted methods 
 * as getCollection
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.2 $ $Date: 2004/05/09 21:05:26 $
 */
public class ViewBeanInfo extends SimpleBeanInfo 
{
  public PropertyDescriptor[] getPropertyDescriptors()
  {
    try
    {
      PropertyDescriptor _maxFetchSize = new PropertyDescriptor("maxFetchSize", View.class, "getMaxFetchSize", "setMaxFetchSize");
      _maxFetchSize.setDisplayName("maxFetchSize");
      _maxFetchSize.setShortDescription("maxFetchSize");
      PropertyDescriptor _version = new PropertyDescriptor("version", View.class, "getVersion", "setVersion");
      _version.setDisplayName("version");
      _version.setShortDescription("version");
      PropertyDescriptor _timestamp = new PropertyDescriptor("timestamp", View.class, "getTimestamp", "setTimestamp");
      _timestamp.setDisplayName("timestamp");
      _timestamp.setShortDescription("timestamp");
      PropertyDescriptor _attributeCollection = new PropertyDescriptor("attributeCollection", View.class, null, null);
      _attributeCollection.setDisplayName("attributeCollection");
      _attributeCollection.setShortDescription("attributeCollection");
      PropertyDescriptor _attributesSize = new PropertyDescriptor("attributesSize", View.class, "getAttributesSize", null);
      _attributesSize.setDisplayName("attributesSize");
      _attributesSize.setShortDescription("attributesSize");
      PropertyDescriptor _class = new PropertyDescriptor("class", View.class, "getClass", null);
      _class.setDisplayName("class");
      _class.setShortDescription("class");
      PropertyDescriptor _targetClassName = new PropertyDescriptor("targetClassName", View.class, "getTargetClassName", "setTargetClassName");
      _targetClassName.setDisplayName("targetClassName");
      _targetClassName.setShortDescription("targetClassName");
      PropertyDescriptor _packageName = new PropertyDescriptor("packageName", View.class, "getPackageName", "setPackageName");
      _packageName.setDisplayName("packageName");
      _packageName.setShortDescription("packageName");
      PropertyDescriptor _name = new PropertyDescriptor("name", View.class, "getName", "setName");
      _name.setDisplayName("name");
      _name.setShortDescription("name");
      PropertyDescriptor _comment = new PropertyDescriptor("comment", View.class, "getComment", "setComment");
      _comment.setDisplayName("comment");
      _comment.setShortDescription("comment");
      PropertyDescriptor _attributes = new PropertyDescriptor("attributes", View.class, "getAttributes", null);
      _attributes.setDisplayName("attributes");
      _attributes.setShortDescription("attributes");
      PropertyDescriptor _tokenValues = new PropertyDescriptor("tokenValues", View.class, "getTokenValues", "setTokenValues");
      _tokenValues.setDisplayName("tokenValues");
      _tokenValues.setShortDescription("tokenValues");
      PropertyDescriptor _startIndex = new PropertyDescriptor("startIndex", View.class, "getStartIndex", "setStartIndex");
      _startIndex.setDisplayName("startIndex");
      _startIndex.setShortDescription("startIndex");
      PropertyDescriptor _sqlQuery = new PropertyDescriptor("sqlQuery", View.class, "getSqlQuery", "setSqlQuery");
      _sqlQuery.setDisplayName("sqlQuery");
      _sqlQuery.setShortDescription("sqlQuery");
      PropertyDescriptor _rowsPerPage = new PropertyDescriptor("rowsPerPage", View.class, "getRowsPerPage", "setRowsPerPage");
      _rowsPerPage.setDisplayName("rowsPerPage");
      _rowsPerPage.setShortDescription("rowsPerPage");
      PropertyDescriptor _rowCountMethod = new PropertyDescriptor("rowCountMethod", View.class, "getRowCountMethod", "setRowCountMethod");
      _rowCountMethod.setDisplayName("rowCountMethod");
      _rowCountMethod.setShortDescription("rowCountMethod");
      PropertyDescriptor _retrieveRowCount = new PropertyDescriptor("retrieveRowCount", View.class, "getRetrieveRowCount", "setRetrieveRowCount");
      _retrieveRowCount.setDisplayName("retrieveRowCount");
      _retrieveRowCount.setShortDescription("retrieveRowCount");
      PropertyDescriptor _parameters = new PropertyDescriptor("parameters", View.class, "getParameters", "setParameters");
      _parameters.setDisplayName("parameters");
      _parameters.setShortDescription("parameters");
      PropertyDescriptor _fullName = new PropertyDescriptor("fullName", View.class, "getFullName", null);
      _fullName.setDisplayName("fullName");
      _fullName.setShortDescription("fullName");
      PropertyDescriptor _bindVariables = new PropertyDescriptor("bindVariables", View.class, "getBindVariables", "setBindVariables");
      _bindVariables.setDisplayName("bindVariables");
      _bindVariables.setShortDescription("bindVariables");
      PropertyDescriptor[] pds = new PropertyDescriptor[] {_bindVariables, _fullName, _parameters, _retrieveRowCount, _rowCountMethod, _rowsPerPage, _sqlQuery, _startIndex, _tokenValues, _attributes, _comment, _name, _packageName, _targetClassName, _class, _attributesSize, _attributeCollection, _timestamp, _version, _maxFetchSize};
      return pds;
    }
    catch(IntrospectionException e)
    {
      e.printStackTrace();
      return null;
    }

  }


}
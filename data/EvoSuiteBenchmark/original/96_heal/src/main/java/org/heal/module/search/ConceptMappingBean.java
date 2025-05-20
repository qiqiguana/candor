package org.heal.module.search;

import java.io.*;

/**
 * This class contains information about concept mapping
 *
 * @author Grace
 * @version 0.1
 */
public class ConceptMappingBean 
{
  public ConceptMappingBean()
  {
  }
  // Properties
  private String conceptId = null;
  private String inputTerms = null;
  private String concept = null;
  private String mapping = null;
 
  public String getConceptId()
  {
    return this.conceptId;
  }

  public void setConceptId(String cId) 
  {
    this.conceptId = cId;
  }
 
  public String getInputTerms() 
  {
    return this.inputTerms;
  }

  public void setInputTerms(String interms) 
  {
    this.inputTerms = interms;
  }

  public String getConcept() 
  {
    return this.concept;
  }

  public void setConcept(String cpt) 
  {
    this.concept = cpt;
  }
  public String getMapping() 
  {
    return this.mapping;
  }
 
  public void setMapping(String map) 
  {
    this.mapping = map;
  }  
}

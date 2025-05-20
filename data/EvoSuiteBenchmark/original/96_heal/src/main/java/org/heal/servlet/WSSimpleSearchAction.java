package org.heal.servlet;

import java.io.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import org.heal.module.metadata.*;
import org.heal.module.search.*;
import org.heal.module.search.MerlotWS.SimpleMerlotSearch;
import org.heal.module.search.ShortMetadataResultBean;
import org.heal.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * An {@link Action} that handles a request to call MERLOT's SOAP based search methods.
 */

public class WSSimpleSearchAction implements Action {
    
    /**
     *
     * @param servlet
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
      String keywords = new String();
      keywords = request.getParameter("keywords");
      ShortMetadataBean currentMeta = null;
      
      InterfaceUtilitiesBean interfaceUtilities = (InterfaceUtilitiesBean) servlet.getServletContext().getAttribute("interfaceUtilities");
      SimpleSearchDAO sd = (SimpleSearchDAO) servlet.getServletContext().getAttribute("SimpleSearchDAO"); 
      MetadataDAO metadataServices = (MetadataDAO) servlet.getServletContext().getAttribute("MetadataDAO");
      
      SearchResultBean srb = null;
      SimpleMerlotSearch sms = new SimpleMerlotSearch();
      String library = new String();
      library = request.getParameter("library");
      String library2 = new String();
      library2 = request.getParameter("library2");


      SearchResultBean hsrb = null;
      
      if("Merlot".equals(library)){    
        srb = sms.doSimpleMerlotSearch(keywords, 1, 25);
        srb  = prepareForInterface(srb,interfaceUtilities);
      }

      
      if("HEAL".equals(library2)){ 
        ParameterBean pb = new ParameterBean(keywords);
        try{
          hsrb = sd.simpleSearch(pb,"check");
          if (hsrb.getShortRecords()!=null){
          int hSearchLength = hsrb.getShortRecords().length;
          if(hSearchLength>25)
            hSearchLength = 25;
          String metadataId = null;
          ShortMetadataBean hShortMetadata = null;
          ShortMetadataResultBean hShortMetadataResultBean[] = null;
          hShortMetadataResultBean  = hsrb.getShortRecords();
          for(int b=0;b<hSearchLength;b++){
            hShortMetadata = hShortMetadataResultBean[b].getShortMetadata();
            metadataId = hShortMetadataResultBean[b].getMetadataId();
            currentMeta = metadataServices.getShortMetadata(metadataId);
            hShortMetadataResultBean[b].setShortMetadata(currentMeta);          
          }
          ShortMetadataResultBean smr[] = new ShortMetadataResultBean[hSearchLength];
          for(int c=0;c<hSearchLength;c++){
            smr[c]=hShortMetadataResultBean[c];
          }
          
          hsrb.setShortRecords(smr);
          hsrb=prepareForInterface(hsrb,interfaceUtilities);
          }
          else
          {
            hsrb=null;
          }
        }
        catch(SQLException se){
          se.printStackTrace();
        }
      }
      
      SearchResultBean rb = new SearchResultBean();
      rb = mixResults(srb, hsrb);
      
      int healCount=0;
      int merlotCount=0;
      if(hsrb!=null)
        healCount = hsrb.getShortRecords().length;
      if(srb!=null)  
        merlotCount = srb.getShortRecords().length;
      
      request.getSession().setAttribute("healCount",healCount);
      request.getSession().setAttribute("merlotCount",merlotCount);
      request.getSession().setAttribute("searchResults", rb);
      
      String queryString = request.getQueryString();
      if(queryString == null || queryString.length() == 0) {
        queryString = "page=1";
      } else {
        queryString = queryString + "&page=1";
      }
      String redirectURL = "wsSearchResults?" + queryString;
      response.sendRedirect(redirectURL);
    }
    
    
    public SearchResultBean prepareForInterface(SearchResultBean srb, InterfaceUtilitiesBean interfaceUtilities){
      if (srb==null)
        return null;
      ShortMetadataResultBean smrb[] = null;
      ShortMetadataBean currentMeta = null;
      smrb = srb.getShortRecords();
      int searchLength = srb.getShortRecords().length;
      for (int a=0;a<searchLength;a++)
      {
        currentMeta = smrb[a].getShortMetadata();
        String format = currentMeta.getFormat();
        if (format == null || format.length()==0) {
          format = "unknown";
				}
				ThumbnailBean thumbnail = interfaceUtilities.getThumbnail(currentMeta.getThumbnail(), format, "../");
        currentMeta.setThumbnail(thumbnail);
        if(currentMeta.getDescription()==null || currentMeta.getDescription().length()==0)
          currentMeta.setDescription("No Description Available");        
        currentMeta.setDescription(interfaceUtilities.getAbbreviatedString(currentMeta.getDescription(), 100));        
        // in the HEAL metadata filesize is required.
        if(null!=currentMeta.getFileSize()&&currentMeta.getFileSize().length()!=0)
          currentMeta.setFileSize(NumberFormat.getInstance().format(Long.parseLong(currentMeta.getFileSize())));
        else
        currentMeta.setFileSize("0");  
      }    
      return srb;    
    }
    
    public SearchResultBean mixResults(SearchResultBean srb, SearchResultBean hsrb){
      if(srb==null&hsrb==null){
        return new SearchResultBean();
      }
      else if(srb==null)
        return hsrb;
      else if(hsrb==null)
        return srb;
      
      SearchResultBean rb = new SearchResultBean();
    
      ShortMetadataResultBean hShortMetadataResultBean[] = null;
      hShortMetadataResultBean  = hsrb.getShortRecords();
      
      ShortMetadataResultBean shortMetadataResultBean[] = null;
      shortMetadataResultBean  = srb.getShortRecords();

      ShortMetadataResultBean smrb[] = new ShortMetadataResultBean[hShortMetadataResultBean.length+shortMetadataResultBean.length];

      int beanLen = 0;
      int sBeanLen = 0;
      
      if(hShortMetadataResultBean.length>shortMetadataResultBean.length){
        beanLen = hShortMetadataResultBean.length;
        sBeanLen = shortMetadataResultBean.length;        
      }
      else{ 
        beanLen = shortMetadataResultBean.length;
        sBeanLen = hShortMetadataResultBean.length;
      }
      int h=0;
      int m=0;
      int r=0;
      for(int a=0;a<beanLen;a++){
        if((hShortMetadataResultBean.length==sBeanLen&&a<sBeanLen)||(hShortMetadataResultBean.length>sBeanLen)&&hShortMetadataResultBean[h]!=null){
          smrb[r++] = hShortMetadataResultBean[h++];       
        }   
        if((shortMetadataResultBean.length==sBeanLen&&a<sBeanLen)||(shortMetadataResultBean.length>sBeanLen)&&shortMetadataResultBean[m]!=null){
          smrb[r++] = shortMetadataResultBean[m++]; 
        }
        rb.setShortRecords(smrb);  
      }
      return rb;    
    }

    /**
     *
     * @return
     */
    public boolean actionRequiresLogin() {
        return false;
    }
}

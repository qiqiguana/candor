package org.heal.module.search.MerlotWS;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import org.merlot.fedsearch.wsdl.*;
import org.heal.module.metadata.ShortMetadataBean;
import org.heal.module.search.SearchResultBean;
import org.heal.module.search.ShortMetadataResultBean;

/**
 * This class is used to call Merlot's search web services.
 * @author Jason Varghese
 */

public  class SimpleMerlotSearch{
    private final static int MAX_RESULTS = 10;
    
    public SearchResultBean doSimpleMerlotSearch(String keywords, int start, int stop){
        SearchResultBean srb = new SearchResultBean();
        try{
            org.merlot.fedsearch.type.SearchResult searchResult = new org.merlot.fedsearch.type.SearchResult();
            org.merlot.fedsearch.type.SearchResultElement[] sre = new org.merlot.fedsearch.type.SearchResultElement[MAX_RESULTS];
            MerlotSearch ims2 = new MerlotSearchLocator();
            SearchService ims = ims2.getSearchServicePort();
            //searchResult = ims.doSearch("ulOPDbfh0RN6PWKw",keywords,1,25);
            searchResult = ims.doSearch("YoFt9llu056hvtZC",keywords,1,25);
            sre = searchResult.getResultElements();
            int count = searchResult.getTotalResultsCount();
            if (count < stop)
                stop = count;
            ShortMetadataResultBean[] smrb = new ShortMetadataResultBean[stop-start+1]; 
            srb.setKeywords(keywords);
            srb.setSimple(true);  
            ShortMetadataBean currentMeta = null;
            if (sre==null){
                return null;
            }  
            for (int a=0;a<sre.length;a++){			
                if (smrb[a]==null)
                    smrb[a]=new ShortMetadataResultBean();
                    currentMeta = smrb[a].getShortMetadata();
                    if(currentMeta==null)
                        currentMeta=new ShortMetadataBean();
                    currentMeta.setTitle(sre[a].getTitle());
                    currentMeta.setDescription(sre[a].getDescription());
                    currentMeta.setLocation(sre[a].getDetailURL());
                    currentMeta.setSourceCollection("MERLOT");
                    currentMeta.setFormat(sre[a].getItemType());
                    currentMeta.setThumbnail(currentMeta.getThumbnail());
                    smrb[a].setShortMetadata(currentMeta);			
                }
            srb.setShortRecords(smrb);
        }
        catch (RemoteException re){
            re.printStackTrace();
        }
        catch (ServiceException se){
            se.printStackTrace();
        }    
        return srb;
    }
}

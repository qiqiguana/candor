package com.imsmart.cms;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MContentManager
{
        public void setMetadataMap(Map metdataMap);
    
	public void connect(String userName, String password);
	
	public boolean createFolder(String parentFolder, String folder);
	
	public boolean checkInContent(File content)throws Exception;
}

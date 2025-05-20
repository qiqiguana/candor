package com.imsmart.parser;

import java.util.HashMap;
import java.util.List;

public interface MParser
{
	public List parseAsList(String fileNameWithPath);
        
        public HashMap parseAsMap(String fileNameWithPath);
}

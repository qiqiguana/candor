package com.imsmart.main;

import java.util.HashMap;
import java.util.Map;

public class MSession
{
	public static final String USER_NAME = "user";

	public static final String USER_PASSWORD = "password";

	private static MSession mSession = null;

	private Map sessionMap = null;

	private MSession()
	{
		sessionMap = new HashMap();
	}

	public static MSession getInstance()
	{
		if (mSession == null)
		{
			mSession = new MSession();
		}
		return mSession;
	}

	public void setAttribute(String attribute, Object obj)
	{
		sessionMap.put(attribute, obj);
	}
	
	public Object getAttribute(String attribute)
	{
		return sessionMap.get(attribute);
	}
}

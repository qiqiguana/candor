/*
 * Created on 07.04.2003
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.xbus.sample;

/**
 * @author Fleckenstein
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JavaWorker
{
	public Object doSomething(Object indata)
	{
		return indata;
	}

	public String reverse(Object indata)
	{
		String data = (String) indata;
		int length = data.length();
		StringBuffer retData = new StringBuffer(length);
		for (int i = 0; i < length; i++)
		{
			retData.append(data.charAt(length - i - 1));
		}
		return retData.toString();
	}
}

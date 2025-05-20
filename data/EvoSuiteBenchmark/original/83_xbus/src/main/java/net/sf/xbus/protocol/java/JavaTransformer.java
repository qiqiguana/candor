package net.sf.xbus.protocol.java;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

/**
 * The <code>JavaTransformer</code> calls a method of a Java class, to
 * transform the source into the destination format. Which class and which
 * method to be used is read out of the configuration.
 * <p />
 * The signature of the called method must be:
 * <p />
 * 
 * <pre>
 *     public Object &lt;i&gt;methodName&lt;/i&gt;(Object inObject) throws XException
 * </pre>
 */
public class JavaTransformer implements Transformer
{
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
			throws XException
	{
		String classname = getClassName(source, destination, destinationMessage);
		String methodname = getMethodName(source, destination,
				destinationMessage);

		Object transformerObject = ReflectionSupport.createObject(classname);

		Class[] parameterTypes = new Class[]
		{Object.class};
		Object[] arguments = new Object[]
		{inObject};

		Object retObject = ReflectionSupport.callMethod(methodname,
				transformerObject, parameterTypes, arguments);

		return retObject;
	}

	private String getClassName(XBUSSystem source, XBUSSystem destination,
			Message destinationMessage) throws XException
	{
		Configuration config = Configuration.getInstance();
		String retString = config.getValueOptional("Transformer", getSection(source, destination,
				destinationMessage), "Class");
		if (retString == null)
		{
			retString = config.getValue("Transformer", getSectionWithFunction(source, destination,
				destinationMessage), "Class");
		}
		return retString;
	}

	private String getMethodName(XBUSSystem source, XBUSSystem destination,
			Message destinationMessage) throws XException
	{
		Configuration config = Configuration.getInstance();
		String retString = config.getValueOptional("Transformer", getSection(source, destination,
				destinationMessage), "Method");
		if (retString == null)
		{			
			retString =  config.getValue("Transformer", getSectionWithFunction(source, destination,
				destinationMessage), "Method");
		}
		return retString;
	}

	private String getSectionWithFunction(XBUSSystem source, XBUSSystem destination,
			Message destinationMessage)
	{
		return new StringBuffer().append(source.getName()).append(".").append(
				destinationMessage.getFunction()).append(".").append(
				destination.getName()).toString();

	}
	private String getSection(XBUSSystem source, XBUSSystem destination,
			Message destinationMessage)
	{
		return new StringBuffer().append(source.getName()).append(".").append(
				destination.getName()).toString();

	}
}

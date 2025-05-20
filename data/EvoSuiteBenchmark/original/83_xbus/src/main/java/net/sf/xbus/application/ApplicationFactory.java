package net.sf.xbus.application;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.protocol.Message;

/**
 * The class <code>ApplicationFactory</code> is used to dynamically create an
 * application object and calls a method of this application object, which
 * processes the incoming <code>Message</code>.
 * <p>
 * It implements the <b>Factory</b> Design-Pattern.
 * <p>
 * 
 * <b>Configuration:</b>
 * <p>
 * <table border>
 * <tr>
 * <th>Chapter</th>
 * <th>Section</th>
 * <th>Key</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>ApplicationFactory</td>
 * <td>call.getSource().call.getFunction()</td>
 * <td>Class</td>
 * <td>Name of the class that should be created.</td>
 * </tr>
 * <tr>
 * <td>ApplicationFactory</td>
 * <td>call.getSource().call.getFunction()</td>
 * <td>Method</td>
 * <td>Name of the method that should process the call.</td>
 * </tr>
 * </table>
 */
public class ApplicationFactory
{
	/**
	 * Creates an application object and calls a method of this application
	 * object, which processes the incoming <code>Message</code> object.
	 * 
	 * @param call the incoming message
	 * @throws XException in case of problems in accessing the configuration, in
	 *             routing, in object instatiation or message processing
	 */
	public static void callApplication(Message call) throws XException
	{
		String section = new StringBuffer().append(call.getSource().getName())
		.append(".").append(call.getFunction()).toString();

		/*
		 * 1. Getting the short name of the class
		 */
		String chapter = "Application";
		Configuration config = Configuration.getInstance();
		String callClassShortname = config.getValueOptional(chapter, call.getSource().getName(),
		"Class");
		if (callClassShortname == null)
		{
		callClassShortname = config.getValueOptional(chapter, section,
				"Class");
		}
		
		/*
		 * 2. Creating the class and calling the method
		 */
		if (callClassShortname == null)
		{
			/*
			 * If there is no entry in the configuration, the Router is used by
			 * default
			 */
			Router router = new Router();
			router.route(call);
		} // then (callClassShortname == null)
		else
		{
			/*
			 * 2.a Getting the complete class name and creating the class
			 */
			String callClass = Configuration.getClass("Application",
					callClassShortname);

			Object applObject = ReflectionSupport.createObject(callClass);

			/*
			 * 2.c Getting the method and invoking it
			 */
			Class[] parameterTypes = new Class[]
			{Message.class};
			Object[] arguments = new Object[]
			{call};

			String applMethod = config.getValueOptional(chapter, call.getSource().getName(), "Method");
			if (applMethod == null)
			{
				applMethod = config.getValue(chapter, section, "Method");
			}

			ReflectionSupport.callMethod(applMethod, applObject,
					parameterTypes, arguments);
		} // else (callClassShortname == null)
	} // callApplication(Message call)

} // ApplicationFactory

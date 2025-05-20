package net.sf.xbus.base.core.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;

/**
 * <code>ReflectionSupport</code> provides methods to create objects and call
 * methods with the reflection API more easy.
 */
public class ReflectionSupport
{
	/**
	 * Create an object of the class with the given name. The class must have a
	 * constructor without parameters.
	 * 
	 * @param classname fully qualified name of the class
	 * @return an object of the class with the given name
	 * @throws XException if the object cannot be created
	 */
	static public Object createObject(String classname) throws XException
	{
		Class classDefinition = classForName(classname);
		Object retObject = null;

		try
		{
			retObject = classDefinition.newInstance();
		}
		catch (InstantiationException e)
		{
			if ((e.getCause() != null) && (e.getCause() instanceof XException))
			{
				/*
				 * If this is an XException it has already been traced, there is
				 * no need to create another XException.
				 */
				try
				{
					throw (XException) e.getCause();
				}
				catch (ClassCastException e1)
				{
					/*
					 * Sometimes it doesn't work, then we throw a XException
					 * explicitly
					 */
					List params = new Vector();
					params.add(classDefinition.getName());
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_COREBASE,
							Constants.PACKAGE_COREBASE_REFLECTION, "1", e,
							params);
				}
			}
			else
			{
				List params = new Vector();
				params.add(classDefinition.getName());
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_COREBASE,
						Constants.PACKAGE_COREBASE_REFLECTION, "1", e, params);
			}
		}
		catch (IllegalAccessException e)
		{
			List params = new Vector();
			params.add(classDefinition.getName());
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_REFLECTION, "2", e, params);
		}

		return retObject;
	}

	/**
	 * Creates an object of the class with the given name.
	 * 
	 * @param classname fully qualified name of the class
	 * @param conArgsClass the parameter types for the constructor
	 * @param conArgs array of objects to be passed as arguments to the
	 *            constructor call
	 * @return a new object created by calling the constructor this object
	 *         represents
	 * @throws XException if the object cannot be created
	 */
	static public Object createObject(String classname, Class[] conArgsClass,
			Object[] conArgs) throws XException
	{
		Object retObject = null;

		try
		{
			Class classDefinition = classForName(classname);
			Constructor objectConstructor = classDefinition
					.getConstructor(conArgsClass);
			retObject = objectConstructor.newInstance(conArgs);
		}
		catch (Exception e)
		{
			if ((e.getCause() != null) && (e.getCause() instanceof XException))
			{
				/*
				 * If this is an XException it has already been traced, there is
				 * no need to create another XException.
				 */
				try
				{
					throw (XException) e.getCause();
				}
				catch (ClassCastException e1)
				{
					/*
					 * Sometimes it doesn't work, then we throw a XException
					 * explicitly
					 */
					List params = new Vector();
					params.add(classname);
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_COREBASE,
							Constants.PACKAGE_COREBASE_REFLECTION, "1", e,
							params);
				}
			}
			else
			{
				List params = new Vector();
				params.add(classname);
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_COREBASE,
						Constants.PACKAGE_COREBASE_REFLECTION, "1", e, params);
			}
		}

		return retObject;
	}

	/**
	 * Returns the <code>Class</code> object associated with the class or
	 * interface with the given string name.
	 * 
	 * @param className the fully qualified name of the desired class
	 * @return the <code>Class</code> object for the class with the specified
	 *         name
	 * @throws XException if the class cannot be found
	 */
	static public Class classForName(String className) throws XException
	{
		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		try
		{
			if ((cl == null) || !(cl instanceof XBUSClassLoader))
			{
				return Class.forName(className, true, XBUSClassLoader
						.getInstance(Thread.currentThread()
								.getContextClassLoader()));
			}
			else
			{
				return Class.forName(className, true, Thread.currentThread()
						.getContextClassLoader());
			}
		}
		catch (ClassNotFoundException e)
		{
			List params = new Vector();
			params.add(className);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_REFLECTION, "3", e, params);
		}
	}

	/**
	 * Calls a method with the help of the reflection API.
	 * 
	 * @param methodname the name of the method to be called
	 * @param inObject the object for which the method shall be called
	 * @param parameterTypes an array containing the classes of the parameters
	 * @param arguments an array with the parameters
	 * @return the result of the method call
	 * @throws XException if the method cannot be called
	 */
	static public Object callMethod(String methodname, Object inObject,
			Class[] parameterTypes, Object[] arguments) throws XException
	{
		Object retObject = null;

		try
		{
			Method method = inObject.getClass().getMethod(methodname,
					parameterTypes);
			retObject = method.invoke(inObject, arguments);
		}
		catch (NoSuchMethodException e)
		{
			List params = new Vector();
			params.add(inObject.getClass().getName());
			params.add(methodname);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_REFLECTION, "4", e, params);
		}
		catch (IllegalAccessException e)
		{
			List params = new Vector();
			params.add(inObject.getClass().getName());
			params.add(methodname);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_REFLECTION, "5", e, params);
		}
		catch (InvocationTargetException e)
		{
			if ((e.getCause() != null) && (e.getCause() instanceof XException))
			{
				/*
				 * If this is an XException it has already been traced, there is
				 * no need to create another XException.
				 */
				try
				{
					throw (XException) e.getCause();
				}
				catch (ClassCastException e1)
				{
					/*
					 * Sometimes it doesn't work, then we throw a XException
					 * explicitly
					 */
					List params = new Vector();
					params.add(inObject.getClass().getName());
					params.add(methodname);
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_COREBASE,
							Constants.PACKAGE_COREBASE_REFLECTION, "6", e,
							params);
				}
			}
			else
			{
				List params = new Vector();
				params.add(inObject.getClass().getName());
				params.add(methodname);
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_COREBASE,
						Constants.PACKAGE_COREBASE_REFLECTION, "6", e, params);
			}
		}
		return retObject;
	}
}

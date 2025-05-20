package net.sf.xbus.admin.soap;

import java.net.URL;

import javax.xml.namespace.QName;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * Restarts the xBus by calling a web service. The xBus must run inside a
 * servlet engine.
 */
public class SOAPAdministrator
{
	/**
	 * Restarts the xBus by calling a web service. The xBus must run inside a
	 * servlet engine.
	 * 
	 * @param host the host were the xBus is running
	 * @param port the port for the web service
	 * @return a message of success or failure
	 * @throws XException if something goes wrong
	 */
	public String restart(String host, Integer port) throws XException
	{
		try
		{
			String endpoint = "http://" + host + ":" + port
					+ "/xbus/services/xBusAdministration";

			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(new URL(endpoint));
			call.setOperationName(new QName("http://soapinterop.org/",
					"restart"));

			return (String) call.invoke(new Object[0]);

		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_ADMIN, Constants.PACKAGE_ADMIN_SOAP, "0", e);
		}
	}
}

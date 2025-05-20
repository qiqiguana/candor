import java.util.HashMap;
import java.util.Map;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;


public class TestP8Conn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String uri = "http://localhost:7001/wsi/FNCEWS40DIME/"; 
		System.out.println("updated");
		Connection conn = Factory.Connection.getConnection(uri); 

		// Get the default domain
		Domain domain = Factory.Domain.getInstance(conn, null);
		// Get an object store
		ObjectStore os = Factory.ObjectStore.fetchInstance(domain, "Sample", null);

		System.out.println("Object store name: " + os.get_Name()); 
	}
	
	public ObjectStore getConnection(String uri, String jassName,
			String userName, String password, String parentObjectStoreName)
	{
		// String uri = "http://10.113.48.249:7001/wsi/FNCEWS40DIME/";
 Map map = new HashMap();
		Connection conn = Factory.Connection.getConnection(uri);

		UserContext uc = UserContext.get();
		uc.pushSubject(UserContext.createSubject(conn, userName, password,
				jassName));

		// Domain domain = Factory.Domain.fetchInstance(conn, "P840Domain",
		// null);
		Domain domain = Factory.Domain.getInstance(conn, null);

		System.out.println("domain name---" + domain.get_Name());

		ObjectStore oObjectStore = Factory.ObjectStore.fetchInstance(domain,
				parentObjectStoreName, null);

		System.out.println("Object Store name---"
				+ oObjectStore.get_DisplayName());
		return oObjectStore;
	}

	public static void main_old(String args[])
	{

		// System.setProperty("java.security.auth.login.con fig","C:\\Program
		// Files\\FileNet\\AE\\CE_API\\config\\jaas.conf.WSI") ;
		System.setProperty("wasp.location",
				"C:\\Program Files\\FileNet\\AE\\CE_API\\wsi");
		// String appId = "com.filenet.acep.CE_Session"; // fully-qualified
		// class name is a good choice

		String uri = "http://10.113.48.249:7001/wsi/FNCEWS40DIME/";

		Connection conn = Factory.Connection.getConnection(uri);

		UserContext uc = UserContext.get();
		uc.pushSubject(UserContext.createSubject(conn, "FBAdminUser",
				"Destiny2007", "FileNetP8WSI"));

		Domain domain = Factory.Domain.fetchInstance(conn, "P840Domain", null);

		System.out.println("domain name---" + domain.get_Name());

		// Get an object store

		com.filenet.api.core.ObjectStore oObjectStore = Factory.ObjectStore
				.fetchInstance(domain, "EVTFS", null);
		// EVTFS is the name of the Object Store
		System.out.println("Object Store name---"
				+ oObjectStore.get_DisplayName());

	}

}

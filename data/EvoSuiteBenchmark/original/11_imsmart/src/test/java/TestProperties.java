import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Properties prop = new Properties();

		try
		{
			prop.load(new FileInputStream("InputDetails.properties"));
			String value = prop.getProperty("docbase");
			System.out.println("Property Value is : "+value);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}

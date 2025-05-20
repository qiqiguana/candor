/**
 * 
 */
import java.io.IOException;
import java.lang.Exception;
import java.sql.*;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * @author Gilles
 *
 */
public class falselight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1- Fichier 
		
		
		//Calendar cal = Calendar.getInstance();
		//String heure=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		//System.out.println(heure);
		
		
		/* Date au format SQL */
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		String formatted = formatter.format(currentTime);
		System.out.println(formatted+"\n");
		
		//Date tg = cal.getTime();
		//System.out.println("tg: "+tg);
		
		
		/* DB */
		/*try {
			// hostname, basename, username, password
			DataBase demo = new DataBase("192.168.1.3:5432","FalseLight","falselight","falselight");
			//int result= demo.executeRequeteSQL("Select * from events;");
			demo.executeUpdateSQL("insert into events values('00:00','FALSE',2)");
						
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		/* Mail */
		//Messages.send("nouzille.matthieu@gmail.com", "tg@tg.fr", "smtp.orange.fr", "tu veut avoir plusieurs filles dans ton lit ?", "Buy viagra !\n discount price $3.30 only !");
		
		/* TESTS */
		//Fichier test = new Fichier("./url.txt");
		//Vector <String> urls = test.readFile();
		//System.out.println(urls.size() + " sites a tester.\n---------------->");
		
		//for(int i=0 ; i<urls.size() ; i++)
		//{
		//	System.out.println(urls.get(i));
		//	System.out.println(Services.testHTTP(urls.get(i), 80));
		//}
		//System.out.println(Services.testSMTP("smtp.orange.fr", 25));
		
		/* schedulage */
		Scheduler sc = new Scheduler();
		

	}

}

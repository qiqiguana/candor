/**
 * Scheduler
 * lire la config (les tests à effectuer) toutes les minutes pour la minute en cour
 * lancer les test grace au pool de thread
 */
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

/**
 * @author Gilles
 *
 */
public class Scheduler {
	
	Timer timer; 
	
	public Scheduler() {
		timer = new Timer("Scheduler", false);
		timer.scheduleAtFixedRate( new TimerTask() //def de la tache a acomplir
			{
				public void run()
					{
					Calendar cal = Calendar.getInstance(); //calcul de la minute en cours
					int thisminute = (cal.get(Calendar.HOUR_OF_DAY)*60) + cal.get(Calendar.MINUTE);
					
					System.out.println("Lecture de la config de la minute  : " + " " + cal.get(Calendar.HOUR_OF_DAY) +
							"h+" + cal.get(Calendar.MINUTE) + "m = "  + thisminute);
					
					timer.cancel();
					}
			}
		, 0 //delay (avant démarage de la tache timé
		, 60000); //fréquence en ms (toutes les minutes)
		
	}

	
	private static class OperateTests implements Runnable
	{
		private Thread testThread;

		public OperateTests() {
			//Init du thread
			testThread = new Thread(this);
			testThread.setName("Tester" + this);
			
		}
		
		public int Test() {
			//lancement du processus de test
			testThread.start();
			return 0;
		}
		
		public void run() {
			//définir le type de test
			
	}
}
}


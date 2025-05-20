package src;

import java.util.TimerTask;

class ReportGenerator extends TimerTask {

  public void run() {
    System.out.println("Generating report");
    //TODO generate report
  }

}

public class AidTool {

  public static void main(String[] args) {
	  CurrentPlayers.getAll().get("Mathew").setFiefdoms("Maine", true);
	  CurrentPlayers.getAll().get("Ryan").getFiefdoms().remove("Maine");
	  CurrentPlayers.getAll().get("Ryan").die();
	  GameAutoActions.saveAll();

  }
}
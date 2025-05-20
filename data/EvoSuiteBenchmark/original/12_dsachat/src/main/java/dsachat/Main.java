package dsachat;

import dsachat.client.gui.MainFrame;
import dsachat.gm.gui.GmFrame;
import dsachat.server.CServer;
import dsachat.server.Handler;
/*
 * FIXME GameMaster TextFrame for sending generated text messages
 * FIXME roll initiative
 * FIXME disconnect button
 * FIXME hold heros online (files and database for login)
 * FIXME Javadoc
 * TODO special challenges for spells?
 * TODO chat commands (additional)
 * IDEA Sonderfertigkeiten, Vorteile
 * IDEA i18n + l10n
 * IDEA automatic fight with selected weapon (to be set) and enemies selected by gm
 * IDEA automatic (solo) GameMaster
 * IDEA Playfield with tokens (players,enemies,landscape/map)
 */
public class Main {
	public static final String version = "0.0.2 (pre-alpha)"; 
	public static void main(String argv[]){
		try{
		    if(argv.length == 0){
		    	MainFrame mf = new MainFrame();
	    		mf.setVisible(true);
		    }else if(argv[0].equals("-p")){
	    		int port=Integer.parseInt(argv[1]);
	    		Handler h = new Handler();
	    		new CServer(port,h).start();
		    }else if(argv[0].equals("-gm")){
		    	GmFrame gf = new GmFrame();
				gf.setVisible(true);
		    }
		}catch(Exception e){
			System.out.println("usage: java -jar DSAChat.jar [-p <PORT>] [-gm]\n" +
					"Start server with -p <PORT> parameter, client without any parameter");
		}
	}
}

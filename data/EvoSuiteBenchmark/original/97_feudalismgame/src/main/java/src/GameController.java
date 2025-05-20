package src;


public class GameController {
	
	public GameController(CommandBean cmdBean){
		Actions actionObj = null;
		
		//Load the action class and run its method that performs that action
		try {
			Class anActionClass = Class.forName("feudalism." + cmdBean.getCommandName());
			try {
				actionObj = (Actions) anActionClass.newInstance();
				actionObj.perform(cmdBean.getArgs());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

package src;

import java.io.Serializable;
import java.util.ArrayList;

public class CommandBean implements Serializable{
	
	private static final long serialVersionUID = 3467694505845130941L;
	private String commandName;
	private ArrayList args;
	
	public CommandBean(){

	}
	
	public CommandBean(String cmdName, ArrayList args){
		this.commandName = cmdName;
		this.args = args;
	}
	
	public ArrayList getArgs() {
		return args;
	}
	public void setArgs(ArrayList args) {
		this.args = args;
	}
	public String getCommandName() {
		return commandName;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
}

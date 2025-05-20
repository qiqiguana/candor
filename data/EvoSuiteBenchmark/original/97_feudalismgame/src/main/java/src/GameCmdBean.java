package src;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author  mpretel
 */
public class GameCmdBean implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="methodName"
	 */
	private String methodName;
	/**
	 * @uml.property  name="args"
	 */
	private ArrayList args;
	
	public GameCmdBean(String methodName, ArrayList args){
		this.methodName = methodName;
		this.args = args;
	}
	
	/**
	 * @return
	 * @uml.property  name="args"
	 */
	public ArrayList<String> getArgs() {
		return args;
	}
	/**
	 * @param args
	 * @uml.property  name="args"
	 */
	public void setArgs(ArrayList args) {
		this.args = args;
	}
	/**
	 * @return
	 * @uml.property  name="methodName"
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName
	 * @uml.property  name="methodName"
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	
	
}

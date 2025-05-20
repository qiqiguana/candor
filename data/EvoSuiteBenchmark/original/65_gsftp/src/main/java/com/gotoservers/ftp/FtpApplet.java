package com.gotoservers.ftp;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;


import mindbright.ssh.SSHPropertyHandler;
import mindbright.ssh.SSHSCPPanel;

public class FtpApplet extends Applet{
    public static final String VERSION = "1.1";
    Button loginButton = new Button("Login"); 
	String server; 
	int port;
	String username;
	String password;
	SSHPropertyHandler props = new SSHPropertyHandler();
	LoginParam lp;
	SSHSCPPanel ftp;
	boolean showDialog = true;
	public String getAppletInfo() {
		return "GoTo Servers FTP Applet v"+VERSION+"\r\nCopyright by GoToServers.com";
	}

	public void init() {
		String s = getParameter("showDialog");
		if( "false".equalsIgnoreCase(s) || "no".equalsIgnoreCase(s) ){
			showDialog = false;
		}
		server = getParameter("server");
		if( server!=null && server.length()>0){
			props.setRemoteServer(server);
		}
		String portS = this.getParameter("port");
		try{
			port=Integer.parseInt(portS);
			if( port>0 ){
				props.setRemotePort(port);
			}
		}catch(Exception e){
			
		}
		username = getParameter("username");
		if( username!=null && username.length()>0){
			props.setRemoteUser(username);
		}
		
		password = getParameter("password");
		if( password!=null && password.length()>0){
			props.setRemotePassword(password);
		}


	}
	protected void login(){
		if( ftp!=null ){
			ftp.close();
			this.remove(ftp);
		}
		ftp = new SSHSCPPanel(props);// //, itor );
		add(ftp,BorderLayout.CENTER);
		ftp.show(props);
		this.validate();
	}
	public void start(){
		Container parent = this.getParent();
		while(parent!=null ){
			if( parent instanceof Frame){
				props.setParent((Frame)parent);
				break;
			}
			parent = parent.getParent();
		}
		
		setLayout(new BorderLayout());
        try{
            Thread.sleep(1000);
        }catch (Exception e) {}
        if( showDialog ){
        	lp = new LoginParam(this,props);
			add(lp,BorderLayout.NORTH);
		}else{
			login();
		}
	}	

	public void destroy(){
		ftp.close();
	}

	//public void actionPerformed(ActionEvent e) {
	//	if( lp.showDialog() ){
	//		this.login();
	//	}
	//}
}

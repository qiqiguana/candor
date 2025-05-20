package src;
 
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
 
public class SendObject extends Thread{
	private Object obj;
	private Socket socket;
	private ObjectOutputStream oos;
	
	public SendObject(Socket socket, Object obj){
		this.obj = obj;	
		this.socket = socket;
	}
	
	public synchronized void run(){
		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			oos.reset();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}

package bierse.view;

public class MyKeyMap {

	private String text;
	private int code;
	
	public MyKeyMap(String text, int code) {
		this.text = text;
		this.code = code;
	}
	
	private MyKeyMap() {};
	
	public String getText() {
		return text;
	}
	
	public int getCode() {
		return code;
	}
	public String toString() {
		return getText();
	}
}

package bierse.view;

import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;

public class KeyMapComboBoxModel extends DefaultComboBoxModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1087130775887552360L;
	
	public static final String KEYNAME_F1 = "F1";
	public static final String KEYNAME_F2 = "F2";
	public static final String KEYNAME_F3 = "F3";
	public static final String KEYNAME_F4 = "F4";
	public static final String KEYNAME_F5 = "F5";
	public static final String KEYNAME_F6 = "F6";
	public static final String KEYNAME_F7 = "F7";
	public static final String KEYNAME_F8 = "F8";
	public static final String KEYNAME_F9 = "F9";
	public static final String KEYNAME_F10 = "F10";
	public static final String KEYNAME_F11 = "F11";
	public static final String KEYNAME_F12 = "F12";
	
	private static final MyKeyMap KM1 = new MyKeyMap(KEYNAME_F1, KeyEvent.VK_F1);
	private static final MyKeyMap KM2 = new MyKeyMap(KEYNAME_F2, KeyEvent.VK_F2);
	private static final MyKeyMap KM3 = new MyKeyMap(KEYNAME_F3, KeyEvent.VK_F3);
	private static final MyKeyMap KM4 = new MyKeyMap(KEYNAME_F4, KeyEvent.VK_F4);
	private static final MyKeyMap KM5 = new MyKeyMap(KEYNAME_F5, KeyEvent.VK_F5);
	private static final MyKeyMap KM6 = new MyKeyMap(KEYNAME_F6, KeyEvent.VK_F6);
	private static final MyKeyMap KM7 = new MyKeyMap(KEYNAME_F7, KeyEvent.VK_F7);
	private static final MyKeyMap KM8 = new MyKeyMap(KEYNAME_F8, KeyEvent.VK_F8);
	private static final MyKeyMap KM9 = new MyKeyMap(KEYNAME_F9, KeyEvent.VK_F9);
	private static final MyKeyMap KM10 = new MyKeyMap(KEYNAME_F10, KeyEvent.VK_F10);
	private static final MyKeyMap KM11 = new MyKeyMap(KEYNAME_F11, KeyEvent.VK_F11);
	private static final MyKeyMap KM12 = new MyKeyMap(KEYNAME_F12, KeyEvent.VK_F12);

	public KeyMapComboBoxModel() {
		this.addElement(KM1);
		this.addElement(KM2);
		this.addElement(KM3);
		this.addElement(KM4);
		this.addElement(KM5);
		this.addElement(KM6);
		this.addElement(KM7);
		this.addElement(KM8);
		this.addElement(KM9);
		this.addElement(KM10);
		this.addElement(KM11);
		this.addElement(KM12);
	}
	
	public static MyKeyMap getMyKeyMapForKeyCode(int keyCode) {
		switch(keyCode) {
		case KeyEvent.VK_F1:
			return KM1;
		case KeyEvent.VK_F2:
			return KM2;
		case KeyEvent.VK_F3:
			return KM3;
		case KeyEvent.VK_F4:
			return KM4;
		case KeyEvent.VK_F5:
			return KM5;
		case KeyEvent.VK_F6:
			return KM6;
		case KeyEvent.VK_F7:
			return KM7;
		case KeyEvent.VK_F8:
			return KM8;
		case KeyEvent.VK_F9:
			return KM9;
		case KeyEvent.VK_F10:
			return KM10;
		case KeyEvent.VK_F11:
			return KM11;
		case KeyEvent.VK_F12:
			return KM12;
		default:
			return null;
		}
	}

}

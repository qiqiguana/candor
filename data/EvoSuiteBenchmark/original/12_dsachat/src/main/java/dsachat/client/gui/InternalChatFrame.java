package dsachat.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class InternalChatFrame extends JInternalFrame
implements ActionListener, MouseListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1182316470653600880L;
	private JList list;
	private DefaultListModel listModel = new DefaultListModel();
	private JTextPane showTxt;
	private JTextField txtInput;
	private MainFrame parent;
	private StyledDocument doc;
	private JPopupMenu listPop;
	private JPopupMenu txtPop;

	public InternalChatFrame(MainFrame parent) {
	    super("Chat",
	          true, //resizable
	          false, //closable
	          true, //maximizable
	          true);//iconifiable
	    this.parent=parent;
	    //...Create the GUI and put it in the window...
	    //...Then set the window size or call pack...
	    createGUI();
	    //Set the window's location.
	    setLocation(0, 0);
	}

	private void createGUI() {
		GridBagConstraints c = new GridBagConstraints();
		JSplitPane split =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		list = new JList(listModel);
		list.setVisible(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(this);
		JScrollPane scrList = new JScrollPane(list);
		
		split.setLeftComponent(scrList);
		JPanel right = new JPanel(new GridBagLayout());
		showTxt = new JTextPane();
		showTxt.setEditable(false);
		showTxt.addMouseListener(this);
		doc = showTxt.getStyledDocument();
		addStylesToDocument(doc);
		
		JScrollPane scrTxt = new JScrollPane(showTxt);
		scrTxt.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1.0;
		c.weighty=0.8;
		c.insets=new Insets(0,0,5,0);
		right.add(scrTxt,c);
		
		txtInput = new JTextField();
		txtInput.addActionListener(parent);
		txtInput.setActionCommand("sendInput");
		
		c.gridx=0;
		c.gridy=1;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1.0;
		c.weighty=0.0;
		c.insets=new Insets(5,0,0,0);
		right.add(txtInput,c);
		
		split.setRightComponent(right);
		split.setDividerLocation(100);
		add(split);
		
		listPop = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("Change Color");
		menuItem.setActionCommand("chooseColor");
		menuItem.addActionListener(this);
		listPop.add(menuItem);
		menuItem = new JMenuItem("whisper");
		menuItem.setActionCommand("whisper");
		menuItem.addActionListener(this);
		listPop.add(menuItem);
		
		txtPop = new JPopupMenu();
		menuItem = new JMenuItem("Change Color");
		menuItem.setActionCommand("bgColor");
		menuItem.addActionListener(this);
		txtPop.add(menuItem);
		
		Dimension drawSize = parent.getSize();
		
		setSize(drawSize.width - 400,drawSize.height-55);
	}
	
	public void addText(String line){
		try {
			if(!line.matches(".*:.*")){//then System message
				doc.insertString(doc.getLength(), line+"\n",
						doc.getStyle("system"));
				return;
			}
			String[] a = line.split(":",2);
			String styleName = a[0];
			if(styleName.matches(".*whispers.*")){
				a = styleName.split("\\s");
				styleName = a[0];
			}
			if(styleName.startsWith("You ")){
				styleName = parent.hero.getName();
			}
			if(styleName.startsWith("*")){
				line = a[0]+a[1];
				styleName = a[0].substring(1);
			}
			Style s = doc.getStyle(styleName);
			
			
			boolean bold = false;
			//bold and italic
			if (line.matches(".*\\*.*") && line.matches(".*[\\+_].*")){
				String[] msgAr = line.split("\\*");
				for(int i=0; i<msgAr.length; i++){
					if(i%2 == 0){
						StyleConstants.setItalic(s, false);
					}else{
						StyleConstants.setItalic(s, true);
					}
					String[] str = msgAr[i].split("[\\+_]");
					if(bold){
						for(int j=0; j<str.length; j++){
							if(j%2==0){
								bold = true;
							}else{
								bold = false;
							}
							StyleConstants.setBold(s, bold);
							doc.insertString(doc.getLength(), str[j],
									s);
						}
					}else{
						for(int j=0; j<str.length; j++){
							if(j%2==0){
								bold = false;
							}else{
								bold = true;
							}
							StyleConstants.setBold(s, bold);
							doc.insertString(doc.getLength(), str[j],
									s);
						}
					}
				}
				StyleConstants.setItalic(s, false);
				StyleConstants.setBold(s, false);
			}
			//italic
			else if(line.matches(".*\\*.*")){
				String[] msgAr = line.split("\\*");
				for(int i=0; i<msgAr.length; i++){
					if(i%2 == 0){
						StyleConstants.setItalic(s, false);
						doc.insertString(doc.getLength(), msgAr[i],
								s);
					} else {
						StyleConstants.setItalic(s, true);
						doc.insertString(doc.getLength(), msgAr[i],
								s);
					}
				}
				StyleConstants.setItalic(s, false);
			}
			//Bold
			else if(line.matches(".*[\\+_].*")){
				String[] msgAr = line.split("[\\+_]");
				for(int i=0; i<msgAr.length; i++){
					if(i%2 == 0){
						StyleConstants.setBold(s, false);
						doc.insertString(doc.getLength(), msgAr[i],
								s);
					} else {
						StyleConstants.setBold(s, true);
						doc.insertString(doc.getLength(), msgAr[i],
								s);
					}
				}
				StyleConstants.setBold(s, false);
			}
			else{
				doc.insertString(doc.getLength(), line,
						s);
			}
			//at last add new line
			doc.insertString(doc.getLength(), "\n",
					doc.getStyle("regular"));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 protected void addStylesToDocument(StyledDocument doc) {
	        //Initialize some styles.
	        Style def = StyleContext.getDefaultStyleContext().
	                        getStyle(StyleContext.DEFAULT_STYLE);

	        Style regular = doc.addStyle("regular", def);
	        StyleConstants.setFontFamily(def, "SansSerif");

	        Style s = doc.addStyle("italic", regular);
	        StyleConstants.setItalic(s, true);

	        s = doc.addStyle("bold", regular);
	        StyleConstants.setBold(s, true);
	        
	        s = doc.addStyle("bold-italic", regular);
	        StyleConstants.setBold(s, true);
	        StyleConstants.setItalic(s, true);
	        
	        s = doc.addStyle("name", regular);
	        StyleConstants.setForeground(s, Color.BLUE);
	        StyleConstants.setFontSize(s, 16);
	        
	        s = doc.addStyle("system", regular);
	        StyleConstants.setForeground(s, new Color(0.0f,0.6f,0.0f, 1.0f) );
	        StyleConstants.setItalic(s, true);
	        StyleConstants.setFontSize(s, 14);

	        s = doc.addStyle("small", regular);
	        StyleConstants.setFontSize(s, 10);

	        s = doc.addStyle("large", regular);
	        StyleConstants.setFontSize(s, 16);
	 }
	 
	 
	 private void changeStyle(int fontSize, Color c,
			 boolean isBold, boolean isItalic, String name){
		 Style s = doc.getStyle(name);
		 if(c != null)
			 StyleConstants.setForeground(s, c);
		 StyleConstants.setBold(s, isBold);
		 StyleConstants.setItalic(s, isItalic);
	 }
	 
	 public void addUser(String user){
		 listModel.add(0, user);
		 list.ensureIndexIsVisible(0);
		 list.setSelectedIndex(0);
		 list.validate();
		 Style parent = doc.getStyle("regular");
		 doc.addStyle(user, parent);
	 }
	 
	 public void deleteUser(String user){
		 listModel.removeElement(user);
	 }
	 
	 public String[] getUserlist(){
		 String[] uList = new String[listModel.size()];
		 for(int i=0; i<listModel.size(); i++){
			 uList[i] = (String)listModel.elementAt(i);
		 }
		 return uList;
	 }

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name = (String)list.getSelectedValue();
		if (e.getActionCommand().equals("chooseColor")){
			Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Color for user "+name,
                    Color.black);
			if(newColor != null) changeStyle(12,newColor,false,false,name);
		}
		if(e.getActionCommand().equals("bgColor")){
			Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Color for user "+name,
                    Color.black);
			if(newColor != null) showTxt.setBackground(newColor);
		}
		if(e.getActionCommand().equals("whisper")){
			String wUser = "/w "+name+" ";
			txtInput.setText(wUser);
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }
	
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
        	if(e.getSource().equals(list)){
	            listPop.show(list,
	                       e.getX(), e.getY());
        	}
        	//background color chooser for showTxt
        	if(e.getSource().equals(showTxt)){
        		txtPop.show(list,
	                       e.getX(), e.getY());
        	}
        }
    }

	public String getChatlog() {
		return showTxt.getText();
	}
}

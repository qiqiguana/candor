/**
 * 
 */
import java.awt.*;
import java.awt.event.*;

/**
 * @author Gilles
 *
 */

public class Ihm {

	TrayIcon trayIcon; // Notre icone dans la barre système
	
public Ihm() {

// On vérifie que le support de la feature du SystemTray est compatible avec l'OS hote
if (SystemTray.isSupported()) {
SystemTray tray = SystemTray.getSystemTray();
Image image = Toolkit.getDefaultToolkit().getImage("duke.gif"); // On récupère l'image qui nous servira d'icone
PopupMenu popup = new PopupMenu(); // Notre menu (clic droit sur l'icone systray)

MenuItem defaultItem = new MenuItem("Quitter");
defaultItem.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {
System.exit(0);
}
});
popup.add(defaultItem);

trayIcon = new TrayIcon(image, "Java 6 Demo !", popup); // Création de l'icone systray

ActionListener actionListener = new ActionListener() {

	public void actionPerformed(ActionEvent e) {
trayIcon.displayMessage("Java 6 new feature !",
"Le System Tray en action !",
TrayIcon.MessageType.INFO);
}
};
trayIcon.setImageAutoSize(true);
trayIcon.addActionListener(actionListener);

try {
tray.add(trayIcon);
} catch (AWTException e) {
e.printStackTrace();
}

 } else {
 // ...
 }
}
} 
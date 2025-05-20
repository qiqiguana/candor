package fr.unice.gfarce.interGraph;

/**
 * classe qui dessine toute l interphace graphique
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import fr.unice.gfarce.identity.Candidat;
import fr.unice.gfarce.identity.Formation;
import fr.unice.gfarce.identity.Identite;
import fr.unice.gfarce.identity.RespFormation;
import fr.unice.gfarce.main.Controler;

public class FenetrePrincipale extends JFrame implements ItemListener, ActionListener {

    /**
     */
    private static final long serialVersionUID = 1L;

    static JTextField textFieldNomResponsable;

    static JTextField textFieldPrenomResponsable;

    static JComboBox comboBoxSexeResponsable;

    static JTextField textFieldEmailResponsable;

    static JTextField textFieldTelResponsable;

    static JTable tableResp;

    static JTextField outputNomResp;

    static JTextField outputPrenomResp;

    static JTextField outputSexeResp;

    static JTextField outputEmailResp;

    static JTextField outputTelResp;

    static JTextField textFieldTitreFormation;

    static JComponent comboBoxDateLimiteFormation;

    static JTextField textFieldPrixDeLaFormation;

    static JComboBox comboBoxResponsableDeLaFormation;

    static JComboBox comboBoxJourDLM;

    static JComboBox comboBoxMoisDLM;

    static JComboBox comboBoxAnneeDLM;

    static JTable tableFormation;

    static JTextField outputTitreFormation;

    static JTextField outputDateLimiteFormation;

    static JTextField outputPrixFormation;

    static JTextField outputRespFormation;

    ModifTableStockage modifTableStockage;

    static JTextField textFieldNomEtudiant;

    static JTextField textFieldPrenomEtudiant;

    static JComboBox comboBoxSexeEtudiant;

    static JTextField textFieldEmailEtudiant;

    static JComponent textFieldPhotoEtudiants;

    static JComponent comboBoxDateNaissanceEtudiant;

    static JTextField textFieldDiplomeEtudiant;

    static JTextField textFieldNationnaliteEtudiant;

    static JTextField textFieldPhotoEtudiant;

    static JComboBox comboBoxJourDDNEtu;

    static JComboBox comboBoxMoisDDNEtu;

    static JComboBox comboBoxAnneeDDNEtu;

    static JComboBox comboBoxFormation1;

    static JComboBox comboBoxFormation2;

    static JTextArea outputRespArea;

    static JLabel titreArea;

    String[] columnNamesResp = { "Nom", "Prenom", "Sexe", "Email", "Telephone" };

    static JTable tableEtu;

    ListSelectionModel listSelectionModel;

    static JTextField outputNom;

    static JTextField outputPrenom;

    static JTextField outputSexe;

    static JTextField outputEmail;

    static JTextField outputDateDeNaissance;

    static JLabel outputPhoto;

    static JTextField outputDiplome;

    static JTextField outputNationalite;

    static JTextField outputFormation;

    static JCheckBox checkBoxAccept;

    static JCheckBox checkBoxBourse;

    static boolean accept;

    static boolean bourse;

    static JTextField montant;

    Object[][] dataResp;

    Zmodel modelResp;

    Zmodel modelForm;

    Zmodel modelEtu;

    Controler c;

    ImageIcon photoEtu;

    String[] columnNamesFormation = { "Titre de la formation", "Date limite", "Prix", "Responsable" };

    Object[][] dataFormation;

    String[] columnNamesEtu = { "Nom", "Prenom", "Sexe", "Email", "Date de naissance", "Photo", "Diplome", "Nationalite", "Formation", "Acceptation", "Bourse" };

    static Object[][] dataEtu;

    /**
     * constructeur qui cree l interface graphique
     * @param c
     */
    public FenetrePrincipale(Controler c) {
    }

    /**
     * creation de la matrice pour la liste deroulante
     * @return matrice pour la liste deroulante responsable
     */
    private Object[][] createListeDeroulanteResponsable();

    /**
     * creation de la matrice pour la liste deroulante
     * @return matrice pour la liste deroulante formation
     */
    private Object[][] createListeDeroulanteFormation();

    /**
     * creation de la matrice pour la liste deroulante
     * @return matrice pour la liste deroulante etudiant
     */
    private Object[][] createListeDeroulanteEtudiant();

    /**
     * creation de la barre d outil
     * @return creation de la barre d outil
     */
    void buildBarreDOutil();

    public void buildOnglet();

    /*
	 * 	reunion des de la creation du premiere ongle
	 */
    JComponent makeFormulaireResponsable();

    /*
	 * 	
	 * 
	 *		|nom :    |
	 *		|prenom : |
	 *		|sexe :   |
	 *		|email :  |
	 */
    JComponent makeFormultext();

    /*
	 * 
	 * 		|textField|
	 * 		|textField|
	 * 		|textField|
	 * 		|textField|
	 * 
	 */
    JComponent makeFormulTextField();

    JComponent makeModifierResponsable();

    JComponent makeFormulaireFormation();

    JComponent makeFormultextFormation();

    JComponent makeFormulTextFieldFormation();

    JComponent makeDateDeLaFormation();

    JComponent makeModifierFormation();

    /*
	 * 	reunion des de la creation du troisieme ongle
	 */
    JComponent makeFormulaireEtudiant();

    JComponent makeFormultextEtudiant();

    JComponent makeFormulTextFieldEtudiant();

    JComponent makeChargeImage();

    JComponent makeDateDeNaissance();

    JComponent makeValidation();

    public void actionPerformed(ActionEvent e);

    /**
     * Lit les checkbox
     */
    public void itemStateChanged(ItemEvent e);

    /**
     * date de la formation
     * @return la date de la formation sous forme de String
     */
    public String dateLimForm();

    /**
     * date de naissance
     * @return la date de naissance sous form de String
     */
    public String dateDeNaiss();

    /**
     * pour l icone
     * @return une image icon, ou null si le chemin est invalide
     */
    protected static ImageIcon createImageIcon(String path);

    /**
     * creation de la fenetre principale
     */
    private void build();

    /**
     * verification si les champ sont editable ou pas
     */
    public void afficheSiCelluleEditable();
}

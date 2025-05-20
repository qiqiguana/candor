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
public class FenetrePrincipale extends JFrame implements ItemListener,ActionListener  {

	/**
	 * 
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

	String[] columnNamesResp = {"Nom",
			"Prenom",
			"Sexe",
			"Email",
	"Telephone"};
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


	String[] columnNamesFormation = {"Titre de la formation",
			"Date limite",
			"Prix",
	"Responsable"};

	Object[][] dataFormation;

	String[] columnNamesEtu = {"Nom",
			"Prenom",
			"Sexe",
			"Email",
			"Date de naissance",
			"Photo",
			"Diplome",
			"Nationalite",
			"Formation",
			"Acceptation",
	"Bourse"};

	static Object[][] dataEtu; 

	/**
	 * constructeur qui cree l interface graphique
	 * @param c
	 */
	public FenetrePrincipale(Controler c){
		super();
		this.c = c;
		dataEtu= createListeDeroulanteEtudiant();//matrice etudiant
		dataFormation= createListeDeroulanteFormation();//matrice formation
		dataResp = createListeDeroulanteResponsable();//matrice responsable
		buildBarreDOutil();//On cree la barre d outil
		buildOnglet();//On cree les onglet
		build();//On initialise notre fenêtre
	}
	/**
	 * creation de la matrice pour la liste deroulante
	 * @return matrice pour la liste deroulante responsable
	 */
	private Object[][] createListeDeroulanteResponsable(){
		RespFormation[] resp = c.findRespForm(null,null);
		if(resp !=null){
			Object[][] matResp = new Object[resp.length][5];
			for(int i=0;i<resp.length;i++){
				matResp[i][0] = resp[i].getNom();
				matResp[i][1] = resp[i].getPrenom();
				matResp[i][2] = resp[i].getSex();
				matResp[i][3] = resp[i].getEmail();
				matResp[i][4] = resp[i].getTelephone();
			}
			return matResp;
		}
		return null;
	}
	/**
	 * creation de la matrice pour la liste deroulante
	 * @return matrice pour la liste deroulante formation
	 */
	private Object[][] createListeDeroulanteFormation(){
		Formation[] tabFormation = c.findFormation(null,null);
		if(tabFormation !=null){
			Object[][] matFormation = new Object[tabFormation.length][4];
			for(int i=0;i<tabFormation.length;i++){
				matFormation[i][0] = tabFormation[i].getTitre_formation();
				matFormation[i][1] = tabFormation[i].getDate_limite_candidature().get(Calendar.DATE)+"/"+tabFormation[i].getDate_limite_candidature().get(Calendar.MONTH)+"/"+tabFormation[i].getDate_limite_candidature().get(Calendar.YEAR);
				matFormation[i][2] = tabFormation[i].getMontant_inscription();
				matFormation[i][3] = tabFormation[i].getResponsable().getNom()+" "+tabFormation[i].getResponsable().getPrenom();
			}
			return matFormation;
		}
		return null;
	}

	/**
	 * creation de la matrice pour la liste deroulante
	 * @return matrice pour la liste deroulante etudiant
	 */
	private Object[][] createListeDeroulanteEtudiant(){
		Candidat[] tabCandidat = c.findCandidat(null,null);
		if(tabCandidat!=null){

			Object[][] matEtu = new Object[tabCandidat.length][11];
			for(int i=0;i<tabCandidat.length;i++){

				matEtu[i][0]= tabCandidat[i].getNom();
				matEtu[i][1]= tabCandidat[i].getPrenom();
				matEtu[i][2]= tabCandidat[i].getSex();
				matEtu[i][3]= tabCandidat[i].getEmail();
				matEtu[i][4]= tabCandidat[i].getDateNaissance().get(Calendar.DATE)+"/"+tabCandidat[i].getDateNaissance().get(Calendar.MONTH)+"/"+tabCandidat[i].getDateNaissance().get(Calendar.YEAR);
				matEtu[i][5]= tabCandidat[i].getPhoto();
				matEtu[i][6]= tabCandidat[i].getDiplome();
				matEtu[i][7]= tabCandidat[i].getNationalite();
				matEtu[i][8]= tabCandidat[i].getFormation().getTitre_formation()+"."+tabCandidat[i].getFormation().getDate_limite_candidature().get(Calendar.DATE)+"/"+tabCandidat[i].getFormation().getDate_limite_candidature().get(Calendar.MONTH)+"/"+tabCandidat[i].getFormation().getDate_limite_candidature().get(Calendar.YEAR);
				if(tabCandidat[i].getAcceptation()==1){
					matEtu[i][9]= "ok";
				}
				else{
					matEtu[i][9]= "non";
				}
				matEtu[i][10]= tabCandidat[i].getBource();
			}
			return matEtu;
		}
		return null;
	}
	/**
	 * creation de la barre d outil
	 * @return creation de la barre d outil
	 */
	void buildBarreDOutil(){

		//---------------------Creation de la barre d outil----------------------------------

		JMenuBar menuBar = new JMenuBar();
		JMenu fichier = new JMenu("Fichier");
		JMenu aide = new JMenu("Aide");

		JMenuItem quitter = new JMenuItem(new QuitterAction("Quitter"));

		fichier.add(quitter);
		menuBar.add(fichier);

		JMenuItem aPropos = new JMenuItem(new AProposAction(this, "A propos"));
		aide.add(aPropos);
		menuBar.add(aide);

		setJMenuBar(menuBar);
		//------------------------------------------------------------------------------------
	}
	/**
	 * construit les different onglet de la fenetre
	 */
	
	public void buildOnglet(){

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = null;

		//1er onglet principal
		JTabbedPane tabbedPaneResp = new JTabbedPane();
		//Premiere ongle responsable
		JComponent panel10 = makeFormulaireResponsable();
		panel10.setLayout(new FlowLayout());
		tabbedPaneResp.addTab("Creer responsable", icon, panel10,
		"");
		tabbedPaneResp.setMnemonicAt(0, KeyEvent.VK_1);
		//2eme onglet responsable
		JComponent panel11 = makeModifierResponsable();
		panel11.setLayout(new FlowLayout());
		tabbedPaneResp.addTab("Modifier responsable", icon, panel11,
		"");
		tabbedPaneResp.setMnemonicAt(1, KeyEvent.VK_2);

		tabbedPane.addTab("Responsable", icon, tabbedPaneResp,
		"");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);




		//Deuxieme ongle principale
		JTabbedPane tabbedPaneFormation = new JTabbedPane();

		//1er onglet creer formation
		JComponent panel20 = makeFormulaireFormation();
		panel20.setLayout(new FlowLayout());
		tabbedPaneFormation.addTab("Creer formation", icon, panel20,
		"");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_4);
		//2eme onglet modification formaion
		JComponent panel21 = makeModifierFormation();
		panel21.setLayout(new FlowLayout());
		tabbedPaneFormation.addTab("modifier une formation", icon, panel21,
		"");
		tabbedPaneFormation.setMnemonicAt(1, KeyEvent.VK_5);


		tabbedPane.addTab("Formation", icon, tabbedPaneFormation,
		"");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_6);



		//3eme onglet principal
		JTabbedPane tabbedPaneEtu = new JTabbedPane();

		//Troisieme ongle
		JComponent panel30 = makeFormulaireEtudiant();
		panel30.setLayout(new FlowLayout());
		tabbedPaneEtu.addTab("Inscrire etudiant", icon, panel30,
		"");
		tabbedPaneEtu.setMnemonicAt(0, KeyEvent.VK_7);

		//Quatrieme ongle
		JComponent panel31 = makeValidation();
		panel31.setLayout(new FlowLayout());
		tabbedPaneEtu.addTab("validation d'inscription et de bourse.", icon, panel31,
		"");
		tabbedPaneEtu.setMnemonicAt(1, KeyEvent.VK_8);
		JScrollPane scrollpaneEtu = new JScrollPane(tabbedPaneEtu);

		tabbedPane.addTab("Etudiant", icon, scrollpaneEtu,
		"");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_9);

		//Add the tabbed pane to this panel.
		add(tabbedPane);

		//The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}


	/********************************************************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * ******************************Creation pour le premiere ongle*****************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 */

	/*
	 * 	reunion des de la creation du premiere ongle
	 */
	JComponent makeFormulaireResponsable() {
		JPanel panel = new JPanel(false);
		Box bVert = Box.createVerticalBox();
		panel.add(bVert);
		Box bHori = Box.createHorizontalBox();
		bVert.add(bHori);
		JComponent formText = makeFormultext();
		JComponent formTextField = makeFormulTextField();
		bHori.add(formText);
		bHori.add(formTextField);

		return panel;
	}
	/*
	 * 	
	 * 
	 *		|nom :    |
	 *		|prenom : |
	 *		|sexe :   |
	 *		|email :  |
	 */
	JComponent makeFormultext() {
		JPanel panel = new JPanel(false);
		JLabel nomResponsable = new JLabel("Nom : *");
		JLabel prenomResponsable = new JLabel("Prenom : *");
		JLabel sexeResponsable = new JLabel("Sexe :");
		JLabel emailResponsable = new JLabel("Email :");
		JLabel telResponsable = new JLabel("Telephone :");
		JButton butonEvoiFormResp = new JButton(new CreationFormateurAction(this, "ENVOI",c));
		Box bVert = Box.createVerticalBox();
		panel.add(bVert);
		bVert.add(nomResponsable);
		bVert.add(Box.createVerticalStrut(4));
		bVert.add(prenomResponsable);
		bVert.add(Box.createVerticalStrut(8));
		bVert.add(sexeResponsable);
		bVert.add(Box.createVerticalStrut(6));
		bVert.add(emailResponsable);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(telResponsable);
		bVert.add(Box.createVerticalStrut(20));
		bVert.add(butonEvoiFormResp);



		return panel;
	}
	/*
	 * 
	 * 		|textField|
	 * 		|textField|
	 * 		|textField|
	 * 		|textField|
	 * 
	 */
	JComponent makeFormulTextField() {
		String[] sexeResponsable = {"M", "F"};
		JPanel panel = new JPanel(false);
		textFieldNomResponsable = new JTextField(10);
		textFieldPrenomResponsable = new JTextField(10);
		comboBoxSexeResponsable = new JComboBox(sexeResponsable);
		textFieldEmailResponsable = new JTextField(10);
		textFieldTelResponsable = new JTextField(10);
		Box bVert = Box.createVerticalBox();
		panel.add(bVert);
		bVert.add(textFieldNomResponsable);
		bVert.add(textFieldPrenomResponsable);
		bVert.add(comboBoxSexeResponsable);
		bVert.add(textFieldEmailResponsable);
		bVert.add(textFieldTelResponsable);
		return panel;
	}



	JComponent makeModifierResponsable(){
		JPanel panel = new JPanel(false);

		Box bVert = Box.createVerticalBox();
		modelResp = new Zmodel(dataResp, columnNamesResp);
		tableResp = new JTable(modelResp);

		listSelectionModel = tableResp.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandlerResp());
		tableResp.setSelectionModel(listSelectionModel);
		tableResp.setPreferredScrollableViewportSize(new Dimension(600, 100));
		tableResp.setFillsViewportHeight(true);

		tableResp.setOpaque(true); //content panes must be opaque
		outputNomResp = new JTextField();
		outputNomResp.setEditable(false);
		outputPrenomResp = new JTextField();
		outputPrenomResp.setEditable(false);
		outputSexeResp = new JTextField();
		outputSexeResp.setEditable(false);
		outputEmailResp = new JTextField();
		outputEmailResp.setEditable(false);
		outputTelResp = new JTextField();
		outputTelResp.setEditable(false);

		JButton envoiResp = new JButton(new EnvoiRespAction(this, "Envoi", c));
		JButton modifierResp = new JButton(new ModifierRespAction(this, "Modifier", c));
		JButton supprimerResp = new JButton(new SupprRespAction(this, "Supprimer", c));
		bVert.add(new JScrollPane(tableResp));
		//bVert.add(scrollPane);

		bVert.add(outputNomResp);
		bVert.add(outputPrenomResp);
		bVert.add(outputSexeResp);
		bVert.add(outputEmailResp);
		bVert.add(outputTelResp);

		JPanel boutonPanel = new JPanel(new FlowLayout());
		boutonPanel.add(envoiResp);
		boutonPanel.add(modifierResp);
		boutonPanel.add(supprimerResp);
		panel.add(bVert);
		panel.add(boutonPanel);

		return panel;
	}
	/********************************************************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * *********************Fin de la Creation pour le premiere ongle*****************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 */


	/********************************************************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * ******************************Creation pour le dexieme ongle*****************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 */


	JComponent makeFormulaireFormation() {
		JPanel panel = new JPanel(false);
		Box bHori = Box.createHorizontalBox();
		panel.add(bHori);
		JComponent formTextFormation = makeFormultextFormation();
		JComponent formTextFieldFormation = makeFormulTextFieldFormation();
		bHori.add(formTextFormation);
		bHori.add(formTextFieldFormation);

		return panel;
	}

	JComponent makeFormultextFormation(){
		JPanel panel = new JPanel(false);
		JLabel titreDeLaFormation = new JLabel("Nom : *");
		JLabel dateLimite = new JLabel("Date limite : *");
		JLabel prixDeLaFormation = new JLabel("Prix de la formation : *");
		JLabel responsableDeLaFormation = new JLabel("Responsable : *");
		JButton butonEvoiFormFormation = new JButton(new CreerUneFormationAction(this,"ENVOI",c));
		Box bVert = Box.createVerticalBox();
		panel.add(bVert);
		bVert.add(Box.createVerticalStrut(0));
		bVert.add(titreDeLaFormation);
		bVert.add(Box.createVerticalStrut(14));
		bVert.add(dateLimite);
		bVert.add(Box.createVerticalStrut(10));
		bVert.add(prixDeLaFormation);
		bVert.add(Box.createVerticalStrut(10));
		bVert.add(responsableDeLaFormation);
		bVert.add(Box.createVerticalStrut(20));
		bVert.add(butonEvoiFormFormation);
		return panel;
	}

	JComponent makeFormulTextFieldFormation() {
		Identite[] idresp= c.findRespForm(null,null);
		String[] listResponsable ={};
		if(idresp !=null){
			listResponsable = new String[idresp.length];
			for(int i=0;i<listResponsable.length;i++){
				listResponsable[i]= idresp[i].getNom()+" "+idresp[i].getPrenom();
			}
		}
		JPanel panel = new JPanel(false);
		textFieldTitreFormation = new JTextField(10);
		comboBoxDateLimiteFormation = makeDateDeLaFormation();
		textFieldPrixDeLaFormation = new JTextField(10);
		comboBoxResponsableDeLaFormation = new JComboBox(listResponsable);
		Box bVert2 = Box.createVerticalBox();
		panel.add(bVert2);
		bVert2.add(textFieldTitreFormation);
		bVert2.add(comboBoxDateLimiteFormation);
		bVert2.add(textFieldPrixDeLaFormation);
		bVert2.add(comboBoxResponsableDeLaFormation);

		return panel;
	}

	JComponent makeDateDeLaFormation(){
		JPanel panel = new JPanel(false);
		panel.setLayout(new FlowLayout());
		String[] jour = new String[31];
		String[] mois = new String[12];
		String[] annee =new String[100];

		for(int i=0; i<31; i++){
			jour[i] = String.valueOf(i+1);
		}

		for(int i=0; i<12; i++){
			mois[i] = String.valueOf(i+1);
		}
		for(int i=0; i<100; i++){
			annee[i] = String.valueOf(i+2009) ;
		}


		comboBoxJourDLM = new JComboBox(jour);
		comboBoxMoisDLM = new JComboBox(mois);
		comboBoxAnneeDLM = new JComboBox(annee);

		panel.add(comboBoxJourDLM);
		panel.add(comboBoxMoisDLM);
		panel.add(comboBoxAnneeDLM);

		return panel;
	}

	JComponent makeModifierFormation(){

		JPanel panel = new JPanel(false);

		Box bVert = Box.createVerticalBox();
		modelForm = new Zmodel(dataFormation, columnNamesFormation);
		tableFormation = new JTable(modelForm);
		listSelectionModel = tableFormation.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandlerFormation());
		tableFormation.setSelectionModel(listSelectionModel);
		tableFormation.setPreferredScrollableViewportSize(new Dimension(600, 100));
		tableFormation.setFillsViewportHeight(true);

		tableFormation.setOpaque(true); //content panes must be opaque
		JScrollPane scrollPane = new JScrollPane(tableFormation);
		outputTitreFormation = new JTextField();
		outputTitreFormation.setEditable(false);
		outputDateLimiteFormation = new JTextField();
		outputDateLimiteFormation.setEditable(false);
		outputPrixFormation = new JTextField();
		outputPrixFormation.setEditable(false);
		outputRespFormation = new JTextField();
		outputRespFormation.setEditable(false);
		titreArea = new JLabel("cout de la formation et liste des candidatures : ");
		outputRespArea = new JTextArea(10,10);
		outputRespArea.setColumns(20);
		outputRespArea.setLineWrap(true);
		outputRespArea.setRows(15);
		outputRespArea.setWrapStyleWord(true);

		JScrollPane scrollPane1 = new JScrollPane(outputRespArea);
		
		JButton envoiFormation = new JButton(new EnvoiFormationAction(this, "Envoi", c));
		JButton modifierFormation = new JButton(new ModifierFormationAction(this, "Modifier", c));
		JButton supprimerFormation = new JButton(new SupprFormationAction(this, "Supprimer", c, modifTableStockage));
		bVert.add(scrollPane);
		bVert.add(outputTitreFormation);
		bVert.add(outputDateLimiteFormation);
		bVert.add(outputPrixFormation);
		bVert.add(outputRespFormation);
		bVert.add(titreArea);
		bVert.add(scrollPane1);
		JPanel boutonPanel = new JPanel(new FlowLayout());
		boutonPanel.add(envoiFormation);
		boutonPanel.add(modifierFormation);
		boutonPanel.add(supprimerFormation);
		panel.add(bVert);
		panel.add(boutonPanel);
		return panel;
	}

	/********************************************************************************************
	 * ******************************************************************************************
	 * *,*****************************************************************************************
	 * *********************Fin de la Creation pour le deuxieme ongle*****************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 */
	/********************************************************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * ******************************Creation pour le troisieme ongle*****************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 */

	/*
	 * 	reunion des de la creation du troisieme ongle
	 */
	JComponent makeFormulaireEtudiant() {
		JPanel panel = new JPanel(false);
		Box bHori = Box.createHorizontalBox();
		panel.add(bHori);
		JComponent formTextEtudiant = makeFormultextEtudiant();
		JComponent formTextFieldEtudiant = makeFormulTextFieldEtudiant();
		bHori.add(formTextEtudiant);
		bHori.add(formTextFieldEtudiant);
		return panel;
	}

	JComponent makeFormultextEtudiant() {
		JPanel panel = new JPanel(false);
		JLabel nomEtudiant = new JLabel("Nom : *");
		JLabel prenomEtudiant = new JLabel("Prenom : *");
		JLabel sexeEtudiant = new JLabel("Sexe :");
		JLabel emailEtudiant = new JLabel("Email : *");
		JLabel dateDeNaissanceEtudiant = new JLabel("Date de naissance : *");
		JLabel photoEtudiant = new JLabel("Photo : *");
		JLabel diplomeEtudiant = new JLabel("diplome : *");
		JLabel nationnaliteEtudiant = new JLabel("nationalite :");
		JLabel choixDeLaFormation = new JLabel("Choix de la formation :");
		JButton butonEvoiFormEtudiant = new JButton(new CreerUnEtudiantAction(this, "ENVOI",c));


		Box bVert = Box.createVerticalBox();
		panel.add(bVert);
		bVert.add(nomEtudiant);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(prenomEtudiant);
		bVert.add(Box.createVerticalStrut(6));
		bVert.add(sexeEtudiant);
		bVert.add(Box.createVerticalStrut(8));
		bVert.add(emailEtudiant);
		bVert.add(Box.createVerticalStrut(13));
		bVert.add(dateDeNaissanceEtudiant);
		bVert.add(Box.createVerticalStrut(15));
		bVert.add(photoEtudiant);
		bVert.add(Box.createVerticalStrut(14));
		bVert.add(diplomeEtudiant);
		bVert.add(Box.createVerticalStrut(4));
		bVert.add(nationnaliteEtudiant);
		bVert.add(Box.createVerticalStrut(7));
		bVert.add(choixDeLaFormation);
		bVert.add(Box.createVerticalStrut(20));
		bVert.add(butonEvoiFormEtudiant);
		return panel;
	}

	JComponent makeFormulTextFieldEtudiant(){
		Formation[] form = c.findAllFormation();
		String[] listFormation ={};
		if(form !=null){
			listFormation=new String[form.length];
			for(int i=0;i<form.length;i++){
				form[i].getDate_limite_candidature();
				listFormation[i]= form[i].getTitre_formation()+"."+form[i].getDate_limite_candidature().get(Calendar.DATE)+"/"+form[i].getDate_limite_candidature().get(Calendar.MONTH)+"/"+form[i].getDate_limite_candidature().get(Calendar.YEAR);
			}
		}
		String[] sexeEtudiant = {"M", "F"};
		JPanel panel = new JPanel(false);
		textFieldNomEtudiant = new JTextField(10);
		textFieldPrenomEtudiant = new JTextField(10);
		comboBoxSexeEtudiant = new JComboBox(sexeEtudiant);
		textFieldEmailEtudiant = new JTextField(10);
		comboBoxDateNaissanceEtudiant = makeDateDeNaissance();
		textFieldPhotoEtudiants = makeChargeImage();
		textFieldDiplomeEtudiant = new JTextField(10);
		textFieldNationnaliteEtudiant = new JTextField(10);
		comboBoxFormation1 = new JComboBox(listFormation);

		Box bVert = Box.createVerticalBox();
		panel.add(bVert);

		bVert.add(textFieldNomEtudiant);
		bVert.add(textFieldPrenomEtudiant);
		bVert.add(comboBoxSexeEtudiant);
		bVert.add(textFieldEmailEtudiant);
		bVert.add(comboBoxDateNaissanceEtudiant);
		bVert.add(textFieldPhotoEtudiants);
		bVert.add(textFieldDiplomeEtudiant);
		bVert.add(textFieldNationnaliteEtudiant);
		bVert.add(comboBoxFormation1);
		return panel;
	}

	JComponent makeChargeImage(){
		JPanel panel = new JPanel(false);
		panel.setLayout(new FlowLayout());
		textFieldPhotoEtudiant = new JTextField(10);
		JButton boutonChargeImage = new JButton(new ChargeImageAction(this, "CHARGER"));
		panel.add(textFieldPhotoEtudiant);
		panel.add(boutonChargeImage);
		return panel;
	}

	JComponent makeDateDeNaissance(){
		JPanel panel = new JPanel(false);
		panel.setLayout(new FlowLayout());
		String[] jourEtu = new String[31];
		String[] moisEtu = new String[12];
		String[] anneeEtu = new String[100];

		for(int i=0; i<31; i++){
			jourEtu[i] = String.valueOf(i+1);
		}

		for(int i=0; i<12; i++){
			moisEtu[i] = String.valueOf(i+1);
		}
		for(int i=0; i<100; i++){
			anneeEtu[i] = String.valueOf(i+1918) ;
		}

		comboBoxJourDDNEtu = new JComboBox(jourEtu);
		comboBoxMoisDDNEtu = new JComboBox(moisEtu);
		comboBoxAnneeDDNEtu = new JComboBox(anneeEtu);

		panel.add(comboBoxJourDDNEtu);
		panel.add(comboBoxMoisDDNEtu);
		panel.add(comboBoxAnneeDDNEtu);

		return panel;
	}



	/********************************************************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * *********************Fin de la Creation pour le troisieme ongle*****************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 */

	/********************************************************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * ******************************Creation pour le quatrieme ongle*****************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 */



	JComponent makeValidation(){
		String[] listFormation ={};
		Formation[] form = c.findAllFormation();
		if(form !=null){
			listFormation=new String[form.length];
			for(int i=0;i<form.length;i++){
				form[i].getDate_limite_candidature();
				listFormation[i]= form[i].getTitre_formation()+"."+form[i].getDate_limite_candidature().get(Calendar.DATE)+"/"+form[i].getDate_limite_candidature().get(Calendar.MONTH)+"/"+form[i].getDate_limite_candidature().get(Calendar.YEAR);
			}
		}
		JPanel panel = new JPanel(false);
		modelEtu = new Zmodel(dataEtu, columnNamesEtu);
		tableEtu = new JTable(modelEtu);
		panel.setLayout(new FlowLayout());
		tableEtu.getColumnModel().getColumn(5).setResizable(false);
		tableEtu.getColumnModel().getColumn(5).setPreferredWidth(0);
		tableEtu.getColumnModel().getColumn(5).setMaxWidth(0);
		tableEtu.getColumnModel().getColumn(5).setMinWidth(0);
		//tableEtu = new JTable(dataEtu, columnNamesEtu);
		listSelectionModel = tableEtu.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandlerEtu());
		tableEtu.setSelectionModel(listSelectionModel);
		tableEtu.setPreferredScrollableViewportSize(new Dimension(700, 150));
		tableEtu.setFillsViewportHeight(true);
		JPanel checkPanel = new JPanel(new FlowLayout());
		JPanel boutonPanel = new JPanel(new FlowLayout());

		tableEtu.setOpaque(true); //content panes must be opaque
		JScrollPane scrollPane = new JScrollPane(tableEtu);
		comboBoxFormation2 = new JComboBox(listFormation);
		comboBoxFormation2.addActionListener(this);
		outputNom = new JTextField();
		outputNom.setEditable(false);
		outputPrenom = new JTextField();
		outputPrenom.setEditable(false);
		outputSexe = new JTextField();
		outputSexe.setEditable(false);
		outputEmail = new JTextField();
		outputEmail.setEditable(false);
		outputDateDeNaissance = new JTextField();
		outputDateDeNaissance.setEditable(false);
		outputPhoto = new JLabel();
		//outputPhoto.setEditable(false);
		outputDiplome = new JTextField();
		outputDiplome.setEditable(false);
		outputNationalite = new JTextField();
		outputNationalite.setEditable(false);
		outputFormation = new JTextField();
		outputFormation.setEditable(false);

		checkBoxAccept = new JCheckBox("accept");

		checkBoxAccept.addItemListener(this);


		checkBoxBourse = new JCheckBox("bourse :");
		checkBoxBourse.addItemListener(this);

		montant = new JTextField(10);
		montant.setEnabled(false);

		JButton envoiAcceptBourse = new JButton(new EnvoiAcceptBourseAction(this, "ENVOI", c));
		JButton modificationEtu = new JButton(new ModificationEtuAction(this, "MODIFIER", c));
		JButton supprimeEtu = new JButton(new SupprimeEtuAction(this, "SUPPRIMER", c));
		JButton importExel = new JButton(new ExportExelAction(this, "exporter table", c));
		Box bVert = Box.createVerticalBox();
		panel.add(bVert);

		bVert.add(comboBoxFormation2);

		bVert.add(scrollPane);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputNom);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputPrenom);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputSexe);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputEmail);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputDateDeNaissance);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputPhoto);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputDiplome);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputNationalite);
		bVert.add(Box.createVerticalStrut(5));
		bVert.add(outputFormation);


		checkPanel.add(checkBoxAccept);
		checkPanel.add(checkBoxBourse);
		checkPanel.add(montant);
		bVert.add(checkPanel);
		boutonPanel.add(envoiAcceptBourse);
		boutonPanel.add(modificationEtu);
		boutonPanel.add(supprimeEtu);
		boutonPanel.add(importExel);
		bVert.add(boutonPanel);





		return panel;
	}
	public void actionPerformed(ActionEvent e){
		System.out.println("apuis resp");
	}
	/********************************************************************************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 * *********************Fin de la Creation pour le quatrieme ongle*****************************
	 * ******************************************************************************************
	 * ******************************************************************************************
	 */


	/** 
	 * Lit les checkbox
	 **/
	public void itemStateChanged(ItemEvent e) {

		Object source = e.getItemSelectable();

		if (source == checkBoxAccept) {
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				checkBoxBourse.setSelected(false);
				montant.setEnabled(false);
				accept = false;
			}
			System.out.println("accept");
			accept = true;
		} else if (source == checkBoxBourse) {
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				montant.setEnabled(false);
				bourse = false;
			}
			else{
				checkBoxAccept.setSelected(true);
				montant.setEnabled(true);
				bourse = true;
			}
		}

	}
	
	/**
	 * date de la formation
	 * @return la date de la formation sous forme de String
	 */
	public String dateLimForm(){
		String date = comboBoxJourDLM.getSelectedItem().toString()+comboBoxMoisDLM.getSelectedItem().toString()+comboBoxAnneeDLM.getSelectedItem().toString();
		return date;
	}
	/**
	 * date de naissance
	 * @return la date de naissance sous form de String
	 */
	public String dateDeNaiss(){
		String date = comboBoxJourDDNEtu.getSelectedItem().toString()+comboBoxMoisDDNEtu.getSelectedItem().toString()+comboBoxAnneeDDNEtu.getSelectedItem().toString();
		return date;
	}
	/** 
	 * pour l icone
	 * @return une image icon, ou null si le chemin est invalide
	 */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = FenetrePrincipale.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * creation de la fenetre principale
	 */
	private void build(){	
		setTitle("Projet !!!!!"); //On donne un titre à l'application
		setSize(800,600); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
	}
	/**
	 * verification si les champ sont editable ou pas
	 */
	public void afficheSiCelluleEditable(){

		for(int i=0; i<dataResp.length; i++){
			for(int j=0; j<dataResp[0].length; j++){
				System.out.println(tableResp.isCellEditable(j, j));
			}
		}
	}


}



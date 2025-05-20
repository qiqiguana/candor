package fr.unice.gfarce.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import fr.unice.gfarce.dao.DaoFactory;
import fr.unice.gfarce.dao.DaoFactory.TypeFabrique;
import fr.unice.gfarce.dao.DaoFactoryException;
import fr.unice.gfarce.dao.FormationDao;
import fr.unice.gfarce.dao.IdentiteDao;
import fr.unice.gfarce.identity.Candidat;
import fr.unice.gfarce.identity.Formation;
import fr.unice.gfarce.identity.Identite;
import fr.unice.gfarce.identity.Identite.TypeIdentite;
import fr.unice.gfarce.identity.RespFormation;
import fr.unice.gfarce.interGraph.FenetreChoix;
import fr.unice.gfarce.xls.WritingXLS;

/**
 * Fait la liaison entre l'interface graphique et le moteur de l'application
 * Rend comprehensible les requetes de l'interface graphique pour les bases de
 * donnees
 *
 * @author scarankle
 */
public class Controler {

    DaoFactory df;

    IdentiteDao idao;

    FormationDao fdao;

    Candidat[] tabcand;

    String formationName;

    TypeFabrique tf;

    /**
     * Constructeur initialisant les dao et le type de la base
     * @param tf type de la base
     * @throws DaoFactoryException
     */
    public Controler() throws DaoFactoryException {
    }

    public void setTypeDataBase(String type) throws DaoFactoryException;

    public void launchDatabase() throws IOException;

    /**
     */
    private void bdChoice(TypeFabrique tfab) throws DaoFactoryException;

    /**
     * Cree un candidat ou un responsable de formation et l'insert dans la base
     * @param nom
     * @param prenom
     * @param sex
     * @param email
     * @param type
     * @param date_naissance
     * @param diplome
     * @param photo
     * @param nationalite
     * @param telephone
     * @param bourse
     * @param acceptation
     * @param formation
     * @throws IOException
     * @return le candidat ou le responsable de formation
     */
    public Identite createIdentite(String nom, String prenom, String sex, String email, TypeIdentite type, String date_naissance, String diplome, String photo, String nationalite, String telephone, int bourse, int acceptation, String formation) throws IOException;

    /**
     * cree un calendar a partir d'une string date de la forme dd/mm/yyyy
     * @param date
     * @return
     */
    private Calendar creerCalendar(String date);

    /**
     * cree une formation et l'enregistre dans la base
     * @param titre
     * @param date_limite
     * @param montant
     * @param responsable
     * @return
     */
    public Formation createFormation(String titre, String date_limite, int montant, String responsable);

    /**
     * Cherche des candidats dans la base
     * @param nom
     * @param prenom
     * @return un tableau contenant les candidats
     */
    public Candidat[] findCandidat(String nom, String prenom);

    /**
     * Cherche des responsables de formation dans la base
     * @param nom
     * @param prenom
     * @return un tableau contenant les responsables de formation
     */
    public RespFormation[] findRespForm(String nom, String prenom);

    /**
     * Cherche des formations dans la base
     * @param name
     * @param date
     * @return un tableau contenant les formations
     */
    public Formation[] findFormation(String name, String date);

    /**
     * Retourne toutes les formations
     * @return un tableau contenant toutes les formations
     */
    public Formation[] findAllFormation();

    /**
     * Retourne tous les Candidats d'une formation donnee
     * @param formation
     * @param date
     * @return tableau contenant tous les etudiants d'une formation donnee
     */
    public Candidat[] findCandidatInFormation(String formation, String date);

    /**
     * Exporte sous forme de fichier excel les candidats d'une formation
     * @throws RowsExceededException
     * @throws WriteException
     * @throws IOException
     * @throws ParseException
     */
    public void exportExcel(String form) throws RowsExceededException, WriteException, IOException, ParseException;

    /**
     * Fait un update d'un candidat
     * @param nom
     * @param prenom
     * @param sex
     * @param email
     * @param date_naissance
     * @param diplome
     * @param photo
     * @param nationalite
     * @param bourse
     * @param acceptation
     * @param formation
     * @throws Exception
     */
    public void updateCandidat(String nom, String prenom, String sex, String email, String date_naissance, String diplome, byte[] photo, String nationalite, int bourse, String acceptation, String formation) throws Exception;

    /**
     * Fait un update d'un responsable
     * @param nom
     * @param prenom
     * @param sex
     * @param email
     * @param telephone
     * @throws Exception
     */
    public void updateResponsable(String nom, String prenom, String sex, String email, String telephone) throws Exception;

    /**
     * Fait un update d'une formation
     * @param titre
     * @param date_limite
     * @param montant
     * @param responsable
     */
    public void updateFormation(String titre, String date_limite, int montant, String responsable);

    /**
     * Enleve un candidat ou un responsable de formation de la base
     * @param nom
     * @param prenom
     */
    public void deleteIdentite(String nom, String prenom, TypeIdentite type);

    /**
     * Enleve une formation de la base
     * @param nom
     * @param date
     */
    public void deleteFormation(String nom, String date);
}

package fr.unice.gfarce.dao;

import java.util.Calendar;

import fr.unice.gfarce.identity.Candidat;
import fr.unice.gfarce.identity.Identite;
import fr.unice.gfarce.identity.RespFormation;

public interface IdentiteDao {
	public void insert(Identite ident, String nomForm, Calendar dateLimite);
	public void delete(Identite identite);
	public Candidat[] findCandidat(String nom,String prenom,String sex, String email);
	public RespFormation[] findRespForm(String nom,String prenom,String sex, String email);
	public void update(Identite identity) throws Exception;
}

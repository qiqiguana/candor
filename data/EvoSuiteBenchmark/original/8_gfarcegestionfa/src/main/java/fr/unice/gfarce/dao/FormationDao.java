package fr.unice.gfarce.dao;

import java.util.Calendar;

import fr.unice.gfarce.identity.Formation;

public interface FormationDao {
	public void insert(Formation f,String nomResp, String prenomResp);
	public void delete(Formation form);
	public Formation[] findFormation(String name,Calendar date);
	public void update(Formation form);
}

package fr.unice.gfarce.dao;

import java.io.IOException;

public class Db4oDaoFactory extends DaoFactory{

	@Override
	public IdentiteDao getIdentiteDao() throws IOException {
		return new Db4oIdentiteFactory();
	}

	@Override
	public FormationDao getFormationDao() throws IOException {
		return new Db4oFormationFactory();
	}

}

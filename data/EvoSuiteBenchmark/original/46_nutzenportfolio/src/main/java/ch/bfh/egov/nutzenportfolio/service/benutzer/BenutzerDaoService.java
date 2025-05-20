/**
 * Nutzenportfolio
 * Copyright (C) 2006 Kompetenzzentrum E-Business, Simon Bergamin

 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ch.bfh.egov.nutzenportfolio.service.benutzer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.bfh.egov.nutzenportfolio.persistence.benutzer.BenutzerDao;
import ch.bfh.egov.nutzenportfolio.tos.Benutzer;

/**
 * Implementierende Service-Klasse für Benutzer.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class BenutzerDaoService implements BenutzerService {
  private BenutzerDao dao;
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für Benutzer. 
   * 
   * @param dao                         Benutzer DataAcessObject-Interface                      
   */
  public BenutzerDaoService(BenutzerDao dao) {
      this.dao = dao;
  }
    
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.benutzer.BenutzerIbatisDao#getBenutzer(Integer)
   */
  public Benutzer getBenutzer(Integer id) {
    return dao.getBenutzer(id);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.benutzer.BenutzerIbatisDao#getBenutzerByBenutzername(String)
   */
  public Benutzer getBenutzerByBenutzername(String benutzername) {
    return dao.getBenutzerByBenutzername(benutzername);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.benutzer.BenutzerIbatisDao#getAllBenutzer(Integer)
   */
  public List<Benutzer> getAllBenutzer(Integer mandantId) {
    return dao.getAllBenutzer(mandantId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.benutzer.BenutzerIbatisDao#insertBenutzer(Benutzer)
   */
  public void insertBenutzer(Benutzer benutzer) {
    dao.insertBenutzer(benutzer);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.benutzer.BenutzerIbatisDao#updateBenutzer(Benutzer)
   */
  public void updateBenutzer(Benutzer benutzer) {
    dao.updateBenutzer(benutzer);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.benutzer.BenutzerIbatisDao#deleteBenutzer(Integer)
   */
  public void deleteBenutzer(Integer id) {
    dao.deleteBenutzer(id);
  }
  
  /**
   * Überprüft das Login eines Benutzers.
   * 
   * @param benutzer    die Login-Daten des Benutzers
   * @return            true bei Erfolg, sonst false
   */
  public Benutzer loginSuccessful(Benutzer benutzer) {
    Benutzer user = dao.getBenutzerByBenutzername(benutzer.getBenutzername());
    if (user != null) {
      // TODO add encrypted passwords
      logger.debug("Benutzername: " + user.getBenutzername());
      logger.debug("Passwort: " + user.getPasswort());
      if (user.getPasswort().equals(benutzer.getPasswort())) {
        return user;
      }
    }
    return null;
  }

}

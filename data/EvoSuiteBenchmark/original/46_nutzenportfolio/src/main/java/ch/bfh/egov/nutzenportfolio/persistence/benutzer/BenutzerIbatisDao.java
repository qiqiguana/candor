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
package ch.bfh.egov.nutzenportfolio.persistence.benutzer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Benutzer;

/**
 * Implementierende Data Access Object-Klasse für Benutzer.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class BenutzerIbatisDao extends SqlMapClientTemplate implements BenutzerDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt alle Benutzer eines Mandanten.
   * 
   * @param mandantId   die Id des Mandanten
   * @return            Liste aller Benutzer
   */
  public List<Benutzer> getAllBenutzer(Integer mandantId) {
    return queryForList("Benutzer.getAll", mandantId);
  }
  
  /**
   * Holt einen Benutzer anhand seiner Id.
   * 
   * @param id          die Id des Benutzers
   * @return            den Benutzer
   */
  public Benutzer getBenutzer(Integer id) {
    return (Benutzer) queryForObject("Benutzer.getById", id);
  }
  
  /**
   * Holt einen Benutzer anhand seines Benutzernamens.
   * 
   * @param benutzername    Benutzername des Benutzers
   * @return                den Benutzer
   */
  public Benutzer getBenutzerByBenutzername(String benutzername) {
    return (Benutzer) queryForObject("Benutzer.getByBenutzername", benutzername);
  }
  
  /**
   * Speichert einen neuen Benutzer.
   * 
   * @param benutzer    Benutzer-Objekt mit benötigten Daten
   */
  public void insertBenutzer(Benutzer benutzer) {
    insert("Benutzer.insert", benutzer);
  }
  
  /**
   * Ändert einen bestehenden Benutzer.
   * 
   * @param benutzer    Benutzer-Objekt mit benötigten Daten
   */
  public void updateBenutzer(Benutzer benutzer) {
    update("Benutzer.update", benutzer);
  }
  
  /**
   * Löscht einen bestehenden Benutzer.
   * 
   * @param id    Id des Benutzers
   */
  public void deleteBenutzer(Integer id) {
    update("Benutzer.delete", id);
  }
}

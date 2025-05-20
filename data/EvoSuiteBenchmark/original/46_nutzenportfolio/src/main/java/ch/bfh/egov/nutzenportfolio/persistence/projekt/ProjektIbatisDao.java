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
package ch.bfh.egov.nutzenportfolio.persistence.projekt;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.Projektgruppe;

/**
 * Implementierende Data Access Object-Klasse für Projekte.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class ProjektIbatisDao extends SqlMapClientTemplate implements ProjektDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt alle Projekte eines Mandanten.
   * 
   * @param mandantId   die Id des Mandanten
   * @return            Liste aller Projekte
   */
  public List<Projekt> getAll(Integer mandantId) {
    return queryForList("Projekt.getAll", mandantId);
  }

  /**
   * Holt alle Projekte der angegebenen Projektgruppe eines Mandanten.
   * 
   * @param pg  Projektgruppe der gesuchten Projekte 
   * @return    Liste der Projekte
   */
  public List<Projekt> getGroup(Projektgruppe pg) {
    return queryForList("Projekt.getGroup", pg);
  }

  /**
   * Holt ein Projekt anhand seiner Id.
   * 
   * @param p           Projekt-Objekt mit benötigten Daten
   * @return            das Projekt
   */
  public Projekt getById(Projekt p) {
    return (Projekt) queryForObject("Projekt.getById", p);
  }
  
  /**
   * Holt ein Projekt anhand seines Namens.
   * 
   * @param p             Projekt-Objekt mit benötigten Daten
   * @return              das Projekt
   */
  public Projekt getByName(Projekt p) {
    return (Projekt) queryForObject("Projekt.getByName", p);
  }
  
  /**
   * Holt ein Projekt anhand seiner UID.
   * 
   * @param projektUID    die UID des Projekts
   * @return              das Projekt
   */
  public Projekt getByUID(Long projektUID) {
    return (Projekt) queryForObject("Projekt.getByUID", projektUID);
  }
  
  /**
   * Speichert ein neues Projekt.
   * 
   * @param p           Projekt-Objekt mit benötigten Daten
   */
  public Integer insert(Projekt p) {
    return (Integer) insert("Projekt.insert", p);
  }
  
  /**
   * Ändert ein bestehendes Projekt.
   * 
   * @param p           Projekt-Objekt mit benötigten Daten
   */
  public void update(Projekt p) {
    update("Projekt.update", p);
  }
  
  /**
   * Ändert die Status eines bestehenden Projekts.
   * 
   * @param p           Projekt-Objekt mit benötigten Daten
   */
  public void updateStatus(Projekt p) {
    update("Projekt.updateStatus", p);
  }
  
  /**
   * Löscht ein bestehendes Projekt.
   * 
   * @param p     das zu löschende Projekt
   */
  public void delete(Projekt p) {
    update("Projekt.delete", p);
  }
}

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
package ch.bfh.egov.nutzenportfolio.persistence.strategischeziele;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

import java.util.List;

/**
 * Implementierende Data Access Object-Klasse für Strategische Ziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class StrategischeZieleIbatisDao
    extends SqlMapClientTemplate
    implements StrategischeZieleDao {
  Log logger = LogFactory.getLog(this.getClass());

  /**
   * Holt alle Strategischen Ziele eines Mandanten.
   * 
   * @param mandantId   die Id des Mandanten
   * @return            Liste aller Strategischen Ziele
   */
  public List<StrategischesZiel> getAll(Integer mandantId) {
    return queryForList("StrategischesZiel.getAll", mandantId);
  }
  
  /**
   * Holt ein Strategisches Ziel anhand seiner Id.
   * 
   * @param ziel        Strategisches Ziel-Objekt mit benötigten Daten
   * @return            das Strategische Ziel
   */
  public StrategischesZiel getById(StrategischesZiel ziel) {
    return (StrategischesZiel) queryForObject("StrategischesZiel.getById", ziel);
  }
  
  /**
   * Holt ein Strategisches Ziel anhand seines Namens.
   * 
   * @param ziel          Strategisches Ziel-Objekt mit benötigten Daten
   * @return              das Strategische Ziel
   */
  public StrategischesZiel getByName(StrategischesZiel ziel) {
    return (StrategischesZiel) queryForObject("StrategischesZiel.getByName", ziel);
  }
  
  /**
   * Holt alle Strategischen Ziele, die dem angegeben Projekt zugewiesen sind.
   * 
   * @param p           Projekt-Objekt mit benötigten Daten
   * @return            Liste aller Strategischen Ziele die den Kriterien entsprechen
   */
  public List<StrategischesZiel> getByProjektId(Projekt p) {
    return queryForList("StrategischesZiel.getByProjektId", p);
  }
  
  /**
   * Holt alle Strategischen Ziele, die der angegeben Projektattraktivität
   * zugewiesen sind.
   * 
   * @param ziel        Strategisches Ziel-Objekt mit benötigten Daten
   * @return            Liste aller Strategischen Ziele die den Kriterien entsprechen
   */
  public List<StrategischesZiel> getAssignments(StrategischesZiel ziel) {
    return queryForList("StrategischesZiel.getAssignments", ziel);
  }
  
  /**
   * Holt alle Customizings, die dem angegeben Strategischen Ziel
   * zugewiesen sind.
   * 
   * @param ziel        Strategisches Ziel-Objekt mit benötigten Daten
   * @return            Liste aller Customizings die den Kriterien entsprechen
   */
  public List<Customizing> getLinkedCustomizings(StrategischesZiel ziel) {
    return queryForList("StrategischesZiel.getLinkedCustomizings", ziel);
  }
  
  /**
   * Holt alle Detailziele, die dem angegeben Strategisches Ziel
   * zugewiesen sind.
   * 
   * @param ziel        Strategisches Ziel-Objekt mit benötigten Daten
   * @return            Liste aller Detailziele die den Kriterien entsprechen
   */
  public List<Detailziel> getLinkedDetailziele(StrategischesZiel ziel) {
    return queryForList("StrategischesZiel.getLinkedDetailziele", ziel);
  }
  
  /**
   * Speichert ein neues Strategisches Ziel.
   * 
   * @param ziel           Strategisches Ziel-Objekt mit benötigten Daten
   */
  public void insert(StrategischesZiel ziel) {
    insert("StrategischesZiel.insert", ziel);
  }
  
  /**
   * Ändert ein bestehendes Strategisches Ziel.
   * 
   * @param ziel           Strategisches Ziel-Objekt mit benötigten Daten
   */
  public void update(StrategischesZiel ziel) {
    update("StrategischesZiel.update", ziel);
  }
  
  /**
   * Löscht ein bestehendes Strategisches Ziel.
   * 
   * @param ziel     das zu löschende Strategisches Ziel
   */
  public void delete(StrategischesZiel ziel) {
    delete("StrategischesZiel.delete", ziel);
  }
  
  /**
   * Speichert eine neue Zuweisung eines Strategischen Ziels zu einer
   * Projektattraktivität.
   * 
   * @param ziel           Strategisches Ziel-Objekt mit benötigten Daten
   */
  public void insertAssignment(StrategischesZiel ziel) {
    insert("StrategischesZiel.insertAssignment", ziel);
  }
  
  /**
   * Löscht alle bestehenden Zuweisungen eines Strategischen Ziels zu einer
   * Projektattraktivität.
   * 
   * @param projektattraktivitaetId   die Id der Projektattraktivität
   */
  public void deleteAssignments(Integer projektattraktivitaetId) {
    delete("StrategischesZiel.deleteAssignments", projektattraktivitaetId);
  }
}

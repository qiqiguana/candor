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
package ch.bfh.egov.nutzenportfolio.persistence.detailziele;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;

import java.util.List;

/**
 * Implementierende Data Access Object-Klasse für Detailziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class DetailzieleIbatisDao extends SqlMapClientTemplate implements DetailzieleDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt alle Detailziele eines Mandanten.
   * 
   * @param mandantId   die Id des Mandanten
   * @return            Liste aller Detailziele
   */
  public List<Detailziel> getAll(Integer mandantId) {
    return queryForList("Detailziel.getAll", mandantId);
  }
  
  /**
   * Holt ein Detailziel anhand seiner Id.
   * 
   * @param ziel        Detailziel-Objekt mit benötigten Daten
   * @return            das Detailziel
   */
  public Detailziel getById(Detailziel ziel) {
    return (Detailziel) queryForObject("Detailziel.getById", ziel);
  }
  
  /**
   * Holt ein Detailziel anhand seines Namens.
   * 
   * @param ziel        Detailziel-Objekt mit benötigten Daten
   * @return            das Detailziel
   */
  public Detailziel getByName(Detailziel ziel) {
    return (Detailziel) queryForObject("Detailziel.getByName", ziel);
  }
  
  /**
   * Holt alle Detailziele, die dem angegeben Projekt zugewiesen sind.
   * 
   * @param p           Projekt-Objekt mit benötigten Daten
   * @return            Liste aller Detailziele die den Kriterien entsprechen
   */
  public List<Detailziel> getByProjektId(Projekt p) {
    return queryForList("Detailziel.getByProjektId", p);
  }
  
  /**
   * Holt alle Detailziele, die dem angegeben Strategischen Ziel
   * zugewiesen sind.
   * 
   * @param ziel        Detailziel-Objekt mit benötigten Daten
   * @return            Liste aller Detailziele die den Kriterien entsprechen
   */
  public List<Detailziel> getByStrategischesZiel(Detailziel ziel) {
    return queryForList("Detailziel.getByStrategischesZiel", ziel);
  }
  
  /**
   * Holt alle Detailziele, die dem angegeben Strategischen Ziel
   * und der angegeben Projektattraktivität zugewiesen sind.
   * 
   * @param ziel        Detailziel-Objekt mit benötigten Daten
   * @return            Liste aller Detailziele die den Kriterien entsprechen
   */
  public List<Detailziel> getByPaAndStrategischesZiel(Detailziel ziel) {
    return queryForList("Detailziel.getByPaAndStrategischesZiel", ziel);
  }
  
  /**
   * Holt alle Detailziele, die der angegeben Projektattraktivität
   * zugewiesen sind.
   * 
   * @param ziel        Detailziel-Objekt mit benötigten Daten
   * @return            Liste aller Detailziele die den Kriterien entsprechen
   */
  public List<Detailziel> getAssignments(Detailziel ziel) {
    return queryForList("Detailziel.getAssignments", ziel);
  }
  
  /**
   * Holt alle Customizings, die dem angegeben Detailziel
   * zugewiesen sind.
   * 
   * @param ziel        Detailziel-Objekt mit benötigten Daten
   * @return            Liste aller Customizings die den Kriterien entsprechen
   */
  public List<Customizing> getLinkedCustomizings(Detailziel ziel) {
    return queryForList("Detailziel.getLinkedCustomizings", ziel);
  }
  
  /**
   * Speichert ein neues Detailziel.
   * 
   * @param ziel           Detailziel-Objekt mit benötigten Daten
   */
  public void insert(Detailziel ziel) {
    insert("Detailziel.insert", ziel);
  }
  
  /**
   * Ändert ein bestehendes Detailziel.
   * 
   * @param ziel           Detailziel-Objekt mit benötigten Daten
   */
  public void update(Detailziel ziel) {
    update("Detailziel.update", ziel);
  }
  
  /**
   * Löscht ein bestehendes Detailziel.
   * 
   * @param detailzielId    die Id des zu löschenden Detailziels
   */
  public void delete(Integer detailzielId) {
    delete("Detailziel.delete", detailzielId);
  }
  
  /**
   * Speichert eine neue Zuweisung eines Detailziels zu einer
   * Projektattraktivität.
   * 
   * @param ziel           Detailziel-Objekt mit benötigten Daten
   */
  public void insertAssignment(Detailziel ziel) {
    insert("Detailziel.insertAssignment", ziel);
  }
  
  /**
   * Löscht eine bestehende Zuweisung eines Detailziels zu einer
   * Projektattraktivität.
   * 
   * @param ziel           Detailziel-Objekt mit benötigten Daten
   */
  public void deleteAssignment(Detailziel ziel) {
    delete("Detailziel.deleteAssignment", ziel);
  }
  
  /**
   * Löscht alle bestehenden Zuweisungen eines Detailziels zu einer
   * Projektattraktivität.
   * 
   * @param projektattraktivitaetId   die Id der Projektattraktivität
   */
  public void deleteAssignments(Integer projektattraktivitaetId) {
    delete("Detailziel.deleteAssignments", projektattraktivitaetId);
  }
}

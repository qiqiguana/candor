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
package ch.bfh.egov.nutzenportfolio.persistence.nutzenkriterium;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

import java.util.List;

/**
 * Implementierende Data Access Object-Klasse für Nutzenkriterien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class NutzenkriteriumIbatisDao extends SqlMapClientTemplate implements NutzenkriteriumDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt alle Nutzenkriterien eines Mandanten.
   * 
   * @param mandantId   die Id des Mandanten
   * @return            Liste aller Nutzenkriterien die den Kriterien entsprechen
   */
  public List<Nutzenkriterium> getAll(Integer mandantId) {
    return queryForList("Nutzenkriterium.getAll", mandantId);
  }
  
  /**
   * Holt ein Nutzenkriterium anhand seiner Id.
   * 
   * @param kriterium       Nutzenkriterium-Objekt mit benötigten Daten
   * @return                das Nutzenkriterium
   */
  public Nutzenkriterium getById(Nutzenkriterium kriterium) {
    return (Nutzenkriterium) queryForObject("Nutzenkriterium.getById", kriterium);
  }
  
  /**
   * Holt ein Nutzenkriterium anhand seines Namens.
   * 
   * @param kriterium       Nutzenkriterium-Objekt mit benötigten Daten
   * @return                das Nutzenkriterium
   */
  public Nutzenkriterium getByName(Nutzenkriterium kriterium) {
    return (Nutzenkriterium) queryForObject("Nutzenkriterium.getByName", kriterium);
  }
  
  /**
   * Holt die Standard-Fragen eines Nutzenkriteriums.
   * 
   * @param kriterium       Nutzenkriterium-Objekt mit benötigten Daten
   * @return                das Nutzenkriterium
   */
  public Nutzenkriterium getDefaultFragen(Nutzenkriterium kriterium) {
    return (Nutzenkriterium) queryForObject("Nutzenkriterium.getDefaultFragen", kriterium);
  }
  
  /**
   * Holt die Zuweisung eines Nutzenkriteriums zu einer Nutzenattraktivität
   * bzw. eines Operativen Nutzens
   * 
   * @param kriterium       Nutzenkriterium-Objekt mit benötigten Daten
   * @return                das Nutzenkriterium
   */
  public Nutzenkriterium getAssignmentById(Nutzenkriterium kriterium) {
    return (Nutzenkriterium) queryForObject("Nutzenkriterium.getAssignmentById", kriterium);
  }
  
  /**
   * Holt alle Nutzenkriterien, die dem angegeben Projekt zugewiesen sind.
   * 
   * @param naOpNu  Nutzenattraktivität / Operativer Nutzen-Objekt
   *                mit benötigten Daten
   * @return        Liste aller Nutzenkriterien die den Kriterien entsprechen
   */
  public List<Nutzenkriterium> getByProjektId(NaOpNu naOpNu) {
    return queryForList("Nutzenkriterium.getByProjektId", naOpNu);
  }
  
  /**
   * Holt alle Nutzenkriterien, die der angegeben Kategorie zugewiesen sind.
   * 
   * @param kriterium   Nutzenkriterium-Objekt mit benötigten Daten
   * @return            Liste aller Nutzenkriterien die den Kriterien entsprechen
   */
  public List<Nutzenkriterium> getByKategorie(Nutzenkriterium kriterium) {
    return queryForList("Nutzenkriterium.getByKategorie", kriterium);
  }
  
  /**
   * Holt alle Nutzenkriterien, die einer angegebenen Nutzenattraktivität
   * bzw. eines Operativen Nutzens und einer angegebenen Kategorie
   * zugewiesen sind.
   * 
   * @param kriterium   Nutzenkriterium-Objekt mit benötigten Daten
   * @return            Liste aller Nutzenkriterien die den Kriterien entsprechen
   */
  public List<Nutzenkriterium> getByNaOpNuAndKategorie(Nutzenkriterium kriterium) {
    return queryForList("Nutzenkriterium.getByNaOpNuAndKategorie", kriterium);
  }
  
  /**
   * Holt alle Nutzenkriterien, die einer angegebenen Nutzenattraktivität
   * bzw. eines Operativen Nutzens  zugewiesen sind.
   * 
   * @param kriterium   Nutzenkriterium-Objekt mit benötigten Daten
   * @return            Liste aller Nutzenkriterien die den Kriterien entsprechen
   */
  public List<Nutzenkriterium> getAssignments(Nutzenkriterium kriterium) {
    return queryForList("Nutzenkriterium.getAssignments", kriterium);
  }
  
  /**
   * Holt alle Customizings, die mit diesem Nutzenkriterium verknüpft sind.
   * 
   * @param kriterium   Nutzenkriterium-Objekt mit benötigten Daten
   * @return            Liste aller verknüpften Customizings
   */
  public List<Customizing> getLinkedCustomizings(Nutzenkriterium kriterium) {
    return queryForList("Nutzenkriterium.getLinkedCustomizings", kriterium);
  }
  
  /**
   * Ändert eine Zuweisung zu diesem Nutzenkriterium.
   * 
   * @param kriterium        Nutzenkriterium-Objekt mit benötigten Daten
   */
  public void updateAssignment(Nutzenkriterium kriterium) {
    update("Nutzenkriterium.updateAssignment", kriterium);
  }
  
  /**
   * Ändert die Gewichtung einer Zuweisung zu diesem Nutzenkriterium.
   * 
   * @param kriterium        Nutzenkriterium-Objekt mit benötigten Daten
   */
  public void updateAssignmentGewichtung(Nutzenkriterium kriterium) {
    update("Nutzenkriterium.updateAssignmentGewichtung", kriterium);
  }
  
  /**
   * Löscht die Gewichtung einer Zuweisung zu 
   *  der angegebenen Nutzenattraktivität bzw. des Operativen Nutzens.
   * 
   * @param naOpNuId        die Id der Nutzenattraktivität bzw. des
   *                        Operativen Nutzens
   */
  public void unsetAssignmentGewichtung(Integer naOpNuId) {
    update("Nutzenkriterium.unsetAssignmentGewichtung", naOpNuId);
  }
  
  /**
   * Speichert ein neues Nutzenkriterium.
   * 
   * @param kriterium        Nutzenkriterium-Objekt mit benötigten Daten
   */
  public void insert(Nutzenkriterium kriterium) {
    insert("Nutzenkriterium.insert", kriterium);
  }
  
  /**
   * Ändert ein bestehendes Nutzenkriterium.
   * 
   * @param kriterium        Nutzenkriterium-Objekt mit benötigten Daten
   */
  public void update(Nutzenkriterium kriterium) {
    update("Nutzenkriterium.update", kriterium);
  }
  
  /**
   * Löscht ein bestehendes Nutzenkriterium.
   * 
   * @param kriterium        Nutzenkriterium-Objekt mit benötigten Daten
   */
  public void delete(Nutzenkriterium kriterium) {
    delete("Nutzenkriterium.delete", kriterium);
  }
  
  /**
   * Speichert eine neue Zuweisung zu diesem Nutzenkriterium.
   * 
   * @param kriterium        Nutzenkriterium-Objekt mit benötigten Daten
   */
  public void insertAssignment(Nutzenkriterium kriterium) {
    insert("Nutzenkriterium.insertAssignment", kriterium);
  }
  
  /**
   * Löscht eine bestehende Zuweisung zu diesem Nutzenkriterium.
   * 
   * @param kriterium        Nutzenkriterium-Objekt mit benötigten Daten
   */
  public void deleteAssignment(Nutzenkriterium kriterium) {
    delete("Nutzenkriterium.deleteAssignment", kriterium);
  }
  
  /**
   * Löscht alle bestehenden Zuweisungen von Nutzenkriterien 
   * zu der angegebenen Nutzenattraktivität bzw. des Operativen Nutzens.
   * 
   * @param naOpNuId        die Id der Nutzenattraktivität bzw. des
   *                        Operativen Nutzens
   */
  public void deleteAssignments(Integer naOpNuId) {
    delete("Nutzenkriterium.deleteAssignments", naOpNuId);
  }
}

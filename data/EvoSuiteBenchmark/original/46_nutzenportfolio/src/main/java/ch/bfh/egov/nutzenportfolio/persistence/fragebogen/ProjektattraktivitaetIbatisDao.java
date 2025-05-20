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
package ch.bfh.egov.nutzenportfolio.persistence.fragebogen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Projektattraktivitaet;

/**
 * Implementierende Data Access Object-Klasse für den
 * Fragebogen Projektattraktivität.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class ProjektattraktivitaetIbatisDao
    extends SqlMapClientTemplate
    implements ProjektattraktivitaetDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holte eine Projektattraktivität anhand der E-Mail-Adresse.
   * 
   * @param pa        die Projektattraktivität mit den benötigten Daten
   * @return          die Projektattraktivität
   */
  public Projektattraktivitaet getByEmail(Projektattraktivitaet pa) {
    return (Projektattraktivitaet) queryForObject("Projektattraktivitaet.getByEmail", pa);
  }
  
  /**
   * Holte eine Projektattraktivität anhand seiner UID.
   * 
   * @param paUID     die UID der Projektattraktivität
   * @return          die Projektattraktivität
   */
  public Projektattraktivitaet getByUID(Long paUID) {
    return (Projektattraktivitaet) queryForObject("Projektattraktivitaet.getByUID", paUID);
  }
  
  /**
   * Speicher eine neues Resultat einer Projektattraktivität.
   * 
   * @param p         die Projektattraktivität mit den benötigten Daten
   * @return          die Id der Projektattraktivität
   */
  public Integer insertProjektattraktivitaetResultat(Projektattraktivitaet p) {
    return (Integer) insert("Projektattraktivitaet.insertProjektattraktivitaetResultat", p);
  }
  
  /**
   * Löscht alle Resultate einer Projektattraktivität.
   * 
   * @param customizingId     die Id des Customizings
   */
  public void deleteAssignments(Integer customizingId) {
    delete("Projektattraktivitaet.deleteAssignments", customizingId);
  }

}

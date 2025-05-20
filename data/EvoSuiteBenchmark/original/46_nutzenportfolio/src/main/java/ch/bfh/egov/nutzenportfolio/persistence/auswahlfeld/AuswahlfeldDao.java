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
package ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld;

import java.util.List;

import ch.bfh.egov.nutzenportfolio.tos.Auswahlfeld;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;

/**
 * Data Access Object-Interface für Auswahlfelder.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface AuswahlfeldDao {
  public Auswahlfeld getById(Auswahlfeld a);
  public Auswahlfeld getByName(Auswahlfeld a);
  public List<Auswahlfeld> getByProjektId(Auswahlfeld a);
  public List<Auswahlfeld> getRealisierbarkeitStatus(Integer paId);
  public List<Auswahlfeld> getAbstufungStatus(Integer naOpNuId);
  public List<Auswahlfeld> getGewichtungStatus(Integer naOpNuId);
  public List<Auswahlfeld> getAnzahlAbstufungen(Integer naOpNuId);
  public List<Auswahlfeld> getAnzahlGewichtungen(Integer naOpNuId);
  public List<Auswahlfeld> getSelectedByPaId(Auswahlfeld a);
  public List<Auswahlfeld> getSelectedByNaOpNuId(Auswahlfeld a);
  public List<Auswahlfeld> getByNaOpNu(Auswahlfeld a);
  public List<Auswahlfeld> getAuswahlFelder(Auswahlfeld a);
  public void insert(Auswahlfeld a);
  public void update(Auswahlfeld a);
  public void delete(Auswahlfeld a);
  public List<Customizing> getLinkedPaCustomizings(Auswahlfeld a);
  public List<Customizing> getLinkedNaOpNuCustomizings(Auswahlfeld a);
  public void insertAssignment(Auswahlfeld a);
  public void insertNaOpNuAssignment(Auswahlfeld a);
  public void deleteAssignments(Auswahlfeld a);
  public void deleteNaOpNuAssignments(Auswahlfeld a);
  public void deleteNaOpNuAssignmentsByNutzenkriterium(Auswahlfeld a);
  public void deleteGewichtungen(Auswahlfeld a);
}


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

import java.util.List;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

/**
 * Data Access Object-Interface für Nutzenkriterien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface NutzenkriteriumDao {
  public List<Nutzenkriterium> getAll(Integer mandantId);
  public Nutzenkriterium getById(Nutzenkriterium kriterium);
  public Nutzenkriterium getByName(Nutzenkriterium kriterium);
  public Nutzenkriterium getDefaultFragen(Nutzenkriterium kriterium);
  public Nutzenkriterium getAssignmentById(Nutzenkriterium kriterium);
  public List<Nutzenkriterium> getByProjektId(NaOpNu naOpNu);
  public List<Nutzenkriterium> getByKategorie(Nutzenkriterium kriterium);
  public List<Nutzenkriterium> getByNaOpNuAndKategorie(Nutzenkriterium kriterium);
  public List<Nutzenkriterium> getAssignments(Nutzenkriterium kriterium);
  public List<Customizing> getLinkedCustomizings(Nutzenkriterium kriterium);
  public void insert(Nutzenkriterium kriterium);
  public void update(Nutzenkriterium kriterium);
  public void delete(Nutzenkriterium kriterium);
  public void insertAssignment(Nutzenkriterium kriterium);
  public void updateAssignment(Nutzenkriterium kriterium);
  public void updateAssignmentGewichtung(Nutzenkriterium kriterium);
  public void unsetAssignmentGewichtung(Integer naOpNuId);
  public void deleteAssignment(Nutzenkriterium kriterium);
  public void deleteAssignments(Integer naOpNuId);
}
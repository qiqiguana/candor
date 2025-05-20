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

import java.util.List;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;

/**
 * Data Access Object-Interface für Detailziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface DetailzieleDao {
  public List<Detailziel> getAll(Integer mandantId);
  public Detailziel getById(Detailziel ziel);
  public Detailziel getByName(Detailziel ziel);
  public List<Detailziel> getByProjektId(Projekt p);
  public List<Detailziel> getByStrategischesZiel(Detailziel ziel);
  public List<Detailziel> getByPaAndStrategischesZiel(Detailziel ziel);
  public List<Detailziel> getAssignments(Detailziel ziel);
  public List<Customizing> getLinkedCustomizings(Detailziel ziel);
  public void insert(Detailziel ziel);
  public void update(Detailziel ziel);
  public void delete(Integer detailzielId);
  public void insertAssignment(Detailziel ziel);
  public void deleteAssignment(Detailziel ziel);
  public void deleteAssignments(Integer projektattraktivitaetId);
}


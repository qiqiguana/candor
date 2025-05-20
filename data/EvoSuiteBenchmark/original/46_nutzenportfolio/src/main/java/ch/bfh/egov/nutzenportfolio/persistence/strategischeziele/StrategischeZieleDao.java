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

import java.util.List;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

/**
 * Data Access Object-Interface für Strategische Ziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface StrategischeZieleDao {
    public List<StrategischesZiel> getAll(Integer mandantId);
    public StrategischesZiel getById(StrategischesZiel ziel);
    public StrategischesZiel getByName(StrategischesZiel ziel);
    public List<StrategischesZiel> getByProjektId(Projekt projekt);
    public List<StrategischesZiel> getAssignments(StrategischesZiel ziel);
    public List<Customizing> getLinkedCustomizings(StrategischesZiel ziel);
    public List<Detailziel> getLinkedDetailziele(StrategischesZiel ziel);
    public void insert(StrategischesZiel ziel);
    public void update(StrategischesZiel ziel);
    public void delete(StrategischesZiel ziel);
    public void insertAssignment(StrategischesZiel ziel);
    public void deleteAssignments(Integer projektattraktivitaetId);
}


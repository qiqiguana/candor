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
package ch.bfh.egov.nutzenportfolio.service.strategischeziele;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.detailziele.DetailzieleService;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

/**
 * Service-Interface für Strategische Ziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface StrategischeZieleService {
  
  // Daten Zugriff
  public List<StrategischesZiel> getAll(Integer mandantId);
  public List<StrategischesZiel> getByProjektId(Projekt projekt);
  public List<StrategischesZiel> getAssignments(StrategischesZiel ziel);
  
  // Business Logik
  public void init(CustomizingService cService, DetailzieleService dzService);
  public void populate(HttpServletRequest request);
  public void edit(HttpServletRequest request, DynaActionForm form);
  public void update(HttpServletRequest request, DynaActionForm form);
  public StrategischesZiel getStrategischesZiel(
      HttpServletRequest request,
      DynaActionForm form);
  public void deleteQuestion(
      HttpServletRequest request,
      StrategischesZiel sz,
      ActionMessages messages);
  public void delete(HttpServletRequest request, StrategischesZiel sz);
  public void setProjektattraktivitaet(List<StrategischesZiel> ziele);
  public boolean add(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors,
      ActionMessages messages);
  public Integer[] getSelectedIdArr(StrategischesZiel ziel);
  public void copy(Integer mandantId, Integer customizingId, Integer newPaId);
  public List<StrategischesZiel> setStatus(
      HttpServletRequest request,
      Integer mandantId,
      Integer customizingId);
}

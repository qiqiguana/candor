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
package ch.bfh.egov.nutzenportfolio.service.detailziele;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

/**
 * Service-Interface für Detailziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface DetailzieleService {
  
  // Daten Zugriff
  public Detailziel getById(Detailziel ziel);
  public List<Detailziel> getAll(Integer mandantId);
  public List<Detailziel> getByProjektId(Projekt p);
  public List<Detailziel> getByStrategischesZiel(Detailziel ziel);
  public List<Detailziel> getByPaAndStrategischesZiel(Detailziel ziel);
  public List<Detailziel> getAssignments(Detailziel ziel);
  public void update(Detailziel ziel);
  public void insertAssignment(Detailziel ziel);
  public void deleteAssignment(Detailziel ziel);
  public void deleteAssignments(Integer projektattraktivitaetId);
  
  // Business Logik
  public void init(
      CommonService cService,
      CustomizingService customizingService,
      StrategischeZieleService szService);
  public void populate(HttpServletRequest request);
  public void edit(HttpServletRequest request, DynaActionForm form);
  public void update(HttpServletRequest request, DynaActionForm form);
  public Detailziel getDetailziel(HttpServletRequest request, DynaActionForm form);
  public void deleteQuestion(
      HttpServletRequest request,
      Detailziel dz,
      ActionMessages messages);
  public void linkedTo(HttpServletRequest request, Detailziel dz);
  public void delete(HttpServletRequest request, Detailziel dz);
  public boolean add(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors,
      ActionMessages messages);
  public void copy(
      Integer mandantId,
      Integer paId,
      Integer newPaId,
      boolean quantifizierbar);
  public boolean setStatus(
      HttpServletRequest request,
      List<StrategischesZiel> szs,
      Integer mandantId,
      Integer paId);
}

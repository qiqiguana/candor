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
package ch.bfh.egov.nutzenportfolio.service.kategorie;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Kategorie;

/**
 * Service-Interface für Kategorien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface KategorieService {
  
  // Datenzugriff
  public List<Kategorie> getAll(Integer mandantId);
  public List<Kategorie> getByProjektId(NaOpNu naOpNu);
  public List<Kategorie> getAssignments(Kategorie k);
  public void updateAssignment(Kategorie k);
  public void deleteAssignments(Integer naOpNuId);
  
  // Business Logik
  public void init(
      CommonService cService,
      CustomizingService customizingService,
      NutzenkriteriumService nService);
  public void list(HttpServletRequest request);
  public void populate(HttpServletRequest request, DynaActionForm form);
  public void edit(HttpServletRequest request, DynaActionForm form);
  public void update(HttpServletRequest request, DynaActionForm form);
  public Kategorie getKategorie(
      HttpServletRequest request,
      DynaActionForm form);
  public void deleteQuestion(
      HttpServletRequest request,
      Kategorie kategorie,
      ActionMessages messages);
  public void delete(HttpServletRequest request, Kategorie kategorie);
  public boolean save(HttpServletRequest request, DynaActionForm form);
  public boolean add(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors,
      ActionMessages messages);
  public void copy(Integer mandantId, Integer naOpNuId, Integer newNaOpNuId);
  public List<Kategorie> setStatus(
      HttpServletRequest request,
      Integer mandantId,
      Integer naOpNuId,
      boolean isNa);
}

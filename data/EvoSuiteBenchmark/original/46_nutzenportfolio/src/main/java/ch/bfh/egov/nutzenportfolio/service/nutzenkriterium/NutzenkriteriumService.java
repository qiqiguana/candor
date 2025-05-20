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
package ch.bfh.egov.nutzenportfolio.service.nutzenkriterium;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.form.FragenForm;
import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.kategorie.KategorieService;
import ch.bfh.egov.nutzenportfolio.tos.Kategorie;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

/**
 * Service-Interface für Nutzenkriterien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface NutzenkriteriumService {
  
  // Datenzugriff
  public Nutzenkriterium getAssignmentById(Nutzenkriterium kriterium);
  public List<Nutzenkriterium> getByProjektId(NaOpNu naOpNu);
  public List<Nutzenkriterium> getAssignments(Nutzenkriterium nutzenkriterium);
  public void update(Nutzenkriterium nutzenkriterium);
  public void unsetAssignmentGewichtung(Integer naOpNuId);
  public void deleteAssignments(Integer naOpNuId);
  
  // Business Logik
  public void init(
      AuswahlfeldService aService,
      CommonService cService,
      KategorieService kService,
      CustomizingService customizingService);
  public void list(HttpServletRequest request);
  public boolean populate(HttpServletRequest request);
  public void edit(HttpServletRequest request, DynaActionForm form);
  public void update(HttpServletRequest request, DynaActionForm form);
  public Nutzenkriterium getNutzenkriterium(HttpServletRequest request, DynaActionForm form);
  public void deleteQuestion(
      HttpServletRequest request,
      Nutzenkriterium nutzenkriterium,
      ActionMessages messages);
  public void linkedTo(HttpServletRequest request, Nutzenkriterium nutzenkriterium);
  public void delete(HttpServletRequest request, Nutzenkriterium nutzenkriterium);
  public void set(HttpServletRequest request, DynaActionForm form);
  public boolean next(HttpServletRequest request);
  public void remove(HttpServletRequest request, DynaActionForm form);
  public boolean populateFragen(HttpServletRequest request, FragenForm form);
  public void populateDirekteGewichtung(HttpServletRequest request, FragenForm form);
  public boolean saveFragen(HttpServletRequest request, FragenForm form, ActionMessages errors);
  public boolean saveDirekteGewichtung(HttpServletRequest request, FragenForm form, ActionMessages errors);
  public boolean direkteGewichtung(HttpServletRequest request);
  public boolean add(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors,
      ActionMessages messages);
  public List<Nutzenkriterium> setStatus(
      HttpServletRequest request,
      List<Kategorie> kategorien,
      Integer mandantId,
      Integer naOpNuId,
      boolean isNa);
  public void deleteFragebogenGewichtung(HttpServletRequest request);
  public void preselect(HttpServletRequest request, String path);
  public void copy(Integer mandantId, Integer naOpNuId, Integer newNaOpNuId);
  public boolean customizingComplete(HttpServletRequest request);
}

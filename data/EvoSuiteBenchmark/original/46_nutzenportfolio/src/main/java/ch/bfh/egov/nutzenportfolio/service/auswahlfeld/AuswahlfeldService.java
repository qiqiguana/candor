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
package ch.bfh.egov.nutzenportfolio.service.auswahlfeld;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Auswahlfeld;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

/**
 * Service-Interface für Auswahlfelder.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface AuswahlfeldService {
  
  // Datenzugriff
  public Auswahlfeld getById(Auswahlfeld a);
  public List<Auswahlfeld> getByProjektId(Auswahlfeld a);
  public List<Auswahlfeld> getByNaOpNu(Auswahlfeld a);
  public List<Auswahlfeld> getGewichtungStatus(Integer naOpNuId);
  public List<Auswahlfeld> getAbstufungStatus(Integer naOpNuId);
  public List<Auswahlfeld> getAnzahlGewichtungen(Integer naOpNuId);
  public List<Auswahlfeld> getAnzahlAbstufungen(Integer naOpNuId);
  public List<Auswahlfeld> getSelectedByPaId(Auswahlfeld a);
  public List<Auswahlfeld> getSelectedByNaOpNuId(Auswahlfeld a);
  public void deleteGewichtungen(Auswahlfeld a);
  
  // Business Logik
  public void init(
      NutzenkriteriumService nService,
      CommonService cService,
      CustomizingService customizingService,
      StrategischeZieleService szService);
  public void list(HttpServletRequest request, String path);
  public void edit(HttpServletRequest request, DynaActionForm form);
  public void update(HttpServletRequest request, DynaActionForm form);
  public Auswahlfeld getAuswahlfeld(HttpServletRequest request, DynaActionForm form);
  public void deleteQuestion(
      HttpServletRequest request,
      Auswahlfeld af,
      ActionMessages messages);
  public void linkedTo(HttpServletRequest request, Auswahlfeld af);
  public void delete(HttpServletRequest request, Auswahlfeld af);
  public void deletePaAssignmentsById(Integer paId);
  public void deleteNaOpNuAssignmentsById(Integer naOpNuId);
  public void populate(HttpServletRequest request, String path, DynaActionForm form);
  public void setAuswahlfelder(HttpServletRequest request, DynaActionForm form, String path);
  public Auswahlfeld createAuswahlfeld(HttpServletRequest request, DynaActionForm form);
  public boolean next(HttpServletRequest request);
  public Auswahlfeld setType(Auswahlfeld a, int type);
  public boolean isOperativerNutzen(HttpServletRequest request);
  public boolean add(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors,
      ActionMessages messages);
  public void copy(
      Integer paId,
      Integer newPaId,
      Integer naOpNuId,
      Integer newNaOpNuId,
      Integer type);
  public boolean setRealisierbarkeitStatus(
      HttpServletRequest request,
      Integer paId);
  public boolean setStufenStatus(
      HttpServletRequest request,
      Integer naOpNuId,
      boolean isNa);
  public boolean setGewichtungStatus(
      HttpServletRequest request,
      List<Nutzenkriterium> kriterien,
      Integer naOpNuId,
      boolean isNa);
  public boolean customizingComplete(HttpServletRequest request);
}

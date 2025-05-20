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
package ch.bfh.egov.nutzenportfolio.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import ch.bfh.egov.nutzenportfolio.common.AutoGrowingList;
import ch.bfh.egov.nutzenportfolio.common.ProjektattraktivitaetLine;

/**
 * Formular für den Fragebogen der Projektattraktivität.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class ProjektattraktivitaetForm extends ActionForm {
  
  private static final long serialVersionUID = -6776891780505418983L;
  private Integer projektId;
  private Integer detailzielId;
  private Integer strategischesZielId;
  private Integer projektattraktivitaetId;
  private Integer mandantId;
  private String name;
  private Boolean quantifizierbar;
  private AutoGrowingList projektattraktivitaetLines;
  private Boolean[] messbarkeit;
  private Integer[] eintrittswahrscheinlichkeitId;
  private Integer[] eintrittszeitpunktId;
  private Boolean[] nachweisbarkeit;
  private Boolean[] einfluss;
  private Log logger = LogFactory.getLog(this.getClass());
  
  public ProjektattraktivitaetForm() {}

  /**
   * Holt eine einzelne Zeile des Fragebogens anhand des angegebenen Indices aus
   * dem Formular.
   * 
   * @param index                der Index ist sozusagen die Zeilennummer des Formulars
   * @return                     eine einzelne Zeile des Fragebogens
   */
  public ProjektattraktivitaetLine getDz(int index) {
    if (projektattraktivitaetLines == null) {
      projektattraktivitaetLines = new AutoGrowingList(ProjektattraktivitaetLine.class);
    }
    logger.debug(index);
    return (ProjektattraktivitaetLine) projektattraktivitaetLines.get(index);
  }

  public Integer getDetailzielId() {
    return detailzielId;
  }

  public void setDetailzielId(Integer detailzielId) {
    this.detailzielId = detailzielId;
  }

  public AutoGrowingList getProjektattraktivitaetLines() {
    return projektattraktivitaetLines;
  }

  public void setProjektattraktivitaetLines(
      AutoGrowingList projektattraktivitaetLines) {
    this.projektattraktivitaetLines = projektattraktivitaetLines;
  }

  public Integer getMandantId() {
    return mandantId;
  }

  public void setMandantId(Integer mandantId) {
    this.mandantId = mandantId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getProjektattraktivitaetId() {
    return projektattraktivitaetId;
  }

  public void setProjektattraktivitaetId(Integer projektattraktivitaetId) {
    this.projektattraktivitaetId = projektattraktivitaetId;
  }

  public Boolean getQuantifizierbar() {
    return quantifizierbar;
  }

  public void setQuantifizierbar(Boolean quantifizierbar) {
    this.quantifizierbar = quantifizierbar;
  }

  public Integer getStrategischesZielId() {
    return strategischesZielId;
  }

  public void setStrategischesZielId(Integer strategischesZielId) {
    this.strategischesZielId = strategischesZielId;
  }

  public Integer[] getEintrittswahrscheinlichkeitId() {
    return eintrittswahrscheinlichkeitId;
  }

  public void setEintrittswahrscheinlichkeitId(
      Integer[] eintrittswahrscheinlichkeitId) {
    this.eintrittswahrscheinlichkeitId = eintrittswahrscheinlichkeitId;
  }

  public Integer[] getEintrittszeitpunktId() {
    return eintrittszeitpunktId;
  }

  public void setEintrittszeitpunktId(Integer[] eintrittszeitpunktId) {
    this.eintrittszeitpunktId = eintrittszeitpunktId;
  }

  public Boolean[] getMessbarkeit() {
    return messbarkeit;
  }

  public void setMessbarkeit(Boolean[] messbarkeit) {
    this.messbarkeit = messbarkeit;
  }

  public Boolean[] getNachweisbarkeit() {
    return nachweisbarkeit;
  }

  public void setNachweisbarkeit(Boolean[] nachweisbarkeit) {
    this.nachweisbarkeit = nachweisbarkeit;
  }

  public Boolean[] getEinfluss() {
    return einfluss;
  }

  public void setEinfluss(Boolean[] einfluss) {
    this.einfluss = einfluss;
  }

  public Integer getProjektId() {
    logger.debug(projektId);
    return projektId;
  }

  public void setProjektId(Integer projektId) {
    logger.debug(projektId);
    this.projektId = projektId;
  }

}

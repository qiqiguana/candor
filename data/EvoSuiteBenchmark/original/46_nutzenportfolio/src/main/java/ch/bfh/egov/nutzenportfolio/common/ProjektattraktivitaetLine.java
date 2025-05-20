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
package ch.bfh.egov.nutzenportfolio.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Einzelne Zeile des Fragebogens der Projektattraktivität.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class ProjektattraktivitaetLine {
  private Integer projektattraktivitaetResultatId;
  private Boolean messbarkeit;
  private Integer eintrittswahrscheinlichkeitId;
  private Integer eintrittszeitpunktId;
  private Boolean nachweisbarkeit;
  private Boolean einfluss;
  private Log logger = LogFactory.getLog(this.getClass());
  
  public ProjektattraktivitaetLine() {}
  
  public Integer getProjektattraktivitaetResultatId() {
    return projektattraktivitaetResultatId;
  }

  public void setProjektattraktivitaetResultatId(
      Integer projektattraktivitaetResultatId) {
    this.projektattraktivitaetResultatId = projektattraktivitaetResultatId;
  }

  public Integer getEintrittswahrscheinlichkeitId() {
    return eintrittswahrscheinlichkeitId;
  }

  public void setEintrittswahrscheinlichkeitId(
      Integer eintrittswahrscheinlichkeitId) {
    logger.debug(eintrittswahrscheinlichkeitId);
    this.eintrittswahrscheinlichkeitId = eintrittswahrscheinlichkeitId;
  }

  public Integer getEintrittszeitpunktId() {
    return eintrittszeitpunktId;
  }

  public void setEintrittszeitpunktId(Integer eintrittszeitpunktId) {
    logger.debug(eintrittszeitpunktId);
    this.eintrittszeitpunktId = eintrittszeitpunktId;
  }

  public Boolean getMessbarkeit() {
    return messbarkeit;
  }

  public void setMessbarkeit(Boolean messbarkeit) {
    logger.debug(messbarkeit);
    this.messbarkeit = messbarkeit;
  }

  public Boolean getNachweisbarkeit() {
    return nachweisbarkeit;
  }

  public void setNachweisbarkeit(Boolean nachweisbarkeit) {
    logger.debug(nachweisbarkeit);
    this.nachweisbarkeit = nachweisbarkeit;
  }

  public Boolean getEinfluss() {
    return einfluss;
  }

  public void setEinfluss(Boolean einfluss) {
    logger.debug(einfluss);
    this.einfluss = einfluss;
  }

}

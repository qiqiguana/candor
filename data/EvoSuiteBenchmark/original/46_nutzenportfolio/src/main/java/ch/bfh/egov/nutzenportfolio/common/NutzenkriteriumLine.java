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
 * Einzelne Zeile der Fragebögen der Nutzenattraktivität
 * und des Operativen Nutzens.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class NutzenkriteriumLine {
  private Integer nutzenkriteriumId;
  private Integer kategorieId;
  private String frage;
  private String gewichtung;
  private String kategorieGewichtung;
  private Log logger = LogFactory.getLog(this.getClass());
  
  public NutzenkriteriumLine() {}

  public String getFrage() {
    logger.debug(frage);
    return frage;
  }

  public void setFrage(String frage) {
    this.frage = frage;
  }

  public String getGewichtung() {
    return gewichtung;
  }

  public void setGewichtung(String gewichtung) {
    this.gewichtung = gewichtung;
  }

  public String getKategorieGewichtung() {
    return kategorieGewichtung;
  }

  public void setKategorieGewichtung(String kategorieGewichtung) {
    this.kategorieGewichtung = kategorieGewichtung;
  }

  public Integer getNutzenkriteriumId() {
    return nutzenkriteriumId;
  }

  public void setNutzenkriteriumId(Integer nutzenkriteriumId) {
    this.nutzenkriteriumId = nutzenkriteriumId;
  }

  public Integer getKategorieId() {
    return kategorieId;
  }

  public void setKategorieId(Integer kategorieId) {
    this.kategorieId = kategorieId;
  }
  
  

}

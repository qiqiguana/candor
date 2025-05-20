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
package ch.bfh.egov.nutzenportfolio.tos;

import java.io.Serializable;

/**
 * Transfer Object für Auswahlfelder.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Auswahlfeld implements Serializable {
  static final long serialVersionUID = -347640745375327734L;
	private Integer auswahlfeldId;
  private Integer projektattraktivitaetId;
  private Integer nutzenattraktivitaetOperativerNutzenId;
  private Integer nutzenkriteriumId;
  private Integer mandantId;
  private Integer projektId;
  private Integer level;
	private String name;
	private Boolean eintrittswahrscheinlichkeit;
  private Boolean eintrittszeitpunkt;
  private Boolean abstufung;
  private Boolean gewichtung;
  private Boolean nutzenattraktivitaet;
  private Boolean operativerNutzen;
	
	public Auswahlfeld() {}

  public Boolean getAbstufung() {
    return abstufung;
  }

  public void setAbstufung(Boolean abstufung) {
    this.abstufung = abstufung;
  }

  public Integer getAuswahlfeldId() {
    return auswahlfeldId;
  }

  public void setAuswahlfeldId(Integer auswahlfeldId) {
    this.auswahlfeldId = auswahlfeldId;
  }

  public Boolean getEintrittswahrscheinlichkeit() {
    return eintrittswahrscheinlichkeit;
  }

  public void setEintrittswahrscheinlichkeit(Boolean eintrittswahrscheinlichkeit) {
    this.eintrittswahrscheinlichkeit = eintrittswahrscheinlichkeit;
  }

  public Boolean getEintrittszeitpunkt() {
    return eintrittszeitpunkt;
  }

  public void setEintrittszeitpunkt(Boolean eintrittszeitpunkt) {
    this.eintrittszeitpunkt = eintrittszeitpunkt;
  }

  public Boolean getGewichtung() {
    return gewichtung;
  }

  public void setGewichtung(Boolean gewichtung) {
    this.gewichtung = gewichtung;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
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

  public Integer getNutzenkriteriumId() {
    return nutzenkriteriumId;
  }

  public void setNutzenkriteriumId(Integer nutzenkriteriumId) {
    this.nutzenkriteriumId = nutzenkriteriumId;
  }

  public Integer getProjektattraktivitaetId() {
    return projektattraktivitaetId;
  }

  public void setProjektattraktivitaetId(Integer projektattraktivitaetId) {
    this.projektattraktivitaetId = projektattraktivitaetId;
  }

  public Integer getProjektId() {
    return projektId;
  }

  public void setProjektId(Integer projektId) {
    this.projektId = projektId;
  }

  public Integer getNutzenattraktivitaetOperativerNutzenId() {
    return nutzenattraktivitaetOperativerNutzenId;
  }

  public void setNutzenattraktivitaetOperativerNutzenId(
      Integer nutzenattraktivitaetOperativerNutzenId) {
    this.nutzenattraktivitaetOperativerNutzenId = nutzenattraktivitaetOperativerNutzenId;
  }

  public Boolean getNutzenattraktivitaet() {
    return nutzenattraktivitaet;
  }

  public void setNutzenattraktivitaet(Boolean nutzenattraktivitaet) {
    this.nutzenattraktivitaet = nutzenattraktivitaet;
  }

  public Boolean getOperativerNutzen() {
    return operativerNutzen;
  }

  public void setOperativerNutzen(Boolean operativerNutzen) {
    this.operativerNutzen = operativerNutzen;
  }
}

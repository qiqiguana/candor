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
 * Transfer Object für Projekte.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Projekt implements Serializable {
  static final long serialVersionUID = 7701906346613910729L;
  private Integer projektId;
  private Integer mandantId;
  private Integer projektgruppeId;
  private Integer nutzenattraktivitaetOperativerNutzenId;
  private Integer customizingId;
  private String name;
  private String beschreibung;
  private Long projektUID;
  private Long paUID;
  private Long naOpNuUID;
  private Long naUID;
  private Long opNuUID;
  private Boolean operativerNutzen;
  private Boolean paStatus;
  private Boolean naStatus;
  private Boolean opNuStatus;
  private Integer anzahlPaResultate;
  private Integer anzahlNaResultate;
  private Integer anzahlOpNuResultate;

	public Projekt() {}

  public String getBeschreibung() {
    return beschreibung;
  }

  public void setBeschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
  }

  public Integer getProjektgruppeId() {
    return projektgruppeId;
  }

  public void setProjektgruppeId(Integer projektgruppeId) {
    this.projektgruppeId = projektgruppeId;
  }

  public Integer getNutzenattraktivitaetOperativerNutzenId() {
    return nutzenattraktivitaetOperativerNutzenId;
  }

  public void setNutzenattraktivitaetOperativerNutzenId(
      Integer nutzenattraktivitaetOperativerNutzenId) {
    this.nutzenattraktivitaetOperativerNutzenId = nutzenattraktivitaetOperativerNutzenId;
  }

  public Integer getCustomizingId() {
    return customizingId;
  }

  public void setCustomizingId(Integer customizingId) {
    this.customizingId = customizingId;
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

  public Integer getProjektId() {
    return projektId;
  }

  public void setProjektId(Integer projektId) {
    this.projektId = projektId;
  }

  public Long getProjektUID() {
    return projektUID;
  }

  public void setProjektUID(Long projektUID) {
    this.projektUID = projektUID;
  }

  public Long getPaUID() {
    return paUID;
  }

  public void setPaUID(Long paUID) {
    this.paUID = paUID;
  }

  public Long getNaOpNuUID() {
    return naOpNuUID;
  }

  public void setNaOpNuUID(Long naOpNuUID) {
    this.naOpNuUID = naOpNuUID;
  }

  public Long getNaUID() {
    return naUID;
  }

  public void setNaUID(Long naUID) {
    this.naUID = naUID;
  }

  public Long getOpNuUID() {
    return opNuUID;
  }

  public void setOpNuUID(Long opNuUID) {
    this.opNuUID = opNuUID;
  }

  public Boolean getOperativerNutzen() {
    return operativerNutzen;
  }

  public void setOperativerNutzen(Boolean operativerNutzen) {
    this.operativerNutzen = operativerNutzen;
  }

  public Boolean getNaStatus() {
    return naStatus;
  }

  public void setNaStatus(Boolean naStatus) {
    this.naStatus = naStatus;
  }

  public Boolean getOpNuStatus() {
    return opNuStatus;
  }

  public void setOpNuStatus(Boolean opNuStatus) {
    this.opNuStatus = opNuStatus;
  }

  public Boolean getPaStatus() {
    return paStatus;
  }

  public void setPaStatus(Boolean paStatus) {
    this.paStatus = paStatus;
  }

  public Integer getAnzahlNaResultate() {
    return anzahlNaResultate;
  }

  public void setAnzahlNaResultate(Integer anzahlNaResultate) {
    this.anzahlNaResultate = anzahlNaResultate;
  }

  public Integer getAnzahlOpNuResultate() {
    return anzahlOpNuResultate;
  }

  public void setAnzahlOpNuResultate(Integer anzahlOpNuResultate) {
    this.anzahlOpNuResultate = anzahlOpNuResultate;
  }

  public Integer getAnzahlPaResultate() {
    return anzahlPaResultate;
  }

  public void setAnzahlPaResultate(Integer anzahlPaResultate) {
    this.anzahlPaResultate = anzahlPaResultate;
  }

}


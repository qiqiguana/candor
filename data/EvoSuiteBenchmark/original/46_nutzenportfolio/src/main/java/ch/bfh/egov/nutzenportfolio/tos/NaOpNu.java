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
 * Transfer Object für Nutzenattraktivitäten und
 * Operative Nutzen.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class NaOpNu implements Serializable {
  static final long serialVersionUID = 4071601248448044927L;
  private Integer nutzenattraktivitaetOperativerNutzenId;
  private Integer nutzenattraktivitaetOperativerNutzenResultatId;
  private Integer customizingId;
  private Integer projektId;
  private Integer mandantId;
  private Boolean nutzenattraktivitaet;
  private Boolean operativerNutzen;
  private String email;
  private Double resultat;
  private String name;
  private Long naOpNuUID;
  private Integer anzahl;

  public Long getNaOpNuUID() {
    return naOpNuUID;
  }

  public void setNaOpNuUID(Long naOpNuUID) {
    this.naOpNuUID = naOpNuUID;
  }

  public NaOpNu() {}

  public Integer getCustomizingId() {
    return customizingId;
  }

  public void setCustomizingId(Integer customizingId) {
    this.customizingId = customizingId;
  }

  public Integer getProjektId() {
    return projektId;
  }

  public void setProjektId(Integer projektId) {
    this.projektId = projektId;
  }

  public Integer getMandantId() {
    return mandantId;
  }

  public void setMandantId(Integer mandantId) {
    this.mandantId = mandantId;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getNutzenattraktivitaetOperativerNutzenResultatId() {
    return nutzenattraktivitaetOperativerNutzenResultatId;
  }

  public void setNutzenattraktivitaetOperativerNutzenResultatId(
      Integer nutzenattraktivitaetOperativerNutzenResultatId) {
    this.nutzenattraktivitaetOperativerNutzenResultatId = nutzenattraktivitaetOperativerNutzenResultatId;
  }

  public Double getResultat() {
    return resultat;
  }

  public void setResultat(Double resultat) {
    this.resultat = resultat;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAnzahl() {
    return anzahl;
  }

  public void setAnzahl(Integer anzahl) {
    this.anzahl = anzahl;
  }

}


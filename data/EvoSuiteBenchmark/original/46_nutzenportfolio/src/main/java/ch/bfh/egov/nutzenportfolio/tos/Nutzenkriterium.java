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
import java.util.List;

/**
 * Transfer Object für Nutzenkriterien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Nutzenkriterium implements Serializable {
  static final long serialVersionUID = -1487257801215264516L;
	private Integer nutzenkriteriumId;
	private Integer kategorieId;
  private Integer nutzenattraktivitaetOperativerNutzenId;
  private Integer mandantId;
	private String name;
	private String beschreibung;
  private String frage;
  private String frageManagement;
  private String frageProjektbetroffene;
  private String frageManagementDefault;
  private String frageProjektbetroffeneDefault;
  private Double gewichtung;
  private Double kategorieGewichtung;
  private String kategorieName;
  private List abstufungen;
  private List gewichtungen;
	
	public Nutzenkriterium() {}

  public String getBeschreibung() {
    return beschreibung;
  }

  public void setBeschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
  }

  public Double getGewichtung() {
    return gewichtung;
  }

  public void setGewichtung(Double gewichtung) {
    this.gewichtung = gewichtung;
  }

  public Double getKategorieGewichtung() {
    return kategorieGewichtung;
  }

  public void setKategorieGewichtung(Double kategorieGewichtung) {
    this.kategorieGewichtung = kategorieGewichtung;
  }

  public Integer getKategorieId() {
    return kategorieId;
  }

  public void setKategorieId(Integer kategorieId) {
    this.kategorieId = kategorieId;
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

  public Integer getNutzenattraktivitaetOperativerNutzenId() {
    return nutzenattraktivitaetOperativerNutzenId;
  }

  public void setNutzenattraktivitaetOperativerNutzenId(
      Integer nutzenattraktivitaetOperativerNutzenId) {
    this.nutzenattraktivitaetOperativerNutzenId = nutzenattraktivitaetOperativerNutzenId;
  }

  public Integer getNutzenkriteriumId() {
    return nutzenkriteriumId;
  }

  public void setNutzenkriteriumId(Integer nutzenkriteriumId) {
    this.nutzenkriteriumId = nutzenkriteriumId;
  }

  public String getFrageManagement() {
    return frageManagement;
  }

  public void setFrageManagement(String frageManagement) {
    this.frageManagement = frageManagement;
  }

  public String getFrageProjektbetroffene() {
    return frageProjektbetroffene;
  }

  public void setFrageProjektbetroffene(String frageProjektbetroffene) {
    this.frageProjektbetroffene = frageProjektbetroffene;
  }

  public String getFrageManagementDefault() {
    return frageManagementDefault;
  }

  public void setFrageManagementDefault(String frageManagementDefault) {
    this.frageManagementDefault = frageManagementDefault;
  }

  public String getFrageProjektbetroffeneDefault() {
    return frageProjektbetroffeneDefault;
  }

  public void setFrageProjektbetroffeneDefault(
      String frageProjektbetroffeneDefault) {
    this.frageProjektbetroffeneDefault = frageProjektbetroffeneDefault;
  }

  public String getKategorieName() {
    return kategorieName;
  }

  public void setKategorieName(String kategorieName) {
    this.kategorieName = kategorieName;
  }

  public String getFrage() {
    return frage;
  }

  public void setFrage(String frage) {
    this.frage = frage;
  }

  public List getAbstufungen() {
    return abstufungen;
  }

  public void setAbstufungen(List abstufungen) {
    this.abstufungen = abstufungen;
  }

  public List getGewichtungen() {
    return gewichtungen;
  }

  public void setGewichtungen(List gewichtungen) {
    this.gewichtungen = gewichtungen;
  }

}

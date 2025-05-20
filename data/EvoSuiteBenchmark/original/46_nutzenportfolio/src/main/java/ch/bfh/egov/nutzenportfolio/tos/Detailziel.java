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

import ch.bfh.egov.nutzenportfolio.common.AutoGrowingList;

/**
 * Transfer Object für Detailziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Detailziel implements Serializable {
  static final long serialVersionUID = 2872567722376913384L;
	private Integer detailzielId;
	private Integer strategischesZielId;
  private Integer projektattraktivitaetId;
  private Integer mandantId;
	private String name;
  private String strategischesZielName;
	private Boolean quantifizierbar;
  private AutoGrowingList messbarkeitBools;
  private AutoGrowingList eintrittswahrscheinlichkeitIds;
  private AutoGrowingList eintrittszeitpunktIds;
  private AutoGrowingList nachweisbarkeitBools;
	
	public Detailziel() {}

  public Integer getDetailzielId() {
    return detailzielId;
  }

  public void setDetailzielId(Integer detailzielId) {
    this.detailzielId = detailzielId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStrategischesZielName() {
    return strategischesZielName;
  }

  public void setStrategischesZielName(String strategischesZielName) {
    this.strategischesZielName = strategischesZielName;
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

  public Integer getProjektattraktivitaetId() {
    return projektattraktivitaetId;
  }

  public void setProjektattraktivitaetId(Integer projektattraktivitaetId) {
    this.projektattraktivitaetId = projektattraktivitaetId;
  }

  public Integer getMandantId() {
    return mandantId;
  }

  public void setMandantId(Integer mandantId) {
    this.mandantId = mandantId;
  }

  public AutoGrowingList getEintrittswahrscheinlichkeitIds() {
    return eintrittswahrscheinlichkeitIds;
  }

  public void setEintrittswahrscheinlichkeitIds(
      AutoGrowingList eintrittswahrscheinlichkeitIds) {
    this.eintrittswahrscheinlichkeitIds = eintrittswahrscheinlichkeitIds;
  }

  public AutoGrowingList getEintrittszeitpunktIds() {
    return eintrittszeitpunktIds;
  }

  public void setEintrittszeitpunktIds(AutoGrowingList eintrittszeitpunktIds) {
    this.eintrittszeitpunktIds = eintrittszeitpunktIds;
  }

  public AutoGrowingList getMessbarkeitBools() {
    return messbarkeitBools;
  }

  public void setMessbarkeitBools(AutoGrowingList messbarkeitBools) {
    this.messbarkeitBools = messbarkeitBools;
  }

  public AutoGrowingList getNachweisbarkeitBools() {
    return nachweisbarkeitBools;
  }

  public void setNachweisbarkeitBools(AutoGrowingList nachweisbarkeitBools) {
    this.nachweisbarkeitBools = nachweisbarkeitBools;
  }

}

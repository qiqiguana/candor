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
package ch.bfh.egov.nutzenportfolio;

public abstract class Constants {
  
  // Objekte
  public static final String BENUTZER                       = "benutzer";
  public static final String CUSTOMIZINGS                   = "customizings";
  public static final String CUSTOMIZING                    = "customizing";
  public static final String STRATEGISCHE_ZIELE             = "strategischeZiele";
  public static final String DETAILZIELE                    = "detailziele";
  public static final String DETAILZIELE_Q                  = "detailzieleQ";
  public static final String DETAILZIELE_NQ                 = "detailzieleNQ";
  public static final String DETAILZIELE_ASSIGNED_Q         = "detailzieleAssignedQ";
  public static final String DETAILZIELE_ASSIGNED_NQ        = "detailzieleAssignedNQ";
  public static final String REALISIERBARKEIT               = "realisierbarkeit";
  public static final String PROJEKTGRUPPEN                 = "projektgruppen";
  public static final String PROJEKTE                       = "projekte";
  public static final String EINTRITTSWAHRSCHEINLICHKEITEN  = "eintrittswahrscheinlichkeiten";
  public static final String EINTRITTSZEITPUNKTE            = "eintrittszeitpunkte";
  public static final String KATEGORIEN                     = "kategorien";
  public static final String KATEGORIEN_NA                  = "kategorienNa";
  public static final String KATEGORIEN_OP_NU               = "kategorienOpNu";
  public static final String NUTZENKRITERIEN                = "nutzenkriterien";
  public static final String NUTZENKRITERIEN_NA             = "nutzenkriterienNa";
  public static final String NUTZENKRITERIEN_OP_NU          = "nutzenkriterienOpNu";
  public static final String NUTZENKRITERIEN_ASSIGNED       = "nutzenkriterienAssigned";
  public static final String FRAGEN                         = "fragen";
  public static final String FRAGEN_NA                      = "fragenNa";
  public static final String FRAGEN_OP_NU                   = "fragenOpNu";
  public static final String ABSTUFUNGEN                    = "abstufungen";
  public static final String ABSTUFUNGEN_NA                 = "abstufungenNa";
  public static final String ABSTUFUNGEN_OP_NU              = "abstufungenOpNu";
  public static final String STUFEN                         = "stufen";
  public static final String GEWICHTUNGEN                   = "gewichtungen";
  public static final String GEWICHTUNGEN_NA                = "gewichtungenNa";
  public static final String GEWICHTUNGEN_OP_NU             = "gewichtungenOpNu";
  public static final String GEWICHTUNGSTYP                 = "gewichtungstyp";
  public static final String GEWICHTUNG_NOT_DIRECT          = "gewichtungNotDirect";
  public static final String PROJEKTBETROFFENE              = "projektbetroffene";
  public static final String GEWICHTUNGSSTUFEN              = "gewichtungsstufen";
  public static final String RESULTATE                      = "resultate";
  public static final String PROJEKTATTRAKTIVITAET          = "projektattraktivitaet";
  public static final String NUTZENATTRAKTIVITAET           = "nutzenattraktivitaet";
  public static final String OPERATIVER                     = "operativer";
  public static final String AUSWAHLFELDER                  = "auswahlfelder";
  
  
  // IDs
  public static final String ID_NAME                  = "idName";
  public static final String ID_VALUE                 = "idValue";
  public static final String BENUTZER_ID              = "benutzerId";
  public static final String CUSTOMIZING_ID           = "customizingId";
  public static final String STRATEGISCHES_ZIEL_ID    = "strategischesZielId";
  public static final String DETAILZIEL_ID            = "detailzielId";
  public static final String MANDANT_ID               = "mandantId";
  public static final String PROJEKTATTRAKTIVITAET_ID = "projektattraktivitaetId";
  public static final String PROJEKTGRUPPE_ID         = "projektgruppeId";
  public static final String PROJEKT_ID               = "projektId";
  public static final String KATEGORIE_ID             = "kategorieId";
  public static final String NUTZENKRITERIUM_ID       = "nutzenkriteriumId";
  public static final String NA_OP_NU_ID              = "naOpNuId";
  public static final String AUSWAHLFELD_ID           = "auswahlfeldId";
  
  // Namen
  public static final String NAME                     = "name";
  public static final String STRATEGISCHES_ZIEL_NAME  = "strategischesZielName";
  public static final String OPERATIVER_NUTZEN        = "opNu";
  
  // Forwards und Dispatchs
  public static final String FORM                     = "form";
  public static final String SUCCESS                  = "success";
  public static final String SUCCESS_EDIT             = "successEdit";
  public static final String FAILURE                  = "failure";
  public static final String LIST                     = "list";
  public static final String LOGOUT                   = "logout";
  public static final String DELETE                   = "delete";
  public static final String NEXT                     = "next";
  public static final String END                      = "end";
  
  // Actions
  public static final String ACTION                   = "action";
  public static final String LOGIN_ACTION             = "/login/view.do";
  public static final String BENUTZER_ACTION          = "/adm/benutzer/view.do";
  public static final String CUSTOMIZING_ACTION       = "/adm/customizing/view.do";
  public static final String QUANTIFZIERBAR_ACTION    = "/adm/detailziele";
  public static final String REALISIERBARKEIT_ACTION  = "/adm/realisierbarkeit";
  public static final String ABSTUFUNGEN_ACTION       = "/adm/abstufungen";
  public static final String STUFENBESCHRIEBE_ACTION  = "/adm/stufenbeschriebe";
  public static final String GEWICHTUNG_ACTION        = "/adm/gewichtung";
  public static final String PROJEKTGRUPPE_ACTION     = "/adm/projektgruppen/view.do";
  public static final String PROJEKT_ACTION           = "/adm/projekte/view.do";
  public static final String VERWALTUNG_STRATEGISCHE_ZIELE_ACTION             = "/adm/verwaltung/strategischeZiele/view.do";
  public static final String VERWALTUNG_DETAILZIELE_ACTION                    = "/adm/verwaltung/detailziele/view.do";
  public static final String VERWALTUNG_EINTRITTSWAHRSCHEINLICHKEITEN_ACTION  = "/adm/verwaltung/eintrittswahrscheinlichkeiten";
  public static final String VERWALTUNG_EINTRITTSZEITPUNKTE_ACTION            = "/adm/verwaltung/eintrittszeitpunkte";
  public static final String VERWALTUNG_ABSTUFUNGEN_ACTION                    = "/adm/verwaltung/abstufungen";
  public static final String VERWALTUNG_GEWICHTUNG_ACTION                     = "/adm/verwaltung/gewichtungen";
  public static final String VERWALTUNG_KATEGORIEN_ACTION                     = "/adm/verwaltung/kategorien/view.do";
  public static final String VERWALTUNG_NUTZENKRITERIEN_ACTION                = "/adm/verwaltung/nutzenkriterien/view.do";
  
  // Dispatchs
  public static final String DISPATCH                 = "dispatch";
  public static final String DISPATCH_YES             = "dispatchYes";
  public static final String DISPATCH_NO              = "dispatchNo";
  
  // Auswahl Felder
  public static final int EINTRITTSWAHRSCHEINLICHKEIT = 1;
  public static final int EINTRITTSZEITPUNKT          = 2;
  public static final int ABSTUFUNG                   = 3;
  public static final int GEWICHTUNG                  = 4;
  
  // Gewichtungstypen
  public static final int GEWICHTUNG_DIREKT           = 1;
  public static final int GEWICHTUNG_MANAGEMENT       = 2;
  public static final int GEWICHTUNG_PROJEKTBETROFFENE= 3;
  
  // Sonstige
  public static final String ID                       = "id";
  public static final String QUESTION                 = "question";
  public static final String LINKED                   = "linked";
  public static final String UPDATE                   = "update";
  public static final String INSERT                   = "insert";
  public static final String QUANTIFIZIERBAR          = "quantifizierbar";
  public static final String ERROR_MSG_KEY            = "errorMsg";
  public static final String STEP                     = "step";
  public static final String LAST_STEP                = "lastStep";
  public static final String DIREKT                   = "direkt";
  public static final String SELECTBOX                = "select";
  public static final String INCOMPLETE               = "incomplete";
  public static final String GEWICHTUNGSABSTUFUNG     = "gewichtungsabstufung";
  public static final String GEWICHTUNG_S             = "gewichtung";
  public static final String STUFENBESCHRIEBE         = "stufenbeschriebe";
  public static final String PROJEKTE_FORWARD         = "projekteForward";
  public static final String ABORT_URL                = "abortURL";

  /**
   * Eintrittswahrscheinlichkeit | < 30% | 30 - 70% | 70 - 100%
   * Eintrittszeitpunkt
   * -----------------------------------------------------------
   * 1 Jahr                      |     1     |     2    |   3
   * 1 - 5 Jahre                 |     1     |     2    |   2
   * < 5 Jahre                   |     1     |     1    |   1
   *  
   */
  public static final Integer[][] REALISIERBARKEIT_MATRIX =
      {
        {1, 1, 1},
        {2, 2, 1},
        {3, 2, 1}
      };
  
  /**
   * Formel für die Nachweisbarkeit bzw. Messbarkeit sowie
   * für die Beurteilbarkeit der Bedürfnissituation der
   * Zielgruppe
   * 
   *  0% - 10%    0.3
   * 11% - 20%    0.6
   * 21% - 30%    1.0
   * 31% - 40%    1.3
   * 41% - 50%    1.6
   * 51% - 60%    2.0
   * 61% - 70%    2.3
   * 71% - 80%    2.6
   * 81% - 90%    3.0
   * 91% - 100%   3.0
   */
  public static final Double[] FORMULA =
      {0.3, 0.6, 1.0, 1.3, 1.6, 2.0, 2.3, 2.6, 3.0, 3.0};
  
  /**
   * Anzahl Antwortmöglichkeiten    |  2  |  3  |  4   |  5
   * Level der Antwortmöglichkeit
   * -----------------------------------------------------------
   * 1                              | 1.0 | 1.0 | 0.75 | 0.6
   * 2                              | -   | -   | -    | 1.0
   * 3                              | -   | 2.0 | 1.5  | 1.5
   * 4                              | -   | -   | 2.0  | 2.0
   * 5                              | 3.0 | 3.0 | 3.0  | 3.0
   */
  public static final Double[][] NA_OP_NU_MATRIX =
      {
        {1.0, null, null, null, 3.0},
        {1.0, null, 2.0, null, 3.0},
        {0.75, null, 1.5, 2.0, 3.0},
        {0.6, 1.0, 1.5, 2.0, 3.0}
      };
}


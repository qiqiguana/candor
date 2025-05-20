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
package ch.bfh.egov.nutzenportfolio.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.bfh.egov.nutzenportfolio.Constants;

/**
 * Tag zum erstellen der Customizing-Navigation.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class CustomizingNavigation extends TagSupport {
  static final long serialVersionUID = -3269345342402491198L;
  private String name = null;
  private HttpServletRequest request = null;
  private static final String[] entries = {
      Constants.STRATEGISCHE_ZIELE,
      Constants.DETAILZIELE,
      Constants.REALISIERBARKEIT,
      Constants.KATEGORIEN_NA,
      Constants.KATEGORIEN_OP_NU,
      Constants.NUTZENKRITERIEN_NA,
      Constants.FRAGEN_NA,
      Constants.NUTZENKRITERIEN_OP_NU,
      Constants.FRAGEN_OP_NU,
      Constants.ABSTUFUNGEN_NA,
      Constants.ABSTUFUNGEN_OP_NU,
      Constants.GEWICHTUNGEN_NA,
      Constants.GEWICHTUNGEN_OP_NU
  };
  private static final String[] paEntries = {
    Constants.STRATEGISCHE_ZIELE,
    Constants.DETAILZIELE,
    Constants.REALISIERBARKEIT,
  };
  private static final String[] naEntries = {
    Constants.KATEGORIEN_NA,
    Constants.NUTZENKRITERIEN_NA,
    Constants.FRAGEN_NA,
    Constants.ABSTUFUNGEN_NA,
    Constants.GEWICHTUNGEN_NA
  };
  private static final String[] opNuEntries = {
    Constants.KATEGORIEN_OP_NU,
    Constants.NUTZENKRITERIEN_OP_NU,
    Constants.FRAGEN_OP_NU,
    Constants.ABSTUFUNGEN_OP_NU,
    Constants.GEWICHTUNGEN_OP_NU
  };
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Setzt den Parameter name, der dem Namen des
   * Navigationspunkts entspricht.
   * 
   * @param value                 der Name des Navigationspunkts
   */
  public void setName(String value) {
    name = value;
  }

  /**
   * Holt den Namen des Navigationspunkts.
   * 
   * @return                      der Name des Navigationspunkts
   */
  public String getName() {
    return(name);
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  /**
   * Setzt den Status eines Navigationspunkts. Rot steht f�r
   * "Schritt zu erledigen", gr�n f�r "Schritt erledigt".
   */
  public int doStartTag() {
    
    // Customizing feststellen
    Integer id = (Integer) pageContext.getSession().getAttribute(Constants.CUSTOMIZING_ID);
    if (name != Constants.CUSTOMIZING && id == null) {
      logger.debug("Customizing nicht gesetzt");
      return SKIP_BODY;
    }
    
    try {
      JspWriter out = pageContext.getOut();

      // Status setzen
      String state = "red";
      boolean ok = checkState();
      if (ok) {
        state = "green";
      }

      String pageName = getCurrentNavigationName();      
      String navImage = "&nbsp;";
      if (pageName.equals(name)) {
          String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
          navImage = "<img class=\"arrow\" src=\"" + contextPath + "/resources/images/nav_arrow.gif\" />";
          logger.debug("location is: " + name);
      }

      // Status mittels CSS Klasse ausgeben.
      out.println("<div class=\"" + state + "\">" + navImage + "</div>");
      
    } catch (Exception ex) {
      throw new Error(ex.getMessage());
    }

    return EVAL_BODY_INCLUDE;
  }
  
  /**
   * Ohne Funktion, gibt SKIP_BODY zur�ck.
   * 
   * @return                  SKIP_BODY
   */
  public int doEndTag() {
    return SKIP_BODY;
  }
  
  /**
   * �berpr�ft den Status eines Navigationspunkts und gibt ihn zur�ck.
   * 
   * @return                     true bei Status ok, sonst false
   */
  public boolean checkState() {
    
    // Name des Navigationspunkts wird ben�tigt
    if (name == null) {
      logger.debug("Parameter name ist null");
      return false;
    }
    
    // �berpr�ft den Status des gesamten Customizings
    if (name.equals(Constants.CUSTOMIZING)) {
      if (checkPartialState(paEntries) &&
          checkPartialState(naEntries) &&
          checkPartialState(opNuEntries)) {
        logger.debug("Customizing ok");
        return true;
      }
      return false;
    }
    
    // �berpr�ft den Status des Customizings Projektattraktivit�t
    else if (name.equals(Constants.PROJEKTATTRAKTIVITAET)) {
      if (checkPartialState(paEntries)) {
        logger.debug("Projektattraktivitaet ok");
        return true;
      }
      return false;
    }
    
    // �berpr�ft den Status des Customizings Nutzenattraktivit�t
    else if (name.equals(Constants.NUTZENATTRAKTIVITAET)) {
      if (checkPartialState(naEntries)) {
        logger.debug("Nutzenattraktivitaet ok");
        return true;
      }
      return false;
    }
    
    // �berpr�ft den Status des Customizings Operativer Nutzen
    else if (name.equals(Constants.OPERATIVER_NUTZEN)) {
      if (checkPartialState(opNuEntries)) {
        logger.debug("Operativer Nutzen ok");
        return true;
      }
      return false;
    }
    
    // �berpr�ft den Status eines spezifizierten Navigationspunkts
    for (int i = 0; i < entries.length; i++) {
      
      // Status-Flag �berpr�fen
      String entry = entries[i];
      if (name.equals(entry) && checkFlag(entry)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * �berpr�ft die Stati einer Reihe von Navigationspunkten
   * und gibt das Endresultat zur�ck.
   * 
   * @param loop                die zu �berpr�fenden Navigationspunkte
   * @return                    true bei Status ok, sonst false
   */
  private boolean checkPartialState(String[] loop) {
    for (int i = 0; i < loop.length; i++) {

      // Status-Flag �berpr�fen
      String entry = loop[i];
      if (!checkFlag(entry)) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Holt den Status-Flag eines Navigationspunkts aus der
   * Session und gibt es zur�ck.
   * 
   * @param entry                   der zu �berpr�fende Navigationspunkt
   * @return                        true bei Status ok, sonst false
   */
  private boolean checkFlag(String entry) {
    
    // Status-Flag aus der Session holen
    Object attribute = null;
    if (request != null) {
      attribute = request.getSession().getAttribute(entry);
    }
    else {
      attribute = pageContext.getSession().getAttribute(entry);
    }
    
    // Status-Flag �berpr�fen und zur�ckgeben
    if (attribute == null ||
        !(attribute instanceof Boolean) ||
        !((Boolean) attribute)) {
      logger.debug(entry + "=" + attribute);
      return false;
    }
    return true;
  }
  
  /**
   * Gibt an hand der URL die aktuelle Seite zur�ck
   * 
   * @return                        aktuelle Seite
   */
	private String getCurrentNavigationName() {
  	String page = "";
  	HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
  	String uri = (String) req.getAttribute("javax.servlet.forward.request_uri");
  	if(uri != null) {
  		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(".*/adm/(\\w+)[/\\.].*");
  		java.util.regex.Matcher matcher = pattern.matcher(uri);
  		if(matcher.matches()) {
  			page = matcher.group(1);
  			if(page.equals(Constants.OPERATIVER))
  				page = Constants.OPERATIVER_NUTZEN;
  			else if(page.equals(Constants.STUFENBESCHRIEBE))
  				page = Constants.ABSTUFUNGEN;
  			else if(page.equals(Constants.GEWICHTUNGSTYP) || page.equals(Constants.GEWICHTUNGSABSTUFUNG) || page.equals(Constants.GEWICHTUNG_S))
  				page = Constants.GEWICHTUNGEN;

  			if(page.equals(Constants.CUSTOMIZING)) {
  				String param = req.getParameter(Constants.DISPATCH);
  				if((param != null) && (param.equals("insertOrUpdate")))
  					page = Constants.PROJEKTATTRAKTIVITAET;
  			}
  			else if(page.equals(Constants.KATEGORIEN) ||
  					page.equals(Constants.NUTZENKRITERIEN) ||
  					page.equals(Constants.FRAGEN) ||
  					page.equals(Constants.ABSTUFUNGEN) ||
  					page.equals(Constants.GEWICHTUNGEN)) {
  				if(isOperativerNutzen())
  					page += "OpNu";
  				else
  					page += "Na";
  			}
  		}
  	}
  	return page;
	}


  /**
   * Gibt zur�ck ob wir uns im Customizing Operativer Nutzens befinden
   * oder nicht.
   * 
   * @return                        true bei Operativer Nutzen, sonst false
   */
  public boolean isOperativerNutzen() {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	  
    // Parameter Operativer Nutzen holen
    String param = request.getParameter(Constants.OPERATIVER_NUTZEN);
    logger.debug("Parameter=" + param);
    boolean paramNull = param == null ? true : false;
    
    // Attribut Operativer Nutzen holen
    String attr = (String) request.getAttribute(Constants.OPERATIVER_NUTZEN);
    logger.debug("Attribut=" + attr);
    boolean attrNull = attr == null ? true : false;
    HttpSession session = request.getSession();
    
    // Parameter und Attribut �berpr�fen
    if (!paramNull || !attrNull) {
      
      // Operativer Nutzen durch Request festgestellt
      if (!paramNull && param.equals("true") ||
          !attrNull && attr.equals("true")) {
        logger.debug("Operativer Nutzen (Request)");
        session.setAttribute(Constants.OPERATIVER_NUTZEN, "true");
        return true;
      }
      
      // Operativer Nutzen durch Session festgestellt
      else if (!paramNull && param.equals("false") || 
          !attrNull && attr.equals("false")) {
        logger.debug("Nutzenattraktivit�t (Session)");
        session.setAttribute(Constants.OPERATIVER_NUTZEN, "false");
        return false;
      }
    }
    
    // Session �berpr�fen
    else {
      String opNu = (String) session.getAttribute(Constants.OPERATIVER_NUTZEN);
      if (opNu.equals("true")) {
        logger.debug("Operativer Nutzen (Session)");
        return true;
      }
      else {
        logger.debug("Nutzenattraktivit�t (Session)");
        return false;
      }
    }
    return false;
  }
  
}

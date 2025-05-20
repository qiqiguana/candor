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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.tos.Resultat;

// Gordon Fraser: Deprecated, replaced with imageio
// import com.sun.image.codec.jpeg.JPEGCodec;
// import com.sun.image.codec.jpeg.JPEGImageEncoder;
import javax.imageio.ImageIO;

/**
 * Grafische Auswertung der Resultate.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class AuswertungGrafik extends HttpServlet {
  private static final long serialVersionUID = -8569374683142648050L;
  private Log logger = LogFactory.getLog(this.getClass());
  
  private static final Integer WIDTH                = 900;
  private static final Integer HEIGHT               = 700;
  private static final Integer PADDING_LEFT         = 80;
  private static final Integer PADDING_TOP          = 50;
  private static final Integer CHART_WIDTH          = 600;
  private static final Integer CHART_RIGHT_END      = CHART_WIDTH + PADDING_LEFT;
  private static final Integer CHART_BOTTOM_END     = CHART_WIDTH + PADDING_TOP;
  private static final Integer CHART_VERT_CENTER    = CHART_BOTTOM_END - CHART_WIDTH / 2;
  private static final Integer CHART_HOR_CENTER     = CHART_RIGHT_END - CHART_WIDTH / 2;
  private static final Integer CHART_VERT_QUARTER1  = PADDING_TOP + CHART_WIDTH / 4;
  private static final Integer CHART_VERT_QUARTER2  = PADDING_TOP + CHART_WIDTH * 3 / 4;
  private static final Integer CHART_HOR_QUARTER1   = PADDING_LEFT + CHART_WIDTH / 4;
  private static final Integer CHART_HOR_QUARTER2   = PADDING_LEFT + CHART_WIDTH * 3 / 4;
  private static final Integer PROJECT_LEGEND       = 760;
  private static final Integer LEGEND_RIGHT         = 695;
  private static final Integer LEGEND_LEFT          = -45;
  private static final Integer NUMBERS_LEFT         = 30;
  private static final Integer LEGEND_BOTTOM        = 675;
  private static final Double RESULT_FAKTOR         = new Double(CHART_WIDTH) / 3;
  
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    
    ServletOutputStream out = res.getOutputStream();
    
    // Bild erstellen
    BufferedImage bImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = bImage.createGraphics();
    
    // Rahmen
    g2d.setColor(Color.WHITE);
    g2d.fillRect(1, 1, WIDTH-2, HEIGHT-2);
    
    // Y und X Achsen
    g2d.setColor(Color.BLACK);
    // Y-Achse (von oben nach unten)
    g2d.drawLine(PADDING_LEFT, PADDING_TOP - 10, PADDING_LEFT, CHART_BOTTOM_END);
    // X-Achse (von links nach rechts)
    g2d.drawLine(PADDING_LEFT, CHART_BOTTOM_END, CHART_RIGHT_END + 10, CHART_BOTTOM_END);
    
    // Pfeile
    g2d.drawLine(PADDING_LEFT, PADDING_TOP - 20, PADDING_LEFT + 4, PADDING_TOP - 10);
    g2d.drawLine(PADDING_LEFT, PADDING_TOP - 20, PADDING_LEFT - 4, PADDING_TOP - 10);
    g2d.drawLine(CHART_RIGHT_END + 10, CHART_BOTTOM_END - 4, CHART_RIGHT_END + 20, CHART_BOTTOM_END);
    g2d.drawLine(CHART_RIGHT_END + 10, CHART_BOTTOM_END + 4, CHART_RIGHT_END + 20, CHART_BOTTOM_END);
    
    // gepunktete Linien
    for (int i = PADDING_TOP; i < CHART_BOTTOM_END; i += 10) {
      g2d.drawLine(CHART_RIGHT_END, i, CHART_RIGHT_END, i + 6);
      g2d.drawLine(CHART_HOR_CENTER, i, CHART_HOR_CENTER, i + 6);
    }
    for (int i = PADDING_LEFT; i < CHART_RIGHT_END; i += 10) {
      g2d.drawLine(i, PADDING_TOP, i + 6, PADDING_TOP);
      g2d.drawLine(i, CHART_VERT_CENTER, i + 6, CHART_VERT_CENTER);
    }
    
    // Y-Achse Beschriftung
    g2d.drawString("0", NUMBERS_LEFT, LEGEND_BOTTOM + 10);
    g2d.drawString("1.5", NUMBERS_LEFT, CHART_VERT_CENTER + 5);
    g2d.drawString("3.0", NUMBERS_LEFT, 55);
    g2d.drawString("leicht", LEGEND_RIGHT, CHART_VERT_QUARTER1 - 10);
    g2d.drawString("erreichbar", LEGEND_RIGHT, CHART_VERT_QUARTER1);
    g2d.drawString("schwer", LEGEND_RIGHT, CHART_VERT_QUARTER2 - 20);
    g2d.drawString("erreichbar / ", LEGEND_RIGHT, CHART_VERT_QUARTER2 - 10);
    g2d.drawString("umsetzbar / ", LEGEND_RIGHT, CHART_VERT_QUARTER2);
    g2d.drawString("nachweisbar", LEGEND_RIGHT, CHART_VERT_QUARTER2 + 10);
    
    // X-Achse Beschriftung
    g2d.drawString("1.5", CHART_HOR_CENTER - 5, LEGEND_BOTTOM + 10);
    g2d.drawString("3.0", CHART_RIGHT_END - 5, LEGEND_BOTTOM + 10);
    g2d.drawString("Nutzenattraktivität", CHART_RIGHT_END - 20, LEGEND_BOTTOM - 5);
    g2d.drawString("niedrig", CHART_HOR_QUARTER1 - 10, LEGEND_BOTTOM);
    g2d.drawString("hoch", CHART_HOR_QUARTER2 - 10, LEGEND_BOTTOM);
    g2d.drawString("\"nice to have\"", CHART_HOR_QUARTER1 - 20, 30);
    g2d.drawString("\"muss\"", CHART_HOR_QUARTER2 - 10, 30);
    
    // Projekte in Grafik einfügen
    doAuswertung(req, g2d);
    
    // Vertikale Beschriftungen
    g2d.setColor(Color.BLACK);
    AffineTransform at = new AffineTransform();
    at.setToRotation(-Math.PI/2.0, WIDTH/2.0, HEIGHT/2.0);
    g2d.setTransform(at);
    g2d.drawString("Projektattraktivität", HEIGHT-10, LEGEND_LEFT + 10);
    g2d.drawString("hoch", CHART_VERT_QUARTER2 + 100, LEGEND_LEFT); // 400
    g2d.drawString("niedrig", CHART_VERT_QUARTER1 + 100, LEGEND_LEFT); // 220
    
    g2d.dispose();
    
    // In JPG konvertieren und ausgeben
    res.setContentType("image/jpeg");
    res.setHeader("Cache-Control", "no-cache");
    ImageIO.write(bImage, "jpeg", out);
    // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    // encoder.encode(bImage);
  }
  
  private void doAuswertung(HttpServletRequest req, Graphics2D g2d) {
    HttpSession session = req.getSession();
    
    Object obj = session.getAttribute(Constants.RESULTATE);
    if (obj == null) {
      logger.warn("Keine Resultate");
      return;
    }
    List resultate = (List) obj; 
    if (resultate == null || resultate.size() == 0) {
      logger.warn("Keine Resultate");
      return;
    }
    logger.debug("Resultate in der Session: " + resultate.size());
    for (int i = 0; i < resultate.size(); i++) {
      Resultat resultat = (Resultat) resultate.get(i);
      boolean hasOpNu = resultat.getOpNuResultat() != null;
      Double paDouble = RESULT_FAKTOR * resultat.getPaResultat();
      logger.debug("Resultat Projektattraktivitaet: " + paDouble);
      Double naDouble = RESULT_FAKTOR * resultat.getNaResultat();
      logger.debug("Resultat Nutzenattraktivitaet: " + naDouble);
      Double opNuDouble = null;
      if(hasOpNu) {
        opNuDouble = RESULT_FAKTOR * resultat.getOpNuResultat();
        logger.debug("Resultat Operativer Nutzen: " + opNuDouble);
      }
      
      Integer pa = paDouble.intValue();
      logger.debug("Resultat Projektattraktivitaet gerundet: " + pa);
      Integer na = naDouble.intValue();
      logger.debug("Resultat Nutzenattraktivitaet gerundet: " + na);
      Integer opNu = null;
      if(hasOpNu) {
        opNu = opNuDouble.intValue();
        logger.debug("Resultat Operativer Nutzen gerundet: " + opNu);
      }
      
      // Zufällige Farbe wählen
      int red = (int) (Math.random() * 220);
      int green = (int) (Math.random() * 220);
      int blue = (int) (Math.random() * 220);

      //fill our array with random colors
      Color c = new Color(red, green, blue);
      
      // Projekt in Diagramm zeichnen
      g2d.setColor(c);
      
      // In Koordinaten für das Diagramm umrechnen
      pa = PADDING_TOP + CHART_WIDTH - pa;
      na = PADDING_LEFT + na;
      if(hasOpNu) {
      	logger.debug("Zeiche Resultat mit operativem Nutzen");
        opNu = (Integer) opNu / 10;
        g2d.drawOval(na - opNu, pa - opNu, opNu*2, opNu*2);
        logger.debug("g2d.drawOval(" + (na - opNu) + ", " + (pa - opNu) + ", " + opNu + ", " + opNu + ");");
      }
      else {
        logger.debug("Zeiche Resultat ohne operativem Nutzen");
        g2d.drawLine(na - 5, pa - 5, na + 5, pa + 5);
        g2d.drawLine(na + 5, pa - 5, na - 5, pa + 5);
        logger.debug("g2d.drawLine(" + (na - 5) + ", " + (pa - 5) + ", " + (na + 5) + ", " + (pa + 5) + ");");
        logger.debug("g2d.drawLine(" + (na + 5) + ", " + (pa - 5) + ", " + (na - 5) + ", " + (pa + 5) + ");");
      }

      // Projektname
      g2d.drawString(resultat.getName(), PROJECT_LEGEND, PADDING_TOP + i*10);
    }

  }
}

package common;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Point;
import javax.xml.xpath.*;
import java.util.Date;

/**
 * Loads a XML-file and write read properties, tiles and start points to
 * map-object
 * 
 * @author christian
 * 
 */
public class MapReader {
	private XmlFunctions xmlFunctions;
	private String mapFilePath;

	private static final Logger logger = Logger.getLogger(MapReader.class);

	/**
	 * constructor
	 * 
	 * @param mapFile -
	 *            Path to XML-file with map information
	 */
	public MapReader(String mapFile) {
		this.mapFilePath = mapFile;
		xmlFunctions = new XmlFunctions(mapFile);
	}

	/**
	 * Returns a specific value in a group of nodes using XPath expressions
	 * 
	 * @param node -
	 *            node(set) in which value should be searched
	 * @param XMLPath -
	 *            XPath expression
	 * @return String - found value
	 */
	public String getXMLValue(Node node, String XMLPath) {
		try {
			// todo: exception wenn feld leer
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile(XMLPath);
			Object result = expr.evaluate(node, XPathConstants.NODE);
			Node foundNode = (Node) result;
			return foundNode.getNodeValue();
		} catch (XPathExpressionException ex) {
			logger.error(ex);
		}
		return new String();
	}

	/**
	 * Sets read properties to map-object using the setter-methods of map-object
	 * 
	 * @param map -
	 *            Map object
	 */
	public void setMapProperies(Map map) {
		map.setArea(Integer.parseInt(xmlFunctions
				.getXmlValue("/map/area/x/text()")), Integer
				.parseInt(xmlFunctions.getXmlValue("/map/area/y/text()")));
		map.setProperties(xmlFunctions.getXmlValue("/map/name/text()"),
				xmlFunctions.getXmlValue("/map/version/text()"), xmlFunctions
						.getXmlValue("/map/author/text()"), new Date(), Integer
						.parseInt(xmlFunctions
								.getXmlValue("/map/difficulty/text()")),
				xmlFunctions.getXmlValue("/map/imageset/text()"), Integer
						.parseInt(xmlFunctions
								.getXmlValue("/map/maxplayers/text()")),
				this.mapFilePath);
	}

	/**
	 * sets the specific tiles to map object
	 * 
	 * @param map
	 */
	public void setTiles(Map map) {
		logger.info("Setting map tiles...");
		// retrieve List of field-nodes
		int MapX = 0, MapY = 0;
		String Type = new String();
		Boolean accessible = false, bombable = false;
		Tile tile;
		Node childNode;
		NodeList nodes = xmlFunctions.findXmlNodes("//fields/field");
		// read and create tiles
		for (int i = 0; i < nodes.getLength(); i++) {
			// run through all child nodes to collect information
			for (childNode = nodes.item(i).getFirstChild(); childNode != null; childNode = childNode
					.getNextSibling()) {
				if (childNode.getNodeName().equals("x")) {
					MapX = Integer.parseInt(childNode.getTextContent());
				} else if (childNode.getNodeName().equals("y")) {
					MapY = Integer.parseInt(childNode.getTextContent());
				} else if (childNode.getNodeName().equals("type")) {
					Type = childNode.getTextContent();
				} else if (childNode.getNodeName().equals("accessible")) {
					accessible = Boolean.valueOf(childNode.getTextContent());
				} else if (childNode.getNodeName().equals("bombable")) {
					bombable = Boolean.valueOf(childNode.getTextContent());
				} else {
					continue; // irrelevant entry
				}
			}
			tile = new Tile(new Point(MapX * Constants.TILE_BORDER + Constants.TILE_BORDER/2, MapY
					* Constants.TILE_BORDER + Constants.TILE_BORDER/2));
			tile.setProperties(Type, accessible, bombable);

			// assign tile to MapGrid, by position/index
			map.setTileByIndex(MapX, MapY, tile);

			// autoWall, if flag = 1 and position 0/0
			if (MapX == 0
					&& MapY == 0
					&& Integer.parseInt(xmlFunctions
							.getXmlValue("/map/autowall/text()")) == 1) {
				logger.info("Autowall ausführen...");
				autoWall(map, getXMLValue((Node) nodes.item(i), "type/text()"));
			}
		}
	}

	public void setPowerups(Map map) {
		logger.info("Setting powerups...");
		// retrieve List of powerup-nodes
		PowerUp powerup;
		String Type = new String();
		int MapX = 0, MapY = 0;
		Node childNode;
		NodeList nodes = xmlFunctions.findXmlNodes("//powerups/powerup");
		// read and create tiles
		for (int i = 0; i < nodes.getLength(); i++) {
			// run through all child nodes to collect information
			for (childNode = nodes.item(i).getFirstChild(); childNode != null; childNode = childNode
					.getNextSibling()) {
				if (childNode.getNodeName().equals("x")) {
					MapX = Integer.parseInt(childNode.getTextContent());
				} else if (childNode.getNodeName().equals("y")) {
					MapY = Integer.parseInt(childNode.getTextContent());
				} else if (childNode.getNodeName().equals("type")) {
					Type = childNode.getTextContent();
				} else {
					continue; // irrelevant entry
				}
			}
			powerup = new PowerUp(new Point(MapX * Constants.TILE_BORDER + Constants.TILE_BORDER/2, MapY
					* Constants.TILE_BORDER + Constants.TILE_BORDER/2));
			powerup.setType(Type);
			
			// assign powerup to MapGrid, by position/index
			map.addPowerup(powerup);
		}
	}

	/**
	 * automatically sets wall on the border of map set
	 * 
	 * @param map
	 */
	public void autoWall(Map map, String Type) {
		Tile tile;
		Point size = map.getArea();
		for (int k = 0; k < size.x; k++) {
			for (int j = 0; j < size.y; j++) {
				if (k == 0 || k == (size.x - 1) || j == 0 || j == (size.y - 1)) {
					tile = new Tile(new Point(k * Constants.TILE_BORDER + Constants.TILE_BORDER/2, j
							* Constants.TILE_BORDER + Constants.TILE_BORDER/2));
					tile.setType(Type);
					tile.setAccessible(false);
					tile.setBombable(false);
					map.setTileByIndex(k, j, tile);
				} else {
					continue;
				}
			}
		}
	}

	/**
	 * sets the start-points in map object. start points are read from xml
	 * 
	 * @param map
	 */
	public void setStartPoints(Map map) {
		int MapX, MapY;
		Point[] StartPoints;
		NodeList nodes = xmlFunctions.findXmlNodes("//startpoints/start");
		logger.info(nodes.getLength() + " Startpunkte gefunden");
		StartPoints = new Point[nodes.getLength()];
		for (int i = 0; i < nodes.getLength(); i++) {
			// read and create start points
			// Player = Integer.parseInt(getXMLValue((Node) nodes.item(i),
			// "player/text()"));
			MapX = Integer.parseInt(getXMLValue((Node) nodes.item(i),
					"x/text()"));
			MapY = Integer.parseInt(getXMLValue((Node) nodes.item(i),
					"y/text()"));
			StartPoints[i] = new Point(MapX, MapY);
		}
		map.setStartPoints(StartPoints);
	}

	/**
	 * gets area of map
	 * 
	 * @return Point with x and y dimension/number of tiles
	 */
	public Point getArea() {
		Point area = new Point();
		area.x = Integer.parseInt(xmlFunctions
				.getXmlValue("/map/area/x/text()"));
		area.y = Integer.parseInt(xmlFunctions
				.getXmlValue("/map/area/y/text()"));
		return area;
	}

}

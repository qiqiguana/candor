package common;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import messages.global.MapInfo;

/**
 * class for listing maps and returning map information
 * 
 * @author christian
 * 
 */
public class MapFilesLister {
	private static final Logger logger = Logger.getLogger(MapFilesLister.class);

	public MapFilesLister() {
	}

	/**
	 * reads directory and returns a list of found xml-files
	 * 
	 * @param dir -
	 *            directory to be searched in
	 * @return Vector<File>
	 */
	public Vector<File> ListMaps(String dir) {
		// open map folder
		logger.info("Read map directory: " + dir + "...");
		File mapDir = new File(dir);
		File[] mapDirFiles = mapDir.listFiles();
		Vector<File> foundFiles = new Vector<File>();
		// open directories in map folder
		for (int i = 0; i < mapDirFiles.length; i++) {
			if (mapDirFiles[i].isFile()
					&& mapDirFiles[i].toString().endsWith(".xml")) {
				logger.info("XML: " + mapDirFiles[i]);
				foundFiles.add(mapDirFiles[i]);
			}
		}
		return foundFiles;
	}

	/**
	 * retrieve list of MapInfo objects
	 * 
	 * @return
	 */
	public List<MapInfo> getMapInfoList() {
		// clear map previews
		clearPreviews();
		List<MapInfo> maps = new LinkedList<MapInfo>();
		// read map directory
		Vector<File> mapFiles = ListMaps(Constants.MAP_PATH);
		for (File mapFile : mapFiles) {
			// get map, just the tiles, no start points and powerups
			Map map = new Map(mapFile.getAbsolutePath(), true, false, false);
			MapPreview mapPreview = new MapPreview(map);
			MapInfo mapInfo = new MapInfo(mapFile.getName(), map.getName(), map
					.getMaxPlayers());
			// creates or reads map preview with 300px width
			mapInfo.setPreview(mapPreview.getMapPreview(300));
			maps.add(mapInfo);
		}
		return maps;
	}

	/**
	 * clears unused or obsolete map preview files
	 */
	public void clearPreviews() {
		String dir = Constants.MAP_PATH;
		logger.info("Clear map-previews in directory: " + dir + "...");
		File mapDir = new File(dir);
		File[] mapDirPreviews = mapDir.listFiles();
		// list files in map directory
		for (int i = 0; i < mapDirPreviews.length; i++) {
			if (mapDirPreviews[i].isFile()
					&& mapDirPreviews[i].toString().endsWith(".png")) {
				// check if map xml is newer than preview
				File mapFileName = new File(mapDirPreviews[i].getPath()
						.replaceAll("_[a-z]+.png$", ""));
				// does map still exist
				if (!mapFileName.exists()) {
					mapDirPreviews[i].delete();
					logger
							.info("Preview: "
									+ mapDirPreviews[i]
									+ " deleted because corresponding map doesn't exists.");
				} else {
					// is preview older than map
					long mapDate = mapFileName.lastModified();
					if (mapDate > mapDirPreviews[i].lastModified()) {
						mapDirPreviews[i].delete();
						logger
								.info("Preview: "
										+ mapDirPreviews[i]
										+ " deleted because it was older than the map itself");
					}
				}
			}
		}
	}

}

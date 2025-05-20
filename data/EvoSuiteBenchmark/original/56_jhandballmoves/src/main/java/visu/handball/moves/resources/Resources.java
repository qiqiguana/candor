package visu.handball.moves.resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Resources {
	private static final String BUNDLE_NAME = "visu.handball.moves.resources.Resources"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Resources() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static String getString(String key, String[] params){
		try {
			String value = RESOURCE_BUNDLE.getString(key); 
			Pattern pattern = Pattern.compile("\\{[0-9]?[0-9]\\}");
			Scanner scanner = new Scanner(value);
			scanner.useDelimiter(pattern);
			int index = 0;
			StringBuffer buffer = new StringBuffer();
			while (scanner.hasNext()) {
				buffer.append(scanner.next());
				buffer.append((index < params.length) ? params[index] : "");
				index++;
			}
			return buffer.toString();
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}

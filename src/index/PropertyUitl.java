package index;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUitl {
	private static Properties props;
	
	public static String getProperty(String key) {
		Properties properties = getProps();
		return properties.getProperty(key);
	}

	private static Properties getProps() {
		if (props == null) {
			try {
				props = new Properties();
				props.load(new FileInputStream("my.conf"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
	}
}

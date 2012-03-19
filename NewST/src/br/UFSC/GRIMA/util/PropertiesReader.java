package br.UFSC.GRIMA.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

	public Properties loadPropFile(String s) {
		Properties properties = null;
		Object obj = null;
		boolean loaded = false;

		try {
//			obj = ClassLoader.getSystemResourceAsStream(s);
			obj = getClass().getClassLoader().getResourceAsStream(s);

			properties = new Properties();
			// /////////////////////////////////////
			if (obj != null) {
				obj = new BufferedInputStream(((InputStream) (obj)));
				try {
					properties.load(((InputStream) (obj)));
					loaded = true;
				} catch (Exception exception) {
					loaded = false;
					Exception lastException = exception;
				}

			} else {
				loaded = false;
				System.err.println(s + " file not found");
			}
			if (loaded) {

			} else {
				System.out.println("not loaded");
			}

		} catch (Exception exception) {
			System.out.println("Exception--$$\t" + exception);
			exception.printStackTrace();
			loaded = false;
		}
		Properties jposProperties = new Properties();
		if (obj != null) {
			obj = new BufferedInputStream(((InputStream) (obj)));
			try {
				jposProperties.load(((InputStream) (obj)));

				loaded = true;
			} catch (Exception exception1) {
				System.out.println("Exception!!\t" + exception1);
				loaded = false;
			} finally {
				try {
					((InputStream) (obj)).close();
				} catch (Exception exception3) {
				}
			}
		} else {
			loaded = false;
			System.err.println(s + "-- file not found");
		}
		return properties;
	}
}

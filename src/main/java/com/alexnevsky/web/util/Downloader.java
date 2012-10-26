package com.alexnevsky.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * @author Alex Nevsky
 * 
 */
public class Downloader {

	private static final Logger logger = Logger.getLogger(Downloader.class);

	/**
	 * Default constructor.
	 */
	public Downloader() {
	}

	/**
	 * Downloads and save file.
	 * 
	 * @param url
	 *            File URL to download.
	 * @param fileNameToSave
	 *            Path to new file.
	 * @param name
	 *            File name to save.
	 * 
	 * @see http://stackoverflow.com/questions/921262/
	 *      how-to-download-and-save-a-file-from-internet-using-java
	 */
	public static void downloadFile(URL url, File fileNameToSave) {
		try {
			org.apache.commons.io.FileUtils.copyURLToFile(url, fileNameToSave);
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	/**
	 * Gets page HTML code.
	 * 
	 * @param pageUrl
	 *            Page URL.
	 * @return page HTML code.
	 * 
	 * @see http://stackoverflow.com/questions/2793150/
	 *      how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
	 */
	public static String getPageHtmlCode(URL pageUrl) {
		StringBuilder sb = new StringBuilder(1024);

		try {
			HttpURLConnection urlConnection = (HttpURLConnection) pageUrl.openConnection();

			urlConnection.setRequestProperty("Accept-Charset", "UTF-8");

			// do as if we're using Chrome
			urlConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/A.B "
							+ "(KHTML, like Gecko) Chrome/X.Y.Z.W Safari/A.B.");

			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String lineSeparator = System.getProperty("line.separator");
				for (String line; (line = reader.readLine()) != null;) {
					sb.append(line);
					sb.append(lineSeparator);
				}
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						logger.error(e, e);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e, e);
		}

		return sb.toString();
	}
}

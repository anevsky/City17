/**
 * 
 */
package com.alexnevsky.web.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author Alex Nevsky
 * 
 */
public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * Makes a directory, including any necessary but nonexistent parent directories.
	 * 
	 * @param path
	 *            directory to create
	 * @return true if the directory has been created successfully
	 */
	public static boolean createDir(final File path) {
		boolean isSuccess = false;

		if (path != null) {
			try {
				org.apache.commons.io.FileUtils.forceMkdir(path);
				isSuccess = true;
			} catch (IOException e) {
				logger.warn(e, e);
			}
		} else {
			logger.warn("Oops!.. Path is null!");
		}

		return isSuccess;
	}

	/**
	 * Returns extension of path without ".". For example, png, jpg etc.
	 * 
	 * @param path
	 *            path to parse
	 * @return path extension or null if no extension in given path
	 */
	public static String getPathExtension(final String path) {
		String result = null;

		if (path != null) {
			result = "";
			if (path.lastIndexOf('.') != -1) {
				result = path.substring(path.lastIndexOf('.'));
				if (result.startsWith(".")) {
					result = result.substring(1);
				}
			}
		}

		return result;
	}

	/**
	 * Returns file size.
	 * 
	 * @param file
	 * @return file size in bytes
	 * @see http://stackoverflow.com/questions/116574/java-get-file-size-efficiently
	 */
	public static long getFileSize(File file) {
		if (file.exists()) {
			return file.length();
		} else {
			return 0;
		}
	}

}

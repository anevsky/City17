/**
 * 
 */
package com.alexnevsky.web.util;

/**
 * @author Alex Nevsky
 * 
 */
public class StringUtil {

	public static boolean isAlphanumericSpace(String string) {
		return org.apache.commons.lang3.StringUtils.isAlphanumericSpace(string);
	}

	/**
	 * Checks if the string contains only Unicode letters, digits or hyphen ('-').
	 * 
	 * null will return false. An empty CharSequence (length()=0) will return true.
	 * 
	 * @param string
	 *            the string to check, may be null
	 * @return true if only contains letters, digits or hyphen, and is non-null
	 */
	public static boolean isAlphanumericHyphen(String string) {
		if (org.apache.commons.lang3.StringUtils.isAlphanumeric(string)) {
			return true;
		} else {
			String[] parts = string.split("-");

			for (String part : parts) {
				if (!org.apache.commons.lang3.StringUtils.isAlphanumeric(part)) {
					return false;
				}
			}

			return true;
		}
	}

	/**
	 * Checks if the string contains only Unicode letters, digits, space (' ') or hyphen ('-').
	 * 
	 * null will return false. An empty CharSequence (length()=0) will return true.
	 * 
	 * @param string
	 *            the string to check, may be null
	 * @return true if only contains letters, digits, space or hyphen, and is non-null
	 */
	public static boolean isAlphanumericSpaceHyphen(String string) {
		if (org.apache.commons.lang3.StringUtils.isAlphanumericSpace(string)) {
			return true;
		} else {
			String[] parts = string.split("-");

			for (String part : parts) {
				if (!org.apache.commons.lang3.StringUtils.isAlphanumericSpace(part)) {
					return false;
				}
			}

			return true;
		}
	}

	/**
	 * Returns the String contains only Unicode letters, digits or hyphen ('-').
	 * 
	 * @param string
	 *            the string to convert
	 * @return converted string or null
	 */
	public static String toAlphanumericHyphen(String string) {
		String result = null;

		if (isAlphanumericHyphen(string)) {
			return string.trim().replaceAll("[-]+", "-"); // multiple replacement
		} else if ((string != null) && !string.isEmpty()) {
			StringBuilder sb = new StringBuilder(string.length());

			for (char c : string.toCharArray()) {
				if (isAlphanumericHyphen(String.valueOf(c))) {
					sb.append(c);
				} else if (org.apache.commons.lang3.StringUtils.isWhitespace(String.valueOf(c))) {
					sb.append("-");
				}
			}

			result = sb.toString().trim().replaceAll("[-]+", "-"); // multiple replacement
		}

		return result;
	}

	/**
	 * Returns the String contains only Unicode letters, digits, space (' ') or hyphen ('-').
	 * 
	 * @param string
	 *            the string to convert
	 * @return converted string or null
	 */
	public static String toAlphanumericSpaceHyphen(String string) {
		String result = null;

		if (isAlphanumericSpaceHyphen(string)) {
			return string.trim().replaceAll("[-]+", "-").replaceAll("[\\s]+", " ");
		} else if ((string != null) && !string.isEmpty()) {
			StringBuilder sb = new StringBuilder(string.length());

			for (char c : string.toCharArray()) {
				if (isAlphanumericSpaceHyphen(String.valueOf(c))) {
					sb.append(c);
				}
			}

			result = sb.toString().trim().replaceAll("[-]+", "-").replaceAll("[\\s]+", " ");
		}

		return result;
	}

	public static String escapeHtml(String html) {
		return org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(html);
	}

	public static String unescapeHtml(String string) {
		return org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(string);
	}
}

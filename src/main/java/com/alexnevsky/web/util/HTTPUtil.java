/**
 * 
 */
package com.alexnevsky.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * HTTP util helper class.
 * 
 * @author Alex Nevsky
 * 
 */
public class HTTPUtil {

	private static final Logger logger = Logger.getLogger(HTTPUtil.class);

	/**
	 * Do HTTP POST Request programmatically.
	 * 
	 * @param url
	 *            Form URL.
	 * @param cookies
	 *            Store for cookies.
	 * @param paramsMap
	 *            Request query parameters.
	 * 
	 * @return page HTML code.
	 * 
	 * @see http://stackoverflow.com/questions/2793150/
	 *      how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
	 */
	public static String doPostRequest(String url, List<String> cookies, Map<String, String> paramsMap) {
		StringBuilder htmlResponseSb = null;

		// Firing a HTTP POST request with query parameters
		try {
			String charset = "UTF-8";

			StringBuilder queryParamsSb = new StringBuilder(32);

			for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				queryParamsSb.append(key);
				queryParamsSb.append("=");
				queryParamsSb.append(URLEncoder.encode(value, charset));
				queryParamsSb.append("&");
			}

			HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();

			// Maintaining the session
			if (cookies != null) {
				for (String cookie : cookies) {
					httpConnection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
				}
			}

			httpConnection.setRequestMethod("POST");

			httpConnection.setDoOutput(true); // Triggers POST.
			httpConnection.setRequestProperty("Accept-Charset", charset);
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

			// do as if we're using Chrome
			httpConnection
					.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/A.B (KHTML, like Gecko) Chrome/X.Y.Z.W Safari/A.B.");

			OutputStream output = null;
			try {
				output = httpConnection.getOutputStream();
				output.write(queryParamsSb.toString().getBytes(charset));
			} finally {
				if (output != null) {
					output.close();
				}
			}

			// Gather all cookies on the request.
			cookies = httpConnection.getHeaderFields().get("Set-Cookie");

			/*
			 * // Gathering HTTP response information
			 * int status = httpConnection.getResponseCode();
			 * // HTTP response headers
			 * for (Entry<String, List<String>> header : httpConnection.getHeaderFields().entrySet()) {
			 * System.out.println(header.getKey() + "=" + header.getValue());
			 * }
			 */

			// HTTP response encoding
			String contentType = httpConnection.getHeaderField("Content-Type");
			for (String param : contentType.replace(" ", "").split(";")) {
				if (param.startsWith("charset=")) {
					charset = param.split("=", 2)[1];
					break;
				}
			}

			htmlResponseSb = new StringBuilder(1024);
			String lineSeparator = System.getProperty("line.separator");

			if (charset != null) {
				BufferedReader reader = null;
				try {
					InputStream response = httpConnection.getInputStream();
					reader = new BufferedReader(new InputStreamReader(response, charset));
					for (String line; (line = reader.readLine()) != null;) {
						htmlResponseSb.append(line);
						htmlResponseSb.append(lineSeparator);
					}
				} finally {
					if (reader != null) {
						reader.close();
					}
				}
			} else {
				// It's likely binary content, use InputStream/OutputStream.
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}

		if (htmlResponseSb != null) {
			return htmlResponseSb.toString();
		} else {
			return null;
		}
	}
}

/**
 * 
 */
package com.alexnevsky.web.util.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets URIs to lower case and removes spaces.
 * 
 * @author Alex Nevsky
 * 
 */
public class NiceUrlFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * @see http://stackoverflow.com/questions/2725102/
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			if (httpRequest.getMethod().equalsIgnoreCase("GET")) {
				String url = httpRequest.getRequestURI();

				boolean isSpaceFound = url.contains("%20");

				if (isSpaceFound) {
					HttpServletResponse httpResponce = (HttpServletResponse) response;
					String redirectToString = url.replaceAll("%20", "-");
					redirectToString = redirectToString.replaceAll("[-]+", "-");

					String queryString = httpRequest.getQueryString();
					if (queryString != null) {
						redirectToString = redirectToString.concat("?").concat(queryString);
					}

					httpResponce.sendRedirect(redirectToString);
				} else {
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
	}
}

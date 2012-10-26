/**
 * 
 */
package com.alexnevsky.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Alex Nevsky
 * 
 */
public class DateUtil {

	public static final long MILLISECONDS_IN_SECOND = 1000;
	public static final long SECOND_IN_MILLISECONDS = MILLISECONDS_IN_SECOND;
	public static final long MINUTE_IN_MILLISECONDS = SECOND_IN_MILLISECONDS * 60;
	public static final long HOUR_IN_MILLISECONDS = MINUTE_IN_MILLISECONDS * 60;
	public static final long DAY_IN_MILLISECONDS = HOUR_IN_MILLISECONDS * 24;

	/**
	 * Get difference between two dates in milliseconds.
	 * 
	 * @param start
	 *            e.g. Calendar start = Calendar.getInstance(); start.set(year, month, date);
	 * @param end
	 *            e.g. Calendar end = Calendar.getInstance(); end.set(year, month, date);
	 * @return difference in milliseconds
	 */
	public static long getDifference(Calendar start, Calendar end) {
		long difference = 0;

		Date startDate = start.getTime();
		Date endDate = end.getTime();
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		difference = endTime - startTime;

		return difference;
	}

	/**
	 * Get difference between two dates in seconds.
	 * 
	 * @param start
	 *            e.g. Calendar start = Calendar.getInstance(); start.set(year, month, date);
	 * @param end
	 *            e.g. Calendar end = Calendar.getInstance(); end.set(year, month, date);
	 * @return difference in seconds
	 */
	public static long getDifferenceInSeconds(Calendar start, Calendar end) {
		long difference = 0;

		Date startDate = start.getTime();
		Date endDate = end.getTime();
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		difference = endTime - startTime;

		return difference / DateUtil.SECOND_IN_MILLISECONDS;
	}

	/**
	 * Get difference between two time periods in seconds.
	 * 
	 * @param start
	 *            start period of time in milliseconds
	 * @param end
	 *            end period of time in milliseconds
	 * @return difference in seconds
	 */
	public static double getDifferenceInSeconds(long start, long end) {
		return (end - start) / (double) DateUtil.MILLISECONDS_IN_SECOND;
	}

	/**
	 * Get difference between two dates in minutes.
	 * 
	 * @param start
	 *            e.g. Calendar start = Calendar.getInstance(); start.set(year, month, date);
	 * @param end
	 *            e.g. Calendar end = Calendar.getInstance(); end.set(year, month, date);
	 * @return difference in minutes
	 */
	public static long getDifferenceInMinutes(Calendar start, Calendar end) {
		long difference = 0;

		Date startDate = start.getTime();
		Date endDate = end.getTime();
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		difference = endTime - startTime;

		return difference / DateUtil.MINUTE_IN_MILLISECONDS;
	}

	/**
	 * Get difference between two dates in hours.
	 * 
	 * @param start
	 *            e.g. Calendar start = Calendar.getInstance(); start.set(year, month, date);
	 * @param end
	 *            e.g. Calendar end = Calendar.getInstance(); end.set(year, month, date);
	 * @return difference in hours
	 */
	public static long getDifferenceInHours(Calendar start, Calendar end) {
		long difference = 0;

		Date startDate = start.getTime();
		Date endDate = end.getTime();
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		difference = endTime - startTime;

		return difference / DateUtil.HOUR_IN_MILLISECONDS;
	}

	/**
	 * Get difference between two dates in days.
	 * 
	 * @param start
	 *            e.g. Calendar start = Calendar.getInstance(); start.set(year, month, date);
	 * @param end
	 *            e.g. Calendar end = Calendar.getInstance(); end.set(year, month, date);
	 * @return difference in days
	 */
	public static long getDifferenceInDays(Calendar start, Calendar end) {
		long difference = 0;

		Date startDate = start.getTime();
		Date endDate = end.getTime();
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		difference = endTime - startTime;

		return difference / DateUtil.DAY_IN_MILLISECONDS;
	}

	/**
	 * Returns the date string in specified format.
	 * 
	 * @param date
	 *            Date to format.
	 * @param format
	 *            Format of the date, e.g. yyyy/MM/dd HH:mm:ss
	 * @return formatted date string or current date like yyyy/MM/dd HH:mm:ss if date or format null
	 */
	public static String dateToString(Date date, String format) {
		if ((date != null) && (format != null)) {
			DateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return dateFormat.format(date);
		}
	}
}

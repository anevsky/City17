/**
 * 
 */
package com.alexnevsky.web.util.type;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Alex Nevsky
 * 
 */
public class BirthDate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Properties
	// -------------------------------------------------------------------------------

	private int year;
	private int month;
	private int day;

	// Constructors
	// -------------------------------------------------------------------------------

	public BirthDate() {
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 */
	public BirthDate(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	// Getters
	// -------------------------------------------------------------------------------

	public int getYear() {
		return this.year;
	}

	public int getMonth() {
		return this.month;
	}

	public int getDay() {
		return this.day;
	}

	public int getAge() {
		int age = 0;

		if ((this.year != 0) && (this.month != 0) && (this.day != 0)) {
			Date currentDate = new Date();

			GregorianCalendar gregorianCalendarCurrentDate = new GregorianCalendar();
			gregorianCalendarCurrentDate.setTime(currentDate);

			int currentYear = gregorianCalendarCurrentDate.get(Calendar.YEAR);
			int currentMonth = gregorianCalendarCurrentDate.get(Calendar.MONTH) + 1;
			int currentDay = gregorianCalendarCurrentDate.get(Calendar.DAY_OF_MONTH);

			int diffYear = currentYear - this.year;

			if (currentMonth > this.month) {
				age = diffYear;
			} else if (currentMonth < this.month) {
				age = diffYear - 1;
			} else {
				// years and months equals, so how about days
				if (currentDay > this.day) {
					age = diffYear;
				} else if (currentDay < this.day) {
					age = diffYear - 1;
				} else {
					// years, months and days equals, so...
					age = diffYear;
				}
			}
		} else {
			return age;
		}

		return age;
	}

	// Setters
	// -------------------------------------------------------------------------------

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	// Override
	// -------------------------------------------------------------------------------

	@Override
	public int hashCode() {
		return ((this.year != 0) && (this.month != 0) && (this.day != 0)) ? (this.getClass().hashCode() + this.year
				+ this.month + this.day) : super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BirthDate)) {
			return false;
		}
		BirthDate other = (BirthDate) obj;
		if (this.day != other.day) {
			return false;
		}
		if (this.month != other.month) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the String representation of this entity.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(128);

		sb.append("BirthDate [year=");
		sb.append(this.year);
		sb.append(", month=");
		sb.append(this.month);
		sb.append(", day=");
		sb.append(this.day);
		sb.append("]");

		return sb.toString();
	}
}

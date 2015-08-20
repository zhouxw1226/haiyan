/*
 * Created on 2006-6-27
 *
 */
package haiyan.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import haiyan.common.exception.Warning;

/**
 * @author zhouxw
 */
public class DateUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	private static String DATE_DIVISION = "-";
	private static String TIME_DIVISION = ":";
	private static String DATE_TIME_DIVISION = " ";
	private int m_year;
	private int m_month;
	private int m_day;
	private int m_hour;
	private int m_minute;
	private int m_second;
	private static String isHolidays = PropUtil.getProperty("Holiday","holiday","");
	private static String overtime = PropUtil.getProperty("Holiday","overtime","");
	/**
	 * 
	 */
	public DateUtil() {
		GregorianCalendar now = new GregorianCalendar();
		m_year = now.get(1);
		m_month = now.get(2) + 1;
		m_day = now.get(5);
		m_hour = now.get(11);
		m_minute = now.get(12);
		m_second = now.get(13);
	}
	// /**
	// * @param date
	// * @throws Throwable
	// */
	// public DateUtil(java.sql.Date date) throws Throwable {
	// String s = sdf.format(date); // date.toString();
	// int r[] = getDateFields(s);
	// m_year = r[0];
	// m_month = r[1];
	// m_day = r[2];
	// m_hour = r[3];
	// m_minute = r[4];
	// m_second = r[5];
	// if (!isValid(m_year, m_month, m_day, m_hour, m_minute, m_second)) {
	// throw new Warning("DateUtil(" + date.toString() + ")");
	// } else {
	// return;
	// }
	// }
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @param date
	 * @throws Throwable
	 */
	public DateUtil(java.util.Date date) throws Throwable {
		this(sdf.format(date)); // date.toString();
	}
	/**
	 * @param date
	 * @throws Throwable
	 */
	public DateUtil(String date) throws Throwable {
		int r[] = getDateFields(date);
		m_year = r[0];
		m_month = r[1];
		m_day = r[2];
		m_hour = r[3];
		m_minute = r[4];
		m_second = r[5];
		if (!isValid(m_year, m_month, m_day, m_hour, m_minute, m_second)) {
			throw new Warning("DateUtil(" + date + ")");
		} else {
			return;
		}
	}
	/**
	 * @param dateUtil
	 * @throws Throwable
	 */
	public DateUtil(DateUtil dateUtil) throws Throwable {
		if (dateUtil == null) {
			throw new Warning("DateUtil(Null)");
		}
		if (!dateUtil.isValid()) {
			throw new Warning("DateUtil(" + dateUtil + ")");
		} else {
			m_year = dateUtil.m_year;
			m_month = dateUtil.m_month;
			m_day = dateUtil.m_day;
			m_hour = dateUtil.m_hour;
			m_minute = dateUtil.m_minute;
			m_second = dateUtil.m_second;
			return;
		}
	}
	/**
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @throws Throwable
	 */
	public DateUtil(int year, int month, int day, int hour, int minute,
			int second) throws Throwable {
		if (!isValid(year, month, day, hour, minute, second)) {
			throw new Warning("DateUtil(" + year + "," + month + "," + day
					+ "," + hour + "," + minute + "," + second + ")");
		} else {
			m_year = year;
			m_month = month;
			m_day = day;
			m_hour = hour;
			m_minute = minute;
			m_second = second;
			return;
		}
	}
	@Override
	public boolean equals(Object v) {
		if (!(v instanceof DateUtil)) {
			return false;
		}
		DateUtil d = (DateUtil) v;
		return m_year == d.m_year && m_month == d.m_month && m_day == d.m_day
				&& m_hour == d.m_hour && m_minute == d.m_minute
				&& m_second == d.m_second;
	}
	@Override
	public String toString() {
		return getFullString();
	}
	/**
	 * @return String
	 */
	public String getDateStringNew() {
		// yyyyMMdd
		String affMonth = m_month >= 10 ? String.valueOf(m_month) : "0"
				+ String.valueOf(m_month);
		String affDay = m_day >= 10 ? String.valueOf(m_day) : "0"
				+ String.valueOf(m_day);
		return String.valueOf(m_year) + affMonth + affDay;
	}
	/**
	 * @return String
	 */
	public String getDateString() {
		// yyyy-MM-dd
		String affMonth = m_month >= 10 ? String.valueOf(m_month) : "0"
				+ String.valueOf(m_month);
		String affDay = m_day >= 10 ? String.valueOf(m_day) : "0"
				+ String.valueOf(m_day);
		return String.valueOf(m_year) + DATE_DIVISION + affMonth
				+ DATE_DIVISION + affDay;
	}
	/**
	 * @return String
	 */
	public String getFullString() {
		// yyyy-MM-dd HH:mm:ss
		String affMonth = m_month >= 10 ? String.valueOf(m_month) : "0"
				+ String.valueOf(m_month);
		String affDay = m_day >= 10 ? String.valueOf(m_day) : "0"
				+ String.valueOf(m_day);
		String affHour = m_hour >= 10 ? String.valueOf(m_hour) : "0"
				+ String.valueOf(m_hour);
		String affMinute = m_minute >= 10 ? String.valueOf(m_minute) : "0"
				+ String.valueOf(m_minute);
		String affSecond = m_second >= 10 ? String.valueOf(m_second) : "0"
				+ String.valueOf(m_second);
		return String.valueOf(m_year) + DATE_DIVISION + affMonth
				+ DATE_DIVISION + affDay + DATE_TIME_DIVISION + affHour
				+ TIME_DIVISION + affMinute + TIME_DIVISION + affSecond;
	}
	/**
	 * @return int
	 */
	public int getYear() {
		return m_year;
	}
	/**
	 * @return int
	 */
	public int getQuarter() {
		return (m_month + 2) / 3;
	}
	/**
	 * @return int
	 */
	public int getMonth() {
		return m_month;
	}
	/**
	 * @return int
	 */
	public int getDay() {
		return m_day;
	}
	/**
	 * @return int
	 */
	public int getHour() {
		return m_hour;
	}
	/**
	 * @return int
	 */
	public int getMinute() {
		return m_minute;
	}
	/**
	 * @return int
	 */
	public int getSecond() {
		return m_second;
	}
	/**
	 * @return java.sql.Date
	 */
	public java.sql.Date getSqlDate() {
		return java.sql.Date.valueOf(getDateString());
	}
	/**
	 * @return long
	 */
	public long getTime() {
		return this.getSqlDate().getTime();
	}
	/**
	 * @return java.util.Date
	 */
	public java.util.Date getUtilDate() {
		// new java.util.Date(getDateString());
		java.util.Date dt = new java.util.Date();
		dt.setTime(getTime());
		return dt;
	}
	/**
	 * @param days
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getAdvanceDayUtil(int days) throws Throwable {
		DateUtil adr = new DateUtil(this);
		adr.advanceDays(days);
		return adr;
	}
	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getRealMonthEnd() throws Throwable {
		DateUtil r = getMonthStart();
		if (r.getMonth() == 1 || r.getMonth() == 3 || r.getMonth() == 5
				|| r.getMonth() == 7 || r.getMonth() == 8 || r.getMonth() == 10
				|| r.getMonth() == 12) {
			r.advanceDays(30);
		}
		if (r.getMonth() == 4 || r.getMonth() == 6 || r.getMonth() == 9
				|| r.getMonth() == 11) {
			r.advanceDays(29);
		}
		if (r.getMonth() == 2) {
			int year = r.getYear();
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				r.advanceDays(28);
			} else {
				r.advanceDays(27);
			}
		}
		return r;
	}
	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getRealMonthStart() throws Throwable {
		return new DateUtil(m_year, m_month, 1, 0, 0, 0);
	}
	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getMonthStart() throws Throwable {
		// return new DateUtil(m_year, m_month, 1, 0, 0, 0);
		int m_year_last = m_year;
		int m_month_last = m_month - 1;
		int m_day_last = m_day;
		if (m_month_last == 0) {
			if (m_day_last >= 2006) {
				m_month_last = m_month;
			} else {
				m_month_last = 12;
				m_year_last = m_year - 1;
			}
		}
		return new DateUtil(m_year_last, m_month_last, 1, 0, 0, 0);
	}
	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getMonthStartEx() throws Throwable {
		// Created by Pengsa
		int m_year_last = m_year;
		int m_month_last = m_month - 1;
		int m_day_last = m_day;
		if (m_day_last >= 1) {
			m_month_last = m_month;
		}
		if (m_month_last == 0) {
			m_month_last = 12;
			m_year_last = m_year - 1;
		}
		return new DateUtil(m_year_last, m_month_last, 1, 0, 0, 0);
	}
	/**
	 * @return String
	 */
	public String getTimeString() {
		String affHour = m_hour >= 10 ? String.valueOf(m_hour) : "0"
				+ String.valueOf(m_hour);
		String affMinute = m_minute >= 10 ? String.valueOf(m_minute) : "0"
				+ String.valueOf(m_minute);
		String affSecond = m_second >= 10 ? String.valueOf(m_second) : "0"
				+ String.valueOf(m_second);
		return affHour + TIME_DIVISION + affMinute + TIME_DIVISION + affSecond;
	}
	/**
	 * @return int
	 * @throws Throwable
	 */
	public int getWeekDay() throws Throwable {
		DateUtil std = new DateUtil("2000-01-03");
		int days = daysBetween(std);
		int m = days % 7;
		if (m >= 0) {
			return m + 1;
		} else {
			return m + 8;
		}
	}	
	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getWeekEnd() throws Throwable {
		DateUtil r = getWeekStart();
		r.advanceDays(7);
		return r;
	}
	/**
	 * @return DateUtil
	 * @throws Throwable
	 */
	public DateUtil getWeekStart() throws Throwable {
		DateUtil std = new DateUtil("2000-01-03");
		int days = daysBetween(std);
		int m = days % 7;
		DateUtil r;
		if (m >= 0) {
			r = getAdvanceDayUtil(-m);
		} else {
			r = getAdvanceDayUtil(-m - 7);
		}
		return r;
	}
	/**
	 * @return
	 */
	public boolean isValid() {
		return isValid(m_year, m_month, m_day, m_hour, m_minute, m_second);
	}
	/**
	 * @param date
	 * @return int
	 */
	public int daysBetween(DateUtil date) {
		return toJulian() - date.toJulian();
	}
	/**
	 * @param days
	 */
	public void advanceDays(int days) {
		fromJulian(toJulian() + days);
	}
	/**
	 * @param j
	 */
	private void fromJulian(int j) {
		int ja = j;
		int JGREG = 0x231519;
		if (j >= JGREG) {
			int jalpha = (int) (((double) (float) (j - 0x1c7dd0) - 0.25D) / 36524.25D);
			ja += (1 + jalpha) - (int) (0.25D * (double) jalpha);
		}
		int jb = ja + 1524;
		int jc = (int) (6680D + ((double) (float) (jb - 0x253abe) - 122.09999999999999D) / 365.25D);
		int jd = (int) ((double) (365 * jc) + 0.25D * (double) jc);
		int je = (int) ((double) (jb - jd) / 30.600100000000001D);
		m_day = jb - jd - (int) (30.600100000000001D * (double) je);
		m_month = je - 1;
		if (m_month > 12) {
			m_month -= 12;
		}
		m_year = jc - 4715;
		if (m_month > 2) {
			m_year--;
		}
		if (m_year <= 0) {
			m_year--;
		}
	}
	/**
	 * @return int
	 */
	private int toJulian() {
		int jy = m_year;
		if (m_year < 0) {
			jy++;
		}
		int jm = m_month;
		if (m_month > 2) {
			jm++;
		} else {
			jy--;
			jm += 13;
		}
		int jul = (int) (Math.floor(365.25D * (double) jy)
				+ Math.floor(30.600100000000001D * (double) jm)
				+ (double) m_day + 1720995D);
		int IGREG = 0x8fc1d;
		if (m_day + 31 * (m_month + 12 * m_year) >= IGREG) {
			int ja = (int) (0.01D * (double) jy);
			jul += (2 - ja) + (int) (0.25D * (double) ja);
		}
		return jul;
	}
	/**
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return boolean
	 */
	public final static boolean isValid(int year, int month, int day, int hour,
			int minute, int second) {
		if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0
				|| second > 59) {
			return false;
		}
		if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
			return false;
		}
		switch (month) {
		case 1: // '\001'
		case 3: // '\003'
		case 5: // '\005'
		case 7: // '\007'
		case 8: // '\b'
		case 10: // '\n'
		case 12: // '\f'
			return true;

		case 4: // '\004'
		case 6: // '\006'
		case 9: // '\t'
		case 11: // '\013'
			return day <= 30;

		case 2: // '\002'
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				return day <= 29;
			}
			return day <= 28;
		}
		return false;
	}
	/**
	 * @param sDate
	 * @return boolean
	 * @throws Throwable
	 */
	public final static boolean isValid(String sDate) throws Throwable {
		int r[];
		try {
			r = getDateFields(sDate);
		} catch (Warning e) {
			return false;
		}
		return isValid(r[0], r[1], r[2], r[3], r[4], r[5]);
	}
	/**
	 * @param str
	 * @param token
	 * @return int[]
	 */
	private static int[] getIntsFromStr(String str, String token) {
		StringTokenizer st = new StringTokenizer(str, token);
		Vector<String> v = new Vector<String>();
		for (; st.hasMoreTokens(); v.add(st.nextToken())) {
			//
		}
		int ints[] = new int[v.size()];
		for (int i = 0; i < v.size(); i++) {
			ints[i] = Integer.parseInt((String) v.get(i));
		}
		return ints;
	}
	/**
	 * @param time
	 * @return int[]
	 * @throws Throwable
	 */
	public final static int[] getDateFields(String time) throws Throwable {
		// String dayAndtime[] = getStringsFromStr(t, DATE_TIME_DIVISION);
		String dayAndtime[] = StringUtil.split(time, DATE_TIME_DIVISION);
		if (dayAndtime.length != 2 && dayAndtime.length != 1) {
			throw new Warning("DateUtil.getDateFields(" + time + ")");
		}
		int dates[] = getIntsFromStr(dayAndtime[0], DATE_DIVISION);
		if (dates.length != 3) {
			throw new Warning("DateUtil.getDateFields(" + time + ")");
		}
		int times[] = new int[3];
		if (dayAndtime.length == 2) {
			int ts[] = getIntsFromStr(dayAndtime[1], TIME_DIVISION);
			if (ts.length > 3) {
				throw new Warning("DateUtil.getDateFields(" + time + ")");
			}
			if (ts.length == 1) {
				times[0] = ts[0];
				times[1] = 0;
				times[2] = 0;
			}
			if (ts.length == 2) {
				times[0] = ts[0];
				times[1] = ts[1];
				times[2] = 0;
			}
			if (ts.length == 3) {
				times[0] = ts[0];
				times[1] = ts[1];
				times[2] = ts[2];
			}
		} else {
			times[0] = 0;
			times[1] = 0;
			times[2] = 0;
		}
		int r[] = new int[6];
		r[0] = dates[0];
		r[1] = dates[1];
		r[2] = dates[2];
		r[3] = times[0];
		r[4] = times[1];
		r[5] = times[2];
		return r;
	}
	/**
	 * @return Date
	 */
	public final static java.sql.Date getCurrDate() {
		return new java.sql.Date(new java.sql.Timestamp(
				System.currentTimeMillis()).getTime());
	}
	/**
	 * @param sd
	 * @param ed
	 * @return int
	 */
	public final static int daysBetween(DateUtil sd, DateUtil ed) {
		return ed.toJulian() - sd.toJulian();
	}
	/**
	 * @return String
	 * @throws ParseException
	 */
	public final static String getLastTime() {
		try {
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastTime = new java.sql.Timestamp(System.currentTimeMillis()).toString();
			// DebugUtil.debug(lastTime);
			return lastTime;
		} catch (Throwable ex) {
			throw Warning.wrapException(ex);
		}
	}

	/**
	 * @return String
	 * @throws ParseException
	 */
	public final static String getLastTime(String style) {
		try {
			if (style == null)
				style = "yyyy-MM-dd HH:mm:ss";
			else if ("yyyy-MM-dd hh:mm:ss".equals(style))
				style = "yyyy-MM-dd HH:mm:ss";
			return format(new Date(), style);
			// java.text.DateFormat df = new SimpleDateFormat(style);
			// SimpleDateFormat sdf = new SimpleDateFormat("",
			// Locale.SIMPLIFIED_CHINESE);
			// sdf.applyPattern(style);
			// SimpleDateFormat sdf = new SimpleDateFormat(style);
			// return new java.sql.Timestamp(sdf.parse(getLastTime()).getTime())
			// .toString().substring(0, 19);
		} catch (Throwable ex) {
			throw Warning.wrapException(ex);
		}
	}
	/**
	 * @param day
	 * @return java.util.Date
	 */
	public final static java.util.Date getFirstDayOfMonth(java.util.Date day) {
		if (day == null)
			day = new java.util.Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(day);
		gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
		gc.set(GregorianCalendar.HOUR, 0);
		gc.set(GregorianCalendar.MINUTE, 0);
		gc.set(GregorianCalendar.SECOND, 0);
		//
		return gc.getTime();
	}
	/**
	 * @param day
	 * @return java.util.Date
	 */
	public final static java.util.Date getLastDayOfMonth(java.util.Date day) {
		if (day == null)
			day = new java.util.Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(day);
		int lastMonthDay = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		gc.set(GregorianCalendar.DAY_OF_MONTH, lastMonthDay);
		gc.set(GregorianCalendar.HOUR, 23);
		gc.set(GregorianCalendar.MINUTE, 59);
		gc.set(GregorianCalendar.SECOND, 59);
		return gc.getTime();
	}
	/**
	 * @param day
	 * @return java.util.Date
	 */
	public final static java.util.Date getFirstDayOfQuarter(java.util.Date day) {
		if (day == null)
			day = new java.util.Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(day);
		// Check in wich quarter we are....
		int currentMonth = gc.get(GregorianCalendar.MONTH);
		if (currentMonth <= GregorianCalendar.MARCH) // From January to March
		{
			gc.set(gc.get(GregorianCalendar.YEAR), GregorianCalendar.JANUARY, 1);
		} else if (currentMonth <= GregorianCalendar.JUNE) // From April to
		// June
		{
			gc.set(gc.get(GregorianCalendar.YEAR), GregorianCalendar.APRIL, 1);
		} else if (currentMonth <= GregorianCalendar.SEPTEMBER) // From July to
		// September
		{
			gc.set(gc.get(GregorianCalendar.YEAR), GregorianCalendar.JULY, 1);
		} else // if (currentMonth <= GregorianCalendar.MARCH) // From Octoner
		// to December
		{
			gc.set(gc.get(GregorianCalendar.YEAR), GregorianCalendar.OCTOBER, 1);
		}
		return getFirstDayOfMonth(gc.getTime());
	}
	/**
	 * @param day
	 * @return java.util.Date
	 */
	public final static java.util.Date getLastDayOfQuarter(java.util.Date day) {
		if (day == null)
			day = new java.util.Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(day);
		// Check in wich quarter we are....
		int currentMonth = gc.get(GregorianCalendar.MONTH);
		if (currentMonth <= GregorianCalendar.MARCH) // From January to March
		{
			gc.set(gc.get(GregorianCalendar.YEAR), GregorianCalendar.MARCH, 1);
		} else if (currentMonth <= GregorianCalendar.JUNE) // From April to
		// June
		{
			gc.set(gc.get(GregorianCalendar.YEAR), GregorianCalendar.JUNE, 1);
		} else if (currentMonth <= GregorianCalendar.SEPTEMBER) // From July to
		// September
		{
			gc.set(gc.get(GregorianCalendar.YEAR), GregorianCalendar.SEPTEMBER, 1);
		} else // if (currentMonth <= GregorianCalendar.MARCH) // From Octoner
		// to December
		{
			gc.set(gc.get(GregorianCalendar.YEAR), GregorianCalendar.DECEMBER, 1);
		}
		return getLastDayOfMonth(gc.getTime());
	}
	/**
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public final static java.util.Date getDate(String value)
			throws ParseException {
		java.util.Date d = null;
		// try {
		if (value == null) {
			return d;
		} else if (value.length() > 10) {
			if (value.endsWith("Z"))
				value = value.substring(0, value.length() - 1);
			if (value.indexOf('T') >= 0)
				value = StringUtil.replaceAll(value, "T", " ");
			if (value.length() < 18) {
				value = value.substring(0, 10);
				d = new SimpleDateFormat("yyyy-MM-dd").parse(value);
			} else {
				// if (value.indexOf(" ") == 3)
				// d = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
				// .parse("Sun Jan 04 00:00:00 CST 2009");
				// else
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
			}
		} else if (!StringUtil.isStrBlankOrNull(value))
			d = new SimpleDateFormat("yyyy-MM-dd").parse(value);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		return d;
	}
	/**
	 * @param timeStr
	 * @param dateStyle
	 * @return java.util.Date
	 */
	public final static java.util.Date getDate(String timeStr, String dateStyle) {
		try {
			return new SimpleDateFormat(dateStyle).parse(timeStr);
		} catch (Throwable ex) {
			throw Warning.getWarning(ex);
		}
	}
	/**
	 * @param timeStr
	 * @param dateStyle
	 * @return String
	 */
	public final static String format(String timeStr, String dateStyle) {
		String style = dateStyle == null ? "yyyy-MM-dd" : dateStyle;
		if (style.length() > timeStr.length() && timeStr.indexOf(" ") < 0) {
			timeStr += " 00:00:00"; // 冗余修正
		}
		java.util.Date date = getDate(timeStr, style);
		return format(date, style);
		// return new SimpleDateFormat(style).format(date);
	}
	/**
	 * 将日期类型转化为字符串类型
	 * 
	 * @param timeStr
	 * @param sFormat
	 * @return String
	 */
	public final static String format(java.util.Date date, String sFormat) {
		sFormat = sFormat == null ? "yyyy-MM-dd" : sFormat;
		SimpleDateFormat sy1 = null;
		// return new SimpleDateFormat(style).format(date);
		// if ("YY-MM-DD".equalsIgnoreCase(sFormat)) {
		// sy1 = new SimpleDateFormat("yyyy-MM-dd");
		// String sDate = sy1.format(date);
		// return sDate.substring(2, sDate.length());
		// } else if ("YYMMDD".equalsIgnoreCase(sFormat)) {
		// sy1 = new SimpleDateFormat("yyyy-MM-dd");
		// String sDate = sy1.format(date);
		// return sDate.substring(2, sDate.length()).replaceAll("-", "");
		// } else {
		// else if ("YYYY-MM-DD HH:MM:SS".equalsIgnoreCase(sFormat))
		// sFormat = "yyyy-MM-dd HH:mm:ss";
		if ("YY-MM-DD".equalsIgnoreCase(sFormat))
			sFormat = "yy-MM-dd";
		else if ("YYMMDD".equalsIgnoreCase(sFormat))
			sFormat = "yyMMdd";
		else if ("MM-DD".equalsIgnoreCase(sFormat))
			sFormat = "MM-dd";
		else if ("MMDD".equalsIgnoreCase(sFormat))
			sFormat = "MMdd";
		else if ("YYYY-MM-DD".equalsIgnoreCase(sFormat))
			sFormat = "yyyy-MM-dd";
		else if ("YYYY-MM".equalsIgnoreCase(sFormat))
			sFormat = "yyyy-MM";
		else if ("YYYYMM".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMM";
		else if ("YYYY".equalsIgnoreCase(sFormat))
			sFormat = "yyyy";
		else if ("YYYYMMDD".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMMdd";
		else if ("YYYYMMDDHHMMSS".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMMddHHmmss";
		else if ("YYYYMMDDHH".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMMddHH";
		else if ("YYYYMMDDHHMM".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMMddHHmm";
		else if ("YYYYMMDDMMSS".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMMddmmss";
		else if ("YYYYMMDDHHSS".equalsIgnoreCase(sFormat))
			sFormat = "yyyyMMddHHss";
		// sFormat = "yyyy-MM-dd";
		// sy1 = new SimpleDateFormat(sFormat);
		// return sy1.format(date).replaceAll("-", "");
		sy1 = new SimpleDateFormat(sFormat);
		return sy1.format(date);
		// }
	}
	public static String format(Object date, String dateStyle) {
		if (date == null) {
			return null;
		}
		if ((date instanceof java.util.Date)) {
			return format((java.util.Date) date, dateStyle);
		}
		String s = date.toString();
		if (s.length() == 0) {
			return null;
		}
		return format(s, dateStyle);
	}
	// /**
	// * 将日期类型转化为字符串类型
	// *
	// * @param timeStr
	// * @param dateStyle
	// * @return String
	// */
	// public final static String format(java.sql.Date date, String dateStyle) {
	// String style = dateStyle == null ? "yyyy-MM-dd" : dateStyle;
	// return new SimpleDateFormat(style).format(date);
	// }
	//
	// /**
	// * 将日期类型转化为字符串类型
	// *
	// * @param timeStr
	// * @param dateStyle
	// * @return String
	// */
	// public final static String format(Object date, String dateStyle) {
	// assert date != null;
	// if (date instanceof java.sql.Date) {
	// return format((java.sql.Date) date, dateStyle);
	// } else if (date instanceof java.util.Date) {
	// return format((java.util.Date) date, dateStyle);
	// } else {
	// return format(date.toString(), dateStyle);
	// }
	// }
	/**
	 * @return 返回当前中国日期
	 * 
	 */
	public final static String getLastTimeChina() {
		return ChinaDate.today();
	}
	/**
	 * @return 返回当前中国节日
	 */
	public final static String getFtvChina() {
		String ftv = ChinaDate.todayFtv();
		if (ftv != null)
			return ftv.substring(5);
		return ftv;
	}
	/**
	 * @param date
	 * @return 返回当时中国节日
	 */
	public final static String getFtvChina(Date date) {
		String ftv = ChinaDate.dateFtv(date);
		if (ftv != null)
			return ftv.substring(5);
		return ftv;
	}
	/**
	 * @param date
	 * @param adDays
	 * @return 返回当时中国节日
	 * @throws Throwable
	 */
	public final static String getFtvChina(Date date, int adDays)
			throws Throwable {
		DateUtil dateUtil = new DateUtil(date);
		dateUtil.advanceDays(adDays);
		String ftv = ChinaDate.dateFtv(dateUtil.getUtilDate());
		if (ftv != null)
			return ftv.substring(5);
		return ftv;
	}
	
	
	/**
	 * 判断是否是法定节假日
	 * @param sdate
	 */
	public static boolean isHoliday(Date sdate){ 
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdate);
		boolean flag = false;//不是假日
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		String date = df.format(sdate);
		if (date.matches(isHolidays)) {
			flag = true;//法定节假日
		}else if (date.matches(overtime)) {
			flag = false;//节假日前后加班日
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			flag = true;//周末不上班
		}
		return flag ;
	}
	
	/**
	 * 使用演示
	 * 
	 * @param args
	 * @throws Throwable
	 */
	public final static void main(String[] args) throws Throwable {
		System.out.println(DateUtil.format(
				DateUtil.getFirstDayOfMonth(getCurrDate()), null));
		System.out.println(DateUtil.format(
				DateUtil.getFirstDayOfQuarter(getCurrDate()), null));
		System.out.println(DateUtil.format(
				DateUtil.getLastDayOfMonth(getCurrDate()), null));
		System.out.println(DateUtil.format(
				DateUtil.getLastDayOfQuarter(getCurrDate()), null));
		System.out.println(DateUtil.getLastTimeChina());
		// System.out.println(new DateUtil().getWeekEnd());
		// System.out.println(getFtvChina());
		// System.out.println(getFtvChina(new Date(), 2));
		// System.out.println(dateFtv(Calendar.getInstance(Locale.SIMPLIFIED_CHINESE).));
	}
}
class ChinaDate {
	final private static long[] lunarInfo = new long[] { 0x04bd8, 0x04ae0,
			0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0,
			0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540,
			0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5,
			0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
			0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3,
			0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
			0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0,
			0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8,
			0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570,
			0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5,
			0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0,
			0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50,
			0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0,
			0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
			0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7,
			0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50,
			0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954,
			0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
			0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0,
			0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0,
			0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20,
			0x0ada0 };
	final private static int[] year20 = new int[] { 1, 4, 1, 2, 1, 2, 1, 1, 2,
			1, 2, 1 };
	final private static int[] year19 = new int[] { 0, 3, 0, 1, 0, 1, 0, 0, 1,
			0, 1, 0 };
	final private static int[] year2000 = new int[] { 0, 3, 1, 2, 1, 2, 1, 1,
			2, 1, 2, 1 };
	// //
	private final static String[] nStr1 = new String[] { "", "正", "二", "三",
			"四", "五", "六", "七", "八", "九", "十", "十一", "十二" };
	// 天干
	private final static String[] gan = new String[] { "甲", "乙", "丙", "丁", "戊",
			"己", "庚", "辛", "壬", "癸" };
	// 地支
	private final static String[] zhi = new String[] { "子", "丑", "寅", "卯", "辰",
			"巳", "午", "未", "申", "酉", "戌", "亥" };
	// 属相
	private final static String[] animals = new String[] { "鼠", "牛", "虎", "兔",
			"龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
	// 分时
	@SuppressWarnings("unused")
	private final static String[] solarTerm = new String[] { "小寒", "大寒", "立春",
			"雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑",
			"立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };
	// 中国新节日
	private final static String[] sFtv = new String[] { "0101*元旦", "0214 情人节",
			"0308 妇女节", "0312 植树节", "0315 消费者权益日", "0401 愚人节", "0501 劳动节",
			"0504 青年节", "0512 护士节", "0601 儿童节", "0701 建党节", "0801 建军节",
			"0808 父亲节", "0909 毛泽东逝世纪念", "0910 教师节", "0928 孔子诞辰", "1001*国庆节",
			"1006 老人节", "1024 联合国日", "1112 孙中山诞辰", "1220 澳门回归", "1225 圣诞节",
			"1226 毛泽东诞辰" };
	// 中国旧传统节日
	private final static String[] lFtv = new String[] { "0101*农历春节",
			"0115 元宵节", "0505 端午节", "0707 七夕情人节", "0815 中秋节", "0909 重阳节",
			"1208 腊八节", "1224 小年", "0100*除夕" };
	// private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 EEEEE");
	/**
	 * 传回农历 y年的总天数
	 * 
	 * @param y
	 * @return int
	 */
	final private static int lYearDays(int y) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((lunarInfo[y - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + leapDays(y));
	}
	/**
	 * 传回农历 y年闰月的天数
	 * 
	 * @param y
	 * @return int
	 */
	final private static int leapDays(int y) {
		if (leapMonth(y) != 0) {
			if ((lunarInfo[y - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}
	/**
	 * 传回农历 y年闰哪个月 1-12 , 没闰传回 0
	 * 
	 * @param y
	 * @return int
	 */
	final private static int leapMonth(int y) {
		return (int) (lunarInfo[y - 1900] & 0xf);
	}
	/**
	 * 传回农历 y年m月的总天数
	 * 
	 * @param y
	 * @param m
	 * @return int
	 */
	final private static int monthDays(int y, int m) {
		if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
			return 29;
		else
			return 30;
	}
	/**
	 * 传回农历 y年的生肖
	 * 
	 * @param y
	 * @return String
	 */
	final static String animalsYear(int y) {
		return animals[(y - 4) % 12];
	}
	/**
	 * 传入 月日的offset 传回干支,0=甲子
	 * 
	 * @param num
	 * @return String
	 */
	final private static String cyclicalm(int num) {
		return (gan[num % 10] + zhi[num % 12]);
	}
	/**
	 * 传入 offset 传回干支, 0=甲子
	 * 
	 * @param y
	 * @return String
	 */
	final static String cyclical(int y) {
		int num = y - 1900 + 36;
		return (cyclicalm(num));
	}
	/**
	 * 传出农历.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
	 * 
	 * @param y
	 * @param m
	 * @return long[]
	 */
	@SuppressWarnings("unused")
	final private long[] Lunar(final int y, final int m) {
		long[] nongDate = new long[7];
		int i = 0, temp = 0, leap = 0;
		Date baseDate = new GregorianCalendar(1900 + 1900, 1, 31).getTime();
		Date objDate = new GregorianCalendar(y + 1900, m, 1).getTime();
		long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
		if (y < 2000)
			offset += year19[m - 1];
		if (y > 2000)
			offset += year20[m - 1];
		if (y == 2000)
			offset += year2000[m - 1];
		nongDate[5] = offset + 40;
		nongDate[4] = 14;
		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = lYearDays(i);
			offset -= temp;
			nongDate[4] += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			nongDate[4] -= 12;
		}
		nongDate[0] = i;
		nongDate[3] = i - 1864;
		leap = leapMonth(i); // 闰哪个月
		nongDate[6] = 0;
		for (i = 1; i < 13 && offset > 0; i++) {
			// 闰月
			if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
				--i;
				nongDate[6] = 1;
				temp = leapDays((int) nongDate[0]);
			} else {
				temp = monthDays((int) nongDate[0], i);
			}
			// 解除闰月
			if (nongDate[6] == 1 && i == (leap + 1))
				nongDate[6] = 0;
			offset -= temp;
			if (nongDate[6] == 0)
				nongDate[4]++;
		}
		if (offset == 0 && leap > 0 && i == leap + 1) {
			if (nongDate[6] == 1) {
				nongDate[6] = 0;
			} else {
				nongDate[6] = 1;
				--i;
				--nongDate[4];
			}
		}
		if (offset < 0) {
			offset += temp;
			--i;
			--nongDate[4];
		}
		nongDate[1] = i;
		nongDate[2] = offset + 1;
		return nongDate;
	}
	/**
	 * 传出y年m月d日对应的农历.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
	 * 
	 * @param y
	 * @param m
	 * @param d
	 * @return long[]
	 */
	final static long[] calElement(int y, int m, int d) {
		long[] nongDate = new long[7];
		int i = 0, temp = 0, leap = 0;
		Date baseDate = new GregorianCalendar(0 + 1900, 0, 31).getTime();
		Date objDate = new GregorianCalendar(y, m - 1, d).getTime();
		long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
		nongDate[5] = offset + 40;
		nongDate[4] = 14;

		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = lYearDays(i);
			offset -= temp;
			nongDate[4] += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			nongDate[4] -= 12;
		}
		nongDate[0] = i;
		nongDate[3] = i - 1864;
		leap = leapMonth(i); // 闰哪个月
		nongDate[6] = 0;
		for (i = 1; i < 13 && offset > 0; i++) {
			// 闰月
			if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
				--i;
				nongDate[6] = 1;
				temp = leapDays((int) nongDate[0]);
			} else {
				temp = monthDays((int) nongDate[0], i);
			}
			// 解除闰月
			if (nongDate[6] == 1 && i == (leap + 1))
				nongDate[6] = 0;
			offset -= temp;
			if (nongDate[6] == 0)
				nongDate[4]++;
		}
		if (offset == 0 && leap > 0 && i == leap + 1) {
			if (nongDate[6] == 1) {
				nongDate[6] = 0;
			} else {
				nongDate[6] = 1;
				--i;
				--nongDate[4];
			}
		}
		if (offset < 0) {
			offset += temp;
			--i;
			--nongDate[4];
		}
		nongDate[1] = i;
		nongDate[2] = offset + 1;
		return nongDate;
	}
	/**
	 * @param day
	 * @return String
	 */
	final static String getChinaDate(int day) {
		String a = "";
		if (day == 10)
			return "初十";
		if (day == 20)
			return "二十";
		if (day == 30)
			return "三十";
		int two = (int) ((day) / 10);
		if (two == 0)
			a = "初";
		if (two == 1)
			a = "十";
		if (two == 2)
			a = "廿";
		if (two == 3)
			a = "三";
		int one = (int) (day % 10);
		switch (one) {
		case 1:
			a += "一";
			break;
		case 2:
			a += "二";
			break;
		case 3:
			a += "三";
			break;
		case 4:
			a += "四";
			break;
		case 5:
			a += "五";
			break;
		case 6:
			a += "六";
			break;
		case 7:
			a += "七";
			break;
		case 8:
			a += "八";
			break;
		case 9:
			a += "九";
			break;
		}
		return a;
	}
	final static String today() {
		Calendar today = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int date = today.get(Calendar.DATE);
		long[] l = calElement(year, month, date);
		//
		StringBuffer sToday = new StringBuffer();
		try {
			sToday.append(new SimpleDateFormat("yyyy年M月d日 EEEEE").format(today
					.getTime()));
			sToday.append(" 农历");
			sToday.append(cyclical(year));
			sToday.append('(');
			sToday.append(animalsYear(year));
			sToday.append(")年");
			sToday.append(nStr1[(int) l[1]]);
			sToday.append("月");
			sToday.append(getChinaDate((int) (l[2])));
			return sToday.toString();
		} finally {
			sToday = null;
		}
	}
	final static String todayFtv() {
		return dateFtv(new Date());
	}
	final static String dateFtv(Date tDate) {
		String dateFtv = null;
		// Calendar cal = new GregorianCalendar();
		// int year = tDate.getYear();
		// int month = tDate.getMonth();
		// int date = tDate.getDate();
		Calendar cal = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
		cal.setTime(tDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		// //
		String m0 = month > 10 ? "" + month : "0" + month;
		String d0 = date > 10 ? "" + date : "0" + date;
		for (String sFtvU : sFtv) {
			if (sFtvU.startsWith(m0 + d0)) {
				dateFtv = sFtvU;
			}
		}
		// //
		long[] l = calElement(year, month, date);
		String m1 = l[1] > 10 ? "" + l[1] : "0" + l[1];
		String d1 = l[2] > 10 ? "" + l[2] : "0" + l[2];
		for (String lFtvU : lFtv) {
			if (lFtvU.startsWith(m1 + d1)) {
				if (dateFtv == null)
					dateFtv = lFtvU;
				else
					dateFtv += " " + lFtvU;
			}
		}
		return dateFtv;
	}
	/**
	 * 农历日历工具使用演示
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(today());
	}

	
}
// public static String dateFtv(Calendar cal) {
// int year = cal.get(Calendar.YEAR);
// int month = cal.get(Calendar.MONTH) + 1;
// int date = cal.get(Calendar.DATE);
// long[] l = calElement(year, month, date);
// // for (long li : l) {
// // System.out.println(li);
// // }
// String m = l[1] > 10 ? "" + l[1] : "0" + l[1];
// String d = l[2] > 10 ? "" + l[2] : "0" + l[2];
// for (String sFtvU : sFtv) {
// if (sFtvU.startsWith(m + d))
// return sFtvU;
// }
// for (String lFtvU : lFtv) {
// if (lFtvU.startsWith(m + d))
// return lFtvU;
// }
// return null;
// }
// /**
// * @param args
// */
// public final static void main(String[] args) {
// try {
// //
// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
// // st.setNanos(1);
// System.out.println(new java.sql.Timestamp(System.currentTimeMillis()));
// System.out.println(new java.util.Date());
// System.out.println(new java.sql.Timestamp(sdf.parse(getLastTime()).getTime()));
// System.out.println(sdf.format(new java.util.Date()));
// } catch (ParseException e) {
// e.printStackTrace();
// }
// }
// /**
// * @param args
// * @throws Throwable
// */
// public final static void main(String[] args) throws Throwable {
// // DebugUtil.debug(getLastTime("yyyy-MM-dd hh:mm:ss"));
// // //DebugUtil.debug(getLastTime("yyyyMMdd hhmmss"));
// // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
// // String lastEditTime = sdf.parse(
// // new java.sql.Timestamp(System.currentTimeMillis()).toString()).toString();
// // DebugUtil.debug(lastEditTime);
// // lastEditTime = new java.sql.Timestamp(System.currentTimeMillis()).toString();
// // DebugUtil.debug(lastEditTime);
// // java.sql.Date date = new java.sql.Date(new java.sql.Timestamp(System.currentTimeMillis()).getTime());
// // DebugUtil.debug(date.toString());
// //
// // DebugUtil.debug(sdf.parse("2006-09-01 11:30:00").getTime()
// // - sdf.parse("2006-09-01 11:00:00").getTime());
// DateUtil checkDate = new DateUtil(2006, 1, 1, 0, 0, 0);
// DateUtil currDate = new DateUtil();
// DebugUtil.debug(currDate.toJulian() - checkDate.toJulian());
// DebugUtil.debug(checkDate.toJulian());
// }
package net.rippletec.module;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作模块
 * 
 * @author rolealiu
 * @updateTime 2015/05/28
 */
public class DateModule
{
	/**
	 * 获取当前年份
	 */
	public static int getYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 */
	public static int getMonth()
	{
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 获取当前日期
	 */
	public static int getDay()
	{
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前星期
	 */
	public static int getWeek()
	{
		int week = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		week = calendar.get(Calendar.DAY_OF_WEEK);
		// 将输入星期换为周一至周日（1为周日）
		return week >= 2 ? week - 1 : 7;
	}

	/**
	 * 将yyyyMMdd日期转换为yyyy年MM月dd日
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDate(String dateString)
	{
		String dateFormate = "yyyyMMdd";
		String formate = "yyyy年MM月dd日";
		SimpleDateFormat df1 = new SimpleDateFormat(dateFormate);
		SimpleDateFormat df2 = new SimpleDateFormat(formate);
		Date date = null;
		try
		{
			date = df1.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return df2.format(date);
	}

	/**
	 * 获取YYYY年MM月DD日
	 */
	public static String getDate()
	{
		SimpleDateFormat date = (SimpleDateFormat) SimpleDateFormat
				.getDateInstance();
		return date.format(new Date());
	}

	/**
	 * 根据月日获取星座
	 */
	public static String getConstellation(int month, int day)
	{
		if ((month == 1 && day >= 20) || (month == 2 && day <= 18))
		{
			return "水瓶座";
		}
		else if ((month == 2 && day >= 19) || (month == 3 && day <= 20))
		{
			return "双鱼座";
		}
		else if ((month == 3 && day >= 21) || (month == 4 && day <= 19))
		{
			return "白羊座";
		}
		else if ((month == 4 && day >= 20) || (month == 5 && day <= 20))
		{
			return "金牛座";
		}
		else if ((month == 5 && day >= 21) || (month == 6 && day <= 21))
		{
			return "双子座";
		}
		else if ((month == 6 && day >= 22) || (month == 7 && day <= 22))
		{
			return "巨蟹座";
		}
		else if ((month == 7 && day >= 23) || (month == 8 && day <= 22))
		{
			return "狮子座";
		}
		else if ((month == 8 && day >= 23) || (month == 9 && day <= 22))
		{
			return "处女座";
		}
		else if ((month == 9 && day >= 23) || (month == 10 && day <= 23))
		{
			return "天秤座";
		}
		else if ((month == 10 && day >= 24) || (month == 11 && day <= 22))
		{
			return "天蝎座";
		}
		else if ((month == 11 && day >= 23) || (month == 12 && day <= 21))
		{
			return "射手座";
		}
		else if ((month == 12 && day >= 22) || (month == 1 && day <= 19))
		{
			return "魔蝎座";
		}
		return "";
	}

	/**
	 * 根据年月日获取年龄
	 */
	public static int getAge(int year, int month, int day)
	{
		// 初始化年龄为0
		int age = 0;
		int nowYear = 0;
		int nowMonth = 0;
		int nowDay = 0;

		Calendar calendar = Calendar.getInstance();

		// 获取当前年月日
		nowYear = calendar.get(Calendar.YEAR);
		nowMonth = calendar.get(Calendar.MONTH) + 1;
		nowDay = calendar.get(Calendar.DAY_OF_MONTH);

		if (year < nowYear)
		{
			if (month < nowMonth)
			{
				age = nowYear - year;
			}
			else if (month == nowMonth)
			{
				age = nowYear - year;
				if (day > nowDay)
				{
					age--;
				}
			}
			else
			{
				age = nowYear - year - 1;
			}
		}
		return age;
	}

	/**
	 * 获取时间偏移量
	 * 
	 * @author JiazhaoChen
	 * @updateTime 2015/05/28
	 */
	public static String getOffsetTime(Date time)
	{

		if (time == null)
			return "";
		Date date = new Date(System.currentTimeMillis());
		long difference = date.getTime() - time.getTime();

		if (difference < 1000 * 60 * 1)
			return "一分钟前";
		if (difference < 1000 * 60 * 2)
			return "两分钟前";
		if (difference < 1000 * 60 * 3)
			return "三分钟前";
		if (difference < 1000 * 60 * 5)
			return "五分钟前";
		if (difference < 1000 * 60 * 10)
			return "十分钟前";
		if (difference < 1000 * 60 * 30)
			return "半小时前";
		if (difference < 1000 * 60 * 60)
			return "一小时前";
		if (difference < 1000 * 60 * 60 * 2)
			return "两小时前";
		if (difference < 1000 * 60 * 60 * 3)
			return "三小时前";
		if (difference < 1000 * 60 * 60 * 6)
			return "六小时前";
		if (difference < 1000 * 60 * 60 * 12)
			return "半天前";
		if (difference < 1000 * 60 * 60 * 24)
			return "一天前";
		if (difference < 1000 * 60 * 60 * 24 * 2)
			return "两天前";
		if (difference < 1000 * 60 * 60 * 24 * 3)
			return "三天前";
		if (difference < 1000 * 60 * 60 * 24 * 7)
			return "一周前";

		return (String) DateFormat.format("yyyy-MM-dd", time);
	}

}

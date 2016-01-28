package net.rippletec.module;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast弹框通知
 * @author rolealiu
 * @updateTime 2015/05/07
 */
public class ToastModule
{
	/**
	 * 显示判断
	 */
	public static boolean	isShow	= true;

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, String message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int resID)
	{
		if (isShow)
			Toast.makeText(context, resID, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, String message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int resID)
	{
		if (isShow)
			Toast.makeText(context, resID, Toast.LENGTH_LONG).show();
	}
}

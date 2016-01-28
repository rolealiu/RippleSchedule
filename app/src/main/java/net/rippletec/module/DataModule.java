package net.rippletec.module;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.EditText;

/**
 * 此模块类用于执行所有数据操作： 1.数据加/解密 2.数据格式转化
 * 
 * @author rolealiu
 * @updateTime 2015/06/03
 */

public class DataModule
{

	/**
	 * 获取JSON指定字段值
	 */
	public static String decodeJson(String json, String key)
	{
		JSONObject jsonObj = null;
		try
		{
			jsonObj = new JSONObject(json);
			if (jsonObj != null)
			{
				return jsonObj.getString(key);
			}
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取JSON指定字段值
	 */
	public static String decodeJson(String json, int key)
	{

		JSONArray jsonArr = null;
		try
		{
			jsonArr = new JSONArray(json);
			if (jsonArr != null)
				return jsonArr.getString(key);
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取JSON长度
	 */
	public static int getLength(String json)
	{
		JSONArray jsonArr = null;
		try
		{
			jsonArr = new JSONArray(json);
			return jsonArr.length();
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 将Map转换为JSON
	 */
	public static String encodeJson(HashMap<?, ?> map)
	{
		JSONObject jsonObj = new JSONObject(map);
		return jsonObj.toString();
	}

	/**
	 * 将Collection转换为JSON
	 */
	public static String encodeJson(int[] ints)
	{
		JSONArray jsonArr = new JSONArray();
		int length = ints.length;
		for (int i = 0; i <= length - 1; i++)
		{
			jsonArr.put(ints[i]);
		}
		return jsonArr.toString();
	}

	/**
	 * 将String[]转换为JSON
	 */
	public static String encodeJson(String[] strs)
	{
		JSONArray jsonArr = new JSONArray();
		int length = strs.length;
		for (int i = 0; i <= length - 1; i++)
		{
			jsonArr.put(strs[i]);
		}
		return jsonArr.toString();
	}

	/**
	 * 将Array转换为JSON
	 */
	public static String encodeJson(Collection<?> arr)
	{
		JSONArray jsonArr = new JSONArray(arr);
		return jsonArr.toString();
	}

	/**
	 * MD5加密
	 */
	public static String MD5(String str)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return md.digest().toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			return null;
		}
	}

	/**
	 * SHA1加密
	 */
	public static String SHA1(String str)
	{
		try
		{
			MessageDigest sha = MessageDigest.getInstance("SHA");
			sha.update(str.getBytes());
			return sha.digest().toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			return null;
		}
	}

	/**
	 * 数据过滤,如果为空则提示
	 */
	public static Boolean stringFilterAndToast(Context context,
			EditText editText)
	{
		if (editText.getText().toString().isEmpty())
		{
			ToastModule.showShort(context, editText.getHint()
					+ "不能为空，请检查并重新输入！");
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * 静默数据过滤，无提示
	 */
	public static Boolean stringFilter(EditText editText)
	{
		if (editText.getText().toString().isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * 批量数据过滤，如果为空则提示
	 */
	public static Boolean stringArrayFilterAndToast(Context context,
			EditText[] editTexts)
	{
		for (int i = 0; i < editTexts.length; i++)
		{
			if (editTexts[i].getText().toString().isEmpty())
			{
				ToastModule.showShort(context, editTexts[i].getHint()
						+ "不能为空，请检查并重新输入！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 静默批量数据过滤，无提示
	 */
	public static Boolean stringArrayFilter(Context context,
			EditText[] editTexts)
	{
		for (int i = 0; i < editTexts.length; i++)
		{
			if (editTexts[i].getText().toString().isEmpty())
			{
				return false;
			}
		}
		return true;
	}
}
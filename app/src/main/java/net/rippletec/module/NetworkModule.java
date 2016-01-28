package net.rippletec.module;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络操作模块
 * 
 * @author rolealiu
 * @updateTime 2015/05/11
 */

public class NetworkModule
{

	private static AsyncHttpClient	client	= new AsyncHttpClient();
	private static String			result	= null;

	/**
	 * 检测网络状态 ，如有则返回网络类型，否则返回null
	 * 
	 * @param context
	 */
	public static String getConnectState(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null)
		{
			// 若已联网
			return info.getTypeName().toString();
		}
		else
		{
			return null;
		}
	}

	/**
	 * 检测网络是否连接
	 * 
	 * @param context
	 */
	public static boolean isConnected(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 异步get请求
	 * 
	 * @param getUrl
	 */
	public static String get(String getUrl)
	{
		AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
		{

			@Override
			// 请求成功
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
			{
				try
				{
					result = new String(arg2, "UTF-8");
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
			}

			@Override
			// 请求失败
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3)
			{
				result = null;
			}
		};
		client.get(getUrl, responseHandler);
		return result;
	}

	/**
	 * 异步get请求
	 * 
	 * @param getUrl
	 * @param handler
	 */
	public static void get(String getUrl, AsyncHttpResponseHandler handler)
	{
		client.get(getUrl, handler);
	}

	/**
	 * 异步post请求
	 * 
	 * @param postUrl
	 */
	public static String post(String postUrl)
	{
		AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
		{

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
			{
				try
				{
					result = new String(arg2, "UTF-8");
				}
				catch (UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3)
			{
				result = null;
			}
		};
		client.post(postUrl, responseHandler);
		return result;
	}

	/**
	 * 异步post请求
	 * 
	 * @param postUrl
	 * @param rp
	 */
	public static String post(String postUrl, RequestParams rp)
	{
		AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
		{

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
			{
				try
				{
					result = new String(arg2, "UTF-8");
				}
				catch (UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3)
			{
				result = null;
			}
		};
		client.post(postUrl, rp, responseHandler);
		return result;
	}

	/**
	 * 异步post请求
	 * 
	 * @param postUrl
	 * @param handler
	 */
	public static String post(String postUrl, AsyncHttpResponseHandler handler)
	{
		client.post(postUrl, handler);
		return result;
	}

	/**
	 * 异步post请求
	 * 
	 * @param postUrl
	 * @param rp
	 * @param handler
	 */
	public static String post(String postUrl, RequestParams rp,
			AsyncHttpResponseHandler handler)
	{
		client.post(postUrl, rp, handler);
		return result;
	}
}

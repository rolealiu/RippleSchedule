package net.rippletec.ui.base;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 此核心类为所有Activity的基础类， 继承有关Activity的核心基础操作.
 * 
 * @author rolealiu
 * @updateDate 2015/09/28
 */
public abstract class ActivityBase extends FragmentActivity
{

	/**
	 * 启动方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	/**
	 * 去除标题栏
	 */
	protected void removeTitleBar()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 隐藏状态栏
	 */
	protected void removeStatusBar()
	{
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	/**
	 * 隐藏导航栏
	 */
	protected void removeNavigationBar()
	{
		// 设置全屏模式
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}

	/**
	 * 设置状态栏
	 */
	protected void setStatusBar()
	{
	}

	/**
	 * 初始化视图
	 */
	protected abstract void initialView();

	/**
	 * 初始化监听器
	 */
	protected abstract void initialListener();

	/**
	 * 获取Fragment适配器
	 * 
	 * @param list_fg
	 * @return
	 */
	protected FragmentPagerAdapter getFragmentAdapter(
			final ArrayList<Fragment> list_fg)
	{
		FragmentPagerAdapter adt_fg;
		adt_fg = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return list_fg.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				// arg0为页数
				return list_fg.get(arg0);
			}
		};
		return adt_fg;
	}

	/**
	 * 显式Intent界面跳转
	 * 
	 * @param classObj
	 */
	protected void jumpTo(Class<?> classObj)
	{
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		doFinish();
	}

	/**
	 * 显式Intent界面跳转（带参数）
	 * 
	 * @param classObj
	 * @param params
	 */
	protected void jumpTo(Class<?> classObj, Bundle params)
	{
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
		doFinish();
	}

	/**
	 * 获取Intent跳转参数
	 * 
	 * @return
	 */
	protected Bundle getExtras()
	{
		return this.getIntent().getExtras();
	}

	/**
	 * 销毁Activity
	 */
	protected void doFinish()
	{
		super.finish();
	}

}

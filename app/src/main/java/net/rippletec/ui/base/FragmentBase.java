package net.rippletec.ui.base;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

/**
 * 此核心类为所有Fragment的基础类， 继承有关Fragment的核心基础操作.
 * 
 * @author rolealiu
 */
public abstract class FragmentBase extends Fragment
{

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
		adt_fg = new FragmentPagerAdapter(getChildFragmentManager())
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
	 * 获取Fragment ArrayList,即将Fragment数组转换为ArrayList,用于填充适配器
	 * 
	 * @param fg_list
	 * @return
	 */
	protected ArrayList<Fragment> getFragmentArrayList(FragmentBase[] fg_list)
	{
		ArrayList<Fragment> list = new ArrayList<Fragment>();
		for (int i = 0; i <= fg_list.length - 1; i++)
		{
			list.add(fg_list[i]);
		}

		return list;
	}

	/**
	 * 初始化视图
	 */
	protected abstract void initialView(View view);

	/**
	 * 初始化监听器
	 */
	protected abstract void initialListener(View view);
	
}

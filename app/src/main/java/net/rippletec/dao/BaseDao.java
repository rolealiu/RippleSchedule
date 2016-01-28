package net.rippletec.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作类
 * @author rolealiu
 *
 */
public class BaseDao extends SQLiteOpenHelper
{

	/**
	 * 定义数据库路径
	 */
	protected static final String DB_PATH = "rippletec_guimitu";
	
	/**
	 * 定义数据库版本
	 */
	protected static final int DB_VERSION = 1;
	
	/**
	 * 构造方法
	 */
	public BaseDao(Context context)
	{
		super(context, DB_PATH, null, DB_VERSION);
	}

	/**
	 * 创建方法
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
	}

	/**
	 * 更新方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		
	}

}

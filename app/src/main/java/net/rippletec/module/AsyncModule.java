package net.rippletec.module;

import android.os.AsyncTask;

/**
 * 异步操作模块
 * 
 * @author rolealiu
 * @updateTime 2015/05/28
 */

public abstract class AsyncModule extends AsyncTask<Object, Object, Object>
{
	// 异步操作前的操作
	@Override
	protected abstract void onPreExecute();

	@Override
	// 异步执行操作
	protected abstract Object doInBackground(Object... params);

	// 更新进度时的操作
	@Override
	protected abstract void onProgressUpdate(Object... values);

	// 异步完成后的操作
	@Override
	protected abstract void onPostExecute(Object result);

}

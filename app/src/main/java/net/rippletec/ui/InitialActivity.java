package net.rippletec.ui;

import net.rippletec.rippleSchedule.R;
import net.rippletec.ui.base.ActivityBase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

public class InitialActivity extends ActivityBase
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		removeTitleBar();
		removeStatusBar();

		// 设置全屏模式
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

		// 设置跳转动画
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		setContentView(R.layout.act_initial);

		initialView();

		// 倒计时跳转
		timeCountDown();
	}

	@Override
	protected void initialView()
	{
	}

	@Override
	protected void initialListener()
	{

	}

	/**
	 * 倒计时2s进入程序
	 */
	private void timeCountDown()
	{
		CountDownTimer timer = new CountDownTimer(1000, 1000)
		{

			@Override
			public void onTick(long millisUntilFinished)
			{
			}

			@Override
			public void onFinish()
			{
				jumpTo(MainActivity.class);
			}
		};
		timer.start();
	}
}
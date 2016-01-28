package net.rippletec.layout;

import java.util.Timer;
import java.util.TimerTask;

import net.rippletec.module.ScreenModule;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 波浪控件，注意有各种参数可供设置，如不设置则完全按照默认值进行显示
 * 
 * @author rolealiu 刘昊臻
 * @updateTime 2015/09/26
 *
 */

public class WaveView extends View
{
	// view上下文
	Context					context;

	// 屏幕宽高
	private int				screenWidth				= -1;
	private int				screenHeight			= -1;

	// 波浪透明度
	private int				alpha					= 100;

	// 波浪颜色
	private int				color					= Color.RED;

	// 波浪宽度
	private float			waveWidth				= 90f;

	// 波浪峰值
	private int				waveCrest				= 20;

	// 波浪高度
	private float			waveHeight				= 30f;

	// 波浪最高高度
	private int				maxWaveHeight			= 30;

	// 波浪最低高度
	private int				minWaveHeight			= 20;

	// 波浪移动速度(每秒移动dp)
	private float			waveSpeed				= 1.0f;

	// 水位上升速度(每秒上升dp)
	private float			floatSpeed				= 1.0f;

	// 波浪移动偏移量
	private float			waveOffset				= 0;

	// 波浪移动方向
	private int				wavingWay				= WaveView.MOVE_FROM_RIGHT_TO_LEFT;

	// 波浪浮动方向
	private int				floatingWay				= WaveView.FLOAT_UP;

	// 移动方向常量
	public static final int	MOVE_FROM_LEFT_TO_RIGHT	= 0;
	public static final int	MOVE_FROM_RIGHT_TO_LEFT	= 1;

	// 浮动方向常量
	public static final int	FLOAT_UP				= 0;
	public static final int	FLOAT_DOWN				= 1;

	// 画笔
	private static Paint	pen						= new Paint();

	// 路径
	private static Path		path					= new Path();

	// 定时器任务判断
	private boolean			isWaving				= false;
	private boolean			isFloating				= false;

	// 设定计时器以及发信任务
	private Timer			timer					= new Timer();
	private TimerTask		wavingTask;
	private TimerTask		floatingTask;

	private Handler			waveHandler				= new Handler()
													{
														public void handleMessage(android.os.Message msg)
														{
															if (msg.what == 1001)
															{
																// 判断波浪移动方向
																if (wavingWay == WaveView.MOVE_FROM_RIGHT_TO_LEFT)
																{
																	waveOffset += waveSpeed;
																}
																else if (wavingWay == WaveView.MOVE_FROM_LEFT_TO_RIGHT)
																{
																	waveOffset -= waveSpeed;
																}
																else
																{
																	// 输出参数错误信息
																	Log.e("WaveView Notice", "wrong startWaving() param");
																	return;
																}

																// 如果偏移量的绝对值大于等于一个周期的宽度，那么偏移量清零
																if (Math.abs(waveOffset) >= waveWidth)
																{
																	waveOffset = 0;
																}

																// 判断水位浮动方向
																if (floatingWay == WaveView.FLOAT_UP)
																{
																	if (floatSpeed < 0)
																		floatSpeed = -floatSpeed;
																}
																else if (floatingWay == WaveView.FLOAT_DOWN)
																{
																	if (floatSpeed > 0)
																		floatSpeed = -floatSpeed;
																}
																else
																{
																	Log.e("WaveView Notice", "wrong startFloating() param");
																	return;
																}
															}
															if (msg.what == 1002)
															{
																waveHeight -= floatSpeed;
																if (waveHeight <= maxWaveHeight && isFloating)
																{
																	stopFloating();
																	waveHeight = maxWaveHeight;
																}
															}

															// 重绘画布
															postInvalidate();
														};
													};

	public WaveView(Context context)
	{
		super(context);
		this.context = context.getApplicationContext();
		if (screenHeight == -1 && screenWidth == -1)
		{
			screenHeight = ScreenModule.getScreenHeightPX(context.getApplicationContext());
			screenWidth = ScreenModule.getScreenWidthPX(context.getApplicationContext());
		}
	}

	public WaveView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context.getApplicationContext();
		if (screenHeight == -1 && screenWidth == -1)
		{
			screenHeight = ScreenModule.getScreenHeightPX(context.getApplicationContext());
			screenWidth = ScreenModule.getScreenWidthPX(context.getApplicationContext());
		}
	}

	/**
	 * 设置波浪最高高度,相对于屏幕高度来说(0~1f)
	 * 
	 * @param maxHeight
	 */
	public void setMaxWaveHeight(float maxHeight)
	{
		this.maxWaveHeight = (int) ((1.0 - maxHeight) * screenHeight);
	}

	/**
	 * 设置波浪最低高度,相对于屏幕高度来说(0~1f)
	 * 
	 * @param maxHeight
	 */
	public void setMinWaveHeight(float minHeight)
	{
		this.minWaveHeight = (int) ((1.0 - minHeight) * screenHeight);
	}

	/**
	 * 设置波浪高度,相对于屏幕高度来说(0~1f)
	 * 
	 * @param height
	 */
	public void setWaveHeight(float height)
	{
		waveHeight = (float) ((1.0 - height) * screenHeight);
	}

	/**
	 * 设置透明度
	 * 
	 * @param alpha
	 */
	public void setAlpha(float alpha)
	{
		this.alpha = (int) (255 * alpha);
	}

	/**
	 * 设置颜色(Resource)
	 * 
	 * @param color
	 */
	public void setColor(int color)
	{
		this.color = color;
	}

	/**
	 * 设置波长(波浪宽度百分比，相对于屏幕宽度而言)
	 * 
	 * @param width
	 */
	public void setWaveWidth(float width)
	{
		this.waveWidth = (int) (width * screenWidth);
	}

	/**
	 * 设置波峰波谷(波峰波谷百分比，相对于波长而言)
	 * 
	 * @param height
	 */
	public void setWaveCrest(float height)
	{
		this.waveCrest = (int) (height * waveWidth);
	}

	/**
	 * 设置波浪移动方向(使用内置常量)
	 * 
	 * @param way
	 */
	public void setWavingWay(int way)
	{
		wavingWay = way;
	}

	/**
	 * 设置波浪移动方向(使用内置常量)
	 * 
	 * @param way
	 */
	public void setFloatingWay(int way)
	{
		floatingWay = way;
	}

	/**
	 * 设置波浪移动速度(每秒移动dp)
	 * 
	 * @param speed
	 */
	public void setWaveSpeed(float speed)
	{
		waveSpeed = (float) ScreenModule.dp2px(context, speed) / 120;
	}

	/**
	 * 设置水位上升速度(每秒移动dp)
	 * 
	 * @param speed
	 */
	public void setFloatSpeed(float speed)
	{
		floatSpeed = (float) ScreenModule.dp2px(context, speed) / 120;
	}

	/**
	 * 开始波动
	 */
	public void startWaving()
	{
		if (isWaving)
		{
			return;
		}
		else
		{
			wavingTask = new TimerTask()
			{
				@Override
				public void run()
				{
					waveHandler.sendEmptyMessage(1001);
				}
			};
			timer.schedule(wavingTask, 0, 5);
			isWaving = true;
		}
	}

	/**
	 * 停止波动
	 */
	public void stopWaving()
	{
		if (isWaving)
		{
			wavingTask.cancel();
			timer.purge();
			isWaving = false;
		}
	}

	/**
	 * 切换波动状态
	 */
	public void toggleWaving()
	{
		if (isWaving)
			stopWaving();
		else
			startWaving();
	}

	/**
	 * 水位开始浮动
	 */
	public void startFloating()
	{
		if (isFloating)
		{
			return;
		}
		else
		{
			floatingTask = new TimerTask()
			{
				@Override
				public void run()
				{
					waveHandler.sendEmptyMessage(1002);
				}
			};
			timer.schedule(floatingTask, 0, 5);
			isFloating = true;
		}
	}

	/**
	 * 水位停止浮动
	 */
	public void stopFloating()
	{
		if (isFloating)
		{
			floatingTask.cancel();
			timer.purge();
			isFloating = false;
		}
	}

	/**
	 * 切换水位浮动状态
	 */
	public void toggleFloating()
	{
		if (isFloating)
			stopFloating();
		else
			startFloating();
	}

	/**
	 * 切换水位浮动方向
	 */
	public void toggleFloatingWay()
	{
		if (floatingWay == WaveView.FLOAT_UP)
			floatingWay = WaveView.FLOAT_DOWN;
		else
			floatingWay = WaveView.FLOAT_UP;
	}

	protected void onDraw(Canvas canvas)
	{
		setPen();
		drawWave(canvas, pen);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
	}

	/**
	 * 设置画笔
	 */
	private void setPen()
	{
		// 注意，此处有巨坑，颜色必须设置在透明度之前，因为颜色本身包含了透明度，会覆盖掉透明度的设置
		// 笔触颜色
		pen.setColor(color);
		// 透明度
		pen.setAlpha(alpha);
		// 抗锯齿
		pen.setAntiAlias(true);
		// 填充类型
		pen.setStyle(Style.FILL);
	}

	/**
	 * 绘制波浪
	 * 
	 * @param canvas
	 * @return
	 */
	private Canvas drawWave(Canvas canvas, Paint pen)
	{
		// 重置路径
		path.reset();

		// 根据波长计算生成多少周期
		int loop = (int) ((float) (screenWidth / waveWidth) <= 1.0f ? 1 : (float) (screenWidth / waveWidth) + 1);

		float tmpHeight;

		// 判断水面位置
		if (waveHeight >= maxWaveHeight && waveHeight <= minWaveHeight)
		{
			tmpHeight = waveHeight;
		}
		else if (waveHeight > minWaveHeight)
		{
			tmpHeight = minWaveHeight;
		}
		else
		{
			tmpHeight = maxWaveHeight;
		}

		// 判断波浪移动方向
		if (wavingWay == WaveView.MOVE_FROM_LEFT_TO_RIGHT)
		{
			drawLeftToRightWave(loop, tmpHeight);
		}
		else
		{
			drawRightToLeftWave(loop, tmpHeight);
		}

		// 绘制图形
		canvas.drawPath(path, pen);
		return canvas;
	}

	/**
	 * 绘制从左到右移动的波浪
	 * 
	 * @param loop
	 * @param height
	 * @return
	 */
	private Path drawLeftToRightWave(int loop, float height)
	{
		for (int i = 0; i <= loop; i++)
		{
			// 波浪从右侧开始绘制
			path.moveTo(screenWidth - i * waveWidth - waveOffset, height);
			// 波浪后半周期
			path.quadTo(screenWidth - (i * waveWidth + waveWidth / 4) - waveOffset, height - waveCrest / 2, screenWidth - (i * waveWidth + waveWidth / 2) - waveOffset, height);
			// 波浪前半周期
			path.quadTo(screenWidth - (i * waveWidth + 3 * waveWidth / 4) - waveOffset, height + waveCrest / 2, screenWidth - (i * waveWidth + waveWidth) - waveOffset, height);
		}

		// 下方水底
		path.lineTo(screenWidth - loop * waveWidth, screenHeight);
		path.lineTo(screenWidth - waveOffset, screenHeight);
		path.lineTo(screenWidth - waveOffset, height);

		// 闭合图形
		path.close();
		return path;
	}

	/**
	 * 绘制从右到左移动的波浪
	 * 
	 * @param loop
	 * @param height
	 * @return
	 */
	private Path drawRightToLeftWave(int loop, float height)
	{
		for (int i = 0; i <= loop; i++)
		{
			// 波浪从左侧开始绘制
			path.moveTo(i * waveWidth - waveOffset, height);
			// 波浪前半周期
			path.quadTo(i * waveWidth + waveWidth / 4 - waveOffset, height - waveCrest / 2, i * waveWidth + waveWidth / 2 - waveOffset, height);
			// 波浪后半周期
			path.quadTo(i * waveWidth + 3 * waveWidth / 4 - waveOffset, height + waveCrest / 2, i * waveWidth + waveWidth - waveOffset, height);
		}

		// 下方水底
		path.lineTo(loop * waveWidth, screenHeight);
		path.lineTo(0 - waveOffset, screenHeight);
		path.lineTo(0 - waveOffset, height);

		// 闭合图形
		path.close();
		return path;
	}

	public void setWidth(int width)
	{
		this.screenWidth = width;
	}

	public void setHeight(int height)
	{
		this.screenHeight = height;
	}
}

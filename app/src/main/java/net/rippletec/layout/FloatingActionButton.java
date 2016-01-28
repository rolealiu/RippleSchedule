package net.rippletec.layout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 浮动按钮
 * 
 * @author rolealiu 刘昊臻
 *
 */
public class FloatingActionButton extends ImageView
{
	public static int		TOP_LEFT		= 0;
	public static int		TOP_RIGHT		= 1;
	public static int		BOTTOM_LEFT		= 2;
	public static int		BOTTOM_RIGHT	= 3;
	public static int		CENTER			= 4;
	private int				position		= BOTTOM_RIGHT;
	private ObjectAnimator	showAnim;
	private ObjectAnimator	hideAnim;

	public FloatingActionButton(Context context)
	{
		super(context);
	}

	public FloatingActionButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
	}

}

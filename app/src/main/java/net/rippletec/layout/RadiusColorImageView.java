package net.rippletec.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 自定义的圆角矩形ImageView
 * 
 * @author rolealiu
 * @updateTime 2015/05/01
 */
public class RadiusColorImageView extends ImageView
{

	private Paint	paint		= new Paint();
	private int		imageWidth;
	private int		imageHeight;
	private int		colorNow	= 0xffb8dd9d;

	public RadiusColorImageView(Context context)
	{
		this(context, null);
	}

	public RadiusColorImageView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public RadiusColorImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);
		imageWidth -= 2*left;
	}

	/**
	 * 绘制圆角矩形图片
	 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		paint.setColor(colorNow);
		RectF rectf = new RectF(0, 0, imageWidth, imageWidth);
		canvas.drawRoundRect(rectf, 7, 7, paint);
	}

	public void setColor(int color)
	{
		this.colorNow = color;
		postInvalidate();
	}

	public void setWidth(int width)
	{
		imageWidth = width;
		postInvalidate();
	}

	public void setHeight(int height)
	{
		imageHeight = height;
		postInvalidate();
	}
}
package net.rippletec.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import net.rippletec.module.ScreenModule;

import java.util.ArrayList;

/**
 * 垂直滚动选择视图
 *
 * @author rolealiu 刘昊臻
 * @updateDate 2015/10/27
 */
public class NumberWheelView extends ScrollView {
    private static final int SCROLL_UP = 0;
    private static final int SCROLL_DOWN = 1;
    private static int oldY = -1;
    private static int currentY = -1;
    private LinearLayout ly_content;
    private Context context;
    private Scroller scroller;
    private int showItemNum = 5;
    private int itemHeight = -1;
    private int viewWidth = -1;
    private int tv_padding;
    private int currentIndex = 0;
    private float originalY = 0;
    private float offset = 0;
    private int scroll_way;
    private int spareOffset;
    private boolean isTouching;
    private Paint bg_paint;
    private String divideLineColor = "#83cde6";
    private String textFocusedColor = "#83cde6";
    private String textUnfocusedColor = "#999999";
    private Handler scrollHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1005) {
                currentY = getScrollY();
                if (currentY == oldY) {

                    if (currentY > currentIndex * itemHeight && currentY <= currentIndex * itemHeight + itemHeight / 2) {
                        spareOffset = currentY - currentIndex * itemHeight;
                        autoFinishScroll(currentY, -spareOffset);
                    } else {
                        spareOffset = currentIndex * itemHeight - currentY;
                        autoFinishScroll(currentY, spareOffset);
                    }
                }
                oldY = getScrollY();
            }
        }
    };

    // 用于存放所有的列表项
    private ArrayList<String> items = new ArrayList<String>();

    public NumberWheelView(Context context) {
        super(context);
        initialView(context);
    }

    public NumberWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialView(context);
    }

    /**
     * 获取所有列表项
     *
     * @return
     */
    public ArrayList<String> getItems() {
        return items;
    }

    /**
     * 获取列表项数量
     *
     * @return
     */
    public int getCount() {
        return items.size();
    }

    /**
     * 获取滑动方向
     *
     * @return
     */
    public int getScrollWay() {
        return scroll_way;
    }

    /**
     * 获取具体列表项
     *
     * @param position
     * @return
     */
    public String getItemValue(int position) {
        return items.get(position);
    }

    /**
     * 获取当前选中的列表项的序列（0开始）
     *
     * @return
     */
    public int getCurrentItemIndex() {
        return currentIndex;
    }

    /**
     * 设置选中分割线的颜色
     *
     * @param color
     */
    public void setDivideLineColor(String color) {
        bg_paint = null;
        this.divideLineColor = color;
        invalidate();
    }

    /**
     * 设置列表项
     *
     * @param items
     */
    public void setItems(ArrayList<String> items) {
        // 如果列表项不为空，则清空列表
        if (this.items != null)
            this.items.clear();

        // 往列表项中添加
        this.items.addAll(items);

        int offset = showItemNum / 2;
        for (int i = 1; i <= offset; i++) {
            this.items.add(0, "");
            this.items.add("");
        }

        // 初始化列表项
        initialItem();
    }

    /**
     * 设置最多显示多少条列表项
     *
     * @param num
     */
    public void setShowItemNum(int num) {
        this.showItemNum = num;
        ly_content.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 初始化滚动视图
     *
     * @param context
     */
    private void initialView(Context context) {
        this.context = context;
        scroller = new Scroller(context);
        tv_padding = ScreenModule.dp2px(context, 8);
        setVerticalScrollBarEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setFadingEdgeLength(0);
        ly_content = new LinearLayout(context);
        ly_content.setOrientation(LinearLayout.VERTICAL);
        this.addView(ly_content);

    }

    /**
     * 初始化列表项
     */
    private void initialItem() {
        for (int i = 0; i < items.size(); i++) {
            ly_content.addView(createItemView(i));
        }

    }

    /**
     * 创建列表项子项布局
     *
     * @param position
     * @return
     */
    private TextView createItemView(int position) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setSingleLine(true);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tv.setText(items.get(position));
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor(textUnfocusedColor));
        if (position == currentIndex + showItemNum / 2)
            tv.setTextColor(Color.parseColor(textFocusedColor));
        tv.setPadding(0, tv_padding, 0, tv_padding);
        tv.setBackgroundColor(Color.parseColor("#00000000"));
        if (itemHeight < 0) {
            tv.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            itemHeight = tv.getMeasuredHeight();
            ly_content.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * showItemNum));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
            this.setLayoutParams(new LinearLayout.LayoutParams(lp.width, itemHeight * showItemNum));
        }
        return tv;
    }

    @Override
    protected void onScrollChanged(int currentHorizontalScroll, int currentVerticalScroll, int oldHorizontalScroll, int oldVerticalScroll) {
        //对齐选中项
        if (!isTouching)
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollHandler.sendEmptyMessage(1005);
                }
            }, 50);

        // 根据滑动修改当前选中的列表项
        if (currentVerticalScroll > currentIndex * itemHeight + itemHeight / 2) {
            currentIndex++;
            setItemForcused(currentIndex);
        } else if (currentVerticalScroll <= currentIndex * itemHeight - itemHeight / 2) {
            currentIndex--;
            setItemForcused(currentIndex);
        }

        // 判断滑动方向
        if (currentVerticalScroll < oldVerticalScroll) {
            scroll_way = SCROLL_UP;
        } else {
            scroll_way = SCROLL_DOWN;
        }
        super.onScrollChanged(currentHorizontalScroll, currentVerticalScroll, oldHorizontalScroll, oldVerticalScroll);
    }

    public void scrollToItem(int destination) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isTouching = true;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                isTouching = true;
                break;
            }
            case MotionEvent.ACTION_UP: {
                isTouching = false;
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollHandler.sendEmptyMessage(1005);
                        scrollHandler.sendEmptyMessage(1005);
                    }
                }, 50);
                break;
            }
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 画出分割线
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public void setBackground(Drawable background) {
        if (bg_paint == null) {
            bg_paint = new Paint();
            bg_paint.setColor(Color.parseColor(divideLineColor));
            bg_paint.setStrokeWidth(ScreenModule.dp2px(context, 1));
        }

        background = new Drawable() {

            @Override
            public void setColorFilter(ColorFilter cf) {
            }

            @Override
            public void setAlpha(int alpha) {
            }

            @Override
            public int getOpacity() {
                return 0;
            }

            @Override
            public void draw(Canvas canvas) {
                viewWidth = getMeasuredWidth();
                canvas.drawLine(0, showItemNum / 2 * itemHeight, viewWidth, showItemNum / 2 * itemHeight, bg_paint);
                canvas.drawLine(0, (showItemNum / 2 + 1) * itemHeight, viewWidth, (showItemNum / 2 + 1) * itemHeight, bg_paint);
            }
        };
        super.setBackground(background);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setBackground(null);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 2);
    }

    private void setItemForcused(int position) {
        for (int i = 0; i < ly_content.getChildCount(); i++) {
            if (i == position + showItemNum / 2 - 2 || i == position + showItemNum / 2 + 2) {
                ((TextView) ly_content.getChildAt(i)).setTextColor(Color.parseColor(textUnfocusedColor));
                ((TextView) ly_content.getChildAt(i)).setAlpha(0.8f);
                // ((TextView) ly_content.getChildAt(i)).setScaleY(0.8f);
                // ((TextView) ly_content.getChildAt(i)).setScaleX(0.8f);
            } else if (i == position + showItemNum / 2 - 1 || i == position + showItemNum / 2 + 1) {
                ((TextView) ly_content.getChildAt(i)).setTextColor(Color.parseColor(textUnfocusedColor));
                ((TextView) ly_content.getChildAt(i)).setAlpha(0.9f);
                // ((TextView) ly_content.getChildAt(i)).setScaleY(0.9f);
                // ((TextView) ly_content.getChildAt(i)).setScaleX(0.9f);
            } else if (i == position + showItemNum / 2) {
                ((TextView) ly_content.getChildAt(i)).setTextColor(Color.parseColor(textFocusedColor));
                ((TextView) ly_content.getChildAt(i)).setAlpha(1f);
                // ((TextView) ly_content.getChildAt(i)).setScaleY(1f);
                // ((TextView) ly_content.getChildAt(i)).setScaleX(1f);
            } else {
                ((TextView) ly_content.getChildAt(i)).setTextColor(Color.parseColor(textUnfocusedColor));
                ((TextView) ly_content.getChildAt(i)).setAlpha(0.7f);
                // ((TextView) ly_content.getChildAt(i)).setScaleY(0.7f);
                // ((TextView) ly_content.getChildAt(i)).setScaleX(0.7f);
            }
        }
    }

    private void autoFinishScroll(int currentY, int destination) {
        scroller.startScroll(0, currentY, 0, destination, 200);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (!isTouching && scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * 设置当前选中项
     *
     * @param position
     */
    public void setCurrentItemIndex(int position) {
        scroller.startScroll(getScrollX(),getScrollY(),getScrollX(),itemHeight*position-getScrollY(),0);
        postInvalidate();
        currentIndex = position;
        setItemForcused(position);
    }
}
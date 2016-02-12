package net.rippletec.layout;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 可以获取滚动方向的线性RecyclerView,包含OnItemClickListener
 *
 * @author 钟毅凯
 * @updateDate 2016/01/28
 */
public class ListRecyclerView extends RecyclerView {
    public static final int SCROLL_UP = 0;
    public static final int SCROLL_DOWN = 1;
    private LinearLayoutManager mLinearLayoutManager;
    private OnItemClickListener mOnItemClickListener;
    public int scrollWay = 1;
    private float originalY = 0;
    private float offsetY = 0;

    public ListRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置垂直的线性布局
        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(mLinearLayoutManager);
        //设置item插入删除的动画
        setItemAnimator(new DefaultItemAnimator());
        //设置识别手势的itemtouchlistener
        addOnItemTouchListener(new GestureOnItemTouchListener(context));
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 首先判断手势的动作
        // 按下的时候获取当前的手指的Y坐标，同时赋值给偏移量offsetY
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            originalY = ev.getY();
            offsetY = originalY;
        }
        // 当手指移动的时候
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            // 如果单次的偏移量大于15或者总的偏移量大于30
            if (ev.getY() - offsetY > 15 || offsetY - originalY > 30) {
                // 如果总的偏移量大于30，那么将此时的Y坐标赋值给originalY（原始Y坐标）
                if (offsetY - originalY > 30)
                    originalY = ev.getY();
                // 此时的滚动方向是向下滚动
                scrollWay = SCROLL_DOWN;
            } else if (ev.getY() - offsetY < -15 || offsetY - originalY < -30) {
                if (offsetY - originalY < -30)
                    originalY = ev.getY();
                scrollWay = SCROLL_UP;
            }
            offsetY = ev.getY();
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    private class GestureOnItemTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector mGestureDetector;

        public GestureOnItemTouchListener(Context context) {
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mOnItemClickListener != null && mGestureDetector.onTouchEvent(e)) {
                // 触发单击事件
                mOnItemClickListener.onItemClick(ListRecyclerView.this, childView, rv.getChildPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View item, int position);
    }
}

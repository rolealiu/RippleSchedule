package net.rippletec.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * 可以拦截touch事件的FrameLayout
 *
 * @author 钟毅凯
 * @updateDate 2016/01/28
 */
public class DispatchTouchFrameLayout extends FrameLayout {

    private OnDispatchTouchEventListener mOnDispatchTouchEventListener = null;

    public DispatchTouchFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mOnDispatchTouchEventListener != null)
            mOnDispatchTouchEventListener.onDispatchTouchhEvent(event);
        super.dispatchTouchEvent(event);
        return true;
    }

    public void setOnDispatchTouchEventListener(OnDispatchTouchEventListener onDispatchTouchEventListener) {
        this.mOnDispatchTouchEventListener = onDispatchTouchEventListener;
    }

    public interface OnDispatchTouchEventListener {
        void onDispatchTouchhEvent(MotionEvent event);
    }
}

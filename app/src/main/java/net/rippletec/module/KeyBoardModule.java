package net.rippletec.module;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 键盘操作模块
 *
 * @author rolealiu
 * @updateTime 2015/05/28
 */
public class KeyBoardModule
{
    /**
     * 打开软键盘(用于跳转界面之后打开软键盘)
     */
    public static void openKeybord(final EditText editText,
                                   final Context context)
    {
        // 添加延迟是为了防止页面元素还没加载完，导致软键盘找不到输入焦点而无法弹出
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                InputMethodManager inputMethodManager = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editText,
                        InputMethodManager.RESULT_SHOWN);
            }
            // 如果300ms依然无法弹出，请适当延长时间，但是最好不要超过1000ms，否则用户会感到“卡顿”
        }, 300);
    }

    /**
     * 立即打开软键盘
     */
    public static void openKeybordImmediately(final EditText editText,
                                              final Context context)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText,
                InputMethodManager.RESULT_SHOWN);
    }

    /**
     * 立即关闭软键盘
     * @error 功能失效，暂时未知原因
     */
    public static void closeKeybord(EditText editText, Context context)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromInputMethod(editText.getWindowToken(), 0);
    }
}

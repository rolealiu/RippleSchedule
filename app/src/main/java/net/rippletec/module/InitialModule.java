package net.rippletec.module;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 获取该版本app启动的次数
 *
 * @author 钟毅凯
 * @version 2016/03/04
 */
public class InitialModule {

    /**
     * 该版本app是否首次启动
     *
     * @param context
     * @return
     */
    public static boolean isFirstLaunch(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LaunchCount", Context.MODE_PRIVATE);
        int launchCount = sharedPreferences.getInt(InfoModule.getVersionName(context), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(InfoModule.getVersionName(context));
        editor.putInt(InfoModule.getVersionName(context), launchCount + 1);
        editor.commit();
        editor.apply();
        return launchCount == 0;
    }

    /**
     * 获取app启动的次数
     *
     * @param context
     * @return
     */
    public static int getLaunchCount(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LaunchCount", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(InfoModule.getVersionName(context), 0);
    }
}

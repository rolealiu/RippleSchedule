package net.rippletec.module;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.rippletec.dao.DBHelper;
import net.rippletec.rippleSchedule.R;

/**
 * 显示Notification工具类
 *
 * @author 钟毅凯
 * @version 2016/03/09
 */
public class NotificationModule {

    /**
     * 在特定的时间显示一个Notification,当startTime比当前时间还早时无效果
     *
     * @param context
     * @param iconId
     * @param ticker
     * @param title
     * @param text
     * @param id
     * @param startTime
     */
    public static void showNotification(Context context, int iconId, String ticker, String title, String text, long id, long startTime, PendingIntent clickAction) {
        if (startTime<System.currentTimeMillis())
            return;
        Intent alarmIntent = new Intent("net.rippletec.rippleSchedule.ALARM_RECEIVER");
        alarmIntent.putExtra("iconId", iconId);
        alarmIntent.putExtra("ticker", ticker);
        alarmIntent.putExtra("title", title);
        alarmIntent.putExtra("text", text);
        alarmIntent.putExtra("id", id);
        alarmIntent.putExtra("clickAction", clickAction);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, (int) id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, startTime, alarmPendingIntent);
    }

    /**
     * 取消一个Notification
     *
     * @param context
     * @param id
     */
    public static void cancelNotification(Context context, long id) {
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.cancel((int) id);
    }

    /**
     * 取消该程序的所有Notification
     *
     * @param context
     */
    public static void cancelAllNotification(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    /**
     * 在设定的时间接收广播的receiver，需要在Manifest里注册
     */
    public static class AlarmBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra("id", 1);
            //如果数据库中没有这个记录，则说明该任务已经被删除
            boolean isExist = DBHelper.isExist(context, id);
            if (!isExist)
                return;
            PendingIntent clickAction = intent.getParcelableExtra("clickAction");
            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(intent.getIntExtra("iconId", R.drawable.ic_launcher));
            builder.setTicker(intent.getStringExtra("ticker"));
            builder.setContentTitle(intent.getStringExtra("title"));
            builder.setContentText(intent.getStringExtra("text"));
            Notification notification = builder.getNotification();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.contentIntent = clickAction;
            notification.defaults |= Notification.DEFAULT_ALL;
            nm.notify((int) id, notification);
        }
    }
}
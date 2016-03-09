package net.rippletec.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据库操作类
 *
 * @author 钟毅凯
 * @version 2016/03/09
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ripple_schedule.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "task";

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 新建表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + "(id varchar,desc varchar,start_year int,start_month int,start_day int,start_hour int,start_minute int,limit_day int,limit_hour int,limit_minute int,signal_color int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 向数据库中添加一个任务
     *
     * @param task
     */
    public static void addSingleTask(Context context, TaskData task) {
        DBHelper helper = new DBHelper(context);
        helper.addTask(task);
        helper.close();
    }

    /**
     * 向数据库中添加一个任务
     *
     * @param task
     */
    public void addTask(TaskData task) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", String.valueOf(task.getId()));
        values.put("desc", task.getTaskDesc());
        values.put("start_year", task.getStartYear());
        values.put("start_month", task.getStartMonth());
        values.put("start_day", task.getStartDay());
        values.put("start_hour", task.getStartHour());
        values.put("start_minute", task.getStartMinute());
        values.put("limit_day", task.getLimitDay());
        values.put("limit_hour", task.getLimitHour());
        values.put("limit_minute", task.getLimitMinute());
        values.put("signal_color", task.getSignalColor());
        database.insert(TABLE_NAME, "id", values);
        database.close();
    }

    /**
     * 传入task的id删除数据库中对应的记录
     *
     * @param taskId
     */
    public static void removeTask(Context context, long taskId) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME + " where id  = " + taskId);
        database.close();
        helper.close();
    }

    /**
     * 判断该id所指任务是否存在
     *
     * @param taskId
     * @return
     */
    public static boolean isExist(Context context, long taskId) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, "id = ?", new String[]{String.valueOf(taskId)}, null, null, null);
        boolean isExist = cursor.getCount() > 0;
        cursor.close();
        database.close();
        helper.close();
        return isExist;
    }

    /**
     * 获取当前数据库中所有的任务
     *
     * @return
     */
    public static ArrayList<TaskData> queryAllTask(Context context) {
        ArrayList<TaskData> list = new ArrayList<>();
        DBHelper helper = new DBHelper(context);
        Cursor cursor = helper.getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
        for (int i = 0; i < cursor.getCount(); ++i) {
            cursor.moveToPosition(i);
            TaskData task = new TaskData();
            task.setId(Long.parseLong(cursor.getString(0)));
            task.setTaskDesc(cursor.getString(1));
            task.setStartYear(cursor.getInt(2));
            task.setStartMonth(cursor.getInt(3));
            task.setStartDay(cursor.getInt(4));
            task.setStartHour(cursor.getInt(5));
            task.setStartMinute(cursor.getInt(6));
            task.setLimitDay(cursor.getInt(7));
            task.setLimitHour(cursor.getInt(8));
            task.setLimitMinute(cursor.getInt(9));
            task.setSignalColor(cursor.getInt(10));
            list.add(task);
        }
        cursor.close();
        helper.close();
        Collections.reverse(list);
        return list;
    }

    /**
     * 更新数据库的内容
     *
     * @param taskList
     */
    public static void updateTask(Context context, List<TaskData> taskList) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME);
        database.close();
        for (int i = taskList.size() - 1; i >= 0; --i)
            helper.addTask(taskList.get(i));
        helper.close();
    }
}

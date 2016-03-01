package net.rippletec.dao;

/**
 * 每一个任务的数据
 *
 * @author 钟毅凯
 * @updateDate 2016/01/25
 */
public class TaskData {

    //任务的id，用创建时的毫秒数表示
    private long id;

    //任务描述
    private String taskDesc;

    //开始时间
    private int startYear;
    private int startMonth;
    private int startDay;
    private int startHour;
    private int startMinute;

    //任务限期
    private int limitDay;
    private int limitHour;
    private int limitMinute;

    //颜色标记
    private int signalColor;

    public TaskData(){
        this.id = System.currentTimeMillis();
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getLimitHour() {
        return limitHour;
    }

    public void setLimitHour(int limitHour) {
        this.limitHour = limitHour;
    }

    public int getSignalColor() {
        return signalColor;
    }

    public void setSignalColor(int signalColor) {
        this.signalColor = signalColor;
    }

    public int getLimitMinute() {
        return limitMinute;
    }

    public void setLimitMinute(int limitMinute) {
        this.limitMinute = limitMinute;
    }

    public int getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(int limitDay) {
        this.limitDay = limitDay;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

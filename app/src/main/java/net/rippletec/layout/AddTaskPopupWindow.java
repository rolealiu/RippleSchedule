package net.rippletec.layout;

import java.util.ArrayList;

import net.rippletec.dao.TaskData;
import net.rippletec.rippleSchedule.R;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.format.Time;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加任务弹出窗口
 *
 * @author rolealiu 刘昊臻
 * @editor 钟毅凯
 * @updateDate 2016/02/11
 */
public class AddTaskPopupWindow implements OnClickListener {
    private static final int[] CLR_SIGNAL = {0xff000000, 0xFFAF4EF3, 0xFF4ED1F3, 0xFF9ACB59, 0xFFFDDD43, 0xFFEE5261};
    private static final int[] ID_IMG_SIGNAL_UNCHOSEN = {R.drawable.img_signal_none_n, R.drawable.img_signal_purple_n, R.drawable.img_signal_blue_n, R.drawable.img_signal_green_n, R.drawable.img_signal_yellow_n, R.drawable.img_signal_red_n};
    private static final int[] ID_IMG_SIGNAL_CHOSEN = {R.drawable.img_signal_none_a, R.drawable.img_signal_purple_a, R.drawable.img_signal_blue_a, R.drawable.img_signal_green_a, R.drawable.img_signal_yellow_a, R.drawable.img_signal_red_a};
    private static final int START_DATE_PAGE = 1;
    private static final int START_TIME_PAGE = 2;
    private static final int DURATION_PAGE = 3;
    private int currentPage = 0;
    private int chosenSignalClr = 0;
    private int startYear;
    private int startMonth;
    private int startDay;
    private Context context;
    private PopupWindow window;
    private View contentView;
    private int windowWidth = 0;
    private RelativeLayout lyConfirm;
    private TextView tvConfirm;
    private ImageView ivConfirm;
    private RelativeLayout lySetStartDate;
    private RelativeLayout lySetStartTime;
    private RelativeLayout lySetDuration;
    private LinearLayout lyTaskInfo;
    private LinearLayout lyStartDateDetail;
    private LinearLayout lyStartTimeDetail;
    private LinearLayout lyDurationDetail;
    private TextView tvStartTime;
    private TextView tvStartDate;
    private TextView tvDuration;
    private Time time;
    private CalendarView cvStartDate;
    private NumberWheelView wvStartTimeHour;
    private NumberWheelView wvStartTimeMinutes;
    private NumberWheelView wvDurationDays;
    private NumberWheelView wvDurationHours;
    private NumberWheelView wvDurationMinutes;
    private ImageView ivSignalNone;
    private ImageView ivSignalPurple;
    private ImageView ivSignalBlue;
    private ImageView ivSignalGreen;
    private ImageView ivSignalYellow;
    private ImageView ivSignalRed;
    private EditText etTaskDesc;
    private VelocityTracker mVelocityTracker;
    private TaskData task;

    public AddTaskPopupWindow(Context context, int resource, int width, int height) {
        this.context = context;
        contentView = LayoutInflater.from(context).inflate(resource, null);
        windowWidth = width;
        window = new PopupWindow(contentView, windowWidth, LayoutParams.WRAP_CONTENT, false);
        window.setFocusable(true);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.task_detail_popwindow);
        mVelocityTracker = VelocityTracker.obtain();
        initView();
        initListener();
    }

    private void initView() {
        lyConfirm = (RelativeLayout) contentView.findViewById(R.id.ly_add_task_confirm);
        tvConfirm = (TextView) contentView.findViewById(R.id.tv_add_task_confirm);
        ivConfirm = (ImageView) contentView.findViewById(R.id.iv_add_task_confirm);

        lySetStartDate = (RelativeLayout) contentView.findViewById(R.id.ly_add_task_startDate);
        lySetStartTime = (RelativeLayout) contentView.findViewById(R.id.ly_add_task_startTime);
        lySetDuration = (RelativeLayout) contentView.findViewById(R.id.ly_add_task_duration);
        lyTaskInfo = (LinearLayout) contentView.findViewById(R.id.ly_add_task_info);
        lyStartDateDetail = (LinearLayout) contentView.findViewById(R.id.ly_add_task_startDate_detail);
        lyStartTimeDetail = (LinearLayout) contentView.findViewById(R.id.ly_add_task_startTime_detail);
        lyDurationDetail = (LinearLayout) contentView.findViewById(R.id.ly_add_task_duration_detail);
        etTaskDesc = (EditText) contentView.findViewById(R.id.et_add_task_desc);
        cvStartDate = (CalendarView) contentView.findViewById(R.id.calendarview_start_date);

        tvStartDate = (TextView) contentView.findViewById(R.id.tv_add_task_startDate);
        tvStartTime = (TextView) contentView.findViewById(R.id.tv_add_task_startTime);
        tvDuration = (TextView) contentView.findViewById(R.id.tv_add_task_duration);

        wvStartTimeHour = (NumberWheelView) contentView.findViewById(R.id.wv_add_task_startTime_hour);
        wvStartTimeHour.setLayoutParams(new android.widget.LinearLayout.LayoutParams(windowWidth / 2, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));

        wvStartTimeMinutes = (NumberWheelView) contentView.findViewById(R.id.wv_add_task_startTime_minutes);
        wvStartTimeMinutes.setLayoutParams(new android.widget.LinearLayout.LayoutParams(windowWidth / 2, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));

        wvDurationDays = (NumberWheelView) contentView.findViewById(R.id.wv_add_task_duration_day);
        wvDurationDays.setLayoutParams(new android.widget.LinearLayout.LayoutParams(windowWidth / 3, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
        wvDurationHours = (NumberWheelView) contentView.findViewById(R.id.wv_add_task_duration_hour);
        wvDurationHours.setLayoutParams(new android.widget.LinearLayout.LayoutParams(windowWidth / 3, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
        wvDurationMinutes = (NumberWheelView) contentView.findViewById(R.id.wv_add_task_duration_minutes);
        wvDurationMinutes.setLayoutParams(new android.widget.LinearLayout.LayoutParams(windowWidth / 3, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));

        ivSignalNone = (ImageView) contentView.findViewById(R.id.iv_add_task_color_signal_none);
        ivSignalPurple = (ImageView) contentView.findViewById(R.id.iv_add_task_color_signal_purple);
        ivSignalBlue = (ImageView) contentView.findViewById(R.id.iv_add_task_color_signal_blue);
        ivSignalGreen = (ImageView) contentView.findViewById(R.id.iv_add_task_color_signal_green);
        ivSignalYellow = (ImageView) contentView.findViewById(R.id.iv_add_task_color_signal_yellow);
        ivSignalRed = (ImageView) contentView.findViewById(R.id.iv_add_task_color_signal_red);

        final String year = context.getResources().getString(R.string.txt_year);
        final String month = context.getResources().getString(R.string.txt_month);
        final String day = context.getResources().getString(R.string.txt_day);
        final String date = context.getResources().getString(R.string.txt_date);
        final String hour = context.getResources().getString(R.string.txt_hour);
        final String minute = context.getResources().getString(R.string.txt_minute);

        ArrayList<String> dayItems = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            dayItems.add(i + day);

        ArrayList<String> hourItems = new ArrayList<>();
        for (int i = 0; i < 24; i++)
            hourItems.add(i + hour);

        ArrayList<String> minutesItems = new ArrayList<>();
        for (int i = 0; i < 60; i++)
            minutesItems.add(i + minute);

        wvStartTimeHour.setShowItemNum(7);
        wvStartTimeHour.setItems(hourItems);
        wvStartTimeMinutes.setShowItemNum(7);
        wvStartTimeMinutes.setItems(minutesItems);
        wvDurationDays.setShowItemNum(7);
        wvDurationDays.setItems(dayItems);
        wvDurationHours.setShowItemNum(7);
        wvDurationHours.setItems(hourItems);
        wvDurationMinutes.setShowItemNum(7);
        wvDurationMinutes.setItems(minutesItems);

        time = new Time();
        time.setToNow();
        tvStartDate.setText(time.year + year + (time.month + 1) + month + time.monthDay + date);
        tvStartTime.setText(time.hour + ":" + (time.minute + 5 > 59 ? 59 : time.minute + 5));
        tvDuration.setText(0 + day + 3 + hour + 0 + minute);

        startYear = time.year;
        startMonth = time.month + 1;
        startDay = time.monthDay;

        wvDurationHours.post(new Runnable() {
            @Override
            public void run() {
                wvDurationHours.setCurrentItemIndex(3);
            }
        });
        wvStartTimeHour.post(new Runnable() {
            @Override
            public void run() {
                wvStartTimeHour.setCurrentItemIndex(time.hour);
            }
        });
        wvStartTimeMinutes.post(new Runnable() {
            @Override
            public void run() {
                wvStartTimeMinutes.setCurrentItemIndex(time.minute + 5 > 59 ? 59 : time.minute + 5);
            }
        });
    }

    private void initListener() {
        lyConfirm.setOnClickListener(this);
        lyConfirm.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tvConfirm.setTextColor(contentView.getResources().getColor(R.color.clr_text_confirm_a));
                    ivConfirm.setImageResource(R.drawable.img_task_confirm_a);
                    lyConfirm.setBackgroundColor(contentView.getResources().getColor(R.color.clr_ly_confirm_a));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    tvConfirm.setTextColor(contentView.getResources().getColor(R.color.clr_text_confirm_n));
                    ivConfirm.setImageResource(R.drawable.img_task_confirm_n);
                    lyConfirm.setBackgroundColor(contentView.getResources().getColor(R.color.clr_none));
                }
                return false;
            }
        });

        lySetStartDate.setOnClickListener(this);
        lySetStartTime.setOnClickListener(this);
        lySetDuration.setOnClickListener(this);

        //监听手势，向右滑返回
        ((DispatchTouchFrameLayout) contentView).setOnDispatchTouchEventListener(new DispatchTouchFrameLayout.OnDispatchTouchEventListener() {
            private float downX;

            @Override
            public void onDispatchTouchhEvent(MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        mVelocityTracker.clear();
                        mVelocityTracker.addMovement(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mVelocityTracker.addMovement(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        mVelocityTracker.computeCurrentVelocity(1000);
                        if (mVelocityTracker.getXVelocity() > 70 && event.getX() > downX && event.getX() - downX > 200 && mVelocityTracker.getYVelocity() < 50) {
                            backToHomePage();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        //监听选择开始日期
        cvStartDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                startYear = year;
                startMonth = month + 1;
                startDay = dayOfMonth;
            }
        });

        ivSignalNone.setOnClickListener(signalOnClickListener);
        ivSignalPurple.setOnClickListener(signalOnClickListener);
        ivSignalBlue.setOnClickListener(signalOnClickListener);
        ivSignalGreen.setOnClickListener(signalOnClickListener);
        ivSignalYellow.setOnClickListener(signalOnClickListener);
        ivSignalRed.setOnClickListener(signalOnClickListener);

        //监听返回键
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            private int downCount = 0;

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && downCount++ < 1) {
                    if (currentPage == START_DATE_PAGE || currentPage == START_TIME_PAGE || currentPage == DURATION_PAGE)
                        backToHomePage();
                    else
                        window.dismiss();
                    return true;
                }
                downCount = 0;
                return false;
            }
        });
        //设置点击popupwindow外面消失
        contentView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int[] l = new int[2];
                //获取view在屏幕中的绝对位置
                contentView.getLocationOnScreen(l);
                if (motionEvent.getRawX() < l[0] || motionEvent.getRawX() > contentView.getX() + contentView.getWidth() || motionEvent.getRawY() < l[1] || motionEvent.getRawY() > contentView.getY() + contentView.getHeight()) {
                    window.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    public void show(View view) {
        task = null;
        window.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        window.setOnDismissListener(onDismissListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_add_task_confirm: {
                task = new TaskData();
                task.setTaskDesc(etTaskDesc.getText().toString());
                task.setStartYear(startYear);
                task.setStartMonth(startMonth);
                task.setStartDay(startDay);
                task.setStartHour(wvStartTimeHour.getCurrentItemIndex());
                task.setStartMinute(wvStartTimeMinutes.getCurrentItemIndex());
                task.setLimitDay(wvDurationDays.getCurrentItemIndex());
                task.setLimitHour(wvDurationHours.getCurrentItemIndex());
                task.setLimitMinute(wvDurationMinutes.getCurrentItemIndex());
                task.setSignalColor(CLR_SIGNAL[chosenSignalClr]);
                window.dismiss();
                break;
            }
            case R.id.ly_add_task_startDate: {
                switchToPicker(lyStartDateDetail, lyTaskInfo);
                currentPage = START_DATE_PAGE;
                break;
            }
            case R.id.ly_add_task_startTime: {
                switchToPicker(lyStartTimeDetail, lyTaskInfo);
                currentPage = START_TIME_PAGE;
                break;
            }
            case R.id.ly_add_task_duration: {
                switchToPicker(lyDurationDetail, lyTaskInfo);
                currentPage = DURATION_PAGE;
                break;
            }
            default:
                break;
        }
    }

    /**
     * 用动画显示选择器
     *
     * @param picker 选择器的layout
     * @param before 要隐藏的layout
     */
    private void switchToPicker(final View picker, final View before) {
        AnimatorSet anims = new AnimatorSet();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(before, "alpha", 1.0f, 0.0f).setDuration(350);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(picker, "alpha", 0.0f, 1.0f).setDuration(350);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(picker, "translationX", 300f, 0.0f).setDuration(350);
        anims.play(anim1).with(anim2).with(anim3);
        anims.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                picker.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                etTaskDesc.setFocusable(false);
                etTaskDesc.setFocusableInTouchMode(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        anims.start();
    }

    /**
     * 从各个选择器返回主页
     */
    private void backToHomePage() {
        final View picker;
        switch (currentPage) {
            case START_DATE_PAGE:
                picker = lyStartDateDetail;
                currentPage = 0;
                break;
            case START_TIME_PAGE:
                picker = lyStartTimeDetail;
                currentPage = 0;
                break;
            case DURATION_PAGE:
                picker = lyDurationDetail;
                currentPage = 0;
                break;
            default:
                return;
        }
        AnimatorSet anims = new AnimatorSet();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(picker, "alpha", 1.0f, 0.0f).setDuration(350);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(lyTaskInfo, "alpha", 0f, 1.0f).setDuration(350);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(picker, "translationX", 0, 300f).setDuration(350);
        anims.play(anim1).with(anim2).with(anim3);
        anims.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                etTaskDesc.setFocusable(true);
                etTaskDesc.setFocusableInTouchMode(true);
                picker.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        anims.start();
        updateTaskData();
    }

    /**
     * 关闭选择器时更新显示的数字
     */
    private void updateTaskData() {
        final String year = context.getResources().getString(R.string.txt_year);
        final String month = context.getResources().getString(R.string.txt_month);
        final String day = context.getResources().getString(R.string.txt_day);
        final String date = context.getResources().getString(R.string.txt_date);
        final String hour = context.getResources().getString(R.string.txt_hour);
        final String minute = context.getResources().getString(R.string.txt_minute);
        tvStartDate.setText(startYear + year + startMonth + month + startDay + date);
        tvStartTime.setText(wvStartTimeHour.getCurrentItemIndex() + ":" + wvStartTimeMinutes.getCurrentItemIndex());
        tvDuration.setText(wvDurationDays.getCurrentItemIndex() + day + wvDurationHours.getCurrentItemIndex() + hour + wvDurationMinutes.getCurrentItemIndex() + minute);
    }

    private OnClickListener signalOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (chosenSignalClr) {
                case 0:
                    ivSignalNone.setImageResource(ID_IMG_SIGNAL_UNCHOSEN[0]);
                    break;
                case 1:
                    ivSignalPurple.setImageResource(ID_IMG_SIGNAL_UNCHOSEN[1]);
                    break;
                case 2:
                    ivSignalBlue.setImageResource(ID_IMG_SIGNAL_UNCHOSEN[2]);
                    break;
                case 3:
                    ivSignalGreen.setImageResource(ID_IMG_SIGNAL_UNCHOSEN[3]);
                    break;
                case 4:
                    ivSignalYellow.setImageResource(ID_IMG_SIGNAL_UNCHOSEN[4]);
                    break;
                case 5:
                    ivSignalRed.setImageResource(ID_IMG_SIGNAL_UNCHOSEN[5]);
                    break;
                default:
                    break;
            }
            switch (view.getId()) {
                case R.id.iv_add_task_color_signal_none:
                    ivSignalNone.setImageResource(ID_IMG_SIGNAL_CHOSEN[0]);
                    chosenSignalClr = 0;
                    break;
                case R.id.iv_add_task_color_signal_purple:
                    ivSignalPurple.setImageResource(ID_IMG_SIGNAL_CHOSEN[1]);
                    chosenSignalClr = 1;
                    break;
                case R.id.iv_add_task_color_signal_blue:
                    ivSignalBlue.setImageResource(ID_IMG_SIGNAL_CHOSEN[2]);
                    chosenSignalClr = 2;
                    break;
                case R.id.iv_add_task_color_signal_green:
                    ivSignalGreen.setImageResource(ID_IMG_SIGNAL_CHOSEN[3]);
                    chosenSignalClr = 3;
                    break;
                case R.id.iv_add_task_color_signal_yellow:
                    ivSignalYellow.setImageResource(ID_IMG_SIGNAL_CHOSEN[4]);
                    chosenSignalClr = 4;
                    break;
                case R.id.iv_add_task_color_signal_red:
                    ivSignalRed.setImageResource(ID_IMG_SIGNAL_CHOSEN[5]);
                    chosenSignalClr = 5;
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取要增加的task，只有在用户点击“确认添加”按钮后才会返回非空对象
     * @return
     */
    public TaskData getResult() {
        return this.task;
    }
}
package net.rippletec.layout;

import java.util.ArrayList;

import net.rippletec.rippleSchedule.R;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
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
 * @updateDate 2016/01/28
 */
public class AddTaskPopupWindow implements OnClickListener {
    private static final int START_DATE_PAGE = 1;
    private static final int START_TIME_PAGE = 2;
    private static final int DURATION_PAGE = 3;
    private int currentPage = 0;
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
    private NumberWheelView wvStartTimeHour;
    private NumberWheelView wvStartTimeMinutes;
    private NumberWheelView wvDurationDays;
    private NumberWheelView wvDurationHours;
    private NumberWheelView wvDurationMinutes;
    private VelocityTracker mVelocityTracker;

    public AddTaskPopupWindow(Context context, int resource, int width, int height) {
        this.context = context;
        mVelocityTracker = VelocityTracker.obtain();
        contentView = LayoutInflater.from(context).inflate(resource, null);
        windowWidth = width;
        window = new PopupWindow(contentView, windowWidth, LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(0x000000));
        window.setFocusable(true);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.task_detail_popwindow);
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

        final String year = context.getResources().getString(R.string.txt_year);
        final String month = context.getResources().getString(R.string.txt_month);
        final String day = context.getResources().getString(R.string.txt_day);
        final String date = context.getResources().getString(R.string.txt_date);
        final String hour = context.getResources().getString(R.string.txt_hour);
        final String minute = context.getResources().getString(R.string.txt_minute);

        ArrayList<String> dayItems = new ArrayList<String>();
        for (int i = 0; i < 3; i++)
            dayItems.add(i + day);

        ArrayList<String> hourItems = new ArrayList<String>();
        for (int i = 0; i < 24; i++)
            hourItems.add(i + hour);

        ArrayList<String> minutesItems = new ArrayList<String>();
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
        tvStartTime.setText(time.hour + ":" + (time.minute + 5));
        tvDuration.setText(0 + day + 3 + hour + 0 + minute);

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
                wvStartTimeMinutes.setCurrentItemIndex(time.minute + 5);
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
                        if (mVelocityTracker.getXVelocity() > 70 && event.getX() > downX && mVelocityTracker.getYVelocity() < 50) {
                            backToHomePage();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void show(View view) {
        window.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        window.setOnDismissListener(onDismissListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_add_task_confirm: {
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
     * 动画显示选择器
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
        View picker;
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
                lyTaskInfo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        anims.start();
    }
}
package net.rippletec.layout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import net.rippletec.dao.TaskData;
import net.rippletec.rippleSchedule.R;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 任务详情弹出窗口
 *
 * @author rolealiu 刘昊臻
 * @editor 钟毅凯
 * @updateDate 2016/02/12
 */
public class TaskDetailPopupWindow implements OnClickListener {
    private TaskData task;
    private Context context;
    private PopupWindow window;
    private View contentView;
    private int windowWidth = 0;
    private int windowHeight = 0;
    private ImageView ivColorBg;
    private ImageView ivConfirm;
    private TextView tvConfirm;
    private TextView tvTitle;
    private TextView tvPercent;
    private RelativeLayout lyTaskInfo;
    private RelativeLayout lyTaskConfirm;
    private WaveView waveBg;
    private int colorNow = 0;
    private int colorTo = 1;
    private int colorFrom;
    private boolean isSetup = true;
    private boolean isFinished = false;
    private Timer timer = new Timer();
    private TimerTask updateTimeTask = new TimerTask() {

        @Override
        public void run() {
            updateTimeHandle.sendEmptyMessage(1002);
        }
    };
    private Handler updateTimeHandle = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1002) {
                updateInfo(getPercentage(task));
            }
        }
    };

    public TaskDetailPopupWindow(Context context, int resource, int width, int height, TaskData task) {
        this.task = task;
        this.context = context;
        contentView = LayoutInflater.from(context).inflate(resource, null);
        windowWidth = width;
        windowHeight = height;
        window = new PopupWindow(contentView, windowWidth, LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(0x000000));
        window.setFocusable(true);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.task_detail_popwindow);
        initView();
        initListener();
    }

    private void initListener() {
        lyTaskConfirm.setOnClickListener(this);
        lyTaskConfirm.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tvConfirm.setTextColor(contentView.getResources().getColor(R.color.clr_text_confirm_a));
                    ivConfirm.setImageResource(R.drawable.img_task_confirm_a);
                    lyTaskConfirm.setBackgroundColor(contentView.getResources().getColor(R.color.clr_ly_confirm_a));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    tvConfirm.setTextColor(contentView.getResources().getColor(R.color.clr_text_confirm_n));
                    ivConfirm.setImageResource(R.drawable.img_task_confirm_n);
                    lyTaskConfirm.setBackgroundColor(contentView.getResources().getColor(R.color.clr_none));
                }
                return false;
            }
        });
    }

    private void initView() {
        int tmpHeight = (int) (windowWidth * 0.8);

        waveBg = (WaveView) contentView.findViewById(R.id.wv_task_detail_wave_bg);
        android.view.ViewGroup.LayoutParams waveLP = waveBg.getLayoutParams();
        waveLP.width = windowWidth;
        waveLP.height = tmpHeight;
        waveBg.setLayoutParams(waveLP);

        waveBg.setWidth(windowWidth);
        waveBg.setHeight(tmpHeight);
        waveBg.setWavingWay(WaveView.MOVE_FROM_RIGHT_TO_LEFT);
        waveBg.setWaveHeight(0.8f);
        waveBg.setWaveSpeed(45);
        waveBg.setWaveWidth(1.7f);
        waveBg.setWaveCrest(0.1f);
        waveBg.setColor(contentView.getResources().getColor(R.color.clr_white));
        waveBg.setAlpha(0.3f);
        waveBg.setFloatingWay(WaveView.FLOAT_DOWN);
        waveBg.setMaxWaveHeight(0.8f);
        waveBg.setMinWaveHeight(0.1f);
        waveBg.startWaving();

        ivColorBg = (ImageView) contentView.findViewById(R.id.iv_task_detail_colog_bg);
        ivColorBg.setBackgroundColor(contentView.getResources().getColor(R.color.clr_12_15));

        android.widget.FrameLayout.LayoutParams ivLP = (android.widget.FrameLayout.LayoutParams) ivColorBg.getLayoutParams();
        ivLP.width = windowWidth;
        ivLP.height = tmpHeight;
        ivColorBg.setLayoutParams(ivLP);

        lyTaskInfo = (RelativeLayout) contentView.findViewById(R.id.ly_task_detail_info);
        android.widget.FrameLayout.LayoutParams rLP = (android.widget.FrameLayout.LayoutParams) lyTaskInfo.getLayoutParams();
        rLP.width = windowWidth;
        rLP.height = tmpHeight;
        lyTaskInfo.setLayoutParams(rLP);

        lyTaskConfirm = (RelativeLayout) contentView.findViewById(R.id.ly_task_detail_confirm);
        tvConfirm = (TextView) contentView.findViewById(R.id.tv_task_detail_confirm);
        tvPercent = (TextView) contentView.findViewById(R.id.tv_task_detail_percent);
        tvTitle = (TextView) contentView.findViewById(R.id.tv_task_detail_title);
        ivConfirm = (ImageView) contentView.findViewById(R.id.iv_task_detail_confirm);

        tvTitle.setText(task.getTaskDesc());
        // 启动百分比更新计时器,时间间隔1.5s
        timer.schedule(updateTimeTask, 0, 1500);
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
            case R.id.ly_task_detail_confirm: {
                isFinished = true;
                window.dismiss();
                break;
            }
            default:
                break;
        }
    }

    private void updateBackgroundColor(int percent) {
        if (percent <= 10) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_10_20);
            colorTo = contentView.getResources().getColor(R.color.clr_per_0_10);
        } else if (percent <= 20) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_20_30);
            colorTo = contentView.getResources().getColor(R.color.clr_per_10_20);
        } else if (percent <= 30) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_30_40);
            colorTo = contentView.getResources().getColor(R.color.clr_per_20_30);
        } else if (percent <= 40) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_40_50);
            colorTo = contentView.getResources().getColor(R.color.clr_per_30_40);
        } else if (percent <= 50) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_50_60);
            colorTo = contentView.getResources().getColor(R.color.clr_per_40_50);
        } else if (percent <= 60) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_60_70);
            colorTo = contentView.getResources().getColor(R.color.clr_per_50_60);
        } else if (percent <= 70) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_70_80);
            colorTo = contentView.getResources().getColor(R.color.clr_per_60_70);
        } else if (percent <= 80) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_80_90);
            colorTo = contentView.getResources().getColor(R.color.clr_per_70_80);
        } else if (percent <= 90) {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_90_100);
            colorTo = contentView.getResources().getColor(R.color.clr_per_80_90);
        } else {
            colorFrom = contentView.getResources().getColor(R.color.clr_per_0_10);
            colorTo = contentView.getResources().getColor(R.color.clr_per_90_100);
        }

        if (colorNow == colorTo) {
            return;
        } else {
            colorNow = colorTo;
        }

        if (isSetup) {
            ivColorBg.setBackgroundColor(colorNow);
            isSetup = false;
        } else {
            ValueAnimator color_bg_anim = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            color_bg_anim.setDuration(300);
            color_bg_anim.addUpdateListener(new AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ivColorBg.setBackgroundColor((int) animation.getAnimatedValue());
                }
            });
            color_bg_anim.start();
        }
    }

    private void updateInfo(float percent) {
        tvPercent.setText((int) percent + "%");
        updateBackgroundColor((int) percent);
        waveBg.setWaveHeight(0.1f + 0.7f * percent / 100);
    }

    /**
     * 返回一个task的完成度
     *
     * @param task
     * @return
     * @author 钟毅凯
     */
    private float getPercentage(TaskData task) {
        try {
            DateFormat dateToMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long currentMillis = System.currentTimeMillis();
            long startMillis = dateToMillis.parse(task.getStartYear() + "-" + task.getStartMonth() + "-" + task.getStartDay() + " " + task.getStartHour() + ":" + task.getStartMinute() + ":00").getTime();
            long durationMillis = dateToMillis.parse("1970-01-" + (task.getLimitDay() + 1) + " " + task.getLimitHour() + ":" + task.getLimitMinute() + ":00").getTime() - dateToMillis.parse("1970-01-01 00:00:00").getTime();
            long remainMillis = durationMillis - currentMillis + startMillis;
            if (startMillis < currentMillis)
                return 100 * (remainMillis > 0 ? remainMillis / (float) durationMillis : 0f);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 100f;
    }

    /**
     * 返回该popupwindow所对应的task是否已经完成，只有在用户点击“任务完成”按钮后返回值为真
     *
     * @return
     * @author 钟毅凯
     */
    public boolean isTaskFinished() {
        return isFinished;
    }

    /**
     * 返回该popupwindow所对应的task
     *
     * @return
     * @author 钟毅凯
     */
    public TaskData getTask() {
        return this.task;
    }
}

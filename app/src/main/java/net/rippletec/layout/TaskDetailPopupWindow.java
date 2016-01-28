package net.rippletec.layout;

import java.util.Timer;
import java.util.TimerTask;

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

/**
 * 任务详情弹出窗口
 *
 * @author rolealiu 刘昊臻
 * @updateDate 2015/10/16
 */
public class TaskDetailPopupWindow implements OnClickListener {
    private PopupWindow window;
    private View contentView;
    private int windowWidth = 0;
    private int windowHeight = 0;
    private ImageView iv_color_bg;
    private ImageView iv_confirm;
    private TextView tv_confirm;
    private TextView tv_title;
    private TextView tv_percent;
    private RelativeLayout ly_task_info;
    private RelativeLayout ly_task_confirm;
    private WaveView wave_bg;
    private int colorNow = 0;
    private int colorTo = 1;
    private int colorFrom;
    private boolean isSetup = true;
    private float test = 100;
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
                test -= 0.1f;
                updateInfo(test);
            }
        }
    };

    public TaskDetailPopupWindow(Context context, int resource, int width, int height) {
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
        ly_task_confirm.setOnClickListener(this);
        ly_task_confirm.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tv_confirm.setTextColor(contentView.getResources().getColor(R.color.clr_text_confirm_a));
                    iv_confirm.setImageResource(R.drawable.img_task_confirm_a);
                    ly_task_confirm.setBackgroundColor(contentView.getResources().getColor(R.color.clr_ly_confirm_a));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    tv_confirm.setTextColor(contentView.getResources().getColor(R.color.clr_text_confirm_n));
                    iv_confirm.setImageResource(R.drawable.img_task_confirm_n);
                    ly_task_confirm.setBackgroundColor(contentView.getResources().getColor(R.color.clr_none));
                }
                return false;
            }
        });
    }

    private void initView() {
        int tmpHeight = (int) (windowWidth * 0.8);

        wave_bg = (WaveView) contentView.findViewById(R.id.wv_task_detail_wave_bg);
        android.view.ViewGroup.LayoutParams waveLP = wave_bg.getLayoutParams();
        waveLP.width = windowWidth;
        waveLP.height = tmpHeight;
        wave_bg.setLayoutParams(waveLP);

        wave_bg.setWidth(windowWidth);
        wave_bg.setHeight(tmpHeight);
        wave_bg.setWavingWay(WaveView.MOVE_FROM_RIGHT_TO_LEFT);
        wave_bg.setWaveHeight(0.8f);
        wave_bg.setWaveSpeed(45);
        wave_bg.setWaveWidth(1.7f);
        wave_bg.setWaveCrest(0.1f);
        wave_bg.setColor(contentView.getResources().getColor(R.color.clr_white));
        wave_bg.setAlpha(0.3f);
        wave_bg.setFloatingWay(WaveView.FLOAT_DOWN);
        wave_bg.setMaxWaveHeight(0.8f);
        wave_bg.setMinWaveHeight(0.1f);
        wave_bg.startWaving();

        iv_color_bg = (ImageView) contentView.findViewById(R.id.iv_task_detail_colog_bg);
        iv_color_bg.setBackgroundColor(contentView.getResources().getColor(R.color.clr_12_15));

        android.widget.FrameLayout.LayoutParams ivLP = (android.widget.FrameLayout.LayoutParams) iv_color_bg.getLayoutParams();
        ivLP.width = windowWidth;
        ivLP.height = tmpHeight;
        iv_color_bg.setLayoutParams(ivLP);

        ly_task_info = (RelativeLayout) contentView.findViewById(R.id.ly_task_detail_info);
        android.widget.FrameLayout.LayoutParams rLP = (android.widget.FrameLayout.LayoutParams) ly_task_info.getLayoutParams();
        rLP.width = windowWidth;
        rLP.height = tmpHeight;
        ly_task_info.setLayoutParams(rLP);

        ly_task_confirm = (RelativeLayout) contentView.findViewById(R.id.ly_task_detail_confirm);
        tv_confirm = (TextView) contentView.findViewById(R.id.tv_task_detail_confirm);
        tv_percent = (TextView) contentView.findViewById(R.id.tv_task_detail_percent);
        tv_title = (TextView) contentView.findViewById(R.id.tv_task_detail_title);
        iv_confirm = (ImageView) contentView.findViewById(R.id.iv_task_detail_confirm);

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
            iv_color_bg.setBackgroundColor(colorNow);
            isSetup = false;
        } else {
            ValueAnimator color_bg_anim = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            color_bg_anim.setDuration(300);
            color_bg_anim.addUpdateListener(new AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    iv_color_bg.setBackgroundColor((int) animation.getAnimatedValue());
                }
            });
            color_bg_anim.start();
        }
    }

    ;

    private void updateInfo(float percent) {
        tv_percent.setText((int) percent + "%");
        updateBackgroundColor((int) percent);
        wave_bg.setWaveHeight(0.1f + 0.7f * percent / 100);
    }
}

package net.rippletec.ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import net.rippletec.adapter.TaskListAdapter;
import net.rippletec.dao.TaskData;
import net.rippletec.layout.AddTaskPopupWindow;
import net.rippletec.layout.FloatingActionButton;
import net.rippletec.layout.ListRecyclerView;
import net.rippletec.layout.TaskDetailPopupWindow;
import net.rippletec.module.ImageModule;
import net.rippletec.module.ScreenModule;
import net.rippletec.rippleSchedule.R;
import net.rippletec.ui.base.ActivityBase;

/**
 * 主界面
 *
 * @author rolealiu 刘昊臻
 * @editor 钟毅凯
 * @updateDate 2016/02/12
 */
public class MainActivity extends ActivityBase implements OnClickListener, ListRecyclerView.OnItemClickListener {
    private ObjectAnimator fabHideAnim;
    private ObjectAnimator fabShowAnim;
    private AddTaskPopupWindow addTaskPopupWindow;
    private TaskDetailPopupWindow taskDetailPopupWindow;
    private TextView tvTimeTitle;
    private TextView tvRemainHour;
    private TextView tvRemainMinutes;
    private TextView tvRemainSecond;
    private ListRecyclerView rvTask;
    private TaskListAdapter adtTask;
    private FrameLayout lyHeaderBg;
    private ImageView ivTaskDetailMask;
    private ImageView ivTaskDetailMaskShadow;
    private FloatingActionButton ivFab;
    private int colorNow = 0;
    private int colorFrom;
    private int colorTo;
    private Time nowTime = new Time();
    private int hour;
    private int minute;
    private int second;
    private boolean isSetup = true;
    boolean isFABShow = true;
    boolean isFABHide = false;
    boolean isFABShowing = false;
    boolean isFABHiding = false;
    private Timer timer = new Timer();
    private TimerTask updateRemainingTimeTask = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1003);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1003) {
                // 更新剩余时间
                updateRemainingTime();
                // 根据时间更改背景颜色
                changeHeaderBackgroundColor(hour);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除状态栏、标题栏
        removeTitleBar();
        removeStatusBar();

        // 设置跳转动画
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

        setContentView(R.layout.act_main);

        initialView();
        initialListener();
    }

    @Override
    protected void initialView() {
        // 初始化顶部布局
        lyHeaderBg = (FrameLayout) findViewById(R.id.ly_main_header_bg);

        // 初始化每日倒计时控件
        tvTimeTitle = (TextView) findViewById(R.id.tv_main_header_remainingTime_title);
        tvRemainHour = (TextView) findViewById(R.id.tv_remain_hour);
        tvRemainMinutes = (TextView) findViewById(R.id.tv_remain_minutes);
        tvRemainSecond = (TextView) findViewById(R.id.tv_remain_second);

        // 初始化弹出窗口背景
        ivTaskDetailMask = (ImageView) findViewById(R.id.iv_main_popupWindow_mask);
        ivTaskDetailMaskShadow = (ImageView) findViewById(R.id.iv_main_popupWindow_mask_shadow);

        // 初始化浮动按钮
        ivFab = (FloatingActionButton) findViewById(R.id.iv_main_floating_action_button);

        // 刷新时间，时间间隔为1s
        timer.schedule(updateRemainingTimeTask, 0, 1000);

        // 初始化任务列表
        rvTask = (ListRecyclerView) findViewById(R.id.rv_main_task);
        ArrayList<TaskData> itemList = new ArrayList<TaskData>();
        for (int i = 0; i < 4; i++) {
            TaskData task = new TaskData();
            task.setTaskDesc("task " + i);
            task.setSignalColor(0xff000000);
            task.setStartYear(2016);
            task.setStartMonth(2);
            task.setStartDay(12+ i);
            task.setStartHour(14+i);
            task.setStartMinute(20+i*5);
            task.setLimitHour(i%3);
            task.setLimitHour(i*2);
            task.setLimitMinute(i*12);
            itemList.add(task);
        }
        adtTask = new TaskListAdapter(this, itemList, rvTask);
        rvTask.setAdapter(adtTask);
    }

    @Override
    protected void initialListener() {
        fabHideAnim = ObjectAnimator.ofFloat(ivFab, "alpha", 1.0f, 0.0f).setDuration(200);
        fabShowAnim = ObjectAnimator.ofFloat(ivFab, "alpha", 0.0f, 1.0f).setDuration(200);
        fabShowAnim.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ivFab.setVisibility(View.VISIBLE);
                isFABShowing = true;
                isFABShow = true;
                isFABHide = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isFABShowing = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isFABShowing = false;
                isFABShow = false;
            }
        });

        fabHideAnim.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isFABHiding = true;
                isFABHide = true;
                isFABShow = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivFab.setVisibility(View.GONE);
                isFABHiding = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isFABHiding = false;
            }
        });

        rvTask.setOnItemClickListener(this);
        //监听onscroll控制FloatingActionButton的消失出现
        rvTask.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (rvTask.scrollWay == rvTask.SCROLL_UP && !isFABHiding && !isFABHide) {
                    if (isFABShowing)
                        fabShowAnim.cancel();
                    fabHideAnim.start();
                } else if (rvTask.scrollWay == rvTask.SCROLL_DOWN && !isFABShowing && !isFABShow) {
                    if (isFABHiding)
                        fabHideAnim.cancel();
                    fabShowAnim.start();
                }
            }
        });

        ivFab.setOnClickListener(this);
    }

    /**
     * 更改顶部背景颜色
     *
     * @param hour
     */
    private void changeHeaderBackgroundColor(int hour) {
        // 通过时间判断颜色
        if (hour <= 5 || hour >= 23) {
            colorTo = getResources().getColor(R.color.clr_23_5);
            colorFrom = getResources().getColor(R.color.clr_20_22);
        } else if (hour <= 8) {
            colorTo = getResources().getColor(R.color.clr_6_8);
            colorFrom = getResources().getColor(R.color.clr_23_5);
        } else if (hour <= 11) {
            colorTo = getResources().getColor(R.color.clr_9_11);
            colorFrom = getResources().getColor(R.color.clr_6_8);
        } else if (hour <= 15) {
            colorTo = getResources().getColor(R.color.clr_12_15);
            colorFrom = getResources().getColor(R.color.clr_9_11);
        } else if (hour <= 17) {
            colorTo = getResources().getColor(R.color.clr_16_17);
            colorFrom = getResources().getColor(R.color.clr_12_15);
        } else if (hour <= 19) {
            colorTo = getResources().getColor(R.color.clr_18_19);
            colorFrom = getResources().getColor(R.color.clr_16_17);
        } else {
            colorTo = getResources().getColor(R.color.clr_20_22);
            colorFrom = getResources().getColor(R.color.clr_18_19);
        }

        if (colorNow == colorTo) {
            return;
        } else {
            colorNow = colorTo;
        }

        // 设置颜色动画
        ValueAnimator header_bg_anim = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);

        // 如果是新进入程序，则背景颜色直接设置为当前时间段的颜色，不需要过渡效果
        if (isSetup) {
            lyHeaderBg.setBackgroundColor(colorNow);
            isSetup = false;
        } else {
            // 颜色变化时常15s
            header_bg_anim.setDuration(15000);
            header_bg_anim.addUpdateListener(new AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    lyHeaderBg.setBackgroundColor((int) animation.getAnimatedValue());
                }
            });
            header_bg_anim.start();
        }
    }

    /**
     * 更新剩余时间
     */
    private void updateRemainingTime() {
        nowTime.setToNow();
        hour = nowTime.hour;
        minute = nowTime.minute;
        second = nowTime.second;
        if (hour <= 11) {
            tvTimeTitle.setText(R.string.txt_newday_time_title);
            tvRemainHour.setText(hour <= 9 ? "0" + hour : "" + hour);
            tvRemainMinutes.setText(minute <= 9 ? "0" + minute : "" + minute);
            tvRemainSecond.setText(second <= 9 ? "0" + second : "" + second);
        } else {
            tvTimeTitle.setText(R.string.txt_remaining_time_title);
            tvRemainHour.setText(23 - hour <= 9 ? "0" + (23 - hour) : "" + (23 - hour));
            tvRemainMinutes.setText(59 - minute <= 9 ? "0" + (59 - minute) : "" + (59 - minute));
            tvRemainSecond.setText(59 - second <= 9 ? "0" + (59 - second) : "" + (59 - second));
        }

        //每5秒更新任务列表的样式
        if (second % 5 == 0)
            adtTask.notifyDataSetChanged();
    }

    /**
     * 任务列表点击事件监听器
     */
    @Override
    public void onItemClick(RecyclerView parent, View item, int position) {
        switch (parent.getId()) {
            case R.id.rv_main_task: {
                if (taskDetailPopupWindow == null)
                    showTaskDetailPopUpWindow(item, position);
                break;
            }
            default:
                break;
        }
    }

    /**
     * 显示任务详情弹出框
     *
     * @param view
     * @param position
     */
    private void showTaskDetailPopUpWindow(View view, int position) {
        // 设置宽高
        int screenWidth = (int) (ScreenModule.getScreenWidthPX(this) * 0.9);
        int screenHeight = (int) (ScreenModule.getScreenHeightPX(this) * 0.7);
        if (taskDetailPopupWindow == null) {
            taskDetailPopupWindow = new TaskDetailPopupWindow(this, R.layout.ly_task_detail, screenWidth, screenHeight,adtTask.getItem(position));
        }

        // 设置背景遮罩动画
        Bitmap snapShotBitmap = ScreenModule.snapShotWithoutStatusBar(this);
        final Bitmap blurBitmap = ImageModule.getFastBlurBitmap(snapShotBitmap, 0.1f, 4);
        snapShotBitmap.recycle();
        snapShotBitmap = null;
        ivTaskDetailMask.setImageBitmap(blurBitmap);

        // 显示被隐藏的遮罩层
        ivTaskDetailMask.setVisibility(View.VISIBLE);
        ivTaskDetailMaskShadow.setVisibility(View.VISIBLE);

        // 设置遮罩层弹出动画
        AnimatorSet alphaAnimatorSet = new AnimatorSet();
        alphaAnimatorSet.play(ObjectAnimator.ofFloat(ivTaskDetailMask, "alpha", 0f, 1f)).with(ObjectAnimator.ofFloat(ivTaskDetailMaskShadow, "alpha", 0f, 0.1f));
        alphaAnimatorSet.setDuration(250);
        alphaAnimatorSet.start();

        taskDetailPopupWindow.show(view);
        taskDetailPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                AnimatorSet alphaAnimatorSet = new AnimatorSet();
                alphaAnimatorSet.play(ObjectAnimator.ofFloat(ivTaskDetailMask, "alpha", 1f, 0f)).with(ObjectAnimator.ofFloat(ivTaskDetailMaskShadow, "alpha", 0.1f, 0f));
                alphaAnimatorSet.setDuration(250);
                alphaAnimatorSet.addListener(new AnimatorListener() {

                    // 当动画结束时
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // 隐藏遮罩层
                        ivTaskDetailMask.setVisibility(View.GONE);
                        ivTaskDetailMaskShadow.setVisibility(View.GONE);
                        blurBitmap.recycle();
                        taskDetailPopupWindow = null;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                alphaAnimatorSet.start();

                //查看popupwindow关闭前是否点击完成按钮
                if (taskDetailPopupWindow.isTaskFinished())
                    adtTask.removeItem(taskDetailPopupWindow.getTask());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_main_floating_action_button: {
                if (addTaskPopupWindow == null)
                    showAddTaskPopupWindow();
                break;
            }
            default:
                break;
        }
    }

    public void showAddTaskPopupWindow() {
        // 设置宽高
        int screenWidth = (int) (ScreenModule.getScreenWidthPX(this) * 0.9);
        int screenHeight = (int) (ScreenModule.getScreenHeightPX(this) * 0.7);
        if (addTaskPopupWindow == null) {
            addTaskPopupWindow = new AddTaskPopupWindow(this, R.layout.ly_add_task, screenWidth, screenHeight);
        }

        // 设置背景遮罩动画
        Bitmap snapShotBitmap = ScreenModule.snapShotWithoutStatusBar(this);
        final Bitmap blurBitmap = ImageModule.getFastBlurBitmap(snapShotBitmap, 0.1f, 4);
        snapShotBitmap.recycle();
        snapShotBitmap = null;
        ivTaskDetailMask.setImageBitmap(blurBitmap);

        // 显示被隐藏的遮罩层
        ivTaskDetailMask.setVisibility(View.VISIBLE);
        ivTaskDetailMaskShadow.setVisibility(View.VISIBLE);

        // 设置遮罩层弹出动画
        AnimatorSet alphaAnimatorSet = new AnimatorSet();
        alphaAnimatorSet.play(ObjectAnimator.ofFloat(ivTaskDetailMask, "alpha", 0f, 1f)).with(ObjectAnimator.ofFloat(ivTaskDetailMaskShadow, "alpha", 0f, 0.1f));
        alphaAnimatorSet.setDuration(250);
        alphaAnimatorSet.start();

        addTaskPopupWindow.show(getWindow().getDecorView());
        addTaskPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                AnimatorSet alphaAnimatorSet = new AnimatorSet();
                alphaAnimatorSet.play(ObjectAnimator.ofFloat(ivTaskDetailMask, "alpha", 1f, 0f)).with(ObjectAnimator.ofFloat(ivTaskDetailMaskShadow, "alpha", 0.1f, 0f));
                alphaAnimatorSet.setDuration(250);
                alphaAnimatorSet.addListener(new AnimatorListener() {

                    // 当动画结束时
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // 隐藏遮罩层
                        ivTaskDetailMask.setVisibility(View.GONE);
                        ivTaskDetailMaskShadow.setVisibility(View.GONE);
                        blurBitmap.recycle();
                        addTaskPopupWindow = null;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                alphaAnimatorSet.start();
                //popupwindow关闭时查看是否有返回结果
                if (addTaskPopupWindow.getResult() != null)
                    adtTask.addItem(addTaskPopupWindow.getResult(),0);
            }
        });
    }

}
package net.rippletec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.rippletec.dao.DBHelper;
import net.rippletec.dao.TaskData;
import net.rippletec.rippleSchedule.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * 主界面RecyclerView的adapter
 *
 * @author 钟毅凯
 * @updateDate 2016/03/09
 */
public class TaskListAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater lyInflater;
    private RecyclerView recyclerView;
    private List<TaskData> itemList;

    public TaskListAdapter(Context context, final List<TaskData> taskList, RecyclerView recyclerView) {
        super();
        this.context = context;
        this.lyInflater = LayoutInflater.from(context);
        this.itemList = taskList;
        this.recyclerView = recyclerView;
        // 设置任务列表的拖拽、滑动动画
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(itemList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem(taskList.get(viewHolder.getAdapterPosition()));
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = lyInflater.inflate(R.layout.ly_task_item, parent, false);
        return new ItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskData task = itemList.get(position);
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        itemHolder.tvTaskTitle.setText(task.getTaskDesc());
        itemHolder.tvTaskTitle.setTextColor(task.getSignalColor());

        final String strRemainTimeToStart = context.getResources().getString(R.string.txt_remain_time_to_start);
        final String strRemainTime = context.getResources().getString(R.string.txt_remain_time);
        final String strDay = context.getResources().getString(R.string.txt_day);
        final String strHour = context.getResources().getString(R.string.txt_hour);
        final String strMinute = context.getResources().getString(R.string.txt_minute);
        //根据当前时间计算显示的剩余时间及左边图标
        try {
            DateFormat dateToMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long currentMillis = System.currentTimeMillis();
            long startMillis = dateToMillis.parse(task.getStartYear() + "-" + task.getStartMonth() + "-" + task.getStartDay() + " " + task.getStartHour() + ":" + task.getStartMinute() + ":00").getTime();
            long durationMillis = dateToMillis.parse("1970-01-" + (task.getLimitDay() + 1) + " " + task.getLimitHour() + ":" + task.getLimitMinute() + ":00").getTime() - dateToMillis.parse("1970-01-01 00:00:00").getTime();
            if (currentMillis > startMillis) {
                long remainMillis = durationMillis - currentMillis + startMillis;
                itemHolder.tvRemainTime.setText(strRemainTime + remainMillis / 1000 / 60 / 60 / 24 + strDay + remainMillis / 1000 / 60 / 60 % 24 + strHour + remainMillis / 1000 / 60 % 60 + strMinute);
                switch ((int) (10 * (remainMillis / (double) durationMillis))) {
                    case 10:
                    case 9:
                    case 8:
                        itemHolder.ivProgress.setImageResource(R.drawable.img_task_90);
                        break;
                    case 7:
                    case 6:
                        itemHolder.ivProgress.setImageResource(R.drawable.img_task_75);
                        break;
                    case 5:
                    case 4:
                        itemHolder.ivProgress.setImageResource(R.drawable.img_task_50);
                        break;
                    case 3:
                    case 2:
                        itemHolder.ivProgress.setImageResource(R.drawable.img_task_25);
                        break;
                    case 1:
                    case 0:
                        itemHolder.ivProgress.setImageResource(R.drawable.img_task_fail);
                        break;
                    default:
                        itemHolder.tvRemainTime.setText(strRemainTime + 0 + strDay + 0 + strHour + 0 + strMinute);
                        itemHolder.ivProgress.setImageResource(R.drawable.img_task_fail);
                        break;
                }
            } else {
                long remainMillis = -currentMillis + startMillis;
                itemHolder.ivProgress.setImageResource(R.drawable.img_task_90);
                itemHolder.tvRemainTime.setText(strRemainTimeToStart + remainMillis / 1000 / 60 / 60 / 24 + strDay + remainMillis / 1000 / 60 / 60 % 24 + strHour + remainMillis / 1000 / 60 % 60 + strMinute);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * 返回position位置的task
     *
     * @param position
     */
    public TaskData getItem(int position) {
        return itemList.get(position);
    }

    /**
     * 在index位置增加该adapter的item
     *
     * @param task
     * @param index
     */
    public void addItem(TaskData task, int index) {
        itemList.add(index, task);
        notifyItemInserted(index);
        recyclerView.scrollToPosition(index);
        DBHelper.addSingleTask(context, task);
    }

    /**
     * 移除该adapter中task条目
     *
     * @param task
     */
    public void removeItem(TaskData task) {
        notifyItemRemoved(itemList.indexOf(task));
        itemList.remove(task);
        DBHelper.removeTask(context, task.getId());
    }

    private static final class ItemViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public ImageView ivProgress;
        public TextView tvTaskTitle;
        public TextView tvRemainTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivProgress = (ImageView) itemView.findViewById(R.id.iv_task_item_progress);
            tvTaskTitle = (TextView) itemView.findViewById(R.id.tv_task_item_title);
            tvRemainTime = (TextView) itemView.findViewById(R.id.tv_task_item_remainTime);
        }
    }
}

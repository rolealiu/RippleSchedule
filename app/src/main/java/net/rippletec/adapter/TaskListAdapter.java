package net.rippletec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.rippletec.dao.TaskData;
import net.rippletec.rippleSchedule.R;

import java.util.ArrayList;

/**
 * 主界面RecyclerView的adapter
 *
 * @author 钟毅凯
 * @updateDate 2016/01/28
 */
public class TaskListAdapter extends RecyclerView.Adapter {
    private LayoutInflater lyInflater;
    private ArrayList<TaskData> itemList;

    public TaskListAdapter(Context context, ArrayList<TaskData> itemList) {
        super();
        this.lyInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = lyInflater.inflate(R.layout.ly_task_item, parent, false);
        return new ItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        itemHolder.tvTaskTitle.setText(itemList.get(position).getTaskDesc());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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

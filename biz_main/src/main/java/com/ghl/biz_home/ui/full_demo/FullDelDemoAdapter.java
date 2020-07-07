package com.ghl.biz_home.ui.full_demo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ghl.biz_home.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/12.
 */

public class FullDelDemoAdapter extends RecyclerView.Adapter<FullDelDemoAdapter.FullDelDemoVH> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<SwipeBean> mDatas;

    public FullDelDemoAdapter(Context context, List<SwipeBean> mDatas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @NotNull
    @Override
    public FullDelDemoVH onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new FullDelDemoVH(mInflater.inflate(R.layout.item_cst_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(final FullDelDemoVH holder, final int position) {
        //这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑
        ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(true);

        holder.content.setText(String.format("%s%s", mDatas.get(position).name, position % 2 == 0 ? "我右白虎" : "我左青龙"));

        //验证长按
        holder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "longclig", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
                return false;
            }
        });

//        holder.btnUnRead.setVisibility(position % 3 == 0 ? View.GONE : View.VISIBLE);

        holder.btnDelete.setOnClickListener(v -> {
            if (null != mOnSwipeListener) {
                //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                //((CstSwipeDelMenu) holder.itemView).quickClose();
                mOnSwipeListener.onDel(holder.getAdapterPosition());
            }
        });
        //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
        (holder.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).name, Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onClick() called with: v = [" + v + "]");
            }
        });
        //置顶：
        holder.btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    mOnSwipeListener.onTop(holder.getAdapterPosition());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel(int pos);

        void onTop(int pos);
    }

    private onSwipeListener mOnSwipeListener;

    public onSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

    static class FullDelDemoVH extends RecyclerView.ViewHolder {
        TextView content;
        Button btnDelete;
        Button btnUnRead;
        Button btnTop;

        public FullDelDemoVH(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUnRead = itemView.findViewById(R.id.btnUnRead);
            btnTop = itemView.findViewById(R.id.btnTop);
        }
    }
}


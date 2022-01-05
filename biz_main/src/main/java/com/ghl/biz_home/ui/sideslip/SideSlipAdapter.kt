package com.ghl.biz_home.ui.sideslip

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ghl.biz_home.R
import com.ghl.biz_home.ui.sideslip.SideSlipAdapter.FullDelDemoVH

class SideSlipAdapter(private val mContext: Context, private val mDatas: List<SwipeBean>?) : RecyclerView.Adapter<FullDelDemoVH>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullDelDemoVH {
        return FullDelDemoVH(mInflater.inflate(R.layout.item_cst_swipe, parent, false))
    }

    override fun onBindViewHolder(holder: FullDelDemoVH, position: Int) {
        //这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑
        (holder.itemView as SwipeMenuLayout).setIos(false).setLeftSwipe(true)

        holder.content.text = String.format("%s%s", mDatas!![position].name, if (position % 2 == 0) "我右白虎" else "我左青龙")

        //验证长按
        holder.content.setOnLongClickListener { v ->
            Toast.makeText(mContext, "longClick", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "onLongClick() called with: v = [$v]")
            false
        }

//        holder.btnUnRead.setVisibility(position % 3 == 0 ? View.GONE : View.VISIBLE);
        holder.btnDelete.setOnClickListener { _: View? ->
            if (null != onDelListener) {
                //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                //((CstSwipeDelMenu) holder.itemView).quickClose();
                onDelListener!!.onDel(holder.adapterPosition)
            }
        }
        //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
        holder.content.setOnClickListener { v ->
            Toast.makeText(mContext, "onClick:" + mDatas[holder.adapterPosition].name, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "onClick() called with: v = [$v]")
        }
        //置顶：
        holder.btnTop.setOnClickListener {
            if (null != onDelListener) {
                onDelListener!!.onTop(holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return mDatas?.size ?: 0
    }

    interface OnSwipeListener {
        fun onDel(pos: Int)
        fun onTop(pos: Int)
    }

    var onDelListener: OnSwipeListener? = null

    class FullDelDemoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content: TextView = itemView.findViewById(R.id.content)
        var btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        var btnUnRead: Button = itemView.findViewById(R.id.btnUnRead)
        var btnTop: Button = itemView.findViewById(R.id.btnTop)

    }

}
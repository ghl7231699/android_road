package com.ghl.biz_home.ui.sideslip

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ghl.biz_home.R
import com.ghl.biz_home.ui.sideslip.SideSlipAdapter.OnSwipeListener
import com.ghl.biz_home.ui.sideslip.SwipeMenuLayout.Companion.viewCache
import com.ghl.common.constants.main.ACTIVITY_SIDE_SLIP
import com.ghl.router_annotation.Route
import java.util.*

/**
 * 侧滑删除
 */
@Route(ACTIVITY_SIDE_SLIP)
class SideSlipActivity : Activity() {
    private lateinit var mRv: RecyclerView
    private lateinit var mAdapter: SideSlipAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mDatas: MutableList<SwipeBean>

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_del_demo)
        mRv = findViewById(R.id.rv)
        initDatas()


        mAdapter = SideSlipAdapter(this, mDatas)

        mRv.apply {
            adapter = mAdapter
            //        mRv.setLayoutManager(mLayoutManager = new GridLayoutManager(this, 2));
            layoutManager = LinearLayoutManager(this@SideSlipActivity).also { mLayoutManager = it }

            // 可以用在：当点击外部空白处时，关闭正在展开的侧滑菜单。我个人觉得意义不大，
            setOnTouchListener(View.OnTouchListener { _: View?, event: MotionEvent ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val viewCache = viewCache
                    viewCache?.smoothClose()
                }
                false
            })
        }

        mAdapter.apply {
            onDelListener = object : OnSwipeListener {
                override fun onDel(pos: Int) {
                    if (pos >= 0 && pos < mDatas.size) {
                        Toast.makeText(this@SideSlipActivity, "删除:$pos", Toast.LENGTH_SHORT).show()
                        mDatas.removeAt(pos)
                        notifyItemRemoved(pos) //推荐用这个
                        //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                        //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                        //mAdapter.notifyDataSetChanged();
                    }
                }

                override fun onTop(pos: Int) {
                    if (pos > 0 && pos < mDatas.size) {
                        val swipeBean = mDatas[pos]
                        mDatas.remove(swipeBean)
                        notifyItemInserted(0)
                        mDatas.add(0, swipeBean)
                        mAdapter.notifyItemRemoved(pos + 1)
                        if (mLayoutManager.findFirstVisibleItemPosition() == 0) {
                            mRv.scrollToPosition(0)
                        }
                        //notifyItemRangeChanged(0,holder.getAdapterPosition()+1);
                    }
                }
            }
        }

    }

    private fun initDatas() {
        mDatas = ArrayList()
        for (i in 0..19) {
            mDatas.add(SwipeBean("" + i))
        }
    }
}
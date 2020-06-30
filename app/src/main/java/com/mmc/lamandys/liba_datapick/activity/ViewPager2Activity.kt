package com.mmc.lamandys.liba_datapick.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ghl.lib_dirty.base.BaseActivity
import com.ghl.lib_dirty.constants.Constants.View_Pager2_Activity
import com.ghl.lib_dirty.widgets.transform.ScaleInTransformer
import com.ghl.router_annotation.Route
import com.mmc.lamandys.liba_datapick.R

@Route(View_Pager2_Activity)
class ViewPager2Activity : BaseActivity(), View.OnClickListener {

    private var mViewPager2: ViewPager2? = null
    private var mChange: TextView? = null
    private var mFakeBy: TextView? = null
    private var mAdapter: ViewPager2Adapter? = null
    private var orientation: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager2)
        initView()
    }

    private fun initView() {
        mViewPager2 = findViewById(R.id.view_pager)
        val list: MutableList<String> = ArrayList()
        for (i in 0..3) {
            list.add(i, "我是第 $i 页")
        }
        mAdapter = ViewPager2Adapter(list)
        mViewPager2?.adapter = mAdapter

        mChange = findViewById(R.id.tv_orientation)
        mChange?.setOnClickListener(this)

        mFakeBy = findViewById(R.id.tv_fake)
        mFakeBy?.setOnClickListener(this)



        mViewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Toast.makeText(this@ViewPager2Activity, "page selected $position", Toast.LENGTH_SHORT).show()
            }
        })



        mViewPager2?.apply {
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = dip2px(this@ViewPager2Activity, 40f)

                setPadding(padding, 0, padding, 0)
                clipToPadding = false

            }

        }


//        mViewPager2?.isUserInputEnabled=false//设置禁止滑动
        //设置页面间距
//        mViewPager2?.setPageTransformer(MarginPageTransformer(dip2px(this, 50f)))
        //集合式的PageTransformer
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(ScaleInTransformer())
        compositePageTransformer.addTransformer(MarginPageTransformer(dip2px(this, 20f)))
        mViewPager2?.setPageTransformer(compositePageTransformer)


    }

    private fun fakeDragBy(v: View?) {
        mViewPager2?.beginFakeDrag()
        if (mViewPager2?.fakeDragBy(-310f)!!) {
            mViewPager2?.endFakeDrag()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_orientation -> notifyChanged()
            R.id.tv_fake -> fakeDragBy(null)
        }
    }

    private fun notifyChanged() {
        if (!orientation!!) {
            mViewPager2?.orientation = ViewPager2.ORIENTATION_VERTICAL
            orientation = true
        } else {
            mViewPager2?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            orientation = false
        }
        mAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}

class ViewPager2Adapter(list: List<String>) : RecyclerView.Adapter<ViewPager2Holder>() {
    private var mList: List<String> = list

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewPager2Holder, position: Int) {
        holder.bindData(mList[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_viewpager2_item, parent, false)
        return ViewPager2Holder(itemView)
    }

}

class ViewPager2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mIv: AppCompatImageView = itemView.findViewById(R.id.iv_item)
    private val mGiv: AppCompatImageView = itemView.findViewById(R.id.giv_item)
    private var colors = arrayOf(R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3, R.drawable.banner_4)

    fun bindData(i: String, position: Int) {
//        val random = Random
//        val nextInt = random.nextInt(3)
//        mIv.setBackgroundColor(Color.parseColor(colors[nextInt]))
        mIv.setBackgroundResource(colors[position])
//        mGiv.setBackgroundResource(colors[position])
    }
}

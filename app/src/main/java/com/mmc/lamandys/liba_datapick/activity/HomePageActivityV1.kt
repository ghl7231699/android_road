package com.mmc.lamandys.liba_datapick.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.mmc.lamandys.liba_datapick.R
import com.mmc.lamandys.liba_datapick.adapter.HomePageAdapter
import com.mmc.lamandys.liba_datapick.base.BaseActivity
import com.mmc.lamandys.liba_datapick.util.StatusBarUtils
import com.mmc.lamandys.liba_datapick.widgets.transform.ScaleInTransformer
import kotlinx.android.synthetic.main.activity_home_page_layout_v1.*

class HomePageActivityV1 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.transparencyBar(this)
        setContentView(R.layout.activity_home_page_layout_v1)
        initView()
    }

    private fun initView() {
        view_pager_home_page.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        // 设置 三屏同时显示
        view_pager_home_page.apply {
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = ViewPager2Activity.dip2px(this@HomePageActivityV1, 20f)
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
        }

        //集合式的PageTransformer
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(ScaleInTransformer())
        compositePageTransformer.addTransformer(MarginPageTransformer(dip2px(this, 10f)))
        view_pager_home_page?.setPageTransformer(compositePageTransformer)

        val mAdapter = HomeBannerAdapter()
        view_pager_home_page?.adapter = mAdapter

        with(rv_home_page) {
            val params = GridLayoutManager(this@HomePageActivityV1, 4)
            params.orientation = LinearLayoutManager.VERTICAL
            layoutManager = params
            adapter = HomePageAdapter(initData())
        }
    }

    private fun initData(): List<String> {
        val list = mutableListOf<String>()
        list.add("111")
        list.add("111")
        list.add("111")
        list.add("111")
        list.add("111")
        list.add("111")
        list.add("111")
        list.add("111")
        list.add("111")

        return list
    }

    companion object {
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }

}

class HomeBannerAdapter : RecyclerView.Adapter<HomeBannerViewHolder>() {

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_viewpager2_item, parent, false)
        return HomeBannerViewHolder(itemView)
    }
}

class HomeBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mIv: AppCompatImageView? = itemView.findViewById(R.id.iv_item)
    private var colors: Array<Int> = arrayOf(R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3, R.drawable.banner_4)

    init {
        mIv?.setOnClickListener { itemView.context.startActivity(Intent(itemView.context, MainActivity::class.java)) }
    }


    fun bindData(position: Int) {
        mIv?.setBackgroundResource(colors[position])
    }
}
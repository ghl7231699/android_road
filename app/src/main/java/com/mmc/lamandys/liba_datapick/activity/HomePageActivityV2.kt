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
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mmc.lamandys.liba_datapick.Constants
import com.mmc.lamandys.liba_datapick.ENGINE_ID
import com.mmc.lamandys.liba_datapick.R
import com.mmc.lamandys.liba_datapick.activity.ui.login.LoginActivity
import com.mmc.lamandys.liba_datapick.adapter.HomePageAdapter
import com.mmc.lamandys.liba_datapick.util.StatusBarUtils
import io.flutter.app.FlutterActivity
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterMain

class HomePageActivityV2 : FlutterActivity() {


    init {
        val engine = FlutterEngineCache.getInstance().get(ENGINE_ID)

        val channel = MethodChannel(engine?.dartExecutor, Constants.Method_channel)

        channel.setMethodCallHandler { call, _ ->
            when (call.method) {
                Constants.method_finish -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
    }

    private var mRecyclerView: RecyclerView? = null
    var mBannerRecyclerView: RecyclerView? = null
    private lateinit var methodChannel: MethodChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.transparencyBar(this)
        setContentView(R.layout.activity_home_page_layout_v2)
        //强烈建议放到Application里初始化,初始化一次即可,放这里只是举个例子
        FlutterMain.startInitialization(this)
        initView()
    }


    private fun initView() {
        mRecyclerView = findViewById(R.id.rv_home_page)
        mBannerRecyclerView = findViewById(R.id.tv_home_page_banner)

        val params = GridLayoutManager(this, 2)
        params.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView?.layoutManager = params
        mRecyclerView?.adapter = HomePageAdapter(initData())

        val mAdapter = HomeV2BannerAdapter()
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mBannerRecyclerView)
        mBannerRecyclerView?.layoutManager = manager
        mBannerRecyclerView?.adapter = mAdapter

    }

    private fun initData(): List<String> {
        val list = mutableListOf<String>()
        list.add("ViewPager2")
        list.add("RecyclerView")
        list.add("ToolBar")
        list.add("Radio")
        list.add("Drawer")
        list.add("Animation")
        list.add("自定义Behavior")
        list.add("调用flutter页面")
        list.add("调用指定flutter页面")
        list.add("Flutter与原生交互")
        list.add("咸鱼FlutterBoost")

        return list
    }

    companion object {
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }

}


class HomeV2BannerAdapter : RecyclerView.Adapter<HomeV2BannerViewHolder>() {

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: HomeV2BannerViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeV2BannerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_viewpager2_item, parent, false)
        return HomeV2BannerViewHolder(itemView)
    }
}

class HomeV2BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mIv: AppCompatImageView? = null
    private var colors: Array<Int>

    init {
        mIv = itemView.findViewById(R.id.iv_item)
        colors = arrayOf(R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3, R.drawable.banner_4)

        mIv?.setOnClickListener { itemView.context.startActivity(Intent(itemView.context, MainActivity::class.java)) }

    }


    fun bindData(position: Int) {
        mIv?.setBackgroundResource(colors[position])
    }
}
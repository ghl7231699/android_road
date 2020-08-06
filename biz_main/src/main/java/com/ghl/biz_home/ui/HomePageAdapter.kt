package com.ghl.biz_home.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.RecyclerView
import com.ghl.biz_home.R
import com.ghl.lib_dirty.ENGINE_ID
import com.ghl.lib_dirty.constants.Constants
import com.ghl.lib_dirty.constants.login.LOGIN_TEST_PAGE
import com.ghl.lib_dirty.constants.main.ACTIVITY_EXPANDABLE_LIST
import com.ghl.lib_dirty.constants.main.ACTIVITY_LIVE_DATA_VIEW_MODEL
import com.ghl.lib_dirty.constants.main.ACTIVITY_SIDE_SLIP
import com.ghl.lib_dirty.constants.track.ACTIVITY_BEHAVIOR
import com.ghl.lib_dirty.constants.track.ACTIVITY_DRAWER
import com.ghl.lib_dirty.constants.track.ACTIVITY_RADIO
import com.ghl.lib_dirty.constants.track.ACTIVITY_TOOL_BAR
import com.ghl.lib_dirty.flutter.PageRouter
import com.ghl.router.lib.Router
import com.idlefish.flutterboost.containers.BoostFlutterActivity.createDefaultIntent
import io.flutter.embedding.android.FlutterActivity

class HomePageAdapter(list: List<String>) : RecyclerView.Adapter<HomePageViewHolder>() {
    private val datas: List<String> = list
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        holder.bind(datas[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        return HomePageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_page, parent, false))
    }
}

class HomePageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mTv: TextView = itemView.findViewById(R.id.item_tv)
    private val mContext: Context = itemView.context
    fun bind(text: String, position: Int) {
        mTv.text = text
        mTv.setOnClickListener {
            when (position) {
                0 -> jump(Constants.View_Pager2_Activity)
                1 -> jump(Constants.Tab_Activity)
                2 -> jump(ACTIVITY_TOOL_BAR)
                3 -> jump(ACTIVITY_RADIO)
                4 -> jump(ACTIVITY_DRAWER)
                5 -> makeText(mContext, "属性动画", Toast.LENGTH_SHORT).show()
                6 -> jump(ACTIVITY_BEHAVIOR)
                7 -> mContext.startActivity(createDefaultIntent(mContext))
                8 -> mContext.startActivity(FlutterActivity
                        .withNewEngine()
                        .initialRoute("/login/index")
                        .build(mContext))
                9 -> mContext.startActivity(FlutterActivity
                        .withCachedEngine(ENGINE_ID)
                        .build(mContext))
//                10 -> jump(ACTIVITY_FLUTTER_MAIN)
                10 -> {
                    val params = HashMap<String, Any>()
                    params["test1"] = "v_test1"
                    params["test2"] = "v_test2"
                    PageRouter.openPageByUrl(mContext, PageRouter.FLUTTER_PAGE_URL, params)
                }
                11 -> jump(ACTIVITY_LIVE_DATA_VIEW_MODEL)
                12 -> {
//                    val api = NetApi()
//                    api.get("http://www.baidu.com")
//                    api.post("http://www.baidu.com")
//                    RetrofitManager.getInstance()
//                            .createApi(HomeService::class.java)
//                            .getList()
//                            .enqueue(object : Callback<BaseBean<ArticleListInfo>> {
//                                override fun onFailure(call: Call<BaseBean<ArticleListInfo>>, t: Throwable) {
//                                    t.printStackTrace()
//                                }
//
//                                override fun onResponse(call: Call<BaseBean<ArticleListInfo>>, response: Response<BaseBean<ArticleListInfo>>) {
//                                    println(response.body().toString())
//                                }
//                            })
                    jump(LOGIN_TEST_PAGE)
                }
                13 -> {
                    jump(ACTIVITY_SIDE_SLIP)
                }
                14 -> {
                    jump(ACTIVITY_EXPANDABLE_LIST)
                }
            }
        }
    }

    private fun jump(target: String) {
        Router.with(mContext).target(target).start()
    }

}
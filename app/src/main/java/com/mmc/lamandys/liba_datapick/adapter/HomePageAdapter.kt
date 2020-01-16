package com.mmc.lamandys.liba_datapick.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.RecyclerView
import com.mmc.lamandys.liba_datapick.R
import com.mmc.lamandys.liba_datapick.activity.*
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

    private val mTv: Button = itemView.findViewById(R.id.item_tv)
    private val mContext: Context = itemView.context
    fun bind(text: String, position: Int) {
        mTv.text = text

        mTv.setOnClickListener {
            when (position) {
                0 -> jump(ViewPager2Activity::class.java)
                1 -> jump(TabActivity::class.java)
                2 -> jump(ToolBarActivity::class.java)
                3 -> jump(RadioActivity::class.java)
                4 -> jump(DrawerActivity::class.java)
                5 -> makeText(mContext, "属性动画", Toast.LENGTH_SHORT).show()
                6 -> jump(BehaviorActivity::class.java)
                7 -> mContext.startActivity(FlutterActivity.createDefaultIntent(mContext))
                8 -> mContext.startActivity(FlutterActivity.withNewEngine().initialRoute("/login/index").build(mContext))
            }
        }
    }

    private fun jump(clazz: Class<*>) {
        mContext.startActivity(Intent(mContext, clazz))
    }

}
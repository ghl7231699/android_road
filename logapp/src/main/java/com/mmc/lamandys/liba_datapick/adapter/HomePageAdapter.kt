package com.mmc.lamandys.liba_datapick.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmc.lamandys.liba_datapick.R

class HomePageAdapter(list: List<String>) : RecyclerView.Adapter<HomePageViewHolder>() {
    private val datas: List<String> = list
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {

        return HomePageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_page, parent, false))
    }
}

class HomePageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mTv: Button = itemView.findViewById(R.id.item_tv)

    fun bind(text: String) {
        mTv.text = text
    }

}
package com.ghl.biz_home.ui.expandable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ghl.biz_main.R

class ExpandableActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private var dataBeanList: MutableList<DataBean> = mutableListOf()
    private var dataBean: DataBean? = null
    private var mAdapter: RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable_list)
        mRecyclerView = findViewById(R.id.recycle_view)
        initData()
    }

    /**
     * 模拟数据
     */
    private fun initData() {
        for (i in 1..5) {
            dataBean = DataBean()
            dataBean!!.id = i.toString() + ""
            dataBean!!.type = 0
            dataBean!!.parentLeftTxt = "父--$i"
            dataBean!!.parentRightTxt = "父内容--$i"
            dataBean!!.childLeftTxt = "子--$i"
            dataBean!!.childRightTxt = "子内容--$i"
            dataBean!!.childBean = dataBean
            dataBeanList.add(dataBean!!)
        }
        setData()
    }

    private fun setData() {
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = RecyclerAdapter(this, dataBeanList)
        mRecyclerView.adapter = mAdapter
        //滚动监听
        mAdapter!!.setOnScrollListener { pos -> mRecyclerView.scrollToPosition(pos) }
    }
}
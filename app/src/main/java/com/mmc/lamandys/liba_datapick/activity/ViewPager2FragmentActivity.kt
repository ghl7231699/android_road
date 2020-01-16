//package com.mmc.lamandys.liba_datapick.activity
//
//import android.os.Bundle
//import android.util.SparseArray
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentActivity
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import androidx.viewpager2.widget.ViewPager2
//import com.mmc.lamandys.liba_datapick.R
//
//class ViewPager2FragmentActivity : FragmentActivity() {
//
//    private var mViewpager2: ViewPager2? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_pager2_fragment)
//        initView()
//    }
//
//    private fun initView() {
//        mViewpager2 = findViewById(R.id.vp_fragment)
//    }
//}
//
//
//class AdapterFragmentPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
//
//    private val fragments:SparseArray<>
//    override fun createFragment(position: Int): Fragment {
//
//    }
//
//    override fun getItemCount(): Int {
//    }
//
//}
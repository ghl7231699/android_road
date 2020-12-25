package com.ghl.biz_flutter.flutter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ghl.biz_flutter.R
import com.ghl.imc.ModuleServiceManager
import com.ghl.common.service.FlutterModuleService
import io.flutter.embedding.android.FlutterFragment

class MyFlutterFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_flutter, container, false)

        val initialRoute = FlutterFragment.withNewEngine().initialRoute("/")
        val build = initialRoute.build<FlutterFragment>()

//        val build = FlutterFragment.createDefault()

        fragmentManager?.apply {
            beginTransaction()
                    .replace(R.id.fragment_container, build, ModuleServiceManager
                            .getClassTarget(FlutterModuleService::class.java).getPageTag("flutter_fragment"))
                    .commit()
        }
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}

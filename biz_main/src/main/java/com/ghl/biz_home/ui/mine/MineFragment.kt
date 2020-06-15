package com.ghl.biz_home.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ghl.biz_home.R
import com.ghl.lib_dirty.constants.login.LOGIN_MAIN_PAGE
import com.ghl.router.lib.Router
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_mine_login.setOnClickListener {
            Router.with(activity).target(LOGIN_MAIN_PAGE).start()
        }
    }
}

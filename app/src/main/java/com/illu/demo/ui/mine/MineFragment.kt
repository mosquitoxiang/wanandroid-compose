package com.illu.demo.ui.mine

import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.R
import com.illu.demo.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseVmFragment<MineViewModel>() {

    companion object {
        fun instance() = MineFragment()
    }

    override fun viewModelClass(): Class<MineViewModel>  = MineViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {
        cslLogin.setOnClickListener {
            checkLogin()
        }
        llMinePoints.setOnClickListener {
            checkLogin()
        }
        llPointsRank.setOnClickListener {
            checkLogin()
        }
        llMineShare.setOnClickListener {
            checkLogin()
        }
        llMineCollect.setOnClickListener {
            checkLogin()
        }
        llHistory.setOnClickListener {

        }
        llOpenSource.setOnClickListener {

        }
        llAboutAuthor.setOnClickListener {

        }
        llSetting.setOnClickListener {

        }
    }

    override fun initData() {
    }
}
package com.illu.demo.ui.home

import android.util.Log
import com.illu.baselibrary.base.BaseVmFragment
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R
import com.orhanobut.logger.Logger

class HomeFragment : BaseVmFragment<HomeViewModel>() {

    companion object {
        fun INSTANCE() = HomeFragment()
    }

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        LogUtil.d("HomeFragment")
    }

    override fun initData() {
    }
}
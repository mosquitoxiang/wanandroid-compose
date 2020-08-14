package com.illu.demo.ui.navigation

import com.illu.demo.base.BaseVmFragment
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R

class NavigationFragment : BaseVmFragment<NavigationViewModel>() {

    companion object {
        fun instance() = NavigationFragment()
    }

    override fun viewModelClass(): Class<NavigationViewModel> = NavigationViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_navigation

    override fun initView() {
        LogUtil.d("NavigationFragment")
    }

    override fun initData() {
    }
}
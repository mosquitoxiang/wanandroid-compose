package com.illu.demo.ui.home

import com.illu.demo.base.BaseVmFragment
import com.illu.demo.R

class HomeFragment : BaseVmFragment<HomeViewModel>() {

    companion object {
        fun INSTANCE() = HomeFragment()
    }

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {

    }

    override fun initData() {
    }
}
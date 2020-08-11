package com.illu.demo.ui.system

import com.illu.demo.base.BaseVmFragment
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R

class SystemFragment : BaseVmFragment<SystemViewModel>() {

    companion object {
        fun INSTANCE() = SystemFragment()
    }

    override fun viewModelClass(): Class<SystemViewModel> = SystemViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_system

    override fun initView() {
        LogUtil.d("SystemFragment")
    }

    override fun initData() {
    }
}
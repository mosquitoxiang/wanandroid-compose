package com.illu.demo.ui.find

import com.illu.demo.base.BaseVmFragment
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R

class FindFragment : BaseVmFragment<FindViewModel>() {

    companion object {
        fun INSTANCE() = FindFragment()
    }

    override fun viewModelClass(): Class<FindViewModel> = FindViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_find

    override fun initView() {
        LogUtil.d("FindFragment")
    }

    override fun initData() {
    }
}
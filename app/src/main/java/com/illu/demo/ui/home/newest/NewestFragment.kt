package com.illu.demo.ui.home.newest

import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment

class NewestFragment : BaseVmFragment<NewestViewModel>() {

    companion object {
        fun INSTANCE() = NewestFragment()
    }
    override fun viewModelClass(): Class<NewestViewModel> = NewestViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_newest

    override fun initView() {
    }

    override fun initData() {
    }

}
package com.illu.demo.ui.home.square

import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment

class SquareFragment : BaseVmFragment<SquareViewModel>() {

    companion object {
        fun INSTANCE() = SquareFragment()
    }

    override fun viewModelClass(): Class<SquareViewModel> = SquareViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_square

    override fun initView() {
    }

    override fun initData() {
    }

}
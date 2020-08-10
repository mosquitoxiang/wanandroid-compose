package com.illu.demo.ui.mine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.illu.baselibrary.base.BaseVmFragment
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R

class MineFragment : BaseVmFragment<MineViewModel>() {

    companion object {
        fun INSTANCE() = MineFragment()
    }

    override fun viewModelClass(): Class<MineViewModel>  = MineViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {
        LogUtil.d("MineFragment")
    }

    override fun initData() {
    }
}
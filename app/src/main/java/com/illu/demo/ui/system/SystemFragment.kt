package com.illu.demo.ui.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.illu.baselibrary.base.BaseVmFragment
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
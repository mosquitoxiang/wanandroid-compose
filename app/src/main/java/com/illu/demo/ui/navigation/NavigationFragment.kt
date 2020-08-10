package com.illu.demo.ui.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.illu.baselibrary.base.BaseVmFragment
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R

class NavigationFragment : BaseVmFragment<NavigationViewModel>() {

    companion object {
        fun INSTANCE() = NavigationFragment()
    }

    override fun viewModelClass(): Class<NavigationViewModel> = NavigationViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_navigation

    override fun initView() {
        LogUtil.d("NavigationFragment")
    }

    override fun initData() {
    }
}
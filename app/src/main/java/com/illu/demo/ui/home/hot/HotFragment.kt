package com.illu.demo.ui.home.hot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment

class HotFragment : BaseVmFragment<HotViewModel>() {

    companion object {
        fun INSTANCE() = HotFragment()
    }
    override fun viewModelClass(): Class<HotViewModel> = HotViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {

    }

}
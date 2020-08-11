package com.illu.demo.ui.home.gzh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment

class GzhFragment : BaseVmFragment<GzhViewModel>() {

    companion object {
        fun INSTANCE() = GzhFragment()
    }

    override fun viewModelClass(): Class<GzhViewModel> = GzhViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_gzh

    override fun initView() {
    }

    override fun initData() {
    }

}
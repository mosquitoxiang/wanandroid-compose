package com.illu.baselibrary.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

abstract class BaseVmFragment <VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(requireActivity()).get(viewModelClass())
    }

    abstract fun viewModelClass() : Class<VM>
}
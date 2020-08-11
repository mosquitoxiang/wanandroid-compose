package com.illu.demo.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.common.isLogin

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
    
    fun checkLogin(then: (() -> Unit)?= null): Boolean {
        return if (isLogin()) {
            then?.invoke()
            true
        } else {
//            ActivityHelper.start(LoginActivity::class.java)
            false
        }
    }
}
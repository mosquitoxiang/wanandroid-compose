package com.illu.demo.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.common.isLogin
import com.illu.demo.ui.login.LoginActivity

abstract class BaseVmFragment <VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM
    private var lazyLoaded = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        observe()
    }

    override fun onResume() {
        super.onResume()
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }

    open fun lazyLoadData() {
        // Override if need
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
            ActivityHelper.start(LoginActivity::class.java)
            false
        }
    }

    open fun observe() {
        mViewModel.loginStatusInvalid.observe(viewLifecycleOwner, Observer {
            if (it) {
                ActivityHelper.start(LoginActivity::class.java)
            }
        })
    }
}
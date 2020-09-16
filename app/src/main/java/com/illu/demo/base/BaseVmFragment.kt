package com.illu.demo.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_LOGIN_STATE_CHANGED
import com.illu.demo.common.isLogin
import com.illu.demo.ui.login.LoginActivity

abstract class BaseVmFragment <VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM
    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
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
        if (!mViewModel.loginStatusInvalid.hasObservers()) {
            mViewModel.loginStatusInvalid.observe(viewLifecycleOwner, Observer {
                if (it) {
                    Bus.post(USER_LOGIN_STATE_CHANGED, false)
                    ActivityHelper.start(LoginActivity::class.java)
                }
            })
        }
    }
}
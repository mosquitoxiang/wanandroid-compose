package com.illu.demo.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.common.isLogin
import com.illu.demo.ui.login.LoginActivity

abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observer()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    protected abstract fun viewModelClass(): Class<VM>

    open fun observer() {
        mViewModel.loginStatusInvalid.observe(this, Observer {
            if (it) {
                ActivityHelper.start(LoginActivity::class.java)
            }
        })
    }

    fun checkLogin(then: (() -> Unit)?= null): Boolean {
        return if (isLogin()) {
            then?.invoke()
            true
        } else {
            ActivityHelper.start(LoginActivity::class.java)
            false
        }
    }
}
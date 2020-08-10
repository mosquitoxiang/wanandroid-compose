package com.illu.baselibrary.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.illu.baselibrary.core.ActivityHelper

abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    protected abstract fun viewModelClass(): Class<VM>

    open fun observer() {
        mViewModel.loginStatusInvalid.observe(this, Observer {
            if (it) {
//                ActivityHelper.start(LoginActivity::class.java)
            }
        })
    }

//    fun checkLogin(then: (() -> Unit)? = null): Boolean {
//        return if ()
//    }
}
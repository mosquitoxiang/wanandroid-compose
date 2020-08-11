package com.illu.demo.ui

import com.illu.demo.base.BaseActivity
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.R

class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        postDelayed(Runnable { ActivityHelper.start(MainActivity::class.java) }, 1000)
    }

    override fun initData() {
    }
}
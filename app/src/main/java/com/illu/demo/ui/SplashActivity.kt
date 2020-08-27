package com.illu.demo.ui

import android.os.Bundle
import com.illu.demo.base.BaseActivity
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.R
import com.illu.demo.test.Name
import com.illu.demo.test.PieView
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private var list = mutableListOf(
        Name("qq", 20),
        Name("ww", 30),
        Name("ee", 40),
        Name("rr", 50)
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
//        val pv = PieView(this)
        pv.setData(list)
//        postDelayed(Runnable {
//            ActivityHelper.start(MainActivity::class.java)
//            ActivityHelper.finish(SplashActivity::class.java)
//        }, 1000)
    }

    override fun initData() {
    }
}
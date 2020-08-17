package com.illu.demo.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.OnTitleBarListener
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.R
import com.xiaojianjun.wanandroid.common.dialog.ProgressDialogFragment
import com.zhangyu.util.EventBusUtil.register
import com.zhangyu.util.EventBusUtil.unRegister

abstract class BaseActivity : AppCompatActivity(), OnTitleBarListener {

    private lateinit var progressDialogFragment: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if (isRegisterEventbus()) {
            register(this)
        }
//        initImmer()
        initView()
        initData()
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initView()
    open fun initData() {}

    override fun onDestroy() {
        HANDLER.removeCallbacksAndMessages(mHandler)
        super.onDestroy()
        if (isRegisterEventbus()) {
            unRegister(this)
        }
    }

    override fun onLeftClick(v: View) {
        ActivityHelper.finish(this::class.java)
    }

    override fun onRightClick(v: View) {}
    override fun onTitleClick(v: View) {}

    private fun initImmer() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(isDark())
            .statusBarColor(setStatusBarColor())
            .fitsSystemWindows(true)
            .init()
    }

    private val mHandler: Any = hashCode()

    fun postDelayed(r: Runnable?, delayMillis: Long): Boolean {
        var delayMillis = delayMillis
        if (delayMillis < 0) {
            delayMillis = 0
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis)
    }

    fun postAtTime(r: Runnable?, uptimeMillis: Long): Boolean {
        return HANDLER.postAtTime(r, mHandler, uptimeMillis)
    }

    protected fun isDark(): Boolean{
        return true
    }

    protected fun setStatusBarColor(): Int {
        return R.color.white
    }

    protected fun isRegisterEventbus(): Boolean{
        return false
    }

    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }

    companion object {
        private val HANDLER = Handler(Looper.getMainLooper())
    }
}
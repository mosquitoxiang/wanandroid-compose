package com.illu.baselibrary

import android.app.Application
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.manager.SettingManager
import com.illu.baselibrary.utils.isMainProcess
import com.illu.baselibrary.utils.setNightMode
import dagger.hilt.android.HiltAndroidApp

class App : Application(){

    companion object{
        var INSTANCE = Application()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        if (isMainProcess(this)){
            init()
        }
    }

    private fun init() {
        ActivityHelper.init(this)
        setDayNightMode()
    }

    private fun setDayNightMode() {
        setNightMode(SettingManager.getNightMode())
    }
}
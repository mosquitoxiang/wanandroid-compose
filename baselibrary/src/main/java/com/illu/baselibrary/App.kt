package com.illu.baselibrary

import android.app.Application
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.core.clearSpValue
import com.illu.baselibrary.core.putSpValue
import com.illu.baselibrary.core.removeSpValue

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        init()
    }

    private fun init() {
        ActivityHelper.init(this)
    }

    companion object{

        var INSTANCE = Application()
    }
}
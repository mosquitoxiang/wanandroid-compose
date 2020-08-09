package com.illu.baselibrary

import android.app.Application
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.core.clearSpValue
import com.illu.baselibrary.core.putSpValue
import com.illu.baselibrary.core.removeSpValue
import com.illu.baselibrary.utils.isMainProcess

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        if (isMainProcess(this)){
            init()
        }
    }

    private fun init() {
        ActivityHelper.init(this)
    }

    companion object{

        var INSTANCE = Application()
    }
}
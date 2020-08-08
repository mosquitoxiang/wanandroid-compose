package com.illu.giao

import android.app.Application

class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object{

        var INSTANCE = Application()
    }
}
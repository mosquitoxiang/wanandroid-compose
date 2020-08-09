package com.illu.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.illu.baselibrary.core.getSpValue
import com.illu.baselibrary.core.putSpValue

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        putSpValue("q", 1)
        println("qq: " + getSpValue("q", 0))
    }
}
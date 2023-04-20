package com.illu.demo.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.illu.demo.ui.home.AppContent
import com.illu.demo.ui.theme.ComposePracticeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePracticeTheme {
                AppContent()
            }
        }
    }
}
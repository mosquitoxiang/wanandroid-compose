package com.illu.demo.ui.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.illu.demo.R

@Composable
fun SystemPageContent() {
    TitleBarBase(
        isShowLeftIcon = false,
        title = "体系",
        isShowRightIcon = true,
        rightIconId = R.drawable.ic_filter)
}
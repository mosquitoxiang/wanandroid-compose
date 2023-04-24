package com.illu.demo.ui.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.illu.demo.R

@Composable
fun DiscoveryPageContent() {
    TitleBarBase(
        isShowLeftIcon = true,
        title = "发现",
        isShowRightIcon = false,
        leftIconId = R.drawable.ic_add_black_24dp
    )
}
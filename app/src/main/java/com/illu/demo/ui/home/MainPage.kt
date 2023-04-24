package com.illu.demo.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.illu.demo.R

@Composable
fun MainPage(onBottomNavClick: ((Pair<Int, String>) -> Unit)) {
    //首页来的时候禁用了状态栏和导航栏，这里打开
    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = true
    //给状态栏换个颜色和布局相同
    systemUiController.setStatusBarColor(colorResource(id = R.color.white), darkIcons = true)
    var navIndex by rememberSaveable { mutableStateOf(0) }
    //用于控制上滑时隐藏nav
    val isNavVisible = remember { mutableStateOf(true) }
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
    ) {
        val stateHolder = rememberSaveableStateHolder()
        Box(modifier = Modifier
            .padding(it)
            .fillMaxHeight()) {
            when (navIndex) {
                0 -> stateHolder.SaveableStateProvider(key = "首页") {
                    HomePageContent(isNavVisible)
                }
                1 -> stateHolder.SaveableStateProvider(key = "体系") {
                    SystemPageContent()
                }
                2 -> stateHolder.SaveableStateProvider(key = "发现") {
                    DiscoveryPageContent()
                }
                3 -> stateHolder.SaveableStateProvider(key = "导航") {
                    NavigationPageContent()
                }
                4 -> stateHolder.SaveableStateProvider(key = "我的") {
                    MinePageContent()
                }
            }
            NavigationBar(modifier = Modifier.align(Alignment.BottomCenter), isNavVisible, onClick = {
                navIndex = it.first
                onBottomNavClick.invoke(it)
            })
        }
    }
}

@Composable
fun EmptyComingSoon(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.empty_screen_title),
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(id = R.string.empty_screen_subtitle),
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}
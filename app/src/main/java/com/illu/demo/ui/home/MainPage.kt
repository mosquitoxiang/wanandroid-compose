package com.illu.demo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopBar(navIndex)
        },
        bottomBar = {
            NavigationBar(onClick = {
                navIndex = it.first
                onBottomNavClick.invoke(it)
            })
        }
    ) {
        val stateHolder = rememberSaveableStateHolder()
        Box(modifier = Modifier.padding(it)) {
            when (navIndex) {
                0 -> stateHolder.SaveableStateProvider(key = "首页") {
                    HomePageContent()
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
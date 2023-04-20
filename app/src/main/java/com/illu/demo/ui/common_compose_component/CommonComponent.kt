package com.illu.demo.ui.common_compose_component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.illu.demo.R
import kotlinx.coroutines.launch

//可滑动的tablayout
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollableTabLayout(pagerState: PagerState, data: List<String>) {
    val coroutineScope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
        edgePadding = 0.dp,
        backgroundColor = colorResource(id = R.color.white)
    ) {
        data.forEachIndexed { index, item ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(text = item)
                },
                selectedContentColor = colorResource(id = R.color.textColorPrimary),
                unselectedContentColor = colorResource(id = R.color.textColorThird)
            )
        }
    }
}

//固定的，平分屏幕宽度的
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FixedTabLayout(pagerState: PagerState, data: List<String>) {
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
        backgroundColor = colorResource(id = R.color.white)
    ) {
        data.forEachIndexed { index, item ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(text = item)
                },
                selectedContentColor = colorResource(id = R.color.textColorPrimary),
                unselectedContentColor = colorResource(id = R.color.textColorThird)
            )
        }
    }
}

@Composable
fun BoxLayout(
    isLoading: Boolean,
    innerPadding: PaddingValues = PaddingValues(),
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        content()
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
//                color = colorResource(id = R.color.theme_orange)
            )
        }
    }
}

@Composable
fun <T> LazyColumnItemCard(data: List<T>?, content: @Composable (index: Int, item: T) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bgColorSecondary)),
        //控制item间隔的
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(data ?: emptyList()) { index: Int, item: T ->
            Surface(elevation = 1.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.white))
                        .padding(horizontal = 16.dp, vertical = 16.dp)

                ) {
                    content(index, item)
                }
            }
        }
    }
}

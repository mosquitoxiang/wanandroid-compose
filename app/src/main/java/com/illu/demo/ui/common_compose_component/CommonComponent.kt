package com.illu.demo.ui.common_compose_component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.illu.demo.R
import com.illu.demo.bean.ArticleBean
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.abs

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
        backgroundColor = colorResource(id = R.color.white),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> LazyColumnItemCard(
    data: List<T>?,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean,
    isNavVisible: MutableState<Boolean> = mutableStateOf(true),
    loadMore: () -> Unit,
    reload: () -> Unit,
    content: @Composable (index: Int, item: T) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val isScrollToBottom = remember(lazyListState) {
        derivedStateOf {
            val index = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val count = lazyListState.layoutInfo.totalItemsCount - 1
            lazyListState.isScrollInProgress.not() && index == count
        }
    }
    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        if (!data.isNullOrEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.bgColorSecondary)),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = lazyListState,
            ) {
                itemsIndexed(data) { index: Int, item: T ->
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
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(15.dp),
                            color = colorResource(id = R.color.black),
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "加载更多",
                            modifier = Modifier,
                            fontSize = 12.sp
                        )
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { reload.invoke() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "加载失败，请点击重试", color = colorResource(id = R.color.textColorPrimary))
            }
        }
    }

    LaunchedEffect(isScrollToBottom.value) {
        if (isScrollToBottom.value && !isRefreshing) {
            loadMore.invoke()
            delay(200)
        }
    }
    isNavVisible.value = lazyListState.isScrollingUp().first
}

@Composable
private fun LazyListState.isScrollingUp(): Pair<Boolean, Int> {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    var scrollDistance by remember { mutableStateOf(0) }
    val isScrollingUp = remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                scrollDistance = abs(previousScrollOffset - firstVisibleItemScrollOffset)
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
    return isScrollingUp to scrollDistance
}

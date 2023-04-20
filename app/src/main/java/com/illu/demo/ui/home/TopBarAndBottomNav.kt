package com.illu.demo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.illu.demo.R

@Composable
fun TopBar(navIndex: Int) {
    if (navIndex == 0) {
        SearchView()
    } else {
        TitleBar(navIndex)
    }
}

@Preview
@Composable
fun SearchView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp, bottom = 8.dp)
            .height(32.dp)
            .background(colorResource(id = R.color.bgColorThird), RoundedCornerShape(32.dp)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_black_24dp),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.CenterVertically)
                .width(18.dp)
                .height(18.dp),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.textColorThird))
        )
        Text(
            text = "搜索关键词以空格隔开",
            fontSize = 14.sp,
            color = colorResource(id = R.color.textColorThird),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun TitleBar(navIndex: Int) {
    when (navIndex) {
        1 -> TitleBarBase(
            isShowLeftIcon = false,
            title = "体系",
            isShowRightIcon = true,
            rightIconId = R.drawable.ic_filter
        )
        2 -> TitleBarBase(
            isShowLeftIcon = true,
            title = "发现",
            isShowRightIcon = false,
            leftIconId = R.drawable.ic_add_black_24dp
        )
        3 -> TitleBarBase(isShowLeftIcon = false, title = "导航", isShowRightIcon = false)
        4 -> TitleBarBase(isShowLeftIcon = false, title = "我的", isShowRightIcon = false)
    }
}

@Composable
fun TitleBarBase(
    title: String,
    titleSize: Int = 13,
    isShowLeftIcon: Boolean = false,
    leftIconId: Int = 0,
    isShowRightIcon: Boolean = false,
    rightIconId: Int = 0,
    barHeight: Int = 56,
) {
    Surface(elevation = 2.dp) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight.dp)
                .padding(horizontal = 10.dp)
        ) {
            val (leftImage, titleName, rightImage) = createRefs()
            if (isShowLeftIcon) {
                Image(
                    painter = painterResource(id = leftIconId),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(leftImage) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
            }
            Text(
                text = title,
                fontSize = titleSize.sp,
                modifier = Modifier
                    .constrainAs(titleName) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
            )
            if (isShowRightIcon) {
                Image(
                    painter = painterResource(id = rightIconId),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(rightImage) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(15.dp))
            }
        }
    }
}

@Composable
fun NavigationBar(onClick: ((Pair<Int, String>) -> Unit)) {
    var currentItem by remember { mutableStateOf(0) }
    BottomNavigation(
        modifier = Modifier,
        backgroundColor = colorResource(id = R.color.white),
        elevation = 15.dp
    ) {
        HOME_BOTTOM_ICON.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = currentItem == index,
                onClick = {
                    currentItem = index
                    val pair = when (index) {
                        0 -> 0 to HomeRoute.HOME
                        1 -> 1 to HomeRoute.SYSTEM
                        2 -> 2 to HomeRoute.DISCOVER
                        3 -> 3 to HomeRoute.NAVIGATION
                        4 -> 4 to HomeRoute.MINE
                        else -> throw IllegalAccessException("")
                    }
                    onClick.invoke(pair)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .padding(bottom = 3.dp),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.iconTextId),
                        fontSize = 13.sp,
                    )
                },
            )

        }
    }
}
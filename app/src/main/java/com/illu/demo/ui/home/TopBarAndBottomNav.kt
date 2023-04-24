package com.illu.demo.ui.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
fun NavigationBar(
    modifier: Modifier = Modifier,
    isNavVisible: MutableState<Boolean>,
    onClick: ((Pair<Int, String>) -> Unit)
) {
    var currentItem by remember { mutableStateOf(0) }
    var offsetY by remember { mutableStateOf(0.dp) }
    var duration by remember { mutableStateOf(0) }
    if (isNavVisible.value) {
        duration = 225
        offsetY = 0.dp
    } else {
        duration = 175
        offsetY = 154.dp
    }
    val animatedOffset = animateDpAsState(targetValue = offsetY, tween(duration))
    Box(modifier = modifier) {
        BottomNavigation(
            modifier = Modifier
                .offset(y = animatedOffset.value),
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
}
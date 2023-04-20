package com.illu.demo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.illu.demo.R

@Composable
fun MinePageContent() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.bgColorSecondary))
            .fillMaxSize()
    ) {
        Spacer()
        Head() {

        }
        Spacer()
        LL(img = R.drawable.ic_my_integral, title = "我的积分") {

        }
        LL(img = R.drawable.ic_graphic_eq_black_24dp, title = "积分排行") {

        }
        Spacer()
        LL(img = R.drawable.ic_add_circle_outline_black_24dp, title = "我的分享") {

        }
        LL(img = R.drawable.ic_star_border_black_24dp, title = "我的收藏") {

        }
        LL(img = R.drawable.ic_history_black_24dp, title = "浏览历史") {

        }
        Spacer()
        LL(img = R.drawable.ic_github, title = "开源许可") {

        }
        LL(img = R.drawable.ic_info_outline_black_24dp, title = "关于作者") {

        }
        LL(img = R.drawable.ic_settings_black_24dp, title = "系统设置") {

        }
    }
}

@Composable
fun Spacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(colorResource(id = R.color.bgColorSecondary))
    )
}

@Composable
private fun Head(onClick: () -> Unit) {
    Surface(elevation = 1.dp) {
        ConstraintLayout(modifier = Modifier
            .clickable {
                onClick.invoke()
            }
            .background(colorResource(id = R.color.bgColorPrimary))
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            val (imageHead, name, id, tv, rightIcon) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_avatar_black_96dp),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .constrainAs(imageHead) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Composable
private fun LL(
    img: Int,
    title: String,
    onClick: (() -> Unit)
) {
    Surface(elevation = 1.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick.invoke()
                }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = null
            )
            Text(
                text = title,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}
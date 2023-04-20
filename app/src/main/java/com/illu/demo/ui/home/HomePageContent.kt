package com.illu.demo.ui.home

import android.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.illu.baselibrary.ext.htmlToSpanned
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.UserManager
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.isLogin
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.ui.common_compose_component.FixedTabLayout
import com.illu.demo.R
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageContent() {
    val tabs = listOf("热门", "广场", "项目", "公众号")
    val pagerState = rememberPagerState()
    Column {
        FixedTabLayout(data = tabs, pagerState = pagerState)
        HorizontalPager(pageCount = tabs.size, state = pagerState) {
            when (it) {
                0 -> HotContent()
                1 -> SquareContent()
                2 -> ProjectContent()
                3 -> GzhContent()
            }
        }
    }
}

//热门
@Preview
@Composable
fun HotContent() {
    val viewModel: HotViewModel = viewModel()
    val articleListState = viewModel.articleList.observeAsState()
    LazyColumn(
        modifier = Modifier

            .fillMaxSize()
            .background(colorResource(id = R.color.bgColorSecondary)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(articleListState.value ?: emptyList()) { index, item ->
            Surface(elevation = 1.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.white))
                        .padding(horizontal = 8.dp, vertical = 8.dp)

                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (item.top) {
                            Text(
                                text = "置顶",
                                color = colorResource(id = R.color.colorBadge),
                                modifier = Modifier.padding(end = 16.dp),
                                fontSize = 12.sp
                            )
                        }
                        when {
                            item.author.isNotEmpty() -> {
                                item.author
                            }
                            item.shareUser.isNotEmpty() -> {
                                item.shareUser
                            }
                            else -> "匿名"
                        }.let {
                            Text(
                                text = it,
                                color = colorResource(id = R.color.textColorPrimary),
                                modifier = Modifier.padding(end = 8.dp),
                                fontSize = 12.sp
                            )
                        }
                        if (item.tags.isNotEmpty()) {
                            Card(
                                shape = RoundedCornerShape(2.dp), elevation = 0.dp,
                                modifier = Modifier,
                                border = BorderStroke(
                                    0.8.dp,
                                    colorResource(id = R.color.textColorPrimary)
                                )
                            ) {
                                Text(
                                    text = item.tags[0].name,
                                    color = colorResource(id = R.color.textColorPrimary),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(vertical = 1.5.dp, horizontal = 3.dp)
                                )
                            }
                        }
                        val chapter = when {
                            item.superChapterName.isNotEmpty() && item.chapterName.isNotEmpty() ->
                                "${item.superChapterName.htmlToSpanned()}/${item.chapterName.htmlToSpanned()}"
                            item.superChapterName.isEmpty() && item.chapterName.isNotEmpty() ->
                                item.chapterName.htmlToSpanned()
                            item.superChapterName.isNotEmpty() && item.chapterName.isEmpty() ->
                                item.superChapterName.htmlToSpanned()
                            else -> ""
                        }
                        Text(
                            text = "$chapter",
                            color = colorResource(id = R.color.textColorThird),
                            modifier = Modifier.weight(1f),
                            fontSize = 12.sp,
                            textAlign = TextAlign.End //假如文本的长度为100,内容的长度为20，这样置顶等于文本在最后面
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        maxLines = 2,
                        text = item.title,
                        overflow = TextOverflow.Ellipsis,//超出部分省略号
                        color = colorResource(
                            id = R.color.textColorPrimary
                        ),
                        fontSize = 13.sp
                    )
                    if (item.desc.isNotEmpty()) {
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            maxLines = 3,
                            text = item.desc.htmlToSpanned().toString(),
                            overflow = TextOverflow.Ellipsis,//超出部分省略号
                            color = colorResource(
                                id = R.color.textColorSecondary
                            ),
                            fontSize = 13.sp
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (item.desc.isNotEmpty()) {
                            Text(
                                text = "新",
                                color = colorResource(id = R.color.colorBadge),
                                modifier = Modifier.padding(end = 8.dp),
                                fontSize = 12.sp,
                            )
                        }
                        Text(
                            text = item.niceDate,
                            color = colorResource(id = R.color.textColorThird),
                            modifier = Modifier.weight(1f),
                            fontSize = 12.sp,
                        )
                        val image = if (item.collect) {
                            painterResource(id = R.drawable.ic_star_black_24dp)
                        } else {
                            painterResource(id = R.drawable.ic_star_border_black_24dp)
                        }
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(top = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

//广场
@Composable
fun SquareContent() {

}

//项目
@Composable
fun ProjectContent() {

}

//公众号
@Composable
fun GzhContent() {

}
package com.illu.demo.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.illu.baselibrary.ext.htmlToSpanned
import com.illu.demo.R
import com.illu.demo.ui.common_compose_component.FixedTabLayout
import com.illu.demo.ui.common_compose_component.LazyColumnItemCard

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
    LazyColumnItemCard(articleListState.value) { index, item ->
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

//广场
@Composable
fun SquareContent() {
    val viewModel: SquareViewModel = viewModel()
    val articleListState = viewModel.articleList.observeAsState()
    LazyColumnItemCard(articleListState.value) { index, item ->
        Column {
            Text(
                maxLines = 2,
                text = item.title,
                overflow = TextOverflow.Ellipsis,//超出部分省略号
                color = colorResource(
                    id = R.color.textColorPrimary
                ),
                fontSize = 13.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (item.desc.isNotEmpty()) {
                    Text(
                        text = "新",
                        color = colorResource(id = R.color.colorBadge),
                        modifier = Modifier.padding(end = 8.dp),
                        fontSize = 12.sp,
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

//项目
@Composable
fun ProjectContent() {
    val viewModel: ProjectViewModel = viewModel()
    val treeState = viewModel.treeListLiveData.observeAsState()
    val indexState = remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(treeState.value ?: emptyList()) { index, item ->
                val bgColor = if (indexState.value == index) {
                    R.color.bgColorThird
                } else {
                    R.color.bgColorSecondary
                }
                Text(
                    text = item.name,
                    color = colorResource(id = R.color.selector_color_category_item),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .background(
                            colorResource(id = bgColor),
                            RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .clickable(interactionSource = remember{ MutableInteractionSource() }, indication = null) {
                            indexState.value = index
                            viewModel.changePosition(index)
                        }
                )
            }
        }
        val articleListState = viewModel.articleList.observeAsState()
        LazyColumnItemCard(articleListState.value) { index, item ->
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

//公众号
@Composable
fun GzhContent() {

}
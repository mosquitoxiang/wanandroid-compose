package com.illu.demo.ui.navigation

import com.illu.demo.bean.ArticleBean
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nav(
    var articles: List<ArticleBean>,
    var cid: Int = 0,
    var name: String? = ""
): Parcelable

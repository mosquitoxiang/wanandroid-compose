package com.illu.demo.ui.navigation

import android.os.Parcelable
import com.illu.demo.bean.ArticleBean
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nav(
    var articles: List<ArticleBean>,
    var cid: Int = 0,
    var name: String? = ""
): Parcelable
